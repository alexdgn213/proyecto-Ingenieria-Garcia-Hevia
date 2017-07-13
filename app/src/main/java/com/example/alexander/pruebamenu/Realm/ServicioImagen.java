package com.example.alexander.pruebamenu.Realm;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Servicio para acceder, crear, modificar y eliminar Imagenes en realm
*/


import com.example.alexander.pruebamenu.Model.Imagen;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ServicioImagen{
    private Realm realm;
    public ServicioImagen(Realm realm){
        this.realm = realm;
    }

    /*Descripcion general: Devuelve una lista con todas las imagenes
     */
    public List<Imagen> obtenerImagenes(){

        RealmResults<Imagen> results = realm.where(Imagen.class).findAll();
        return Arrays.asList(results.toArray(new Imagen[results.size()]));
    }

    /*Descripcion general: Devuelve la primera imagen de una subasta en especifico
      Parametros: Titulo de la subasta
     */
    public Imagen obtenerImagenPrincipal(String subasta){
        Imagen resultado = realm.where(Imagen.class).equalTo("subasta",subasta).findFirst();
        return resultado;
    }


    /*Descripcion general: Devuelve una lista con todas las imagenes de una subasta en especifico
      Parametros: Titulo de la subasta
     */
    public List<Imagen> obtenerImagenesSubasta(String subasta){
        RealmResults<Imagen> resultado = realm.where(Imagen.class).equalTo("subasta",subasta).findAll();
        return Arrays.asList(resultado.toArray(new Imagen[resultado.size()]));
    }


    /*Descripcion general: Crea una nueva imagen
      Parametros: datos de la imagen
     */
    public void crearImagen(int direccion,String descripcion,String subasta) {
        realm.beginTransaction();
        Imagen i = realm.createObject(Imagen.class,direccion);
        i.setDescripcion(descripcion);
        i.setSubasta(subasta);
        realm.commitTransaction();

    }


    /*Descripcion general: elimina todas las imagenes
      */
    public void eliminarTodasImagenes() {

        realm.beginTransaction();
        realm.delete(Imagen.class);
        realm.commitTransaction();

    }
}