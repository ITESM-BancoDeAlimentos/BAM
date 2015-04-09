/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "Unidad", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unidad.findAll", query = "SELECT u FROM Unidad u"),
    @NamedQuery(name = "Unidad.findLike", query = "SELECT u FROM Unidad u WHERE u.iDUnidad LIKE :query OR u.unidad LIKE :query"),
    @NamedQuery(name = "Unidad.findByIDUnidad", query = "SELECT u FROM Unidad u WHERE u.iDUnidad = :iDUnidad"),
    @NamedQuery(name = "Unidad.findByUnidad", query = "SELECT u FROM Unidad u WHERE u.unidad = :unidad")})
public class Unidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "IDUnidad", nullable = false, length = 10)
    private String iDUnidad;
    @Size(max = 15)
    @Column(name = "Unidad", length = 15)
    private String unidad;
    @OneToMany(mappedBy = "unidadMed2")
    private Collection<Articulos> articulosCollection;
    @OneToMany(mappedBy = "unidadMed1")
    private Collection<Articulos> articulosCollection1;

    public Unidad() {
    }

    public Unidad(String iDUnidad) {
        this.iDUnidad = iDUnidad;
    }

    public String getIDUnidad() {
        return iDUnidad;
    }

    public void setIDUnidad(String iDUnidad) {
        this.iDUnidad = iDUnidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    @XmlTransient
    public Collection<Articulos> getArticulosCollection() {
        return articulosCollection;
    }

    public void setArticulosCollection(Collection<Articulos> articulosCollection) {
        this.articulosCollection = articulosCollection;
    }

    @XmlTransient
    public Collection<Articulos> getArticulosCollection1() {
        return articulosCollection1;
    }

    public void setArticulosCollection1(Collection<Articulos> articulosCollection1) {
        this.articulosCollection1 = articulosCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDUnidad != null ? iDUnidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unidad)) {
            return false;
        }
        Unidad other = (Unidad) object;
        if ((this.iDUnidad == null && other.iDUnidad != null) || (this.iDUnidad != null && !this.iDUnidad.equals(other.iDUnidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return unidad;
    }
    
}
