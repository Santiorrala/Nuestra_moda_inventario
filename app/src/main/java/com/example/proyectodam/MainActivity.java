package com.example.proyectodam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private EditText textUsuario, textContraseña;
    private CheckBox checkBoxRecordar;
    private Button btnIniciar, btnSalir;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textUsuario = findViewById(R.id.textUsuario);
        textContraseña = findViewById(R.id.textContraseña);
        checkBoxRecordar = findViewById(R.id.checkBoxRecordar);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnSalir = findViewById(R.id.btnSalir);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
        cargarCredencialesGuardadas();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inicio, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.agregar_usuario) {
            Intent intent = new Intent(this, Agregar_usuario.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
    public void iniciarSesion(){
        String usuario = textUsuario.getText().toString();
        String contraseña = textContraseña.getText().toString();

        SQLServer conexion = new SQLServer(this);
        final SQLiteDatabase db = conexion.getReadableDatabase();
        String consulta = "SELECT * FROM usuario WHERE usuario = ? AND contraseña = ?";
        Cursor cursor = db.rawQuery(consulta, new String[]{usuario, contraseña});
        if (cursor.moveToFirst()) {
            // Inicio de sesión exitoso
            if (checkBoxRecordar.isChecked()){
                guardarCredenciales(usuario, contraseña);
            }
            else{
                eliminarCredenciales();
            }

            // Aquí puedes realizar las acciones correspondientes al inicio de sesión
            Intent intentPrincipal = new Intent(this, Pagina_principal.class);
            intentPrincipal.putExtra("nombreUsuario", usuario);
            startActivity(intentPrincipal);
            finish();
        } else {
            // Usuario o contraseña incorrectos
            // Aquí puedes mostrar un mensaje de error o realizar cualquier otra acción necesaria
            Toast.makeText(this,"Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
        cursor.close();


    }
    public void salir(){
        finishAffinity(); // Cierra todas las actividades abiertas en la aplicación
        System.exit(0);
    }
    public void guardarCredenciales(String Usuario, String Contraseña){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usuario", Usuario);
        editor.putString("contraseña", Contraseña);
        editor.apply();

    }
    private void cargarCredencialesGuardadas() {
        String usuario = sharedPreferences.getString("usuario", "");
        String contraseña = sharedPreferences.getString("contraseña", "");

        textUsuario.setText(usuario);
        textContraseña.setText(contraseña);

        if (!usuario.isEmpty() && !contraseña.isEmpty()){
            checkBoxRecordar.setChecked(true);
        }
    }
    private void eliminarCredenciales(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("usuario");
        editor.remove("contraseña");
        editor.apply();
    }
}