package com.example.proyectodam;

public class Producto {
    public String id, nombre, cantidad, tipo, precio;

    public Producto(){

    }
    public Producto(String id, String nombre, String cantidad, String tipo, String precio){
    this.id = id;
    this.nombre = nombre;
    this.cantidad = cantidad;
    this.tipo = tipo;
    this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


}
