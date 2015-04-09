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
@Table(name = "AreaPrograma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AreaPrograma.findAll", query = "SELECT a FROM AreaPrograma a"),
    @NamedQuery(name = "AreaPrograma.findByIDAreaPrograma", query = "SELECT a FROM AreaPrograma a WHERE a.iDAreaPrograma = :iDAreaPrograma")})
public class AreaPrograma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDAreaPrograma")
    private Integer iDAreaPrograma;
    @JoinColumn(name = "IDPrograma", referencedColumnName = "IDPrograma")
    @ManyToOne(optional = false)
    private Programas iDPrograma;
    @JoinColumn(name = "IDArea", referencedColumnName = "IDArea")
    @ManyToOne(optional = false)
    private Areas iDArea;

    public AreaPrograma() {
    }

    public AreaPrograma(Integer iDAreaPrograma) {
        this.iDAreaPrograma = iDAreaPrograma;
    }

    public Integer getIDAreaPrograma() {
        return iDAreaPrograma;
    }

    public void setIDAreaPrograma(Integer iDAreaPrograma) {
        this.iDAreaPrograma = iDAreaPrograma;
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
        hash += (iDAreaPrograma != null ? iDAreaPrograma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AreaPrograma)) {
            return false;
        }
        AreaPrograma other = (AreaPrograma) object;
        if ((this.iDAreaPrograma == null && other.iDAreaPrograma != null) || (this.iDAreaPrograma != null && !this.iDAreaPrograma.equals(other.iDAreaPrograma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.AreaPrograma[ iDAreaPrograma=" + iDAreaPrograma + " ]";
    }
    
}
