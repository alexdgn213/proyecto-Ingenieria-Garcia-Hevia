package com.example.alexander.pruebamenu.Realm;

/*  Integrantes: Alexander Garcia y Veronica Hevia

Descripcion general: Clase utilizada para cargar datos generados por los integrantes y guardarlos en realm
*/

import com.example.alexander.pruebamenu.R;

import io.realm.Realm;
import io.realm.annotations.PrimaryKey;

public class IniciarDatos {
    public IniciarDatos() {
        //reiniciar();
        try{
            iniciarUsuarioActual();
        }
        catch (io.realm.exceptions.RealmPrimaryKeyConstraintException e){
            Realm.getDefaultInstance().commitTransaction();
        }
        try{
            iniciarUsuarios();
        }
        catch (io.realm.exceptions.RealmPrimaryKeyConstraintException e){
            Realm.getDefaultInstance().commitTransaction();
        }
        try{
            iniciarSubastas();
        }
        catch (io.realm.exceptions.RealmPrimaryKeyConstraintException e){
            Realm.getDefaultInstance().commitTransaction();
        }
        try{
            iniciarPosturas();
        }
        catch (io.realm.exceptions.RealmPrimaryKeyConstraintException e){
            Realm.getDefaultInstance().commitTransaction();
        }
        try{
            iniciarImagenes();
        }
        catch (io.realm.exceptions.RealmPrimaryKeyConstraintException e){
            Realm.getDefaultInstance().commitTransaction();
        }
        try{
            iniciarPostulaciones();
        }
        catch (io.realm.exceptions.RealmPrimaryKeyConstraintException e){
            Realm.getDefaultInstance().commitTransaction();
        }
    }

    public void reiniciar(){
        ServicioUsuario servicioUsuario = new ServicioUsuario(Realm.getDefaultInstance());
        servicioUsuario.eliminarTodosUsuarios();
        ServicioSubasta servicioSubasta = new ServicioSubasta(Realm.getDefaultInstance());
        servicioSubasta.eliminarTodasSubastas();
        ServicioImagen servicioImagen = new ServicioImagen(Realm.getDefaultInstance());
        servicioImagen.eliminarTodasImagenes();
    }

    public void iniciarUsuarios(){
        ServicioUsuario servicioUsuario = new ServicioUsuario(Realm.getDefaultInstance());
        //servicioUsuario.eliminarTodosUsuarios();
        servicioUsuario.crearUsuario("postor1", "Postor","1","postor1","Venezuela","Caracas","postor1@gmail.com","Postor",R.drawable.p1,false);
        servicioUsuario.crearUsuario("postor2", "Postor","2","postor2","Venezuela","Valencia","postor2@gmail.com","Postor",R.drawable.p2,false);
        servicioUsuario.crearUsuario("postor3", "Postor","3","postor3","Venezuela","La guaira","postor3@gmail.com","Postor",R.drawable.p3,false);
        servicioUsuario.crearUsuario("postor4", "Postor","4","postor4","Venezuela","Maracaibo","postor4@gmail.com","Postor",R.drawable.p4,true);
        servicioUsuario.crearUsuario("martillero1", "Martillero","1","martillero1","Venezuela","Miranda","martillero1@gmail.com","Martillero",R.drawable.m1,false);
        servicioUsuario.crearUsuario("martillero2", "Martillero","2","martillero2","Venezuela","La Guaira","martillero2@gmail.com","Martillero",R.drawable.m2,false);
        servicioUsuario.crearUsuario("vendedor1", "Vendedor","1","vendedor1","Venezuela","Merida","vendedor1@gmail.com","Vendedor",R.drawable.v1,false);
        servicioUsuario.crearUsuario("vendedor2", "Vendedor","2","vendedor1","Venezuela","Caracas","vendedor2@gmail.com","Vendedor",R.drawable.v2,false);
    }

