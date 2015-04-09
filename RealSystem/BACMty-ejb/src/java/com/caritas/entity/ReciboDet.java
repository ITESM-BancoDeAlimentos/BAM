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
@Table(name = "ReciboDet", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReciboDet.findAll", query = "SELECT r FROM ReciboDet r"),
    @NamedQuery(name = "ReciboDet.findByIDFolio", query = "SELECT r FROM ReciboDet r WHERE r.iDFolio = :iDFolio"),
    @NamedQuery(name = "ReciboDet.findByTipoMov", query = "SELECT r FROM ReciboDet r WHERE r.tipoMov = :tipoMov"),
    @NamedQuery(name = "ReciboDet.findByFechaCad", query = "SELECT r FROM ReciboDet r WHERE r.fechaCad = :fechaCad"),
    @NamedQuery(name = "ReciboDet.findByCantidad", query = "SELECT r FROM ReciboDet r WHERE r.cantidad = :cantidad"),
    @NamedQuery(name = "ReciboDet.findByPeso", query = "SELECT r FROM ReciboDet r WHERE r.peso = :peso"),
    @NamedQuery(name = "ReciboDet.findByMerma", query = "SELECT r FROM ReciboDet r WHERE r.merma = :merma"),
    @NamedQuery(name = "ReciboDet.findByCostoBenef", query = "SELECT r FROM ReciboDet r WHERE r.costoBenef = :costoBenef"),
    @NamedQuery(name = "ReciboDet.findByCuotaRecup", query = "SELECT r FROM ReciboDet r WHERE r.cuotaRecup = :cuotaRecup"),
    @NamedQuery(name = "ReciboDet.findByIDReciboDet", query = "SELECT r FROM ReciboDet r WHERE r.iDReciboDet = :iDReciboDet"),
    @NamedQuery(name = "ReciboDet.findByIDFolioTipo", query = "SELECT r FROM ReciboDet r WHERE r.iDFolio = :iDFolio AND r.tipoMov = :tipoMov ORDER BY r.iDReciboDet"),
    @NamedQuery(name = "ReciboDet.findArticulo", query = "SELECT a FROM Articulos a WHERE a.iDArticulo = :iDArticulo")})
public class ReciboDet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "IDFolio", nullable = false, length = 10)
    private String iDFolio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TipoMov", nullable = false, length = 10)
    private String tipoMov;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FechaCad", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cantidad", nullable = false)
    private double cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Peso", precision = 53)
    private Double peso;
    @Column(name = "Merma", precision = 53)
    private Double merma;
    @Column(name = "CostoBenef", precision = 53)
    private Double costoBenef;
    @Column(name = "CuotaRecup", precision = 53)
    private Double cuotaRecup;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRecibo_Det", nullable = false)
    private Integer iDReciboDet;
    @JoinColumn(name = "IDPrograma", referencedColumnName = "IDPrograma")
    @ManyToOne
    private Programas iDPrograma;
    @JoinColumn(name = "IDArticulo", referencedColumnName = "IDArticulo", nullable = false)
    @ManyToOne(optional = false)
    private Articulos iDArticulo;
    @JoinColumn(name = "IDArea", referencedColumnName = "IDArea")
    @ManyToOne
    private Areas iDArea;

    public ReciboDet() {
    }

    public ReciboDet(Integer iDReciboDet) {
        this.iDReciboDet = iDReciboDet;
    }

    public ReciboDet(Integer iDReciboDet, String iDFolio, String tipoMov, Date fechaCad, double cantidad) {
        this.iDReciboDet = iDReciboDet;
        this.iDFolio = iDFolio;
        this.tipoMov = tipoMov;
        this.fechaCad = fechaCad;
        this.cantidad = cantidad;
    }

    public String getIDFolio() {
        return iDFolio;
    }

    public void setIDFolio(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public String getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
    }

    public Date getFechaCad() {
        return fechaCad;
    }

    public void setFechaCad(Date fechaCad) {
        this.fechaCad = fechaCad;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getMerma() {
        return merma;
    }

    public void setMerma(Double merma) {
        this.merma = merma;
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

    public Integer getIDReciboDet() {
        return iDReciboDet;
    }

    public void setIDReciboDet(Integer iDReciboDet) {
        this.iDReciboDet = iDReciboDet;
    }

    public Programas getIDPrograma() {
        return iDPrograma;
    }

    public void setIDPrograma(Programas iDPrograma) {
        this.iDPrograma = iDPrograma;
    }

    public Articulos getIDArticulo() {
        return iDArticulo;
    }

    public void setIDArticulo(Articulos iDArticulo) {
        this.iDArticulo = iDArticulo;
    }

    public Areas getIDArea() {
        return iDArea;
    }

    public void setIDArea(Areas iDArea) {
        this.iDArea = iDArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDReciboDet != null ? iDReciboDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReciboDet)) {
            return false;
        }
        ReciboDet other = (ReciboDet) object;
        if ((this.iDReciboDet == null && other.iDReciboDet != null) || (this.iDReciboDet != null && !this.iDReciboDet.equals(other.iDReciboDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.ReciboDet[ iDReciboDet=" + iDReciboDet + " ]";
    }
    
}
