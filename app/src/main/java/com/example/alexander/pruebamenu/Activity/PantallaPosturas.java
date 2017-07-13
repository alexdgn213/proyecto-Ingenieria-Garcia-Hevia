package com.example.alexander.pruebamenu.Activity;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Actividad que permite ver las posturas de una subasta, realizar posturas a los postores postulados y
                     pausarla a los martilleros
*/

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexander.pruebamenu.Adapter.EmptyStateRVAdapter;
import com.example.alexander.pruebamenu.Adapter.PosturaRVAdapter;
import com.example.alexander.pruebamenu.Model.Postura;
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

public class PantallaPosturas extends AppCompatActivity {
    ServicioSubasta servicioSubasta; //Coneccion con realm para trabajar con subastas
    ServicioPostura servicioPostura; //Coneccion con realm para trabajar con posturas
    ServicioUsuarioActual servicioUsuarioActual; //Coneccion con realm para trabajar con el usuario Actual
    UsuarioActual user; //Usuario logueado en la aplicacion
    Subasta subasta; // Subasta a la que pertenecen las posturas
    List<Postura> posturas; //Lista de posturas de la subasta
    RecyclerView rvPosturas; // Contenedor de las posturas
    TextView titulo; // Contenedor con el titulo de la subasta
    ImageView fondo; // Contenedor con la imagen principal de una subasta
    TextView valorMinimo; // Contenedor con el valor minimo de la posturas
    TextView valorInicial; // Contenedor con el valor inicial de la subastas
    EditText nuevoValor; // Contenedor con el valor de la nueva postura
    ConstraintLayout pantallaPostor; // Contenedor con los elementos necesarios para hacer una postura
    ConstraintLayout pantallaObservador; // Contenedor para aquellos que no pueden realizar posturas
    ConstraintLayout pantallaAnimacion; //Contenedor con una animacion para cuando esta finalizada una subasta
    ConstraintLayout pantallaMartillero; //
    FloatingActionButton fvPausar; //Boton para pausar o renaudar una subasta
    private AnimationDrawable frameAnimationAdjudicado; //Animacion
    ImageView animacion; // Contenedor de la animacion
    TextView textoAnimacion; // Contenedor del texto de la animacion
    TextView textoObservador; // Contenedor del mensaje por el que el usuario no puede hacer posturas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_posturas);

        //Asignar cada variable a su componente en el xlm
        titulo = (TextView) findViewById(R.id.TituloPosturas);
        fondo = (ImageView) findViewById(R.id.imagenPosturas);
        valorMinimo = (TextView) findViewById(R.id.MinimaPosturas);
        valorInicial = (TextView) findViewById(R.id.ValorInicial);
        pantallaPostor = (ConstraintLayout) findViewById(R.id.lPostor);
        pantallaObservador = (ConstraintLayout) findViewById(R.id.lObservador);
        pantallaAnimacion = (ConstraintLayout) findViewById(R.id.contenedorAnimacion);
        pantallaMartillero = (ConstraintLayout) findViewById(R.id.lMartillero);
        animacion = (ImageView) findViewById(R.id.imagenAnimacionPostura);
        textoAnimacion = (TextView) findViewById(R.id.textoAnimacionPostura);
        textoObservador = (TextView) findViewById(R.id.mensajePostura);
        fvPausar = (FloatingActionButton) findViewById(R.id.fAPausar);
        nuevoValor = (EditText) findViewById(R.id.NuevaPostura);

        //Iniciar los servicios
        servicioPostura = new ServicioPostura(Realm.getDefaultInstance());
        servicioUsuarioActual = new ServicioUsuarioActual(Realm.getDefaultInstance());
        servicioSubasta = new ServicioSubasta(Realm.getDefaultInstance());
        ServicioPostulacion servicioPostulacion = new ServicioPostulacion(Realm.getDefaultInstance());

        //Generar el usuario que esta logueado
        user = servicioUsuarioActual.obtenerUsuarioActual();

        //Generar la subasta en la que se esta
        String tituloSubasta = getIntent().getStringExtra("subasta"); // Se trae el titulo por parametro
        subasta = servicioSubasta.obtenerSubastaPorTitulo(tituloSubasta);

