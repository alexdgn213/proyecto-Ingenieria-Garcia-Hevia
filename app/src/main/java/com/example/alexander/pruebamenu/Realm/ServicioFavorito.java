package com.example.alexander.pruebamenu.Realm;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Servicio para acceder, crear, modificar y eliminar Favoritos en realm
*/

import com.example.alexander.pruebamenu.Model.Favorito;
import com.example.alexander.pruebamenu.Model.Like;
import com.example.alexander.pruebamenu.Model.Subasta;
import com.example.alexander.pruebamenu.Model.UsuarioActual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ServicioFavorito {
    private Realm realm;
    public ServicioFavorito(Realm realm){
        this.realm = realm;
    }

    /*Descripcion general: Devuelve una lista con todos los favoritos
     */
    public List<Favorito> obtenerfavoritos(){
        RealmResults<Favorito> results = realm.where(Favorito.class).findAll();
        return Arrays.asList(results.toArray(new Favorito[results.size()]));
    }

    /*Descripcion general: Devuelve una lista con todos los favoritos de un usuario en especifico
      Parametros: Nombre del usuario a buscar
     */
    public List<Favorito> obtenerfavoritosUsuario(String usuario){
        RealmResults<Favorito> results = realm.where(Favorito.class).equalTo("usuario",usuario).findAll();
        return Arrays.asList(results.toArray(new Favorito[results.size()]));
    }

    /*Descripcion general: Devuelve una lista con todas las subastas que son favoritas de un usuario en especifico
      Parametros: Nombre del usuario a buscar
     */
    public List<Subasta> obtenerSubastasFavoritas(String usuario){
        RealmResults<Favorito> results = realm.where(Favorito.class).equalTo("usuario",usuario).findAll();
        List<Subasta> subastas = new ArrayList<Subasta>();
        ServicioSubasta servicioSubasta = new ServicioSubasta(Realm.getDefaultInstance());
        for(Favorito f : results){
            Subasta s = servicioSubasta.obtenerSubastaPorTitulo(f.getSubasta());
            subastas.add(s);
        }
        return subastas;
    }

    /*Descripcion general: Crea un nuevo favorito
      Parametros: Nombre del usuario y titulo de la subasta
     */
    public void crearFavorito(String subasta,String usuario) {
        realm.beginTransaction();
        Favorito f = realm.createObject(Favorito.class,subasta+usuario+"favorito");
        f.setSubasta(subasta);
        f.setUsuario(usuario);
        realm.commitTransaction();
    }

    /*Descripcion general: Elimina el favorito relacionado con el nombre de usuario y la subasta que se de
     Parametros: Nombre del usuario y titulo de la subasta
    */
    public void eliminarFavoritoUsuario(String subasta, String usuario) {
        RealmResults<Favorito> realmResults = realm.where(Favorito.class).equalTo("usuario",usuario).findAll();
        realm.beginTransaction();
        for (Favorito f : realmResults) {
            if(f.getSubasta().equals(subasta)){
                f.deleteFromRealm();
                break;
            }
        }
        realm.commitTransaction();
    }

    /*Descripcion general: Retorna un booleano que es true si la subasta es favotira del usuario o false de lo contrario
     Parametros: Nombre del usuario y titulo de la subasta
    */
    public boolean esFavorito(String subasta, String usuario) {
        RealmResults<Favorito> realmResults = realm.where(Favorito.class).equalTo("usuario",usuario).findAll();
        for (Favorito f : realmResults) {
            if(f.getSubasta().equals(subasta)) {
                return true;
            }
        }
        return false;
    }

    /*Descripcion general: Dada una lista de subastas, coloca como favoritas aquellas que el usuario tenga como favorito
     Parametros: Nombre del usuario y lista de subastas
    */
    public List<Subasta> generarFavoritos(List<Subasta> subastas, UsuarioActual usuario) {
        for(Subasta s : subastas){
            ServicioSubasta servicioSubasta = new ServicioSubasta(Realm.getDefaultInstance());
            realm.beginTransaction();
            if( esFavorito(s.getTitulo(),usuario.getUsuario())) {
                s.setEsFavorito(true);
            }
            else {
                s.setEsFavorito(false);
            }
            realm.commitTransaction();
        }
        return subastas;
    }

    /*Descripcion general: Elimina todos los favoritos
    */
    public void eliminarTodosFavoritos() {
        realm.beginTransaction();
        realm.delete(Favorito.class);
        realm.commitTransaction();
    }
}