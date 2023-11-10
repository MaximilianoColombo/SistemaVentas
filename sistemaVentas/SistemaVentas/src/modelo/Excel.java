/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.String.format;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.text.MessageFormat.format;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.*;
import org.apache.commons.collections4.ListValuedMap;
import static org.apache.poi.ss.formula.SheetNameFormatter.format;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;




public class Excel {
 
    public static CellStyle formatearCelda(Workbook libro,String tipo){
        
        CreationHelper createHelper = libro.getCreationHelper();
        CellStyle estilo=libro.createCellStyle();
        String formatoString = null;
        
        switch(tipo.toLowerCase()){
                case "fecha" -> formatoString="dd/MM/yyyy";
                case "moneda" ->formatoString="$#,##0.00";
                case "texto"-> formatoString="@";
                case "entero" ->formatoString="0";
                default -> formatoString="General";
        }
        estilo.setDataFormat(createHelper.createDataFormat().getFormat(formatoString));
        return estilo;
    }
    

    
    
    public static void exportarProductos(){
        try {
            //---CONEXION CON LA BBDD-----//
           Connection con;
           Conexion cn = new Conexion();
           PreparedStatement ps;
           ResultSet rs;
           con=cn.getConexion();

           //---------EJECUTAR CONSULTA---------//
           String SQL = "SELECT * FROM productos";
           ps=con.prepareStatement(SQL);
           rs=ps.executeQuery();
           
           //-----------CREAR LIBRO---------//
           Workbook libroExcel = new XSSFWorkbook();
           Sheet hoja = libroExcel.createSheet("Productos");
           int i=0;
           //...........encabezados...........//
           Row encabezados = hoja.createRow(i++);
           encabezados.createCell(0).setCellValue("ID");
           encabezados.createCell(1).setCellValue("Código");
           encabezados.createCell(2).setCellValue("Descripcion");
           encabezados.createCell(3).setCellValue("Proveedor");
           encabezados.createCell(4).setCellValue("Stock");
           encabezados.createCell(5).setCellValue("Precio");
           encabezados.createCell(6).setCellValue("Fecha");
           
           //.............datos.............//
           while(rs.next()){
               Row fila = hoja.createRow(i++);
               //..........insertar datos.........//
               fila.createCell(0).setCellValue(rs.getInt("id"));
               fila.createCell(1).setCellValue(rs.getString("codigo"));
               fila.createCell(2).setCellValue(rs.getString("descripcion"));
               fila.createCell(3).setCellValue(rs.getString("proveedor"));
               fila.createCell(4).setCellValue(rs.getInt("stock"));
               fila.createCell(5).setCellValue(rs.getFloat("precio"));
               fila.createCell(6).setCellValue(rs.getDate("fecha"));
               //...........darle formato.............//
               fila.getCell(0).setCellStyle(formatearCelda(libroExcel,"entero"));
               fila.getCell(1).setCellStyle(formatearCelda(libroExcel,"texto"));
               fila.getCell(2).setCellStyle(formatearCelda(libroExcel,"texto"));
               fila.getCell(3).setCellStyle(formatearCelda(libroExcel,"texto"));
               fila.getCell(4).setCellStyle(formatearCelda(libroExcel,"entero"));
               fila.getCell(5).setCellStyle(formatearCelda(libroExcel,"moneda"));
               fila.getCell(6).setCellStyle(formatearCelda(libroExcel,"fecha"));
               
           }
           
           
           //---------CERRAR CONEXIÓN-------//
           rs.close();ps.close();con.close();

           //-----------GUARDAR ARCHIVO-------------//
           try {
                FileOutputStream archivoExcel = new FileOutputStream("TablaProductos.xlsx");
                libroExcel.write(archivoExcel);
                System.out.println("Archivo creado");
            } 
           catch (IOException e) {
               System.out.println(e.toString());
            }
        
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        
        
        
    }
    
    
    
}
