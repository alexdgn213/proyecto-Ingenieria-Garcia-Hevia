package com.example.alexander.pruebamenu.Realm;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Servicio para acceder, crear, modificar y eliminar Likes en realm
*/


import com.example.alexander.pruebamenu.Model.Imagen;
import com.example.alexander.pruebamenu.Model.Like;
import com.example.alexander.pruebamenu.Model.Subasta;
import com.example.alexander.pruebamenu.Model.Usuario;
import com.example.alexander.pruebamenu.Model.UsuarioActual;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ServicioLike {
    private Realm realm;
    public ServicioLike(Realm realm){
        this.realm = realm;
    }

    /*Descripcion general: Devuelve una lista con todos los likes
     */
    public List<Like> obtenerLikes(){
        RealmResults<Like> results = realm.where(Like.class).findAll();
        return Arrays.asList(results.toArray(new Like[results.size()]));
    }

    /*Descripcion general: Devuelve una lista con todos los likes de un usuario en especifico
      Parametros: Nombre del usuario a buscar
     */
    public List<Like> obtenerLikesUsuario(String usuario){
        RealmResults<Like> results = realm.where(Like.class).equalTo("usuario",usuario).findAll();
        return Arrays.asList(results.toArray(new Like[results.size()]));
    }

    /*Descripcion general: Crea un nuevo like y aumenta en 1 el numero de likes de la subasta
      Parametros: Nombre del usuario y titulo de la subasta
     */
    public void crearLike(String subasta,String usuario) {
        ServicioSubasta servicioSubasta = new ServicioSubasta(Realm.getDefaultInstance());
        Subasta s = servicioSubasta.obtenerSubastaPorTitulo(subasta);
        realm.beginTransaction();
        Like l = realm.createObject(Like.class,subasta+usuario+"like");
        l.setSubasta(subasta);
        l.setUsuario(usuario);
        s.setLikes(s.getLikes()+1);
        realm.commitTransaction();
    }

    /*Descripcion general: Elimina un like y adisminuye en 1 el numero de likes de la subasta
      Parametros: Nombre del usuario y titulo de la subasta
     */
    public void eliminarLikeUsuario(String subasta, String usuario) {
        RealmResults<Like> realmResults = realm.where(Like.class).equalTo("usuario",usuario).findAll();
        ServicioSubasta servicioSubasta = new ServicioSubasta(Realm.getDefaultInstance());
        Subasta s = servicioSubasta.obtenerSubastaPorTitulo(subasta);
        realm.beginTransaction();
        for (Like l : realmResults) {
            if(l.getSubasta().equals(subasta)) {
                l.deleteFromRealm();
                break;
            }
        }
        s.setLikes(s.getLikes()-1);
        realm.commitTransaction();
    }

    /*Descripcion general: Retorna un booleano que es true si al usuario le gusta la subasta o false de lo contrario
     Parametros: Nombre del usuario y titulo de la subasta
    */
    public boolean esLike(String subasta, String usuario) {
        RealmResults<Like> realmResults = realm.where(Like.class).equalTo("usuario",usuario).findAll();
        for (Like l : realmResults) {
            if(l.getSubasta().equals(subasta)) {
                return true;
            }
        }
        return false;
    }

    /*Descripcion general: Dada una lista de subastas, coloca como like aquellas que le gusten al usuario
     Parametros: Nombre del usuario y lista de subastas
    */
    public List<Subasta> generarLikes(List<Subasta> subastas, UsuarioActual usuario) {
        realm.beginTransaction();
        for(Subasta s : subastas){
            if( esLike(s.getTitulo(),usuario.getUsuario())) s.setEsLike(true);
            else s.setEsLike(false);
        }
        realm.commitTransaction();
        return subastas;
    }

    /*Descripcion general: Elimina todos los likes
    */
    public void eliminarTodosLikes() {
        realm.beginTransaction();
        realm.delete(Like.class);
        realm.commitTransaction();
    }
}