package com.itsoeh.jbrigido.projectevaluator.modelo;

public class Usuario {

    private String nombre;
    private String appa;
    private String apma;
    private String contrasena;
    private String correo;

    public Usuario() {
    }

    public Usuario(String nombre, String appa, String apma, String contrasena, String correo) {
        this.nombre = nombre;
        this.appa = appa;
        this.apma = apma;
        this.contrasena = contrasena;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAppa() {
        return appa;
    }

    public void setAppa(String appa) {
        this.appa = appa;
    }

    public String getApma() {
        return apma;
    }

    public void setApma(String apma) {
        this.apma = apma;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}