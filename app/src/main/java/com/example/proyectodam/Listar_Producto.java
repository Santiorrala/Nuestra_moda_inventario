package com.example.proyectodam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Listar_Producto extends AppCompatActivity {
    private ListView listviewProductos;
    ArrayList<String> listaInformacion;
    ArrayList<Producto> listaProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_producto);
        listviewProductos = findViewById(R.id.listViewProductos);

        consultarListaProductos();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_factura, listaInformacion);
        listviewProductos.setAdapter(adapter);

        listviewProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String informacion = "id: " + listaProducto.get(pos).getId() + "\n";
                informacion += "nombre: "+ listaProducto.get(pos).getNombre() + "\n";
                informacion += "cantidad: "+ listaProducto.get(pos).getCantidad() + "\n";
                informacion += "tipo: "+ listaProducto.get(pos).getTipo() + "\n";
                informacion += "precio"+ listaProducto.get(pos).getPrecio()+"\n";

                Toast.makeText(Listar_Producto.this,informacion, Toast.LENGTH_LONG).show();

            }
        });


    }
    public void consultarListaProductos(){
        SQLServer conexion = new SQLServer(this);
        final SQLiteDatabase db = conexion.getReadableDatabase();
        Producto producto = null;
        listaProducto = new ArrayList<Producto>();
        Cursor cursor = db.rawQuery("SELECT * FROM productos",null);
         while (cursor.moveToNext()){
            producto = new Producto();
             producto.setId(cursor.getString(0));
             producto.setNombre(cursor.getString(1));
             producto.setCantidad(cursor.getString(2));
             producto.setTipo(cursor.getString(3));
             producto.setPrecio(cursor.getString(4));
             listaProducto.add(producto);

         }
         obtenerLista();

    }
    public void obtenerLista(){
        listaInformacion = new ArrayList<String>();

        for (int i=0; i<listaProducto.size(); i++){
            listaInformacion.add("| ID: "+listaProducto.get(i).getId()+
                    "\n| Nombre: " + listaProducto.get(i).getNombre() +
                    "\n| Cantidad: " + listaProducto.get(i).getCantidad() +
                    "\n| Tipo: " + listaProducto.get(i).getTipo() +
                    "\n| Precio: "+ listaProducto.get(i).getPrecio()+"$"+"\n")
            ;
        }



    }
}