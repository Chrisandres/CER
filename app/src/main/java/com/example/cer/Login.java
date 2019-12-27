package com.example.cer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity{

    ImageView logo;
    TextView tvRegistro;
    @NotEmpty(message = "Este campo es obligatorio")
    @Length(min = 4, message = "Falta rellenar este campo")
    EditText txtNombre;
    @NotEmpty(message ="este campo es obligatorio")
    @Password(min = 8, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS, message = "Debe tener mínimo 8 carácteres, al menos una mayúscula, un número y un símbolo")
    EditText txtPassword;
    Validator validator;
    Button btnIngresarCliente, btnIngresarColectivo;
    String EmailHolder, PasswordHolder;
    String finalResult;
    String HttpURL = "http://10.0.3.2/PHP/CER/validar_usuario.php";
    //String HttpURL2 = "https://www.barberapp.cl/BarberApp/BarberLogin.php";
    //String HttpURL3 = "https://www.barberapp.cl/BarberApp/AdminLogin.php";
    // EJEMPLO
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String UserEmail ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                validarUsuario("http://10.0.3.2/php/cer/validar_usuario.php");
            }
        });

        btnIngresarColectivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MapsActivityColectivo.class);
                startActivity(i);
            }
        });

    }

    private void validarUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent=new Intent(getApplicationContext(),MainCliente.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
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
