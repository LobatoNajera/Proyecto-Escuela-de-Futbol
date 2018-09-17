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

/**
 *
 * @author JuanCarlos
 */
public class JFrameGestionCategorias extends javax.swing.JFrame {

    /**
     * Creates new form JFrameGestionCategorias
     */
    public JFrameGestionCategorias() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Gestion de categorias.");
        setResizable(false);
    }

    public int idCategoria = 0;
    public String nombreCategoria = "";
    public int edadMinimaCategoria = 0;
    public int edadMaximaCategoria = 0;

    public boolean nombreCorrecto = false;
    public boolean edadMinimaCorrecto = false;
    public boolean edadMaximaCorrecto = false;

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

    public boolean cargarBaseDatosBecaBuscada(String idCategoriaBuscada) {
        String consulta = consulta("select * from categoria where Nombre='" + idCategoriaBuscada + "';");
        String data[] = consulta.split("°°°");
        System.out.println("es" + data);
        if (data.length / 4 == 1) {
            idCategoria = Integer.parseInt(data[0]);
            nombreCategoria = data[1];
            edadMinimaCategoria = Integer.parseInt(data[2]);
            edadMaximaCategoria = Integer.parseInt(data[3]);
            cerrarConexion();
            return true;
        } else {
            idCategoria = 0;
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
    public Matcher patron(String cadena, String expresion) {
        Pattern pattern = Pattern.compile(expresion, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cadena);

        return matcher;
    }

    public boolean validarIdCategoria(String cadena) {
        String expresion = "([1-9][\\d]{0,7})";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarNombreCategoria(String cadena) {
        String expresion = "(([a-zA-ZñÑ]+)|(([1-9]|[1]\\d|[2][0-4])(\\s)([a-zA-ZñÑ]+)))((\\s)(([a-zA-ZñÑ]+)|(\\d+)))*([.]$)?";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarEdadMinima(String cadena) {
        String expresion = "([3-9])|([1]\\d)|([2][0-2])";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarEdadMaxima(String cadena) {
        String expresion = "([3-9])|([1]\\d)|([2][0-2])";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public void validarDatosCorrectos() {
        //if (nombreCorrecto && edadMinimaCorrecto && edadMaximaCorrecto) {
        if (edadMinimaCorrecto && edadMaximaCorrecto) {
            if (idCategoria != 0) {
                if (modificacion) {
                    jButtonEliminarCategoria.setEnabled(false);
                    jButtonModificarCategoria.setEnabled(true);
                } else {
                    jButtonEliminarCategoria.setEnabled(true);
                    jButtonModificarCategoria.setEnabled(false);
                }
            } else {
                jButtonRegistrarCategoria.setEnabled(true);
            }
        } else {
            jButtonRegistrarCategoria.setEnabled(false);
            jButtonModificarCategoria.setEnabled(false);
            jButtonEliminarCategoria.setEnabled(false);
        }

        if (edadMinimaCorrecto) {
            jTextFieldEdadMinimaCategoria.setEnabled(true);
            jTextFieldEdadMaximaCategoria.setEnabled(true);
        } else {
            // jTextFieldEdadMinimaCategoria.setEnabled(false);
            jTextFieldEdadMaximaCategoria.setEnabled(false);
        }

        /**
         * if (nombreCorrecto) { jTextFieldEdadMinimaCategoria.setEnabled(true);
         * } else { jTextFieldEdadMinimaCategoria.setEnabled(false);
         * jTextFieldEdadMaximaCategoria.setEnabled(false); }
         */
    }

    //Eventos de botones
    public void buscar(String idCategoriaBuscada) {
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            boolean mensualidadEncontrada = cargarBaseDatosBecaBuscada(idCategoriaBuscada);
            System.out.println("es" + mensualidadEncontrada);
            if (mensualidadEncontrada) {
                //jTextFieldNombreCategoria.setEnabled(true);
                //jTextFieldNombreCategoria.setText(nombreCategoria);
                jTextFieldEdadMinimaCategoria.setText(String.valueOf(edadMinimaCategoria));
                jTextFieldEdadMaximaCategoria.setText(String.valueOf(edadMaximaCategoria));
                jButtonEliminarCategoria.setEnabled(true);
            } else {
                idCategoria = 0;
                jTextFieldEdadMinimaCategoria.setEnabled(false);
                //jTextFieldBuscarCategoria.setText("");
                //jTextFieldNombreCategoria.setText("");
                jTextFieldEdadMinimaCategoria.setText("");
                jTextFieldEdadMaximaCategoria.setText("");
                jButtonEliminarCategoria.setEnabled(false);
                int seleccionada = JOptionPane.showConfirmDialog(null, "La categoria no se encuentra registrada...\n¿Desea registrarla en el sistema?", "Notificacion...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                switch (seleccionada) {
                    case JOptionPane.YES_OPTION:
                        //jTextFieldNombreCategoria.setEnabled(true);
                        jTextFieldEdadMinimaCategoria.setEnabled(true);
                        break;

                    case JOptionPane.NO_OPTION:
                        jTextFieldNombreCategoria.setText("");
                        //jTextFieldBuscarCategoria.setText("");
                        break;

                    case JOptionPane.CANCEL_OPTION:
                        jTextFieldNombreCategoria.setText("");
                        //jTextFieldBuscarCategoria.setText("");
                        break;
                }
            }

        } catch (SQLException e) {
            System.out.println("erro en agregar MySQL: " + e.getLocalizedMessage());
        }
    }

    public boolean registrar(String nombre, int rangoEdadMinima, int rangoEdadMaxima) {
        boolean b = false;
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            if (rangoEdadMinima < rangoEdadMaxima) {
                b = sentencia.execute("insert into categoria values(0,\"" + nombre + "\"," + rangoEdadMinima + "," + rangoEdadMaxima + ");");

                if (b == false) {
                    idCategoria = 0;
                    jTextFieldNombreCategoria.setEnabled(true);
//                jTextFieldBuscarCategoria.setText("");
                    jTextFieldNombreCategoria.setText("");
                    jTextFieldEdadMinimaCategoria.setText("");
                    jTextFieldEdadMaximaCategoria.setText("");
                    JOptionPane.showMessageDialog(null, "Se registro la categoria de " + nombre + " correctamente", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error en la edad, porfavor verifique los datos");
                jTextFieldEdadMinimaCategoria.setText("");
                jTextFieldEdadMaximaCategoria.setText("");
            }
        } catch (SQLException e) {
            System.out.println("erro en agregar MySQL: " + e.getLocalizedMessage());
        }
        return b;
    }

    public void modificar(String nombre, int rangoEdadMinima, int rangoEdadMaxima) {
        jTextFieldEdadMinimaCategoria.setEnabled(false);
        int seleccionada = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea modificar\n el registro?", "Confirmacion...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (seleccionada) {
            case JOptionPane.YES_OPTION:
                boolean b = false;
                try {
                    if (rangoEdadMinima < rangoEdadMaxima) {
                        conexion = Conexion.getConnection();
                        sentencia = conexion.createStatement();
                        b = sentencia.execute("UPDATE categoria SET Nombre = \"" + nombre + "\", RangoEdadMinima = " + rangoEdadMinima + ", RangoEdadMaxima = " + rangoEdadMaxima + " Where idCategoria = " + idCategoria + ";");

                        if (b == false) {
                            idCategoria = 0;
                            jTextFieldNombreCategoria.setEnabled(true);
                            jTextFieldEdadMinimaCategoria.setEnabled(false);
                            //jTextFieldBuscarCategoria.setText("");
                            jTextFieldNombreCategoria.setText("");
                            jTextFieldEdadMinimaCategoria.setText("");
                            jTextFieldEdadMaximaCategoria.setText("");
                            JOptionPane.showMessageDialog(null, "El registro de la categoria se modifico correctamente", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error en la edad, porfavor verifique los datos");
                        jTextFieldEdadMinimaCategoria.setText("");
                        jTextFieldEdadMaximaCategoria.setText("");
                        //jTextFieldEdadMinimaCategoria.setEnabled(true);
                    }
                } catch (SQLException e) {
                    System.out.println("erro en agregar MySQL: " + e.getLocalizedMessage());
                    System.out.println("UPDATE categoria SET Nombre = \"" + nombre + "\", RangoEdadMinimo = " + rangoEdadMinima + ", RangoEdadMaximo = " + rangoEdadMaxima + " Where idCategoria = " + idCategoria + ";");
                }
                cerrarConexion();
                break;

            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(null, "Se cancelo la modificacion de la categoria", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                break;

            case JOptionPane.CLOSED_OPTION:
                JOptionPane.showMessageDialog(null, "Se cancelo la modificacion de la categoria", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }

    public void eliminar() {
        int seleccionada = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar\n el registro?", "Confirmacion...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (seleccionada) {
            case JOptionPane.YES_OPTION:

                boolean b = false;
                try {
                    conexion = Conexion.getConnection();
                    sentencia = conexion.createStatement();
                    b = sentencia.execute("DELETE FROM categoria where idCategoria = " + idCategoria + ";");

                    if (b == false) {
                        idCategoria = 0;
                        jTextFieldNombreCategoria.setEnabled(true);
//                        jTextFieldBuscarCategoria.setText("");
                        jTextFieldNombreCategoria.setText("");
                        jTextFieldEdadMinimaCategoria.setText("");
                        jTextFieldEdadMaximaCategoria.setText("");
                        JOptionPane.showMessageDialog(null, "El regirtro de la categoria se elimino correctamente", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e) {
                    System.out.println("erro en agregar MySQL: " + e.getLocalizedMessage());
                }
                break;

            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(null, "Se cancelo la eliminacion de la categoria", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
                break;

            case JOptionPane.CLOSED_OPTION:
                JOptionPane.showMessageDialog(null, "Se cancelo la eliminacion de la categoria", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
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
        jButtonBuscarCategoria = new javax.swing.JButton();
        jTextFieldNombreCategoria = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldEdadMinimaCategoria = new javax.swing.JTextField();
        jTextFieldEdadMaximaCategoria = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButtonRegistrarCategoria = new javax.swing.JButton();
        jButtonModificarCategoria = new javax.swing.JButton();
        jButtonEliminarCategoria = new javax.swing.JButton();
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

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar categoria.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jButtonBuscarCategoria.setText("Buscar");
        jButtonBuscarCategoria.setEnabled(false);
        jButtonBuscarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarCategoriaActionPerformed(evt);
            }
        });

        jTextFieldNombreCategoria.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldNombreCategoriaCaretUpdate(evt);
            }
        });
        jTextFieldNombreCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreCategoriaActionPerformed(evt);
            }
        });
        jTextFieldNombreCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNombreCategoriaKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Nombre de la categoria:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldNombreCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jButtonBuscarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jTextFieldNombreCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonBuscarCategoria))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de la categoria.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Rango de edad:");

        jLabel14.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Minima:");

        jTextFieldEdadMinimaCategoria.setEnabled(false);
        jTextFieldEdadMinimaCategoria.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldEdadMinimaCategoriaCaretUpdate(evt);
            }
        });
        jTextFieldEdadMinimaCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEdadMinimaCategoriaKeyTyped(evt);
            }
        });

        jTextFieldEdadMaximaCategoria.setEnabled(false);
        jTextFieldEdadMaximaCategoria.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldEdadMaximaCategoriaCaretUpdate(evt);
            }
        });
        jTextFieldEdadMaximaCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEdadMaximaCategoriaActionPerformed(evt);
            }
        });
        jTextFieldEdadMaximaCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEdadMaximaCategoriaKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Maxima:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel13))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldEdadMinimaCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldEdadMaximaCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldEdadMinimaCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jTextFieldEdadMaximaCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jButtonRegistrarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add.png"))); // NOI18N
        jButtonRegistrarCategoria.setText("Registrar");
        jButtonRegistrarCategoria.setEnabled(false);
        jButtonRegistrarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarCategoriaActionPerformed(evt);
            }
        });

        jButtonModificarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/findReplace.png"))); // NOI18N
        jButtonModificarCategoria.setText("Actualizar");
        jButtonModificarCategoria.setEnabled(false);
        jButtonModificarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarCategoriaActionPerformed(evt);
            }
        });

        jButtonEliminarCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Bin.png"))); // NOI18N
        jButtonEliminarCategoria.setText("Eliminar");
        jButtonEliminarCategoria.setEnabled(false);
        jButtonEliminarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarCategoriaActionPerformed(evt);
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
                .addComponent(jButtonRegistrarCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonModificarCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonEliminarCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addComponent(jButtonRegistrarCategoria)
                        .addComponent(jButtonModificarCategoria))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonEliminarCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

    private void jButtonBuscarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarCategoriaActionPerformed
        try {

            // buscar(Integer.parseInt(jTextFieldBuscarCategoria.getText()));
           
            buscar(jTextFieldNombreCategoria.getText());
            jButtonModificarCategoria.setEnabled(false);
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Excepcion, no se encontro la categoria buscada", "Mensaje de error.", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonBuscarCategoriaActionPerformed

    private void jTextFieldNombreCategoriaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldNombreCategoriaCaretUpdate
        if (validarNombreCategoria(jTextFieldNombreCategoria.getText())) {
            jButtonBuscarCategoria.setEnabled(true);
            //nombreCorrecto = true;
            //edadMinimaCorrecto = false;
            validarDatosCorrectos();
        } else {
            jButtonBuscarCategoria.setEnabled(false);
            //nombreCorrecto = false;
            //edadMinimaCorrecto = false;
            validarDatosCorrectos();
        }

        if (idCategoria != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_jTextFieldNombreCategoriaCaretUpdate

    private void jTextFieldNombreCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNombreCategoriaActionPerformed

    private void jTextFieldNombreCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNombreCategoriaKeyTyped
        if (jTextFieldNombreCategoria.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTextFieldNombreCategoriaKeyTyped

    private void jTextFieldEdadMinimaCategoriaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldEdadMinimaCategoriaCaretUpdate
        if (validarEdadMinima(jTextFieldEdadMinimaCategoria.getText())) {
            edadMinimaCorrecto = true;
            validarDatosCorrectos();
        } else {
            edadMinimaCorrecto = false;
            validarDatosCorrectos();
        }

        if (nombreCategoria != "") {
            modificacion = true;
        }
    }//GEN-LAST:event_jTextFieldEdadMinimaCategoriaCaretUpdate

    private void jTextFieldEdadMinimaCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEdadMinimaCategoriaKeyTyped
        if (jTextFieldEdadMinimaCategoria.getText().length() == 2) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 2 numeros", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTextFieldEdadMinimaCategoriaKeyTyped

    private void jTextFieldEdadMaximaCategoriaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldEdadMaximaCategoriaCaretUpdate
        if (validarEdadMaxima(jTextFieldEdadMaximaCategoria.getText())) {
            edadMaximaCorrecto = true;
            validarDatosCorrectos();
        } else {
            edadMaximaCorrecto = false;
            validarDatosCorrectos();
        }

        if (idCategoria != 0) {
            modificacion = true;
        }
    }//GEN-LAST:event_jTextFieldEdadMaximaCategoriaCaretUpdate

    private void jTextFieldEdadMaximaCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEdadMaximaCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEdadMaximaCategoriaActionPerformed

    private void jTextFieldEdadMaximaCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEdadMaximaCategoriaKeyTyped
        if (jTextFieldEdadMaximaCategoria.getText().length() == 2) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 2 numeros", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTextFieldEdadMaximaCategoriaKeyTyped

    private void jButtonRegistrarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarCategoriaActionPerformed
        if (registrar(jTextFieldNombreCategoria.getText(), Integer.parseInt(jTextFieldEdadMinimaCategoria.getText()), Integer.parseInt(jTextFieldEdadMaximaCategoria.getText())) == false) {
            jTextFieldNombreCategoria.setText("");
            jTextFieldEdadMinimaCategoria.setText("");
            jTextFieldEdadMaximaCategoria.setText("");
        }
        jTextFieldEdadMinimaCategoria.setEnabled(false);
    }//GEN-LAST:event_jButtonRegistrarCategoriaActionPerformed

    private void jButtonModificarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarCategoriaActionPerformed
        try {
            if ((nombreCategoria.equals(jTextFieldNombreCategoria.getText())) && (edadMinimaCategoria == Integer.parseInt(jTextFieldEdadMinimaCategoria.getText())) && (edadMaximaCategoria == Integer.parseInt(jTextFieldEdadMaximaCategoria.getText()))) {
                JOptionPane.showMessageDialog(null, "No se ha hecho ninguna modificacion de los\ncampos de la mensualidad numero " + idCategoria, "Notificacion...", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String nombre = jTextFieldNombreCategoria.getText();
                int edadMinima = Integer.parseInt(jTextFieldEdadMinimaCategoria.getText());
                int edadMaxima = Integer.parseInt(jTextFieldEdadMaximaCategoria.getText());

                modificar(nombre, edadMinima, edadMaxima);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        jTextFieldEdadMinimaCategoria.setEnabled(false);
    }//GEN-LAST:event_jButtonModificarCategoriaActionPerformed

    private void jButtonEliminarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarCategoriaActionPerformed
        try {
            eliminar();
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        jTextFieldEdadMinimaCategoria.setEnabled(false);
    }//GEN-LAST:event_jButtonEliminarCategoriaActionPerformed

    private void jButtonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegresarActionPerformed
        dispose();
        Principal_Admin padmin = new Principal_Admin();
        padmin.setVisible(true);
    }//GEN-LAST:event_jButtonRegresarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscarCategoria;
    public javax.swing.JButton jButtonEliminarCategoria;
    public javax.swing.JButton jButtonModificarCategoria;
    public javax.swing.JButton jButtonRegistrarCategoria;
    public javax.swing.JButton jButtonRegresar;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    public javax.swing.JTextField jTextFieldEdadMaximaCategoria;
    public javax.swing.JTextField jTextFieldEdadMinimaCategoria;
    public javax.swing.JTextField jTextFieldNombreCategoria;
    // End of variables declaration//GEN-END:variables
}
