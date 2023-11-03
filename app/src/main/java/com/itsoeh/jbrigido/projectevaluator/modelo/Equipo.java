package com.itsoeh.jbrigido.projectevaluator.modelo;

import java.util.ArrayList;

public class Equipo {
    private ArrayList<Integrante> integrantes;
    private Proyecto proyecto;

    public Equipo() {
        this.integrantes = new ArrayList<>();
        this.proyecto = new Proyecto();
    }

    public ArrayList<Integrante> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(ArrayList<Integrante> integrantes) {
        this.integrantes = integrantes;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
}
