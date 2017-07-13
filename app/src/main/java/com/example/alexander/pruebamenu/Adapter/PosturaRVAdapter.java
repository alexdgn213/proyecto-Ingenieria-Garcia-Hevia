package com.example.alexander.pruebamenu.Adapter;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Adaptador que permite generar posturas en un RecyclerView a partir de una lista
*/

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.pruebamenu.Activity.PantallaImagenes;
import com.example.alexander.pruebamenu.Model.Imagen;
import com.example.alexander.pruebamenu.Model.Postura;
import com.example.alexander.pruebamenu.Model.UsuarioActual;
import com.example.alexander.pruebamenu.R;
import com.example.alexander.pruebamenu.Realm.ServicioUsuario;
import com.example.alexander.pruebamenu.Realm.ServicioUsuarioActual;

import java.util.List;

import io.realm.Realm;

public class PosturaRVAdapter extends RecyclerView.Adapter<PosturaRVAdapter.PosturaHolder> {
    Context context;
    List<Postura> posturas;
    final ServicioUsuarioActual servicioUsuarioActual=new ServicioUsuarioActual(Realm.getDefaultInstance());
    final UsuarioActual user = servicioUsuarioActual.obtenerUsuarioActual();
    ServicioUsuario servicioUsuario = new ServicioUsuario(Realm.getDefaultInstance());

    public static class PosturaHolder extends RecyclerView.ViewHolder {
        static CardView cv;
        static ImageView imagen;
        static TextView usuario;
        static TextView valor;

        PosturaHolder(View itemView) {
            // aqui van todos los elementos del cardView que van a ser modificados
            super(itemView);
        }

    }

    public int getItemViewType(int position) {
        Postura p = posturas.get(position);
        int viewType = 1; //Default is 1
        if (p.getPostor().equals(this.user.getUsuario())) viewType = 0; //if zero, it will be a header view
        return viewType;
    }

    public PosturaRVAdapter(List<Postura> posturas, Context context){
        this.posturas = posturas;
        this.context = context;
    }

    @Override
    public PosturaRVAdapter.PosturaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==1){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.postura_card, parent, false);
            PosturaRVAdapter.PosturaHolder pvh = new PosturaRVAdapter.PosturaHolder(v);
            pvh.cv = (CardView) v.findViewById(R.id.cardPostura);
            pvh.imagen = (ImageView) v.findViewById(R.id.ImagenPostura);
            pvh.usuario = (TextView) v.findViewById(R.id.UsuarioPostura);
            pvh.valor = (TextView) v.findViewById(R.id.ValorPostura);
            return pvh;
        }
        else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.postura_propia_card, parent, false);
            PosturaRVAdapter.PosturaHolder pvh = new PosturaRVAdapter.PosturaHolder(v);
            pvh.cv = (CardView) v.findViewById(R.id.CardPosturaPropia);
            pvh.imagen = (ImageView) v.findViewById(R.id.ImagenPosturaPropia);
            pvh.usuario = (TextView) v.findViewById(R.id.UsuarioPosturaPropia);
            pvh.valor = (TextView) v.findViewById(R.id.ValorPosturaPropia);
            return pvh;
        }
    }

    @Override
    public void onBindViewHolder(PosturaRVAdapter.PosturaHolder holder, final int position) {
        //Aqui van todas las modificaciones especificas de cada elemento
        final Postura p = posturas.get(position);
        if (p.getPostor().equals(user.getUsuario())){
            PosturaRVAdapter.PosturaHolder.usuario.setText("Yo");
        }
        else{
            PosturaRVAdapter.PosturaHolder.usuario.setText(p.getPostor());
        }
        PosturaRVAdapter.PosturaHolder.valor.setText(String.valueOf(p.getValor()));
        PosturaRVAdapter.PosturaHolder.imagen.setImageResource(servicioUsuario.obtenerImagenPorUsuario(p.getPostor()));

    }

    @Override
    public int getItemCount() {
        return posturas.size();
    }
}
