package gspm;

import Conexion.Conexion;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sonia
 */
public class CobroColegiatura extends javax.swing.JFrame {

    /**
     * Creates new form CobroColegiatura
     */
    public CobroColegiatura() {
        initComponents();
        setLocationRelativeTo(null);
        inactivo();
        setTitle("Cobro de colegiatura");
        fecha();
        setResizable(false);
    }

    protected Connection conexion;
    protected Statement sentencia;
    protected ResultSet resultados;

    void fecha() {//metodo para obtener la fecha del servidor
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = new GregorianCalendar();
        String dia = Integer.toString(c2.get(Calendar.DATE));
        String mes = Integer.toString(c2.get(Calendar.MONTH));
        String anio = Integer.toString(c2.get(Calendar.YEAR));
        int mesC = Integer.parseInt(mes) + 1;
        txtfecha.setText(dia + " - " + mesC + " - " + anio);
    }

    public void inactivo() { //metodo para desabhilitar los campos
        txtNombreAlumno.setEnabled(false);
        txtNombreAp.setEnabled(false);
        txtApellidoM.setEnabled(false);
        txtMensualidad.setEnabled(false);
        txtBeca.setEnabled(false);
        txtDescuento.setEnabled(false);
        txtCantidad.setEnabled(false);
        BtImprimir.setEnabled(false);
        txtfecha.setEnabled(false);
        txtTipoMensualidad.setEnabled(false);
        BtBuscar.setEnabled(false);
    }

