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
public class HistorialVisitas {
    private int IDRuta, IDSucursal, Vehiculo, Piloto, Copiloto;
    private Date Fecha, HoraSalida, HoraLlegada;
    private String Comentarios;

    public HistorialVisitas(int IDRuta, int IDSucursal, int Vehiculo, int Piloto, int Copiloto, Date Fecha, Date HoraSalida, Date HoraLlegada, String Comentarios) {
        this.IDRuta = IDRuta;
        this.IDSucursal = IDSucursal;
        this.Vehiculo = Vehiculo;
        this.Piloto = Piloto;
        this.Copiloto = Copiloto;
        this.Fecha = Fecha;
        this.HoraSalida = HoraSalida;
        this.HoraLlegada = HoraLlegada;
        this.Comentarios = Comentarios;
    }

    public int getIDRuta() {
        return IDRuta;
    }

    public void setIDRuta(int IDRuta) {
        this.IDRuta = IDRuta;
    }

    public int getIDSucursal() {
        return IDSucursal;
    }

    public void setIDSucursal(int IDSucursal) {
        this.IDSucursal = IDSucursal;
    }

    public int getVehiculo() {
        return Vehiculo;
    }

    public void setVehiculo(int Vehiculo) {
        this.Vehiculo = Vehiculo;
    }

    public int getPiloto() {
        return Piloto;
    }

    public void setPiloto(int Piloto) {
        this.Piloto = Piloto;
    }

    public int getCopiloto() {
        return Copiloto;
    }

    public void setCopiloto(int Copiloto) {
        this.Copiloto = Copiloto;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public Date getHoraSalida() {
        return HoraSalida;
    }

    public void setHoraSalida(Date HoraSalida) {
        this.HoraSalida = HoraSalida;
    }

    public Date getHoraLlegada() {
        return HoraLlegada;
    }

    public void setHoraLlegada(Date HoraLlegada) {
        this.HoraLlegada = HoraLlegada;
    }

    public String getComentarios() {
        return Comentarios;
    }

    public void setComentarios(String Comentarios) {
        this.Comentarios = Comentarios;
    }
    
}
