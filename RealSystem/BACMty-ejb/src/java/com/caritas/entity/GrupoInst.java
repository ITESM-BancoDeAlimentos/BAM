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
@Table(name = "GrupoInst")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoInst.findAll", query = "SELECT g FROM GrupoInst g"),
    @NamedQuery(name = "GrupoInst.findLike", query = "SELECT g FROM GrupoInst g WHERE g.iDGrupoInst LIKE :query OR g.grupoInst LIKE :query"),
    @NamedQuery(name = "GrupoInst.findByIDGrupoInst", query = "SELECT g FROM GrupoInst g WHERE g.iDGrupoInst = :iDGrupoInst"),
    @NamedQuery(name = "GrupoInst.findByGrupoInst", query = "SELECT g FROM GrupoInst g WHERE g.grupoInst = :grupoInst")})
public class GrupoInst implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "IDGrupoInst")
    private String iDGrupoInst;
    @Size(max = 20)
    @Column(name = "GrupoInst")
    private String grupoInst;
    @OneToMany(mappedBy = "iDGrupoInst")
    private Collection<Instituciones> institucionesCollection;

    public GrupoInst() {
    }

    public GrupoInst(String iDGrupoInst) {
        this.iDGrupoInst = iDGrupoInst;
    }

    public String getIDGrupoInst() {
        return iDGrupoInst;
    }

    public void setIDGrupoInst(String iDGrupoInst) {
        this.iDGrupoInst = iDGrupoInst;
    }

    public String getGrupoInst() {
        return grupoInst;
    }

    public void setGrupoInst(String grupoInst) {
        this.grupoInst = grupoInst;
    }

    @XmlTransient
    public Collection<Instituciones> getInstitucionesCollection() {
        return institucionesCollection;
    }

    public void setInstitucionesCollection(Collection<Instituciones> institucionesCollection) {
        this.institucionesCollection = institucionesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDGrupoInst != null ? iDGrupoInst.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoInst)) {
            return false;
        }
        GrupoInst other = (GrupoInst) object;
        if ((this.iDGrupoInst == null && other.iDGrupoInst != null) || (this.iDGrupoInst != null && !this.iDGrupoInst.equals(other.iDGrupoInst))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.GrupoInst[ iDGrupoInst=" + iDGrupoInst + " ]";
    }

    }
