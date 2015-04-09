/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "RelacionRepVar", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelacionRepVar.findAll", query = "SELECT r FROM RelacionRepVar r"),
    @NamedQuery(name = "RelacionRepVar.findByIDRelRepVar", query = "SELECT r FROM RelacionRepVar r WHERE r.iDRelRepVar = :iDRelRepVar"),
    @NamedQuery(name = "RelacionRepVar.findByReporte", query = "SELECT r FROM RelacionRepVar r WHERE r.reporte = :reporte"),
    @NamedQuery(name = "RelacionRepVar.findByReporteVariedad", query = "SELECT r FROM RelacionRepVar r WHERE r.reporte = :reporte AND r.variedad = :variedad"),
    @NamedQuery(name = "RelacionRepVar.findByClase", query = "SELECT r FROM RelacionRepVar r WHERE r.clase = :clase")})
public class RelacionRepVar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRelRepVar")
    private Integer iDRelRepVar;
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
    @JoinColumn(name = "Variedad", referencedColumnName = "IDVariedad")
    @ManyToOne(fetch = FetchType.LAZY)
    private Variedad variedad;

    public RelacionRepVar() {
    }

    public RelacionRepVar(Integer iDRelRepVar) {
        this.iDRelRepVar = iDRelRepVar;
    }

    public RelacionRepVar(Integer iDRelRepVar, String reporte, String clase) {
        this.iDRelRepVar = iDRelRepVar;
        this.reporte = reporte;
        this.clase = clase;
    }

    public Integer getIDRelRepVar() {
        return iDRelRepVar;
    }

    public void setIDRelRepVar(Integer iDRelRepVar) {
        this.iDRelRepVar = iDRelRepVar;
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

    public Variedad getVariedad() {
        return variedad;
    }

    public void setVariedad(Variedad variedad) {
        this.variedad = variedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDRelRepVar != null ? iDRelRepVar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelacionRepVar)) {
            return false;
        }
        RelacionRepVar other = (RelacionRepVar) object;
        if ((this.iDRelRepVar == null && other.iDRelRepVar != null) || (this.iDRelRepVar != null && !this.iDRelRepVar.equals(other.iDRelRepVar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.RelacionRepVar[ iDRelRepVar=" + iDRelRepVar + " ]";
    }
    
}
