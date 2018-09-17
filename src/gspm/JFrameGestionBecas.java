/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gspm;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author JuanCarlos
 */
public class JFrameGestionBecas extends javax.swing.JFrame {

    /**
     * Creates new form JFrameGestionBecas
     */
    public JFrameGestionBecas() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Gestion de becas.");
        setResizable(false);
    }
    
    public int idBeca = 0;
    public String nombreBeca = "";
    public int descuentoBeca = 0;
    public String descripcionBeca = "";
    
    public boolean nombreCorrecto = false;
    public boolean descuentoCorrecto = false;
    public boolean descripcionCorrecto = false;
    
    public boolean modificacion = false;
    
    protected Connection conexion;
    protected Statement sentencia;
    protected ResultSet resultados;
    
    public String consulta(String consulta){
        String str = "";
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            resultados = sentencia.executeQuery(consulta);
            
            int col = resultados.getMetaData().getColumnCount();
            while (resultados.next()) {
                for (int i = 1; i <= col; i++) {
                    str += resultados.getObject(i).toString() + "°°°";
                }
                str += "\n";
            }
        } catch (SQLException e) {
        }
        
        return str;
    }
    
    
    public boolean cargarBaseDatosBecaBuscada(String idBecaBuscada){
        String consulta = consulta("select * from Beca where Nombre='"+idBecaBuscada+"';");
        String data[] = consulta.split("°°°");
        
        if (data.length/4 == 1) {
            idBeca = Integer.parseInt(data[0]);
            nombreBeca = data[1];
            descuentoBeca = Integer.parseInt(data[2]);
            descripcionBeca = data[3];
            cerrarConexion();
            return true;
        } else {
            idBeca = 0;
            cerrarConexion();
            return false;
        }
        
        
    }
    
    public void cerrarConexion() {
        try {
            conexion.close();
            sentencia.close();
            resultados.close();
        } catch (Exception e) {
        }
    }
    
    //Validacion de los campos de texto
    public Matcher patron(String cadena, String expresion){
        Pattern pattern = Pattern.compile(expresion, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cadena);
        
        return matcher;
    }
    
    public boolean validarIdBeca(String cadena){
        String expresion = "([1-9][\\d]{0,7})";
        if(patron(cadena, expresion).matches()){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean validarNombreBeca(String cadena){
        String expresion = "[a-zA-ZñÑ]+";
        //String expresion = "(([a-zA-ZñÑ]+)|(([1-9]|[1]\\d|[2][0-4])(\\s)([a-zA-ZñÑ]+)))((\\s)(([a-zA-ZñÑ]+)|(\\d+)))*([.]$)?";
        if(patron(cadena, expresion).matches()){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean validarDescuentoBeca(String cadena){
        String expresion = "(\\d)|([1-9]\\d)|([1][0][0])";
        if(patron(cadena, expresion).matches()){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean validarDescripcionBeca(String cadena){
        //String expresion = "(([a-zA-ZñÑ]+)|(([1-9]|[1]\\d|[2][0-4])(\\s)([a-zA-ZñÑ]+)))((\\s)(([a-zA-ZñÑ]+)|(\\d+)[%]?))*([.]$)?";
        String expresion = "[a-zA-Z ]+";
        if(patron(cadena, expresion).matches()){
            return true;
        } else {
            return false;
        }
    }
    
    
    public void validarDatosCorrectos() {
        if (descuentoCorrecto && descripcionCorrecto) {
            if (idBeca != 0) {
                if (modificacion) {
                    jButtonEliminarBeca.setEnabled(false);
                    jButtonModificarBeca.setEnabled(true);
                } else {
                    jButtonEliminarBeca.setEnabled(true);
                    jButtonModificarBeca.setEnabled(false);
                }
            } else {
                jButtonRegistrarBeca.setEnabled(true);
            }
        } else {
            jButtonRegistrarBeca.setEnabled(false);
            jButtonModificarBeca.setEnabled(false);
            jButtonEliminarBeca.setEnabled(false);
        }
        
        if (descuentoCorrecto) {
            jTextFieldDescuentoBeca.setEnabled(true);
            jTextAreaDescripcionBeca.setEnabled(true);
        } else {
            jTextAreaDescripcionBeca.setEnabled(false);
        }
        
        /**if (nombreCorrecto) {
            jTextFieldDescuentoBeca.setEnabled(true);
        } else {
            jTextFieldDescuentoBeca.setEnabled(false);
            jTextAreaDescripcionBeca.setEnabled(false);
        }*/
    }
    
    
    //Eventos de botones
    
    public void buscar(String idBecaBuscada) {
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            boolean becaEncontrada = cargarBaseDatosBecaBuscada(idBecaBuscada);
            
            if (becaEncontrada) {
                //jTextFieldNombreBeca.setEnabled(true);
               // jTextFieldNombreBeca.setText(nombreBeca);
                jTextFieldDescuentoBeca.setText(String.valueOf(descuentoBeca));
                jTextAreaDescripcionBeca.setText(descripcionBeca);
                jButtonEliminarBeca.setEnabled(true);
            } else {
                idBeca = 0;
                jTextFieldDescuentoBeca.setEnabled(false);
                //jTextFieldBuscarBeca.setText("");
                //jTextFieldNombreBeca.setText("");
                jTextFieldDescuentoBeca.setText("");
                jTextAreaDescripcionBeca.setText("");
                jButtonEliminarBeca.setEnabled(false);
                int seleccionada = JOptionPane.showConfirmDialog(null, "La beca no se encuentra registrada...\n¿Desea registrarla en el sistema?", "Notificacion...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                switch(seleccionada) {
                    case JOptionPane.YES_OPTION:
                        //jTextFieldNombreBeca.setEnabled(true);
                        jTextFieldDescuentoBeca.setEnabled(true);
                    break;
                    
                    case JOptionPane.NO_OPTION:
                        //jTextFieldBuscarBeca.setText("");
                        jTextFieldNombreBeca.setText("");
                    break;
                    
                    case JOptionPane.CANCEL_OPTION:
                        jTextFieldNombreBeca.setText("");
                        //jTextFieldBuscarBeca.setText("");
                    break;
                }
            }
            
        } catch (SQLException e) {
            System.out.println("erro en agregar MySQL: " + e.getLocalizedMessage());
        }
    }
    
    
    public boolean registrar(String nombre, int descuento, String descripcion){
        boolean b = false;
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            b = sentencia.execute("insert into Beca values(0,\""+ nombre +"\","+ descuento +",\""+ descripcion +"\");");
            
            if(b == false){
                idBeca = 0;
                jTextFieldNombreBeca.setEnabled(true);
                //jTextFieldBuscarBeca.setText("");
                jTextFieldNombreBeca.setText("");
                jTextFieldDescuentoBeca.setText("");
                jTextAreaDescripcionBeca.setText("");
                JOptionPane.showMessageDialog(null, "Se registro la beca de " + nombre + " correctamente", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("erro en agregar MySQL: " + e.getLocalizedMessage());
            System.out.println("insert into Beca values(0,\""+ nombre +"\","+ descuento +",\""+ descripcion +"\");");
        }
        return b;
    }
    
    
    public void modificar(String nombre, int descuento, String descripcion){
        int seleccionada = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea modificar\n el registro?", "Confirmacion...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (seleccionada) {
            case JOptionPane.YES_OPTION:
                
                boolean b = false;
                try {
                    conexion = Conexion.getConnection();
                    sentencia = conexion.createStatement();
                    b = sentencia.execute("UPDATE Beca SET Nombre = \"" + nombre + "\", Descuento = " + descuento + ", Descripcion = \"" + descripcion + "\" Where idBeca = " + idBeca + ";");
                    
                    if (b == false) {
                        idBeca = 0;
                        jTextFieldNombreBeca.setEnabled(true);
                        jTextFieldDescuentoBeca.setEnabled(false);
                        //jTextFieldBuscarBeca.setText("");
                        jTextFieldNombreBeca.setText("");
                        jTextFieldDescuentoBeca.setText("");
                        jTextAreaDescripcionBeca.setText("");
                        JOptionPane.showMessageDialog(null, "El regirtro de la beca se modifico correctamente", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e) {
                    System.out.println("erro en agregar MySQL: " + e.getLocalizedMessage());
                    System.out.println("UPDATE Beca SET Nombre = \"" + nombre + "\", Descuento = " + descuento + ", Descripcion = \"" + descripcion + "\" Where idBeca = " + idBeca + ";");
                }
                cerrarConexion();
                break;

            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(null, "Se cancelo la modificacion de la beca", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                break;

            case JOptionPane.CLOSED_OPTION:
                JOptionPane.showMessageDialog(null, "Se cancelo la modificacion de la beca", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }
    
    public void eliminar(){
        int seleccionada = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar\n el registro?", "Confirmacion...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch(seleccionada) {
            case JOptionPane.YES_OPTION:
                
                boolean b = false;
                try {
                    conexion = Conexion.getConnection();
                    sentencia = conexion.createStatement();
                    b = sentencia.execute("DELETE FROM Beca where idBeca = " + idBeca + ";");
                    
                    if(b == false){
                        idBeca = 0;
                        jTextFieldNombreBeca.setEnabled(true);
                        //jTextFieldBuscarBeca.setText("");
                        jTextFieldNombreBeca.setText("");
                        jTextFieldDescuentoBeca.setText("");
                        jTextAreaDescripcionBeca.setText("");
                        JOptionPane.showMessageDialog(null, "El regirtro de la beca se elimino correctamente", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e) {
                    System.out.println("erro en agregar MySQL: " + e.getLocalizedMessage());
                    System.out.println("DELETE FROM Beca where idBeca = " + idBeca + ";");
                }
            break;
                
            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(null, "Se cancelo la eliminacion de la beca", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
            break;
                
            case JOptionPane.CLOSED_OPTION:
                JOptionPane.showMessageDialog(null, "Se cancelo la eliminacion de la beca", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
            break;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButtonBuscarBeca = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldNombreBeca = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldDescuentoBeca = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaDescripcionBeca = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jButtonRegistrarBeca = new javax.swing.JButton();
        jButtonModificarBeca = new javax.swing.JButton();
        jButtonEliminarBeca = new javax.swing.JButton();
        jButtonRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(51, 0, 0));

        jLabel5.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Escuela de Futbol Club Cruz Azul Guerrero");

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Calle Doctor Grabriel Leyva Alarcon 12");

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText(" Unidad Habitacional, Chilpancingo de los Bravo.");

        jLabel7.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("39090");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7))
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar beca.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jButtonBuscarBeca.setText("Buscar");
        jButtonBuscarBeca.setEnabled(false);
        jButtonBuscarBeca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarBecaActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Nombre de la beca:");

        jTextFieldNombreBeca.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldNombreBecaCaretUpdate(evt);
            }
        });
        jTextFieldNombreBeca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreBecaActionPerformed(evt);
            }
        });
        jTextFieldNombreBeca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNombreBecaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel12)
                .addGap(29, 29, 29)
                .addComponent(jTextFieldNombreBeca, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButtonBuscarBeca, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBuscarBeca)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldNombreBeca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de la beca.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Descuento de la beca (%):");

        jLabel14.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Desccripcion de la beca:");

        jTextFieldDescuentoBeca.setEnabled(false);
        jTextFieldDescuentoBeca.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldDescuentoBecaCaretUpdate(evt);
            }
        });
        jTextFieldDescuentoBeca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldDescuentoBecaKeyTyped(evt);
            }
        });

        jTextAreaDescripcionBeca.setColumns(20);
        jTextAreaDescripcionBeca.setRows(5);
        jTextAreaDescripcionBeca.setEnabled(false);
        jTextAreaDescripcionBeca.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextAreaDescripcionBecaCaretUpdate(evt);
            }
        });
        jTextAreaDescripcionBeca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextAreaDescripcionBecaKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jTextAreaDescripcionBeca);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldDescuentoBeca)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDescuentoBeca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jButtonRegistrarBeca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add.png"))); // NOI18N
        jButtonRegistrarBeca.setText("Registrar");
        jButtonRegistrarBeca.setEnabled(false);
        jButtonRegistrarBeca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarBecaActionPerformed(evt);
            }
        });

        jButtonModificarBeca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/findReplace.png"))); // NOI18N
        jButtonModificarBeca.setText("Actualizar");
        jButtonModificarBeca.setEnabled(false);
        jButtonModificarBeca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarBecaActionPerformed(evt);
            }
        });

        jButtonEliminarBeca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Bin.png"))); // NOI18N
        jButtonEliminarBeca.setText("Eliminar");
        jButtonEliminarBeca.setEnabled(false);
        jButtonEliminarBeca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarBecaActionPerformed(evt);
            }
        });

        jButtonRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/icono volver.png"))); // NOI18N
        jButtonRegresar.setText("Regresar");
        jButtonRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonRegistrarBeca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonModificarBeca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonEliminarBeca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonRegistrarBeca)
                        .addComponent(jButtonModificarBeca))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonEliminarBeca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldNombreBecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreBecaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNombreBecaActionPerformed

    private void jTextFieldNombreBecaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldNombreBecaCaretUpdate
        if (validarNombreBeca(jTextFieldNombreBeca.getText())){
            ///nombreCorrecto = true;
            //jTextFieldDescuentoBeca.setEnabled(true);
            jButtonBuscarBeca.setEnabled(true);
            validarDatosCorrectos();
        } else {
            //nombreCorrecto = false;
           // jTextFieldDescuentoBeca.setEnabled(false);
            //jTextAreaDescripcionBeca.setEnabled(false);
            jButtonBuscarBeca.setEnabled(false);
            validarDatosCorrectos();
        }
        
        if (idBeca != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_jTextFieldNombreBecaCaretUpdate

    private void jTextFieldDescuentoBecaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldDescuentoBecaCaretUpdate
        if(validarDescuentoBeca(jTextFieldDescuentoBeca.getText())){
            descuentoCorrecto = true;
            jTextAreaDescripcionBeca.setEnabled(true);
            validarDatosCorrectos();
        } else {
            descuentoCorrecto = false;
            jTextAreaDescripcionBeca.setEnabled(false);
            validarDatosCorrectos();
        }
        
        if (idBeca != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_jTextFieldDescuentoBecaCaretUpdate

    private void jTextFieldNombreBecaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNombreBecaKeyTyped
        if (jTextFieldNombreBeca.getText().length() == 45){
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_jTextFieldNombreBecaKeyTyped

    private void jTextFieldDescuentoBecaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDescuentoBecaKeyTyped
        if (jTextFieldDescuentoBeca.getText().length() == 1 && jTextFieldDescuentoBeca.getText().charAt(0) == '0') {
            evt.consume();
            JOptionPane.showMessageDialog(null, "En este campo solo se puede ingresar\nun porcentaje del 100% como maximo", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        } else
        if (jTextFieldDescuentoBeca.getText().length() == 2 && jTextFieldDescuentoBeca.getText().charAt(0) != '0' && jTextFieldDescuentoBeca.getText().charAt(0) != '1') {
            evt.consume();
            JOptionPane.showMessageDialog(null, "En este campo solo se puede ingresar\nun porcentaje del 100% como maximo", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        } else
        if (jTextFieldDescuentoBeca.getText().length() == 3 && jTextFieldDescuentoBeca.getText().charAt(0) == '1' && jTextFieldDescuentoBeca.getText().charAt(1) == '0' && jTextFieldDescuentoBeca.getText().charAt(2) == '0') {
            evt.consume();
            JOptionPane.showMessageDialog(null, "En este campo solo se puede ingresar\nun porcentaje del 100% como maximo", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTextFieldDescuentoBecaKeyTyped

    private void jButtonBuscarBecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarBecaActionPerformed
        try {
        
        buscar(jTextFieldNombreBeca.getText());
        jButtonModificarBeca.setEnabled(false);
        } catch (ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null, "Excepcion, no se encontro la beca buscada", "Mensaje de error.", JOptionPane.ERROR_MESSAGE);
        }
        //jTextFieldDescuentoBeca.setEnabled(false);
    }//GEN-LAST:event_jButtonBuscarBecaActionPerformed

    private void jButtonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegresarActionPerformed
        dispose();
        Principal_Admin padmin = new Principal_Admin();
        padmin.setVisible(true);
    }//GEN-LAST:event_jButtonRegresarActionPerformed

    private void jButtonModificarBecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarBecaActionPerformed
        try {
            if ((nombreBeca.equals(jTextFieldNombreBeca.getText())) && (descuentoBeca == Integer.parseInt(jTextFieldDescuentoBeca.getText())) && descripcionBeca.equals(jTextAreaDescripcionBeca.getText())) {
                JOptionPane.showMessageDialog(null, "No se ha hecho ninguna modificacion de los\ncampos de la beca numero "+idBeca, "Notificacion...", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String nombre = jTextFieldNombreBeca.getText();
                int descuento = Integer.parseInt(jTextFieldDescuentoBeca.getText());
                String descripcion = jTextAreaDescripcionBeca.getText();
                
                modificar(nombre, descuento, descripcion);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado alguna fila", "Mensaje de error.", JOptionPane.ERROR_MESSAGE);
        }
        jTextFieldDescuentoBeca.setEnabled(false);
    }//GEN-LAST:event_jButtonModificarBecaActionPerformed

    private void jButtonRegistrarBecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarBecaActionPerformed
        if(registrar(jTextFieldNombreBeca.getText(), Integer.parseInt(jTextFieldDescuentoBeca.getText()), jTextAreaDescripcionBeca.getText()) == false){
            jTextFieldNombreBeca.setText("");
            jTextFieldDescuentoBeca.setText("");
            jTextAreaDescripcionBeca.setText("");
        }
        jTextFieldDescuentoBeca.setEnabled(false);
    }//GEN-LAST:event_jButtonRegistrarBecaActionPerformed

    private void jButtonEliminarBecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarBecaActionPerformed
        try {
            eliminar();

        } catch (ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null, "No ha seleccionado alguna fila", "Mensaje de error.", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonEliminarBecaActionPerformed

    private void jTextAreaDescripcionBecaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextAreaDescripcionBecaCaretUpdate
        if(validarDescripcionBeca(jTextAreaDescripcionBeca.getText())){
            descripcionCorrecto = true;
            validarDatosCorrectos();
        } else {
            descripcionCorrecto = false;
            validarDatosCorrectos();
        }
        
        if (idBeca != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_jTextAreaDescripcionBecaCaretUpdate

    private void jTextAreaDescripcionBecaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextAreaDescripcionBecaKeyTyped
        if (jTextAreaDescripcionBeca.getText().length() == 45){
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTextAreaDescripcionBecaKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscarBeca;
    public javax.swing.JButton jButtonEliminarBeca;
    public javax.swing.JButton jButtonModificarBeca;
    public javax.swing.JButton jButtonRegistrarBeca;
    public javax.swing.JButton jButtonRegresar;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextArea jTextAreaDescripcionBeca;
    public javax.swing.JTextField jTextFieldDescuentoBeca;
    public javax.swing.JTextField jTextFieldNombreBeca;
    // End of variables declaration//GEN-END:variables
}
