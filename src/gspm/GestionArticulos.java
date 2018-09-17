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

public class GestionArticulos extends javax.swing.JFrame {

    public GestionArticulos() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Gestion de Articulo");
        setResizable(false);
    }

    public int idArticulo = 0;
    public String nombreA = "";
    public float precioA = 0;
    public int existenciaA = 0;

    public boolean nombre = false;
    public boolean precio = false;
    public boolean existencia = false;

    public boolean modificacion = false;

    protected Connection conexion;
    protected Statement sentencia;
    protected ResultSet resultados;

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

    public void tipoEmpleado() {
        String registros = null;
        String q2 = "SELECT talla as TallaArticulo from Articulo;";
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            resultados = sentencia.executeQuery(q2);
            int col = resultados.getMetaData().getColumnCount();
            while (resultados.next()) {
                registros = resultados.getString("TallaArticulo");
                talla.addItem(registros);
            }
            resultados.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void LimpiarCampos() {
        jTextFieldNombre.setText("");
        jTextFieldPrecio.setText("");
        jTextFieldExistencia.setText("");
        jButtonRegistrarA.setEnabled(false);
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

    public boolean peso(String cadena) {
        String expresion = "[1-9][0-9]{1,4}(.\\d{1,2})?";
        //"[1-9][0-9]{1,2}(.[0-9]{2})?";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean existencia(String cadena) {
        String expresion = "[1-9]|[1-9]\\d|[1-4]\\d\\d";
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

    public void validarDatosCorrectos() {
        if (precio && existencia) {
            if (idArticulo != 0) {
                System.out.println("entra a id >0");
                if (modificacion) {
                    btnEliminar.setEnabled(false);
                    btnModificar.setEnabled(true);
                } else {
                    btnEliminar.setEnabled(true);
                    btnModificar.setEnabled(false);
                }
            } else {
                System.out.println("entra a mal");
                jButtonRegistrarA.setEnabled(true);
            }
        } else {
            jButtonRegistrarA.setEnabled(false);
            btnModificar.setEnabled(false);
            btnEliminar.setEnabled(false);
        }

        if (precio) {
            jTextFieldPrecio.setEnabled(true);
            jTextFieldExistencia.setEnabled(true);
            talla.setEnabled(true);
        } else {
            jTextFieldExistencia.setEnabled(false);
            talla.setEnabled(false);
        }

        /**
         * if (nombreCorrecto) {
         * jTextFieldNumeroMesesMensualidad.setEnabled(true); } else {
         * jTextFieldNumeroMesesMensualidad.setEnabled(false);
         * jTextFieldCostoMensualidad.setEnabled(false); }*
         */
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jTextFieldPrecio = new javax.swing.JTextField();
        talla = new javax.swing.JComboBox();
        jTextFieldExistencia = new javax.swing.JTextField();
        jButtonModificarA = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        jButtonRegistrarA = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestion de Articulos");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Articulo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jTextFieldPrecio.setEnabled(false);
        jTextFieldPrecio.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldPrecioCaretUpdate(evt);
            }
        });
        jTextFieldPrecio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextFieldPrecioMousePressed(evt);
            }
        });
        jTextFieldPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPrecioKeyTyped(evt);
            }
        });

        talla.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Uni", "Ch", "M", "G", "--" }));
        talla.setEnabled(false);
        talla.setFocusable(false);
        talla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tallaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tallaMousePressed(evt);
            }
        });
        talla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tallaKeyPressed(evt);
            }
        });

        jTextFieldExistencia.setEnabled(false);
        jTextFieldExistencia.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldExistenciaCaretUpdate(evt);
            }
        });
        jTextFieldExistencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldExistenciaKeyTyped(evt);
            }
        });

        jButtonModificarA.setText("Modificar articulo");
        jButtonModificarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarAActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel5.setText("Precio del articulo");

        jLabel7.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel7.setText("Talla");

        jLabel8.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel8.setText("Existencia");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButtonModificarA))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(talla, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldPrecio, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(talla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(244, 244, 244)
                .addComponent(jButtonModificarA)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(51, 0, 0));

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Escuela de Futbol Club Cruz Azul Guerrero");

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Calle Doctor Grabriel Leyva Alarcon 12");

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText(" Unidad Habitacional, Chilpancingo de los Bravo.");

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("39090");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                        .addComponent(jLabel6))
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        btnEliminar.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Bin.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnRegresar.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/icono volver.png"))); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnModificar.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Tabla.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        jButtonRegistrarA.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jButtonRegistrarA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add.png"))); // NOI18N
        jButtonRegistrarA.setText("Registrar");
        jButtonRegistrarA.setEnabled(false);
        jButtonRegistrarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonRegistrarA)
                .addGap(33, 33, 33)
                .addComponent(btnModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEliminar)
                .addGap(26, 26, 26)
                .addComponent(btnRegresar))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButtonRegistrarA, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel2.setText("Nombre del articulo");

        jTextFieldNombre.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldNombreCaretUpdate(evt);
            }
        });
        jTextFieldNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNombreKeyTyped(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnBuscar)
                                        .addGap(23, 23, 23)))))
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        String Nombre = jTextFieldNombre.getText();
        boolean b = false;
        boolean b1 = false;
        String id = consulta("SELECT idArticulo from articulo where Nombre = '" + Nombre + "'");
        String so[] = id.split("°°°");
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            b1 = sentencia.execute("DELETE FROM articulo_venta where idArticulo =" + so[0] + ";");
            b = sentencia.execute("DELETE FROM Articulo where idArticulo = " + so[0] + ";");
            if (b == false) {
                JOptionPane.showMessageDialog(null, "El registro del Articulo se Elimino correctamente", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("error en eliminar MySQL: " + e.getLocalizedMessage());
        }
        cerrarConexion();
        jTextFieldNombre.setText("");
        jTextFieldPrecio.setText("");
        talla.setEnabled(false);
        jTextFieldExistencia.setText("");
        btnEliminar.setEnabled(false);
        jTextFieldNombre.setEnabled(true);
        jTextFieldPrecio.setEnabled(false);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.dispose();
        Principal_secretaria principals = new Principal_secretaria();
        principals.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed

        String Nombre = jTextFieldNombre.getText();
        float Precio = Float.parseFloat(jTextFieldPrecio.getText());
        String Talla = (String) talla.getSelectedItem();
        int Existencia = Integer.parseInt(jTextFieldExistencia.getText());
        boolean b = false;
        try {
            String id = consulta("SELECT idArticulo from articulo where Nombre = '" + Nombre + "'");
            String so[] = id.split("°°°");
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            b = sentencia.execute("UPDATE Articulo set  Nombre = '" + Nombre + "',Precio=" + Precio + ",Talla='" + Talla + "',Existencia = " + Existencia + " where idArticulo = " + so[0] + ";");
            if (b == true) {
                JOptionPane.showInputDialog(null, "Articulo modificado correctamente");
            }
        } catch (SQLException e) {
            System.out.println("error en moficar MySQL: " + e.getLocalizedMessage());
        }
        cerrarConexion();

        jTextFieldNombre.setText("");
        jTextFieldPrecio.setText("");
        jTextFieldExistencia.setText("");
        btnModificar.setEnabled(false);
        jTextFieldNombre.setEnabled(true);
        jTextFieldPrecio.setEnabled(false);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void jButtonModificarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarAActionPerformed

    }//GEN-LAST:event_jButtonModificarAActionPerformed

    private void jTextFieldExistenciaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldExistenciaCaretUpdate
        if (existencia(jTextFieldExistencia.getText())) {
            //jButtonRegistrarA.setEnabled(true);
            existencia = true;
            validarDatosCorrectos();
        } else {
            // jButtonRegistrarA.setEnabled(false);
            existencia = false;
            validarDatosCorrectos();
        }
        if (idArticulo != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_jTextFieldExistenciaCaretUpdate

    private void jButtonRegistrarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarAActionPerformed
        String Nombre = jTextFieldNombre.getText();
        float Precio = Float.parseFloat(jTextFieldPrecio.getText());
        String Talla = (String) talla.getSelectedItem();
        int Existencia = Integer.parseInt(jTextFieldExistencia.getText());
        boolean b = false;
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            b = sentencia.execute("insert into Articulo values(0,'" + Nombre + "'," + Precio + ",'" + Talla + "'," + Existencia + ");");
            if (b == false) {
                JOptionPane.showMessageDialog(null, "Registro Exitoso");
            }
        } catch (SQLException e) {
            System.out.println("error en agregar MySQL: " + e.getLocalizedMessage());
            System.out.println("insert into articulo values(0,'" + Nombre + "'," + Precio + ",'" + Talla + "','" + Existencia + "');\ncommit;");
        }
        LimpiarCampos();
        cerrarConexion();
        jTextFieldPrecio.setEnabled(false);
        idArticulo = 0;
    }//GEN-LAST:event_jButtonRegistrarAActionPerformed

    private void tallaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tallaMouseClicked
        jTextFieldExistencia.setEnabled(true);
    }//GEN-LAST:event_tallaMouseClicked

    private void jTextFieldPrecioCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldPrecioCaretUpdate
        if (peso(jTextFieldPrecio.getText())) {
            //talla.setEnabled(true);
            precio = true;
            validarDatosCorrectos();
        } else {
            precio = false;
            validarDatosCorrectos();
            //talla.setEnabled(false);
        }
        if (idArticulo != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_jTextFieldPrecioCaretUpdate

    private void jTextFieldNombreCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldNombreCaretUpdate
        if (validarNombres(jTextFieldNombre.getText())) {
            //jTextFieldPrecio.setEnabled(true);
            btnBuscar.setEnabled(true);
            //precio = true;
            validarDatosCorrectos();
        } else {
            btnBuscar.setEnabled(false);
            //jTextFieldPrecio.setEnabled(false);
            //precio = true;
            validarDatosCorrectos();
        }

        if (idArticulo != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_jTextFieldNombreCaretUpdate

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String Nombre = jTextFieldNombre.getText();
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            String respuesta = consulta("SELECT * from articulo WHERE Nombre = '" + Nombre + "'");
            System.out.println("es" + respuesta);
            String data[] = respuesta.split("°°°");
            if (respuesta != "") {
                idArticulo = Integer.parseInt(data[0]);
                jTextFieldPrecio.setText(data[2]);
                talla.setSelectedItem(data[3]);
                jTextFieldExistencia.setText(data[4]);
                jTextFieldPrecio.setEnabled(true);
                talla.setEnabled(true);
                jTextFieldExistencia.setEnabled(true);
                btnBuscar.setEnabled(false);
                btnEliminar.setEnabled(true);
                jButtonRegistrarA.setEnabled(false);
                btnModificar.setEnabled(false);
            } else {
                int confirmado = JOptionPane.showConfirmDialog(null, "Articulo"
                        + "no registrado, desea registrar ¿ahora?");

                if (JOptionPane.OK_OPTION == confirmado) {
                    System.out.println("confirmado");
                    idArticulo = 0;
                    jTextFieldPrecio.setEnabled(true);
                } else {
                    System.out.println("vale... no borro nada...");
                    jTextFieldNombre.setText("");
                }
            }

        } catch (SQLException e) {
            System.out.println("error en Buscar MySQL: " + e.getLocalizedMessage());
        }
        cerrarConexion();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jTextFieldPrecioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldPrecioMousePressed
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(true);
    }//GEN-LAST:event_jTextFieldPrecioMousePressed

    private void tallaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tallaMousePressed
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(true);
    }//GEN-LAST:event_tallaMousePressed

    private void jTextFieldNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNombreKeyTyped
        if (jTextFieldNombre.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTextFieldNombreKeyTyped

    private void jTextFieldPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPrecioKeyTyped
        if (jTextFieldPrecio.getText().length() == 7) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 7 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTextFieldPrecioKeyTyped

    private void tallaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tallaKeyPressed
        jTextFieldExistencia.setEnabled(true);
    }//GEN-LAST:event_tallaKeyPressed

    private void jTextFieldExistenciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldExistenciaKeyTyped
        if (jTextFieldExistencia.getText().length() == 11) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 11 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTextFieldExistenciaKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton jButtonModificarA;
    private javax.swing.JButton jButtonRegistrarA;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldExistencia;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldPrecio;
    private javax.swing.JComboBox talla;
    // End of variables declaration//GEN-END:variables

}
