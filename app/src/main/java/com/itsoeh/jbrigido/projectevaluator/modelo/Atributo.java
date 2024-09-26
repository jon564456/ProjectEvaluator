package com.itsoeh.jbrigido.projectevaluator.modelo;

import androidx.annotation.NonNull;

public class Atributo {

    private int id;
    private String  responsable;
    private  String descripcion;
    private int puntuacion;
    private CategoriaProyecto categoriaProyecto;

    public Atributo(int id, String responsable, String descripcion, int puntuacion, CategoriaProyecto categoriaProyecto) {
        this.id = id;
        this.responsable = responsable;
        this.descripcion = descripcion;
        this.puntuacion = puntuacion;
        this.categoriaProyecto = categoriaProyecto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public CategoriaProyecto getCategoriaProyecto() {
        return categoriaProyecto;
    }

    public void setCategoriaProyecto(CategoriaProyecto categoriaProyecto) {
        this.categoriaProyecto = categoriaProyecto;
    }

}
