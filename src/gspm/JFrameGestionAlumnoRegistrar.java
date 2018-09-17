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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Lobato
 */
public class JFrameGestionAlumnoRegistrar extends javax.swing.JFrame {

    /**
     * Creates new form GestionAlumno
     */
    public JFrameGestionAlumnoRegistrar() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Registro de Alumnos");
        fecha();
        rbVaronil.setSelected(true);
        nombreBeca();
        nombreCategoria();
        nombreMensualidad();
        setResizable(false);
    }

    protected Connection conexion;
    protected Statement sentencia;
    protected ResultSet resultados;

    public String fechaN = "";
    
    public boolean nombreB = false;
    public boolean apellidoPaternoB = false;
    public boolean apellidoMaternoB = false;
    public boolean fechNacimientoDiaB = false;
    public boolean fechNacimientoMesB = false;
    public boolean fechNacimientoAñoB = false;
    public boolean calleB = false;
    public boolean numeroB = false;
    public boolean coloniaB = false;
    public boolean tutorB = false;
    public boolean telefonoB = false;
    public boolean correoB = false;
    public boolean pesoB = false;
    public boolean alturaB = false;
    public boolean numeroJugadorB = false;
    
    
    public void validarCampos() {
        if (nombreB && apellidoPaternoB && apellidoMaternoB && fechNacimientoDiaB && fechNacimientoMesB && fechNacimientoAñoB && calleB && numeroB && coloniaB && tutorB && telefonoB && correoB && pesoB && alturaB && numeroJugadorB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            diaN.setEnabled(true);
            mesN.setEnabled(true);
            anioN.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(true);
            txtTelefono.setEnabled(true);
            txtFB.setEnabled(true);
            txtpeso.setEnabled(true);
            txtaltura.setEnabled(true);
            txtnumJugador.setEnabled(true);
            btnregistrarAlum.setEnabled(true);
            rbVaronil.setEnabled(true);
            rbFemenil.setEnabled(true);
            categoriaI.setEnabled(true);
            tipoBeca.setEnabled(true);
            tipoMensualidad.setEnabled(true);
            rbSi.setEnabled(true);
            rbNo.setEnabled(true);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && fechNacimientoDiaB && fechNacimientoMesB && fechNacimientoAñoB && calleB && numeroB && coloniaB && tutorB && telefonoB && correoB && pesoB && alturaB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            diaN.setEnabled(true);
            mesN.setEnabled(true);
            anioN.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(true);
            txtTelefono.setEnabled(true);
            txtFB.setEnabled(true);
            txtpeso.setEnabled(true);
            txtaltura.setEnabled(true);
            txtnumJugador.setEnabled(true);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(true);
            rbFemenil.setEnabled(true);
            categoriaI.setEnabled(true);
            tipoBeca.setEnabled(true);
            tipoMensualidad.setEnabled(true);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && fechNacimientoDiaB && fechNacimientoMesB && fechNacimientoAñoB && calleB && numeroB && coloniaB && tutorB && telefonoB && correoB && pesoB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            diaN.setEnabled(true);
            mesN.setEnabled(true);
            anioN.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(true);
            txtTelefono.setEnabled(true);
            txtFB.setEnabled(true);
            txtpeso.setEnabled(true);
            txtaltura.setEnabled(true);
            txtnumJugador.setEnabled(false);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(true);
            rbFemenil.setEnabled(true);
            categoriaI.setEnabled(true);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && fechNacimientoDiaB && fechNacimientoMesB && fechNacimientoAñoB && calleB && numeroB && coloniaB && tutorB && telefonoB && correoB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            diaN.setEnabled(true);
            mesN.setEnabled(true);
            anioN.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(true);
            txtTelefono.setEnabled(true);
            txtFB.setEnabled(true);
            txtpeso.setEnabled(true);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(true);
            rbFemenil.setEnabled(true);
            categoriaI.setEnabled(true);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && fechNacimientoDiaB && fechNacimientoMesB && fechNacimientoAñoB && calleB && numeroB && coloniaB && tutorB && telefonoB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            diaN.setEnabled(true);
            mesN.setEnabled(true);
            anioN.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(true);
            txtTelefono.setEnabled(true);
            txtFB.setEnabled(true);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(false);
            rbFemenil.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && fechNacimientoDiaB && fechNacimientoMesB && fechNacimientoAñoB && calleB && numeroB && coloniaB && tutorB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            diaN.setEnabled(true);
            mesN.setEnabled(true);
            anioN.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(true);
            txtTelefono.setEnabled(true);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(false);
            rbFemenil.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && fechNacimientoDiaB && fechNacimientoMesB && fechNacimientoAñoB && calleB && numeroB && coloniaB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            diaN.setEnabled(true);
            mesN.setEnabled(true);
            anioN.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(true);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(false);
            rbFemenil.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && fechNacimientoDiaB && fechNacimientoMesB && fechNacimientoAñoB && calleB && numeroB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            diaN.setEnabled(true);
            mesN.setEnabled(true);
            anioN.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(false);
            rbFemenil.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && fechNacimientoDiaB && fechNacimientoMesB && fechNacimientoAñoB && calleB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            diaN.setEnabled(true);
            mesN.setEnabled(true);
            anioN.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(false);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(false);
            rbFemenil.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && fechNacimientoDiaB && fechNacimientoMesB && fechNacimientoAñoB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            diaN.setEnabled(true);
            mesN.setEnabled(true);
            anioN.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(false);
            rbFemenil.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && fechNacimientoDiaB && fechNacimientoMesB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            diaN.setEnabled(true);
            mesN.setEnabled(true);
            anioN.setEnabled(true);
            txtCalle.setEnabled(false);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(false);
            rbFemenil.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && fechNacimientoDiaB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            diaN.setEnabled(true);
            mesN.setEnabled(true);
            anioN.setEnabled(false);
            txtCalle.setEnabled(false);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(false);
            rbFemenil.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            diaN.setEnabled(true);
            mesN.setEnabled(false);
            anioN.setEnabled(false);
            txtCalle.setEnabled(false);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(false);
            rbFemenil.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            diaN.setEnabled(false);
            mesN.setEnabled(false);
            anioN.setEnabled(false);
            txtCalle.setEnabled(false);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(false);
            rbFemenil.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(false);
            diaN.setEnabled(false);
            mesN.setEnabled(false);
            anioN.setEnabled(false);
            txtCalle.setEnabled(false);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(false);
            rbFemenil.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else {
            txtapPaternoA.setEnabled(false);
            txtapMaternoA.setEnabled(false);
            diaN.setEnabled(false);
            mesN.setEnabled(false);
            anioN.setEnabled(false);
            txtCalle.setEnabled(false);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnregistrarAlum.setEnabled(false);
            rbVaronil.setEnabled(false);
            rbFemenil.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        }
        
    }
    
    
    public int conteoid() {
        int registros = 0;
        String q2 = "SELECT max(idPersona) as total FROM Persona;";
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            resultados = sentencia.executeQuery(q2);
            resultados.next();
            registros = resultados.getInt("total");
            resultados.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return registros;
    }

    void fecha() {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = new GregorianCalendar();
        String dia = Integer.toString(c2.get(Calendar.DATE));
        String mes = Integer.toString(c2.get(Calendar.MONTH));
        String anio = Integer.toString(c2.get(Calendar.YEAR));
        int mesC = Integer.parseInt(mes) + 1;
        diaI.setText(dia);
        mesI.setText(String.valueOf(mesC));
        anioI.setText(anio);
        diaFE.setText(dia);
        mesFE.setText(String.valueOf(mesC));
        anioFE.setText(anio);
    }
    
    public void nombreBeca() {
        String registros = null;
        String q2 = "SELECT Nombre as NombreBeca from Beca;";
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            resultados = sentencia.executeQuery(q2);
            int col = resultados.getMetaData().getColumnCount();
            while (resultados.next()) {
                registros = resultados.getString("NombreBeca");
                tipoBeca.addItem(registros);
            }
            resultados.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void nombreCategoria() {
        String registros = null;
        String q2 = "SELECT Nombre as NombreCategoria from Categoria;";
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            resultados = sentencia.executeQuery(q2);
            int col = resultados.getMetaData().getColumnCount();
            while (resultados.next()) {
                registros = resultados.getString("NombreCategoria");
                categoriaI.addItem(registros);
            }
            resultados.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void nombreMensualidad() {
        String registros = null;
        String q2 = "SELECT idMensualidad as id,Nombre as NombreMensualidad from tipo_mensualidad;";
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            resultados = sentencia.executeQuery(q2);
            int col = resultados.getMetaData().getColumnCount();
            while (resultados.next()) {
                registros = resultados.getString("NombreMensualidad");
                tipoMensualidad.addItem(registros);
            }
            resultados.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int idmensualidad(String nombre) {
        int registros = 0;
        String q2 = "select idMensualidad as id from tipo_mensualidad where Nombre = '" + nombre + "';";
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            resultados = sentencia.executeQuery(q2);
            resultados.next();
            registros = resultados.getInt("id");
            System.out.println(registros);
            resultados.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return registros;
    }

    public int idBeca(String nombre) {
        int registros = 0;
        String q2 = "select idBeca as id from Beca where Nombre = '" + nombre + "';";
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            resultados = sentencia.executeQuery(q2);
            resultados.next();
            registros = resultados.getInt("id");
            System.out.println(registros);
            resultados.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return registros;
    }

    public int idCategoria(String nombre) {
        int registros = 0;
        String q2 = "select idCategoria as id from Categoria where Nombre = '" + nombre + "';";
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            resultados = sentencia.executeQuery(q2);
            resultados.next();
            registros = resultados.getInt("id");
            System.out.println(registros);
            resultados.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return registros;
    }

    public Matcher patron(String cadena, String expresion) {
        Pattern pattern = Pattern.compile(expresion, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cadena);

        return matcher;
    }

    public boolean validarNombres(String cadena) {
        String expresion = "(^[A-Za-z][A-Za-z][A-Za-z]+\\s[A-Za-z]+\\s[A-Za-z]+\\s[A-Za-z]*)|(^[A-Za-z][A-Za-z][A-Za-z]*\\s?[A-Za-z]*\\s?[A-Za-z]*)";
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
    
    public boolean validarTelefono(String cadena) {
        String expresion = "([\\d]{7})";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean validarFechaDia(String cadena) {
        String expresion = "((0[1-9])|([1,2][0-9])|(3[0,1]))";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean validarFechaMes(String cadena) {
        String expresion = "((0[1-9])|(1[0-2]))";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean validarFechaAño(String cadena) {
        String expresion = "((19\\d{2})|(20[01]\\d))";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    
    public boolean validarCorreo(String cadena) {
        String expresion = "(^[_a-z0-9-.].*@([-A-Z0-9+&#/%?=~_|!:,.;])*([-A-Z0-9+&@#/%=~_|]))";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    
    public boolean validarnum(String cadena) {
        String expresion = "[1-9][0-9]{0,2}";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean fb(String cadena) {
        String expresion = "[a-zA-ZñÑ0-9]+[@-_0-9a-zA-ZñÑ.]+";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean peso(String cadena) {
        String expresion = "[0-9]{1,2}(.[0-9]{1,2})?";
        if (patron(cadena, expresion).matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean altura(String cadena) {
        String expresion = "(0([.][4-9][0-9]?)?)|(1([.][0-9]{1,2}))|(2([.][0-1][0-9]?)?)?";
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoRama = new javax.swing.ButtonGroup();
        grupoEntregaUni = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtnombreTutor = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtFB = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombreA = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtapPaternoA = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtapMaternoA = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        diaN = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        mesN = new javax.swing.JTextField();
        anioN = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtCalle = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtColonia = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtpeso = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        rbVaronil = new javax.swing.JRadioButton();
        rbFemenil = new javax.swing.JRadioButton();
        categoriaI = new javax.swing.JComboBox();
        tipoBeca = new javax.swing.JComboBox();
        tipoMensualidad = new javax.swing.JComboBox();
        diaI = new javax.swing.JTextField();
        mesI = new javax.swing.JTextField();
        anioI = new javax.swing.JTextField();
        txtaltura = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtnumJugador = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        rbSi = new javax.swing.JRadioButton();
        rbNo = new javax.swing.JRadioButton();
        diaFE = new javax.swing.JTextField();
        anioFE = new javax.swing.JTextField();
        mesFE = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        btnregistrarAlum = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de los padres o Tutores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Correo o FaceBook");

        txtnombreTutor.setEnabled(false);
        txtnombreTutor.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtnombreTutorCaretUpdate(evt);
            }
        });
        txtnombreTutor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnombreTutorKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Nombre del Tutor");

        jLabel20.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Telefono");

        txtTelefono.setEnabled(false);
        txtTelefono.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTelefonoCaretUpdate(evt);
            }
        });
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        txtFB.setEnabled(false);
        txtFB.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtFBCaretUpdate(evt);
            }
        });
        txtFB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFBKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel20)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnombreTutor, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFB, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtnombreTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtFB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Alumno", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel1.setText("Nombre(s)");

        txtNombreA.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNombreACaretUpdate(evt);
            }
        });
        txtNombreA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreAKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel2.setText("Apellido Paterno");

        txtapPaternoA.setEnabled(false);
        txtapPaternoA.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtapPaternoACaretUpdate(evt);
            }
        });
        txtapPaternoA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtapPaternoAKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel3.setText("Apellido Materno");

        txtapMaternoA.setEnabled(false);
        txtapMaternoA.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtapMaternoACaretUpdate(evt);
            }
        });
        txtapMaternoA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtapMaternoAKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel6.setText("Fecha de Nacimiento");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Dia:");

        diaN.setEnabled(false);
        diaN.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                diaNCaretUpdate(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Mes:");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setText("Año:");

        mesN.setEnabled(false);
        mesN.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                mesNCaretUpdate(evt);
            }
        });

        anioN.setEnabled(false);
        anioN.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                anioNCaretUpdate(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel34.setText("Direccion");

        jLabel13.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel13.setText("Calle");

        txtCalle.setEnabled(false);
        txtCalle.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCalleCaretUpdate(evt);
            }
        });
        txtCalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCalleKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel10.setText("Numero");

        txtNumero.setEnabled(false);
        txtNumero.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNumeroCaretUpdate(evt);
            }
        });
        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel11.setText("Colonia");

        txtColonia.setEnabled(false);
        txtColonia.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtColoniaCaretUpdate(evt);
            }
        });
        txtColonia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtColoniaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel34)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel13)
                                .addGap(10, 10, 10)
                                .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addGap(18, 18, 18)
                                        .addComponent(diaN, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNombreA, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel2))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(71, 71, 71)
                                        .addComponent(jLabel16)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtapPaternoA, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                                    .addComponent(mesN))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtapMaternoA, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                                    .addComponent(anioN)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(txtColonia)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(txtNombreA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtapPaternoA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtapMaternoA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(diaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(mesN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(anioN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtColonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Registro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Rama");

        jLabel7.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Fecha de Inscripccion");

        jLabel8.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Categoria");

        jLabel9.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Peso");

        jLabel26.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Estatura");

        txtpeso.setEnabled(false);
        txtpeso.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtpesoCaretUpdate(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Tipo de Beca");

        jLabel28.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Tipo de Mensualidad");

        grupoRama.add(rbVaronil);
        rbVaronil.setSelected(true);
        rbVaronil.setText("Varonil");
        rbVaronil.setEnabled(false);

        grupoRama.add(rbFemenil);
        rbFemenil.setText("Femenil");
        rbFemenil.setEnabled(false);
        rbFemenil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbFemenilKeyPressed(evt);
            }
        });

        categoriaI.setEnabled(false);
        categoriaI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                categoriaIMouseClicked(evt);
            }
        });

        tipoBeca.setEnabled(false);
        tipoBeca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoBecaActionPerformed(evt);
            }
        });

        tipoMensualidad.setEnabled(false);
        tipoMensualidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tipoMensualidadMouseClicked(evt);
            }
        });

        diaI.setEditable(false);

        mesI.setEditable(false);

        anioI.setEditable(false);

        txtaltura.setEnabled(false);
        txtaltura.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtalturaCaretUpdate(evt);
            }
        });

        jLabel12.setText("Kg");
        jLabel12.setEnabled(false);

        jLabel19.setText("m");
        jLabel19.setEnabled(false);

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel41.setText("Mes:");

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel40.setText("Dia:");

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel42.setText("Año:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel40)
                            .addComponent(jLabel9))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtpeso, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(txtaltura)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addGap(350, 350, 350))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(diaI)
                                    .addComponent(categoriaI, 0, 139, Short.MAX_VALUE))
                                .addGap(34, 34, 34)
                                .addComponent(jLabel41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(mesI, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(anioI, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tipoBeca, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(jLabel5))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbVaronil)
                        .addGap(141, 141, 141)
                        .addComponent(rbFemenil))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(tipoMensualidad, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(rbFemenil)
                    .addComponent(rbVaronil))
                .addGap(9, 9, 9)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(diaI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41)
                            .addComponent(mesI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42)
                            .addComponent(anioI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(categoriaI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtpeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel26)
                            .addComponent(txtaltura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tipoBeca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27)))
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(tipoMensualidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Uniforme", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Berlin Sans FB", 0, 14))); // NOI18N

        jLabel29.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("Numero de Jugador");

        jLabel30.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("¿Se le Entrego uniforme?");

        txtnumJugador.setEnabled(false);
        txtnumJugador.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtnumJugadorCaretUpdate(evt);
            }
        });
        txtnumJugador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnumJugadorKeyTyped(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Berlin Sans FB", 0, 12)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel31.setText("Fecha de Entrega");

        grupoEntregaUni.add(rbSi);
        rbSi.setText("Si");
        rbSi.setEnabled(false);
        rbSi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbSiMouseClicked(evt);
            }
        });

        grupoEntregaUni.add(rbNo);
        rbNo.setText("No");
        rbNo.setEnabled(false);
        rbNo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbNoMouseClicked(evt);
            }
        });

        diaFE.setEditable(false);

        anioFE.setEditable(false);

        mesFE.setEditable(false);

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel35.setText("Dia:");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel36.setText("Mes:");

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel39.setText("Año:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel30))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel35))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(diaFE, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(mesFE, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(114, 114, 114)
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(anioFE, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(rbSi)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rbNo)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addGap(18, 18, 18)
                                .addComponent(txtnumJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtnumJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel29))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel30)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbSi)
                    .addComponent(rbNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel31)
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(diaFE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(mesFE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(anioFE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btnregistrarAlum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add.png"))); // NOI18N
        btnregistrarAlum.setText("Registrar Alumno");
        btnregistrarAlum.setEnabled(false);
        btnregistrarAlum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregistrarAlumActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/error.gif"))); // NOI18N
        btnCancelar.setText("Cancelar Registro");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 804, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnregistrarAlum, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnregistrarAlum)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
        JFrameGestionAlumno pa = new JFrameGestionAlumno();
        pa.setVisible(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnregistrarAlumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregistrarAlumActionPerformed
        String nombre = txtNombreA.getText();
        String apP = txtapPaternoA.getText();
        String apM = txtapMaternoA.getText();
        String mesNac = (String) mesN.getText();
        String anioNac =  String.valueOf(anioN.getText());
        String diaNac = (String) diaN.getText();
        fechaN = anioNac + "-" + mesNac + "-" + diaNac;
        String col = txtColonia.getText();
        String call = txtCalle.getText();
        int num = Integer.parseInt(txtNumero.getText());
        String ramaFV = "";
        if (rbVaronil.isSelected() == true) {
            ramaFV = "Varonil";
        } else if (rbFemenil.isSelected() == true) {
            ramaFV = "Femenil";
        }
        String rama = ramaFV;
        String mesIns = mesI.getText();
        String anioIns = anioI.getText();
        String diaIns = diaI.getText();
        String fechaI = anioIns + "-" + mesIns + "-" + diaIns;
        String categoria = (String) categoriaI.getSelectedItem();//extraigo lo que se ha seleccionado
        int CategoriaFinal = idCategoria(categoria);
        float peso = Float.parseFloat(txtpeso.getText());
        float altura = Float.parseFloat(txtaltura.getText());
        String TBeca = (String) tipoBeca.getSelectedItem();
        int becaFinal = idBeca(TBeca);
        String tMensualidad = (String) tipoMensualidad.getSelectedItem(); //extraigo el nombre de la mensualidad
        int mensFinal = idmensualidad(tMensualidad);
        String nombreTutor = txtnombreTutor.getText();
        int tel = Integer.parseInt(txtTelefono.getText());
        String fb = txtFB.getText();
        int numJ = Integer.parseInt(txtnumJugador.getText());
        String eUni = "";
        if (rbSi.isSelected() == true) {
            eUni = "s";
        } else if (rbNo.isSelected() == true) {
            eUni = "n";
        }
        String eUniforme = eUni;
        int idT = 0;
        String mesE = mesFE.getText();
        String anioE = anioFE.getText();
        String diaE = diaFE.getText();
        String fechaE = "";
        if (rbSi.isSelected() == true) {
            fechaE = anioE + "-" + mesE + "-" + diaE;
        } else if (rbNo.isSelected() == true) {
            fechaE = "00" + "-" + "00" + "-" + "00";
        }

        boolean b = false;//agrega persona
        boolean b1 = false;//agrega tutor
        boolean b2 = false;//agrega Alumno
        boolean b3 = false;//agrega Unifome_Alumno
        boolean b5 = false;
        int idPersona = 0;
        int idSuma = 0;
        //int idSuma = idPersona + 1;
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            b = sentencia.execute("insert into Persona values(0,'" + nombre + "','" + apP + "','" + apM + "','" + col + "','" + call + "'," + num + ");");
            
            String consulta = consulta("select idPersona from persona;");
            String data[] = consulta.split("°°°");
            
            for (int i = 0; i < data.length-1; i++) {
                System.out.println("Dato:  ñ" + data[i] + "ñ");
                idPersona = Integer.parseInt(data[i].replaceAll("\n", ""));
            }
            
            
            idSuma = idPersona;
            
            b3 = sentencia.execute("insert into Uniforme_Alumno values(" + idSuma + ",'" + eUniforme + "','" + fechaE + "'," + 1 + ");");
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al ingresar sus datos");
            System.out.println("error en agregar MySQL: " + e.getLocalizedMessage());
            System.out.println("insert into Persona values(0,'" + nombre + "','" + apP + "','" + apM + "','" + col + "','" + call + "'," + num + ");");
            System.out.println("insert into uniforme_alumno values(" + idSuma + ",'" + eUniforme + "','" + fechaE + "'," + 1 + ");");
        }
        cerrarConexion();
        
        try {
            conexion = Conexion.getConnection();
            sentencia = conexion.createStatement();
            System.out.println("el numero del alumno es"+numJ);
            b1 = sentencia.execute("insert into Tutor values(" + idSuma + ",'" + nombreTutor + "'," + tel + ",'" + fb + "');");
            b2 = sentencia.execute("insert into Alumno values(" + idSuma + ",'" + fechaN + "','" + rama + "'," + numJ + "," + peso + ",'" + fechaI + "'," + altura + "," + becaFinal + "," + mensFinal + "," + CategoriaFinal + "," + idSuma + ");");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al ingresar sus datos, verifique de nuevo porfavor");
            System.out.println("insert into Tutor values(" + idSuma + ",'" + nombreTutor + "'," + tel + ",'" + fb + "');");
            System.out.println("insert into Alumno values(" + idSuma + ",'" + fechaN + "','" + rama + "'," + numJ + "," + peso + ",'" + fechaI + "'," + altura + "," + becaFinal + "," + mensFinal + "," + CategoriaFinal + "," + idSuma + ");");
        }
        cerrarConexion();
        dispose();
       // if(b1== false && b2==false){
            JFrameGestionAlumno pa = new JFrameGestionAlumno();
        pa.setVisible(true);
       // }
        
        
    }//GEN-LAST:event_btnregistrarAlumActionPerformed

    private void tipoBecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoBecaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoBecaActionPerformed

    private void txtNombreACaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNombreACaretUpdate
        if (validarNombres(txtNombreA.getText())) {
            nombreB = true;
            validarCampos();
        } else {
            nombreB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtNombreACaretUpdate

    private void txtapMaternoACaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtapMaternoACaretUpdate
        if (validarNombres(txtapMaternoA.getText())) {
            apellidoMaternoB = true;
            validarCampos();
        } else {
            apellidoMaternoB = true;
            validarCampos();
        }
    }//GEN-LAST:event_txtapMaternoACaretUpdate

    private void txtapPaternoACaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtapPaternoACaretUpdate
        if (validarNombres(txtapPaternoA.getText())) {
            apellidoPaternoB = true;
            validarCampos();
        } else {
            apellidoPaternoB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtapPaternoACaretUpdate

    private void txtCalleCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCalleCaretUpdate
        if (validarnombreNum(txtCalle.getText())) {
            calleB = true;
            validarCampos();
        } else {
            calleB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtCalleCaretUpdate

    private void txtNumeroCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNumeroCaretUpdate
        if (validarnum(txtNumero.getText())) {
            numeroB = true;
            validarCampos();
        } else {
            numeroB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtNumeroCaretUpdate

    private void txtnombreTutorCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtnombreTutorCaretUpdate
        if (validarNombres(txtnombreTutor.getText())) {
            tutorB = true;
            validarCampos();
        } else {
            tutorB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtnombreTutorCaretUpdate

    private void txtColoniaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtColoniaCaretUpdate
        if (validarnombreNum(txtColonia.getText())) {
            coloniaB = true;
            validarCampos();
        } else {
            coloniaB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtColoniaCaretUpdate

    private void txtTelefonoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTelefonoCaretUpdate
        if (validarTelefono(txtTelefono.getText())) {
            telefonoB = true;
            validarCampos();
        } else {
            telefonoB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtTelefonoCaretUpdate

    private void txtFBCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFBCaretUpdate
        if (fb(txtFB.getText())) {
            correoB = true;
            validarCampos();
        } else {
            correoB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtFBCaretUpdate

    private void rbFemenilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbFemenilKeyPressed

    }//GEN-LAST:event_rbFemenilKeyPressed

    private void categoriaIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_categoriaIMouseClicked
        txtpeso.setEnabled(true);
    }//GEN-LAST:event_categoriaIMouseClicked

    private void txtpesoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtpesoCaretUpdate
        if (peso(txtpeso.getText())) {
            pesoB = true;
            validarCampos();
        } else {
            pesoB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtpesoCaretUpdate

    private void tipoMensualidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tipoMensualidadMouseClicked
        txtnumJugador.setEnabled(true);
    }//GEN-LAST:event_tipoMensualidadMouseClicked

    private void txtnumJugadorCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtnumJugadorCaretUpdate
        if (validarnum(txtnumJugador.getText())) {
            numeroJugadorB = true;
            validarCampos();
        } else {
            numeroJugadorB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtnumJugadorCaretUpdate

    private void rbSiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbSiMouseClicked
       btnregistrarAlum.setEnabled(true);
    }//GEN-LAST:event_rbSiMouseClicked

    private void rbNoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbNoMouseClicked
       btnregistrarAlum.setEnabled(true);
    }//GEN-LAST:event_rbNoMouseClicked

    private void txtalturaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtalturaCaretUpdate
        if (altura(txtaltura.getText())) {
            alturaB = true;
            validarCampos();
        } else {
            alturaB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtalturaCaretUpdate

    private void txtNombreAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreAKeyTyped
        if (txtNombreA.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtNombreAKeyTyped

    private void txtapPaternoAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtapPaternoAKeyTyped
         if (txtNombreA.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtapPaternoAKeyTyped

    private void txtapMaternoAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtapMaternoAKeyTyped
        if (txtapMaternoA.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtapMaternoAKeyTyped

    private void txtCalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalleKeyTyped
       if (txtCalle.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtCalleKeyTyped

    private void txtColoniaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColoniaKeyTyped
        if (txtColonia.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtColoniaKeyTyped

    private void txtNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyTyped
        if (txtNumero.getText().length() == 11) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 11 digitos", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtNumeroKeyTyped

    private void txtnombreTutorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreTutorKeyTyped
       if (txtnombreTutor.getText().length() == 150) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 150 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtnombreTutorKeyTyped

    private void txtFBKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFBKeyTyped
        if (txtFB.getText().length() == 50) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 50 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtFBKeyTyped

    private void txtnumJugadorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnumJugadorKeyTyped
       if (txtnumJugador.getText().length() == 3) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 3 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtnumJugadorKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        if (txtTelefono.getText().length() == 11) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 11 digitos", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void diaNCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_diaNCaretUpdate
        if (validarFechaDia(diaN.getText())) {
            fechNacimientoDiaB = true;
            validarCampos();
        } else {
            fechNacimientoDiaB = false;
            validarCampos();
        }
    }//GEN-LAST:event_diaNCaretUpdate

    private void mesNCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_mesNCaretUpdate
        if (validarFechaMes(mesN.getText())) {
            fechNacimientoMesB = true;
            validarCampos();
        } else {
            fechNacimientoMesB = false;
            validarCampos();
        }
    }//GEN-LAST:event_mesNCaretUpdate

    private void anioNCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_anioNCaretUpdate
        if (validarFechaAño(anioN.getText())) {
            fechNacimientoAñoB = true;
            validarCampos();
        } else {
            fechNacimientoAñoB = false;
            validarCampos();
        }
    }//GEN-LAST:event_anioNCaretUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField anioFE;
    private javax.swing.JTextField anioI;
    private javax.swing.JTextField anioN;
    public javax.swing.JButton btnCancelar;
    public javax.swing.JButton btnregistrarAlum;
    private javax.swing.JComboBox categoriaI;
    private javax.swing.JTextField diaFE;
    private javax.swing.JTextField diaI;
    private javax.swing.JTextField diaN;
    private javax.swing.ButtonGroup grupoEntregaUni;
    private javax.swing.ButtonGroup grupoRama;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField mesFE;
    private javax.swing.JTextField mesI;
    private javax.swing.JTextField mesN;
    private javax.swing.JRadioButton rbFemenil;
    private javax.swing.JRadioButton rbNo;
    private javax.swing.JRadioButton rbSi;
    private javax.swing.JRadioButton rbVaronil;
    private javax.swing.JComboBox tipoBeca;
    private javax.swing.JComboBox tipoMensualidad;
    public javax.swing.JTextField txtCalle;
    public javax.swing.JTextField txtColonia;
    public javax.swing.JTextField txtFB;
    public javax.swing.JTextField txtNombreA;
    public javax.swing.JTextField txtNumero;
    public javax.swing.JTextField txtTelefono;
    public javax.swing.JTextField txtaltura;
    public javax.swing.JTextField txtapMaternoA;
    public javax.swing.JTextField txtapPaternoA;
    public javax.swing.JTextField txtnombreTutor;
    public javax.swing.JTextField txtnumJugador;
    public javax.swing.JTextField txtpeso;
    // End of variables declaration//GEN-END:variables
}
