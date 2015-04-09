/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author software
 */
@Entity
@Table(name = "ClaseReporte", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClaseReporte.findAll", query = "SELECT c FROM ClaseReporte c"),
    @NamedQuery(name = "ClaseReporte.findByIDClaRe", query = "SELECT c FROM ClaseReporte c WHERE c.iDClaRe = :iDClaRe"),
    @NamedQuery(name = "ClaseReporte.findByReporte", query = "SELECT c FROM ClaseReporte c WHERE c.reporte = :reporte"),
    @NamedQuery(name = "ClaseReporte.findByClase", query = "SELECT c FROM ClaseReporte c WHERE c.clase = :clase")})
public class ClaseReporte implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDClaRe")
    private Integer iDClaRe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Reporte")
    private String reporte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Clase")
    private String clase;

    public ClaseReporte() {
    }

    public ClaseReporte(Integer iDClaRe) {
        this.iDClaRe = iDClaRe;
    }

    public ClaseReporte(Integer iDClaRe, String reporte, String clase) {
        this.iDClaRe = iDClaRe;
        this.reporte = reporte;
        this.clase = clase;
    }

    public Integer getIDClaRe() {
        return iDClaRe;
    }

    public void setIDClaRe(Integer iDClaRe) {
        this.iDClaRe = iDClaRe;
    }

    public String getReporte() {
        return reporte;
    }

    public void setReporte(String reporte) {
        this.reporte = reporte;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDClaRe != null ? iDClaRe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClaseReporte)) {
            return false;
        }
        ClaseReporte other = (ClaseReporte) object;
        if ((this.iDClaRe == null && other.iDClaRe != null) || (this.iDClaRe != null && !this.iDClaRe.equals(other.iDClaRe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.ClaseReporte[ iDClaRe=" + iDClaRe + " ]";
    }
    
}
