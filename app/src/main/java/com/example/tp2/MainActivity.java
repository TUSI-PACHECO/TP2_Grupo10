package com.example.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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

    private EditText txtEmail, txtDate;

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

        txtEmail = findViewById(R.id.txt_email);
        txtDate = findViewById(R.id.txt_date);

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

        boolean isEmailValid = validateEmail(email);
        boolean isDateValid = validateDate(date);
        if (!isEmailValid) {
            txtEmail.setError("Correo electrónico no válido");
        }
        if (!isDateValid) {
            txtDate.setError("Fecha no válida o futura");
        }
        if (!isEmailValid || !isDateValid) {
            Toast.makeText(this, "Revise los campos ingresados", Toast.LENGTH_SHORT).show();
        }

        return isEmailValid && isDateValid;
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

}