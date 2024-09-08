package com.example.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class listado extends AppCompatActivity {

    private ListView lv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv1 = findViewById(R.id.lv1);

        mostrarDatosEnListView();
    }

    private void mostrarDatosEnListView() {
        ArrayList<String> listaContactos = new ArrayList<>();
        try {
            File directorio = new File(getFilesDir(), "contactos");
            if (directorio.exists() && directorio.isDirectory()) {
                File[] archivos = directorio.listFiles();

                if (archivos != null) {
                    for (File archivo : archivos) {
                        if (archivo.isFile()) {
                            listaContactos.add(archivo.getName().replace("-", " ").replace(".txt", ""));
                        }
                    }
                }
            } else {
                Toast.makeText(this, "No se encontró la carpeta de contactos", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al leer los datos", Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaContactos);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener((parent, view, position, id) -> {
            String nombreArchivo = listaContactos.get(position).replace(" ", "-") + ".txt";
            mostrarModalConInfoArchivo(nombreArchivo);
        });
    }

    private void mostrarModalConInfoArchivo(String nombreArchivo) {
        try {
            File archivo = new File(getFilesDir() + "/contactos", nombreArchivo);
            StringBuilder contenidoArchivo = new StringBuilder();

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                contenidoArchivo.append(linea).append("\n");
            }
            br.close();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Detalles de Contacto");
            builder.setMessage(contenidoArchivo.toString());

            builder.setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al leer el archivo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_agregar_contactos) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}