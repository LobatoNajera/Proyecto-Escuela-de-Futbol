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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author JuanCarlos
 */
public class JFrameGestionMensualidad extends javax.swing.JFrame {

    /**
     * Creates new form JFrameGestionMensualidad
     */
    public JFrameGestionMensualidad() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Gestion de mensualidades.");
        setResizable(false);
    }
    
    
    public int idMensualidad = 0;
    public String nombreMensualidad = "";
    public int numeroMesesMensualidad = 0;
    public float costoMensualidad = 0;
    
    public boolean nombreCorrecto = false;
    public boolean numeroMesesCorrecto = false;
    public boolean costoCorrecto = false;
    
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
    
    
    public boolean cargarBaseDatosBecaBuscada(String idMensualidadBuscada){
        String consulta = consulta("select * from tipo_mensualidad where Nombre='"+idMensualidadBuscada+"';");
        String data[] = consulta.split("°°°");
        
        if (data.length/4 == 1) {
            idMensualidad = Integer.parseInt(data[0]);
            nombreMensualidad = data[1];
            numeroMesesMensualidad = Integer.parseInt(data[2]);
            costoMensualidad = Float.parseFloat(data[3]);
            cerrarConexion();
            return true;
        } else {
            idMensualidad = 0;
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
    
    public boolean validarIdMensualidad(String cadena){
        String expresion = "([1-9][\\d]{0,7})";
        if(patron(cadena, expresion).matches()){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean validarNombreMensualidad(String cadena){
        String expresion = "(([a-zA-ZñÑ]+)|(([1-9]|[1]\\d|[2][0-4])(\\s)([a-zA-ZñÑ]+)))((\\s)(([a-zA-ZñÑ]+)|(\\d+)))*([.]$)?";
        if(patron(cadena, expresion).matches()){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean validarNumeroMesesMensualidad(String cadena){
        String expresion = "([1-9])|([1]\\d)|([2][0-4])";
        if(patron(cadena, expresion).matches()){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean validarCostoMensualidad(String cadena){
        String expresion = "(\\d)(\\d)(\\d)+(([.])(\\d){1,2})?";
        if(patron(cadena, expresion).matches()){
            return true;
        } else {
            return false;
        }
    }
    
    
    public void validarDatosCorrectos() {
        if (numeroMesesCorrecto && costoCorrecto) {
            if (idMensualidad != 0) {
                if (modificacion) {
                    jButtonEliminarMensualidad.setEnabled(false);
                    jButtonModificarMensualidad.setEnabled(true);
                } else {
                    jButtonEliminarMensualidad.setEnabled(true);
                    jButtonModificarMensualidad.setEnabled(false);
                }
            } else {
                jButtonRegistrarMensualidad.setEnabled(true);
            }
        } else {
            jButtonRegistrarMensualidad.setEnabled(false);
            jButtonModificarMensualidad.setEnabled(false);
            jButtonEliminarMensualidad.setEnabled(false);
        }
        
        if (numeroMesesCorrecto) {
            jTextFieldNumeroMesesMensualidad.setEnabled(true);
            jTextFieldCostoMensualidad.setEnabled(true);
        } else {
            jTextFieldCostoMensualidad.setEnabled(false);
        }
        
       /** if (nombreCorrecto) {
            jTextFieldNumeroMesesMensualidad.setEnabled(true);
        } else {
            jTextFieldNumeroMesesMensualidad.setEnabled(false);
            jTextFieldCostoMensualidad.setEnabled(false);
        }**/
    }
    
    
    //Eventos de botones
    
    public void buscar(String idMensualidadBuscada) {
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            boolean mensualidadEncontrada = cargarBaseDatosBecaBuscada(idMensualidadBuscada);
            
            if (mensualidadEncontrada) {
               // jTextFieldNombreMensualidad.setEnabled(true);
               // jTextFieldNombreMensualidad.setText(nombreMensualidad);
                jTextFieldNumeroMesesMensualidad.setText(String.valueOf(numeroMesesMensualidad));
                jTextFieldCostoMensualidad.setText(String.valueOf(costoMensualidad));
                jButtonEliminarMensualidad.setEnabled(true);
            } else {
                idMensualidad = 0;
                jTextFieldNumeroMesesMensualidad.setEnabled(false);
                //jTextFieldNombreMensualidad.setEnabled(false);
               // jTextFieldBuscarBeca.setText("");
                //jTextFieldNombreMensualidad.setText("");
                jTextFieldNumeroMesesMensualidad.setText("");
                jTextFieldCostoMensualidad.setText("");
                jButtonEliminarMensualidad.setEnabled(false);
                int seleccionada = JOptionPane.showConfirmDialog(null, "La mensualidad no se encuentra registrada...\n¿Desea registrarla en el sistema?", "Notificacion...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                switch(seleccionada) {
                    case JOptionPane.YES_OPTION:
                        jTextFieldNumeroMesesMensualidad.setEnabled(true);
                        //jTextFieldNombreMensualidad.setEnabled(true);
                    break;
                    
                    case JOptionPane.NO_OPTION:
                        jTextFieldNombreMensualidad.setText("");
                        //jTextFieldBuscarBeca.setText("");
                    break;
                    
                    case JOptionPane.CANCEL_OPTION:
                        jTextFieldNombreMensualidad.setText("");
                        //jTextFieldBuscarBeca.setText("");
                    break;
                }
            }
            
        } catch (SQLException e) {
            System.out.println("erro en agregar MySQL: " + e.getLocalizedMessage());
        }
    }
    
    
    public boolean registrar(String nombre, int numeroMeses, float costo){
        boolean b = false;
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            b = sentencia.execute("insert into tipo_mensualidad values(0,\""+ nombre +"\","+ numeroMeses +","+ costo +");");
            
            if(b == false){
                idMensualidad = 0;
                jTextFieldNombreMensualidad.setEnabled(true);
                //jTextFieldBuscarBeca.setText("");
                jTextFieldNombreMensualidad.setText("");
                jTextFieldNumeroMesesMensualidad.setText("");
                jTextFieldCostoMensualidad.setText("");
                JOptionPane.showMessageDialog(null, "Se registro la mensualidad de " + nombre + " correctamente", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("erro en agregar MySQL: " + e.getLocalizedMessage());
        }
        return b;
    }
    
    
    public void modificar(String nombre, int numeroMeses, float costo){
        int seleccionada = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea modificar\n el registro?", "Confirmacion...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (seleccionada) {
            case JOptionPane.YES_OPTION:
                boolean b = false;
                try {
                    conexion = Conexion.getConnection();
                    sentencia = conexion.createStatement();
                    System.out.println("el id mensualidad es"+idMensualidad);
                    b = sentencia.execute("UPDATE tipo_mensualidad SET Nombre = '" + nombre + "', Numero_Meses = " + numeroMeses + ", Costo = " + costo + " Where idMensualidad=" + idMensualidad + ";");
                    
                    if (b == false) {
                        idMensualidad = 0;
                        jTextFieldNombreMensualidad.setEnabled(true);
                        //jTextFieldBuscarBeca.setText("");
                        jTextFieldNombreMensualidad.setText("");
                        jTextFieldNumeroMesesMensualidad.setText("");
                        jTextFieldCostoMensualidad.setText("");
                        JOptionPane.showMessageDialog(null, "El regirtro de la mensualidad se modifico correctamente", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e) {
                    System.out.println("erro en agregar MySQL: " + e.getLocalizedMessage());
                    System.out.println("UPDATE tipo_mensualidad SET Nombre = \"" + nombre + "\", Numero_Meses = " + numeroMeses + ", Costo = " + costo + " Where Nombre = '" + idMensualidad + "';");
                }
                cerrarConexion();
                break;

            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(null, "Se cancelo la modificacion de la mensualidad", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                break;

            case JOptionPane.CLOSED_OPTION:
                JOptionPane.showMessageDialog(null, "Se cancelo la modificacion de la mensualidad", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }
    
    public void eliminar(String nombre){
        int seleccionada = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar\n el registro?", "Confirmacion...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch(seleccionada) {
            case JOptionPane.YES_OPTION:
                
                boolean b = false;
                try {
                    conexion = Conexion.getConnection();
                    sentencia = conexion.createStatement();
                    b = sentencia.execute("DELETE FROM tipo_mensualidad where idMensualidad =" + idMensualidad + ";");
                    
                    if(b == false){
                        idMensualidad = 0;
                        jTextFieldNombreMensualidad.setEnabled(true);
                        //jTextFieldBuscarBeca.setText("");
                        jTextFieldNombreMensualidad.setText("");
                        jTextFieldNumeroMesesMensualidad.setText("");
                        jTextFieldCostoMensualidad.setText("");
                        JOptionPane.showMessageDialog(null, "El regirtro de la mensualidad se elimino correctamente", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e) {
                    System.out.println("erro en agregar MySQL: " + e.getLocalizedMessage());
                }
            break;
                
            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(null, "Se cancelo la eliminacion de la mensualidad", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
            break;
                
            case JOptionPane.CLOSED_OPTION:
                JOptionPane.showMessageDialog(null, "Se cancelo la eliminacion de la mensualidad", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
            break;
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButtonBuscarMensualidad = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldNombreMensualidad = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldNumeroMesesMensualidad = new javax.swing.JTextField();
        jTextFieldCostoMensualidad = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jButtonRegistrarMensualidad = new javax.swing.JButton();
        jButtonModificarMensualidad = new javax.swing.JButton();
        jButtonEliminarMensualidad = new javax.swing.JButton();
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

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar mensualidad.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jButtonBuscarMensualidad.setText("Buscar");
        jButtonBuscarMensualidad.setEnabled(false);
        jButtonBuscarMensualidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarMensualidadActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Nombre de la mensualidad:");

        jTextFieldNombreMensualidad.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldNombreMensualidadCaretUpdate(evt);
            }
        });
        jTextFieldNombreMensualidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreMensualidadActionPerformed(evt);
            }
        });
        jTextFieldNombreMensualidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNombreMensualidadKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldNombreMensualidad, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jButtonBuscarMensualidad, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBuscarMensualidad)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldNombreMensualidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de la mensualidad.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Numero de meses:");

        jLabel14.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Costo:");

        jTextFieldNumeroMesesMensualidad.setEnabled(false);
        jTextFieldNumeroMesesMensualidad.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldNumeroMesesMensualidadCaretUpdate(evt);
            }
        });
        jTextFieldNumeroMesesMensualidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNumeroMesesMensualidadKeyTyped(evt);
            }
        });

        jTextFieldCostoMensualidad.setEnabled(false);
        jTextFieldCostoMensualidad.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldCostoMensualidadCaretUpdate(evt);
            }
        });
        jTextFieldCostoMensualidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCostoMensualidadActionPerformed(evt);
            }
        });
        jTextFieldCostoMensualidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCostoMensualidadKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldNumeroMesesMensualidad)
                    .addComponent(jTextFieldCostoMensualidad, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldNumeroMesesMensualidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldCostoMensualidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jButtonRegistrarMensualidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add.png"))); // NOI18N
        jButtonRegistrarMensualidad.setText("Registrar");
        jButtonRegistrarMensualidad.setEnabled(false);
        jButtonRegistrarMensualidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarMensualidadActionPerformed(evt);
            }
        });

        jButtonModificarMensualidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/findReplace.png"))); // NOI18N
        jButtonModificarMensualidad.setText("Actualizar");
        jButtonModificarMensualidad.setEnabled(false);
        jButtonModificarMensualidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarMensualidadActionPerformed(evt);
            }
        });

        jButtonEliminarMensualidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/error.gif"))); // NOI18N
        jButtonEliminarMensualidad.setText("Eliminar");
        jButtonEliminarMensualidad.setEnabled(false);
        jButtonEliminarMensualidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarMensualidadActionPerformed(evt);
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
                .addComponent(jButtonRegistrarMensualidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonModificarMensualidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonEliminarMensualidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addComponent(jButtonRegistrarMensualidad)
                        .addComponent(jButtonModificarMensualidad))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonEliminarMensualidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBuscarMensualidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarMensualidadActionPerformed
        try {
            buscar(jTextFieldNombreMensualidad.getText());
            jButtonModificarMensualidad.setEnabled(false);
        } catch (ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null, "Excepcion, no se encontro la mensualidad buscada", "Mensaje de error.", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonBuscarMensualidadActionPerformed

    private void jTextFieldNombreMensualidadCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldNombreMensualidadCaretUpdate
        if (validarNombreMensualidad(jTextFieldNombreMensualidad.getText())){
           // nombreCorrecto = true;
           // jTextFieldNumeroMesesMensualidad.setEnabled(true);
            jButtonBuscarMensualidad.setEnabled(true);
            validarDatosCorrectos();
        } else {
            jButtonBuscarMensualidad.setEnabled(true);
           // nombreCorrecto = false;
           // jTextFieldNumeroMesesMensualidad.setEnabled(false);
           // jTextFieldCostoMensualidad.setEnabled(false);
            validarDatosCorrectos();
        }

        if (idMensualidad != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_jTextFieldNombreMensualidadCaretUpdate

    private void jTextFieldNombreMensualidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreMensualidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNombreMensualidadActionPerformed

    private void jTextFieldNombreMensualidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNombreMensualidadKeyTyped
        if (jTextFieldNombreMensualidad.getText().length() == 45){
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTextFieldNombreMensualidadKeyTyped

    private void jTextFieldNumeroMesesMensualidadCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldNumeroMesesMensualidadCaretUpdate
        if(validarNumeroMesesMensualidad(jTextFieldNumeroMesesMensualidad.getText())){
            numeroMesesCorrecto = true;
            jTextFieldCostoMensualidad.setEnabled(true);
            validarDatosCorrectos();
        } else {
            numeroMesesCorrecto = false;
            jTextFieldCostoMensualidad.setEnabled(false);
            validarDatosCorrectos();
        }

        if (idMensualidad != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_jTextFieldNumeroMesesMensualidadCaretUpdate

    private void jTextFieldNumeroMesesMensualidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNumeroMesesMensualidadKeyTyped
        if (jTextFieldNumeroMesesMensualidad.getText().length() == 2) {
                evt.consume();
                JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 2 numeros", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_jTextFieldNumeroMesesMensualidadKeyTyped

    private void jTextFieldCostoMensualidadCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldCostoMensualidadCaretUpdate
        if(validarCostoMensualidad(jTextFieldCostoMensualidad.getText())){
            costoCorrecto = true;
            validarDatosCorrectos();
        } else {
            costoCorrecto = false;
            validarDatosCorrectos();
        }

        if (idMensualidad != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_jTextFieldCostoMensualidadCaretUpdate

    private void jTextFieldCostoMensualidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCostoMensualidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCostoMensualidadActionPerformed

    private void jTextFieldCostoMensualidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCostoMensualidadKeyTyped
        if (jTextFieldCostoMensualidad.getText().length() == 8){
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 8 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTextFieldCostoMensualidadKeyTyped

    private void jButtonRegistrarMensualidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarMensualidadActionPerformed
        if(registrar(jTextFieldNombreMensualidad.getText(), Integer.parseInt(jTextFieldNumeroMesesMensualidad.getText()), Float.parseFloat(jTextFieldCostoMensualidad.getText())) == false){
            jTextFieldNombreMensualidad.setText("");
            jTextFieldNumeroMesesMensualidad.setText("");
            jTextFieldCostoMensualidad.setText("");
        }
        jTextFieldNumeroMesesMensualidad.setEnabled(false);
    }//GEN-LAST:event_jButtonRegistrarMensualidadActionPerformed

    private void jButtonModificarMensualidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarMensualidadActionPerformed
        try {
            if ((nombreMensualidad.equals(jTextFieldNombreMensualidad.getText())) && (numeroMesesMensualidad == Integer.parseInt(jTextFieldNumeroMesesMensualidad.getText())) && (costoMensualidad == Float.parseFloat(jTextFieldCostoMensualidad.getText()))) {
                JOptionPane.showMessageDialog(null, "No se ha hecho ninguna modificacion de los\ncampos de la mensualidad numero "+idMensualidad, "Notificacion...", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String nombre = jTextFieldNombreMensualidad.getText();
                int numeroMeses = Integer.parseInt(jTextFieldNumeroMesesMensualidad.getText());
                float costo = Float.parseFloat(jTextFieldCostoMensualidad.getText());
                System.out.println("es"+nombre);
                modificar(nombre, numeroMeses, costo);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            
        }
        jTextFieldNumeroMesesMensualidad.setEnabled(false);
    }//GEN-LAST:event_jButtonModificarMensualidadActionPerformed

    private void jButtonEliminarMensualidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarMensualidadActionPerformed
        try {
            String nombre = jTextFieldNombreMensualidad.getText();
            eliminar(nombre);

        } catch (ArrayIndexOutOfBoundsException e){
            
        }
        jTextFieldNumeroMesesMensualidad.setEnabled(false);
    }//GEN-LAST:event_jButtonEliminarMensualidadActionPerformed

    private void jButtonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegresarActionPerformed
        dispose();
        Principal_Admin padmin = new Principal_Admin();
        padmin.setVisible(true);
    }//GEN-LAST:event_jButtonRegresarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscarMensualidad;
    public javax.swing.JButton jButtonEliminarMensualidad;
    public javax.swing.JButton jButtonModificarMensualidad;
    public javax.swing.JButton jButtonRegistrarMensualidad;
    public javax.swing.JButton jButtonRegresar;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    public javax.swing.JTextField jTextFieldCostoMensualidad;
    public javax.swing.JTextField jTextFieldNombreMensualidad;
    public javax.swing.JTextField jTextFieldNumeroMesesMensualidad;
    // End of variables declaration//GEN-END:variables
}
