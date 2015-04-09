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
@Table(name = "ReciboDeducible")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReciboDeducible.findAll", query = "SELECT r FROM ReciboDeducible r"),
    @NamedQuery(name = "ReciboDeducible.findByIDFolio", query = "SELECT r FROM ReciboDeducible r WHERE r.iDFolio = :iDFolio"),
    @NamedQuery(name = "ReciboDeducible.findByFecha", query = "SELECT r FROM ReciboDeducible r WHERE r.fecha = :fecha"),
    @NamedQuery(name = "ReciboDeducible.findByCantidad", query = "SELECT r FROM ReciboDeducible r WHERE r.cantidad = :cantidad"),
    @NamedQuery(name = "ReciboDeducible.findByCantLetra", query = "SELECT r FROM ReciboDeducible r WHERE r.cantLetra = :cantLetra"),
    @NamedQuery(name = "ReciboDeducible.findByObservacion", query = "SELECT r FROM ReciboDeducible r WHERE r.observacion = :observacion"),
    @NamedQuery(name = "ReciboDeducible.findByPiezas", query = "SELECT r FROM ReciboDeducible r WHERE r.piezas = :piezas"),
    @NamedQuery(name = "ReciboDeducible.findByKilos", query = "SELECT r FROM ReciboDeducible r WHERE r.kilos = :kilos")})
public class ReciboDeducible implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "IDFolio")
    private String iDFolio;
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Cantidad")
    private BigDecimal cantidad;
    @Size(max = 255)
    @Column(name = "CantLetra")
    private String cantLetra;
    @Size(max = 255)
    @Column(name = "Observacion")
    private String observacion;
    @Column(name = "Piezas")
    private BigDecimal piezas;
    @Column(name = "Kilos")
    private BigDecimal kilos;
    @JoinColumn(name = "IDDonante", referencedColumnName = "IDDonante")
    @ManyToOne
    private Donantes iDDonante;

    public ReciboDeducible() {
    }

    public ReciboDeducible(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public String getIDFolio() {
        return iDFolio;
    }

    public void setIDFolio(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getCantLetra() {
        return cantLetra;
    }

    public void setCantLetra(String cantLetra) {
        this.cantLetra = cantLetra;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public BigDecimal getPiezas() {
        return piezas;
    }

    public void setPiezas(BigDecimal piezas) {
        this.piezas = piezas;
    }

    public BigDecimal getKilos() {
        return kilos;
    }

    public void setKilos(BigDecimal kilos) {
        this.kilos = kilos;
    }

    public Donantes getIDDonante() {
        return iDDonante;
    }

    public void setIDDonante(Donantes iDDonante) {
        this.iDDonante = iDDonante;
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
        if (!(object instanceof ReciboDeducible)) {
            return false;
        }
        ReciboDeducible other = (ReciboDeducible) object;
        if ((this.iDFolio == null && other.iDFolio != null) || (this.iDFolio != null && !this.iDFolio.equals(other.iDFolio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.ReciboDeducible[ iDFolio=" + iDFolio + " ]";
    }
    
}
