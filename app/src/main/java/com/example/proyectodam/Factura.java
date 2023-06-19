package com.example.proyectodam;

public class Factura {

    public String id, nombre, apellido,  email, cantidad, total;

    public Factura(){

    }
    public Factura(String id, String nombre, String apellido, String email, String cantidad, String total){
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.cantidad = cantidad;
    this.total = total;


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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTotal(){return total;}

    public void setTotal(String total){this.total = total;}




}
