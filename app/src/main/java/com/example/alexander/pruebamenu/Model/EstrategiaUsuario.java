package com.example.alexander.pruebamenu.Model;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Interfaz que define los metodos que deben ser implementados en cada una de las estrategias de usuario
*/

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.pruebamenu.Realm.ServicioSubasta;

import java.util.List;

public interface EstrategiaUsuario {
    public void pantallaSubasta(Button postularse, Button cambiarFase,Button suspender,boolean esMartilleroPostor);
    public void pantallaPostura(RecyclerView rv, FloatingActionButton pausar, ConstraintLayout lObservador, ConstraintLayout lmartillero, ConstraintLayout lPostor, TextView mensaje,boolean pausada, boolean esMartilleroPostor);
    public List<Subasta> obtenerSubastas(ServicioSubasta servicioSubasta, String usuario);
}
