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
@Table(name = "FoliosEntrada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FoliosEntrada.findAll", query = "SELECT f FROM FoliosEntrada f"),
    @NamedQuery(name = "FoliosEntrada.findByFolioEntrada", query = "SELECT f FROM FoliosEntrada f WHERE f.folioEntrada = :folioEntrada")})
public class FoliosEntrada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FolioEntrada")
    private String folioEntrada;

    public FoliosEntrada() {
    }

    public FoliosEntrada(String folioEntrada) {
        this.folioEntrada = folioEntrada;
    }

    public String getFolioEntrada() {
        return folioEntrada;
    }

    public void setFolioEntrada(String folioEntrada) {
        this.folioEntrada = folioEntrada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (folioEntrada != null ? folioEntrada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FoliosEntrada)) {
            return false;
        }
        FoliosEntrada other = (FoliosEntrada) object;
        if ((this.folioEntrada == null && other.folioEntrada != null) || (this.folioEntrada != null && !this.folioEntrada.equals(other.folioEntrada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.FoliosEntrada[ folioEntrada=" + folioEntrada + " ]";
    }
    
}