    public void iniciarSubastas(){
        ServicioSubasta servicioSubasta = new ServicioSubasta(Realm.getDefaultInstance());
        //servicioSubasta.eliminarTodasSubastas();
        servicioSubasta.crearSubasta("Laptop Hp Pavilion G4","Laptop HP con 4 GB de memoria RAM, procesador i5, tarjeta gráfica de última generación","Venezuela","Caracas","vendedor1","martillero1","13/08/2017","4:00pm","6:00pm","Virtual","Electronica","EnCurso",20,200,78);
        servicioSubasta.crearSubasta("Iphone 5","IPhone 5 de 16GB, colores negro y gris. Cámara frontal de 5Mp y trasera de 16Mp","Venezuela","Caracas","vendedor1","martillero1","15/08/2017","2:00pm","8:00pm","Virtual","Electronica","Publicidad",10,300,100);
        servicioSubasta.crearSubasta("Toyota Hilux","Camioneta 4x4, motor de 8000 caballos de fuerza, ideal para terrenos rocosos","Venezuela","Caracas","vendedor2","martillero2","21/08/2017","10:00am","8:00pm","Virtual","Vehiculos","Finalizada",100,4000,85);
        servicioSubasta.crearSubasta("Matt Everlast","Perfecto para hacer yoga, pilates o ejercicios en casa, mas colores disponibles","Venezuela","Valencia","vendedor1","martillero2","29/07/2017","9:00am","8:00pm","Virtual","Varios","Publicidad",10,80,85);
        servicioSubasta.crearSubasta("Mesa para computadora","Mesa para computadora color caoba, perfecta para estudiar. Adaptable con gavetas","Venezuela","Caracas","vendedor2","martillero2","21/08/2017","11:00am","5:00pm","Virtual","Hogar","Finalizada",40,600,85);
        servicioSubasta.crearSubasta("Libro Divergente","1era parte de la trilogia del la best seller Veronica Roth, Divergente. Carátula dura. Idioma: Inglés","Venezuela","Caracas","vendedor2","martillero2","21/07/2017","07:00am","11:00am","Virtual","Libros","Finalizada",2,20,85);
        servicioSubasta.crearSubasta("Juego de pinceles SOLITA","Juego de 10 pinceles ideales para pintar con acuarela, pintadedos. Super resistentes, de hebra gruesa","Venezuela","Maracaibo","vendedor2","martillero2","21/08/2017","1:00pm","3:00pm","Virtual","Arte","Finalizada",1,15,85);
        servicioSubasta.crearSubasta("Juego de ollas","Juego de ollas de todos los tamaños antiadeherentes marca TRAKI","Venezuela","San Cristobal","vendedor2","martillero2","22/08/2017","2:00pm","6:00pm","Virtual","Hogar","Finalizada",10,80,85);
        servicioSubasta.crearSubasta("Chevrolet Optra","Optra chevrolet año 2007, 30.000km, AA, Radio con AUX, cauchos nuevos","Venezuela","La Guaira","vendedor2","martillero2","21/08/2017","7:00am","7:00pm","Virtual","Vehiculos","EnCurso",80,2000,85);
        servicioSubasta.crearSubasta("Libro 100 años de soledad","Del conocido autor Gabriel Garcia Marques, un clásico 100 años de soledad. Portada dura","Venezuela","Caracas","vendedor2","martillero2","03/08/2017","2:00am","4:00pm","Virtual","Libros","Finalizada",1,15,85);
        servicioSubasta.crearSubasta("Cafetera Oster","Cafetera marca oster con reloj incorporado, programable. Color negro","Venezuela","Caracas","vendedor2","martillero2","10/07/2017","9:00am","12:00pm","Virtual","Hogar","Publicidad",3,20,85);
        
    }

