package com.example.proyectodam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Reporte_Inventario extends AppCompatActivity {
    private ListView listReporteInventario;
    private EditText textProductosTotal;
    ArrayList<String> listaInformacion;
    ArrayList<Producto> listaInvenatario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_inventario);
        consultarListaInventario();

        listReporteInventario = findViewById(R.id.listReporteInventario);
        textProductosTotal = findViewById(R.id.textProductosTotal);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_factura, listaInformacion);
        listReporteInventario.setAdapter(adapter);
        listReporteInventario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String informacion = "Id: " + listaInvenatario.get(pos).getId() + "\n";
                informacion += "Nombre: "+ listaInvenatario.get(pos).getNombre() + "\n";
                informacion += "Cantidad: "+ listaInvenatario.get(pos).getCantidad() + "\n";

                Toast.makeText(Reporte_Inventario.this,informacion, Toast.LENGTH_LONG).show();

            }
        });
        consultarTotalProductos();
    }
    public void consultarListaInventario(){
        SQLServer conexion = new SQLServer(this);
        final SQLiteDatabase db = conexion.getReadableDatabase();
        Producto producto = null;
        listaInvenatario = new ArrayList<Producto>();
        Cursor cursor = db.rawQuery("SELECT id, nombre, cantidad FROM productos",null);
        while (cursor.moveToNext()){
            producto = new Producto();
            producto.setId(cursor.getString(0));
            producto.setNombre(cursor.getString(1));
            producto.setCantidad(cursor.getString(2));
            listaInvenatario.add(producto);

        }
        obtenerLista();

    }
    public void obtenerLista(){
        listaInformacion = new ArrayList<String>();

        for (int i=0; i<listaInvenatario.size(); i++){
            listaInformacion.add("| ID: "+listaInvenatario.get(i).getId()+
                    "\n| Nombre: " + listaInvenatario.get(i).getNombre() +
                    "\n| Disponible: " + listaInvenatario.get(i).getCantidad() +
                    "\n")
            ;
        }
    }
    public void consultarTotalProductos(){
        SQLServer conexion = new SQLServer(this);
        final SQLiteDatabase db = conexion.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT SUM(cantidad) FROM productos", null);
            if (cursor.moveToFirst()) {
                int totalVentas = cursor.getInt(0);
                // Aquí puedes usar el valor totalVentas como desees, como establecerlo en el EditText
                textProductosTotal.setText(String.valueOf(totalVentas));
            }
            cursor.close();
            db.close();


        }catch (Exception e){
            Toast.makeText(this, "No se puedo obtener el total", Toast.LENGTH_SHORT).show();
        }
    }

}