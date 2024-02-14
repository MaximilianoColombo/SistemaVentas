/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author colom
 */
public class EmpresaDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public Empresa devolverObjetoEmpresa(){
        String SQL="Select * FROM empresa";
        Empresa empresa= new Empresa();
        try{
            con = cn.getConexion();
            ps=con.prepareStatement(SQL);
            rs=ps.executeQuery();
            if(rs.next()){
                empresa.setNombre(rs.getString("nombre"));
                empresa.setCuil(rs.getString("cuil"));
                empresa.setTelefono(rs.getString("telefono"));
                empresa.setDireccion(rs.getString("direccion"));
                empresa.setRazon(rs.getString("razon"));
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
     return empresa;   
    }
}
