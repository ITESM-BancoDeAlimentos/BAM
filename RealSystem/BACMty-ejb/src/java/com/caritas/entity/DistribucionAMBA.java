package com.caritas.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "DistribucionAMBA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DistribucionAMBA.findAll", query = "SELECT d FROM DistribucionAMBA d"),
    @NamedQuery(name = "DistribucionAMBA.findByIDFolio", query = "SELECT d FROM DistribucionAMBA d WHERE d.iDFolio = :iDFolio"),
    @NamedQuery(name = "DistribucionAMBA.findByCedisOrigen", query = "SELECT d FROM DistribucionAMBA d WHERE d.cedisOrigen = :cedisOrigen"),
    @NamedQuery(name = "DistribucionAMBA.findByTotalKilogramos", query = "SELECT d FROM DistribucionAMBA d WHERE d.totalKilogramos = :totalKilogramos"),
    @NamedQuery(name = "DistribucionAMBA.findByTotalPiezas", query = "SELECT d FROM DistribucionAMBA d WHERE d.totalPiezas = :totalPiezas"),
    @NamedQuery(name = "DistribucionAMBA.findByTotalFlete", query = "SELECT d FROM DistribucionAMBA d WHERE d.totalFlete = :totalFlete")})
public class DistribucionAMBA implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "IDFolio")
    private String iDFolio;
    @Size(max = 50)
    @Column(name = "CedisOrigen")
    private String cedisOrigen;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TotalKilogramos")
    private double totalKilogramos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TotalPiezas")
    private int totalPiezas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TotalFlete")
    private double totalFlete;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FechaMov")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMov;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TotalPoblacion")
    private int totalPoblacion;
    @JoinColumn(name = "IDSucursales", referencedColumnName = "IDSucursales")
    @ManyToOne
    private Sucursales iDSucursales;
    @JoinColumn(name = "IDDonante", referencedColumnName = "IDDonante")
    @ManyToOne(optional = false)
    private Donantes iDDonante;

    public DistribucionAMBA() {
    }

    public DistribucionAMBA(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public DistribucionAMBA(String iDFolio, double totalKilogramos, int totalPiezas, double totalFlete) {
        this.iDFolio = iDFolio;
        this.totalKilogramos = totalKilogramos;
        this.totalPiezas = totalPiezas;
        this.totalFlete = totalFlete;
    }

    //<editor-fold defaultstate="collapsed" desc="SETTERS & GETTERS">
    public String getIDFolio() {
        return iDFolio;
    }

    public void setIDFolio(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public String getCedisOrigen() {
        if (cedisOrigen == null) {
            cedisOrigen = "";
        }
        return cedisOrigen;
    }

    public void setCedisOrigen(String cedisOrigen) {
        this.cedisOrigen = cedisOrigen;
    }

    public double getTotalKilogramos() {
        return totalKilogramos;
    }

    public void setTotalKilogramos(double totalKilogramos) {
        this.totalKilogramos = totalKilogramos;
    }

    public int getTotalPiezas() {
        return totalPiezas;
    }

    public void setTotalPiezas(int totalPiezas) {
        this.totalPiezas = totalPiezas;
    }

    public double getTotalFlete() {
        return totalFlete;
    }

    public void setTotalFlete(double totalFlete) {
        this.totalFlete = totalFlete;
    }

    public Sucursales getIDSucursales() {
        return iDSucursales;
    }

    public void setIDSucursales(Sucursales iDSucursales) {
        this.iDSucursales = iDSucursales;
    }

    public Donantes getIDDonante() {
        return iDDonante;
    }

    public void setIDDonante(Donantes iDDonante) {
        this.iDDonante = iDDonante;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

    public int getTotalPoblacion() {
        return totalPoblacion;
    }

    public Double getTotalPoblacionDouble() {
        return (double) (totalPoblacion);
    }

    public void setTotalPoblacion(int totalPoblacion) {
        this.totalPoblacion = totalPoblacion;
    }

    //</editor-fold>
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDFolio != null ? iDFolio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DistribucionAMBA)) {
            return false;
        }
        DistribucionAMBA other = (DistribucionAMBA) object;
        if ((this.iDFolio == null && other.iDFolio != null) || (this.iDFolio != null && !this.iDFolio.equals(other.iDFolio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.DistribucionAMBA[ iDFolio=" + iDFolio + " ]";
    }
}
