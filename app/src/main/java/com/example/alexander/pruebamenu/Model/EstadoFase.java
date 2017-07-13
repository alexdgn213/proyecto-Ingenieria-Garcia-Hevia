package com.example.alexander.pruebamenu.Model;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Interfaz que define los metodos que deben ser implementados en cada uno de los estados de una subasta
*/

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.pruebamenu.Realm.ServicioSubasta;

public interface EstadoFase {

    public void mostrarOcultarSubasta(FloatingActionButton acceder, Button postularse, Button cambiarFase, Button suspender);
    public void mostrarOcultarPosturas(ConstraintLayout lmensaje, TextView mensaje, ConstraintLayout fondoAnimacion, ConstraintLayout lpostura, boolean gano, AnimationDrawable animacion, ImageView imagenAnimacion,TextView textoAnimacion);
    public void cambiarFase(Subasta s, ServicioSubasta servicioSubasta);
    public void suspender(Subasta s, ServicioSubasta servicioSubasta);

}
