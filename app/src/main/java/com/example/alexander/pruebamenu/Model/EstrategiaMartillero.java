package com.example.alexander.pruebamenu.Model;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Clase para manejar la estrategia al ser el usuario un Martillero
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

public class EstrategiaMartillero implements EstrategiaUsuario {

    /*
    Descripcion general: Muestra los bones para configurar si la subasta pertenece al martillero y oculta los de posturas
    Parametros: botones a mostrar/ocultar y un booleano que dice si la subasta pertenece o no al martillero
    */
    @Override
    public void pantallaSubasta(Button postularse, Button cambiarFase, Button suspender,boolean esMartillero) {
        postularse.setVisibility(View.GONE);
        if (esMartillero){
            cambiarFase.setVisibility(View.VISIBLE);
            suspender.setVisibility(View.VISIBLE);
        }
        else{
            cambiarFase.setVisibility(View.GONE);
            suspender.setVisibility(View.GONE);
        }
    }

    /*
    Descripcion general: Oculta los contenedores para hacer posturas y despliega un mensaje en caso de que la subasta no pertenezca
                         al martillero. ademas muestra e boton para pausar/renaudar si es el martillero de la subasta
    Parametros: Contenedores a mostrar/ocultar, el boton para pausar/renaudar y el booleano que indica si la subasta pertence o no al martillero
    */
    @Override
    public void pantallaPostura(RecyclerView rv, FloatingActionButton pausar, ConstraintLayout lObservador, ConstraintLayout lmartillero, ConstraintLayout lPostor,TextView mensaje,boolean pausada,boolean esMartillero) {
        lObservador.setVisibility(View.GONE);
        lPostor.setVisibility(View.GONE);
        if(esMartillero){
            pausar.setVisibility(View.VISIBLE);
            lmartillero.setVisibility(View.VISIBLE);
        }
        else{
            pausar.setVisibility(View.GONE);
            lmartillero.setVisibility(View.GONE);
            lObservador.setVisibility(View.VISIBLE);
            mensaje.setText(R.string.no_martillero);
        }
    }

    /*Descripcion general: Genera una lista con las subastas que pertenecen al martillero
    Parametros: El servicio para las subastas y el nombre del usuario actual
    */
    @Override
    public List<Subasta> obtenerSubastas(ServicioSubasta servicioSubasta, String usuario) {
        return servicioSubasta.obtenerSubastasMartillero(usuario);
    }
}
