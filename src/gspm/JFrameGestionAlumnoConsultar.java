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
 * @author JuanCarlos
 */
public class JFrameGestionAlumnoConsultar extends javax.swing.JFrame {

    /**
     * Creates new form JFrameGestionAlumnoConsultar
     */
    
    public int idPersonaConsultada;
    
    public JFrameGestionAlumnoConsultar() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Consultar Alumno");
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
    
    public boolean seEntregoUniforme = false;
    
    
    
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
    public void colocarId(int id) {
        idPersonaConsultada = id;
        cargarBaseDatosGeneral();
    }
    
    public void cargarBaseDatosGeneral(){
        String consulta = consulta("select * from Persona where idPersona="+idPersonaConsultada+";");
        String data[] = consulta.split("°°°");
        
        String consulta2 = consulta("select * from Alumno where idPersona="+idPersonaConsultada+";");
        String data2[] = consulta2.split("°°°");
         System.out.println("es data2"+consulta2);
        
        String consulta3 = consulta("select * from Uniforme_Alumno where idUniformeA="+idPersonaConsultada+";");
        String data3[] = consulta3.split("°°°");
        
        String consulta4 = consulta("select * from Tutor where idPersona="+idPersonaConsultada+";");
        String data4[] = consulta4.split("°°°");
        
        
        
        diaN.setEnabled(false);
        mesN.setEnabled(false);
        anioN.setEnabled(false);
        rbVaronil.setEnabled(false);
        rbFemenil.setEnabled(false);
        
        
        txtNombreA.setText(data[1]);
        txtapPaternoA.setText(data[2]);
        txtapMaternoA.setText(data[3]);
        
        diaN.setText(data2[1].charAt(8) + "" + data2[1].charAt(9));
        mesN.setText(data2[1].charAt(5) + "" + data2[1].charAt(6));
        anioN.setText(data2[1].charAt(0) + "" + data2[1].charAt(1) + "" + data2[1].charAt(2) + "" + data2[1].charAt(3));
        
        txtColonia.setText(data[4]);
        txtCalle.setText(data[5]);
        txtNumero.setText(data[6]);
        
        String ramaFV = data2[2];
        if (ramaFV.equals("Varonil")) {
            rbVaronil.setSelected(true);
        } else if (ramaFV.equals("Femenil")) {
            rbFemenil.setSelected(true);
        }
        
        diaI.setText(data2[5].charAt(8) + "" + data2[5].charAt(9));
        mesI.setText(data2[5].charAt(5) + "" + data2[5].charAt(6));
        anioI.setText(data2[5].charAt(0) + "" + data2[5].charAt(1) + "" + data2[5].charAt(2) + "" + data2[5].charAt(3));
        
        String consulta5 = consulta("select nombre from beca where idBeca=" + data2[7]+";");
        String data5[] = consulta5.split("°°°");
        String consulta6 = consulta("select nombre from tipo_mensualidad where idMensualidad=" + data2[8]+";");
        String data6[] = consulta6.split("°°°");
        String consulta7 = consulta("select Nombre from categoria where idCategoria="+data2[9]+";");
        String data7[] = consulta7.split("°°°");
        
        String tMensualidad = data6[0];
        tipoMensualidad.setSelectedItem(tMensualidad);
        
        String TBeca = data5[0];
        tipoBeca.setSelectedItem(TBeca);
        
        String cat = consulta7.substring(0, 6);
        categoriaI.setSelectedItem(cat);
        
        txtpeso.setText(data2[4]);
        txtaltura.setText(data2[6]);
        
        
        txtnombreTutor.setText(data4[1]);
        txtTelefono.setText(data4[2]);
        txtFB.setText(data4[3]);
        
        txtnumJugador.setText(data2[3]);
        
        String eUni = data3[1];
        System.out.println("es ei"+eUni);
        if (eUni.equals("s")) {
            seEntregoUniforme = true;
            diaFE.setText(data3[2].charAt(8) + "" + data3[2].charAt(9));
            mesFE.setText(data3[2].charAt(5) + "" + data3[2].charAt(6));
            anioFE.setText(data3[2].charAt(0) + "" + data3[2].charAt(1) + "" + data3[2].charAt(2) + "" + data3[2].charAt(3));
            rbSi.setSelected(true);//seleccionado
            rbNo.setSelected(false);
            rbSi.setEnabled(false);//habilitado
            rbNo.setEnabled(false);
             diaFE.setEnabled(false);//fecha no habilitada
            mesFE.setEnabled(false);
            anioFE.setEnabled(false);
            
        } else if (eUni.equals("n")) {
            seEntregoUniforme = false;
            fechaEntrega();
            rbNo.setSelected(true);//habilitado
            rbSi.setSelected(false);
            //diaFE.setEditable(true);
            //mesFE.setEditable(true);
            //anioFE.setEditable(true);
            diaFE.setEnabled(true);//fecha habilitada
            mesFE.setEnabled(true);
            anioFE.setEnabled(true);
        }
        
        validarCampos();
        
        
    }
    
    
    
    
    
