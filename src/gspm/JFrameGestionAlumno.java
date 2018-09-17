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
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author JuanCarlos
 */
public class JFrameGestionAlumno extends javax.swing.JFrame {

    /**
     * Creates new form JFrameGestionAlumno
     */
    public JFrameGestionAlumno() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Gestion de Alumno.");
        setResizable(false);
        cargarDatosCategoria();
        setResizable(false);
    }
    
    
    public int idPersonaBuscada = 0;
    ArrayList<Integer> arrayIdCategoria = new ArrayList<>();
    ArrayList<String> arrayNombreCategoria = new ArrayList<>();
    
    
    protected Connection conexion;
    protected Statement sentencia;
    protected ResultSet resultados;
    
    
    public void cerrarConexion() {
        try {
            conexion.close();
            sentencia.close();
            resultados.close();
        } catch (Exception e) {
        }
    }
    
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
    
    
    public void cargarDatosCategoria() {
        int tamañoArray = arrayIdCategoria.size();
        for (int i = tamañoArray; i > 0; i--) {
            arrayIdCategoria.remove(i);
            arrayNombreCategoria.remove(i);
            jComboBoxNombreCategoriaBuscar.remove(i);
        }
        
        String consulta = consulta("select idCategoria,Nombre from categoria;");
        String data[] = consulta.split("°°°");
        
        int cols = 2;
        int rows = data.length / (2);
        int aux = 0;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.println("Aux: " + data[aux]);
                if(j == 0){
                    arrayIdCategoria.add(Integer.parseInt(data[aux].replaceAll("\n", "")));
                } else {
                    jComboBoxNombreCategoriaBuscar.addItem(data[aux]);
                    arrayNombreCategoria.add(data[aux]);
                }
                aux++;
            }
        }
        
        cerrarConexion();
    }
    
    
    public boolean cargarBaseDatosAlumnoBuscado(int idCategoriaBuscar, int numeroJugadorBuscar) {
        String consulta = consulta("select idPersona from alumno where idCategoria="+idCategoriaBuscar+" and NumeroJugador="+numeroJugadorBuscar+";");
        String data[] = consulta.split("°°°");
        
        if (data[0] != "") {
            
            idPersonaBuscada = Integer.parseInt(data[0].replaceAll("\n", ""));
            consulta = consulta("select Nombre,Apellido_paterno,Apellido_materno from persona where idPersona="+idPersonaBuscada+";");
            String data2[] = consulta.split("°°°");
            
            
            consulta = consulta("select FechaNacimiento,FechaInscripcion from alumno where idPersona="+idPersonaBuscada+";");
            String data3[] = consulta.split("°°°");
            
            jLabelNombreAlumno.setText(data2[0]);
            jLabelApellidoPaternoAlumno.setText(data2[1]);
            jLabelApellidoMaternoAlumno.setText(data2[2]);
            String fechaNacimiento = data3[0];
            jLabelFechaNacimientoDia.setText(String.valueOf(fechaNacimiento.charAt(8) + "" + fechaNacimiento.charAt(9)));
            jLabelFechaNacimientoMes.setText(String.valueOf(fechaNacimiento.charAt(5) + "" + fechaNacimiento.charAt(6)));
            jLabelFechaNacimientoAño.setText(String.valueOf(fechaNacimiento.charAt(2) + "" + fechaNacimiento.charAt(3)));
            String fechaInscripcion = data3[1].replaceAll("\n", "");
            jLabelFechaInscripcionDia.setText(String.valueOf(fechaInscripcion.charAt(8) + "" + fechaInscripcion.charAt(9)));
            jLabelFechaInscripcionMes.setText(String.valueOf(fechaInscripcion.charAt(5) + "" + fechaInscripcion.charAt(6)));
            jLabelFechaInscripcionAño.setText(String.valueOf(fechaInscripcion.charAt(2) + "" + fechaInscripcion.charAt(3)));
            
            jTextFieldBuscarNumeroJugador.setText("");
            
            return true;
        } else {
            return false;
        }
        
    }
    
    
    
    //Validacion de los campos de texto
    public Matcher patron(String cadena, String expresion){
        Pattern pattern = Pattern.compile(expresion, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cadena);
        
        return matcher;
    }
    
    public boolean validarNumeroCategoria(String cadena){
        String expresion = "([1-9][\\d]{0,7})";
        if(patron(cadena, expresion).matches()){
            return true;
        } else {
            return false;
        }
    }
    
    
    
    
    
    
    //Eventos de botones
    
    public void buscar() {
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            
            int categoria = jComboBoxNombreCategoriaBuscar.getSelectedIndex();
            int numeroJugador = Integer.parseInt(jTextFieldBuscarNumeroJugador.getText());
            
            boolean mensualidadEncontrada = cargarBaseDatosAlumnoBuscado(arrayIdCategoria.get(categoria), numeroJugador);
            
            if (mensualidadEncontrada) {
                jButtonRegistrarCategoria.setEnabled(false);
                jButtonConsultarAlumno.setEnabled(true);
                jButtonDarDeBajaAlumno.setEnabled(true);
            } else {
                jButtonRegistrarCategoria.setEnabled(true);
                idPersonaBuscada = 0;
                jTextFieldBuscarNumeroJugador.setText("");
                jLabelNombreAlumno.setText("");
                jLabelApellidoMaternoAlumno.setText("");
                jLabelApellidoPaternoAlumno.setText("");
                jLabelFechaNacimientoDia.setText("");
                jLabelFechaNacimientoMes.setText("");
                jLabelFechaNacimientoAño.setText("");
                jLabelFechaInscripcionDia.setText("");
                jLabelFechaInscripcionMes.setText("");
                jLabelFechaInscripcionAño.setText("");
                jButtonDarDeBajaAlumno.setEnabled(false);
                jButtonConsultarAlumno.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No se encontro registro del Alumno en el sistema", "Notificacion...", JOptionPane.INFORMATION_MESSAGE);
                
            }
            
        } catch (SQLException e) {
            System.out.println("error en agregar MySQL: " + e.getLocalizedMessage());
        }
    }
    
    
    
    public void darDeBaja(){
        int seleccionada = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea dar de baja\n el registro del Alumno?", "Confirmacion...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch(seleccionada) {
            case JOptionPane.YES_OPTION:
                
                boolean b1 = false;
                try {
                    conexion = Conexion.getConnection();
                    sentencia = conexion.createStatement();
                    b1 = sentencia.execute("DELETE FROM alumno WHERE idPersona = \"" + idPersonaBuscada + "\":");
                    
                    if(!b1){
                        idPersonaBuscada = 0;
                        jTextFieldBuscarNumeroJugador.setText("");
                        jLabelNombreAlumno.setText("");
                        jLabelApellidoMaternoAlumno.setText("");
                        jLabelApellidoPaternoAlumno.setText("");
                        jLabelFechaNacimientoDia.setText("");
                        jLabelFechaNacimientoMes.setText("");
                        jLabelFechaNacimientoAño.setText("");
                        jLabelFechaInscripcionDia.setText("");
                        jLabelFechaInscripcionMes.setText("");
                        jLabelFechaInscripcionAño.setText("");
                        jButtonDarDeBajaAlumno.setEnabled(false);
                        jButtonConsultarAlumno.setEnabled(false);
                        JOptionPane.showMessageDialog(null, "El Alumno fue dado de baja correctamente", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e) {
                    System.out.println("erro en agregar MySQL: " + e.getLocalizedMessage());
                }
            break;
                
            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(null, "Se cancelo la accion dar de baja Alumno", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
            break;
                
            case JOptionPane.CLOSED_OPTION:
                JOptionPane.showMessageDialog(null, "Se cancelo la accion dar de baja Alumno", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
            break;
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButtonRegistrarCategoria = new javax.swing.JButton();
        jButtonConsultarAlumno = new javax.swing.JButton();
        jButtonDarDeBajaAlumno = new javax.swing.JButton();
        jButtonRegresar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jButtonBuscarCategoria = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldBuscarNumeroJugador = new javax.swing.JTextField();
        jComboBoxNombreCategoriaBuscar = new javax.swing.JComboBox<String>();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabelNombreAlumno = new javax.swing.JLabel();
        jLabelApellidoMaternoAlumno = new javax.swing.JLabel();
        jLabelApellidoPaternoAlumno = new javax.swing.JLabel();
        jLabelFechaNacimientoDia = new javax.swing.JLabel();
        jLabelFechaInscripcionDia = new javax.swing.JLabel();
        jLabelFechaNacimientoMes = new javax.swing.JLabel();
        jLabelFechaNacimientoAño = new javax.swing.JLabel();
        jLabelFechaInscripcionMes = new javax.swing.JLabel();
        jLabelFechaInscripcionAño = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 0, 0));

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Escuela de Futbol Club Cruz Azul Guerrero");

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Calle Doctor Grabriel Leyva Alarcon 12");

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText(" Unidad Habitacional, Chilpancingo de los Bravo.");

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("39090");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jButtonRegistrarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add.png"))); // NOI18N
        jButtonRegistrarCategoria.setText("Registrar");
        jButtonRegistrarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarCategoriaActionPerformed(evt);
            }
        });

        jButtonConsultarAlumno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/findReplace.png"))); // NOI18N
        jButtonConsultarAlumno.setText("Consultar");
        jButtonConsultarAlumno.setEnabled(false);
        jButtonConsultarAlumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultarAlumnoActionPerformed(evt);
            }
        });

        jButtonDarDeBajaAlumno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Bin.png"))); // NOI18N
        jButtonDarDeBajaAlumno.setText("Dar de baja");
        jButtonDarDeBajaAlumno.setEnabled(false);
        jButtonDarDeBajaAlumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDarDeBajaAlumnoActionPerformed(evt);
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
                .addComponent(jButtonRegistrarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonConsultarAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonDarDeBajaAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonRegistrarCategoria)
                        .addComponent(jButtonConsultarAlumno))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonDarDeBajaAlumno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar Alumno.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Categoria:");

        jButtonBuscarCategoria.setText("Buscar");
        jButtonBuscarCategoria.setEnabled(false);
        jButtonBuscarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarCategoriaActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Numero del jugador:");

        jTextFieldBuscarNumeroJugador.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldBuscarNumeroJugadorCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBoxNombreCategoriaBuscar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldBuscarNumeroJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonBuscarCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jButtonBuscarCategoria)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldBuscarNumeroJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxNombreCategoriaBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Alumno.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Fecha de nacimiento:");

        jLabel16.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Apellido paterno:");

        jLabel17.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Apellido materno:");

        jLabel18.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Mes:");

        jLabel19.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Año:");

        jLabel20.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Dia:");

        jLabel21.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Nombre(s):");

        jLabel22.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Fecha de inscripcion:");

        jLabel23.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Dia:");

        jLabel24.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Mes:");

        jLabel25.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Año:");

        jLabelNombreAlumno.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabelNombreAlumno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabelApellidoMaternoAlumno.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabelApellidoMaternoAlumno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabelApellidoPaternoAlumno.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabelApellidoPaternoAlumno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabelFechaNacimientoDia.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabelFechaNacimientoDia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabelFechaInscripcionDia.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabelFechaInscripcionDia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabelFechaNacimientoMes.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabelFechaNacimientoMes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabelFechaNacimientoAño.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabelFechaNacimientoAño.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabelFechaInscripcionMes.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabelFechaInscripcionMes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabelFechaInscripcionAño.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabelFechaInscripcionAño.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelFechaNacimientoDia, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelFechaNacimientoMes, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelFechaNacimientoAño, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelNombreAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelApellidoPaternoAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelApellidoMaternoAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelFechaInscripcionDia, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelFechaInscripcionMes, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelFechaInscripcionAño, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(jLabel17))
                    .addComponent(jLabelNombreAlumno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelApellidoPaternoAlumno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelApellidoMaternoAlumno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jLabel18)
                        .addComponent(jLabel19)
                        .addComponent(jLabel20))
                    .addComponent(jLabelFechaNacimientoDia, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFechaNacimientoMes, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFechaNacimientoAño, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22)
                        .addComponent(jLabel24)
                        .addComponent(jLabel25)
                        .addComponent(jLabel23))
                    .addComponent(jLabelFechaInscripcionDia, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFechaInscripcionMes, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFechaInscripcionAño, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void jButtonRegistrarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarCategoriaActionPerformed
        idPersonaBuscada = 0;
        jTextFieldBuscarNumeroJugador.setText("");
        jLabelNombreAlumno.setText("");
        jLabelApellidoMaternoAlumno.setText("");
        jLabelApellidoPaternoAlumno.setText("");
        jLabelFechaNacimientoDia.setText("");
        jLabelFechaNacimientoMes.setText("");
        jLabelFechaNacimientoAño.setText("");
        jLabelFechaInscripcionDia.setText("");
        jLabelFechaInscripcionMes.setText("");
        jLabelFechaInscripcionAño.setText("");
        jButtonDarDeBajaAlumno.setEnabled(false);
        jButtonConsultarAlumno.setEnabled(false);
        JFrameGestionAlumnoRegistrar objRegistrarAlumno = new JFrameGestionAlumnoRegistrar();
        objRegistrarAlumno.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButtonRegistrarCategoriaActionPerformed

    private void jButtonConsultarAlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarAlumnoActionPerformed
        
        jTextFieldBuscarNumeroJugador.setText("");
        jLabelNombreAlumno.setText("");
        jLabelApellidoMaternoAlumno.setText("");
        jLabelApellidoPaternoAlumno.setText("");
        jLabelFechaNacimientoDia.setText("");
        jLabelFechaNacimientoMes.setText("");
        jLabelFechaNacimientoAño.setText("");
        jLabelFechaInscripcionDia.setText("");
        jLabelFechaInscripcionMes.setText("");
        jLabelFechaInscripcionAño.setText("");
        jButtonDarDeBajaAlumno.setEnabled(false);
        jButtonConsultarAlumno.setEnabled(false);
        
        
        JFrameGestionAlumnoConsultar objGestionConsultarAlumno = new JFrameGestionAlumnoConsultar();
        objGestionConsultarAlumno.setVisible(true);
        objGestionConsultarAlumno.colocarId(idPersonaBuscada);
        
        idPersonaBuscada = 0;
        dispose();
    }//GEN-LAST:event_jButtonConsultarAlumnoActionPerformed

    private void jButtonDarDeBajaAlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDarDeBajaAlumnoActionPerformed
        try {
            darDeBaja();

        } catch (ArrayIndexOutOfBoundsException e){

        }
    }//GEN-LAST:event_jButtonDarDeBajaAlumnoActionPerformed

    private void jButtonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegresarActionPerformed
        dispose();
        Principal_secretaria principals = new Principal_secretaria();
        principals.setVisible(true);
    }//GEN-LAST:event_jButtonRegresarActionPerformed

    private void jButtonBuscarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarCategoriaActionPerformed
        try {

            buscar();
            
        } catch (ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null, "Excepcion, no se encontro la categoria buscada", "Mensaje de error.", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonBuscarCategoriaActionPerformed

    private void jTextFieldBuscarNumeroJugadorCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldBuscarNumeroJugadorCaretUpdate
        if (validarNumeroCategoria(jTextFieldBuscarNumeroJugador.getText())) {
            jButtonBuscarCategoria.setEnabled(true);
        } else {
            jButtonBuscarCategoria.setEnabled(false);
        }
    }//GEN-LAST:event_jTextFieldBuscarNumeroJugadorCaretUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscarCategoria;
    public javax.swing.JButton jButtonConsultarAlumno;
    public javax.swing.JButton jButtonDarDeBajaAlumno;
    public javax.swing.JButton jButtonRegistrarCategoria;
    public javax.swing.JButton jButtonRegresar;
    private javax.swing.JComboBox<String> jComboBoxNombreCategoriaBuscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelApellidoMaternoAlumno;
    private javax.swing.JLabel jLabelApellidoPaternoAlumno;
    private javax.swing.JLabel jLabelFechaInscripcionAño;
    private javax.swing.JLabel jLabelFechaInscripcionDia;
    private javax.swing.JLabel jLabelFechaInscripcionMes;
    private javax.swing.JLabel jLabelFechaNacimientoAño;
    private javax.swing.JLabel jLabelFechaNacimientoDia;
    private javax.swing.JLabel jLabelFechaNacimientoMes;
    private javax.swing.JLabel jLabelNombreAlumno;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField jTextFieldBuscarNumeroJugador;
    // End of variables declaration//GEN-END:variables
}
