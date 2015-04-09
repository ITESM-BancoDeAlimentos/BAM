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
@Table(name = "PartidasValesCajas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PartidasValesCajas.findAll", query = "SELECT p FROM PartidasValesCajas p"),
    @NamedQuery(name = "PartidasValesCajas.findByIDFolio", query = "SELECT p FROM PartidasValesCajas p WHERE p.iDFolio = :iDFolio"),
    @NamedQuery(name = "PartidasValesCajas.findByPeso", query = "SELECT p FROM PartidasValesCajas p WHERE p.peso = :peso"),
    @NamedQuery(name = "PartidasValesCajas.findByFechaPesado", query = "SELECT p FROM PartidasValesCajas p WHERE p.fechaPesado = :fechaPesado"),
    @NamedQuery(name = "PartidasValesCajas.findByCosto", query = "SELECT p FROM PartidasValesCajas p WHERE p.costo = :costo"),
    @NamedQuery(name = "PartidasValesCajas.findByIDPartidasValesCajas", query = "SELECT p FROM PartidasValesCajas p WHERE p.iDPartidasValesCajas = :iDPartidasValesCajas")})
public class PartidasValesCajas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 10)
    @Column(name = "IDFolio")
    private String iDFolio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Peso")
    private Double peso;
    @Column(name = "FechaPesado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPesado;
    @Column(name = "Costo")
    private Double costo;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPartidasValesCajas")
    private Integer iDPartidasValesCajas;
    @JoinColumn(name = "IDCaja", referencedColumnName = "IDCaja")
    @ManyToOne
    private Cajas iDCaja;

    public PartidasValesCajas() {
    }

    public PartidasValesCajas(Integer iDPartidasValesCajas) {
        this.iDPartidasValesCajas = iDPartidasValesCajas;
    }

    public String getIDFolio() {
        return iDFolio;
    }

    public void setIDFolio(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Date getFechaPesado() {
        return fechaPesado;
    }

    public void setFechaPesado(Date fechaPesado) {
        this.fechaPesado = fechaPesado;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Integer getIDPartidasValesCajas() {
        return iDPartidasValesCajas;
    }

    public void setIDPartidasValesCajas(Integer iDPartidasValesCajas) {
        this.iDPartidasValesCajas = iDPartidasValesCajas;
    }

    public Cajas getIDCaja() {
        return iDCaja;
    }

    public void setIDCaja(Cajas iDCaja) {
        this.iDCaja = iDCaja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDPartidasValesCajas != null ? iDPartidasValesCajas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartidasValesCajas)) {
            return false;
        }
        PartidasValesCajas other = (PartidasValesCajas) object;
        if ((this.iDPartidasValesCajas == null && other.iDPartidasValesCajas != null) || (this.iDPartidasValesCajas != null && !this.iDPartidasValesCajas.equals(other.iDPartidasValesCajas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.PartidasValesCajas[ iDPartidasValesCajas=" + iDPartidasValesCajas + " ]";
    }
    
}
