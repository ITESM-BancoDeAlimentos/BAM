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
public class ControlCombustible {
    private int IDVehiculo, Kilometraje, Carga, PrecioGas, NoTicket;
    private Date Fecha;
    private String FormaPago, Combustible;

    public ControlCombustible(int IDVehiculo, int Kilometraje, int Carga, int PrecioGas, int NoTicket, Date Fecha, String FormaPago, String Combustible) {
        this.IDVehiculo = IDVehiculo;
        this.Kilometraje = Kilometraje;
        this.Carga = Carga;
        this.PrecioGas = PrecioGas;
        this.NoTicket = NoTicket;
        this.Fecha = Fecha;
        this.FormaPago = FormaPago;
        this.Combustible = Combustible;
    }

    public int getIDVehiculo() {
        return IDVehiculo;
    }

    public void setIDVehiculo(int IDVehiculo) {
        this.IDVehiculo = IDVehiculo;
    }

    public int getKilometraje() {
        return Kilometraje;
    }

    public void setKilometraje(int Kilometraje) {
        this.Kilometraje = Kilometraje;
    }

    public int getCarga() {
        return Carga;
    }

    public void setCarga(int Carga) {
        this.Carga = Carga;
    }

    public int getPrecioGas() {
        return PrecioGas;
    }

    public void setPrecioGas(int PrecioGas) {
        this.PrecioGas = PrecioGas;
    }

    public int getNoTicket() {
        return NoTicket;
    }

    public void setNoTicket(int NoTicket) {
        this.NoTicket = NoTicket;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public String getFormaPago() {
        return FormaPago;
    }

    public void setFormaPago(String FormaPago) {
        this.FormaPago = FormaPago;
    }

    public String getCombustible() {
        return Combustible;
    }

    public void setCombustible(String Combustible) {
        this.Combustible = Combustible;
    }
    
}
