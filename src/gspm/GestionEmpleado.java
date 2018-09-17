package gspm;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.time.Clock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ErikaYalitza
 */
public class GestionEmpleado extends javax.swing.JFrame {

    public GestionEmpleado() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Gestion de Empleados");
        setResizable(false);
    }

    public int idEmpleado = 0;
    public String curp = "";
    
    public boolean curpE = false;

    public boolean nombreE = false;
    public boolean apellidoPaternoE = false;
    public boolean apellidoMaternoE = false;
    public boolean calleE = false;
    public boolean numeroE = false;
    public boolean coloniaE = false;
    public boolean diasE = false;
    public boolean salarioE = false;
    public boolean descE = false;

    public boolean modificacion = false;

    protected Connection conexion;
    protected Statement sentencia;
    protected ResultSet resultados;

    public void validarCampos() {
        if (nombreE && apellidoPaternoE && apellidoMaternoE && calleE && numeroE && coloniaE && diasE && salarioE && descE) {
            if (idEmpleado != 0) {
                System.out.println("entra a id >0");
                if (modificacion) {
                    System.out.println("Entro aqui todo Modificacion verdadero");
                    btnBorrar.setEnabled(false);
                    btnModificar.setEnabled(true);
                } else {
                    btnBorrar.setEnabled(true);
                    btnModificar.setEnabled(false);
                }
            } else {
                btnGestionarEmpleado.setEnabled(true);
            }
        } else {
            // System.out.println("Entro aqui todo los campos dan falso");
            btnGestionarEmpleado.setEnabled(false);
            btnBorrar.setEnabled(false);
            btnModificar.setEnabled(false);
        }

        if (nombreE && apellidoPaternoE && apellidoMaternoE && calleE && numeroE && coloniaE && diasE && salarioE && descE) {
            txtapPaternoE.setEnabled(true);
            txtapMaternoE.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtDiasT.setEnabled(true);
            txtSalario.setEnabled(true);
            txtAreaDesc.setEnabled(true);
            txtNombreE.setEnabled(true);
        } else if (nombreE && apellidoPaternoE && apellidoMaternoE && calleE && numeroE && coloniaE && diasE && salarioE) {
            txtapPaternoE.setEnabled(true);
            txtapMaternoE.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtDiasT.setEnabled(true);
            txtSalario.setEnabled(true);
            txtNombreE.setEnabled(true);
            txtAreaDesc.setEnabled(true);
        } else if (nombreE && apellidoPaternoE && apellidoMaternoE && calleE && numeroE && coloniaE && diasE) {
            txtapPaternoE.setEnabled(true);
            txtapMaternoE.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtDiasT.setEnabled(true);
            txtSalario.setEnabled(true);
            txtNombreE.setEnabled(true);
            txtAreaDesc.setEnabled(false);
        } else if (nombreE && apellidoPaternoE && apellidoMaternoE && calleE && numeroE && coloniaE) {
            txtapPaternoE.setEnabled(true);
            txtapMaternoE.setEnabled(true);
            txtNombreE.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtDiasT.setEnabled(true);
            txtColonia.setEnabled(true);
            txtSalario.setEnabled(false);
            txtAreaDesc.setEnabled(false);
        } else if (nombreE && apellidoPaternoE && apellidoMaternoE && calleE && numeroE) {
            txtapPaternoE.setEnabled(true);
            txtNombreE.setEnabled(true);
            txtapMaternoE.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtDiasT.setEnabled(false);
            txtSalario.setEnabled(false);
            txtAreaDesc.setEnabled(false);
        } else if (nombreE && apellidoPaternoE && apellidoMaternoE && calleE) {
            txtapPaternoE.setEnabled(true);
            txtNombreE.setEnabled(true);
            txtapMaternoE.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(false);
            txtDiasT.setEnabled(false);
            txtSalario.setEnabled(false);
            txtAreaDesc.setEnabled(false);
        } else if (nombreE && apellidoPaternoE && apellidoMaternoE) {
            txtNombreE.setEnabled(true);
            txtapPaternoE.setEnabled(true);
            txtapMaternoE.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtDiasT.setEnabled(false);
            txtSalario.setEnabled(false);
            txtAreaDesc.setEnabled(false);
        } else if (nombreE && apellidoPaternoE) {
            txtNombreE.setEnabled(true);
            txtapPaternoE.setEnabled(true);
            txtapMaternoE.setEnabled(true);
            txtCalle.setEnabled(false);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtDiasT.setEnabled(false);
            txtSalario.setEnabled(false);
            txtAreaDesc.setEnabled(false);
        } else if (nombreE) {
            txtNombreE.setEnabled(true);
            txtapPaternoE.setEnabled(true);
            txtapMaternoE.setEnabled(false);
            txtCalle.setEnabled(false);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtDiasT.setEnabled(false);
            txtSalario.setEnabled(false);
            txtAreaDesc.setEnabled(false);
        /**} else if (curpE) {
            txtNombreE.setEnabled(true);**/
        } else {
            txtapPaternoE.setEnabled(false);
            txtapMaternoE.setEnabled(false);
            txtCalle.setEnabled(false);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtDiasT.setEnabled(false);
            txtSalario.setEnabled(false);
            txtAreaDesc.setEnabled(false);
        }

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

    public boolean validarNombres(String cadena) {
        String expresion = "[a-zA-ZñÑ ]+";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarnombreNum(String cadena) {
        String expresion = "[a-zA-ZñÑ0-9 ]+";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarnum(String cadena) {
        String expresion = "[1-7]";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean peso(String cadena) {
        String expresion = "[0-9]+.?[0-9]+";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarnumD(String cadena) {
        String expresion = "[0-9]+";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarSalario(String cadena) {
        String expresion = "([1-9]\\d{3}(.\\d{1,2})?)|([1-9]\\d{2}(.\\d{1,2})?)";
        //"[[[1-9][0-9][0-9][0-9](.[0-9][0-9])?]|[[1-9][0-9][0-9](.[0-9][0-9])?]]";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarCurp(String cadena) {
        String expresion = "(([A-ZÑ][AEIOU][A-ZÑ]{2})(\\d\\d(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01]))([HM])([A-ZÑ]{2})([BCDFGHJKLMNÑPQRSTVWXYZ]{3})(\\w\\d))";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCurp = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombreE = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtapPaternoE = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtapMaternoE = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtCalle = new javax.swing.JTextField();
        txtColonia = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaDesc = new javax.swing.JTextArea();
        txtDiasT = new javax.swing.JTextField();
        txtSalario = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtPuesto = new javax.swing.JComboBox();
        txtMes = new javax.swing.JComboBox();
        txtDia = new javax.swing.JComboBox();
        txtAnio = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        btnRegresar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnGestionarEmpleado = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestion Empleado");
        setMinimumSize(null);

        jPanel5.setBackground(new java.awt.Color(51, 0, 0));

        jLabel12.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Escuela de Futbol Club Cruz Azul Guerrero");

        jLabel15.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Calle Doctor Grabriel Leyva Alarcon 12");

        jLabel17.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Unidad Habitacional, Chilpancingo de los Bravo.");

        jLabel18.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("39090");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel12))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addGap(41, 41, 41))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel6.setText("Curp");

        txtCurp.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCurpCaretUpdate(evt);
            }
        });
        txtCurp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurpKeyTyped(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.setEnabled(false);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 18))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel1.setText("Nombre(s)");

        txtNombreE.setEnabled(false);
        txtNombreE.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNombreECaretUpdate(evt);
            }
        });
        txtNombreE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtNombreEMousePressed(evt);
            }
        });
        txtNombreE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreEKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel2.setText("Apellido Paterno");

        txtapPaternoE.setEnabled(false);
        txtapPaternoE.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtapPaternoECaretUpdate(evt);
            }
        });
        txtapPaternoE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtapPaternoEMousePressed(evt);
            }
        });
        txtapPaternoE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtapPaternoEKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel3.setText("Apellido Materno");

        txtapMaternoE.setEnabled(false);
        txtapMaternoE.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtapMaternoECaretUpdate(evt);
            }
        });
        txtapMaternoE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtapMaternoEMousePressed(evt);
            }
        });
        txtapMaternoE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtapMaternoEKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addComponent(txtNombreE, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtapPaternoE, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(txtapMaternoE, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombreE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtapPaternoE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtapMaternoE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(314, 314, 314))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dirreccion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 18))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel10.setText("Numero");

        txtNumero.setEnabled(false);
        txtNumero.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNumeroCaretUpdate(evt);
            }
        });
        txtNumero.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtNumeroMousePressed(evt);
            }
        });
        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel11.setText("Colonia");

        jLabel13.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel13.setText("Calle");

        txtCalle.setEnabled(false);
        txtCalle.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCalleCaretUpdate(evt);
            }
        });
        txtCalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCalleMousePressed(evt);
            }
        });
        txtCalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCalleKeyTyped(evt);
            }
        });

        txtColonia.setEnabled(false);
        txtColonia.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtColoniaCaretUpdate(evt);
            }
        });
        txtColonia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtColoniaMousePressed(evt);
            }
        });
        txtColonia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtColoniaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel13)
                .addGap(28, 28, 28)
                .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(txtColonia, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtColonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Contratacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 18))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel4.setText("Puesto");

        jLabel5.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel5.setText("Fecha de Contratacion");

        jLabel7.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel7.setText("Salario");

        jLabel8.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel8.setText("Dias de Trabajo");

        jLabel9.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel9.setText("Descripcción");

        txtAreaDesc.setColumns(20);
        txtAreaDesc.setRows(5);
        txtAreaDesc.setEnabled(false);
        txtAreaDesc.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtAreaDescCaretUpdate(evt);
            }
        });
        txtAreaDesc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtAreaDescMousePressed(evt);
            }
        });
        txtAreaDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAreaDescKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtAreaDesc);

        txtDiasT.setEnabled(false);
        txtDiasT.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtDiasTCaretUpdate(evt);
            }
        });
        txtDiasT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtDiasTMousePressed(evt);
            }
        });
        txtDiasT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiasTKeyTyped(evt);
            }
        });

        txtSalario.setEnabled(false);
        txtSalario.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtSalarioCaretUpdate(evt);
            }
        });
        txtSalario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtSalarioMousePressed(evt);
            }
        });
        txtSalario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSalarioKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("-");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("-");

        txtPuesto.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        txtPuesto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Profesor(a)", "Secretario(a)", " " }));
        txtPuesto.setEnabled(false);
        txtPuesto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPuestoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtPuestoMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtPuestoMousePressed(evt);
            }
        });
        txtPuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPuestoKeyPressed(evt);
            }
        });

        txtMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        txtMes.setEnabled(false);
        txtMes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtMesMousePressed(evt);
            }
        });

        txtDia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Día", "01\t", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        txtDia.setEnabled(false);
        txtDia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtDiaMousePressed(evt);
            }
        });

        txtAnio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Año", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035" }));
        txtAnio.setEnabled(false);
        txtAnio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAnioMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtAnioMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtAnioMousePressed(evt);
            }
        });
        txtAnio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAnioKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9))
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtDiasT, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120)
                        .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(264, 264, 264))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDiasT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jLabel6)
                        .addGap(51, 51, 51)
                        .addComponent(txtCurp, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnBuscar)
                        .addGap(0, 145, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCurp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel6);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "M E N U", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 11))); // NOI18N

        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/icono volver.png"))); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Bin.png"))); // NOI18N
        btnBorrar.setText("Dar de Baja a un Empleado");
        btnBorrar.setEnabled(false);
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/findReplace.png"))); // NOI18N
        btnModificar.setText("Modificar Empleado");
        btnModificar.setEnabled(false);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnGestionarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add.png"))); // NOI18N
        btnGestionarEmpleado.setText("Registrar Empleado");
        btnGestionarEmpleado.setEnabled(false);
        btnGestionarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionarEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGestionarEmpleado)
                .addGap(18, 18, 18)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGestionarEmpleado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGestionarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionarEmpleadoActionPerformed
        String curp = txtCurp.getText();
        String nombre = txtNombreE.getText();
        String apP = txtapPaternoE.getText();
        String apM = txtapMaternoE.getText();
        String colonia = txtColonia.getText();
        String calle = txtCalle.getText();
        int num = Integer.parseInt(txtNumero.getText());
        int diasT = Integer.parseInt(txtDiasT.getText());
        float salario = Float.parseFloat(txtSalario.getText());
        String mes = (String) txtMes.getSelectedItem();
        String anio = (String) txtAnio.getSelectedItem();
        String dia = (String) txtDia.getSelectedItem();
        String fechaC = anio + "-" + mes + "-" + dia;
        int tipo = 0;
        String categoria1 = (String) txtPuesto.getSelectedItem();
        if (categoria1.equals("Profesor(a)")) {
            tipo = 1;
        } else if (categoria1.equals("Secretario(a)")) {
            tipo = 2;
        }
        int tipo_em = tipo;
        String desc = txtAreaDesc.getText();

        boolean b = false;
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            b = sentencia.execute("insert into Empleado values(0,'" + curp + "','" + nombre + "','" + apP + "','" + apM + "','" + colonia + "','" + calle + "'," + num + "," + diasT + "," + salario + ",'" + fechaC + "'," + tipo_em + ",'" + desc + "');");
            if (b == false) {
                JOptionPane.showMessageDialog(null, "Registro Exitoso");
            }
        } catch (SQLException e) {
            System.out.println("error en agregar MySQL: " + e.getLocalizedMessage());
            System.out.println("insert into Empleado values(0,'" + curp + "','" + nombre + "','" + apP + "','" + apM + "','" + colonia + "','" + calle + "'," + num + "," + diasT + "," + salario + ",'" + fechaC + "'," + tipo_em + ",'" + desc + "');\ncommit;");
        }
        cerrarConexion();

        txtCurp.setText("");
        txtNombreE.setText("");
        txtapPaternoE.setText("");
        txtapMaternoE.setText("");
        txtColonia.setText("");
        txtCalle.setText("");
        txtNumero.setText("");
        txtDiasT.setText("");
        txtSalario.setText("");
        txtAreaDesc.setText("");
        btnModificar.setEnabled(false);
        btnBuscar.setEnabled(false);
        txtCurp.setEnabled(true);

        txtNombreE.setEnabled(false);
        txtapPaternoE.setEnabled(false);
        txtapMaternoE.setEnabled(false);
        txtCalle.setEnabled(false);
        txtNumero.setEnabled(false);
        txtColonia.setEnabled(false);
        txtDiasT.setEnabled(false);
        txtSalario.setEnabled(false);
        txtAreaDesc.setEnabled(false);
        txtDia.setEnabled(false);
        txtAnio.setEnabled(false);
        txtMes.setEnabled(false);
        txtPuesto.setEnabled(false);
        idEmpleado = 0;
    }//GEN-LAST:event_btnGestionarEmpleadoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed

        String curp = txtCurp.getText();
        String nombre = txtNombreE.getText();
        String apP = txtapPaternoE.getText();
        String apM = txtapMaternoE.getText();
        String colonia = txtColonia.getText();
        String calle = txtCalle.getText();
        int num = Integer.parseInt(txtNumero.getText());
        int diasT = Integer.parseInt(txtDiasT.getText());
        float salario = Float.parseFloat(txtSalario.getText());
        String mes = (String) txtMes.getSelectedItem();
        String anio = (String) txtAnio.getSelectedItem();
        String dia = (String) txtDia.getSelectedItem();
        String fechaC = anio + "-" + mes + "-" + dia;
        int tipo = 0;
        String categoria1 = (String) txtPuesto.getSelectedItem();
        if (categoria1.equals("Profesor(a)")) {
            tipo = 1;
        } else if (categoria1.equals("Secretario(a)")) {
            tipo = 2;
        }
        int tipo_em = tipo;
        String desc = txtAreaDesc.getText();

        boolean b = false;
        try {

            String id = consulta("SELECT idEmpleado from empleado where Curp = '" + curp + "'");
            String so[] = id.split("°°°");
            System.out.println("es" + id);
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            b = sentencia.execute("UPDATE Empleado SET Curp ='" + curp + "', Nombre = '" + nombre + "',Apellido_paterno='" + apP + "',Apellido_materno='" + apM + "',Colonia = '" + colonia + "',Calle = '" + calle + "',Numero =" + num + ",Dias_de_trabajo=" + diasT + ",Salario = " + salario + ",Fecha_contratacion = '" + fechaC + "',idTipoEmpleado=" + tipo_em + ",Descripcion = '" + desc + "' where idEmpleado = " + so[0] + ";");
            if (b == true) {
                JOptionPane.showInputDialog(null, "Empleado modificado correctamente");
            }
        } catch (SQLException e) {
            System.out.println("error en moficar MySQL: " + e.getLocalizedMessage());
        }
        cerrarConexion();

        txtCurp.setText("");
        txtNombreE.setText("");
        txtapPaternoE.setText("");
        txtapMaternoE.setText("");
        txtColonia.setText("");
        txtCalle.setText("");
        txtNumero.setText("");
        txtDiasT.setText("");
        txtSalario.setText("");
        txtAreaDesc.setText("");
        txtAreaDesc.setEnabled(false);
        txtPuesto.setEnabled(false);
        btnModificar.setEnabled(false);
        txtCurp.setEnabled(true);

        txtNombreE.setEnabled(false);
        txtapPaternoE.setEnabled(false);
        txtapMaternoE.setEnabled(false);
        txtCalle.setEnabled(false);
        txtNumero.setEnabled(false);
        txtColonia.setEnabled(false);
        txtDiasT.setEnabled(false);
        txtSalario.setEnabled(false);
        txtAreaDesc.setEnabled(false);
        txtDia.setEnabled(false);
        txtAnio.setEnabled(false);
        txtMes.setEnabled(false);
        txtPuesto.setEnabled(false);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.dispose();
        Principal_Admin padmin = new Principal_Admin();
        padmin.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed

        String curp = txtCurp.getText();
        boolean b = false;
        String id = consulta("SELECT idEmpleado from empleado where Curp = '" + curp + "'");
        String so[] = id.split("°°°");
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            b = sentencia.execute("DELETE FROM Empleado where idEmpleado = " + so[0] + ";");
            if (b == false) {
                JOptionPane.showMessageDialog(null, "Operacion Exitosa");
            }
        } catch (SQLException e) {
            System.out.println("erro en eliminar MySQL: " + e.getLocalizedMessage());
        }
        cerrarConexion();
        txtCurp.setText("");
        txtNombreE.setText("");
        txtapPaternoE.setText("");
        txtapMaternoE.setText("");
        txtColonia.setText("");
        txtCalle.setText("");
        txtNumero.setText("");
        txtDiasT.setText("");
        txtSalario.setText("");
        txtAreaDesc.setText("");
        txtAreaDesc.setEnabled(false);
        txtPuesto.setEnabled(false);
        btnBorrar.setEnabled(false);
        txtCurp.setEnabled(true);

        txtNombreE.setEnabled(false);
        txtapPaternoE.setEnabled(false);
        txtapMaternoE.setEnabled(false);
        txtCalle.setEnabled(false);
        txtNumero.setEnabled(false);
        txtColonia.setEnabled(false);
        txtDiasT.setEnabled(false);
        txtSalario.setEnabled(false);
        txtAreaDesc.setEnabled(false);
        txtDia.setEnabled(false);
        txtAnio.setEnabled(false);
        txtMes.setEnabled(false);
        txtPuesto.setEnabled(false);
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void txtCurpCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCurpCaretUpdate
        if (validarCurp(txtCurp.getText())) {
            btnBuscar.setEnabled(true);
            validarCampos();
            //txtNombreE.setEnabled(true);
        } else {
            btnBuscar.setEnabled(false);
            //txtNombreE.setEnabled(false);
            validarCampos();
        }
        
        System.out.println("EMPLEADO"+idEmpleado);
        if (idEmpleado != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_txtCurpCaretUpdate

    private void txtNombreECaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNombreECaretUpdate
        if (validarNombres(txtNombreE.getText())) {
            //txtapPaternoE.setEnabled(true);
            nombreE = true;
            validarCampos();
        } else {
            nombreE = false;
            validarCampos();
            //txtapPaternoE.setEnabled(false);
        }
        
        System.out.println("EMPLEADO"+idEmpleado);
        if (idEmpleado != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_txtNombreECaretUpdate

    private void txtapMaternoECaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtapMaternoECaretUpdate
        if (validarNombres(txtapMaternoE.getText())) {
            //txtCalle.setEnabled(true);
            apellidoMaternoE = true;
            validarCampos();
        } else {
            apellidoMaternoE = false;
            validarCampos();
            //txtCalle.setEnabled(false);
        }
        
        System.out.println("EMPLEADO"+idEmpleado);
        if (idEmpleado != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_txtapMaternoECaretUpdate

    private void txtapPaternoECaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtapPaternoECaretUpdate
        if (validarNombres(txtapPaternoE.getText())) {
            //txtapMaternoE.setEnabled(true);
            apellidoPaternoE = true;
            validarCampos();
        } else {
            apellidoPaternoE = false;
            validarCampos();
            //txtapMaternoE.setEnabled(false);
        }
        
        System.out.println("EMPLEADO"+idEmpleado);
        if (idEmpleado != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_txtapPaternoECaretUpdate

    private void txtCalleCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCalleCaretUpdate
        if (validarnombreNum(txtCalle.getText())) {
            //txtNumero.setEnabled(true);
            calleE = true;
            validarCampos();
        } else {
            calleE = false;
            validarCampos();
            //txtNumero.setEnabled(false);
        }

        if (idEmpleado != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_txtCalleCaretUpdate

    private void txtNumeroCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNumeroCaretUpdate
        if (validarnumD(txtNumero.getText())) {
            //txtColonia.setEnabled(true);
            numeroE = true;
            validarCampos();
        } else {
            //txtColonia.setEnabled(false);
            numeroE = false;
            validarCampos();
        }
        
        System.out.println("EMPLEADO"+idEmpleado);
        if (idEmpleado != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_txtNumeroCaretUpdate

    private void txtColoniaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtColoniaCaretUpdate
        if (validarnombreNum(txtColonia.getText())) {
            txtDia.setEnabled(true);
            txtMes.setEnabled(true);
            txtAnio.setEnabled(true);
            coloniaE = true;
            validarCampos();
        } else {
            coloniaE = false;
            validarCampos();
            txtDia.setEnabled(false);
            txtMes.setEnabled(false);
            txtAnio.setEnabled(false);
        }

        System.out.println("EMPLEADO"+idEmpleado);
        if (idEmpleado != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_txtColoniaCaretUpdate

    private void txtAnioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAnioMouseClicked
        //txtPuesto.setEditable(true);
        //txtPuesto.setEnabled(true);
        txtDiasT.setEnabled(true);
        validarCampos();
    }//GEN-LAST:event_txtAnioMouseClicked

    private void txtPuestoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPuestoMouseClicked
        txtAreaDesc.setEnabled(true);
    }//GEN-LAST:event_txtPuestoMouseClicked

    private void txtDiasTCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtDiasTCaretUpdate
        if (validarnum(txtDiasT.getText())) {
            //txtSalario.setEnabled(true);
            diasE = true;
            validarCampos();
        } else {
            diasE = false;
            validarCampos();
            //txtSalario.setEnabled(false);
        }
        
        System.out.println("EMPLEADO"+idEmpleado);
        if (idEmpleado != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_txtDiasTCaretUpdate

    private void txtSalarioCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtSalarioCaretUpdate
        if (validarSalario(txtSalario.getText())) {
            txtPuesto.setEnabled(true);
            //btnGestionarEmpleado.setEnabled(true);
            salarioE = true;
            validarCampos();
        } else {
            salarioE = false;
            validarCampos();
            txtPuesto.setEnabled(false);
            // btnGestionarEmpleado.setEnabled(false);
        }
        
        System.out.println("EMPLEADO"+idEmpleado);
        if (idEmpleado != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_txtSalarioCaretUpdate

    private void txtCurpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurpKeyTyped
        if (txtCurp.getText().length() == 18) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 18 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtCurpKeyTyped

    private void txtNombreEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreEKeyTyped
        if (txtNombreE.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtNombreEKeyTyped

    private void txtapPaternoEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtapPaternoEKeyTyped
        if (txtNombreE.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtapPaternoEKeyTyped

    private void txtapMaternoEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtapMaternoEKeyTyped
        if (txtapMaternoE.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtapMaternoEKeyTyped

    private void txtCalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalleKeyTyped
        if (txtCalle.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtCalleKeyTyped

    private void txtNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyTyped
        if (txtNumero.getText().length() == 11) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 11 digitos", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtNumeroKeyTyped

    private void txtColoniaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColoniaKeyTyped
        if (txtColonia.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtColoniaKeyTyped

    private void txtDiasTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiasTKeyTyped
        if (txtDiasT.getText().length() == 1) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 1 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtDiasTKeyTyped

    private void txtSalarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalarioKeyTyped
        if (txtSalario.getText().length() == 7) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 7 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtSalarioKeyTyped

    private void txtAnioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAnioMouseEntered
        txtDiasT.setEnabled(true);
    }//GEN-LAST:event_txtAnioMouseEntered

    private void txtAnioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnioKeyPressed
//        txtPuesto.setEnabled(true);
//        txtDiasT.setEnabled(true);
    }//GEN-LAST:event_txtAnioKeyPressed

    private void txtPuestoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPuestoKeyPressed
        txtAreaDesc.setEnabled(true);
    }//GEN-LAST:event_txtPuestoKeyPressed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String curp = txtCurp.getText();//variable que almacena el texto a buscar
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            String respuesta = consulta("SELECT * from Empleado WHERE Curp = '" + curp + "'");
            String data[] = respuesta.split("°°°");
            System.out.println("respuesta es" + respuesta);
            if (respuesta != "") {
                idEmpleado = Integer.parseInt(data[0]);
                String fecha = data[10];
                String[] fechaArray = fecha.split("-");
                txtNombreE.setText(data[2]);
                txtapPaternoE.setText(data[3]);
                txtapMaternoE.setText(data[4]);
                txtColonia.setText(data[5]);
                txtCalle.setText(data[6]);
                txtNumero.setText(data[7]);
                txtDiasT.setText(data[8]);
                txtSalario.setText(data[9]);
                txtAreaDesc.setText(data[12]);
                txtDia.setSelectedItem(fechaArray[2]);
                txtMes.setSelectedItem(fechaArray[1]);
                txtAnio.setSelectedItem(fechaArray[0]);
                //txtNombreE.setEnabled(true);
               // txtPuesto.setEnabled(true);
               // txtAreaDesc.setEnabled(true);
                btnBorrar.setEnabled(true);
                txtCurp.setEnabled(true);
                btnBuscar.setEnabled(true);
                btnModificar.setEnabled(false);
            } else {
                int confirmado = JOptionPane.showConfirmDialog(null, "Empleado "
                        + "no registrado, desea registrar ¿ahora?");

                if (JOptionPane.OK_OPTION == confirmado) {
                    System.out.println("confirmado");
                    idEmpleado = 0;
                    txtNombreE.setEnabled(true);
                } else {
                    System.out.println("vale... no borro nada...");
                    txtCurp.setText("");
                }
            }
        } catch (SQLException e) {
            System.out.println("error en Buscar MySQL: " + e.getLocalizedMessage());
        }
        cerrarConexion();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtNombreEMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreEMousePressed
//        btnBorrar.setEnabled(false);
//        btnModificar.setEnabled(true);
    }//GEN-LAST:event_txtNombreEMousePressed

    private void txtapPaternoEMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtapPaternoEMousePressed
//        btnBorrar.setEnabled(false);
//        btnModificar.setEnabled(true);
    }//GEN-LAST:event_txtapPaternoEMousePressed

    private void txtapMaternoEMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtapMaternoEMousePressed
//        btnBorrar.setEnabled(false);
//        btnModificar.setEnabled(true);
    }//GEN-LAST:event_txtapMaternoEMousePressed

    private void txtCalleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCalleMousePressed
//        btnBorrar.setEnabled(false);
//        btnModificar.setEnabled(true);
    }//GEN-LAST:event_txtCalleMousePressed

    private void txtNumeroMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumeroMousePressed
//        btnBorrar.setEnabled(false);
//        btnModificar.setEnabled(true);
    }//GEN-LAST:event_txtNumeroMousePressed

    private void txtColoniaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtColoniaMousePressed
//        btnBorrar.setEnabled(false);
//        btnModificar.setEnabled(true);
    }//GEN-LAST:event_txtColoniaMousePressed

    private void txtDiasTMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDiasTMousePressed
//        btnBorrar.setEnabled(false);
//        btnModificar.setEnabled(true);
    }//GEN-LAST:event_txtDiasTMousePressed

    private void txtSalarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSalarioMousePressed
//        btnBorrar.setEnabled(false);
//        btnModificar.setEnabled(true);
    }//GEN-LAST:event_txtSalarioMousePressed

    private void txtPuestoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPuestoMousePressed
