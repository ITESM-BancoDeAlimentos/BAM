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
@Table(name = "MovCarAbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovCarAbo.findAll", query = "SELECT m FROM MovCarAbo m"),
    @NamedQuery(name = "MovCarAbo.findByTipoMov", query = "SELECT m FROM MovCarAbo m WHERE m.tipoMov = :tipoMov"),
    @NamedQuery(name = "MovCarAbo.findByIDFolioCarAbo", query = "SELECT m FROM MovCarAbo m WHERE m.iDFolioCarAbo = :iDFolioCarAbo"),
    @NamedQuery(name = "MovCarAbo.findByIDFolio", query = "SELECT m FROM MovCarAbo m WHERE m.iDFolio = :iDFolio"),
    @NamedQuery(name = "MovCarAbo.findByFechaMov", query = "SELECT m FROM MovCarAbo m WHERE m.fechaMov = :fechaMov"),
    @NamedQuery(name = "MovCarAbo.findByImporte", query = "SELECT m FROM MovCarAbo m WHERE m.importe = :importe"),
    @NamedQuery(name = "MovCarAbo.findByAbono", query = "SELECT m FROM MovCarAbo m WHERE m.abono = :abono"),
    @NamedQuery(name = "MovCarAbo.findByReferencia", query = "SELECT m FROM MovCarAbo m WHERE m.referencia = :referencia"),
    @NamedQuery(name = "MovCarAbo.findByStatus", query = "SELECT m FROM MovCarAbo m WHERE m.status = :status"),
    @NamedQuery(name = "MovCarAbo.findBySaldo", query = "SELECT m FROM MovCarAbo m WHERE m.saldo = :saldo"),
    @NamedQuery(name = "MovCarAbo.findByBonificacion", query = "SELECT m FROM MovCarAbo m WHERE m.bonificacion = :bonificacion"),
    @NamedQuery(name = "MovCarAbo.findByDonativo", query = "SELECT m FROM MovCarAbo m WHERE m.donativo = :donativo"),
    @NamedQuery(name = "MovCarAbo.findByIncobrable", query = "SELECT m FROM MovCarAbo m WHERE m.incobrable = :incobrable"),
    @NamedQuery(name = "MovCarAbo.findByIDMovCarAbo", query = "SELECT m FROM MovCarAbo m WHERE m.iDMovCarAbo = :iDMovCarAbo")})
public class MovCarAbo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 10)
    @Column(name = "TipoMov")
    private String tipoMov;
    @Size(max = 10)
    @Column(name = "IDFolioCarAbo")
    private String iDFolioCarAbo;
    @Size(max = 10)
    @Column(name = "IDFolio")
    private String iDFolio;
    @Column(name = "FechaMov")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMov;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Importe")
    private BigDecimal importe;
    @Column(name = "Abono")
    private BigDecimal abono;
    @Size(max = 50)
    @Column(name = "Referencia")
    private String referencia;
    @Size(max = 10)
    @Column(name = "Status")
    private String status;
    @Column(name = "Saldo")
    private BigDecimal saldo;
    @Column(name = "Bonificacion")
    private BigDecimal bonificacion;
    @Column(name = "Donativo")
    private BigDecimal donativo;
    @Column(name = "Incobrable")
    private BigDecimal incobrable;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDMovCarAbo")
    private Integer iDMovCarAbo;
    @JoinColumn(name = "IDPrograma", referencedColumnName = "IDPrograma")
    @ManyToOne
    private Programas iDPrograma;
    @JoinColumn(name = "IDInstitucion", referencedColumnName = "IDInstitucion")
    @ManyToOne
    private Instituciones iDInstitucion;
    @JoinColumn(name = "IDArea", referencedColumnName = "IDArea")
    @ManyToOne
    private Areas iDArea;

    public MovCarAbo() {
    }

    public MovCarAbo(Integer iDMovCarAbo) {
        this.iDMovCarAbo = iDMovCarAbo;
    }

    public String getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
    }

    public String getIDFolioCarAbo() {
        return iDFolioCarAbo;
    }

    public void setIDFolioCarAbo(String iDFolioCarAbo) {
        this.iDFolioCarAbo = iDFolioCarAbo;
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

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getAbono() {
        return abono;
    }

    public void setAbono(BigDecimal abono) {
        this.abono = abono;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(BigDecimal bonificacion) {
        this.bonificacion = bonificacion;
    }

    public BigDecimal getDonativo() {
        return donativo;
    }

    public void setDonativo(BigDecimal donativo) {
        this.donativo = donativo;
    }

    public BigDecimal getIncobrable() {
        return incobrable;
    }

    public void setIncobrable(BigDecimal incobrable) {
        this.incobrable = incobrable;
    }

    public Integer getIDMovCarAbo() {
        return iDMovCarAbo;
    }

    public void setIDMovCarAbo(Integer iDMovCarAbo) {
        this.iDMovCarAbo = iDMovCarAbo;
    }

    public Programas getIDPrograma() {
        return iDPrograma;
    }

    public void setIDPrograma(Programas iDPrograma) {
        this.iDPrograma = iDPrograma;
    }

    public Instituciones getIDInstitucion() {
        return iDInstitucion;
    }

    public void setIDInstitucion(Instituciones iDInstitucion) {
        this.iDInstitucion = iDInstitucion;
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
        hash += (iDMovCarAbo != null ? iDMovCarAbo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovCarAbo)) {
            return false;
        }
        MovCarAbo other = (MovCarAbo) object;
        if ((this.iDMovCarAbo == null && other.iDMovCarAbo != null) || (this.iDMovCarAbo != null && !this.iDMovCarAbo.equals(other.iDMovCarAbo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.MovCarAbo[ iDMovCarAbo=" + iDMovCarAbo + " ]";
    }
    
}
