package com.example.alexander.pruebamenu.Adapter;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Adaptador que permite generar el empty state en un RecyclerView
*/

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.alexander.pruebamenu.Activity.PantallaImagenes;
import com.example.alexander.pruebamenu.Model.Imagen;
import com.example.alexander.pruebamenu.R;

import java.util.ArrayList;
import java.util.List;


public class EmptyStateRVAdapter extends RecyclerView.Adapter<EmptyStateRVAdapter.ESHolder> {
    Context context;
    List<Imagen> imagenes;

    public static class ESHolder extends RecyclerView.ViewHolder {
        ESHolder(View itemView) {
            super(itemView);
        }

    }

    public EmptyStateRVAdapter( Context context){
        imagenes = new ArrayList<Imagen>();
        Imagen i = new Imagen();
        imagenes.add(i);
        this.context = context;
    }

    @Override
    public EmptyStateRVAdapter.ESHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_state, parent, false);
        EmptyStateRVAdapter.ESHolder pvh = new EmptyStateRVAdapter.ESHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(EmptyStateRVAdapter.ESHolder holder, final int position) {
        final Imagen i = imagenes.get(position);
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }
}
