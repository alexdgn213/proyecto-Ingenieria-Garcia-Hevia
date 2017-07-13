package com.example.alexander.pruebamenu.Realm;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Servicio para acceder, crear, modificar y eliminar Subastas en realm
*/

import com.example.alexander.pruebamenu.Model.Postulacion;
import com.example.alexander.pruebamenu.Model.Subasta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ServicioSubasta {
    private Realm realm;
    public ServicioSubasta(Realm realm){
        this.realm = realm;
    }

    /*Descripcion general: Devuelve una lista con todas las subastas
    */
    public List<Subasta> obtenerSubastas(){
        RealmResults<Subasta> results = realm.where(Subasta.class).findAll();
        return Arrays.asList(results.toArray(new Subasta[results.size()]));
    }

    /*Descripcion general: Devuelve una lista con todas las subastas en las faces de: publicidad,enCurso y finalizada
    */
    public List<Subasta> obtenerSubastasParaMostrar(){
        RealmResults<Subasta> results = realm.where(Subasta.class).equalTo("fase","Publicidad").or().equalTo("fase","EnCurso").or().equalTo("fase","Finalizada").findAll();
        return Arrays.asList(results.toArray(new Subasta[results.size()]));
    }

    /*Descripcion general: Devuelve una lista con todas las subastas de una categoria
      Parametros: nombre de la categoria
    */
    public List<Subasta> obtenerSubastasCategoria(String categoria){
        RealmResults<Subasta> results = realm.where(Subasta.class).equalTo("categoria",categoria).findAll();
        return Arrays.asList(results.toArray(new Subasta[results.size()]));
    }

    /*Descripcion general: Devuelve una lista con todas las subastas de un martillero
      Parametros: nombre del martillero*/
    public List<Subasta> obtenerSubastasMartillero(String martillero){
        RealmResults<Subasta> results = realm.where(Subasta.class).equalTo("martillero",martillero).findAll();
        return Arrays.asList(results.toArray(new Subasta[results.size()]));
    }

    /*Descripcion general: Devuelve una lista con todas las subastas de un vendedor
      Parametros: nombre del vendedor
    */
    public List<Subasta> obtenerSubastasVendedor(String vendedor){
        RealmResults<Subasta> results = realm.where(Subasta.class).equalTo("vendedor",vendedor).findAll();
        return Arrays.asList(results.toArray(new Subasta[results.size()]));
    }

    /*Descripcion general: Devuelve una lista con todas las subastas en la que se encuentra postulado un usuario
      Parametros: nombre del ususuario
    */
    public List<Subasta> obtenerSubastasPostulado(String usuario){
        List<Subasta> lista = new ArrayList<>();
        RealmResults<Postulacion> postulaciones = realm.where(Postulacion.class).equalTo("usuario",usuario).findAll();
        for (Postulacion p : postulaciones){
            lista.add(obtenerSubastaPorTitulo(p.getSubasta()));
        }
        return lista;
    }

    /*Descripcion general: Devuelve una lista con todas las subastas ordenadas por likes
    */
    public List<Subasta> obtenerSubastasPorLikes(){
        RealmResults<Subasta> results = realm.where(Subasta.class).equalTo("fase","Publicidad").or().equalTo("fase","EnCurso").or().equalTo("fase","Finalizada").findAll();
        results = results.sort("likes", Sort.DESCENDING);
        return Arrays.asList(results.toArray(new Subasta[results.size()]));
    }

    /*Descripcion general: Devuelve una subasta buscandola por titulo
      Parametros: titulo de la subasta
    */
    public Subasta obtenerSubastaPorTitulo(String titulo){
        Subasta resultado = realm.where(Subasta.class).equalTo("titulo",titulo).findFirst();
        return resultado;
    }

    /*Descripcion general: Actualiza la fase de una subasta
      Parametros: subasta a modificar y nueva fase
    */
    public void actualizarSubastaFase(Subasta subasta, String fase){
        realm.beginTransaction();
        subasta.setFase(fase);
        realm.commitTransaction();

    }

    /*Descripcion general: pausa o renauda una subasta dependiendo de su estado
      Parametros: subasta a modificar
    */
    public void pausaRenaudarSubasta(Subasta subasta){
        realm.beginTransaction();
        if (subasta.isPausada()){
            subasta.setPausada(false);
        }
        else subasta.setPausada(true);
        realm.commitTransaction();

    }


    /*Descripcion general: Crea una nueva subasta
      Parametros: datos de la subasta
    */
    public void crearSubasta(String titulo, String descripcion, String pais, String ciudad, String vendedor, String martillero, String fecha, String horaInicio, String horaFin,String tipo, String categoria,String fase, int posturaMinima, int valorInicial, int likes) {
        realm.beginTransaction();
        Subasta s = realm.createObject(Subasta.class,titulo);
        s.setTipo(titulo);
        s.setDescripcion(descripcion);
        s.setPais(pais);
        s.setCiudad(ciudad);
        s.setVendedor(vendedor);
        s.setMartillero(martillero);
        s.setFecha(fecha);
        s.setHoraInicio(horaInicio);
        s.setHoraFin(horaFin);
        s.setTipo(tipo);
        s.setCategoria(categoria);
        s.setFase(fase);
        s.setPosturaMinima(posturaMinima);
        s.setValorInicial(valorInicial);
        s.setLikes(likes);
        s.setPausada(false);
        realm.commitTransaction();
    }

    /*Descripcion general: elimina una subasta
      Parametros: titulo de la subasta
    */
    public void eliminarSubasta(String tituloSubasta){
        Subasta s = obtenerSubastaPorTitulo(tituloSubasta);
        realm.beginTransaction();
        s.deleteFromRealm();
        realm.commitTransaction();
    }


    /*Descripcion general: elimina todas las subastas
    */
    public void eliminarTodasSubastas() {
        realm.beginTransaction();
        RealmResults<Subasta> realmResults = realm.where(Subasta.class).findAll();
        realm.delete(Subasta.class);
        realm.commitTransaction();
    }
}
