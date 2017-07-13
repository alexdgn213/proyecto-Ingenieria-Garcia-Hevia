package com.example.alexander.pruebamenu.Model;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Usuario logueado actualmente en la aplcacion
*/

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.example.alexander.pruebamenu.Realm.ServicioPostulacion;
import com.example.alexander.pruebamenu.Realm.ServicioSubasta;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class UsuarioActual extends RealmObject{
    private static UsuarioActual usuarioActual;
    @PrimaryKey
    int codigo;
    String usuario;
    String nombre;
    String apellido;
    String clave;
    String pais;
    String ciudad;
    String correo;
    String tipo;
    boolean suspendido;
    int foto;
    @Ignore
    EstrategiaUsuario estrategia;

    private UsuarioActual(int codigo) {
        this.codigo=codigo;
    }

    public UsuarioActual() {
    }

    public static UsuarioActual getUsuarioActual() {
        if (usuarioActual==null){
            return new UsuarioActual(1);
        }
        return usuarioActual;
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public EstrategiaUsuario getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(EstrategiaUsuario estrategia) {
        this.estrategia = estrategia;
    }

    public boolean isSuspendido() {
        return suspendido;
    }

    public void setSuspendido(boolean suspendido) {
        this.suspendido = suspendido;
    }

    public void generarEstrategia() {
        if(tipo.equals("Postor")) this.estrategia = new EstrategiaPostor();
        else if(tipo.equals("Martillero")) this.estrategia = new EstrategiaMartillero();
        else if(tipo.equals("Vendedor")) this.estrategia = new EstrategiaVendedor();
    }

    public void pantallaSubasta(Button postularse, Button cambiarFase, Button suspender,boolean esPostorMartillero){
        generarEstrategia();
        estrategia.pantallaSubasta(postularse, cambiarFase, suspender,esPostorMartillero);
    }

    public void pantallaPostura(RecyclerView rv, FloatingActionButton pausar, ConstraintLayout lObservador, ConstraintLayout lmartillero, ConstraintLayout lPostor, TextView mensaje, boolean pausada, boolean esPostorMartillero){
        generarEstrategia();
        estrategia.pantallaPostura(rv, pausar, lObservador, lmartillero, lPostor,  mensaje, pausada, esPostorMartillero);
    }

    public List<Subasta> obtenerSubastas(ServicioSubasta servicioSubasta, String usuario){
        generarEstrategia();
        return estrategia.obtenerSubastas(servicioSubasta,usuario);
    }

    public List<Subasta> obtenerPostulaciones(){
        ServicioPostulacion servicioPostulacion = new ServicioPostulacion(Realm.getDefaultInstance());
        ServicioSubasta servicioSubasta = new ServicioSubasta(Realm.getDefaultInstance());
        List<Postulacion> postulaciones = servicioPostulacion.obtenerPostulacionUsuario(this.usuario);
        List<Subasta> subastas = new ArrayList<Subasta>();
        for(Postulacion p : postulaciones){
            Subasta s = servicioSubasta.obtenerSubastaPorTitulo(p.getSubasta());
            subastas.add(s);
        }
        return subastas;
    }

}
