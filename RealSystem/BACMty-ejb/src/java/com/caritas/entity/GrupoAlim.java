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
@Table(name = "GrupoAlim", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoAlim.findAll", query = "SELECT g FROM GrupoAlim g"),
    @NamedQuery(name = "GrupoAlim.findLike", query = "SELECT g FROM GrupoAlim g WHERE g.idGrupoAlim LIKE :query OR g.grupoAlim LIKE :query"),
    @NamedQuery(name = "GrupoAlim.findByIdGrupoAlim", query = "SELECT g FROM GrupoAlim g WHERE g.idGrupoAlim = :idGrupoAlim"),
    @NamedQuery(name = "GrupoAlim.findByGrupoAlim", query = "SELECT g FROM GrupoAlim g WHERE g.grupoAlim = :grupoAlim"),
    @NamedQuery(name = "GrupoAlim.findByPrecioRecupera", query = "SELECT g FROM GrupoAlim g WHERE g.precioRecupera = :precioRecupera")})
public class GrupoAlim implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "IdGrupoAlim", nullable = false, length = 8)
    private String idGrupoAlim;
    @Size(max = 30)
    @Column(name = "GrupoAlim", length = 30)
    private String grupoAlim;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PrecioRecupera", precision = 53)
    private Double precioRecupera;
    @OneToMany(mappedBy = "iDGrupoAlim")
    private Collection<Articulos> articulosCollection;

    public GrupoAlim() {
    }

    public GrupoAlim(String idGrupoAlim) {
        this.idGrupoAlim = idGrupoAlim;
    }

    public String getIdGrupoAlim() {
        return idGrupoAlim;
    }

    public void setIdGrupoAlim(String idGrupoAlim) {
        this.idGrupoAlim = idGrupoAlim;
    }

    public String getGrupoAlim() {
        return grupoAlim;
    }

    public void setGrupoAlim(String grupoAlim) {
        this.grupoAlim = grupoAlim;
    }

    public Double getPrecioRecupera() {
        return precioRecupera;
    }

    public void setPrecioRecupera(Double precioRecupera) {
        this.precioRecupera = precioRecupera;
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
        hash += (idGrupoAlim != null ? idGrupoAlim.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoAlim)) {
            return false;
        }
        GrupoAlim other = (GrupoAlim) object;
        if ((this.idGrupoAlim == null && other.idGrupoAlim != null) || (this.idGrupoAlim != null && !this.idGrupoAlim.equals(other.idGrupoAlim))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.GrupoAlim[ idGrupoAlim=" + idGrupoAlim + " ]";
    }
    
}
