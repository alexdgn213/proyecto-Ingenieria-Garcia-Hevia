package com.example.alexander.pruebamenu.Activity;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Actividad que permite ver en detalle una subasta, postularse, acceder a ella, y configurarla dependiendo de
                     la fase en la que se encuentre y el usuario logueado
*/

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexander.pruebamenu.Adapter.ImageRVAdapter;
import com.example.alexander.pruebamenu.Model.Imagen;
import com.example.alexander.pruebamenu.Model.Subasta;
import com.example.alexander.pruebamenu.Model.UsuarioActual;
import com.example.alexander.pruebamenu.R;
import com.example.alexander.pruebamenu.Realm.ServicioPostulacion;
import com.example.alexander.pruebamenu.Realm.ServicioPostura;
import com.example.alexander.pruebamenu.Realm.ServicioSubasta;
import com.example.alexander.pruebamenu.Realm.ServicioUsuarioActual;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PantallaSubasta extends AppCompatActivity {
    Subasta subasta; //Subasta que se esta mostrando
    ServicioSubasta servicioSubasta; //Coneccion con realm para trabajar con subastas
    List<Imagen> listaImagenes; //Lista con las imagenes de la subasta
    RecyclerView imagenes; //Contenedor para las imagenes de la subasta
    ServicioUsuarioActual servicioUsuarioActual; //Coneccion con realm para trabajar con el usuario logueado
    UsuarioActual user; // Usuario logueado
    ServicioPostulacion servicioPostulacion; //Coneccion con realm para trabajar con  las postulaciones
    boolean estaPostulado; //Variable que indica si el usuario esta postulado a la subasta
    Button botonPostularse; //Boton para postularse a la subasta
    Button botonFaseSiguiente; //Boton para cambiar de fase a la subasta
    Button botonSuspender; //Boton para suspender la subasta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_subasta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // boton que permite acceder a las posturas de una subasta
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.BotonContinuar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PantallaSubasta.this, PantallaPosturas.class);
                intent.putExtra("subasta",subasta.getTitulo()); // Se pasa por parametro el titulo de la subasta al intent
                startActivity(intent);
            }
        });

        //Inicializacion de servicios
        servicioSubasta = new ServicioSubasta(Realm.getDefaultInstance());
        servicioUsuarioActual = new ServicioUsuarioActual(Realm.getDefaultInstance());
        servicioPostulacion = new ServicioPostulacion(Realm.getDefaultInstance());

        //Generar la subasta en la que se esta
        String tituloSubasta = getIntent().getStringExtra("tituloSubasta");// Se trae el titulo por parametro
        subasta = servicioSubasta.obtenerSubastaPorTitulo(tituloSubasta);

        //Generar el usuario que esta logueado
        user = servicioUsuarioActual.obtenerUsuarioActual();

        //Agsignar los contenedores de datos de la subasta
        TextView titulo = (TextView) findViewById(R.id.tituloSubasta);
        TextView estado = (TextView) findViewById(R.id.estadoSubasta);
        TextView fecha = (TextView) findViewById(R.id.fechaSubasta);
        TextView hora = (TextView) findViewById(R.id.horaSubasta);
        TextView martilero = (TextView) findViewById(R.id.martilleroSubasta);
        TextView vendedor = (TextView) findViewById(R.id.vendedorSubasta);
        TextView precio = (TextView) findViewById(R.id.precioSubasta);
        TextView posturaMinima = (TextView) findViewById(R.id.posturaMinimaSubasta);
        TextView descripcion = (TextView) findViewById(R.id.descripcionSubasta);

        //Cambiar el contenido de los contenedores a lo que dice la subasta
        titulo.setText(subasta.getTitulo());
        estado.setText(subasta.getFase());
        fecha.setText(subasta.getFecha());
        hora.setText(subasta.getHoraInicio());
        martilero.setText(subasta.getMartillero());
        vendedor.setText(subasta.getVendedor());
        precio.setText(String.valueOf(subasta.getValorInicial()));
        posturaMinima.setText(String.valueOf(subasta.getPosturaMinima()));
        AppBarLayout barra = (AppBarLayout) findViewById(R.id.app_bar_Subasta);
        barra.setBackgroundResource(subasta.getImagenPrincipal());
        descripcion.setText(subasta.getDescripcion());

        //Mostrar todas las imagenes de la subasta
        listaImagenes = subasta.getImagenes();
        imagenes = (RecyclerView) findViewById(R.id.RVImagenes);
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false); // Layout Horizontal para el RV
        imagenes.setLayoutManager(llm);
        ImageRVAdapter adapter = new ImageRVAdapter(listaImagenes,this); // adaptador de las imagenes al RV
        imagenes.setAdapter(adapter);

        //Asignar los botones a sus variables
        botonPostularse = (Button) findViewById(R.id.botonPostularse);
        botonFaseSiguiente = (Button) findViewById(R.id.botonFaseSiquiente);
        botonSuspender = (Button) findViewById(R.id.botonSuspender);

        //Verificar si el usuario actual esta postulado
        estaPostulado = servicioPostulacion.estaPostuladoUsuario(user.getUsuario(),subasta.getTitulo());
        if (estaPostulado){
            botonPostularse.setText(getResources().getString(R.string.salirse));
        }

        //Trabajar con la estrategia del usuario
        user.pantallaSubasta(botonPostularse,botonFaseSiguiente,botonSuspender,estaPostulado || user.getUsuario().equals(subasta.getMartillero()));

        //Trabajar con el estado de la subasta
        subasta.pantallaSubasta(fab,botonPostularse,botonFaseSiguiente,botonSuspender);
    }

        /*
    Descripcion general: Permite al usuario postularse y salirse de una subasta siempre y cuando este no se encuente suspendido
    */
    public void postularse(View v){
        if(!user.isSuspendido()){
            estaPostulado = servicioPostulacion.estaPostuladoUsuario(user.getUsuario(),subasta.getTitulo());
            if(!estaPostulado){
                servicioPostulacion.crearPostulacion(user.getUsuario(),subasta.getTitulo());
                botonPostularse.setText(getResources().getString(R.string.salirse));
                estaPostulado = true;
                Toast.makeText(this,R.string.postulacion,Toast.LENGTH_SHORT).show();
            }
            else{
                servicioPostulacion.eliminarPostulacion(user.getUsuario(),subasta.getTitulo());
                ServicioPostura servicioPostura = new ServicioPostura(Realm.getDefaultInstance());
                servicioPostura.eliminarPosturasUsuario(user.getUsuario());
                botonPostularse.setText(getResources().getString(R.string.postularse));
                Toast.makeText(this,R.string.quitarPostulacion,Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this,R.string.usuarioSuspendido,Toast.LENGTH_SHORT).show();
        }

    }

    /*
    Descripcion general: Permite al martillero cambiar la fase de la subasta a la siguiente
    */
    public void cambiarFase(View v){
        subasta.cambiarFase(subasta,servicioSubasta);
        finish();
    }

    /*
    Descripcion general: Permite al martillero suspender la subasta
*/
    public void suspender(View v){
        subasta.suspender(subasta,servicioSubasta);
        finish();
    }
}
