package com.example.cer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainCliente extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Context context = getApplicationContext();
        CharSequence text = null;
        int duration = Toast.LENGTH_SHORT;

        if (id == R.id.Recorridos) {
            text = "Recargando página";
            Intent actionC = new Intent(MainCliente.this, MainCliente.class);
            actionC.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(actionC);
            finish();
        } else if (id == R.id.Favoritos) {
            text = "Cargando página de favoritos";
            Intent actionC = new Intent(MainCliente.this, Favoritos.class);
            actionC.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(actionC);
            finish();
        } else if (id == R.id.Nosotros) {
            text = "Cargando página Sobre Nosotros";
            Intent actionC = new Intent(MainCliente.this, AboutUsCliente.class);
            actionC.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(actionC);
            finish();
        } else if (id == R.id.CerrarSesion) {
            text = "Ha cerrado su sesión";
            Intent actionC = new Intent(MainCliente.this, Login.class);
            actionC.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(actionC);
            finish();
        }

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        return super.onOptionsItemSelected(item);
    }

    //private static final String BASE_URL = "http://10.0.2.2";
    private static final String BASE_URL = "http://10.0.3.2";
    private static final String FULL_URL = BASE_URL+"/PHP/CER/";

    class Colectivo {
        @SerializedName("id")
        private int id;
        @SerializedName("nombre")
        private String nombre;
        @SerializedName("inicio")
        private String inicio;
        @SerializedName("destino")
        private String destino;
        @SerializedName("imagen")
        private String imagen;
        @SerializedName("disponible")
        private int disponible;

        public Colectivo(int id, String nombre, String inicio,String destino, String imagen, int disponible) {
            this.id = id;
            this.nombre = nombre;
            this.inicio = inicio;
            this.destino = destino;
            this.imagen = imagen;
            this.disponible = disponible;
        }

        /*
         *GETTERS AND SETTERS
         */
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getNombre() {
            return nombre;
        }
        public String getInicio() {
            return inicio;
        }
        public String getDestino() {
            return destino;
        }
        public String getImagen() {
            return imagen;
        }
        public int getDisponible() {
            return disponible;
        }
        @Override
        public String toString() {
            return nombre;
        }
    }

    interface MyAPIService {

        @GET("/PHP/CER/colectivos.php")
        Call<List<Colectivo>> getColectivos();
    }

    static class RetrofitClientInstance {
        private static Retrofit retrofit;

        public static Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }

    class FilterHelper extends Filter {
        private List<Colectivo> currentList;
        private ListViewAdapter adapter;
        private Context c;

        public FilterHelper(List<Colectivo> currentList, ListViewAdapter adapter, Context c) {
            this.currentList = currentList;
            this.adapter = adapter;
            this.c=c;
        }
        /*
        - Perform actual filtering.
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults=new FilterResults();

            if(constraint != null && constraint.length()>0)
            {
                //CHANGE TO UPPER
                constraint=constraint.toString().toUpperCase();

                //HOLD FILTERS WE FIND
                ArrayList<Colectivo> foundFilters=new ArrayList<>();

                Colectivo colectivo=null;

                //ITERATE CURRENT LIST
                for (int i=0;i<currentList.size();i++)
                {
                    colectivo= currentList.get(i);

                    //SEARCH
                    if(colectivo.getNombre().toUpperCase().contains(constraint) )
                    {
                        //ADD IF FOUND
                        foundFilters.add(colectivo);
                    }
                }

                //SET RESULTS TO FILTER LIST
                filterResults.count=foundFilters.size();
                filterResults.values=foundFilters;
            }else
            {
                //NO ITEM FOUND.LIST REMAINS INTACT
                filterResults.count=currentList.size();
                filterResults.values=currentList;
            }

            //RETURN RESULTS
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            adapter.setColectivos((ArrayList<Colectivo>) filterResults.values);
            adapter.refresh();
        }
    }

    class ListViewAdapter extends BaseAdapter implements Filterable {

        private List<Colectivo> colectivos;
        private Context context;
        private List<Colectivo> currentList;
        private FilterHelper filterHelper;

        public ListViewAdapter(Context context,List<Colectivo> colectivos){
            this.context = context;
            this.colectivos = colectivos;
            this.currentList=colectivos;
        }

        @Override
        public int getCount() {
            return colectivos.size();
        }

        @Override
        public Object getItem(int pos) {
            return colectivos.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if(view==null)
            {
                view= LayoutInflater.from(context).inflate(R.layout.model,viewGroup,false);
            }

            TextView txtNombre = view.findViewById(R.id.NombreTextView);
            TextView txtInicio = view.findViewById(R.id.InicioTextView);
            CheckBox chkDisponible = view.findViewById(R.id.myCheckBox);
            ImageView PatenteImageView = view.findViewById(R.id.PatenteImageView);

            final Colectivo thisColectivo= colectivos.get(position);

            txtNombre.setText(thisColectivo.getNombre());
            txtInicio.setText(thisColectivo.getInicio());
            chkDisponible.setChecked( thisColectivo.getDisponible()== 1);
            chkDisponible.setEnabled(false);

            if(thisColectivo.getImagen() != null && thisColectivo.getImagen().length()>0)
            {
                Picasso.get().load(FULL_URL+"/images/"+thisColectivo.getImagen()).placeholder(R.drawable.car).into(PatenteImageView);
            }else {
                Toast.makeText(context, "Empty Image URL", Toast.LENGTH_LONG).show();
                Picasso.get().load(R.drawable.car).into(PatenteImageView);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, thisColectivo.getNombre(), Toast.LENGTH_SHORT).show();
                    String techExists="";
                    if(thisColectivo.getDisponible()==1){
                        techExists="YES";
                    }else{
                        techExists="NO";
                    }
                    String[] colectivo = {
                            thisColectivo.getNombre(),
                            thisColectivo.getInicio(),
                            thisColectivo.getDestino(),
                            techExists,
                            FULL_URL+"/images/"+thisColectivo.getImagen()
                    };
                    openDetailActivity(colectivo);
                }
            });


            return view;
        }
        private void openDetailActivity(String[] data) {
            Intent intent = new Intent(MainCliente.this, DetailsActivity.class);
            intent.putExtra("NOMBRE_KEY", data[0]);
            intent.putExtra("INICIO_KEY", data[1]);
            intent.putExtra("DESTINO_KEY", data[2]);
            intent.putExtra("DISPONIBLE_KEY", data[3]);
            intent.putExtra("IMAGEN_KEY", data[4]);
            startActivity(intent);
        }

        public void setColectivos(ArrayList<Colectivo> filteredColectivos)
        {
            this.colectivos=filteredColectivos;
        }
        @Override
        public Filter getFilter() {
            if(filterHelper==null)
            {
                filterHelper=new FilterHelper(currentList,this,context);
            }
            return filterHelper;
        }
        public void refresh(){
            notifyDataSetChanged();
        }
    }

    private ListViewAdapter adapter;
    private ListView mListView;
    private ProgressBar mProgressBar;
    private SearchView mSearchView;

    private void initializeWidgets(){
        mListView = findViewById(R.id.mListView);
        mProgressBar= findViewById(R.id.mProgressBar);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);
        mSearchView=findViewById(R.id.mSearchView);
        mSearchView.setIconified(true);
    }

    private void populateListView(List<Colectivo> colectivoListList) {
        adapter = new ListViewAdapter(this,colectivoListList);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente);

        this.initializeWidgets();

        /*Create handle for the RetrofitInstance interface*/
        MyAPIService myAPIService = RetrofitClientInstance.getRetrofitInstance().create(MyAPIService.class);

        Call<List<Colectivo>> call = myAPIService.getColectivos();
        call.enqueue(new Callback<List<Colectivo>>() {

            @Override
            public void onResponse(Call<List<Colectivo>> call, Response<List<Colectivo>> response) {
                mProgressBar.setVisibility(View.GONE);
                populateListView(response.body());
            }
            @Override
            public void onFailure(Call<List<Colectivo>> call, Throwable throwable) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(MainCliente.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });

    }
}
