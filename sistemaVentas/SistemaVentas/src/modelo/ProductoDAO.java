/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author colom
 */
public class ProductoDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    

    public boolean comprobarCodigo(String codigo){
        try {
            String SQL="SELECT codigo FROM productos";
            con = cn.getConexion();
            ps = con.prepareStatement(SQL);
            rs=ps.executeQuery();

            while(rs.next()){
                if(rs.getString(1).equals(codigo)){
                    return true;   
                }
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return false;
        
    }
    
    public Producto devolverProducto(String codigo){
        
        try{
                String SQL="SELECT * FROM productos WHERE codigo=?";
                con = cn.getConexion();
                ps = con.prepareStatement(SQL);
                ps.setString(1, codigo);
                rs=ps.executeQuery();
                if(rs.next()){
                    Producto prod = new Producto();
                    prod.setCodigo(rs.getString("codigo"));
                    prod.setDescripcion(rs.getString("descripcion"));
                    prod.setStock(rs.getInt("stock"));
                    prod.setProveedor(rs.getString("proveedor"));
                    prod.setPrecio(rs.getFloat("precio"));
                    return prod;
            }
            
        }catch(SQLException e){
            System.out.println(e.toString());
        }return null;
    }
    
    public void rellenarListaProveedores(JComboBox lista){
        String SQL = "SELECT nombre FROM proveedor";
        
        try {
            con= cn.getConexion();
            ps = con.prepareStatement(SQL);
            rs=ps.executeQuery();
            while(rs.next()){
                lista.addItem(rs.getString(1));
            }
            
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    public boolean actualizarStockPrecio(Producto prod,String codigo){
        
        try {
             
            String sql="UPDATE productos SET stock=stock+?,precio=(precio*stock+?*?)/(stock+?) WHERE codigo=?";
                con = cn.getConexion();
                ps = con.prepareStatement(sql);
                ps.setInt(1, prod.getStock());
                ps.setFloat(2, prod.getPrecio());
                ps.setInt(3, prod.getStock());
                ps.setInt(4, prod.getStock());
                ps.setString(5,codigo);
                ps.executeUpdate();
                ps.close();
                
                
                
        } catch (SQLException e) {
             System.out.println(e.toString());
             return false;
        }
        
         return true;
    }
    
    public boolean registrarProducto(Producto prod){
        try {
           
                String sql = "INSERT INTO productos (codigo,descripcion,proveedor,stock,precio) VALUES(?,?,?,?,?)";
                con = cn.getConexion();
                ps=con.prepareStatement(sql);
                ps.setString(1, prod.getCodigo());
                ps.setString(2, prod.getDescripcion());
                ps.setString(3, prod.getProveedor());
                ps.setInt(4, prod.getStock());
                ps.setFloat(5, prod.getPrecio());

                ps.execute();
            }
            
           
         catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
         return true;
    }
    
    public void modificarProducto(String codigo,Producto prod){

        try {
            String SQL="UPDATE productos SET codigo=?,descripcion=?,proveedor=?,stock=?,precio=? "
                    + "WHERE codigo=?"; //Consulta para actualizar la fila
            con = cn.getConexion();
            ps = con.prepareStatement(SQL);
            ps.setString(1, prod.getCodigo());
            ps.setString(2, prod.getDescripcion());
            ps.setString(3, prod.getProveedor());
            ps.setInt(4, prod.getStock());
            ps.setFloat(5, prod.getPrecio());
            ps.setString(6, codigo);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    
     public List listaProducto(){
        List<Producto> listaProd = new ArrayList();
        String SQL = "SELECT * FROM productos";
        try {
            con = cn.getConexion();
            ps=con.prepareStatement(SQL);
            rs=ps.executeQuery();
            while(rs.next()){
                Producto prod = new Producto();
                prod.setPrecio(rs.getFloat("precio"));
                prod.setStock(rs.getInt("stock"));
                prod.setCodigo(rs.getString("codigo"));
                prod.setProveedor(rs.getString("proveedor"));
                prod.setDescripcion(rs.getString("descripcion"));
                listaProd.add(prod);
            }
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return listaProd;
    }
    
     public void eliminarProducto(String codigo){
     
     try {
         String SQL="DELETE FROM productos WHERE codigo=?"; //Elimina la fila mediante SQL
         con = cn.getConexion();
         ps=con.prepareStatement(SQL);
         ps.setString(1, codigo);
         ps.executeUpdate();
         ps.close();
     } catch (SQLException e) {
         System.out.println(e.toString());
     }
 }
    
     
     public void actualizarStockVenta(List<Detalle> listaDetalle){
         String SQL="UPDATE productos SET stock=stock-? WHERE codigo=? ";
         try {
             con=cn.getConexion();
             ps=con.prepareStatement(SQL);
             
             for(Detalle detalle: listaDetalle ){
                 ps.setInt(1,detalle.getCantidad());
                 ps.setString(2, detalle.getCodigoProducto());
                 ps.addBatch();
             }
             ps.executeBatch();
             ps.close();
         } catch (SQLException e) {
         }
     }
}
