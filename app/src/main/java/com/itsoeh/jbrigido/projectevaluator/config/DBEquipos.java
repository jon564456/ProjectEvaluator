package com.itsoeh.jbrigido.projectevaluator.config;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.itsoeh.jbrigido.projectevaluator.modelo.Equipo;
import com.itsoeh.jbrigido.projectevaluator.modelo.Integrante;
import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;

import java.util.ArrayList;

public class DBEquipos extends Database {
    public DBEquipos(@Nullable Context context) {
        super(context);
    }

    public ArrayList<Equipo> all() {

        ArrayList<Equipo> teams = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT distinct pid,pnombre,pclave,pcat, pdes,pgrado,pgrupo,pstatus  from vista_equipos";

        Cursor reg = db.rawQuery(query, null);

        while (reg.moveToNext()) {
            Equipo temp = new Equipo();
            Proyecto proyecto = new Proyecto();
            proyecto.setId(reg.getInt(0));
            proyecto.setNombre(reg.getString(1));
            proyecto.setClave(reg.getString(2));
            proyecto.setCategoria(reg.getString(3));
            proyecto.setDescripcion(reg.getString(4));
            proyecto.setGrado(reg.getInt(5));
            proyecto.setGrupo(reg.getString(6));
            proyecto.setStatus(reg.getString(7));
            temp.setProyecto(proyecto);
            temp.setIntegrantes(listMembers(proyecto.getId()));
            teams.add(temp);
        }
        db.close();
        return teams;
    }

   public ArrayList<Integrante> listMembers(int id) {
        ArrayList<Integrante> x = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT inid, imatri,inombre,iappa,iappm,icorreo, lider FROM vista_equipos WHERE pid = ?;";
        Cursor reg = db.rawQuery(query, new String[]{id + ""});

        while (reg.moveToNext()) {
            Integrante temp = new Integrante();
            temp.setId(reg.getInt(0));
           // temp.setMatricula(reg.getString(1));
            temp.setNombre(reg.getString(2));
            temp.setAppa(reg.getString(3));
            temp.setApma(reg.getString(4));
            temp.setCorreo(reg.getString(5));
            x.add(temp);
        }
        return x;
    }

    public ArrayList<Equipo> listQualification() {
        ArrayList<Equipo> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT distinct proyecto,avg(calificacion) FROM evaluaciones group by proyecto; ";
        Cursor reg = db.rawQuery(query, null);
        while (reg.moveToNext()) {
            Equipo temp = new Equipo();
            temp.getProyecto().setId(reg.getInt(0));
            temp.getProyecto().setCalificacion(reg.getInt(1));
            temp.setIntegrantes(listMembers(reg.getInt(0)));
            list.add(temp);
        }
        return list;
    }
}
