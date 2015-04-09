/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
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
@Table(name = "PartidasVales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PartidasVales.findAll", query = "SELECT p FROM PartidasVales p"),
    @NamedQuery(name = "PartidasVales.findByIDFolio", query = "SELECT p FROM PartidasVales p WHERE p.iDFolio = :iDFolio"),
    @NamedQuery(name = "PartidasVales.findByFechaCad", query = "SELECT p FROM PartidasVales p WHERE p.fechaCad = :fechaCad"),
    @NamedQuery(name = "PartidasVales.findByCantidad", query = "SELECT p FROM PartidasVales p WHERE p.cantidad = :cantidad"),
    @NamedQuery(name = "PartidasVales.findByCostoBenef", query = "SELECT p FROM PartidasVales p WHERE p.costoBenef = :costoBenef"),
    @NamedQuery(name = "PartidasVales.findByCuotaRecup", query = "SELECT p FROM PartidasVales p WHERE p.cuotaRecup = :cuotaRecup"),
    @NamedQuery(name = "PartidasVales.findByStatus", query = "SELECT p FROM PartidasVales p WHERE p.status = :status"),
    @NamedQuery(name = "PartidasVales.findByIDPartidasVales", query = "SELECT p FROM PartidasVales p WHERE p.iDPartidasVales = :iDPartidasVales")})
public class PartidasVales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "IDFolio")
    private String iDFolio;
    @Column(name = "FechaCad")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Cantidad")
    private Double cantidad;
    @Column(name = "CostoBenef")
    private Double costoBenef;
    @Column(name = "CuotaRecup")
    private Double cuotaRecup;
    @Size(max = 10)
    @Column(name = "Status")
    private String status;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPartidasVales")
    private Integer iDPartidasVales;
    @JoinColumn(name = "IDCaja", referencedColumnName = "IDCaja")
    @ManyToOne
    private Cajas iDCaja;
    @JoinColumn(name = "IDArticulo", referencedColumnName = "IDArticulo")
    @ManyToOne
    private Articulos iDArticulo;

    public PartidasVales() {
    }

    public PartidasVales(Integer iDPartidasVales) {
        this.iDPartidasVales = iDPartidasVales;
    }

    public PartidasVales(Integer iDPartidasVales, String iDFolio) {
        this.iDPartidasVales = iDPartidasVales;
        this.iDFolio = iDFolio;
    }

    public String getIDFolio() {
        return iDFolio;
    }

    public void setIDFolio(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public Date getFechaCad() {
        return fechaCad;
    }

    public void setFechaCad(Date fechaCad) {
        this.fechaCad = fechaCad;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getCostoBenef() {
        return costoBenef;
    }

    public void setCostoBenef(Double costoBenef) {
        this.costoBenef = costoBenef;
    }

    public Double getCuotaRecup() {
        return cuotaRecup;
    }

    public void setCuotaRecup(Double cuotaRecup) {
        this.cuotaRecup = cuotaRecup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIDPartidasVales() {
        return iDPartidasVales;
    }

    public void setIDPartidasVales(Integer iDPartidasVales) {
        this.iDPartidasVales = iDPartidasVales;
    }

    public Cajas getIDCaja() {
        return iDCaja;
    }

    public void setIDCaja(Cajas iDCaja) {
        this.iDCaja = iDCaja;
    }

    public Articulos getIDArticulo() {
        return iDArticulo;
    }

    public void setIDArticulo(Articulos iDArticulo) {
        this.iDArticulo = iDArticulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDPartidasVales != null ? iDPartidasVales.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartidasVales)) {
            return false;
        }
        PartidasVales other = (PartidasVales) object;
        if ((this.iDPartidasVales == null && other.iDPartidasVales != null) || (this.iDPartidasVales != null && !this.iDPartidasVales.equals(other.iDPartidasVales))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.PartidasVales[ iDPartidasVales=" + iDPartidasVales + " ]";
    }
    
}
