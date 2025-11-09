package com.example.carritocompras;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carritocompras.utils.DataStore;

public class LoginActivity<Customer> extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        TextView textViewGoToRegister = findViewById(R.id.textViewGoToRegister);

        buttonLogin.setOnClickListener(v -> performLogin());

        textViewGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void performLogin() {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor, complete ambos campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // SIMULACIÓN DE LECTURA (R)
        Customer customer = DataStore.findCustomerByCredentials(email, password);

        if (customer != null) {
            Toast.makeText(LoginActivity.this, "¡Bienvenido, " + customer.getClass() + "!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, ProductListActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Email o contraseña incorrectos.", Toast.LENGTH_LONG).show();
        }
    }
}


