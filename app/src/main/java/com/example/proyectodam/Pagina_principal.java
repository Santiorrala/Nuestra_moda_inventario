package com.example.proyectodam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Pagina_principal extends AppCompatActivity {
    private View cardProductos, cardFacturas, cardReportes, cardCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        String tituloToolbar = "Bienvenido " + nombreUsuario;

        getSupportActionBar().setTitle(tituloToolbar);

        cardProductos = findViewById(R.id.cardProductos);
        cardFacturas = findViewById(R.id.cardFacturas);
        cardReportes = findViewById(R.id.cardReportes);
        cardCerrarSesion = findViewById(R.id.cardCerrarSesion);

        cardProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarPaginaProductos();
            }
        });
        cardFacturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarPaginaFacturas();
            }
        });
        cardReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarPaginaReportes();
            }
        });
        cardCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoCerrarSesion();
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
            mostrarDialogoCerrarSesion();
        }
        return super.onOptionsItemSelected(item);
    }

    public void mostrarDialogoCerrarSesion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Estás seguro que deseas cerrar sesión?");
        builder.setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                cerrarSesion();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void cerrarSesion() {
        Intent intentCerrarSesion = new Intent(this, MainActivity.class);
        intentCerrarSesion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentCerrarSesion);
        finish();
    }

    public void iniciarPaginaProductos() {
        Intent intentPaginaProductos = new Intent(Pagina_principal.this, Pagina_productos.class);
        startActivity(intentPaginaProductos);
    }

    public void iniciarPaginaFacturas() {
        Intent intentPaginaFacturas = new Intent(Pagina_principal.this, Pagina_facturas.class);
        startActivity(intentPaginaFacturas);
    }

    public void iniciarPaginaReportes() {
        Intent intentPaginaReportes = new Intent(Pagina_principal.this, Pagina_reportes.class);
        startActivity(intentPaginaReportes);
    }
}
