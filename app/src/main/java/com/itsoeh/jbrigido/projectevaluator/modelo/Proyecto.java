package com.itsoeh.jbrigido.projectevaluator.modelo;

import java.util.ArrayList;

public class Proyecto {

    private int id;
    private String nombre;
    private String clave;
    private String categoria;
    private String descripcion;
    private int grado;
    private char grupo;
    private String status;
    private ArrayList<Evaluador> evaluadores;
    private ArrayList<Integrante> integrantes;
    private double calificacion;

    public Proyecto() {
        this.evaluadores = new ArrayList<Evaluador>();
    }

    public Proyecto(int id, String nombre, String clave, String categoria, String descripcion, int grado, char grupo, String status, double calificacion) {
        this.id = id;
        this.nombre = nombre;
        this.clave = clave;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.grado = grado;
        this.grupo = grupo;
        this.status = status;
        this.calificacion = calificacion;
        this.evaluadores = new ArrayList<Evaluador>();
        this.integrantes = new ArrayList<Integrante>();
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }

    public char getGrupo() {
        return grupo;
    }

    public void setGrupo(char grupo) {
        this.grupo = grupo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Evaluador> getEvaluadores() {
        return evaluadores;
    }

    public void setEvaluadores(ArrayList<Evaluador> evaluadores) {
        this.evaluadores = evaluadores;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public ArrayList<Integrante> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(ArrayList<Integrante> integrantes) {
        this.integrantes = integrantes;
    }
}
