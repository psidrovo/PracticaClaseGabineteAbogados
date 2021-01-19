/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Paul Idrovo
 */
public class Procurador extends Persona{

    private int idProcurador;
    private String numeroExpediente;

    public int getIdProcurador() {
        return idProcurador;
    }

    public void setIdProcurador(int idProcurador) {
        this.idProcurador = idProcurador;
    }

    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    public Procurador() {
    }

    public Procurador(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    public Procurador(String numeroExpediente, String cedula) {
        super(cedula);
        this.numeroExpediente = numeroExpediente;
    }
    
       
    public boolean crearProcurador(Procurador procurador) {
        try {
            ConexionSql.getConnection();
            Connection conn = ConexionSql.getConn();
            PreparedStatement sqlstm = conn.prepareStatement("INSERT INTO  `gabinete_abogados`.`procurador` VALUES (null, ?, ?)");
            sqlstm.setString(1, procurador.getNumeroExpediente());
            sqlstm.setString(2, procurador.getCedula());
            sqlstm.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "ERROR INGRESAR PROCURADOR", "ERROR DE DATOS", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
    
    public Procurador obtenerIdProcurador(String cedula_procurador) {
        try {
            ConexionSql.getConnection();
            Connection conn = ConexionSql.getConn();
            PreparedStatement sqlstm = conn.prepareStatement("SELECT * FROM gabinete_abogados.procurador where cedula_procurador = ?");
            sqlstm.setString(1, cedula_procurador);
            ResultSet rs = sqlstm.executeQuery();
            while (rs.next()) {
                Procurador pr = new Procurador();
                pr.setIdProcurador(rs.getInt("id_procurador"));
                pr.setNumeroExpediente(rs.getString("id_asunto"));
                return pr;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL BUSCAR CEDULA", "ERROR DE DATOS", JOptionPane.WARNING_MESSAGE);
        }
        return null;

    }
    public List<Procurador> obtenerListaProcuradorAsuntoId(String numeroExpediente) {
        try {
            List<Procurador> listaProcuradores = new ArrayList<>();
            
            ConexionSql.getConnection();
            Connection conn = ConexionSql.getConn();
            PreparedStatement sqlstm = conn.prepareStatement("SELECT * FROM gabinete_abogados.procurador where numero_expediente = ?");
            sqlstm.setString(1, numeroExpediente);
            ResultSet rs = sqlstm.executeQuery();
            while (rs.next()) {
                Procurador pr = new Procurador();
                pr.setIdProcurador(rs.getInt("id_procurador"));
                pr.setCedula(rs.getString("cedula_procurador"));
                listaProcuradores.add(pr);
            }
            return listaProcuradores;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL BUSCAR CEDULA", "ERROR DE DATOS", JOptionPane.WARNING_MESSAGE);
        }
        return null;

    }
}
