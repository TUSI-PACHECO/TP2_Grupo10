package com.example.tp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText txtNombre, txtApellido, txtTelefono, txtDireccion, txtEmail, txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtNombre = findViewById(R.id.txt_nombre);
        txtApellido=findViewById(R.id.txt_apellido);
        txtTelefono=findViewById(R.id.txt_telefono);
        txtDireccion=findViewById(R.id.txt_direccion);
        txtEmail = findViewById(R.id.txt_email);
        txtDate = findViewById(R.id.txt_date);
        Spinner phoneOptionsSpinner = findViewById(R.id.spinner_telefono);
        Spinner mailOptionsSpinner = findViewById(R.id.spinner_email);

        String [] spinnerOptions = {"Casa", "Trabajo", "Móvil"};
        ArrayAdapter <String> adapterOptions = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerOptions);
        phoneOptionsSpinner.setAdapter(adapterOptions);
        mailOptionsSpinner.setAdapter(adapterOptions);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_listado_contactos) {
            Intent intent = new Intent(this, listado.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validateForm() {
        String email = txtEmail.getText().toString();
        String date = txtDate.getText().toString();
        String name = txtNombre.getText().toString();
        String surname = txtApellido.getText().toString();
        String phone = txtTelefono.getText().toString();

        boolean isEmailValid = validateEmail(email);
        boolean isDateValid = validateDate(date);
        boolean isNameValid = validateString(name);
        boolean isSurnameValid = validateString(surname);
        boolean isPhoneValid = validatePhone(phone);

        if (!isEmailValid) {
            txtEmail.setError("Correo electrónico no válido");
        }
        if (!isDateValid) {
            txtDate.setError("Fecha no válida o futura");
        }
        if (!isNameValid) {
            txtNombre.setError("Nombre no válido");
        }
        if (!isSurnameValid) {
            txtApellido.setError("Apellido no válido");
        }
        if (!isPhoneValid) {
            txtTelefono.setError("Número de teléfono no válido");
        }

        if (!isEmailValid || !isDateValid || !isNameValid || !isSurnameValid || !isPhoneValid) {
            Toast.makeText(this, "Revise los campos ingresados", Toast.LENGTH_SHORT).show();
        }

        return isEmailValid && isDateValid;
    }

    private boolean validatePhone(String phone) {
        if (phone.isEmpty()){
            return false;
        }
        String phonePattern = "^\\d{8,10}(-\\d{1,3}){0,3}$";
        return phone.matches(phonePattern);
    }

    private boolean validateString(String s) {
        if (s.isEmpty()){
            return false;
        }
        String stringValidPattern = "^[A-Za-zÀ-ÖØ-öø-ÿ']+(?: [A-Za-zÀ-ÖØ-öø-ÿ']+)*$";
        return s.matches(stringValidPattern);
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            return false;
        }
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private boolean validateDate(String date) {
        if (date.isEmpty()) {
            return false;
        }
        String datePattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        dateFormat.setLenient(false);
        try {
            Date dateObject = dateFormat.parse(date);
            Date currentDate = new Date();
            return dateObject.before(currentDate);

        } catch (ParseException e) {
            return false;
        }
    }

    public void siguiente (View view)
    {
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();
        String telefono = txtTelefono.getText().toString();
        String email = txtEmail.getText().toString();
        String direccion = txtDireccion.getText().toString();
        String fechaNacimiento = txtDate.getText().toString();

        if (!validateForm()) {
            return;
        }

// Crear un intent para pasar los datos a la siguiente actividad
        Intent intent = new Intent(this, formularioMasDatos.class);
        intent.putExtra("nombre", nombre);
        intent.putExtra("apellido", apellido);
        intent.putExtra("telefono", telefono);
        intent.putExtra("email", email);
        intent.putExtra("direccion", direccion);
        intent.putExtra("fechaNacimiento", fechaNacimiento);
        startActivity(intent);
    }


}