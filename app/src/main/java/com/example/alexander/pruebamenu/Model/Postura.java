package com.example.alexander.pruebamenu.Model;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Clase para registrar la postura hecha por un usuario a una subasta
*/

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Postura extends RealmObject {
    @PrimaryKey
    String codigo;
    String subasta;
    String postor;
    int valor;

    public Postura() {
    }

    public Postura(String subasta, String postor, int valor) {
        this.codigo = subasta + String.valueOf(valor);
        this.subasta = subasta;
        this.postor = postor;
        this.valor = valor;
    }

    public String getSubasta() {
        return subasta;
    }

    public void setSubasta(String subasta) {
        this.subasta = subasta;
    }

    public String getPostor() {
        return postor;
    }

    public void setPostor(String postor) {
        this.postor = postor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
