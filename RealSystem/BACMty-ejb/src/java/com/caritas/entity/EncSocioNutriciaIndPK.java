/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author tecnologia
 */
@Embeddable
public class EncSocioNutriciaIndPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "Expediente")
    private int expediente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AreaGeo")
    private char areaGeo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "Nombre")
    private String nombre;

    public EncSocioNutriciaIndPK() {
    }

    public EncSocioNutriciaIndPK(int expediente, char areaGeo, String nombre) {
        this.expediente = expediente;
        this.areaGeo = areaGeo;
        this.nombre = nombre;
    }

    public int getExpediente() {
        return expediente;
    }

    public void setExpediente(int expediente) {
        this.expediente = expediente;
    }

    public char getAreaGeo() {
        return areaGeo;
    }

    public void setAreaGeo(char areaGeo) {
        this.areaGeo = areaGeo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) expediente;
        hash += (int) areaGeo;
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncSocioNutriciaIndPK)) {
            return false;
        }
        EncSocioNutriciaIndPK other = (EncSocioNutriciaIndPK) object;
        if (this.expediente != other.expediente) {
            return false;
        }
        if (this.areaGeo != other.areaGeo) {
            return false;
        }
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.EncSocioNutriciaIndPK[ expediente=" + expediente + ", areaGeo=" + areaGeo + ", nombre=" + nombre + " ]";
    }
    
}
