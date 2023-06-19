package com.example.proyectodam;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registro_producto extends AppCompatActivity {
    private EditText textNombreP, textPreciop, textCantidadP;
    private Spinner spinnerTipoP;
    private Button btnRegistrarP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_producto);

        textNombreP = findViewById(R.id.textNombreP);
        textCantidadP = findViewById(R.id.textCantidadP);
        textPreciop = findViewById(R.id.textPrecioP);
        spinnerTipoP = findViewById(R.id.spinnerTipoP);
        btnRegistrarP = findViewById(R.id.btnRegistrarP);

        btnRegistrarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarProducto(v);
            }
        });
    }
    public void registrarProducto(View v) {
        String spinnerSelecionadoTipo = spinnerTipoP.getSelectedItem().toString();
        String nombreProducto = textNombreP.getText().toString();
        String cantidadProducto = textCantidadP.getText().toString();
        String precioProducto = textPreciop.getText().toString();

        SQLServer conexion = new SQLServer(v.getContext());
        final SQLiteDatabase db = conexion.getWritableDatabase();

        if (nombreProducto.isEmpty() || cantidadProducto.isEmpty() || spinnerSelecionadoTipo.isEmpty() || precioProducto.isEmpty()) {
            Toast.makeText(this,"Ingrese los datos correctamente", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues cv = new ContentValues();
            cv.put("nombre", nombreProducto);
            cv.put("cantidad", cantidadProducto);
            cv.put("tipo", spinnerSelecionadoTipo);
            cv.put("precio", precioProducto);

            db.insert("productos", null, cv);
            Toast.makeText(this, " Se ha registrado el producto con exito", Toast.LENGTH_SHORT).show();

            textNombreP.setText("");
            textCantidadP.setText("");
            spinnerTipoP.setSelection(0);
            textPreciop.setText("");
            db.close();
        }
    }



}