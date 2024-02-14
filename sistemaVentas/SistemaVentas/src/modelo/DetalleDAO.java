/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author colom
 */
public class DetalleDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public void registrarDetalles(List<Detalle> listaDetalle,int idVenta){
        try {
            String SQL="INSERT INTO detalle (codProducto,cantidad,precio,idVenta) VALUES (?,?,?,?)";
            con = cn.getConexion();
            ps=con.prepareStatement(SQL);
            
            for(Detalle detalle: listaDetalle){
                ps.setString(1,detalle.getCodigoProducto());
                ps.setInt(2,detalle.getCantidad());
                ps.setFloat(3, detalle.getPrecio());
                ps.setInt(4, idVenta);
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    public List<Detalle> devolverDetalles(int idVenta){
        String SQL="SELECT * FROM detalle where idVenta=?";
        List<Detalle> listaDetalles = new ArrayList<>();
        
        try {
            con=cn.getConexion();
            ps=con.prepareStatement(SQL);
            ps.setInt(1, idVenta);
            rs=ps.executeQuery();
            while(rs.next()){
                Detalle det = new Detalle();
                det.setId(rs.getInt("id"));
                det.setCodigoProducto(rs.getString("codProducto"));
                det.setCantidad(rs.getInt("cantidad"));
                det.setPrecio(rs.getFloat("precio"));
                listaDetalles.add(det);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }return listaDetalles;
    }
}
