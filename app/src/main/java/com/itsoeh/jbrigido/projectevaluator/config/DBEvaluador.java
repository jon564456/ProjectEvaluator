package com.itsoeh.jbrigido.projectevaluator.config;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.itsoeh.jbrigido.projectevaluator.modelo.Evaluador;

import java.util.ArrayList;

public class DBEvaluador extends Database {
    public DBEvaluador(@Nullable Context context) {
        super(context);
    }

    public ArrayList<Evaluador> evaluadores() {
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
}