        //Asignar los contenedores de datos de la subasta
        titulo.setText(subasta.getTitulo());
        fondo.setImageResource(subasta.getImagenPrincipal());
        valorMinimo.setText(String.valueOf(subasta.getPosturaMinima()));
        valorInicial.setText(String.valueOf(subasta.getValorInicial()));

        //Mostrar todas las posturas de la subasta
        posturas = subasta.getPosturas();
        rvPosturas = (RecyclerView) findViewById(R.id.RVPosturas);
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);  // Layout vertical invertido para el RV
        rvPosturas.setLayoutManager(llm);

        cargarPosturas();

        //Trabajar con la estrategia del usuario
        user.pantallaPostura(rvPosturas,fvPausar,pantallaObservador,pantallaMartillero,pantallaPostor,textoObservador,subasta.isPausada(),subasta.getMartillero().equals(user.getUsuario())||servicioPostulacion.estaPostuladoUsuario(user.getUsuario(),subasta.getTitulo()));

        //Trabajar con el estado de la subasta
        Postura mejorPostura = servicioPostura.obtenerMejorPostura(subasta.getTitulo());//Se busca la mayor postura
        if(mejorPostura!=null) {
            subasta.pantallaPostura(pantallaObservador, textoObservador, pantallaAnimacion, pantallaPostor, mejorPostura.getPostor().equals(user.getUsuario()), frameAnimationAdjudicado, animacion, textoAnimacion);
        }

        //Modificar el boton de pausar/renaudar concorde al estado de la subasta
        if(subasta.isPausada()) fvPausar.setImageResource(R.drawable.renaudar);
        else fvPausar.setImageResource(R.drawable.pausar);
    }

    /*
    Descripcion general: Agrega una postura siempre y cuando sea valida
    */
    public void agregarPostura(View v){
        String nuevo = nuevoValor.getText().toString();
        Postura mejorPostura = servicioPostura.obtenerMejorPostura(subasta.getTitulo()); // mejor postura hasta ahora
        //Verifico que se haya ingresado un valor
        if(nuevo.length()>0){
            int valor = Integer.parseInt(nuevo);
            //Verifico que existan posturas
            if(mejorPostura!=null) {
                //Verifico que el valor ingresado sea mayor al exigido
                if (valor >= mejorPostura.getValor() + subasta.getPosturaMinima()) {
                    servicioPostura.crearPostura(subasta.getTitulo(), user.getUsuario(), valor);
                    posturas = subasta.getPosturas();
                    cargarPosturas();
                }else{
                    Toast.makeText(this,R.string.mensaje_error_postura,Toast.LENGTH_SHORT).show();

                }
            }
            else{
                //Verifico que el valor ingresado es mayo al exigido
                if (valor >= subasta.getValorInicial()) {
                    servicioPostura.crearPostura(subasta.getTitulo(), user.getUsuario(), valor);
                    posturas = subasta.getPosturas();
                    cargarPosturas();
                }
            }
        }
        else{
            Toast.makeText(this,R.string.mensaje_postura_vacia,Toast.LENGTH_SHORT).show();
        }

    }

    /*
    Descripcion general: Pausa o renauda una subasta cambiando en el boton el icono segun aplique
    */
    public void pausarRenaudarSubasta(View v){
        servicioSubasta.pausaRenaudarSubasta(subasta);
        if(subasta.isPausada()) {
            Toast.makeText(this,R.string.mensaje_subasta_pausada,Toast.LENGTH_SHORT).show();
            fvPausar.setImageResource(R.drawable.renaudar);
        }
        else {
            Toast.makeText(this,R.string.mensaje_subasta_renaudada,Toast.LENGTH_SHORT).show();
            fvPausar.setImageResource(R.drawable.pausar);
        }
    }

    /*
    Descripcion general: Carga todas las posturas al RV
    */
    public void cargarPosturas(){
        if(posturas.size()>0){
            PosturaRVAdapter adapter = new PosturaRVAdapter(posturas,this);
            rvPosturas.setAdapter(adapter);
        }
        else{
            EmptyStateRVAdapter adapter = new EmptyStateRVAdapter(this);
            rvPosturas.setAdapter(adapter);
        };
    }

    /*
    Descripcion general: Agrega una postura siempre y cuando sea valida
    */
    public void cerrarAnimacion(View v){
        pantallaAnimacion.setVisibility(View.GONE);
    }

}
