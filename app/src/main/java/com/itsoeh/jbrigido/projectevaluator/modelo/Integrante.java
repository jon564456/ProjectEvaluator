package com.itsoeh.jbrigido.projectevaluator.modelo;

public class Integrante {
    private int id;
    private String nombre;
    private String appa;
    private String apma;
    private int matricula;
    private String correo;

    public Integrante() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integrante(int id, String nombre, String appa, String apma, int matricula) {
        this.id = id;
        this.nombre = nombre;
        this.appa = appa;
        this.apma = apma;
        this.matricula = matricula;

    }
}
