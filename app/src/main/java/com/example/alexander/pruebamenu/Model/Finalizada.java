package com.example.alexander.pruebamenu.Model;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Clase para manejar los metodos de una subasta en estado de Finalizada
*/


import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.pruebamenu.R;
import com.example.alexander.pruebamenu.Realm.ServicioSubasta;

public class Finalizada implements EstadoFase{

    /*
    Descripcion general: oculta el boton para acceder a las posturas pero oculta todos los demas
    Parametros: botones a mostrar/ocultar
    */
    @Override
    public void mostrarOcultarSubasta(FloatingActionButton acceder, Button postularse, Button cambiarFase, Button suspender) {
        acceder.setVisibility(View.VISIBLE);
        postularse.setVisibility(View.GONE);
        cambiarFase.setVisibility(View.GONE);
        suspender.setVisibility(View.GONE);
    }

    /*
    Descripcion general: Oculta los contenedores para hacer posturas y despliega un mensaje de que la subasta ha finalizado,
                         ademas genera una animacion de que la subasta a finalizado que varia segun si el usuario gano o no
    Parametros: Contenedores a mostrar/ocultar y la animacion junto con su contenedor y texto/. ademas un booleano que es true
                si el usuario gano la subasta o false si perdio o no participo
    */
    @Override
    public void mostrarOcultarPosturas(ConstraintLayout lmensaje, TextView mensaje, ConstraintLayout fondoAnimacion, ConstraintLayout lpostura, ConstraintLayout lmatrillero, boolean gano, AnimationDrawable animacion, ImageView imagenAnimacion, TextView textoAnimacion) {
        lmensaje.setVisibility(View.VISIBLE);
        lpostura.setVisibility(View.GONE);
        lmatrillero.setVisibility(View.GONE);
        mensaje.setText(R.string.estadoFinalizada);
        fondoAnimacion.setVisibility(View.VISIBLE);
        //Compruebo si el usuario gano
        if(gano) {
            imagenAnimacion.setBackgroundResource(R.drawable.adjudicado);
            textoAnimacion.setText(R.string.adjudicado);
        }
        else {
            imagenAnimacion.setBackgroundResource(R.drawable.finalizado);
            textoAnimacion.setText(R.string.finalizado);
        }
        animacion = (AnimationDrawable) imagenAnimacion.getBackground();
        animacion.start();
    }

    @Override
    public void cambiarFase(Subasta s, ServicioSubasta servicioSubasta) {
    }

    @Override
    public void suspender(Subasta s, ServicioSubasta servicioSubasta) {
    }
}
