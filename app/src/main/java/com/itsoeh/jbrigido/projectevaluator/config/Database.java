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
                "correo TEXT NOT NULL UNIQUE," +
                "verificado TEXT" +
                ");";
        sql.execSQL(query);
        //      query = "INSERT INTO usuarios values(null,'Jonathan','Brigido','Perez','12345678','19011188@itsoeh.edu.mx','verficado')";
        //      sql.execSQL(query);
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
        query = "INSERT INTO evaluadores values(null,'Mario','Perez','Bautista','Mbautista@itsoeh.edu.mx','Desarrollo movil', 'Ingeniero','ITSOEH')";
        sql.execSQL(query);
        query = "INSERT INTO evaluadores values(null,'Aline','Perez','Martinez','AperezMartinez@itsoeh.edu.mx','Desarrollo movil', 'Maestra','ITSOEH')";
        sql.execSQL(query);
        query = "INSERT INTO evaluadores values(null,'Cristy Elizabeth','Aguilar','Ojeda','Caguilar@itsoeh.edu.mx','Telecomunicaciones', 'Maestra','ITSOEH')";
        sql.execSQL(query);
        query = "CREATE TABLE " + TABLE_PROYECTOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "clave TEXT , " +
                "categoria TEXT NOT NULL," +
                "descripcion TEXT NOT NULL," +
                "grado TEXT NOT NULL," +
                "grupo TEXT NOT NULL," +
                "status INTEGER" +
                ");";
        sql.execSQL(query);
        query = "INSERT INTO proyectos values(1,'Exposic','INT70A','Integrador','Evalua proyecto','7','A', 'Pendiente')";
        sql.execSQL(query);
        query = "INSERT INTO proyectos values(2,'Tienda virtual','INT50A','Integrador','Realiza compras en una tienda virtual','5','A', 'Pendiente')";
        sql.execSQL(query);
        query = "INSERT INTO proyectos values(3,'Juego de gato','MAT50A','Materia','Juega al gato encerrado','1','A', 'Pendiente')";
        sql.execSQL(query);
        query = "INSERT INTO proyectos values(4,'CABLESCTRU','MAT50A','MATERIA','Implementacion de red estructurada','6','A', 'Pendiente')";
        sql.execSQL(query);
        query = "CREATE TABLE " + TABLE_INTEGRANTES + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "matricula INTEGER NOT NULL," +
                "nombre TEXT NOT NULL," +
                "ape_pa TEXT NOT NULL," +
                "ape_ma TEXT," +
                "correo TEXT NOT NULL" +
                " );";
        sql.execSQL(query);

        query = "INSERT INTO integrantes values(null, '20011888','Jaziel','Brigido','Sanchez','20011888@itsoeh.edu.mx')";
        sql.execSQL(query);
        query = "INSERT INTO integrantes values(null, '20011898','Jordan','Migueles','Lopez','20011898@itsoeh.edu.mx')";
        sql.execSQL(query);
        query = "INSERT INTO integrantes values(null, '20011800','María Guadalupe','Lopez','Serrano','20011800@itsoeh.edu.mx')";
        sql.execSQL(query);
        query = "INSERT INTO integrantes values(null, '20011102','Edwin','Yañez','Zuñiga','20011102@itsoeh.edu.mx')";
        sql.execSQL(query);
        query = "INSERT INTO integrantes values(null, '20011001','Alberto','Fernandez','Sanchez','20011001@itsoeh.edu.mx')";
        sql.execSQL(query);
        query = "INSERT INTO integrantes values(null, '20019010','Luis','Dominguez','Lopez','20019010itsoeh.edu.mx')";
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
                "integranteLider INTEGER," +
                "integrante INTEGER," +
                "FOREIGN KEY (proyecto) REFERENCES " + TABLE_PROYECTOS +
                "(id)," +
                "FOREIGN KEY (integrante) REFERENCES " + TABLE_INTEGRANTES + "(id), " +
                "FOREIGN KEY (integranteLider) REFERENCES " + TABLE_INTEGRANTES + "(id) " +
                ");"
        ;
        sql.execSQL(query);
        query = "INSERT INTO equipos values(1,1,1)";
        sql.execSQL(query);
        query = "INSERT INTO equipos values(1,1,2)";
        sql.execSQL(query);
        query = "INSERT INTO equipos values(2,3,2)";
        sql.execSQL(query);
        query = "INSERT INTO equipos values(2,3,4)";
        sql.execSQL(query);
        query = "INSERT INTO equipos values(3,5,2)";
        sql.execSQL(query);
        query = "INSERT INTO equipos values(3,5,6)";
        sql.execSQL(query);

        query = "CREATE VIEW vista_disponibles AS" +
                "    SELECT " +
                "        p.id AS p,\n" +
                "        COUNT(e.proyecto) AS asignado" +
                "    FROM" +
                "        proyectos p " +
                "        LEFT JOIN evaluaciones e ON p.id = e.proyecto" +
                "    GROUP BY p.id" +
                "    HAVING (asignado < 3)";
        sql.execSQL(query);
        query = "CREATE VIEW vista_equipos AS " +
                "SELECT p.id AS pid," +
                "    p.nombre AS pnombre," +
                "    p.clave AS pclave," +
                "    p.categoria AS pcat," +
                "    p.descripcion AS pdes," +
                "    p.grado AS pgrado," +
                "    p.grupo AS pgrupo," +
                "    p.status AS pstatus," +
                "    i.id AS inid," +
                "    i.matricula AS imatri," +
                "    i.nombre AS inombre," +
                "    i.ape_pa AS iappa," +
                "    i.ape_ma AS iappm," +
                "    i.correo AS icorreo," +
                "    IFNULL(e.integranteLider, i.id) AS lider " +
                "FROM proyectos p LEFT JOIN equipos e ON p.id = e.proyecto" +
                " LEFT JOIN integrantes i ON i.id = e.integrante;";
        sql.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sql, int i, int i1) {

    }
}
