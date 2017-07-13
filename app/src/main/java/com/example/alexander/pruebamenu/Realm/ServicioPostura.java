package com.example.alexander.pruebamenu.Realm;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Servicio para acceder, crear, modificar y eliminar Posturas en realm
*/

import com.example.alexander.pruebamenu.Model.Imagen;
import com.example.alexander.pruebamenu.Model.Postulacion;
import com.example.alexander.pruebamenu.Model.Postura;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ServicioPostura {
    private Realm realm;
    public ServicioPostura(Realm realm){
        this.realm = realm;
    }

    /*Descripcion general: Devuelve una lista con todas las posturas
     */
    public List<Postura> obtenerPosturas(){
        RealmResults<Postura> results = realm.where(Postura.class).findAll();
        return Arrays.asList(results.toArray(new Postura[results.size()]));
    }

    /*Descripcion general: Devuelve una lista con todos las posturas de una subasta en especifico
      Parametros: Nombre de la subasta
     */
    public List<Postura> obtenerPosturasSubasta(String subasta){

        RealmResults<Postura> resultado = realm.where(Postura.class).equalTo("subasta",subasta).findAll();
        resultado = resultado.sort("valor", Sort.DESCENDING);
        return Arrays.asList(resultado.toArray(new Postura[resultado.size()]));

    }

    /*Descripcion general: Devuelve la mayor postura de una subasta
      Parametros: Nombre de la subasta
     */
    public Postura obtenerMejorPostura(String subasta){
        RealmResults<Postura> resultado = realm.where(Postura.class).equalTo("subasta",subasta).findAll();
        resultado = resultado.sort("valor", Sort.DESCENDING);
        List<Postura> lista = Arrays.asList(resultado.toArray(new Postura[resultado.size()]));
        if (lista.size()>0) return lista.get(0);
        else return null;
    }

    /*Descripcion general: Crea una nueva postura
      Parametros: Nombre del postor, titulo de la subasta y el valor de la postura
     */
    public void crearPostura(String subasta, String postor, int valor) {
        realm.beginTransaction();
        Postura p = realm.createObject(Postura.class,subasta+String.valueOf(valor));
        p.setSubasta(subasta);
        p.setPostor(postor);
        p.setValor(valor);
        realm.commitTransaction();

    }

     /*Descripcion general: Elimina una postura
      Parametros: Nombre del usuario y titulo de la subasta
     */
    public void eliminarPosturasUsuario(String usuario) {

        RealmResults<Postura> realmResults = realm.where(Postura.class).equalTo("postor",usuario).findAll();
        realm.beginTransaction();
        for (Postura p : realmResults) {
            p.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    /*Descripcion general: Elimina todas las posturas
    */
    public void eliminarTodasPosturas() {
        realm.beginTransaction();
        realm.delete(Postura.class);
        realm.commitTransaction();
    }
}