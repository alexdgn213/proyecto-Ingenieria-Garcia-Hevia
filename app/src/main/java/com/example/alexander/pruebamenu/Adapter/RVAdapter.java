package com.example.alexander.pruebamenu.Adapter;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Adaptador que permite generar subastas en un RecyclerView a partir de una lista
*/

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.pruebamenu.Activity.PantallaSubasta;
import com.example.alexander.pruebamenu.Model.Usuario;
import com.example.alexander.pruebamenu.Model.UsuarioActual;
import com.example.alexander.pruebamenu.R;
import com.example.alexander.pruebamenu.Model.Subasta;
import com.example.alexander.pruebamenu.Realm.ServicioFavorito;
import com.example.alexander.pruebamenu.Realm.ServicioLike;
import com.example.alexander.pruebamenu.Realm.ServicioPostura;
import com.example.alexander.pruebamenu.Realm.ServicioSubasta;

import java.util.List;

import io.realm.Realm;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.subastaHolder>{

    Context context;
    List<Subasta> subastas;
    UsuarioActual usuario;
    ServicioFavorito servicioFavorito;
    ServicioLike servicioLike;

    public static class subastaHolder extends RecyclerView.ViewHolder {
        static CardView cv;
        static TextView titulo;
        static Toolbar toolbarCard;
        static ImageView imagen;
        static ImageButton boton;
        static ImageButton like;
        static ImageButton favorito;
        static TextView fecha;
        static TextView modalidad;
        static TextView precio;
        static TextView descripcion;



        subastaHolder(View itemView) {
            // aqui van todos los elementos del cardView que van a ser modificados
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card);
            toolbarCard = (Toolbar) itemView.findViewById(R.id.tituloCard);
            imagen = (ImageView) itemView.findViewById(R.id.fotoPrincipal);
            boton = (ImageButton) itemView.findViewById(R.id.ampliar);
            like = (ImageButton) itemView.findViewById(R.id.meGusta);
            favorito = (ImageButton) itemView.findViewById(R.id.favorito);
            fecha = (TextView) itemView.findViewById(R.id.fechaHora);
            modalidad = (TextView) itemView.findViewById(R.id.modalidad);
            precio = (TextView) itemView.findViewById(R.id.precio);
            descripcion = (TextView) itemView.findViewById(R.id.textoDecripcion);


        }
        TextView getTitulo(){
            return titulo;
        }
    }

    public RVAdapter(List<Subasta> subastas, UsuarioActual usuario, Context context){
        this.subastas = subastas;
        this.context = context;
        this.usuario = usuario;
        this.servicioFavorito = new ServicioFavorito(Realm.getDefaultInstance());
        this.servicioLike = new ServicioLike(Realm.getDefaultInstance());
    }

    @Override
    public RVAdapter.subastaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        subastaHolder pvh = new subastaHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(RVAdapter.subastaHolder holder, final int position) {
        //Aqui van todas las modificaciones especificas de cada elemento
        Subasta s = subastas.get(position);
        subastaHolder.toolbarCard.setTitle(s.getTitulo());
        subastaHolder.fecha.setText(s.getFecha()+" "+s.getHoraInicio());
        subastaHolder.precio.setText(String.valueOf(s.getValorInicial()));
        subastaHolder.modalidad.setText(s.getTipo());
        subastaHolder.descripcion.setText(s.getDescripcion());
        subastaHolder.imagen.setImageResource(subastas.get(position).getImagenPrincipal());
        subastaHolder.toolbarCard.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PantallaSubasta.class);
                intent.putExtra("tituloSubasta", subastas.get(position).getTitulo());
                context.startActivity(intent);
            }
        });
        subastaHolder.cv.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PantallaSubasta.class);
                intent.putExtra("tituloSubasta", subastas.get(position).getTitulo());
                context.startActivity(intent);
            }
        });
        subastaHolder.boton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                ConstraintLayout fondo = (ConstraintLayout) view.getParent();
                ConstraintLayout linearLayoutDetails = (ConstraintLayout) fondo.findViewById(R.id.contenido);
                ImageButton imageViewExpand = (ImageButton) fondo.findViewById(R.id.ampliar);
                if (linearLayoutDetails.getVisibility() == View.GONE) {
                    linearLayoutDetails.setVisibility(View.VISIBLE);
                    imageViewExpand.setImageResource(R.drawable.detalles_less);
                } else {
                    linearLayoutDetails.setVisibility(View.GONE);
                    imageViewExpand.setImageResource(R.drawable.detalles);
                }
            }
        });

        if (subastas.get(position).isEsLike()){
            subastaHolder.like.setImageResource(R.drawable.es_me_gusta);
            subastaHolder.like.setTag(R.drawable.es_me_gusta);
        }
        else{
            subastaHolder.like.setImageResource(R.drawable.me_gusta);
            subastaHolder.like.setTag(R.drawable.me_gusta);
        }
        if (subastas.get(position).isEsFavorito()) {
            subastaHolder.favorito.setImageResource(R.drawable.es_favorito);
            subastaHolder.favorito.setTag(R.drawable.es_favorito);
        }
        else {
            subastaHolder.favorito.setImageResource(R.drawable.favorito);
            subastaHolder.favorito.setTag(R.drawable.favorito);
        }

        subastaHolder.like.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                ConstraintLayout fondo = (ConstraintLayout) view.getParent();
                ImageButton botonLike = (ImageButton) fondo.findViewById(R.id.meGusta);
                Toolbar titulo = (Toolbar) fondo.findViewById(R.id.tituloCard);
                String nombreUsuario = usuario.getUsuario();
                if ((Integer)botonLike.getTag()==R.drawable.es_me_gusta) {
                    botonLike.setImageResource(R.drawable.me_gusta);
                    botonLike.setTag(R.drawable.me_gusta);
                    servicioLike.eliminarLikeUsuario((String)titulo.getTitle(),nombreUsuario);
                } else {
                    botonLike.setImageResource(R.drawable.es_me_gusta);
                    botonLike.setTag(R.drawable.es_me_gusta);
                    servicioLike.crearLike((String)titulo.getTitle(),nombreUsuario);
                }
            }
        });

        subastaHolder.favorito.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                ConstraintLayout fondo = (ConstraintLayout) view.getParent();
                Toolbar titulo = (Toolbar) fondo.findViewById(R.id.tituloCard);
                String nombreUsuario = usuario.getUsuario();
                ImageButton botonFavorito = (ImageButton) fondo.findViewById(R.id.favorito);
                if ((Integer) botonFavorito.getTag()==R.drawable.es_favorito) {
                    botonFavorito.setImageResource(R.drawable.favorito);
                    botonFavorito.setTag(R.drawable.favorito);
                    servicioFavorito.eliminarFavoritoUsuario((String)titulo.getTitle(),nombreUsuario);
                } else {
                    botonFavorito.setImageResource(R.drawable.es_favorito);
                    botonFavorito.setTag(R.drawable.es_favorito);
                    servicioFavorito.crearFavorito((String)titulo.getTitle(),nombreUsuario);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subastas.size();
    }

}
