/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Paul Idrovo
 */
public class Persona {

    private int idPersona;
    private String cedula;
    private String nombre;
    private String direccion;
    private String telefono;

    public Persona() {
    }
    
    public Persona(String cedula) {
        this.cedula = cedula;
    }

    public Persona(String cedula, String nombre, String direccion, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean crearPersona(Persona persona) {
        try {
            ConexionSql.getConnection();
            Connection conn = ConexionSql.getConn();
            PreparedStatement sqlstm = conn.prepareStatement("INSERT INTO  `gabinete_abogados`.`persona` VALUES (null, ?, ? , ? , ?)");
            sqlstm.setString(1, persona.getCedula());
            sqlstm.setString(2, persona.getNombre());
            sqlstm.setString(3, persona.getDireccion());
            sqlstm.setString(4, persona.getTelefono());
            sqlstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "PERSONA CREADA", "NUEVOS DATOS", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            switch (e.getMessage()) {
                case "Data truncation: Incorrect date value: 'null' for column 'fechaNacimiento' at row 1":
                    JOptionPane.showMessageDialog(null, "FORMATO DE FECHA NO VALIDA", "ERROR DE DATOS", JOptionPane.WARNING_MESSAGE);
                    return false;
                default:
                    JOptionPane.showMessageDialog(null, "YA EXISTE UNA PERSONA REGISTRADA CON ESTA CEDULA", "ERROR DE DATOS", JOptionPane.WARNING_MESSAGE);
                    return false;
            }
        }
    }

    public int obtenerIdCedula(String cedula) {
        try {
            ConexionSql.getConnection();
            Connection conn = ConexionSql.getConn();
            PreparedStatement sqlstm = conn.prepareStatement("SELECT id_persona FROM gabinete_abogados.persona where cedula = ?");
            sqlstm.setString(1, cedula);
            ResultSet rs = sqlstm.executeQuery();
            while (rs.next()) {
                return rs.getInt("id_persona");
            }
            return 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL BUSCAR CEDULA", "ERROR DE DATOS", JOptionPane.WARNING_MESSAGE);
        }
        return 0;

    }

    public Persona obtenerPersonaCedula(String cedula) {
        try {
            ConexionSql.getConnection();
            Connection conn = ConexionSql.getConn();
            PreparedStatement sqlstm = conn.prepareStatement("SELECT * FROM gabinete_abogados.persona where cedula = ?");
            sqlstm.setString(1, cedula);
            ResultSet rs = sqlstm.executeQuery();
            while (rs.next()) {
                Persona pr = new Persona();
                pr.setIdPersona(rs.getInt("id_persona"));
                pr.setCedula(rs.getString("cedula"));
                pr.setNombre(rs.getString("nombre"));
                pr.setDireccion(rs.getString("direccion"));
                pr.setTelefono(rs.getString("telefono"));
                return pr;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL BUSCAR CEDULA", "ERROR DE DATOS", JOptionPane.WARNING_MESSAGE);
        }
        return null;

    }

}
