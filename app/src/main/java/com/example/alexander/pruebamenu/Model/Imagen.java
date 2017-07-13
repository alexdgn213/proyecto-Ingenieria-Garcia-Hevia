package com.example.alexander.pruebamenu.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Clase en la que se encuentra una imagen junto con su descripcion, subasta y ruta en la que se encuentra
*/
public class Imagen extends RealmObject{
    @PrimaryKey
    int direccion;
    String descripcion;
    String subasta;

    public Imagen(int direccion, String descripcion, String subasta) {
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.subasta = subasta;
    }

    public Imagen() {
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSubasta() {
        return subasta;
    }

    public void setSubasta(String subasta) {
        this.subasta = subasta;
    }
}
