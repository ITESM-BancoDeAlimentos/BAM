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
@Table(name = "FoliosDonativoEsp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FoliosDonativoEsp.findAll", query = "SELECT f FROM FoliosDonativoEsp f"),
    @NamedQuery(name = "FoliosDonativoEsp.findByFolioDonativoEsp", query = "SELECT f FROM FoliosDonativoEsp f WHERE f.folioDonativoEsp = :folioDonativoEsp")})
public class FoliosDonativoEsp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FolioDonativoEsp")
    private String folioDonativoEsp;

    public FoliosDonativoEsp() {
    }

    public FoliosDonativoEsp(String folioDonativoEsp) {
        this.folioDonativoEsp = folioDonativoEsp;
    }

    public String getFolioDonativoEsp() {
        return folioDonativoEsp;
    }

    public void setFolioDonativoEsp(String folioDonativoEsp) {
        this.folioDonativoEsp = folioDonativoEsp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (folioDonativoEsp != null ? folioDonativoEsp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FoliosDonativoEsp)) {
            return false;
        }
        FoliosDonativoEsp other = (FoliosDonativoEsp) object;
        if ((this.folioDonativoEsp == null && other.folioDonativoEsp != null) || (this.folioDonativoEsp != null && !this.folioDonativoEsp.equals(other.folioDonativoEsp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.FoliosDonativoEsp[ folioDonativoEsp=" + folioDonativoEsp + " ]";
    }
    
}
