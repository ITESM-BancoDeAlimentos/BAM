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
@Table(name = "DonativoEsp_Det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DonativoEspDet.findAll", query = "SELECT d FROM DonativoEspDet d"),
    @NamedQuery(name = "DonativoEspDet.findByIDFolio", query = "SELECT d FROM DonativoEspDet d WHERE d.iDFolio = :iDFolio"),
    @NamedQuery(name = "DonativoEspDet.findByProducto", query = "SELECT d FROM DonativoEspDet d WHERE d.producto = :producto"),
    @NamedQuery(name = "DonativoEspDet.findByCantidad", query = "SELECT d FROM DonativoEspDet d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DonativoEspDet.findByDescripcion", query = "SELECT d FROM DonativoEspDet d WHERE d.descripcion = :descripcion"),
    @NamedQuery(name = "DonativoEspDet.findByPesoTotal", query = "SELECT d FROM DonativoEspDet d WHERE d.pesoTotal = :pesoTotal"),
    @NamedQuery(name = "DonativoEspDet.findByCostoUnitario", query = "SELECT d FROM DonativoEspDet d WHERE d.costoUnitario = :costoUnitario"),
    @NamedQuery(name = "DonativoEspDet.findByIDDonativoEspDet", query = "SELECT d FROM DonativoEspDet d WHERE d.iDDonativoEspDet = :iDDonativoEspDet")})
public class DonativoEspDet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "IDFolio")
    private String iDFolio;
    @Size(max = 50)
    @Column(name = "Producto")
    private String producto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cantidad")
    private BigDecimal cantidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PesoTotal")
    private BigDecimal pesoTotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CostoUnitario")
    private BigDecimal costoUnitario;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDDonativoEsp_Det")
    private Integer iDDonativoEspDet;

    public DonativoEspDet() {
    }

    public DonativoEspDet(Integer iDDonativoEspDet) {
        this.iDDonativoEspDet = iDDonativoEspDet;
    }

    public DonativoEspDet(Integer iDDonativoEspDet, String iDFolio, BigDecimal cantidad, String descripcion, BigDecimal pesoTotal, BigDecimal costoUnitario) {
        this.iDDonativoEspDet = iDDonativoEspDet;
        this.iDFolio = iDFolio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.pesoTotal = pesoTotal;
        this.costoUnitario = costoUnitario;
    }

    public String getIDFolio() {
        return iDFolio;
    }

    public void setIDFolio(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(BigDecimal pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public Integer getIDDonativoEspDet() {
        return iDDonativoEspDet;
    }

    public void setIDDonativoEspDet(Integer iDDonativoEspDet) {
        this.iDDonativoEspDet = iDDonativoEspDet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDDonativoEspDet != null ? iDDonativoEspDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DonativoEspDet)) {
            return false;
        }
        DonativoEspDet other = (DonativoEspDet) object;
        if ((this.iDDonativoEspDet == null && other.iDDonativoEspDet != null) || (this.iDDonativoEspDet != null && !this.iDDonativoEspDet.equals(other.iDDonativoEspDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.DonativoEspDet[ iDDonativoEspDet=" + iDDonativoEspDet + " ]";
    }
    
}
