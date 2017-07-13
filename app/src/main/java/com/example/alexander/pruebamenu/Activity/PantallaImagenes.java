package com.example.alexander.pruebamenu.Activity;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Actividad que permite ver en detalle una imagen con su descripcion
*/

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.pruebamenu.R;

import org.w3c.dom.Text;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PantallaImagenes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_imagenes);

        //Se inicializan los contenedores
        ImageView imagen = (ImageView) findViewById(R.id.ImagenDetalle);
        TextView texto = (TextView) findViewById(R.id.DescripcionDetalle);

        //Se leen los parametros que mando la actividad anterior
        int direccionImagen = getIntent().getIntExtra("imagen",0);
        String descripcion = getIntent().getStringExtra("descripcion");

        //Se asignan los valores
        imagen.setImageResource(direccionImagen);
        texto.setText(descripcion);

    }
}
