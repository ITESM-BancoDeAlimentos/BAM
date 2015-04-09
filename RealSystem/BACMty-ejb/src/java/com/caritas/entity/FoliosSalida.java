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
@Table(name = "FoliosSalida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FoliosSalida.findAll", query = "SELECT f FROM FoliosSalida f"),
    @NamedQuery(name = "FoliosSalida.findByFolioSalida", query = "SELECT f FROM FoliosSalida f WHERE f.folioSalida = :folioSalida")})
public class FoliosSalida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FolioSalida")
    private String folioSalida;

    public FoliosSalida() {
    }

    public FoliosSalida(String folioSalida) {
        this.folioSalida = folioSalida;
    }

    public String getFolioSalida() {
        return folioSalida;
    }

    public void setFolioSalida(String folioSalida) {
        this.folioSalida = folioSalida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (folioSalida != null ? folioSalida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FoliosSalida)) {
            return false;
        }
        FoliosSalida other = (FoliosSalida) object;
        if ((this.folioSalida == null && other.folioSalida != null) || (this.folioSalida != null && !this.folioSalida.equals(other.folioSalida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.FoliosSalida[ folioSalida=" + folioSalida + " ]";
    }
    
}
