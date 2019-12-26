package com.example.cer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText txtNombre;
    EditText txtPassword;
    TextView tvRegistro;
    Button btnIngresarCliente;
    Button btnIngresarColectivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        tvRegistro = (TextView)findViewById(R.id.tvRegistro);
        tvRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),UserTypeCreation.class);
                startActivity(i);
            }
        });

        btnIngresarCliente = (Button)findViewById(R.id.btnIngresarCliente);
        btnIngresarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainCliente.class);
                startActivity(i);
            }
        });

        btnIngresarColectivo = (Button)findViewById(R.id.btnIngresarColectivo);
        btnIngresarColectivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MapsActivityColectivo.class);
                startActivity(i);
            }
        });

    }
}
