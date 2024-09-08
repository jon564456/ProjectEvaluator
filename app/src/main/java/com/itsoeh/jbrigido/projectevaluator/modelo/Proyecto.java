package com.itsoeh.jbrigido.projectevaluator.modelo;

import android.media.audiofx.Equalizer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Proyecto {

    private int id;
    private String nombre;
    private String clave;
    private String categoria;
    private String descripcion;
    private int grado;
    private String grupo;
    private String status;
    private ArrayList<Evaluador> evaluadores;
    private int calificacion;

    public Proyecto() {
        this.evaluadores = new ArrayList<Evaluador>();
    }

    public Proyecto(int id, String nombre, String clave, String categoria, String descripcion, int grado, String grupo, String status, int calificacion) {
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

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
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

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())return false;
        Proyecto proyecto = (Proyecto) obj;
        return proyecto.id == id;
    }


    @NonNull
    @Override
    public String toString() {
        return this.nombre;
    }
}
