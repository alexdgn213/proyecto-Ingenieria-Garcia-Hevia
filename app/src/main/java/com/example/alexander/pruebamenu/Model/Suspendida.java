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

import com.example.alexander.pruebamenu.Realm.ServicioSubasta;

public class Suspendida implements EstadoFase {

    /*Descripcion general: oculta todos los botones
    Parametros: botones a mostrar/ocultar
    */
    @Override
    public void mostrarOcultarSubasta(FloatingActionButton acceder, Button postularse, Button cambiarFase, Button suspender) {
        acceder.setVisibility(View.GONE);
        postularse.setVisibility(View.GONE);
        cambiarFase.setVisibility(View.GONE);
        suspender.setVisibility(View.GONE);
    }

    @Override
    public void mostrarOcultarPosturas(ConstraintLayout lmensaje, TextView mensaje, ConstraintLayout fondoAnimacion, ConstraintLayout lpostura, ConstraintLayout lmatrillero, boolean gano, AnimationDrawable animacion, ImageView imagenAnimacion, TextView textoAnimacion) {

    }

    @Override
    public void cambiarFase(Subasta s, ServicioSubasta servicioSubasta) {
    }

    @Override
    public void suspender(Subasta s, ServicioSubasta servicioSubasta) {
    }
}
