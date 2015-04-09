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
@Table(name = "FoliosRecDeducible")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FoliosRecDeducible.findAll", query = "SELECT f FROM FoliosRecDeducible f"),
    @NamedQuery(name = "FoliosRecDeducible.findByFolioRecDeducible", query = "SELECT f FROM FoliosRecDeducible f WHERE f.folioRecDeducible = :folioRecDeducible")})
public class FoliosRecDeducible implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FolioRecDeducible")
    private String folioRecDeducible;

    public FoliosRecDeducible() {
    }

    public FoliosRecDeducible(String folioRecDeducible) {
        this.folioRecDeducible = folioRecDeducible;
    }

    public String getFolioRecDeducible() {
        return folioRecDeducible;
    }

    public void setFolioRecDeducible(String folioRecDeducible) {
        this.folioRecDeducible = folioRecDeducible;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (folioRecDeducible != null ? folioRecDeducible.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FoliosRecDeducible)) {
            return false;
        }
        FoliosRecDeducible other = (FoliosRecDeducible) object;
        if ((this.folioRecDeducible == null && other.folioRecDeducible != null) || (this.folioRecDeducible != null && !this.folioRecDeducible.equals(other.folioRecDeducible))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.FoliosRecDeducible[ folioRecDeducible=" + folioRecDeducible + " ]";
    }
    
}
