package com.itsoeh.jbrigido.projectevaluator.config;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.itsoeh.jbrigido.projectevaluator.LoginActivity;
import com.itsoeh.jbrigido.projectevaluator.modelo.Usuario;

public class DBusuario extends Database {
    public DBusuario(@Nullable Context context) {
        super(context);
    }


    public void guardar(Usuario x) {
        SQLiteDatabase database = getWritableDatabase();
        String query = "INSERT INTO usuarios values(?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(query);
        statement.bindNull(1);
        statement.bindString(2, x.getNombre());
        statement.bindString(3, x.getAppa());
        statement.bindString(4, x.getApma());
        statement.bindString(5, x.getContrasena());
        statement.bindString(6, x.getCorreo());
        statement.executeInsert();
        statement.close();
    }

    public String[] buscarContrasenia(String correo) {
        String datos[] = new String[2];

        SQLiteDatabase database = getReadableDatabase();
        String query = "SELECT correo,contrasena  FROM usuarios where correo = (?)";
        Cursor cursor = database.rawQuery(query, new String[]{correo});
        if (cursor.moveToFirst()) {
            datos[0] = cursor.getString(0);
            datos[1] = cursor.getString(1);
        }
        return datos;
    }

    public void UserLogeado(String correo) {
        SQLiteDatabase database = getReadableDatabase();
        String query = "SELECT * FROM usuarios where correo = (?)";
        Cursor cursor = database.rawQuery(query, new String[]{correo});
        if (cursor.moveToFirst()) {
            Usuario x = new Usuario();
            x.setNombre(cursor.getString(1));
            x.setAppa(cursor.getString(2));
            x.setApma(cursor.getString(3));
            x.setContrasena(cursor.getString(4));
            x.setCorreo(cursor.getString(5));
            LoginActivity.usuario = x;
        }
    }
}
