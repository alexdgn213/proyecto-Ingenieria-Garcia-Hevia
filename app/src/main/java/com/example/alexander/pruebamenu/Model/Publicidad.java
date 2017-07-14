package com.example.alexander.pruebamenu.Model;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Clase para manejar los metodos de una subasta en estado de Publicidad
*/

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.pruebamenu.Realm.ServicioSubasta;

public class Publicidad implements EstadoFase {

    /*
   Descripcion general: oculta el boton para acceder a las posturas
   Parametros: botones a mostrar/ocultar
   */
    @Override
    public void mostrarOcultarSubasta(FloatingActionButton acceder, Button postularse, Button cambiarFase, Button suspender) {
        acceder.setVisibility(View.GONE);
    }

    @Override
    public void mostrarOcultarPosturas(ConstraintLayout lmensaje, TextView mensaje, ConstraintLayout fondoAnimacion, ConstraintLayout lpostura, ConstraintLayout lmatrillero, boolean gano, AnimationDrawable animacion, ImageView imagenAnimacion, TextView textoAnimacion) {

    }

    /*
    Descripcion general: Cambia una subasta de Publicidad a EnCurso
    Parametros: la subasta y la coneccion a realm para realizar los cambios
    */
    @Override
    public void cambiarFase(Subasta s, ServicioSubasta servicioSubasta) {
        s.setFaseActual(new EnCurso());
        servicioSubasta.actualizarSubastaFase(s,"EnCurso");
    }

    /*
   Descripcion general: suspende una subasta en estado de Publicidad
   Parametros: la subasta y la coneccion a realm para realizar los cambios
   */
    @Override
    public void suspender(Subasta s, ServicioSubasta servicioSubasta) {
        s.setFaseActual(new Finalizada());
        servicioSubasta.actualizarSubastaFase(s,"Suspendida");
    }
}
