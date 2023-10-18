package com.itsoeh.jbrigido.projectevaluator.modelo;

import java.util.ArrayList;

public class Equipos {
    private Proyecto proyecto;
    private ArrayList<Integrante> integrantes;

    public Equipos() {
    }

    public Equipos(Proyecto proyecto, ArrayList<Integrante> integrantes) {
        this.proyecto = proyecto;
        this.integrantes = integrantes;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public ArrayList<Integrante> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(ArrayList<Integrante> integrantes) {
        this.integrantes = integrantes;
    }
}
