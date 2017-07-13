package com.example.alexander.pruebamenu.Model;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Clase para registrar que a un usuario le gusta una subasta
*/

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Like extends RealmObject {
    @PrimaryKey
    String id;
    String subasta;
    String usuario;

    public Like() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubasta() {
        return subasta;
    }

    public void setSubasta(String subasta) {
        this.subasta = subasta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
