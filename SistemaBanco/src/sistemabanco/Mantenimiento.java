/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemabanco;

import java.util.Date;

/**
 *
 * @author Enrique
 */
public class Mantenimiento {
    private int IDVehiculo, Costo, Kilometraje, KilomActual;
    private Date Fecha;
    private String Tipo, Taller, Comentarios;

    public Mantenimiento(int IDVehiculo, int Costo, int Kilometraje, int KilomActual, Date Fecha, String Tipo, String Taller, String Comentarios) {
        this.IDVehiculo = IDVehiculo;
        this.Costo = Costo;
        this.Kilometraje = Kilometraje;
        this.KilomActual = KilomActual;
        this.Fecha = Fecha;
        this.Tipo = Tipo;
        this.Taller = Taller;
        this.Comentarios = Comentarios;
    }

    public int getIDVehiculo() {
        return IDVehiculo;
    }

    public void setIDVehiculo(int IDVehiculo) {
        this.IDVehiculo = IDVehiculo;
    }

    public int getCosto() {
        return Costo;
    }

    public void setCosto(int Costo) {
        this.Costo = Costo;
    }

    public int getKilometraje() {
        return Kilometraje;
    }

    public void setKilometraje(int Kilometraje) {
        this.Kilometraje = Kilometraje;
    }

    public int getKilomActual() {
        return KilomActual;
    }

    public void setKilomActual(int KilomActual) {
        this.KilomActual = KilomActual;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getTaller() {
        return Taller;
    }

    public void setTaller(String Taller) {
        this.Taller = Taller;
    }

    public String getComentarios() {
        return Comentarios;
    }

    public void setComentarios(String Comentarios) {
        this.Comentarios = Comentarios;
    }
    
}
