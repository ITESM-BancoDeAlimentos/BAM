/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "FoliosCaja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FoliosCaja.findAll", query = "SELECT f FROM FoliosCaja f"),
    @NamedQuery(name = "FoliosCaja.findByFolioCaja", query = "SELECT f FROM FoliosCaja f WHERE f.folioCaja = :folioCaja")})
public class FoliosCaja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FolioCaja")
    private String folioCaja;

    public FoliosCaja() {
    }

    public FoliosCaja(String folioCaja) {
        this.folioCaja = folioCaja;
    }

    public String getFolioCaja() {
        return folioCaja;
    }

    public void setFolioCaja(String folioCaja) {
        this.folioCaja = folioCaja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (folioCaja != null ? folioCaja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FoliosCaja)) {
            return false;
        }
        FoliosCaja other = (FoliosCaja) object;
        if ((this.folioCaja == null && other.folioCaja != null) || (this.folioCaja != null && !this.folioCaja.equals(other.folioCaja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.FoliosCaja[ folioCaja=" + folioCaja + " ]";
    }
    
}
