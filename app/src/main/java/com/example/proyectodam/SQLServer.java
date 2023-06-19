package com.example.proyectodam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class SQLServer extends SQLiteOpenHelper {
    private static final String Productos_Table_Create="Create Table productos(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, cantidad INT,tipo TEXT, precio FLOAT)";
    private static final String Facturas_Table_Create="Create Table factura(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, email TEXT, cantidad INT, total FLOAT)";
    private static final String Usuario_Table_Create = "Create Table usuario( id INTEGER PRIMARY KEY AUTOINCREMENT, usuario TEXT, contraseña TEXT, email TEXT)";
    private static final String DbName="NuestraModa.sqlite";
    private static final int DB_version = 2;
    public SQLServer(Context context) {
        super(context, DbName, null,DB_version) ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Productos_Table_Create);
        db.execSQL(Facturas_Table_Create);
        db.execSQL(Usuario_Table_Create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
