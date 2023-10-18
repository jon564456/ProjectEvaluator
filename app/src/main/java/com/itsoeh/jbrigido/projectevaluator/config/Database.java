package com.itsoeh.jbrigido.projectevaluator.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {


    private static String DATABASE_NAME = "ProjectEvaluator";
    private static String TABLE_USUARIO = "usuarios";
    private static String TABLE_PROYECTOS = "proyectos";
    private static String TABLE_INTEGRANTES = "integrantes";
    private static String TABLE_EVALUACIONES = "evaluaciones";
    private static String TABLE_EVALUADORES = "evaluadores";
    private static String TABLE_EQUIPOS = "equipos";

    public static int DATABASE_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sql) {

        String query = "CREATE TABLE " + TABLE_USUARIO + "(" +
                "id INTEGER PRIMARY KEY  AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "ape_pa TEXT NOT NULL," +
                "ape_ma TEXT ," +
                "contrasena TEXT NOT NULL," +
                "correo TEXT NOT NULL UNIQUE" +
                ");";

        sql.execSQL(query);
        query = "CREATE TABLE " + TABLE_EVALUADORES + "(" +
                "id INTEGER PRIMARY KEY  AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "ape_pa TEXT NOT NULL," +
                "ape_ma TEXT ," +
                "correo TEXT NOT NULL UNIQUE," +
                "especialidad TEXT NOT NULL," +
                "grado TEXT NOT NULL," +
                "procedencia TEXT NOT NULL" +
                ");";
        sql.execSQL(query);
        query = "CREATE TABLE " + TABLE_PROYECTOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "categoria TEXT NOT NULL," +
                "descripcion TEXT NOT NULL," +
                "grado TEXT NOT NULL," +
                "grupo TEXT NOT NULL," +
                "status INTEGER" +
                ");";
        sql.execSQL(query);
        query = "CREATE TABLE " + TABLE_INTEGRANTES + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "matricula INTEGER NOT NULL," +
                "nombre TEXT NOT NULL," +
                "ape_pa TEXT NOT NULL," +
                "ape_ma TEXT," +
                "correo TEXT UNIQUE NOT NULL," +
                "lider INTEGER," +
                "FOREIGN KEY (Lider) REFERENCES " + TABLE_INTEGRANTES +
                "(id));";
        sql.execSQL(query);
        query = "CREATE TABLE " + TABLE_EVALUACIONES + "(" +
                "evaluador INTEGER," +
                "proyecto  INTEGER," +
                "calificacion REAL," +
                "fecha TEXT," +
                "FOREIGN KEY (evaluador) REFERENCES " + TABLE_EVALUADORES +
                "(id)," +
                "FOREIGN KEY (proyecto) REFERENCES " + TABLE_PROYECTOS + "(id) " +
                ");";
        sql.execSQL(query);
        query = "CREATE TABLE " + TABLE_EQUIPOS + "(" +
                "proyecto INTEGER," +
                "integrante INTEGER," +
                "FOREIGN KEY (proyecto) REFERENCES " + TABLE_PROYECTOS +
                "(id)," +
                "FOREIGN KEY (integrante) REFERENCES " + TABLE_INTEGRANTES + "(id) " +
                ");";
        sql.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sql, int i, int i1) {

    }
}