    public void validarCampos() {
        if (nombreB && apellidoPaternoB && apellidoMaternoB && calleB && numeroB && coloniaB && tutorB && telefonoB && correoB && pesoB && alturaB && numeroJugadorB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(true);
            txtTelefono.setEnabled(true);
            txtFB.setEnabled(true);
            txtpeso.setEnabled(true);
            txtaltura.setEnabled(true);
            txtnumJugador.setEnabled(true);
            btnActualizarAlum.setEnabled(true);
            categoriaI.setEnabled(true);
            tipoBeca.setEnabled(true);
            tipoMensualidad.setEnabled(true);
            if (!seEntregoUniforme) {
                rbSi.setEnabled(true);
                rbNo.setEnabled(true);
            }
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && calleB && numeroB && coloniaB && tutorB && telefonoB && correoB && pesoB && alturaB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(true);
            txtTelefono.setEnabled(true);
            txtFB.setEnabled(true);
            txtpeso.setEnabled(true);
            txtaltura.setEnabled(true);
            txtnumJugador.setEnabled(true);
            btnActualizarAlum.setEnabled(false);
            categoriaI.setEnabled(true);
            tipoBeca.setEnabled(true);
            tipoMensualidad.setEnabled(true);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && calleB && numeroB && coloniaB && tutorB && telefonoB && correoB && pesoB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(true);
            txtTelefono.setEnabled(true);
            txtFB.setEnabled(true);
            txtpeso.setEnabled(true);
            txtaltura.setEnabled(true);
            txtnumJugador.setEnabled(false);
            btnActualizarAlum.setEnabled(false);
            categoriaI.setEnabled(true);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && calleB && numeroB && coloniaB && tutorB && telefonoB && correoB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(true);
            txtTelefono.setEnabled(true);
            txtFB.setEnabled(true);
            txtpeso.setEnabled(true);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnActualizarAlum.setEnabled(false);
            categoriaI.setEnabled(true);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && calleB && numeroB && coloniaB && tutorB && telefonoB) {
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
            btnActualizarAlum.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && calleB && numeroB && coloniaB && tutorB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(true);
            txtTelefono.setEnabled(true);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnActualizarAlum.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && calleB && numeroB && coloniaB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(true);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnActualizarAlum.setEnabled(false);
            rbVaronil.setEnabled(false);
            rbFemenil.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && calleB && numeroB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(true);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnActualizarAlum.setEnabled(false);
            rbVaronil.setEnabled(false);
            rbFemenil.setEnabled(false);
            categoriaI.setEnabled(false);
            tipoBeca.setEnabled(false);
            tipoMensualidad.setEnabled(false);
            rbSi.setEnabled(false);
            rbNo.setEnabled(false);
        } else
        if (nombreB && apellidoPaternoB && apellidoMaternoB && calleB) {
            txtapPaternoA.setEnabled(true);
            txtapMaternoA.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(true);
            txtColonia.setEnabled(false);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnActualizarAlum.setEnabled(false);
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
            txtCalle.setEnabled(true);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnActualizarAlum.setEnabled(false);
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
            txtCalle.setEnabled(false);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnActualizarAlum.setEnabled(false);
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
            txtCalle.setEnabled(false);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnActualizarAlum.setEnabled(false);
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
            txtCalle.setEnabled(false);
            txtNumero.setEnabled(false);
            txtColonia.setEnabled(false);
            txtnombreTutor.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtFB.setEnabled(false);
            txtpeso.setEnabled(false);
            txtaltura.setEnabled(false);
            txtnumJugador.setEnabled(false);
            btnActualizarAlum.setEnabled(false);
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
    
    void fechaEntrega() {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = new GregorianCalendar();
        
        String dia = Integer.toString(c2.get(Calendar.DATE));
        String mes = Integer.toString(c2.get(Calendar.MONTH));
        String anio = Integer.toString(c2.get(Calendar.YEAR));
        
        int mesC = Integer.parseInt(mes) + 1;
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
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtnombreTutor = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtFB = new javax.swing.JTextField();
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
        btnActualizarAlum = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
        txtCalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCalleActionPerformed(evt);
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel34)
                                        .addGap(35, 35, 35))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(18, 18, 18)))
                                .addComponent(txtCalle)))
                        .addGap(268, 268, 268))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(diaN))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNombreA, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtapPaternoA, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(mesN)
                                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtColonia, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(anioN, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtapMaternoA, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(diaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(mesN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(anioN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtColonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

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
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtnombreTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                            .addComponent(txtFB))
                        .addGap(486, 486, 486))))
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtFB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        buttonGroup1.add(rbVaronil);
        rbVaronil.setSelected(true);
        rbVaronil.setText("Varonil");
        rbVaronil.setEnabled(false);

        buttonGroup1.add(rbFemenil);
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
                        .addGap(73, 73, 73)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbVaronil)
                        .addGap(141, 141, 141)
                        .addComponent(rbFemenil))
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel9)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(diaI, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(85, 85, 85)
                                .addComponent(jLabel41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mesI, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(txtpeso, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(categoriaI, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtaltura, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19)))
                        .addGap(63, 63, 63)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(anioI)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tipoBeca, 0, 204, Short.MAX_VALUE)
                        .addComponent(tipoMensualidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbFemenil)
                    .addComponent(rbVaronil)
                    .addComponent(jLabel5))
                .addGap(8, 8, 8)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(diaI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(mesI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(anioI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(categoriaI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtpeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel26)
                    .addComponent(txtaltura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(tipoBeca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(tipoMensualidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        buttonGroup2.add(rbSi);
        rbSi.setText("Si");
        rbSi.setEnabled(false);
        rbSi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbSiMouseClicked(evt);
            }
        });

        buttonGroup2.add(rbNo);
        rbNo.setText("No");
        rbNo.setEnabled(false);
        rbNo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbNoMouseClicked(evt);
            }
        });

        diaFE.setEnabled(false);

        anioFE.setEnabled(false);

        mesFE.setEnabled(false);

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
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(diaFE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addComponent(jLabel31))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel30))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addComponent(rbSi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rbNo))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtnumJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mesFE, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(anioFE, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtnumJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30)
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbSi)
                    .addComponent(rbNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(diaFE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(mesFE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(anioFE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btnActualizarAlum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/findReplace.png"))); // NOI18N
        btnActualizarAlum.setText("Actualizar datos del Alumno");
        btnActualizarAlum.setEnabled(false);
        btnActualizarAlum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarAlumActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/icono volver.png"))); // NOI18N
        btnCancelar.setText("Regresar");
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
                .addGap(44, 44, 44)
                .addComponent(btnActualizarAlum, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 56, Short.MAX_VALUE))
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
                    .addComponent(btnActualizarAlum)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreACaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNombreACaretUpdate
        if (validarNombres(txtNombreA.getText())) {
            nombreB = true;
            validarCampos();
        } else {
            nombreB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtNombreACaretUpdate

    private void txtNombreAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreAKeyTyped
        if (txtNombreA.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtNombreAKeyTyped

    private void txtapPaternoACaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtapPaternoACaretUpdate
        if (validarNombres(txtapPaternoA.getText())) {
            apellidoPaternoB = true;
            validarCampos();
        } else {
            apellidoPaternoB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtapPaternoACaretUpdate

    private void txtapPaternoAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtapPaternoAKeyTyped
        if (txtNombreA.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtapPaternoAKeyTyped

    private void txtapMaternoACaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtapMaternoACaretUpdate
        if (validarNombres(txtapMaternoA.getText())) {
            apellidoMaternoB = true;
            validarCampos();
        } else {
            apellidoMaternoB = true;
            validarCampos();
        }
    }//GEN-LAST:event_txtapMaternoACaretUpdate

    private void txtapMaternoAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtapMaternoAKeyTyped
        if (txtapMaternoA.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtapMaternoAKeyTyped

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

    private void txtCalleCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCalleCaretUpdate
        if (validarnombreNum(txtCalle.getText())) {
            calleB = true;
            validarCampos();
        } else {
            calleB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtCalleCaretUpdate

    private void txtCalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalleKeyTyped
        if (txtCalle.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtCalleKeyTyped

    private void txtNumeroCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNumeroCaretUpdate
        if (validarnum(txtNumero.getText())) {
            numeroB = true;
            validarCampos();
        } else {
            numeroB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtNumeroCaretUpdate

    private void txtNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyTyped
        if (txtNumero.getText().length() == 11) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 11 digitos", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtNumeroKeyTyped

    private void txtColoniaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtColoniaCaretUpdate
        if (validarnombreNum(txtColonia.getText())) {
            coloniaB = true;
            validarCampos();
        } else {
            coloniaB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtColoniaCaretUpdate

    private void txtColoniaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColoniaKeyTyped
        if (txtColonia.getText().length() == 45) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 45 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtColoniaKeyTyped

    private void txtnombreTutorCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtnombreTutorCaretUpdate
        if (validarNombres(txtnombreTutor.getText())) {
            tutorB = true;
            validarCampos();
        } else {
            tutorB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtnombreTutorCaretUpdate

    private void txtnombreTutorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreTutorKeyTyped
        if (txtnombreTutor.getText().length() == 150) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 150 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtnombreTutorKeyTyped

    private void txtTelefonoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTelefonoCaretUpdate
        if (validarTelefono(txtTelefono.getText())) {
            telefonoB = true;
            validarCampos();
        } else {
            telefonoB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtTelefonoCaretUpdate

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        if (txtTelefono.getText().length() == 11) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 11 digitos", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtFBCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFBCaretUpdate
        if (fb(txtFB.getText())) {
            correoB = true;
            validarCampos();
        } else {
            correoB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtFBCaretUpdate

    private void txtFBKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFBKeyTyped
        if (txtFB.getText().length() == 50) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 50 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtFBKeyTyped

    private void txtpesoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtpesoCaretUpdate
        if (peso(txtpeso.getText())) {
            pesoB = true;
            validarCampos();
        } else {
            pesoB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtpesoCaretUpdate

    private void rbFemenilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbFemenilKeyPressed

    }//GEN-LAST:event_rbFemenilKeyPressed

    private void categoriaIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_categoriaIMouseClicked
        txtpeso.setEnabled(true);
    }//GEN-LAST:event_categoriaIMouseClicked

    private void tipoBecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoBecaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoBecaActionPerformed

    private void tipoMensualidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tipoMensualidadMouseClicked
        txtnumJugador.setEnabled(true);
    }//GEN-LAST:event_tipoMensualidadMouseClicked

    private void txtalturaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtalturaCaretUpdate
        if (altura(txtaltura.getText())) {
            alturaB = true;
            validarCampos();
        } else {
            alturaB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtalturaCaretUpdate

    private void txtnumJugadorCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtnumJugadorCaretUpdate
        if (validarnum(txtnumJugador.getText())) {
            numeroJugadorB = true;
            validarCampos();
        } else {
            numeroJugadorB = false;
            validarCampos();
        }
    }//GEN-LAST:event_txtnumJugadorCaretUpdate

    private void txtnumJugadorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnumJugadorKeyTyped
        if (txtnumJugador.getText().length() == 3) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Este campo solo puede contener\nun maximo de 3 caracteres", "Mensaje de error...", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtnumJugadorKeyTyped

    private void rbSiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbSiMouseClicked
        btnActualizarAlum.setEnabled(true);
    }//GEN-LAST:event_rbSiMouseClicked

    private void rbNoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbNoMouseClicked
        btnActualizarAlum.setEnabled(true);
    }//GEN-LAST:event_rbNoMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
        JFrameGestionAlumno pa = new JFrameGestionAlumno();
        pa.setVisible(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnActualizarAlumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarAlumActionPerformed
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
            b = sentencia.execute("UPDATE Persona SET Nombre='" + nombre + "', Apellido_paterno='" + apP + "', Apellido_materno='" + apM + "', Colonia='" + col + "',Calle='" + call + "', numero=" + num + " where idPersona = "+idPersonaConsultada+";");
            
            
            
            b3 = sentencia.execute("UPDATE Uniforme_Alumno SET Entrega_uniforme='" + eUniforme + "', FechaEntregaUniforme='" + fechaE + "' where idUniformeA = "+idPersonaConsultada+";");
            
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
            b1 = sentencia.execute("UPDATE Tutor SET nombreTutor='" + nombreTutor + "', Telefono=" + tel + ", Correo='" + fb + "' where idPersona="+idPersonaConsultada+";");
            b2 = sentencia.execute("UPDATE Alumno SET FechaNacimiento='" + fechaN + "', Rama='" + rama + "', numeroJugador=" + numJ + ", peso=" + peso + ", FechaInscripcion='" + fechaI + "', Altura=" + altura + ", idBeca=" + becaFinal + ", idMensualidad=" + mensFinal + ", idCategoria=" + CategoriaFinal + " where idPersona="+idPersona+";");

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

    }//GEN-LAST:event_btnActualizarAlumActionPerformed

    private void txtCalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCalleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCalleActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField anioFE;
    private javax.swing.JTextField anioI;
    private javax.swing.JTextField anioN;
    public javax.swing.JButton btnActualizarAlum;
    public javax.swing.JButton btnCancelar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox categoriaI;
    private javax.swing.JTextField diaFE;
    private javax.swing.JTextField diaI;
    private javax.swing.JTextField diaN;
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
