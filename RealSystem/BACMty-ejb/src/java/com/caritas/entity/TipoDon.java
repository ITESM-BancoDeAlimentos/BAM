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
@Table(name = "TipoDon", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDon.findAll", query = "SELECT t FROM TipoDon t"),
    @NamedQuery(name = "TipoDon.findByIDTipoDon", query = "SELECT t FROM TipoDon t WHERE t.iDTipoDon = :iDTipoDon"),
    @NamedQuery(name = "TipoDon.findByTipoDon", query = "SELECT t FROM TipoDon t WHERE t.tipoDon = :tipoDon"),
    @NamedQuery(name = "TipoDon.findAllIDTipoDon", query = "SELECT t.iDTipoDon FROM TipoDon t")})
public class TipoDon implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "IDTipoDon", nullable = false, length = 8)
    private String iDTipoDon;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TipoDon", nullable = false, length = 20)
    private String tipoDon;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDTipoDon")
    private Collection<Donantes> donantesCollection;

    public TipoDon() {
    }

    public TipoDon(String iDTipoDon) {
        this.iDTipoDon = iDTipoDon;
    }

    public TipoDon(String iDTipoDon, String tipoDon) {
        this.iDTipoDon = iDTipoDon;
        this.tipoDon = tipoDon;
    }

    public String getIDTipoDon() {
        return iDTipoDon;
    }

    public void setIDTipoDon(String iDTipoDon) {
        this.iDTipoDon = iDTipoDon;
    }

    public String getTipoDon() {
        return tipoDon;
    }

    public void setTipoDon(String tipoDon) {
        this.tipoDon = tipoDon;
    }

    @XmlTransient
    public Collection<Donantes> getDonantesCollection() {
        return donantesCollection;
    }

    public void setDonantesCollection(Collection<Donantes> donantesCollection) {
        this.donantesCollection = donantesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDTipoDon != null ? iDTipoDon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDon)) {
            return false;
        }
        TipoDon other = (TipoDon) object;
        if ((this.iDTipoDon == null && other.iDTipoDon != null) || (this.iDTipoDon != null && !this.iDTipoDon.equals(other.iDTipoDon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoDon;
    }
    
}