    public String consulta(String consulta) {
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

    public Matcher patron(String cadena, String expresion) {
        Pattern pattern = Pattern.compile(expresion, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cadena);
        return matcher;
    }

    public boolean validarApellidoPaterno(String cadena) {
        String expresion = "([a-zA-ZñÑ.]+)((\\s)([a-zA-ZñÑ.]+))*";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validaColegiatura(String cadena) {
        String expresion = "[a-zA-ZñÑ ]+";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarCantidad(String cadena) {
        String expresion = "[0-9]+";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public void LimpiarCampos() {
        txtBUSCA.setText("");
        txtNombreAp.setText("");
        txtApellidoM.setText("");
        txtMensualidad.setText("");
        txtBeca.setText("");
        txtDescuento.setText("");
        txtCantidad.setText("");
    }

    public void cerrarConexion() {
        try {
            conexion.close();
            sentencia.close();
            resultados.close();
        } catch (Exception e) {
        }
    }

    public boolean registrarCobro() {

        boolean b = false;

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = new GregorianCalendar();
        String dia = Integer.toString(c2.get(Calendar.DATE));
        String mes = Integer.toString(c2.get(Calendar.MONTH));
        String anio = Integer.toString(c2.get(Calendar.YEAR));
        String fecha = anio + "-" + mes + "-" + dia;
        float cobro = Float.parseFloat(txtCantidad.getText());
        String nombre = txtNombreAlumno.getText();
        String Apellido_paterno = txtNombreAp.getText();
        String Apellido_materno = txtApellidoM.getText();
        String beca = txtBeca.getText();
        String mens = txtTipoMensualidad.getText();
        float costoMensualidad = Float.parseFloat(txtMensualidad.getText());
        float descuentoMensualidad = Float.parseFloat(txtDescuento.getText());
        float descuento = costoMensualidad * (descuentoMensualidad / 100);
        float costoTotal = costoMensualidad - descuento;
        //No obtiene nada *************************
        String idPersona = consulta("select idPersona from Persona Where Nombre = \"" + nombre + "\" and Apellido_paterno = \"" + Apellido_paterno + "\" and Apellido_materno = \"" + Apellido_materno + "\";");
        String idBeca = consulta("select idBeca from Alumno Where idPersona =' " + idPersona + "';");
        String idMensualidad = consulta("select idMensualidad from Alumno Where idPersona =' " + idPersona + "';");
        
        String data1[] = idPersona.split("°°°");
        String data2[] = idBeca.split("°°°");
        String data3[] = idMensualidad.split("°°°");
        System.out.println("IdPersona: " + idPersona + "    idBeca: " + idBeca + "    idMensualidad: " + idMensualidad);

        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();

            b = sentencia.execute("insert into CobroColegiatura values(0,'" + fecha + "','" + data2[0] + "','" + data3[0] + "','" + data1[0] + "'," + cobro + ");");

        } catch (SQLException e) {
            System.out.println("erro en agregar MySQL: " + e.getLocalizedMessage());
            System.out.println("insert intoncobrocolegiatura values(0,'" + fecha + "'," + data2[0] + "," + data3[0] + "," + data1[0] + "," + cobro + ");");
        }

        return b;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBUSCA = new javax.swing.JTextField();
        BtBuscar = new javax.swing.JButton();
        BtImprimir = new javax.swing.JButton();
        salir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNombreAlumno = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombreAp = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtApellidoM = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMensualidad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtBeca = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTipoMensualidad = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtfecha = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        txtCantidad = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(null);
        setResizable(false);

        txtBUSCA.setEnabled(false);
        txtBUSCA.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtBUSCACaretUpdate(evt);
            }
        });
        txtBUSCA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBUSCAKeyTyped(evt);
            }
        });

        BtBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Search.png"))); // NOI18N
        BtBuscar.setText("Buscar");
        BtBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BtBuscarMousePressed(evt);
            }
        });
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });

        BtImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/imprimir.png"))); // NOI18N
        BtImprimir.setText("Imprimir nota");
        BtImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BtImprimirMousePressed(evt);
            }
        });
        BtImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtImprimirActionPerformed(evt);
            }
        });

        salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/icono volver.png"))); // NOI18N
        salir.setText("Regresar");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel2.setText("Nombre:");

        txtNombreAlumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreAlumnoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel3.setText("Apellido paterno:");

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel4.setText("Apellido materno:");

        jLabel5.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel5.setText("Costo de la mensualidad:");

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel6.setText("Beca:");

        jLabel7.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel7.setText("Descuento:");

        jLabel9.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel9.setText("Tipo de Mensualidad:");

        txtTipoMensualidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoMensualidadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTipoMensualidad, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(txtNombreAlumno))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMensualidad, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtNombreAp, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtApellidoM, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtBeca)
                        .addGap(233, 233, 233)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(txtNombreAp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtApellidoM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMensualidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtTipoMensualidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtBeca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(30, 30, 30))))
        );

        jPanel3.setBackground(new java.awt.Color(51, 0, 0));

        jLabel10.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Escuela de Futbol Club Cruz Azul Guerrero");

        jLabel11.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Calle Doctor Grabriel Leyva Alarcon 12");

        jLabel12.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText(" Unidad Habitacional, Chilpancingo de los Bravo.");

        jLabel13.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("39090");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13))
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addContainerGap())
        );

        txtfecha.setEditable(false);
        txtfecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfechaActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel15.setText("Fecha");

        jComboBox1.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por:", "Nombre", "Ap. Paterno", "Ap. Materno" }));
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jComboBox1MousePressed(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        txtCantidad.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCantidadCaretUpdate(evt);
            }
        });
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel8.setText("Cantidad a cobrar:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(189, 189, 189)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(txtBUSCA, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(BtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 472, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(32, 32, 32)
                                .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(BtImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(salir, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(32, 32, 32))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBUSCA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(salir, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(BtImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtBuscarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtBuscarMousePressed

    }//GEN-LAST:event_BtBuscarMousePressed

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed

        String datoBusca = (String) jComboBox1.getSelectedItem();
        if (datoBusca == "Ap. Materno") {
            String apellido_materno = txtBUSCA.getText();
            String buscar = consulta("SELECT Persona.Nombre,Persona.Apellido_paterno,Persona.Apellido_materno,tipo_mensualidad.nombre,tipo_mensualidad.costo,Beca.nombre,Beca.descuento from alumno join tipo_mensualidad on Tipo_mensualidad.idMensualidad = alumno.idMensualidad join beca on beca.idBeca = alumno.idBeca join \n"
                    + "persona on persona.idPersona = alumno.idPersona where apellido_materno = '" + apellido_materno + "';");
            if (buscar == "") {
                JOptionPane.showMessageDialog(null, "Dato no encontrado");
                LimpiarCampos();
            } else {
                String data[] = buscar.split("°°°");
                txtNombreAlumno.setText(data[0]);
                txtNombreAp.setText(data[1]);
                txtApellidoM.setText(data[2]);
                txtTipoMensualidad.setText(data[3]);
                txtMensualidad.setText(data[4]);
                txtBeca.setText(data[5]);
                txtDescuento.setText(data[6]);
                BtImprimir.setEnabled(true);
            }
        } else if (datoBusca == "Ap. Paterno") {
            String apellido_materno = txtBUSCA.getText();
            String buscar = consulta("SELECT Persona.Nombre,Persona.Apellido_paterno,Persona.Apellido_materno,tipo_mensualidad.nombre,tipo_mensualidad.costo,Beca.nombre,Beca.descuento from alumno join tipo_mensualidad on Tipo_mensualidad.idMensualidad = alumno.idMensualidad join beca on beca.idBeca = alumno.idBeca join \n"
                    + "persona on persona.idPersona = alumno.idPersona where apellido_paterno = '" + apellido_materno + "';");
            System.out.println("es" + buscar);
            if (buscar == "") {
                JOptionPane.showMessageDialog(null, "Dato no encontrado");
                LimpiarCampos();
            } else {
                String data[] = buscar.split("°°°");
                txtNombreAlumno.setText(data[0]);
                txtNombreAp.setText(data[1]);
                txtApellidoM.setText(data[2]);
                txtTipoMensualidad.setText(data[3]);
                txtMensualidad.setText(data[4]);
                txtBeca.setText(data[5]);
                txtDescuento.setText(data[6]);
                BtImprimir.setEnabled(true);
            }
        } else if (datoBusca == "Nombre") {
            System.out.println("es" + datoBusca);
            String apellido_materno = txtBUSCA.getText();
            String buscar = consulta("SELECT Persona.Nombre,Persona.Apellido_paterno,Persona.Apellido_materno,tipo_mensualidad.nombre,tipo_mensualidad.costo,Beca.nombre,Beca.descuento from alumno join tipo_mensualidad on Tipo_mensualidad.idMensualidad = alumno.idMensualidad join beca on beca.idBeca = alumno.idBeca join \n"
                    + "persona on persona.idPersona = alumno.idPersona where Persona.nombre = '" + apellido_materno + "';");
            System.out.println("es" + buscar);
            if (buscar == "") {
                JOptionPane.showMessageDialog(null, "Dato no encontrado");
                LimpiarCampos();
            } else {
                String data[] = buscar.split("°°°");
                txtNombreAlumno.setText(data[0]);
                txtNombreAp.setText(data[1]);
                txtApellidoM.setText(data[2]);
                txtTipoMensualidad.setText(data[3]);
                txtMensualidad.setText(data[4]);
                txtBeca.setText(data[5]);
                txtDescuento.setText(data[6]);
                BtImprimir.setEnabled(true);
            }
        } else {
            System.out.println("error");
        }
        float costoMensualidad = Float.parseFloat(txtMensualidad.getText());
        float descuentoMensualidad = Float.parseFloat(txtDescuento.getText());
        float descuento = costoMensualidad * (descuentoMensualidad / 100);
        float costoTotal = costoMensualidad - descuento;
        txtCantidad.setText(String.valueOf(costoTotal));

    }//GEN-LAST:event_BtBuscarActionPerformed

    private void txtBUSCAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBUSCAKeyTyped
        char c = evt.getKeyChar();

        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < '!' || c > '.')
                && (c != (char) KeyEvent.VK_SPACE)) {
            evt.consume();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBUSCAKeyTyped

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed

    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtBUSCACaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtBUSCACaretUpdate
        if (validaColegiatura(txtBUSCA.getText())) {
            BtBuscar.setEnabled(true);
        } else {
            BtBuscar.setEnabled(false);

        }
    }//GEN-LAST:event_txtBUSCACaretUpdate

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {

            evt.consume();
            JOptionPane.showMessageDialog(null, "No puede ingresar Letras!!!", "Error Datos", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtCantidadCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCantidadCaretUpdate

    }//GEN-LAST:event_txtCantidadCaretUpdate

    private void txtNombreAlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreAlumnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreAlumnoActionPerformed

    private void BtImprimirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtImprimirMousePressed

    }//GEN-LAST:event_BtImprimirMousePressed

    private void txtfechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfechaActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void txtTipoMensualidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoMensualidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoMensualidadActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        this.dispose();
        Principal_secretaria principals = new Principal_secretaria();
        principals.setVisible(true);
    }//GEN-LAST:event_salirActionPerformed

    private void BtImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtImprimirActionPerformed

        if (!registrarCobro()) {
            JOptionPane.showMessageDialog(null, "El cobro de la colegiatura se registro correctamente");
            Impresora impresora = new Impresora();
            LimpiarCampos();
        } else {
            JOptionPane.showMessageDialog(null, "El cobro de la colegiatura no se registro correctamente");
        }

    }//GEN-LAST:event_BtImprimirActionPerformed

    private void jComboBox1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MousePressed
        txtBUSCA.setEnabled(true);
    }//GEN-LAST:event_jComboBox1MousePressed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        txtBUSCA.setEnabled(true);
    }//GEN-LAST:event_jComboBox1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtImprimir;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton salir;
    private javax.swing.JTextField txtApellidoM;
    private javax.swing.JTextField txtBUSCA;
    private javax.swing.JTextField txtBeca;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtMensualidad;
    private javax.swing.JTextField txtNombreAlumno;
    public javax.swing.JTextField txtNombreAp;
    private javax.swing.JTextField txtTipoMensualidad;
    private javax.swing.JTextField txtfecha;
    // End of variables declaration//GEN-END:variables
}
