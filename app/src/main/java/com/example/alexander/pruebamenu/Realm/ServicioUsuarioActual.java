package com.example.alexander.pruebamenu.Realm;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Servicio para acceder, crear, modificar y eliminar Usuarios en realm
*/

import com.example.alexander.pruebamenu.Model.Usuario;
import com.example.alexander.pruebamenu.Model.UsuarioActual;
import com.example.alexander.pruebamenu.R;

import io.realm.Realm;
import io.realm.RealmResults;

public class ServicioUsuarioActual {

    private Realm realm;
    public ServicioUsuarioActual(Realm realm){
        this.realm = realm;
    }

    /*Descripcion general: Retorna el usuario actual unico
    */
    public UsuarioActual obtenerUsuarioActual(){
        UsuarioActual resultado = realm.where(UsuarioActual.class).findFirst();
        return resultado;
    }

    /*Descripcion general: Borra todos los datos en el usuario actual
    */
    public void CerrarSesion(){
        realm.beginTransaction();
        UsuarioActual usuario = obtenerUsuarioActual();
        usuario.setUsuario("");
        usuario.setNombre("");
        usuario.setApellido("");
        usuario.setClave("");
        usuario.setPais("");
        usuario.setCiudad("");
        usuario.setCorreo("");
        usuario.setTipo("");
        usuario.setFoto(0);
        realm.commitTransaction();

    }

    /*Descripcion general: Crea el usuario actual(solo una vez)
     */
    public void crearUsuario(){

        realm.beginTransaction();
        UsuarioActual usuario = realm.createObject(UsuarioActual.class,1);
        usuario.setUsuario("");
        usuario.setNombre("");
        usuario.setApellido("");
        usuario.setClave("");
        usuario.setPais("");
        usuario.setCiudad("");
        usuario.setCorreo("");
        usuario.setTipo("");
        usuario.setFoto(R.mipmap.ic_launcher);
        realm.commitTransaction();

    }

    /*Descripcion general: Carga los datos de un usuario al usuario Actual
    */
    public void iniciarSesion(Usuario user){
        realm.beginTransaction();
        UsuarioActual usuario = obtenerUsuarioActual();
        usuario.setUsuario(user.getUsuario());
        usuario.setNombre(user.getNombre());
        usuario.setApellido(user.getApellido());
        usuario.setClave(user.getClave());
        usuario.setPais(user.getPais());
        usuario.setCiudad(user.getCiudad());
        usuario.setCorreo(user.getCorreo());
        usuario.setTipo(user.getTipo());
        usuario.setFoto(user.getFotoPerfil());
        usuario.setSuspendido(user.isSuspendido());
        realm.commitTransaction();

    }
}