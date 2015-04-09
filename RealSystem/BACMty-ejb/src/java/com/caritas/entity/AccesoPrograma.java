/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "AccesoPrograma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccesoPrograma.findAll", query = "SELECT a FROM AccesoPrograma a"),
    @NamedQuery(name = "AccesoPrograma.findByIDAccesoPrograma", query = "SELECT a FROM AccesoPrograma a WHERE a.iDAccesoPrograma = :iDAccesoPrograma")})
public class AccesoPrograma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDAccesoPrograma")
    private Integer iDAccesoPrograma;
    @JoinColumn(name = "IDUsuario", referencedColumnName = "IDUsuario")
    @ManyToOne(optional = false)
    private Usuarios iDUsuario;
    @JoinColumn(name = "IDPrograma", referencedColumnName = "IDPrograma")
    @ManyToOne(optional = false)
    private Programas iDPrograma;
    @JoinColumn(name = "IDArea", referencedColumnName = "IDArea")
    @ManyToOne(optional = false)
    private Areas iDArea;

    public AccesoPrograma() {
    }

    public AccesoPrograma(Integer iDAccesoPrograma) {
        this.iDAccesoPrograma = iDAccesoPrograma;
    }

    public Integer getIDAccesoPrograma() {
        return iDAccesoPrograma;
    }

    public void setIDAccesoPrograma(Integer iDAccesoPrograma) {
        this.iDAccesoPrograma = iDAccesoPrograma;
    }

    public Usuarios getIDUsuario() {
        return iDUsuario;
    }

    public void setIDUsuario(Usuarios iDUsuario) {
        this.iDUsuario = iDUsuario;
    }

    public Programas getIDPrograma() {
        return iDPrograma;
    }

    public void setIDPrograma(Programas iDPrograma) {
        this.iDPrograma = iDPrograma;
    }

    public Areas getIDArea() {
        return iDArea;
    }

    public void setIDArea(Areas iDArea) {
        this.iDArea = iDArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDAccesoPrograma != null ? iDAccesoPrograma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccesoPrograma)) {
            return false;
        }
        AccesoPrograma other = (AccesoPrograma) object;
        if ((this.iDAccesoPrograma == null && other.iDAccesoPrograma != null) || (this.iDAccesoPrograma != null && !this.iDAccesoPrograma.equals(other.iDAccesoPrograma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.AccesoPrograma[ iDAccesoPrograma=" + iDAccesoPrograma + " ]";
    }
    
}
