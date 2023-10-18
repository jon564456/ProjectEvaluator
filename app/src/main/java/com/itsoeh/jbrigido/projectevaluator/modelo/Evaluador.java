package com.itsoeh.jbrigido.projectevaluator.modelo;

import java.util.ArrayList;

public class Evaluador {
    private int id;
    private String nombre;
    private String appa;
    private String apma;
    private String correo;
    private String especialidad;
    private String grado;
    private String procedencia;
    private ArrayList<Proyecto> proyectos;

    public Evaluador() {
        this.proyectos = new ArrayList<Proyecto>();
    }

    public Evaluador(int id, String nombre, String appa, String apma, String correo, String especialidad, String grado, String procedencia) {
        this.id = id;
        this.nombre = nombre;
        this.appa = appa;
        this.apma = apma;
        this.correo = correo;
        this.especialidad = especialidad;
        this.grado = grado;
        this.procedencia = procedencia;
        this.proyectos = new ArrayList<Proyecto>();
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public ArrayList<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(ArrayList<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
}
