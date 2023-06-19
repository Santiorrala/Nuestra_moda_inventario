package com.example.proyectodam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

public class Buscar_producto extends AppCompatActivity {
    private EditText textIDP, textNombreBuscarP, textCantidadBuscarP,  textPrecioBuscarP;
    private Button btnBuscarPro, btnActualizarP, btnEliminarP;
    private Spinner spinnerTipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_producto);

        textIDP = findViewById(R.id.txtIDP);
        textNombreBuscarP = findViewById(R.id.textNombreBuscarP);
        textCantidadBuscarP = findViewById(R.id.textCantidadBuscarP);
        textPrecioBuscarP = findViewById(R.id.textPrecioBuscarP);
        spinnerTipo = findViewById(R.id.spinnerTipo);
        btnBuscarPro = findViewById(R.id.btnBuscarPro);
        btnActualizarP = findViewById(R.id.btnActualizarP);
        btnEliminarP = findViewById(R.id.btnEliminarP);

        btnBuscarPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarProducto(v);
            }
        });
        btnActualizarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarProducto();
            }
        });
        btnEliminarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("¿Estás seguro de eliminar este producto?");
                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Acciones a realizar si el usuario confirma la eliminación
                        eliminarProducto();
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
    @SuppressLint("Range")
    public void buscarProducto(View v){
        SQLServer conexion = new SQLServer(this);
        final SQLiteDatabase db = conexion.getReadableDatabase();
        String tipovalue = null;
        try {
            if (db !=null){
                int id = Integer.parseInt(textIDP.getText().toString());
                Cursor c = db.rawQuery("SELECT * FROM productos WHERE id = "+ id, null);
                if (c != null){
                    c.moveToFirst();
                    textNombreBuscarP.setText(c.getString(c.getColumnIndex("nombre")).toString());
                    textCantidadBuscarP.setText(c.getString(c.getColumnIndex("cantidad")).toString());
                    //textTipoBuscarP.setText(c.getString(c.getColumnIndex("tipo")).toString());
                    tipovalue = c.getString(c.getColumnIndex("tipo")).toString();
                    textPrecioBuscarP.setText(c.getString(c.getColumnIndex("precio")).toString());
                }
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.tipo_productos, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTipo.setAdapter(adapter);
                spinnerTipo.setSelection(adapter.getPosition(tipovalue));
                c.close();
                db.close();
            }

        }catch (Exception e){
            Toast.makeText(this, "Error al buscar el producto, por favor" +
                    " ingresar correctamenet los datos", Toast.LENGTH_SHORT).show();
        }


    }
    public void actualizarProducto(){
        try {
        int id = Integer.parseInt(textIDP.getText().toString());
        String nombreActualizado = textNombreBuscarP.getText().toString();
        String cantidadActualizada = textCantidadBuscarP.getText().toString();
        String tipoActualizado = spinnerTipo.getSelectedItem().toString();
        String precioActualizado = textPrecioBuscarP.getText().toString();

        ContentValues valoresActualizados = new ContentValues();
        valoresActualizados.put("nombre", nombreActualizado);
        valoresActualizados.put("cantidad", cantidadActualizada);
        valoresActualizados.put("tipo", tipoActualizado);
        valoresActualizados.put("precio", precioActualizado);

        SQLServer conexion = new SQLServer(this);
        final SQLiteDatabase db = conexion.getWritableDatabase();
                String tabla = "productos";
                String columnaId = "id";
                String whereClause = columnaId + " = ?";
                String[] whereArgs = {String.valueOf(id)};
                int filasActualizadas = db.update(tabla, valoresActualizados, whereClause, whereArgs);
                if (filasActualizadas>=1){
                    Toast.makeText(this,"Se actualizo el Producto Correctamente",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this,"Error al actualizar el producto", Toast.LENGTH_LONG).show();
                }

        }catch (Exception e){
            Toast.makeText(this,"Error al actualizar el producto", Toast.LENGTH_LONG).show();

        }
    }
    public void eliminarProducto(){
        try {
            int id = Integer.parseInt(textIDP.getText().toString());
            SQLServer conexion = new SQLServer(this);
            final SQLiteDatabase db = conexion.getWritableDatabase();

            String table = "productos";
            String columnaId = "id";
            String whereClause = columnaId + " = ?";
            String[] whereArgs = {String.valueOf(id)};
            int filasAfectadas = db.delete(table, whereClause,whereArgs);
            if (filasAfectadas>=1){
                Toast.makeText(this,"Se elimino el producto", Toast.LENGTH_SHORT).show();
                textIDP.setText("");
                textNombreBuscarP.setText("");
                textCantidadBuscarP.setText("");
                spinnerTipo.setSelection(0);
                textPrecioBuscarP.setText("");
            }

        }catch (Exception e){
            Toast.makeText(this,"Error al eliminar producto, ingrese datos creectamente",
                    Toast.LENGTH_SHORT).show();
        }
    }

}