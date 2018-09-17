package gspm;

import Conexion.Conexion;
//import Impresora.*;
import java.awt.print.PrinterException;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Itzel
 */
public class Venta_articulos extends javax.swing.JFrame {

   
    public Venta_articulos() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Venta de articulos.");
        configurarModeloTabla();
        cargarBaseDatosTabla();
        configurarModeloTabla2();
        fecha();
        setResizable(false);
    }

    //Declaramos un modelo de la tabla
    DefaultTableModel modeloTabla = new DefaultTableModel();
    DefaultTableModel modeloTabla2 = new DefaultTableModel();

    ArrayList<Integer> idArticuloVenta = new ArrayList();

    ArrayList<Integer> idArticulos = new ArrayList();
    ArrayList<String> nombreArticulos = new ArrayList();
    ArrayList<Float> precioArticulos = new ArrayList();
    ArrayList<String> tallaArticulos = new ArrayList();
    ArrayList<Integer> cantidadArticulos = new ArrayList();
    ArrayList<Float> costoArticulos = new ArrayList();

    public int arrayId[];

    protected Connection conexion;
    protected Statement sentencia;
    protected ResultSet resultados;

    //Se agregan las columnas que tendra la tabla de becas
    void configurarModeloTabla() {
        modeloTabla.addColumn("Codigo");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Talla");
        modeloTabla.addColumn("Existencia");
        jTableArticulos.setModel(modeloTabla);
    }

    void fecha() {//método para obtener la fecha
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = new GregorianCalendar();

        String dia = Integer.toString(c2.get(Calendar.DATE));
        System.out.println("es" + dia);
        String mes = Integer.toString(c2.get(Calendar.MONTH));
        String anio = Integer.toString(c2.get(Calendar.YEAR));
        int mesC = Integer.parseInt(mes) + 1;
        //condiciones para agregar el segundo digito
        if (mesC >= 1 && mesC <= 9 && Integer.parseInt(dia) >= 1 && Integer.parseInt(dia) <= 9) {
            txtfecha.setText("0" + dia + " - " + "0" + mesC + " - " + anio);
        } else if (mesC >= 1 && mesC <= 9) {
            txtfecha.setText(dia + " - " + "0" + mesC + " - " + anio);
        } else if (Integer.parseInt(dia) >= 1 && Integer.parseInt(dia) <= 9) {
            txtfecha.setText("0" + dia + " - " + mesC + " - " + anio);
        } else {
            txtfecha.setText(dia + " - " + mesC + " - " + anio);
        }
    }

    void configurarModeloTabla2() {

        modeloTabla2.addColumn("Nombre");
        modeloTabla2.addColumn("Talla");
        modeloTabla2.addColumn("Precio");
        modeloTabla2.addColumn("Cantidad");
        modeloTabla2.addColumn("Costo");

        jTableVenta.setModel(modeloTabla2);
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

    void cargarBaseDatosTabla() {
        String consulta = consulta("select * from Articulo;");
        String data[] = consulta.split("°°°");//separa por medio de °°°

        int cols = jTableArticulos.getColumnCount();
        int rows = data.length / cols;
        String title[] = new String[cols];
        String[][] tabla = new String[rows][cols];
        int aux = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                tabla[i][j] = data[aux];
                aux++;
            }
        }

        title[0] = jTableArticulos.getColumnName(0);
        title[1] = jTableArticulos.getColumnName(1);
        title[2] = jTableArticulos.getColumnName(2);
        title[3] = jTableArticulos.getColumnName(3);
        title[4] = jTableArticulos.getColumnName(4);

        jTableArticulos.setModel(new DefaultTableModel(tabla, title));
        cerrarConexion();
    }

    void buscarPorCodigo(String codigo) {
        String consulta = consulta("select * from Articulo where idArticulo =" + codigo + ";");
        String data[] = consulta.split("°°°");
        
        if (consulta.length() == 0) {
            JOptionPane.showMessageDialog(null, "No se encontro registro del producto "+codigo+".", "Mensaje informativo.", JOptionPane.INFORMATION_MESSAGE);
            cargarBaseDatosTabla();
        } else {
            
            int cols = jTableArticulos.getColumnCount();
            int rows = data.length / cols;
            String title[] = new String[cols];
            String[][] tabla = new String[rows][cols];
            int aux = 0;
            
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    tabla[i][j] = data[aux];
                    aux++;
                }
            }
            
            title[0] = jTableArticulos.getColumnName(0);
            title[1] = jTableArticulos.getColumnName(1);
            title[2] = jTableArticulos.getColumnName(2);
            title[3] = jTableArticulos.getColumnName(3);
            title[4] = jTableArticulos.getColumnName(4);
            
            jTableArticulos.setModel(new DefaultTableModel(tabla, title));
            cerrarConexion();
        }
    }

    void buscarTodo(String codigo) {
        String consulta = consulta("select * from Articulo");
        String data[] = consulta.split("°°°");

        int cols = jTableArticulos.getColumnCount();
        int rows = data.length / cols;
        String title[] = new String[cols];
        String[][] tabla = new String[rows][cols];
        int aux = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tabla[i][j] = data[aux];
                aux++;
            }
        }

        title[0] = jTableArticulos.getColumnName(0);
        title[1] = jTableArticulos.getColumnName(1);
        title[2] = jTableArticulos.getColumnName(2);
        title[3] = jTableArticulos.getColumnName(3);
        title[4] = jTableArticulos.getColumnName(4);

        jTableArticulos.setModel(new DefaultTableModel(tabla, title));
        cerrarConexion();
    }

    void buscarPorNombre(String codigo) {
        String consulta = consulta("select * from Articulo where Nombre = '" + codigo + "';");
        String data[] = consulta.split("°°°");

        int cols = jTableArticulos.getColumnCount();
        int rows = data.length / cols;
        String title[] = new String[cols];
        String[][] tabla = new String[rows][cols];
        int aux = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                tabla[i][j] = data[aux];
                aux++;
            }
        }

        title[0] = jTableArticulos.getColumnName(0);
        title[1] = jTableArticulos.getColumnName(1);
        title[2] = jTableArticulos.getColumnName(2);
        title[3] = jTableArticulos.getColumnName(3);
        title[4] = jTableArticulos.getColumnName(4);

        jTableArticulos.setModel(new DefaultTableModel(tabla, title));
        cerrarConexion();
    }

    void cargarDatosTablaVenta() {

        int cols = jTableVenta.getColumnCount();
        int rows = idArticulos.size();
        String title[] = new String[cols];
        String[][] tabla = new String[rows][cols];
        int aux = 0;

        for (int i = 0; i < rows; i++) {
            tabla[i][0] = nombreArticulos.get(i);
            tabla[i][1] = tallaArticulos.get(i);
            tabla[i][2] = String.valueOf(precioArticulos.get(i));
            tabla[i][3] = String.valueOf(cantidadArticulos.get(i));
            tabla[i][4] = String.valueOf(costoArticulos.get(i));
        }

        title[0] = jTableVenta.getColumnName(0);
        title[1] = jTableVenta.getColumnName(1);
        title[2] = jTableVenta.getColumnName(2);
        title[3] = jTableVenta.getColumnName(3);
        title[4] = jTableVenta.getColumnName(4);

        jTableVenta.setModel(new DefaultTableModel(tabla, title));
    }

    void agregaraLista() {
        int fila = jTableArticulos.getSelectedRow();

        int idArticulo = Integer.parseInt((jTableArticulos.getValueAt(fila, 0).toString()).replaceAll("\n", ""));
        String nombre = jTableArticulos.getValueAt(fila, 1).toString();
        String talla = jTableArticulos.getValueAt(fila, 3).toString();
        float precio = Float.parseFloat((jTableArticulos.getValueAt(fila, 2).toString()).replaceAll("\n", ""));
        int cantidad = Integer.parseInt(txtCantidad.getText());
        float costo = precio * cantidad;

        idArticulos.add(idArticulo);
        nombreArticulos.add(nombre);
        tallaArticulos.add(talla);
        precioArticulos.add(precio);
        cantidadArticulos.add(cantidad);
        costoArticulos.add(costo);
    }

    void quitarArtticuloLista() {
        int fila = jTableVenta.getSelectedRow();

        idArticulos.remove(fila);
        nombreArticulos.remove(fila);
        tallaArticulos.remove(fila);
        precioArticulos.remove(fila);
        cantidadArticulos.remove(fila);
        costoArticulos.remove(fila);
    }

    void limpiarListaVenta() {
        int tamaño = jTableVenta.getRowCount();

        for (int i = 0; i < tamaño; i++) {
            idArticulos.remove(0);
            nombreArticulos.remove(0);
            tallaArticulos.remove(0);
            precioArticulos.remove(0);
            cantidadArticulos.remove(0);
            costoArticulos.remove(0);
        }
    }

    void generarImporteTotal() {
        int nImporte = costoArticulos.size();

        if (nImporte != 0) {
            float importeTotal = 0;

            for (int i = 0; i < nImporte; i++) {
                importeTotal += costoArticulos.get(i);
            }

            txtImporteTotal.setText(String.valueOf(importeTotal));
        }
    }

    public Matcher patron(String cadena, String expresion) {
        Pattern pattern = Pattern.compile(expresion, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cadena);

        return matcher;
    }

    public boolean validarNombreArticulo(String cadena) {
        String expresion = "([1-9]|[1-9](\\d){1,5}|Todos|todos|[a-zA-Z]+)";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarCantidadArticulo(String cadena) {
        String expresion = "([1-9]|[1-9]\\d)";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public void disminuirArticulo() {
        int fila = jTableArticulos.getSelectedRow();

        idArticuloVenta.add(Integer.parseInt((jTableArticulos.getValueAt(fila, 0).toString()).replaceAll("\n", "")));

        int existencia = Integer.parseInt(jTableArticulos.getValueAt(fila, 4).toString());
        int cantidad = Integer.parseInt(txtCantidad.getText());
        int restante = existencia - cantidad;

        jTableArticulos.setValueAt(restante, fila, 4);
    }

    public void aumentarArticulo() {
        int fila = jTableVenta.getSelectedRow();
        int posicion = 0;

        for (int i = 0; i < jTableArticulos.getRowCount(); i++) {
            if (Integer.parseInt((jTableArticulos.getValueAt(i, 0).toString()).replaceAll("\n", "")) == idArticulos.get(fila)) {
                posicion = i;
                System.out.println("Posicion: " + posicion);
                break;
            }
        }

        int existencia = Integer.parseInt(jTableArticulos.getValueAt(posicion, 4).toString());
        int cantidad = Integer.parseInt(jTableVenta.getValueAt(fila, 3).toString());
        int suma = existencia + cantidad;
        //String resultado = String.valueOf(suma);

        jTableArticulos.setValueAt(suma, posicion, 4);
    }

    public void actualizarTablaVenta() {

        boolean b;
        int existencia;
        int idArticulo;

        for (int fila = 0; fila < jTableArticulos.getRowCount(); fila++) {
            existencia = Integer.parseInt(jTableArticulos.getValueAt(fila, 4).toString());
            idArticulo = Integer.parseInt((jTableArticulos.getValueAt(fila, 0).toString()).replaceAll("\n", ""));

            System.out.println("Existencia: " + existencia + "   En: " + fila + "\nArrayId = " + idArticulo + "\n");
            b = false;
            try {
                conexion = Conexion.getConnection();
                sentencia = conexion.createStatement();
                b = sentencia.execute("UPDATE Articulo SET Existencia = " + existencia + " Where idArticulo = " + idArticulo + ";");

            } catch (SQLException e) {
                System.out.println("erro en actualizar MySQL: " + e.getLocalizedMessage());
                System.out.println("UPDATE Articulo SET Existencia = " + existencia + " Where idArticulo = " + arrayId[fila] + ";");
            }
            cerrarConexion();
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

    public int consultaFolio(String consulta) {
        int folio = 1;
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            resultados = sentencia.executeQuery(consulta);

            int col = resultados.getMetaData().getColumnCount();
            while (resultados.next()) {
                for (int i = 1; i <= col; i++) {
                    folio = Integer.parseInt(resultados.getObject(i).toString());
                }
            }
        } catch (SQLException e) {
        }

        return folio;
    }

    public boolean registrarVenta() {
        boolean b = false;
        int folio = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = new GregorianCalendar();
        String dia = Integer.toString(c2.get(Calendar.DATE));
        String mes = Integer.toString(c2.get(Calendar.MONTH));
        String anio = Integer.toString(c2.get(Calendar.YEAR));
        String fecha = anio + "-" + mes + "-" + dia;
        float importeTotal = Float.parseFloat(txtImporteTotal.getText());

        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            b = sentencia.execute("insert into Venta values(0," + importeTotal + ",'" + fecha + "');");
            folio = consultaFolio("select folio from Venta;");
        } catch (SQLException e) {
            System.out.println("erro en agregar venta MySQL: " + e.getLocalizedMessage());
            System.out.println("insert into Venta values(0," + importeTotal + ",'" + fecha + "');");
        }

        if (b == false) {
            for (int fila = 0; fila < jTableVenta.getRowCount(); fila++) {
                String idArticulo = jTableVenta.getValueAt(fila, 0).toString();
                String id = consulta("select idArticulo from Articulo where nombre = '" + idArticulo + "'");
                String data[] = id.split("°°°");
                float costo = Float.parseFloat(jTableVenta.getValueAt(fila, 4).toString());
                int cantidad = Integer.parseInt(jTableVenta.getValueAt(fila, 3).toString());

                try {
                    conexion = Conexion.getConnection();
                    sentencia = conexion.createStatement();

                    b = sentencia.execute("insert into articulo_venta values(" + data[0] + "," + folio + "," + costo + "," + cantidad + ");");

                } catch (SQLException e) {
                    System.out.println("error en agregar MySQL: " + e.getLocalizedMessage());
                    System.out.println("insert into Articulo_Venta values(" + data[0] + "," + folio + "," + costo + "," + cantidad + ");");
                }
            }
            JOptionPane.showMessageDialog(null, "Se registro la venta numero de " + folio + " correctamente", "Mensaje informativo...", JOptionPane.INFORMATION_MESSAGE);
        } else if (b == true) {
            folio += 1;
            JOptionPane.showMessageDialog(null, "No se pudo registrar la venta numero de " + folio, "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
        return b;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jButtonBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableArticulos = new JTable() {

            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }

        };
        txtCodigo = new javax.swing.JTextField();
        jButtonAgregarLista = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableVenta = new JTable() {

            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }

        };
        jButtonQuitarArticulo = new javax.swing.JButton();
        jButtonImprimir = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtfecha = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtImporteTotal = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        jFormattedTextField1.setText("jFormattedTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("Cantidad:");

        txtCantidad.setEnabled(false);
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

        jButtonBuscar.setText("Buscar");
        jButtonBuscar.setEnabled(false);
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jTableArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableArticulosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableArticulos);

        txtCodigo.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCodigoCaretUpdate(evt);
            }
        });
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        jButtonAgregarLista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/agregarArticulo.png"))); // NOI18N
        jButtonAgregarLista.setText("Agregar a Lista");
        jButtonAgregarLista.setEnabled(false);
        jButtonAgregarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarListaActionPerformed(evt);
            }
        });

        jTableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableVentaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableVenta);

        jButtonQuitarArticulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/quitarArticulo.png"))); // NOI18N
        jButtonQuitarArticulo.setText("Quitar articulo");
        jButtonQuitarArticulo.setEnabled(false);
        jButtonQuitarArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonQuitarArticuloActionPerformed(evt);
            }
        });

        jButtonImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/imprimir.png"))); // NOI18N
        jButtonImprimir.setText("Imprimir Nota");
        jButtonImprimir.setEnabled(false);
        jButtonImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImprimirActionPerformed(evt);
            }
        });

        jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/icono volver.png"))); // NOI18N
        jButtonCancelar.setText("volver");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jLabel4.setText("Fecha de Venta:");

        txtfecha.setEditable(false);

        jLabel5.setText("Importe total:");

        txtImporteTotal.setEditable(false);
        txtImporteTotal.setEnabled(false);

        jPanel1.setBackground(new java.awt.Color(51, 0, 0));

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Escuela de Futbol Club Cruz Azul Guerrero");

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Calle Doctor Grabriel Leyva Alarcon 12");

        jLabel7.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText(" Unidad Habitacional, Chilpancingo de los Bravo.");

        jLabel8.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("39090");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addComponent(jLabel8))
                    .addComponent(jLabel6)
                    .addComponent(jLabel3))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addContainerGap())
        );

        jLabel9.setText("codigo de Articulo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtImporteTotal))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(106, 106, 106))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButtonImprimir)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonCancelar))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(29, 29, 29)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonAgregarLista)
                                .addGap(68, 68, 68))
                            .addComponent(jButtonQuitarArticulo, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBuscar)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonAgregarLista)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonQuitarArticulo)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtImporteTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonCancelar)
                    .addComponent(jButtonImprimir))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        String codigo = txtCodigo.getText();
        if (codigo.equalsIgnoreCase("todos")) {
            cargarBaseDatosTabla();
        } else {
            buscarPorCodigo(codigo);
        }
        
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonAgregarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarListaActionPerformed
        int fila = jTableArticulos.getSelectedRow();
        int existencia = Integer.parseInt((jTableArticulos.getValueAt(fila, 4).toString()).replaceAll("\n", ""));

        if(existencia > 5){
        if (Integer.parseInt(txtCantidad.getText()) <= existencia) {
            agregaraLista();
            disminuirArticulo();
            cargarDatosTablaVenta();
            txtImporteTotal.setEnabled(true);
            generarImporteTotal();
            jButtonImprimir.setEnabled(true);
            txtCantidad.setText("");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Solo hay " + existencia + " articulos en existencia", "Mnesaje de errror.", JOptionPane.ERROR_MESSAGE);
        }
        }else{
            JOptionPane.showMessageDialog(null, "imposible realizar la venta, porfavor resurta el articulo");
            txtCantidad.setText("");
        }
        txtCantidad.setEnabled(false);

    }//GEN-LAST:event_jButtonAgregarListaActionPerformed

    private void jButtonQuitarArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonQuitarArticuloActionPerformed

        aumentarArticulo();
        quitarArtticuloLista();
        cargarDatosTablaVenta();
        generarImporteTotal();
        if (idArticulos.size() == 0) {
            jButtonImprimir.setEnabled(false);
            txtImporteTotal.setText("");
            txtImporteTotal.setEnabled(false);
        }

    }//GEN-LAST:event_jButtonQuitarArticuloActionPerformed

    private void jTableArticulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableArticulosMouseClicked
        txtCantidad.setEnabled(true);
    }//GEN-LAST:event_jTableArticulosMouseClicked

    private void jTableVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableVentaMouseClicked
        jButtonQuitarArticulo.setEnabled(true);
    }//GEN-LAST:event_jTableVentaMouseClicked

    private void txtCodigoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCodigoCaretUpdate
        if (validarNombreArticulo(txtCodigo.getText())) {
            jButtonBuscar.setEnabled(true);
        } else {
            jButtonBuscar.setEnabled(false);
        }
    }//GEN-LAST:event_txtCodigoCaretUpdate

    private void txtCantidadCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCantidadCaretUpdate
        if (validarCantidadArticulo(txtCantidad.getText())) {
            jButtonAgregarLista.setEnabled(true);
        } else {
            jButtonAgregarLista.setEnabled(false);
        }
    }//GEN-LAST:event_txtCantidadCaretUpdate

    private void jButtonImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImprimirActionPerformed
        if (registrarVenta() == false) {
            actualizarTablaVenta();
            limpiarListaVenta();
            cargarDatosTablaVenta();
            Impresora imp = new Impresora();
        }
    }//GEN-LAST:event_jButtonImprimirActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        this.dispose();
        Principal_secretaria ps = new Principal_secretaria();
        ps.setVisible(true);
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregarLista;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonImprimir;
    private javax.swing.JButton jButtonQuitarArticulo;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableArticulos;
    private javax.swing.JTable jTableVenta;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtImporteTotal;
    private javax.swing.JTextField txtfecha;
    // End of variables declaration//GEN-END:variables
}
