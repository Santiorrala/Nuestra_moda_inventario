package com.example.proyectodam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Listar_factura extends AppCompatActivity {
    private ListView listViewFactura;
    ArrayList<String> listaInformacion;
    ArrayList<Factura> listaFactura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_factura);
        listViewFactura = findViewById(R.id.listViewFactura);

        consultarListaProductos();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_factura, listaInformacion);

        listViewFactura.setAdapter(adapter);

        listViewFactura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String informacion = "ID: " + listaFactura.get(pos).getId() + "\n" +
                        "Nombre: " + listaFactura.get(pos).getNombre() + "\n" +
                        "Apellido: " + listaFactura.get(pos).getApellido() + "\n" +
                        "Email: " + listaFactura.get(pos).getEmail() + "\n" +
                        "Cantidad: " + listaFactura.get(pos).getCantidad() + "\n" +
                        "Total: " + listaFactura.get(pos).getTotal() + "\n";
                Toast.makeText(Listar_factura.this, informacion, Toast.LENGTH_LONG).show();
            }
        });

        EditText editTextIdFactura = findViewById(R.id.editTextIdFactura);
        Button btnGenerarPDF = findViewById(R.id.btnGenerarPDF);
        btnGenerarPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idFactura = editTextIdFactura.getText().toString();
                generarPDF(idFactura);
            }
        });
    }

    private void generarPDF(String idFactura) {
        if (idFactura.isEmpty()) {
            Toast.makeText(this, "Ingrese un ID de factura", Toast.LENGTH_SHORT).show();
            return;
        }

        Factura facturaSeleccionada = null;
        for (Factura factura : listaFactura) {
            if (factura.getId().equals(idFactura)) {
                facturaSeleccionada = factura;
                break;
            }
        }

        if (facturaSeleccionada == null) {
            Toast.makeText(this, "No se encontró la factura con el ID proporcionado", Toast.LENGTH_SHORT).show();
            return;
        }

        File directorio = getExternalFilesDir(null);
        String nombreArchivo = facturaSeleccionada.getApellido() + "_" + facturaSeleccionada.getNombre() + ".pdf";
        File archivoPDF = new File(directorio, nombreArchivo);

        try {
            Document documento = new Document(PageSize.A4);
            PdfWriter.getInstance(documento, new FileOutputStream(archivoPDF));
            documento.open();



            Drawable logoDrawable = getResources().getDrawable(R.drawable.tienda);
            Image logo = Image.getInstance(logoDrawableToBytes(logoDrawable));

            // Ajustar escala del logo
            float logoScalePercentage = 20; //
            logo.scalePercent(logoScalePercentage);



            logo.setAlignment(Element.ALIGN_RIGHT); // Alineación a la izquierda
            documento.add(logo);


            // Agregar saltos de línea antes de mostrar la tabla
            for (int i = 0; i < 2; i++) {
                documento.add(new Paragraph("\n"));
            }


            // Título en el centro
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            fontTitulo.setColor(BaseColor.BLACK);
            PdfPCell celdaTitulo = new PdfPCell(new Phrase("NuestraModa", fontTitulo));
            celdaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaTitulo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celdaTitulo.setBackgroundColor(new BaseColor(230, 230, 230));
            celdaTitulo.setPadding(10);
            celdaTitulo.setColspan(2);
            PdfPTable tablaTitulo = new PdfPTable(1);
            tablaTitulo.setWidthPercentage(100);
            tablaTitulo.addCell(celdaTitulo);
            documento.add(tablaTitulo);

            // Crear tabla para la factura
            PdfPTable tablaFactura = new PdfPTable(2);
            tablaFactura.setWidthPercentage(100);
            tablaFactura.setWidths(new float[]{1, 2});

            // Configurar estilo de celda para el encabezado de la tabla
            PdfPCell celdaEncabezado = new PdfPCell();
            celdaEncabezado.setBackgroundColor(new BaseColor(230, 230, 230));
            celdaEncabezado.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaEncabezado.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celdaEncabezado.setPadding(5);
            Font fontEncabezado = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            fontEncabezado.setColor(BaseColor.BLACK);

            // Agregar encabezados de la tabla
            celdaEncabezado.setPhrase(new Phrase("Campo", fontEncabezado));
            tablaFactura.addCell(celdaEncabezado);
            celdaEncabezado.setPhrase(new Phrase("Valor", fontEncabezado));
            tablaFactura.addCell(celdaEncabezado);

            // Agregar filas con los datos de la factura
            tablaFactura.addCell("ID:");
            tablaFactura.addCell(facturaSeleccionada.getId());
            tablaFactura.addCell("Nombre:");
            tablaFactura.addCell(facturaSeleccionada.getNombre());
            tablaFactura.addCell("Apellido:");
            tablaFactura.addCell(facturaSeleccionada.getApellido());
            tablaFactura.addCell("Email:");
            tablaFactura.addCell(facturaSeleccionada.getEmail());
            tablaFactura.addCell("Cantidad:");
            tablaFactura.addCell(facturaSeleccionada.getCantidad());
            tablaFactura.addCell("Total:");
            tablaFactura.addCell(facturaSeleccionada.getTotal() + "$");

            documento.add(tablaFactura);
            documento.close();

            Toast.makeText(this, "PDF generado correctamente", Toast.LENGTH_SHORT).show();
        } catch (DocumentException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al generar el PDF", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al acceder al almacenamiento externo", Toast.LENGTH_SHORT).show();
        }
    }

    public void consultarListaProductos() {
        SQLServer conexion = new SQLServer(this);
        final SQLiteDatabase db = conexion.getReadableDatabase();
        Factura factura;
        listaFactura = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM factura", null);
        while (cursor.moveToNext()) {
            factura = new Factura();
            factura.setId(cursor.getString(0));
            factura.setNombre(cursor.getString(1));
            factura.setApellido(cursor.getString(2));
            factura.setEmail(cursor.getString(3));
            factura.setCantidad(cursor.getString(4));
            factura.setTotal(cursor.getString(5));
            listaFactura.add(factura);
        }
        obtenerLista();
    }

    public void obtenerLista() {
        listaInformacion = new ArrayList<>();

        for (Factura factura : listaFactura) {
            listaInformacion.add("| ID: " + factura.getId() +
                    "\n| Nombre: " + factura.getNombre() +
                    "\n| Apellido: " + factura.getApellido() +
                    "\n| Correo electrónico: " + factura.getEmail() +
                    "\n| Cantidad: " + factura.getCantidad() +
                    "\n| Total a pagar: " + factura.getTotal() + "$");
        }
    }

    private byte[] logoDrawableToBytes(Drawable drawable) throws IOException {
        Bitmap bitmap = drawableToBitmap(drawable);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
