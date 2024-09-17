package com.itsoeh.jbrigido.projectevaluator.modelo;

import androidx.annotation.NonNull;

public class CategoriaProyecto {

    private int id;
    private String descripcion;

    public CategoriaProyecto() {
    }

    public CategoriaProyecto(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @NonNull
    @Override
    public String toString() {
        return getDescripcion();
    }
}
