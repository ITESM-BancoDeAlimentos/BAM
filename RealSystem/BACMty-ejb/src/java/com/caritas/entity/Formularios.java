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
@Table(name = "Formularios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formularios.findAll", query = "SELECT f FROM Formularios f"),
    @NamedQuery(name = "Formularios.findByIDForm", query = "SELECT f FROM Formularios f WHERE f.iDForm = :iDForm"),
    @NamedQuery(name = "Formularios.findLikeIDForm", query = "SELECT f FROM Formularios f WHERE f.iDForm LIKE :iDForm ORDER BY f.orden"),
    @NamedQuery(name = "Formularios.findByDescripcion", query = "SELECT f FROM Formularios f WHERE f.descripcion = :descripcion"),
    @NamedQuery(name = "Formularios.findByTipo", query = "SELECT f FROM Formularios f WHERE f.tipo = :tipo"),
    @NamedQuery(name = "Formularios.findByOrden", query = "SELECT f FROM Formularios f WHERE f.orden = :orden")})
public class Formularios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 27)
    @Column(name = "IDForm")
    private String iDForm;
    @Size(max = 50)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "Tipo")
    private Short tipo;
    @Column(name = "Orden")
    private Short orden;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDForm")
    private Collection<Accesos> accesosCollection;

    public Formularios() {
    }

    public Formularios(String iDForm) {
        this.iDForm = iDForm;
    }

    public String getIDForm() {
        return iDForm;
    }

    public void setIDForm(String iDForm) {
        this.iDForm = iDForm;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Short getTipo() {
        return tipo;
    }

    public void setTipo(Short tipo) {
        this.tipo = tipo;
    }

    public Short getOrden() {
        return orden;
    }

    public void setOrden(Short orden) {
        this.orden = orden;
    }

    @XmlTransient
    public Collection<Accesos> getAccesosCollection() {
        return accesosCollection;
    }

    public void setAccesosCollection(Collection<Accesos> accesosCollection) {
        this.accesosCollection = accesosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDForm != null ? iDForm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formularios)) {
            return false;
        }
        Formularios other = (Formularios) object;
        if ((this.iDForm == null && other.iDForm != null) || (this.iDForm != null && !this.iDForm.equals(other.iDForm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescripcion();
    }
    
}
