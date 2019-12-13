package com.example.cer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class Favoritos extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id =item.getItemId();

        Context context = getApplicationContext();
        CharSequence text = null;
        int duration = Toast.LENGTH_SHORT;

        if(id == R.id.Recorridos){
            text = "Cargango página de recorridos";
            Intent actionC = new Intent(Favoritos.this,MainCliente.class);
            actionC.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(actionC);
            finish();
        }
        else if(id == R.id.Favoritos){
            text = "Recargando Favoritos";
            Intent actionC = new Intent(Favoritos.this,Favoritos.class);
            actionC.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(actionC);
            finish();
        }
        else if(id == R.id.Nosotros){
            text = "Cargando página Sobre Nosotros";
            Intent actionC = new Intent(Favoritos.this,AboutUsCliente.class);
            actionC.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(actionC);
            finish();
        }
        else if(id == R.id.CerrarSesion){
            text = "Ha cerrado su sesión";
            Intent actionC = new Intent(Favoritos.this,Login.class);
            actionC.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(actionC);
            finish();
        }

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
    }
}
