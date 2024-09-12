package com.itsoeh.jbrigido.projectevaluator.modelo;

import java.util.ArrayList;

public class Evaluador {
    private int id;
    private  String username;
    private String nombre;
    private String apellidos;
    private String correo;
    private String especialidad;
    private String grado;
    private String procedencia;
    private ArrayList<Proyecto> proyectos;

    public Evaluador(){

    }

    public Evaluador(int id, String username, String nombre, String apellidos, String correo, String especialidad, String grado, String procedencia, ArrayList<Proyecto> proyectos) {
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.especialidad = especialidad;
        this.grado = grado;
        this.procedencia = procedencia;
        this.proyectos = proyectos;
    }

    public ArrayList<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(ArrayList<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
