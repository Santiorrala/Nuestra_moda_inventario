package com.example.proyectodam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Pagina_reportes extends AppCompatActivity {
    private Button btnReporteIn, btnReporteVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_reportes);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        btnReporteIn = findViewById(R.id.btnReporteIn);
        btnReporteVe = findViewById(R.id.btnReporteVe);

        btnReporteIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarReporteInventario();
            }
        });
        btnReporteVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarReporteVentas();
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
    public void iniciarReporteInventario(){
        Intent intenInventario = new Intent(this, Reporte_Inventario.class);
        startActivity(intenInventario);
    }
    public void iniciarReporteVentas(){
        Intent intentVentas = new Intent(this, Reporte_Ventas.class);
        startActivity(intentVentas);
    }
}