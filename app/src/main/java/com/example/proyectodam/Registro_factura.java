package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.GestureDescription;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Registro_factura extends AppCompatActivity {
    private EditText textNombreF, textApellidoF, textEmailF, textCantidadF, textTotalPagar;
    private Button btnRegistroF, btnBorrarF;
    private Spinner spinnerProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_factura);

        textNombreF = findViewById(R.id.textNombreF);
        textApellidoF = findViewById(R.id.textApellidoF);
        textEmailF = findViewById(R.id.textEmailF);
        textCantidadF = findViewById(R.id.textCantidadF);
        textTotalPagar = findViewById(R.id.textTotalPagar);
        spinnerProducto = findViewById(R.id.spinnerProducto);
        btnBorrarF = findViewById(R.id.btnBorrarF);
        btnRegistroF = findViewById(R.id.btnRegistroF);

        SQLServer conexion = new SQLServer(this);
        final SQLiteDatabase db = conexion.getReadableDatabase();
        List<String> nombresProductos = new ArrayList<>();


        Cursor cursor = db.rawQuery("SELECT nombre FROM productos", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombreProducto = cursor.getString(cursor.getColumnIndex("nombre"));
                nombresProductos.add(nombreProducto);
            } while (cursor.moveToNext());
        }

        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresProductos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProducto.setAdapter(adapter);

        btnRegistroF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarFactura();
            }
        });
        btnBorrarF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               borrarDatosF();
            }
        });


    }

    public void registrarFactura() {

        SQLServer conexion = new SQLServer(this);
        final SQLiteDatabase db = conexion.getWritableDatabase();
        try {
            String productoSeleccionado = spinnerProducto.getSelectedItem().toString();
            int cantidadDeseada = Integer.parseInt(textCantidadF.getText().toString());
            String consulta = "SELECT cantidad FROM productos WHERE nombre = ?";
            Cursor cursor = db.rawQuery(consulta, new String[]{productoSeleccionado});
            if (cursor.moveToFirst()) {
                @SuppressLint("Range")
                int cantidadDisponible = cursor.getInt(cursor.getColumnIndex("cantidad"));
                if (cantidadDisponible >= cantidadDeseada) {
                    // Generar la factura
                    try {
                        ContentValues cv = new ContentValues();
                        cv.put("nombre", textNombreF.getText().toString());
                        cv.put("apellido", textApellidoF.getText().toString());
                        cv.put("Email", textEmailF.getText().toString());
                        cv.put("cantidad", textCantidadF.getText().toString());

                        int cantidad = Integer.parseInt(textCantidadF.getText().toString());
                        String productoSelecionado = spinnerProducto.getSelectedItem().toString();
                        Cursor cursor1 = db.rawQuery("SELECT precio FROM productos WHERE nombre = ?", new String[]{productoSelecionado});
                        if (cursor1.moveToFirst()) {
                            @SuppressLint("Range")
                            double precioProducto = cursor1.getDouble(cursor1.getColumnIndex("precio"));
                            // Calcula el total a pagar
                            double totalPagar = precioProducto * cantidad;
                            String TotalPagarS = String.valueOf(totalPagar);

                            cv.put("total", totalPagar);
                            textTotalPagar.setText(TotalPagarS+ "$");
                        }
                        db.insert("factura", null, cv);// Guarda la información en la base de datos
                        cursor1.close();
                    }catch (Exception e){
                        Toast.makeText(this, "No se genero la factura", Toast.LENGTH_SHORT).show();
                    }

                    // Actualizar la cantidad en la tabla "productos"
                    int nuevaCantidad = cantidadDisponible - cantidadDeseada;
                    ContentValues valores = new ContentValues();
                    valores.put("cantidad", nuevaCantidad);
                    db.update("productos", valores, "nombre = ?", new String[]{productoSeleccionado});
                    // Mostrar mensaje de éxito
                    Toast.makeText(getApplicationContext(), "Factura generada y cantidad actualizada.", Toast.LENGTH_SHORT).show();
                } else {
                    // Mostrar mensaje de error: cantidad insuficiente
                    Toast.makeText(getApplicationContext(), "Cantidad insuficiente del producto.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Mostrar mensaje de error: producto no encontrado
                Toast.makeText(getApplicationContext(), "Producto no encontrado.", Toast.LENGTH_SHORT).show();
            }

            // Cerrar el cursor
            cursor.close();
            db.close();

        }catch (Exception e){
            Toast.makeText(this, "Por favor ingrese los datos completos", Toast.LENGTH_SHORT).show();
        }


    }
    private void borrarDatosF(){
        textNombreF.setText("");
        textApellidoF.setText("");
        textEmailF.setText("");
        textCantidadF.setText("");
        textTotalPagar.setText("");

    }
}