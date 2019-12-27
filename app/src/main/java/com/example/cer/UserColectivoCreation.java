package com.example.cer;

import android.content.Intent;
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

public class UserColectivoCreation extends AppCompatActivity {

    EditText txtNombreUsuarioColectivo, txtEmailColectivo, txtContraseñaColectivo, txtPhoneColectivo;
    Button btnRegistrarColectivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_colectivo_creation);

        txtNombreUsuarioColectivo=(EditText)findViewById(R.id.txtNombreUsuarioColectivo);
        txtEmailColectivo=(EditText)findViewById(R.id.txtEmailColectivo);
        txtContraseñaColectivo=(EditText)findViewById(R.id.txtContraseñaColectivo);
        txtPhoneColectivo=(EditText)findViewById(R.id.txtPhoneColectivo);
        btnRegistrarColectivo=(Button)findViewById(R.id.btnRegistrarColectivo);

        btnRegistrarColectivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarServicio("http://10.0.3.2/php/cer/insertar_colectivero.php");
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("nombre",txtNombreUsuarioColectivo.getText().toString());
                parametros.put("correo",txtEmailColectivo.getText().toString());
                parametros.put("contrasena",txtContraseñaColectivo.getText().toString());
                parametros.put("telefono",txtPhoneColectivo.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
