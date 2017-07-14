package com.example.alexander.pruebamenu.Model;

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.pruebamenu.Realm.ServicioImagen;
import com.example.alexander.pruebamenu.Realm.ServicioPostura;
import com.example.alexander.pruebamenu.Realm.ServicioSubasta;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Subastas en el sistema
*/

public class Subasta extends RealmObject{
    @PrimaryKey
    private String titulo;
    private String descripcion;
    private String pais;
    private String ciudad;
    private String vendedor;
    private String martillero;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private String tipo;
    private String categoria;
    private String fase;
    private int posturaMinima;
    private int valorInicial;
    private int likes;
    private boolean pausada;
    private boolean esFavorito;
    private boolean esLike;
    @Ignore
    private EstadoFase faseActual;


    public Subasta() {
        faseActual = new Inactiva();
    }

    public Subasta(String titulo, String descripcion, String pais, String ciudad, String vendedor, String martillero, String fecha, String horaInicio, String horaFin, String tipo, String categoria, String fase, int posturaMinima, int valorInicial, int imagenes, int likes, boolean esFavorito, boolean esLike) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.pais = pais;
        this.ciudad = ciudad;
        this.vendedor = vendedor;
        this.martillero = martillero;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tipo = tipo;
        this.categoria = categoria;
        this.fase = fase;
        this.posturaMinima = posturaMinima;
        this.valorInicial = valorInicial;
        this.likes = likes;
        this.esFavorito = esFavorito;
        this.esLike = esLike;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getMartillero() {
        return martillero;
    }

    public void setMartillero(String martillero) {
        this.martillero = martillero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public int getPosturaMinima() {
        return posturaMinima;
    }

    public void setPosturaMinima(int posturaMinima) {
        this.posturaMinima = posturaMinima;
    }

    public int getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(int valorInicial) {
        this.valorInicial = valorInicial;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isEsFavorito() {
        return esFavorito;
    }

    public void setEsFavorito(boolean esFavorito) {
        this.esFavorito = esFavorito;
    }

    public boolean isEsLike() {
        return esLike;
    }

    public void setEsLike(boolean esLike) {
        this.esLike = esLike;
    }

    public EstadoFase getFaseActual() {
        return faseActual;
    }

    public void setFaseActual(EstadoFase faseActual) {
        this.faseActual = faseActual;
    }

    public boolean isPausada() {
        return pausada;
    }

    public void setPausada(boolean pausada) {
        this.pausada = pausada;
    }

    /*Descripcion general: Genera la fase como clase dependiendo del string que indica la misma
    */
    public void generarFase(){
        if(fase.equals("Inactiva")) this.faseActual = new Inactiva();
        else if(fase.equals("Publicidad")) this.faseActual = new Publicidad();
        else if(fase.equals("EnCurso")) this.faseActual = new EnCurso();
        else if(fase.equals("Finalizada")) this.faseActual = new Finalizada();
        else if(fase.equals("Suspendida")) this.faseActual = new Suspendida();
    }

    /*Descripcion general: Devuelve la direccion de la primera imagen de la subasta
    */
    public int getImagenPrincipal(){
        ServicioImagen servicioImagen = new ServicioImagen(Realm.getDefaultInstance());
        return servicioImagen.obtenerImagenPrincipal(this.titulo).getDireccion();
    }

    /*Descripcion general: Devuelve una lista con las imagenes de la subasta
    */
    public List<Imagen> getImagenes(){
        ServicioImagen servicioImagen = new ServicioImagen(Realm.getDefaultInstance());
        return servicioImagen.obtenerImagenesSubasta(this.titulo);
    }

    /*Descripcion general: Devuelve una lista con las posturas de la subasta
   */
    public List<Postura> getPosturas(){
        ServicioPostura servicioPostura = new ServicioPostura(Realm.getDefaultInstance());
        return servicioPostura.obtenerPosturasSubasta(this.titulo);
    }

    /*Descripcion general: Delega las funciones de mostrar/ocultar a su fase actual(Patron estado)
   */
    public void pantallaSubasta(FloatingActionButton acceder, Button postularse, Button cambiarFase, Button suspender){
        generarFase();
        faseActual.mostrarOcultarSubasta(acceder,postularse, cambiarFase, suspender);
    }

    /*Descripcion general: Delega las funciones de mostrar/ocultar a su fase actual(Patron estado)
   */
    public void pantallaPostura(ConstraintLayout lmensaje, TextView mensaje, ConstraintLayout fondoAnimacion, ConstraintLayout lpostura, ConstraintLayout lmartillero, boolean gano, AnimationDrawable animacion, ImageView imagenAnimacion, TextView textoAnimacion){
        generarFase();
        faseActual.mostrarOcultarPosturas(lmensaje, mensaje, fondoAnimacion, lpostura, lpostura, gano, animacion, imagenAnimacion, textoAnimacion);
    }

    /*Descripcion general: Delega la funcion de cambiar fase a su fase actual(Patron estado)
   */
    public void cambiarFase(Subasta s, ServicioSubasta servicioSubasta){
        generarFase();
        faseActual.cambiarFase(s,servicioSubasta);

    }

    /*Descripcion general: Delega la funcion de suspender fase a su fase actual(Patron estado)
   */
    public void suspender(Subasta s, ServicioSubasta servicioSubasta){
        generarFase();
        faseActual.suspender(s,servicioSubasta);
    }

}
