/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Paul Idrovo
 */
public class Asunto extends Persona {

    private int idAsunto;
    private String numeroExpediente;
    private Date fechaInicio;
    private Date fechaFinal;
    private String estado;

    public int getIdAsunto() {
        return idAsunto;
    }

    public void setIdAsunto(int idAsunto) {
        this.idAsunto = idAsunto;
    }

    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean crearAsunto(Asunto asunto) {
        try {
            ConexionSql.getConnection();
            Connection conn = ConexionSql.getConn();
            PreparedStatement sqlstm = conn.prepareStatement("INSERT INTO  `gabinete_abogados`.`asunto` VALUES (null, ?, ?, ?, ?, ?)");
            sqlstm.setString(1, asunto.getNumeroExpediente());
            sqlstm.setDate(2, asunto.getFechaInicio());
            sqlstm.setDate(3, asunto.getFechaFinal());
            sqlstm.setString(4, asunto.getEstado());
            sqlstm.setString(5, asunto.getCedula());
            sqlstm.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "ERROR NUMERO EXPEDIENTE YA EXISTE", "ERROR DE DATOS", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
    
    public String obtenerClienteCedula(String numeroExpediente) {
        try {
            ConexionSql.getConnection();
            Connection conn = ConexionSql.getConn();
            PreparedStatement sqlstm = conn.prepareStatement("SELECT * FROM gabinete_abogados.asunto where numero_expediente = ?");
            sqlstm.setString(1, numeroExpediente);
            ResultSet rs = sqlstm.executeQuery();
            while (rs.next()) {
                return rs.getString("cedula_cliente");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL BUSCAR CEDULA", "ERROR DE DATOS", JOptionPane.WARNING_MESSAGE);
        }
        return "";
    }
    
    public Asunto obtenerListaProcuradorAsuntoId(int idAsunto) {
        try {
            ConexionSql.getConnection();
            Connection conn = ConexionSql.getConn();
            PreparedStatement sqlstm = conn.prepareStatement("SELECT * FROM gabinete_abogados.procurador where id_asunto = ?");
            sqlstm.setInt(1, idAsunto);
            ResultSet rs = sqlstm.executeQuery();
            while (rs.next()) {
                Asunto pr = new Asunto();
                pr.setIdAsunto(rs.getInt("id_asunto"));
                pr.setNumeroExpediente(rs.getString("numero_expediente"));
                pr.setFechaInicio(rs.getDate("fecha_inicio"));
                pr.setFechaFinal(rs.getDate("fecha_final"));
                pr.setEstado(rs.getString("estado"));
                return pr;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL BUSCAR CEDULA", "ERROR DE DATOS", JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }

    public List<Asunto> obtenerListaAsuntos() {
        List<Asunto> listaAsunto = new ArrayList<>();
        try {
            ConexionSql.getConnection();
            Connection conn = ConexionSql.getConn();
            PreparedStatement sqlstm = conn.prepareStatement("SELECT * FROM gabinete_abogados.asunto");
            ResultSet rs = sqlstm.executeQuery();
            while (rs.next()) {
                Asunto pr = new Asunto();
                pr.setIdAsunto(rs.getInt("id_asunto"));
                pr.setNumeroExpediente(rs.getString("numero_expediente"));
                pr.setFechaInicio(rs.getDate("fecha_inicio"));
                pr.setFechaFinal(rs.getDate("fecha_final"));
                pr.setEstado(rs.getString("estado"));
                pr.setCedula(rs.getString("cedula_cliente"));
                listaAsunto.add(pr);
            }
            return listaAsunto;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "ERROR AL BUSCAR ASUNTOS", "ERROR DE DATOS", JOptionPane.WARNING_MESSAGE);
        }
        return null;

    }
}
