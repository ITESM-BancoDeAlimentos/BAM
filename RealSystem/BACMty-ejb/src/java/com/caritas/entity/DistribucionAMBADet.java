/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "DistribucionAMBA_Det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DistribucionAMBADet.findAll", query = "SELECT d FROM DistribucionAMBADet d"),
    @NamedQuery(name = "DistribucionAMBADet.findByIDFolio", query = "SELECT d FROM DistribucionAMBADet d WHERE d.distribucionAMBADetPK.iDFolio = :iDFolio"),
    @NamedQuery(name = "DistribucionAMBADet.findByIDBancoDeAlimentos", query = "SELECT d FROM DistribucionAMBADet d WHERE d.distribucionAMBADetPK.iDBancoDeAlimentos = :iDBancoDeAlimentos"),
    @NamedQuery(name = "DistribucionAMBADet.findByRemision", query = "SELECT d FROM DistribucionAMBADet d WHERE d.remision = :remision"),
    @NamedQuery(name = "DistribucionAMBADet.findByRecibe", query = "SELECT d FROM DistribucionAMBADet d WHERE d.recibe = :recibe"),
    @NamedQuery(name = "DistribucionAMBADet.findByFlete", query = "SELECT d FROM DistribucionAMBADet d WHERE d.flete = :flete"),
    @NamedQuery(name = "DistribucionAMBADet.findByObservaciones", query = "SELECT d FROM DistribucionAMBADet d WHERE d.observaciones = :observaciones")})
public class DistribucionAMBADet implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "Poblacion")
    private int poblacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Porcentaje")
    private double porcentaje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Kilogramos")
    private double kilogramos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Piezas")
    private double piezas;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DistribucionAMBADetPK distribucionAMBADetPK;
    @Size(max = 10)
    @Column(name = "Remision")
    private String remision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Recibe")
    private boolean recibe;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Flete")
    private Double flete;
    @Size(max = 50)
    @Column(name = "Observaciones")
    private String observaciones;
    @JoinColumn(name = "IDBancoDeAlimentos", referencedColumnName = "IDBancoDeAlimentos", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private BancosDeAlimentos bancosDeAlimentos;

    public DistribucionAMBADet() {
    }

    public DistribucionAMBADet(DistribucionAMBADetPK distribucionAMBADetPK) {
        this.distribucionAMBADetPK = distribucionAMBADetPK;
    }

    public DistribucionAMBADet(DistribucionAMBADetPK distribucionAMBADetPK, boolean recibe) {
        this.distribucionAMBADetPK = distribucionAMBADetPK;
        this.recibe = recibe;
    }

    public DistribucionAMBADet(String iDFolio, int iDBancoDeAlimentos) {
        this.distribucionAMBADetPK = new DistribucionAMBADetPK(iDFolio, iDBancoDeAlimentos);
    }

    public DistribucionAMBADetPK getDistribucionAMBADetPK() {
        return distribucionAMBADetPK;
    }

    public void setDistribucionAMBADetPK(DistribucionAMBADetPK distribucionAMBADetPK) {
        this.distribucionAMBADetPK = distribucionAMBADetPK;
    }

    public String getRemision() {
        return remision;
    }

    public void setRemision(String remision) {
        this.remision = remision;
    }

    public boolean getRecibe() {
        return recibe;
    }

    public void setRecibe(boolean recibe) {
        this.recibe = recibe;
    }

    public Double getFlete() {
        return flete;
    }

    public void setFlete(Double flete) {
        this.flete = flete;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BancosDeAlimentos getBancosDeAlimentos() {
        return bancosDeAlimentos;
    }

    public void setBancosDeAlimentos(BancosDeAlimentos bancosDeAlimentos) {
        this.bancosDeAlimentos = bancosDeAlimentos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (distribucionAMBADetPK != null ? distribucionAMBADetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DistribucionAMBADet)) {
            return false;
        }
        DistribucionAMBADet other = (DistribucionAMBADet) object;
        if ((this.distribucionAMBADetPK == null && other.distribucionAMBADetPK != null) || (this.distribucionAMBADetPK != null && !this.distribucionAMBADetPK.equals(other.distribucionAMBADetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.DistribucionAMBADet[ distribucionAMBADetPK=" + distribucionAMBADetPK + " ]";
    }

    public int getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(int poblacion) {
        this.poblacion = poblacion;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double getKilogramos() {
        return kilogramos;
    }

    public void setKilogramos(double kilogramos) {
        this.kilogramos = kilogramos;
    }

    public double getPiezas() {
        return piezas;
    }

    public void setPiezas(double piezas) {
        this.piezas = piezas;
    }
    
}