//        btnBorrar.setEnabled(false);
//        btnModificar.setEnabled(true);
    }//GEN-LAST:event_txtPuestoMousePressed

    private void txtDiaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDiaMousePressed
//        btnBorrar.setEnabled(false);
//        btnModificar.setEnabled(true);
    }//GEN-LAST:event_txtDiaMousePressed

    private void txtMesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMesMousePressed
//        btnBorrar.setEnabled(false);
//        btnModificar.setEnabled(true);
    }//GEN-LAST:event_txtMesMousePressed

    private void txtAnioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAnioMousePressed
        btnBorrar.setEnabled(true);
        btnModificar.setEnabled(true);
        //txtDiasT.setEnabled(true);
    }//GEN-LAST:event_txtAnioMousePressed

    private void txtAreaDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaDescKeyTyped
        if (txtAreaDesc.getText().length() == 150) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 150 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtAreaDescKeyTyped

    private void txtAreaDescMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAreaDescMousePressed
//        btnBorrar.setEnabled(false);
//        btnModificar.setEnabled(true);
    }//GEN-LAST:event_txtAreaDescMousePressed

    private void txtAreaDescCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtAreaDescCaretUpdate
        if (validarNombres(txtAreaDesc.getText())) {
            descE = true;
            validarCampos();
            //btnGestionarEmpleado.setEnabled(true);
        } else {
            descE = false;
            validarCampos();
            //btnGestionarEmpleado.setEnabled(false);
        }
        
        System.out.println("EMPLEADO"+idEmpleado);
        if (idEmpleado != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_txtAreaDescCaretUpdate

    private void txtPuestoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPuestoMouseEntered
        txtAreaDesc.setEnabled(true);
    }//GEN-LAST:event_txtPuestoMouseEntered

    public void BorrarCampos() {
        txtCurp.setText("");
        txtNombreE.setText("");
        txtapPaternoE.setText("");
        txtapMaternoE.setText("");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnGestionarEmpleado;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox txtAnio;
    public javax.swing.JTextArea txtAreaDesc;
    public javax.swing.JTextField txtCalle;
    public javax.swing.JTextField txtColonia;
    public javax.swing.JTextField txtCurp;
    private javax.swing.JComboBox txtDia;
    public javax.swing.JTextField txtDiasT;
    private javax.swing.JComboBox txtMes;
    public javax.swing.JTextField txtNombreE;
    public javax.swing.JTextField txtNumero;
    public javax.swing.JComboBox txtPuesto;
    public javax.swing.JTextField txtSalario;
    public javax.swing.JTextField txtapMaternoE;
    public javax.swing.JTextField txtapPaternoE;
    // End of variables declaration//GEN-END:variables
}
