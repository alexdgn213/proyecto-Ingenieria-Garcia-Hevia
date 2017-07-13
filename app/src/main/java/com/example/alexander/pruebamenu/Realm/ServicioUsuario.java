package com.example.alexander.pruebamenu.Realm;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Servicio para acceder, crear, modificar y eliminar Usuarios en realm
*/

import com.example.alexander.pruebamenu.Model.Usuario;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ServicioUsuario {

    private Realm realm;
    public ServicioUsuario(Realm realm){
        this.realm = realm;
    }

    /*Descripcion general: Devuelve una lista con todos los usuarios
     */
    public Usuario[] obtenerUsuarios(){
        RealmResults<Usuario> results = realm.where(Usuario.class).findAll();
        return results.toArray(new Usuario[results.size()]);
    }

    /*Descripcion general: Devuelve un usuario buscando por su nombre
      Parametros: nombre de usuario
     */
    public Usuario obtenerUsuarioPorUsuario(String usuario){
        Usuario resultado = realm.where(Usuario.class).equalTo("usuario",usuario).findFirst();
        return resultado;
    }

    /*Descripcion general: Devuelve la foto de perfil de un usuarui
      Parametros: nombre de usuario
     */
    public int obtenerImagenPorUsuario(String usuario){
        Usuario resultado = realm.where(Usuario.class).equalTo("usuario",usuario).findFirst();
        return resultado.getFotoPerfil();
    }

    /*Descripcion general: Crea un nuevo usuario
      Parametros: Datos del usuario
     */
    public void crearUsuario(String usuario, String nombre, String apellido, String clave, String pais, String ciudad, String correo, String tipo, int foto,boolean suspendido){
        realm.beginTransaction();
        Usuario u = realm.createObject(Usuario.class,usuario);
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setClave(clave);
        u.setPais(pais);
        u.setCiudad(ciudad);
        u.setCorreo(correo);
        u.setTipo(tipo);
        u.setFotoPerfil(foto);
        u.setSuspendido(suspendido);
        realm.commitTransaction();
    }


    /*Descripcion general: Elimina un usuario en especifico
      Parametros: Nombre del usuario
    */
    public void eliminarUsuario(String nombreUsuario){
        Usuario usuario = obtenerUsuarioPorUsuario(nombreUsuario);
        realm.beginTransaction();
        usuario.deleteFromRealm();
        realm.commitTransaction();
    }

    /*Descripcion general: Elimina todos los usuarios
    */
    public void eliminarTodosUsuarios() {

        realm.beginTransaction();
        realm.delete(Usuario.class);
        realm.commitTransaction();

    }

}