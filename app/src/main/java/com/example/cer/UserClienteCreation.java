package com.example.cer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class UserClienteCreation extends AppCompatActivity {

    EditText txtNombreUsuarioCliente, txtEmailCliente, txtContraseñaCliente, txtPhoneCliente;
    Button btnRegistrarCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cliente_creation);

        txtNombreUsuarioCliente=(EditText)findViewById(R.id.txtNombreUsuarioCliente);
        txtEmailCliente=(EditText)findViewById(R.id.txtEmailCliente);
        txtContraseñaCliente=(EditText)findViewById(R.id.txtContraseñaCliente);
        txtPhoneCliente=(EditText)findViewById(R.id.txtPhoneCliente);
        btnRegistrarCliente=(Button)findViewById(R.id.btnRegistrarCliente);

        btnRegistrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarServicio("http://10.0.3.2/php/cer/insertar_usuario.php");
            }
        });

    }

    private void ejecutarServicio(String URL){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("nombre",txtNombreUsuarioCliente.getText().toString());
                parametros.put("correo",txtEmailCliente.getText().toString());
                parametros.put("contrasena",txtContraseñaCliente.getText().toString());
                parametros.put("telefono",txtPhoneCliente.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
