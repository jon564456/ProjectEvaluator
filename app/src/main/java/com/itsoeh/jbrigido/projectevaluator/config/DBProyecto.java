package com.itsoeh.jbrigido.projectevaluator.config;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;

import java.util.ArrayList;

public class DBProyecto extends Database {
    public DBProyecto(@Nullable Context context) {
        super(context);
    }

    public ArrayList<Proyecto> available() {
        ArrayList<Proyecto> temp = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT p FROM vista_disponibles";
        Cursor reg = db.rawQuery(sql, null);
        while (reg.moveToNext()) {
            Proyecto x = consult(reg.getInt(0));
            temp.add(x);
        }
        db.close();
        return temp;
    }

    public Proyecto consult(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Proyecto x = null;
        String sql = "SELECT * FROM proyectos WHERE id = ?";
        Cursor reg = db.rawQuery(sql, new String[]{id + ""});
        if (reg.moveToFirst()) {
            x = new Proyecto();
            x.setId(reg.getInt(0));
            x.setNombre(reg.getString(1));
            x.setClave(reg.getString(2));
            x.setCategoria(reg.getString(3));
            x.setDescripcion(reg.getString(4));
            x.setGrado(reg.getInt(5));
            x.setGrupo(reg.getString(6));
        }
        return x;
    }
}
