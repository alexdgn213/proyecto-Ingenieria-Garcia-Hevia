package com.example.alexander.pruebamenu.Activity;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Actividad principal de la aplicacion, en ella se muestra de manera dinamica las subastas disponibles
                    para que el usuario acceda,ademas es posible filtrar y ordenarlas para un comodo acceso
*/

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.alexander.pruebamenu.Adapter.EmptyStateRVAdapter;
import com.example.alexander.pruebamenu.Model.UsuarioActual;
import com.example.alexander.pruebamenu.R;
import com.example.alexander.pruebamenu.Adapter.RVAdapter;
import com.example.alexander.pruebamenu.Model.Subasta;
import com.example.alexander.pruebamenu.Realm.ServicioFavorito;
import com.example.alexander.pruebamenu.Realm.ServicioLike;
import com.example.alexander.pruebamenu.Realm.ServicioSubasta;
import com.example.alexander.pruebamenu.Realm.ServicioUsuarioActual;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView subastas; //Contenedor de todas las subastas que puede ver el usuario en un instante
    private List<Subasta> listaDeSubastas; //Lista de las subastas a ser colocadas en el contenedor
    ServicioSubasta servicioSubasta; //Coneccion con realm para trabajar con subastas
    ScrollView categorias; //Contenedor con todas las categorias(invisible hasta que se necesita en pantalla)
    ServicioUsuarioActual servicioUsuarioActual; //Coneccion con realm para trabajar con el Usuario Actual
    ServicioLike servicioLike; //Coneccion con realm para trabajar con likes de subastas
    ServicioFavorito servicioFavorito; //Coneccion con realm para trabajar con favoritos de subastas
    UsuarioActual user; //Usuario utilizando la aplicacion en este instante
    ImageView fotoPerfil; //Donde se mostrara la foto de perfil del usuario
    TextView nombrePerfil; //Donde se mostrara el nombre de usuario del usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);

        //Asignar cada variable a su componente en el xlm
        fotoPerfil = (ImageView) hView.findViewById(R.id.fotoPrincipal);
        nombrePerfil = (TextView) hView.findViewById(R.id.nombrePrincipal);
        categorias = (ScrollView) findViewById(R.id.scrollCategorias);
        subastas = (RecyclerView) findViewById(R.id.recyclerSubastas);

        //Asignar al RecyclerView un layout lineal vertical
        LinearLayoutManager llm = new LinearLayoutManager(this);
        subastas.setLayoutManager(llm);

        // Inicializacion de listas y servicios
        listaDeSubastas = new ArrayList<Subasta>();
        servicioSubasta = new ServicioSubasta(Realm.getDefaultInstance());
        servicioUsuarioActual = new ServicioUsuarioActual(Realm.getDefaultInstance());
        servicioFavorito = new ServicioFavorito(Realm.getDefaultInstance());
        servicioLike = new ServicioLike(Realm.getDefaultInstance());

        //Se trae de realm el usuario actual en la aplicacion
        user = servicioUsuarioActual.obtenerUsuarioActual();

        //Se cambian la foto de perfil y el nombre de usuario para mostrar
        fotoPerfil.setImageResource(user.getFoto());
        nombrePerfil.setText(user.getNombre()+" "+user.getApellido());

        //Cargar al Recycler todas las subastas en fases de: publicidad, enCurso y finalizada
        listaDeSubastas = servicioSubasta.obtenerSubastasParaMostrar();
        cargarSubastas();

        //Establecer la accion de cada boton de categorias
        accionescategorias();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Descripcion general: Metodo que permite cargar en el RecyclerView todas las subastas que se encuentren en la lista de subastas
    */
    public void cargarSubastas(){
        //Se comprueba si la lista esta vacia o no
        if(listaDeSubastas.size()>0){
            listaDeSubastas = servicioLike.generarLikes(listaDeSubastas,user);//Agregar los likes a las subastas en la lista
            listaDeSubastas = servicioFavorito.generarFavoritos(listaDeSubastas,user);//Agregar los favoritos a las subastas en la lista
            RVAdapter adapter = new RVAdapter(listaDeSubastas,user,this);// Adaptador de cada subasta al RV
            subastas.setAdapter(adapter);
        }
        else{
            //Empty State generado cuando no hay subastas que mostrar
            EmptyStateRVAdapter adapter = new EmptyStateRVAdapter(this);//Adaptador del Empty State al RV
            subastas.setAdapter(adapter);
        };
    }

    /*
    Descripcion general: Metodo que permite que se cierre la vista con las categorias y se cargen al RV las subastas pertenecientes
                         a ella, ademas vuelve a mostrar el RV
    Parametros:          Nombre de la categoria que se desea cargar
    */
    public void cargarCategoria(String categoria){
        categorias.setVisibility(View.GONE);
        subastas.setVisibility(View.VISIBLE);
        listaDeSubastas = servicioSubasta.obtenerSubastasCategoria(categoria);//Lista de subastas de dicha categoria
        cargarSubastas();
    }

    /*
    Descripcion general: Metodo que permite asignar a cada categoria la accion de cargar las subastas de su categoria al
                         RecyclerView
    */
    public void accionescategorias(){
        //Asignar cada categoria a una variable
        CardView c1 = (CardView) findViewById(R.id.Categoria1);
        CardView c2 = (CardView) findViewById(R.id.Categoria2);
        CardView c3 = (CardView) findViewById(R.id.Categoria3);
        CardView c4 = (CardView) findViewById(R.id.Categoria4);
        CardView c5 = (CardView) findViewById(R.id.Categoria5);
        CardView c6 = (CardView) findViewById(R.id.Categoria6);
        CardView c7 = (CardView) findViewById(R.id.Categoria7);

        //Asignar que la accion al precionar la categoria sea cargar la misma
        c1.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                cargarCategoria("Electronica");
            }
        });
        c2.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                cargarCategoria("Inmuebles");
            }
        });
        c3.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                cargarCategoria("Hogar");
            }
        });
        c4.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                cargarCategoria("Vehiculos");
            }
        });
        c5.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                cargarCategoria("Libros");
            }
        });
        c6.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                cargarCategoria("Arte");
            }
        });
        c7.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                cargarCategoria("Varios");
            }
        });
    }


    /*
    Descripcion general: Metodo que permite que actua al seleccionar una opcion en la barra lateral, se genera una lista con
                         con las subastas deseadas y se cargan estas subastas. En caso de seleccionar categorias las muestra y en
                         caso de seleccionar cerrar sesion cierra la sesion del usuario y termina la actividad

    */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        categorias.setVisibility(View.GONE);
        subastas.setVisibility(View.VISIBLE);
        if (id == R.id.todas) {
            listaDeSubastas = servicioSubasta.obtenerSubastasParaMostrar();
            cargarSubastas();
        } else if (id == R.id.favoritas) {
            listaDeSubastas = servicioFavorito.obtenerSubastasFavoritas(user.getUsuario());
            cargarSubastas();
        } else if (id == R.id.categorias) {
            categorias.setVisibility(View.VISIBLE);
            subastas.setVisibility(View.GONE);
        } else if (id == R.id.vistas ) {
            listaDeSubastas = servicioSubasta.obtenerSubastasPorLikes();
            cargarSubastas();
        } else if (id == R.id.misSubastas ) {
            listaDeSubastas = user.obtenerSubastas(servicioSubasta,user.getUsuario());
            cargarSubastas();
        }else if (id == R.id.configuracion) {

        } else if (id == R.id.salir) {
            servicioUsuarioActual.CerrarSesion();
            Intent intent = new Intent(MainActivity.this, PantallaCarga.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
