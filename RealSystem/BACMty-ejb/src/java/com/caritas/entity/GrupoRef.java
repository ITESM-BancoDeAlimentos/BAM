/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "GrupoRef", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoRef.findAll", query = "SELECT g FROM GrupoRef g"),
    @NamedQuery(name = "GrupoRef.findLike", query = "SELECT g FROM GrupoRef g WHERE g.iDGrupoRef LIKE :query OR g.grupoRef LIKE :query"),
    @NamedQuery(name = "GrupoRef.findByIDGrupoRef", query = "SELECT g FROM GrupoRef g WHERE g.iDGrupoRef = :iDGrupoRef"),
    @NamedQuery(name = "GrupoRef.findByGrupoRef", query = "SELECT g FROM GrupoRef g WHERE g.grupoRef = :grupoRef")})
public class GrupoRef implements Serializable {
    @OneToMany(mappedBy = "iDGrupoRef")
    private Collection<Movimientos> movimientosCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "IDGrupoRef", nullable = false, length = 10)
    private String iDGrupoRef;
    @Size(max = 20)
    @Column(name = "GrupoRef", length = 20)
    private String grupoRef;
    @OneToMany(mappedBy = "iDGrupoRef")
    private Collection<Recibo> reciboCollection;
    @OneToMany(mappedBy = "iDGrupoRef")
    private Collection<Articulos> articulosCollection;

    public GrupoRef() {
    }

    public GrupoRef(String iDGrupoRef) {
        this.iDGrupoRef = iDGrupoRef;
    }

    public String getIDGrupoRef() {
        return iDGrupoRef;
    }

    public void setIDGrupoRef(String iDGrupoRef) {
        this.iDGrupoRef = iDGrupoRef;
    }

    public String getGrupoRef() {
        return grupoRef;
    }

    public void setGrupoRef(String grupoRef) {
        this.grupoRef = grupoRef;
    }

    @XmlTransient
    public Collection<Recibo> getReciboCollection() {
        return reciboCollection;
    }

    public void setReciboCollection(Collection<Recibo> reciboCollection) {
        this.reciboCollection = reciboCollection;
    }

    @XmlTransient
    public Collection<Articulos> getArticulosCollection() {
        return articulosCollection;
    }

    public void setArticulosCollection(Collection<Articulos> articulosCollection) {
        this.articulosCollection = articulosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDGrupoRef != null ? iDGrupoRef.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoRef)) {
            return false;
        }
        GrupoRef other = (GrupoRef) object;
        if ((this.iDGrupoRef == null && other.iDGrupoRef != null) || (this.iDGrupoRef != null && !this.iDGrupoRef.equals(other.iDGrupoRef))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return iDGrupoRef + " : " + grupoRef;
    }

    @XmlTransient
    public Collection<Movimientos> getMovimientosCollection() {
        return movimientosCollection;
    }

    public void setMovimientosCollection(Collection<Movimientos> movimientosCollection) {
        this.movimientosCollection = movimientosCollection;
    }
    
}
