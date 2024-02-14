/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author colom
 */
public class VentaDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    
    public int registrarVenta(Venta vta){
       int idVenta=-1;
       try{
            String SQL = "INSERT INTO ventas (cliente,vendedor,total) VALUES (?,?,?)";
            con = cn.getConexion();
            ps = con.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, vta.getCliente());
            ps.setString(2, vta.getVendedor());
            ps.setFloat(3, vta.getTotal());
            ps.executeUpdate();
            
            rs=ps.getGeneratedKeys();//Obtener el id de la venta
            if(rs.next()){
                idVenta=rs.getInt(1);
            }
       } catch (SQLException e){
           System.out.println(e.toString());
       }
       return idVenta;
    }
    
    public List<Venta> listaVenta(){
        List<Venta> listaVta = new ArrayList();
        String SQL = "SELECT * FROM ventas";
        try {
            con=cn.getConexion();
            ps=con.prepareStatement(SQL);
            rs=ps.executeQuery();
            while(rs.next()){
                Venta vta = new Venta();
                vta.setId(rs.getInt("id"));
                vta.setCliente(rs.getString("cliente"));
                vta.setVendedor(rs.getString("vendedor"));
                vta.setTotal(rs.getFloat("total"));
                listaVta.add(vta);
            }
        } catch (SQLException e) {
        }return listaVta;
    }
}


