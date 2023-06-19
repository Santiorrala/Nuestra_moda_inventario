package com.example.proyectodam;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Agregar_usuario extends AppCompatActivity {
    private EditText editTextUsuario, editTextContraseña, editTextCorreo;
    private Button botonAgregar, botonCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextContraseña = findViewById(R.id.editTextContraseña);
        editTextCorreo = findViewById(R.id.editTextCorreo);

        botonAgregar = findViewById(R.id.botonAgregar);
        botonCancelar = findViewById(R.id.botonCancelar);

        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarUsuario();

            }
        });

        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Agregar_usuario.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void agregarUsuario(){
        SQLServer conexion = new SQLServer(this);
        final SQLiteDatabase db = conexion.getWritableDatabase();

        String usuario = editTextUsuario.getText().toString();
        String contraseña = editTextContraseña.getText().toString();
        String correo = editTextCorreo.getText().toString();

        if (!usuario.isEmpty() && !contraseña.isEmpty() && !correo.isEmpty()){ //Validacion para agregar usuario
            ContentValues values = new ContentValues();
            values.put("usuario", usuario);
            values.put("contraseña", contraseña);
            values.put("email", correo);

            db.insert("usuario", null, values);
            Toast.makeText(this, " Se ha registrado el usuario con exito", Toast.LENGTH_SHORT).show();

            editTextUsuario.setText("");
            editTextCorreo.setText("");
            editTextContraseña.setText("");
            db.close();
        }
        else {
            Toast.makeText(this, "Por favor ingrese los datos completos", Toast.LENGTH_SHORT).show();
        }
    }
}