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
@Table(name = "Distribucion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Distribucion.findAll", query = "SELECT d FROM Distribucion d"),
    @NamedQuery(name = "Distribucion.findByDistribucion", query = "SELECT d FROM Distribucion d WHERE d.distribucion = :distribucion"),
    @NamedQuery(name = "Distribucion.findByIDDistribucion", query = "SELECT d FROM Distribucion d WHERE d.iDDistribucion = :iDDistribucion")})
public class Distribucion implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Distribucion")
    private Double distribucion;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDDistribucion")
    private Integer iDDistribucion;
    @JoinColumn(name = "IdPrograma", referencedColumnName = "IDPrograma")
    @ManyToOne
    private Programas idPrograma;
    @JoinColumn(name = "IdArea", referencedColumnName = "IDArea")
    @ManyToOne
    private Areas idArea;

    public Distribucion() {
    }

    public Distribucion(Integer iDDistribucion) {
        this.iDDistribucion = iDDistribucion;
    }

    public Double getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(Double distribucion) {
        this.distribucion = distribucion;
    }

    public Integer getIDDistribucion() {
        return iDDistribucion;
    }

    public void setIDDistribucion(Integer iDDistribucion) {
        this.iDDistribucion = iDDistribucion;
    }

    public Programas getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Programas idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Areas getIdArea() {
        return idArea;
    }

    public void setIdArea(Areas idArea) {
        this.idArea = idArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDDistribucion != null ? iDDistribucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Distribucion)) {
            return false;
        }
        Distribucion other = (Distribucion) object;
        if ((this.iDDistribucion == null && other.iDDistribucion != null) || (this.iDDistribucion != null && !this.iDDistribucion.equals(other.iDDistribucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.Distribucion[ iDDistribucion=" + iDDistribucion + " ]";
    }
    
}
