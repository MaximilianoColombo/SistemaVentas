/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.List;

/**
 *
 * @author colom
 */
public class Detalle {
    private int id;
    private String codigoProducto;
    private String descripcion;
    private int cantidad;
    private float precio;
    private int idVenta;

    public Detalle() {
    }

    public Detalle(int id, String codigoProducto, String descripcion, int cantidad, float precio, int idVenta) {
        this.id = id;
        this.codigoProducto = codigoProducto;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.idVenta = idVenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }
    
    public boolean comprobarCodigoRepetido(String codigo,List<Detalle> lista){
        for (Detalle detalle : lista){
            if(detalle.getCodigoProducto().equals(codigo)){
                return true;
            }
        
        }return false;
    }
    
    public float sumarTotalVenta(List<Detalle> lista){
        float sumatoria=0;
        for (Detalle detalle : lista){
            float total = detalle.getPrecio()*detalle.getCantidad();
            sumatoria=sumatoria+total;
        }return sumatoria;
    } 
}
