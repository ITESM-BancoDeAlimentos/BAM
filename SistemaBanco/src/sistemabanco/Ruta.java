package sistemabanco;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Enrique
 */
public class Ruta {
    
    
    
    private String nombre;
    private int IDRuta;
    
    public Ruta() {
        //check something with the ID?
    }

    public Ruta(String nombre, int IDRuta) {
        this.nombre = nombre;
        this.IDRuta = IDRuta;
    }
    
    public int getIDRuta() {
        return IDRuta;
    }

    public void setIDRuta(int IDRuta) {
        this.IDRuta = IDRuta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
