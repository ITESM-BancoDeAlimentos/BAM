/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "TablaTmp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TablaTmp.findAll", query = "SELECT t FROM TablaTmp t"),
    @NamedQuery(name = "TablaTmp.findByIDFolio", query = "SELECT t FROM TablaTmp t WHERE t.iDFolio = :iDFolio"),
    @NamedQuery(name = "TablaTmp.findByTotal", query = "SELECT t FROM TablaTmp t WHERE t.total = :total"),
    @NamedQuery(name = "TablaTmp.findByTotalCDescto", query = "SELECT t FROM TablaTmp t WHERE t.totalCDescto = :totalCDescto"),
    @NamedQuery(name = "TablaTmp.findByPorcDescto", query = "SELECT t FROM TablaTmp t WHERE t.porcDescto = :porcDescto")})
public class TablaTmp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "IDFolio")
    private String iDFolio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "Total")
    private BigDecimal total;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TotalCDescto")
    private BigDecimal totalCDescto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PorcDescto")
    private BigDecimal porcDescto;
    @JoinColumn(name = "IDInstitucion", referencedColumnName = "IDInstitucion")
    @ManyToOne(optional = false)
    private Instituciones iDInstitucion;

    public TablaTmp() {
    }

    public TablaTmp(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public TablaTmp(String iDFolio, BigDecimal total, BigDecimal totalCDescto, BigDecimal porcDescto) {
        this.iDFolio = iDFolio;
        this.total = total;
        this.totalCDescto = totalCDescto;
        this.porcDescto = porcDescto;
    }

    public String getIDFolio() {
        return iDFolio;
    }

    public void setIDFolio(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalCDescto() {
        return totalCDescto;
    }

    public void setTotalCDescto(BigDecimal totalCDescto) {
        this.totalCDescto = totalCDescto;
    }

    public BigDecimal getPorcDescto() {
        return porcDescto;
    }

    public void setPorcDescto(BigDecimal porcDescto) {
        this.porcDescto = porcDescto;
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
        hash += (iDFolio != null ? iDFolio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TablaTmp)) {
            return false;
        }
        TablaTmp other = (TablaTmp) object;
        if ((this.iDFolio == null && other.iDFolio != null) || (this.iDFolio != null && !this.iDFolio.equals(other.iDFolio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.TablaTmp[ iDFolio=" + iDFolio + " ]";
    }
    
}
