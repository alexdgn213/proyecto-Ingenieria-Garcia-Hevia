package com.example.alexander.pruebamenu.Adapter;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Adaptador que permite generar imagenes en un RecyclerView a partir de una lista
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

import java.util.List;


public class ImageRVAdapter extends RecyclerView.Adapter<ImageRVAdapter.ImagenHolder> {
    Context context;
    List<Imagen> imagenes;

    public static class ImagenHolder extends RecyclerView.ViewHolder {
        static CardView cv;
        static ImageView imagen;
        ImagenHolder(View itemView) {
            // aqui van todos los elementos del cardView que van a ser modificados
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.tarjetaImagen);
            imagen = (ImageView) itemView.findViewById(R.id.imagenTarjeta);
        }

    }

    public ImageRVAdapter(List<Imagen> imagenes, Context context){
        this.imagenes = imagenes;
        this.context = context;
    }

    @Override
    public ImageRVAdapter.ImagenHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagen_card, parent, false);
        ImageRVAdapter.ImagenHolder pvh = new ImageRVAdapter.ImagenHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ImageRVAdapter.ImagenHolder holder, final int position) {
        //Aqui van todas las modificaciones especificas de cada elemento
        final Imagen i = imagenes.get(position);
        ImageRVAdapter.ImagenHolder.imagen.setImageResource(i.getDireccion());
        ImageRVAdapter.ImagenHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PantallaImagenes.class);
                intent.putExtra("imagen", i.getDireccion());
                intent.putExtra("descripcion",i.getDescripcion());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }
}
