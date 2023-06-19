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

public class Reporte_Ventas extends AppCompatActivity {
    private ListView listReporteVenta;
    private EditText textTotalenVentas;
    ArrayList<String> listaInformacion;
    ArrayList<Factura> listaVentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_ventas);
        consultarListaVentas();

        listReporteVenta = findViewById(R.id.listReporteVenta);
        textTotalenVentas = findViewById(R.id.textTotalenVentas);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_factura, listaInformacion);
        listReporteVenta.setAdapter(adapter);
        listReporteVenta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String informacion = "Id: " + listaVentas.get(pos).getId() + "\n";
                informacion += "Nombre: "+ listaVentas.get(pos).getNombre() + "\n";
                informacion += "Email: "+ listaVentas.get(pos).getEmail() + "\n";
                informacion += "Total:"+ listaVentas.get(pos).getTotal()+ "\n";

                Toast.makeText(Reporte_Ventas.this,informacion, Toast.LENGTH_LONG).show();

            }
        });
        consultarTotalVentas();

    }
    public void consultarListaVentas(){
        SQLServer conexion = new SQLServer(this);
        final SQLiteDatabase db = conexion.getReadableDatabase();
        Factura factura = null;
        listaVentas = new ArrayList<Factura>();
        Cursor cursor = db.rawQuery("SELECT id, nombre, email, total FROM factura",null);
        while (cursor.moveToNext()){
            factura = new Factura();
            factura.setId(cursor.getString(0));
            factura.setNombre(cursor.getString(1));
            factura.setEmail(cursor.getString(2));
            factura.setTotal(cursor.getString(3));
            listaVentas.add(factura);

        }
        obtenerLista();

    }
    public void obtenerLista(){
        listaInformacion = new ArrayList<String>();

        for (int i=0; i<listaVentas.size(); i++){
            listaInformacion.add("| ID: "+listaVentas.get(i).getId()+
                    "\n| Nombre: " + listaVentas.get(i).getNombre() +
                    "\n| Email: " + listaVentas.get(i).getEmail() +
                    "\n| Total a pagar: " + listaVentas.get(i).getTotal() +"$"+
                    "\n")
            ;
        }
    }
    public void consultarTotalVentas(){
        SQLServer conexion = new SQLServer(this);
        final SQLiteDatabase db = conexion.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT SUM(total) FROM factura", null);
            if (cursor.moveToFirst()) {
                float totalVentas = cursor.getFloat(0);
                // Aquí puedes usar el valor totalVentas como desees, como establecerlo en el EditText
                textTotalenVentas.setText(String.valueOf(totalVentas)+"$");
            }
            cursor.close();
            db.close();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "No se pudo obtener el total", Toast.LENGTH_SHORT).show();
        }

    }
}