package com.example.alexander.pruebamenu.Realm;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Servicio para acceder, crear, modificar y eliminar Postulaciones en realm
*/

import com.example.alexander.pruebamenu.Model.Postulacion;
import com.example.alexander.pruebamenu.Model.Postura;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ServicioPostulacion {
    private Realm realm;
    public ServicioPostulacion(Realm realm){
        this.realm = realm;
    }

    /*Descripcion general: Devuelve una lista con todas las postulaciones
     */
    public List<Postulacion> obtenerPostulaciones(){
        RealmResults<Postulacion> results = realm.where(Postulacion.class).findAll();
        return Arrays.asList(results.toArray(new Postulacion[results.size()]));
    }

    /*Descripcion general: Devuelve una lista con todos las postulaciones de una subasta en especifico
      Parametros: Nombre de la subasta
     */
    public List<Postulacion> obtenerPostulacionSubasta(String subasta){
        RealmResults<Postulacion> resultado = realm.where(Postulacion.class).equalTo("subasta",subasta).findAll();
        return Arrays.asList(resultado.toArray(new Postulacion[resultado.size()]));
    }

    /*Descripcion general: Devuelve una lista con todos las postulaciones de un usuario en especifico
      Parametros: Nombre del usuario a buscar
     */
    public List<Postulacion> obtenerPostulacionUsuario(String usuario){
        RealmResults<Postulacion> resultado = realm.where(Postulacion.class).equalTo("usuario",usuario).findAll();
        return Arrays.asList(resultado.toArray(new Postulacion[resultado.size()]));
    }

    /*Descripcion general: Retorna un booleano que es true si el usuario esta postulado a la subasta o false de lo contrario
     Parametros: Nombre del usuario y titulo de la subasta
    */
    public boolean estaPostuladoUsuario(String usuario,String subasta){
        List<Postulacion> resultado = realm.where(Postulacion.class).equalTo("usuario",usuario).findAll();
        for(Postulacion p : resultado) {
            if (p.getSubasta().equals(subasta)) return true;
        }
        return false;
    }

    /*Descripcion general: Crea una nueva postulacion
      Parametros: Nombre del usuario y titulo de la subasta
     */
    public void crearPostulacion(String usuario, String subasta) {
        realm.beginTransaction();
        Postulacion p = realm.createObject(Postulacion.class,usuario+subasta+"postulacion");
        p.setSubasta(subasta);
        p.setUsuario(usuario);
        realm.commitTransaction();

    }

    /*Descripcion general: Elimina una postulacion
      Parametros: Nombre del usuario y titulo de la subasta
     */
    public void eliminarPostulacion(String usuario,String subasta){
        List<Postulacion> postulaciones = obtenerPostulacionUsuario(usuario);
        for(Postulacion p : postulaciones){
            if (p.getSubasta().equals(subasta)) {
                realm.beginTransaction();
                p.deleteFromRealm();
                realm.commitTransaction();
                break;

            }
        }
    }

    /*Descripcion general: Elimina todas las postulaciones
    */
    public void eliminarTodasPostulaciones() {
        realm.beginTransaction();
        realm.delete(Postulacion.class);
        realm.commitTransaction();
    }
}