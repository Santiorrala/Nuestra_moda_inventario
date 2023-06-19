package com.example.proyectodam;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Pagina_facturas extends AppCompatActivity {
    private Button btnRegistroF, btnListarF;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_facturas);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        btnRegistroF = findViewById(R.id.btnRegistroF);

        btnListarF = findViewById(R.id.btnListarF);


        btnRegistroF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iniciarRegistroF();
            }

        });

        btnListarF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicarListarF();
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cerrarSesion) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Estás seguro que desea cerrar sesion");
            builder.setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Acciones a realizar si el usuario confirma la eliminación
                    cerrarSecion();
                }
            });
            builder.setNegativeButton("Cancelar", null);
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        return super.onOptionsItemSelected(item);
    }
    public void cerrarSecion(){
        Intent intentcerrarSesion = new Intent(this, MainActivity.class);
        intentcerrarSesion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentcerrarSesion);
        finish();
    }
    public void iniciarRegistroF(){
        Intent intentRegistroF = new Intent(this,Registro_factura.class);
        startActivity(intentRegistroF);
    }

    public void inicarListarF(){
        Intent intentListarf = new Intent(this, Listar_factura.class);
        startActivity(intentListarf);

    }

}