package com.example.alexander.pruebamenu.Activity;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Actividad de inicio la cual comprueba si existe un usuario logeado, de ser asi continua con una animacion de carga
                     (la cual puede ser aprovechada para descargar e iniciar datos posteriormente), por el contario permite al usuario iniciar sesion
                      y luego coloca la animacion;
*/

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alexander.pruebamenu.Model.Usuario;
import com.example.alexander.pruebamenu.Model.UsuarioActual;
import com.example.alexander.pruebamenu.R;
import com.example.alexander.pruebamenu.Realm.IniciarDatos;
import com.example.alexander.pruebamenu.Realm.ServicioUsuario;
import com.example.alexander.pruebamenu.Realm.ServicioUsuarioActual;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PantallaCarga extends AppCompatActivity {
    private ImageView imgCargando; //Contenedor de la animacion de cargando
    private AnimationDrawable frameAnimationCargando; //Animacion
    private LinearLayout inicio; //Contenedor de los campos para inicio de sesion
    private EditText usuario; //Contenedor para ingresar el usuario
    private EditText clave; //Contenedor para ingresar la clave
    private TextView mensajeError; //Contenedor del mensaje en caso de que el usuario sea incorrecto
    private TextView mensajeErrorUsuario; //Contenedor del mensaje en caso de que la clave sea incorrecta
    private ServicioUsuario servicioUsuario; //Coneccion con realm para trabajar los usuarios
    private ServicioUsuarioActual servicioUsuarioActual; //Coneccion con realm para trabajar con el usuario actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_carga);

        //Asignar cada variable a su componente en el xlm
        imgCargando = (ImageView)findViewById(R.id.loadingView);
        imgCargando.setBackgroundResource(R.drawable.cargando);
        frameAnimationCargando = (AnimationDrawable) imgCargando.getBackground();
        inicio = (LinearLayout)findViewById(R.id.inicio);
        usuario = (EditText) findViewById(R.id.usuario);
        clave = (EditText) findViewById(R.id.clave);
        mensajeError = (TextView)findViewById(R.id.mensajeInicioSesionClave);
        mensajeErrorUsuario = (TextView)findViewById(R.id.mensajeInicioSesionUsuario);


        //Inicializando Realm
        Realm.init(this);
        //CofigurarRealm
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("configuracionRealm5")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        //Insertar si datos generados por los integrantes para probar la aplicacion
        IniciarDatos inicio = new IniciarDatos();

        //Iniciar los servicios
        servicioUsuario = new ServicioUsuario(Realm.getDefaultInstance());
        servicioUsuarioActual = new ServicioUsuarioActual(Realm.getDefaultInstance());

        //Comprobar si hay un usuario logeado
        UsuarioActual usuarioActual = servicioUsuarioActual.obtenerUsuarioActual();
        if(usuarioActual.getUsuario().length()>0){
            inicio();// Si esta logeado accede directamente
        }

    }

    /*
    Descripcion general: Metodo que verifica el inicio de sesion por parte de un usuario
    */
    public void acceder(View view){
        String nombreUsuario = usuario.getText().toString();
        String claveUruario = clave.getText().toString();
        Usuario nuevo = servicioUsuario.obtenerUsuarioPorUsuario(nombreUsuario);
        mensajeError.setVisibility(View.GONE);
        mensajeErrorUsuario.setVisibility(View.GONE);
        if(nuevo!=null){
            if (nuevo.getClave().equals(claveUruario)){
                servicioUsuarioActual.iniciarSesion(nuevo);
                inicio();
            }
            else{
                mensajeError.setVisibility(View.VISIBLE);
            }
        }else{
            mensajeErrorUsuario.setVisibility(View.VISIBLE);
        }
    }

    /*
    Descripcion general: Metodo que inicia la animacion de carga y que pasa a la siguiente actividad
    */
    public void inicio(){
        //Inicio de animacion de carga
        frameAnimationCargando.start();
        inicio.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PantallaCarga.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
