package com.example.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class contactos extends AppCompatActivity {

    private ListView listViewAgenda;

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

        listViewAgenda = findViewById(R.id.lv1);

        // Leer los datos del archivo y mostrarlos en el ListView
        leerArchivo();

        // Configurar el manejador de clics para el ListView
        verContactos();
    }

    private void leerArchivo() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fis = openFileInput("contactos.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convertir los datos en una lista y mostrarlos en un ListView
        String[] contactosArray = stringBuilder.toString().split("\n\n");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactosArray);
        listViewAgenda.setAdapter(adapter);
    }

    private void verContactos() {
        listViewAgenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String contactoSeleccionado = (String) parent.getItemAtPosition(position);

                // Mostrar todos los detalles del contacto en un Toast o en una nueva actividad
                Intent intent = new Intent(contactos.this, contactos.class);
                intent.putExtra("detallesContacto", contactoSeleccionado);
                startActivity(intent);
            }
        });
    }


}