    public void iniciarImagenes(){
        ServicioImagen servicioImagen = new ServicioImagen(Realm.getDefaultInstance());
        //servicioImagen.eliminarTodasImagenes();
        servicioImagen.crearImagen(R.drawable.laptop,"Imagen principal","Laptop Hp Pavilion G4");
        servicioImagen.crearImagen(R.drawable.laptop2,"Imagen 2","Laptop Hp Pavilion G4");
        servicioImagen.crearImagen(R.drawable.laptop3,"Imagen 3","Laptop Hp Pavilion G4");
        servicioImagen.crearImagen(R.drawable.laptop4,"Imagen 4","Laptop Hp Pavilion G4");
        servicioImagen.crearImagen(R.drawable.laptop5 ,"Imagen 5","Laptop Hp Pavilion G4");
        servicioImagen.crearImagen(R.drawable.telefono,"Imagen principal","Iphone 5");
        servicioImagen.crearImagen(R.drawable.telefono2,"Imagen 2","Iphone 5");
        servicioImagen.crearImagen(R.drawable.telefono3,"Imagen 3","Iphone 5");
        servicioImagen.crearImagen(R.drawable.telefono4,"Imagen 4","Iphone 5");
        servicioImagen.crearImagen(R.drawable.telefono5,"Imagen 5","Iphone 5");
        servicioImagen.crearImagen(R.drawable.telefono6,"Imagen 6","Iphone 5");
        servicioImagen.crearImagen(R.drawable.telefono7,"Imagen 7","Iphone 5");
        servicioImagen.crearImagen(R.drawable.camioneta,"Imagen Principal","Toyota Hilux");
        servicioImagen.crearImagen(R.drawable.camioneta2,"Imagen 2","Toyota Hilux");
        servicioImagen.crearImagen(R.drawable.camioneta3,"Imagen 3","Toyota Hilux");
        servicioImagen.crearImagen(R.drawable.camioneta4,"Imagen 4","Toyota Hilux");
        servicioImagen.crearImagen(R.drawable.camioneta5,"Imagen 5","Toyota Hilux");
        servicioImagen.crearImagen(R.drawable.matt_principal,"Imagen Principal","Matt Everlast");
        servicioImagen.crearImagen(R.drawable.matt1,"Imagen 1","Matt Everlast");
        servicioImagen.crearImagen(R.drawable.matt2,"Imagen 2","Matt Everlast");
        servicioImagen.crearImagen(R.drawable.matt3,"Imagen 3","Matt Everlast");
        servicioImagen.crearImagen(R.drawable.mesacomputadora,"Imagen Principal","Mesa para computadora");
        servicioImagen.crearImagen(R.drawable.mesacomputadora2,"Imagen 2","Mesa para computadora");
        servicioImagen.crearImagen(R.drawable.librodivergente,"Imagen Principal","Libro Divergente");
        servicioImagen.crearImagen(R.drawable.librodivergente2,"Imagen 2","Libro Divergente");
        servicioImagen.crearImagen(R.drawable.librodivergente3,"Imagen 3","Libro Divergente");
        servicioImagen.crearImagen(R.drawable.librodivergente4,"Imagen 4","Libro Divergente");
        servicioImagen.crearImagen(R.drawable.pinceles,"Imagen Principal","Juego de pinceles SOLITA");
        servicioImagen.crearImagen(R.drawable.pinceles2,"Imagen 1","Juego de pinceles SOLITA");
        servicioImagen.crearImagen(R.drawable.ollas,"Imagen Principal","Juego de ollas");
        servicioImagen.crearImagen(R.drawable.ollas2,"Imagen 1","Juego de ollas");
        servicioImagen.crearImagen(R.drawable.chevroletoptra,"Imagen Principal","Chevrolet Optra");
        servicioImagen.crearImagen(R.drawable.chevroletoptra2,"Imagen 1","Chevrolet Optra");
        servicioImagen.crearImagen(R.drawable.chevroletoptra3,"Imagen 2","Chevrolet Optra");
        servicioImagen.crearImagen(R.drawable.librocien,"Imagen Principal","Libro 100 años de soledad");
        servicioImagen.crearImagen(R.drawable.librocien2,"Imagen 1","Libro 100 años de soledad");
        servicioImagen.crearImagen(R.drawable.cafetera,"Imagen Principal","Cafetera Oster");
        servicioImagen.crearImagen(R.drawable.cafetera2,"Imagen 1","Cafetera Oster");
    }


    public void iniciarPosturas(){
        ServicioPostura servicioPostura= new ServicioPostura(Realm.getDefaultInstance());
        //servicioPostura.eliminarTodasPosturas();
        servicioPostura.crearPostura("Laptop Hp Pavilion G4","postor1",250);
        servicioPostura.crearPostura("Laptop Hp Pavilion G4","postor2",300);
        servicioPostura.crearPostura("Laptop Hp Pavilion G4","postor1",350);
        servicioPostura.crearPostura("Laptop Hp Pavilion G4","postor2",400);
        servicioPostura.crearPostura("Laptop Hp Pavilion G4","postor1",450);
        servicioPostura.crearPostura("Laptop Hp Pavilion G4","postor2",500);
        servicioPostura.crearPostura("Toyota Hilux","postor2",4500);
        servicioPostura.crearPostura("Toyota Hilux","postor1",5000);
        servicioPostura.crearPostura("Toyota Hilux","postor3",5500);

    }

    public void iniciarUsuarioActual(){
        ServicioUsuarioActual servicioUsuarioActual = new ServicioUsuarioActual(Realm.getDefaultInstance());
        servicioUsuarioActual.crearUsuario();

    }

    public void iniciarPostulaciones(){
        ServicioPostulacion servicioPostulacion = new ServicioPostulacion(Realm.getDefaultInstance());
        //servicioPostulacion.eliminarTodasPostulaciones();
        servicioPostulacion.crearPostulacion("postor1","Laptop Hp Pavilion G4");
        servicioPostulacion.crearPostulacion("postor1","Toyota Hilux");
    }
}
