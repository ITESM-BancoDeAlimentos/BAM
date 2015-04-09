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
@Table(name = "Tmp_MovtoArt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TmpMovtoArt.findAll", query = "SELECT t FROM TmpMovtoArt t"),
    @NamedQuery(name = "TmpMovtoArt.findByTipoMov", query = "SELECT t FROM TmpMovtoArt t WHERE t.tipoMov = :tipoMov"),
    @NamedQuery(name = "TmpMovtoArt.findByFechaMov", query = "SELECT t FROM TmpMovtoArt t WHERE t.fechaMov = :fechaMov"),
    @NamedQuery(name = "TmpMovtoArt.findByIDFolio", query = "SELECT t FROM TmpMovtoArt t WHERE t.iDFolio = :iDFolio"),
    @NamedQuery(name = "TmpMovtoArt.findByCantidad", query = "SELECT t FROM TmpMovtoArt t WHERE t.cantidad = :cantidad"),
    @NamedQuery(name = "TmpMovtoArt.findByFechaCad", query = "SELECT t FROM TmpMovtoArt t WHERE t.fechaCad = :fechaCad"),
    @NamedQuery(name = "TmpMovtoArt.findByMov", query = "SELECT t FROM TmpMovtoArt t WHERE t.mov = :mov"),
    @NamedQuery(name = "TmpMovtoArt.findByExistencia", query = "SELECT t FROM TmpMovtoArt t WHERE t.existencia = :existencia"),
    @NamedQuery(name = "TmpMovtoArt.findByIDTmpMovtoArt", query = "SELECT t FROM TmpMovtoArt t WHERE t.iDTmpMovtoArt = :iDTmpMovtoArt")})
public class TmpMovtoArt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 10)
    @Column(name = "TipoMov")
    private String tipoMov;
    @Column(name = "FechaMov")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMov;
    @Size(max = 50)
    @Column(name = "IDFolio")
    private String iDFolio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Cantidad")
    private Double cantidad;
    @Column(name = "FechaCad")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCad;
    @Column(name = "Mov")
    private Integer mov;
    @Column(name = "Existencia")
    private Double existencia;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTmp_MovtoArt")
    private Integer iDTmpMovtoArt;
    @JoinColumn(name = "IDUsuario", referencedColumnName = "IDUsuario")
    @ManyToOne
    private Usuarios iDUsuario;
    @JoinColumn(name = "IDPrograma", referencedColumnName = "IDPrograma")
    @ManyToOne
    private Programas iDPrograma;
    @JoinColumn(name = "IDArticulo", referencedColumnName = "IDArticulo")
    @ManyToOne
    private Articulos iDArticulo;
    @JoinColumn(name = "IDArea", referencedColumnName = "IDArea")
    @ManyToOne
    private Areas iDArea;

    public TmpMovtoArt() {
    }

    public TmpMovtoArt(Integer iDTmpMovtoArt) {
        this.iDTmpMovtoArt = iDTmpMovtoArt;
    }

    public String getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

    public String getIDFolio() {
        return iDFolio;
    }

    public void setIDFolio(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaCad() {
        return fechaCad;
    }

    public void setFechaCad(Date fechaCad) {
        this.fechaCad = fechaCad;
    }

    public Integer getMov() {
        return mov;
    }

    public void setMov(Integer mov) {
        this.mov = mov;
    }

    public Double getExistencia() {
        return existencia;
    }

    public void setExistencia(Double existencia) {
        this.existencia = existencia;
    }

    public Integer getIDTmpMovtoArt() {
        return iDTmpMovtoArt;
    }

    public void setIDTmpMovtoArt(Integer iDTmpMovtoArt) {
        this.iDTmpMovtoArt = iDTmpMovtoArt;
    }

    public Usuarios getIDUsuario() {
        return iDUsuario;
    }

    public void setIDUsuario(Usuarios iDUsuario) {
        this.iDUsuario = iDUsuario;
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
        hash += (iDTmpMovtoArt != null ? iDTmpMovtoArt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TmpMovtoArt)) {
            return false;
        }
        TmpMovtoArt other = (TmpMovtoArt) object;
        if ((this.iDTmpMovtoArt == null && other.iDTmpMovtoArt != null) || (this.iDTmpMovtoArt != null && !this.iDTmpMovtoArt.equals(other.iDTmpMovtoArt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.TmpMovtoArt[ iDTmpMovtoArt=" + iDTmpMovtoArt + " ]";
    }
    
}
