/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "tablaTmpDon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TablaTmpDon.findAll", query = "SELECT t FROM TablaTmpDon t"),
    @NamedQuery(name = "TablaTmpDon.findByInstitucion", query = "SELECT t FROM TablaTmpDon t WHERE t.institucion = :institucion"),
    @NamedQuery(name = "TablaTmpDon.findByArea", query = "SELECT t FROM TablaTmpDon t WHERE t.area = :area"),
    @NamedQuery(name = "TablaTmpDon.findByPrograma", query = "SELECT t FROM TablaTmpDon t WHERE t.programa = :programa"),
    @NamedQuery(name = "TablaTmpDon.findByIDFolioCArAbo", query = "SELECT t FROM TablaTmpDon t WHERE t.iDFolioCArAbo = :iDFolioCArAbo"),
    @NamedQuery(name = "TablaTmpDon.findByIDFolio", query = "SELECT t FROM TablaTmpDon t WHERE t.iDFolio = :iDFolio"),
    @NamedQuery(name = "TablaTmpDon.findByFechaMov", query = "SELECT t FROM TablaTmpDon t WHERE t.fechaMov = :fechaMov"),
    @NamedQuery(name = "TablaTmpDon.findByDonativo", query = "SELECT t FROM TablaTmpDon t WHERE t.donativo = :donativo"),
    @NamedQuery(name = "TablaTmpDon.findByIDTablaTmpDon", query = "SELECT t FROM TablaTmpDon t WHERE t.iDTablaTmpDon = :iDTablaTmpDon")})
public class TablaTmpDon implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "Institucion")
    private String institucion;
    @Size(max = 50)
    @Column(name = "Area")
    private String area;
    @Size(max = 50)
    @Column(name = "Programa")
    private String programa;
    @Size(max = 10)
    @Column(name = "IDFolioCArAbo")
    private String iDFolioCArAbo;
    @Size(max = 10)
    @Column(name = "IDFolio")
    private String iDFolio;
    @Column(name = "FechaMov")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMov;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Donativo")
    private BigDecimal donativo;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTablaTmpDon")
    private Integer iDTablaTmpDon;
    @JoinColumn(name = "IDInstitucion", referencedColumnName = "IDInstitucion")
    @ManyToOne(optional = false)
    private Instituciones iDInstitucion;

    public TablaTmpDon() {
    }

    public TablaTmpDon(Integer iDTablaTmpDon) {
        this.iDTablaTmpDon = iDTablaTmpDon;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getIDFolioCArAbo() {
        return iDFolioCArAbo;
    }

    public void setIDFolioCArAbo(String iDFolioCArAbo) {
        this.iDFolioCArAbo = iDFolioCArAbo;
    }

    public String getIDFolio() {
        return iDFolio;
    }

    public void setIDFolio(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

    public BigDecimal getDonativo() {
        return donativo;
    }

    public void setDonativo(BigDecimal donativo) {
        this.donativo = donativo;
    }

    public Integer getIDTablaTmpDon() {
        return iDTablaTmpDon;
    }

    public void setIDTablaTmpDon(Integer iDTablaTmpDon) {
        this.iDTablaTmpDon = iDTablaTmpDon;
    }

    public Instituciones getIDInstitucion() {
        return iDInstitucion;
    }

    public void setIDInstitucion(Instituciones iDInstitucion) {
        this.iDInstitucion = iDInstitucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDTablaTmpDon != null ? iDTablaTmpDon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TablaTmpDon)) {
            return false;
        }
        TablaTmpDon other = (TablaTmpDon) object;
        if ((this.iDTablaTmpDon == null && other.iDTablaTmpDon != null) || (this.iDTablaTmpDon != null && !this.iDTablaTmpDon.equals(other.iDTablaTmpDon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.TablaTmpDon[ iDTablaTmpDon=" + iDTablaTmpDon + " ]";
    }
    
}
