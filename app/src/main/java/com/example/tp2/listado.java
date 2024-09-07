package com.example.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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

        lv1 = (ListView) findViewById(R.id.lv1);

        mostrarDatosEnListView();
    }

    private void mostrarDatosEnListView() {
        ArrayList<String> listaContactos = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput("contactos.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String linea;
            StringBuilder contactoActual = new StringBuilder();

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    if (contactoActual.length() > 0) {
                        listaContactos.add(contactoActual.toString());
                        contactoActual = new StringBuilder();
                    }
                } else {
                    contactoActual.append(linea).append("\n");
                }
            }

            if (contactoActual.length() > 0) {
                listaContactos.add(contactoActual.toString());
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al leer los datos", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaContactos);
        lv1.setAdapter(adapter);
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