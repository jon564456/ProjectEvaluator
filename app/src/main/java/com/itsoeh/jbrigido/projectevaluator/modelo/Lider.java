package com.itsoeh.jbrigido.projectevaluator.modelo;

public class Lider extends Integrante {
    private String rol;

    public Lider() {
    }

    public Lider(int id, String nombre, String appa, String apma, int matricula, String rol) {
        super(id, nombre, appa, apma, matricula);
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
