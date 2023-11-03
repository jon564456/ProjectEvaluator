package com.itsoeh.jbrigido.projectevaluator.config;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.itsoeh.jbrigido.projectevaluator.modelo.Evaluador;
import com.itsoeh.jbrigido.projectevaluator.modelo.Proyecto;

import java.util.ArrayList;

public class DBEvaluador extends Database {
    public DBEvaluador(@Nullable Context context) {
        super(context);
    }

    public ArrayList<Evaluador> all() {
        ArrayList<Evaluador> list = new ArrayList<Evaluador>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM evaluadores";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            list.add(
                    new Evaluador(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7)
                    )
            );
        }
        return list;
    }

    public ArrayList<Proyecto> Asignados(int id) {
        ArrayList<Proyecto> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT proyecto FROM evaluaciones WHERE evaluador = ?";
        Cursor cursor = db.rawQuery(query, new String[]{id + ""});
        while (cursor.moveToNext()) {
            Proyecto x = new Proyecto();
            x.setId(cursor.getInt(0));
            list.add(x);
        }
        db.close();
        return list;
    }

    public void asignarProyecto(int proyecto, int evaluador) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "INSERT INTO evaluaciones (proyecto,evaluador) values (" + proyecto + "," + evaluador + ")";
        db.execSQL(query);
    }
}
