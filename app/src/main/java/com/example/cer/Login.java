package com.example.cer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.HashMap;
import java.util.List;

public class Login extends AppCompatActivity implements Validator.ValidationListener{

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

        validator = new Validator(this);
        validator.setValidationListener(this);

        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        btnIngresarCliente = (Button)findViewById(R.id.btnIngresarCliente);
        btnIngresarColectivo = (Button)findViewById(R.id.btnIngresarColectivo);

        tvRegistro = (TextView)findViewById(R.id.tvRegistro);

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
                //Intent i = new Intent(getApplicationContext(), MainCliente.class);
                //startActivity(i);
                validator.validate();

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){
                    UserLoginFunction(EmailHolder,PasswordHolder);
                }
                else{
                    Toast.makeText(Login.this, "Por favor llene todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void CheckEditTextIsEmptyOrNot(){
        EmailHolder = txtNombre.getText().toString();
        PasswordHolder = txtPassword.getText().toString();

        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){
            CheckEditText = false;
        }
        else{
            CheckEditText = true;
        }
    }

    public void UserLoginFunction(final String email, final String password){
        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Login.this, "Cargando datos", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                if (httpResponseMsg.equalsIgnoreCase("Comprobado")) {
                    finish();
                    Intent intent = new Intent(Login.this, MainCliente.class);
                    intent.putExtra(UserEmail, email);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("email", params[0]);

                hashMap.put("password", params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }
        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(email,password);
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
