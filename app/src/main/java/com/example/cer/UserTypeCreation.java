package com.example.cer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserTypeCreation extends AppCompatActivity {

    Button btnRegistroColectivo;
    Button btnRegistroCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_creation);

        btnRegistroColectivo = (Button)findViewById(R.id.btnRegistroColectivo);
        btnRegistroColectivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),UserColectivoCreation.class);
                startActivity(i);
            }
        });

        btnRegistroCliente = (Button)findViewById(R.id.btnRegistroCliente);
        btnRegistroCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),UserClienteCreation.class);
                startActivity(i);
            }
        });

    }
}
