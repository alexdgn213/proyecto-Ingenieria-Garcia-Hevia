package com.example.alexander.pruebamenu.Model;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Clase para registrar que un usuario esta postulado a una subasta
*/

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Postulacion extends RealmObject{
    @PrimaryKey
    String id;
    String usuario;
    String subasta;

    public Postulacion(String id,String usuario, String subasta) {
        this.id = id;
        this.usuario = usuario;
        this.subasta = subasta;
    }

    public Postulacion() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSubasta() {
        return subasta;
    }

    public void setSubasta(String subasta) {
        this.subasta = subasta;
    }
}
