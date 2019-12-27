package com.example.cer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginColectivo extends AppCompatActivity {

    EditText txtNombre, txtPassword;
    Button btnIngresarCliente, btnIngresarColectivo;
    TextView tvRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_colectivo);

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnIngresarCliente = (Button) findViewById(R.id.btnIngresarCliente);
        btnIngresarColectivo = (Button) findViewById(R.id.btnIngresarColectivo);

        tvRegistro = (TextView) findViewById(R.id.tvRegistro);

        tvRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UserTypeCreation.class);
                startActivity(i);
            }
        });

        btnIngresarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarUsuario("http://10.0.3.2/php/cer/validar_colectivero.php");
            }
        });

        btnIngresarColectivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });

    }

    private void validarUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent=new Intent(getApplicationContext(),MapsActivityColectivo.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginColectivo.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginColectivo.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("correo",txtNombre.getText().toString());
                parametros.put("contrasena",txtPassword.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
