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
        servicioSubasta.crearSubasta("Laptop Hp Pavilion G4","una laptop","Venezuela","Caracas","vendedor1","martillero1","13/08/2017","4:00pm","6:00pm","Virtual","Electronica","EnCurso",20,200,78);
        servicioSubasta.crearSubasta("Iphone 5","dos ihpone","Venezuela","Caracas","vendedor1","martillero1","15/08/2017","2:00pm","8:00pm","Virtual","Electronica","Publicidad",10,300,100);
        servicioSubasta.crearSubasta("Toyota Hilux","una camioneta","Venezuela","Caracas","vendedor2","martillero2","21/08/2017","10:00am","8:00pm","Virtual","Vehiculos","Finalizada",100,4000,85);
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
