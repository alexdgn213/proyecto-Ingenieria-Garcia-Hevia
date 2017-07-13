package com.example.alexander.pruebamenu.Model;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Clase para manejar la estrategia al ser el usuario un Postor
*/

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.pruebamenu.R;
import com.example.alexander.pruebamenu.Realm.ServicioSubasta;

import java.util.List;

public class EstrategiaPostor implements EstrategiaUsuario {

    /*
    Descripcion general: Muestra el boton de postularse y oculta los demas
    Parametros: botones a mostrar/ocultar y un booleano que dice si la el usuario esta postulado a la subasta
    */
    @Override
    public void pantallaSubasta(Button postularse, Button cambiarFase, Button suspender,boolean esPostor) {
        postularse.setVisibility(View.VISIBLE);
        cambiarFase.setVisibility(View.GONE);
        suspender.setVisibility(View.GONE);
    }

    /*
    Descripcion general: Muestra los contenedores para hacer posturas en caso de que la misma no este pausada
                         y despliega un mensaje en caso de que el postor no este postulado a la subasta.
                         ademas oculta el boton para pausar/renaudar
    Parametros: Contenedores a mostrar/ocultar, el boton para pausar/renaudar y el booleano que indica si
                el usuario esta postulado y otro que indica si la subasta esta pausada
    */
    @Override
    public void pantallaPostura(RecyclerView rv, FloatingActionButton pausar, ConstraintLayout lObservador, ConstraintLayout lmartillero, ConstraintLayout lPostor,TextView mensaje,boolean pausada,boolean esPostor) {
        pausar.setVisibility(View.GONE);
        lmartillero.setVisibility(View.GONE);
        if (pausada){
            lObservador.setVisibility(View.VISIBLE);
            mensaje.setText(R.string.subastaPausada);
            lPostor.setVisibility(View.GONE);
        }
        else {
            if (esPostor) {
                lObservador.setVisibility(View.GONE);
                lPostor.setVisibility(View.VISIBLE);
            } else {
                lObservador.setVisibility(View.VISIBLE);
                mensaje.setText(R.string.noPostulado);
                lPostor.setVisibility(View.GONE);
            }
        }
    }

    /*Descripcion general: Genera una lista con las subastas al que el usuario esta postulado
    Parametros: El servicio para las subastas y el nombre del usuario actual
    */
    @Override
    public List<Subasta> obtenerSubastas(ServicioSubasta servicioSubasta, String usuario) {
        return servicioSubasta.obtenerSubastasPostulado(usuario);
    }
}
