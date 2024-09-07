package com.example.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class formularioMasDatos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String apellido = intent.getStringExtra("apellido");
        String telefono = intent.getStringExtra("telefono");
        String email = intent.getStringExtra("email");
        String direccion = intent.getStringExtra("direccion");
        String fechaNacimiento = intent.getStringExtra("fechaNacimiento");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private String getNivelEstudioSeleccionado() {
        // Encuentra el RadioGroup y el RadioButton seleccionado
        RadioGroup radioGroupNivelEstudio = findViewById(R.id.radioGroupEstudios);
        int selectedId = radioGroupNivelEstudio.getCheckedRadioButtonId();

        // Si hay una opción seleccionada
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            return selectedRadioButton.getText().toString(); // Retorna el texto del RadioButton seleccionado
        } else {
            return null; // No se ha seleccionado ninguna opción
        }
    }

    // Obtener los intereses seleccionados (CheckBox)
    private List<String> getInteresesSeleccionados() {
        List<String> intereses = new ArrayList<>();

        // Encuentra los CheckBox de intereses
        CheckBox checkDeporte = findViewById(R.id.checkbox1);
        CheckBox checkMusica = findViewById(R.id.checkbox2);
        CheckBox checkArte = findViewById(R.id.checkbox3);
        CheckBox checkTecnologia = findViewById(R.id.checkbox4);

        // Verifica si cada CheckBox está seleccionado y agrega el texto correspondiente a la lista
        if (checkDeporte.isChecked()) {
            intereses.add(checkDeporte.getText().toString());
        }
        if (checkMusica.isChecked()) {
            intereses.add(checkMusica.getText().toString());
        }
        if (checkArte.isChecked()) {
            intereses.add(checkArte.getText().toString());
        }
        if (checkTecnologia.isChecked()) {
            intereses.add(checkTecnologia.getText().toString());
        }
        return intereses; // Retorna una lista con los intereses seleccionados
    }
    public String verificarSwitch ()

    {

        SwitchMaterial Switch1 = findViewById(R.id.material_switch);

        String eleccion;
        if (Switch1.isChecked()) {
            eleccion = "Desea recibir información";
            Toast.makeText(this, eleccion, Toast.LENGTH_SHORT).show();
        } else {
            eleccion = "No desea recibir información";
            Toast.makeText(this, eleccion, Toast.LENGTH_SHORT).show();
        }
        return eleccion;
    }

    public void guardar(View view)
    {

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String apellido = intent.getStringExtra("apellido");
        String telefono = intent.getStringExtra("telefono");
        String email = intent.getStringExtra("email");
        String direccion = intent.getStringExtra("direccion");

        String fechaNacimiento = intent.getStringExtra("fechaNacimiento");
        String nivelEstudios = getNivelEstudioSeleccionado();
        String deseaRecibirInfo = verificarSwitch();
        List<String> intereses = getInteresesSeleccionados();

        String datosContacto = "Nombre: " + nombre + "\n" +
                "Apellido: " + apellido + "\n" +
                "Teléfono: " + telefono + "\n" +
                "Email: " + email + "\n" +
                "Dirección: " + direccion + "\n" +
                "Fecha de Nacimiento: " + fechaNacimiento + "\n" +
                "Nivel de Estudios: " + nivelEstudios + "\n" +
                "Intereses: " + intereses.toString() + "\n" +
                "Recibe Información: " + deseaRecibirInfo + "\n\n";

        try {
            FileOutputStream fos = openFileOutput("contactos.txt", MODE_APPEND);
            fos.write(datosContacto.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        finish();
    }
}
