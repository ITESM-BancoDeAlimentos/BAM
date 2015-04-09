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
@Table(name = "FoliosCarAbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FoliosCarAbo.findAll", query = "SELECT f FROM FoliosCarAbo f"),
    @NamedQuery(name = "FoliosCarAbo.findByFolioCarAbo", query = "SELECT f FROM FoliosCarAbo f WHERE f.folioCarAbo = :folioCarAbo")})
public class FoliosCarAbo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FolioCarAbo")
    private String folioCarAbo;

    public FoliosCarAbo() {
    }

    public FoliosCarAbo(String folioCarAbo) {
        this.folioCarAbo = folioCarAbo;
    }

    public String getFolioCarAbo() {
        return folioCarAbo;
    }

    public void setFolioCarAbo(String folioCarAbo) {
        this.folioCarAbo = folioCarAbo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (folioCarAbo != null ? folioCarAbo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FoliosCarAbo)) {
            return false;
        }
        FoliosCarAbo other = (FoliosCarAbo) object;
        if ((this.folioCarAbo == null && other.folioCarAbo != null) || (this.folioCarAbo != null && !this.folioCarAbo.equals(other.folioCarAbo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.FoliosCarAbo[ folioCarAbo=" + folioCarAbo + " ]";
    }
    
}
