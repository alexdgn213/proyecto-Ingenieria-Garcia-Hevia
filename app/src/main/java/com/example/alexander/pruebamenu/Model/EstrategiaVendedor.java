package com.example.alexander.pruebamenu.Model;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Clase para manejar la estrategia al ser el usuario un Vendedor
*/

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alexander.pruebamenu.R;
import com.example.alexander.pruebamenu.Realm.ServicioSubasta;

import java.util.List;

public class EstrategiaVendedor implements EstrategiaUsuario {

    /*
    Descripcion general: Oculta todos los botones
    Parametros: botones a mostrar/ocultar
    */
    @Override
    public void pantallaSubasta(Button postularse, Button cambiarFase, Button suspender, boolean esPostor) {
        postularse.setVisibility(View.GONE);
        cambiarFase.setVisibility(View.GONE);
        suspender.setVisibility(View.GONE);
    }

    /*
    Descripcion general: Muestra un mensaje de que los vendedores no pueden realizar posturas y oculta
                         todas los otros contenedores  y botones
    Parametros: Contenedores a mostrar/ocultar, el boton para pausar/renaudar
    */
    @Override
    public void pantallaPostura(RecyclerView rv, FloatingActionButton pausar, ConstraintLayout lObservador, ConstraintLayout lmartillero, ConstraintLayout lPostor, TextView mensaje,boolean pausada, boolean esPostor) {
        pausar.setVisibility(View.GONE);
        lmartillero.setVisibility(View.GONE);
        lObservador.setVisibility(View.VISIBLE);
        mensaje.setText(R.string.es_vendedor);
        lPostor.setVisibility(View.GONE);
    }

    /*Descripcion general: Genera una lista con las subastas al que pertenecen a este vendedor
    Parametros: El servicio para las subastas y el nombre del usuario actual
    */
    @Override
    public List<Subasta> obtenerSubastas(ServicioSubasta servicioSubasta, String usuario) {
        return servicioSubasta.obtenerSubastasVendedor(usuario);
    }
}
