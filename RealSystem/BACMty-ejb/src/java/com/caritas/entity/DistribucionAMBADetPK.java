/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author tecnologia
 */
@Embeddable
public class DistribucionAMBADetPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "IDFolio")
    private String iDFolio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDBancoDeAlimentos")
    private int iDBancoDeAlimentos;

    public DistribucionAMBADetPK() {
    }

    public DistribucionAMBADetPK(String iDFolio, int iDBancoDeAlimentos) {
        this.iDFolio = iDFolio;
        this.iDBancoDeAlimentos = iDBancoDeAlimentos;
    }

    public String getIDFolio() {
        return iDFolio;
    }

    public void setIDFolio(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public int getIDBancoDeAlimentos() {
        return iDBancoDeAlimentos;
    }

    public void setIDBancoDeAlimentos(int iDBancoDeAlimentos) {
        this.iDBancoDeAlimentos = iDBancoDeAlimentos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDFolio != null ? iDFolio.hashCode() : 0);
        hash += (int) iDBancoDeAlimentos;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DistribucionAMBADetPK)) {
            return false;
        }
        DistribucionAMBADetPK other = (DistribucionAMBADetPK) object;
        if ((this.iDFolio == null && other.iDFolio != null) || (this.iDFolio != null && !this.iDFolio.equals(other.iDFolio))) {
            return false;
        }
        if (this.iDBancoDeAlimentos != other.iDBancoDeAlimentos) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.DistribucionAMBADetPK[ iDFolio=" + iDFolio + ", iDBancoDeAlimentos=" + iDBancoDeAlimentos + " ]";
    }
    
}
