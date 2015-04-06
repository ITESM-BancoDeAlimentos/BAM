/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemabanco;

/**
 *
 * @author Enrique
 */
public class Visitas {
    private int IDRuta, IDSucursal;
    private boolean dias[];
    public Visitas(int IDRuta, int IDSucursal, boolean[] dias) {
        this.IDRuta = IDRuta;
        this.IDSucursal = IDSucursal;
        this.dias = dias;
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

    public boolean[] getDias() {
        return dias;
    }

    public void setDias(boolean[] dias) {
        this.dias = dias;
    }
    
}
