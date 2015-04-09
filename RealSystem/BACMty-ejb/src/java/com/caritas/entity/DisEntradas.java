/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "DisEntradas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DisEntradas.findAll", query = "SELECT d FROM DisEntradas d"),
    @NamedQuery(name = "DisEntradas.findByFolioMov", query = "SELECT d FROM DisEntradas d WHERE d.folioMov = :folioMov"),
    @NamedQuery(name = "DisEntradas.findByCantidad", query = "SELECT d FROM DisEntradas d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DisEntradas.findByIDDisEntradas", query = "SELECT d FROM DisEntradas d WHERE d.iDDisEntradas = :iDDisEntradas")})
public class DisEntradas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 10)
    @Column(name = "FolioMov")
    private String folioMov;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Cantidad")
    private Double cantidad;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDDisEntradas")
    private Integer iDDisEntradas;
    @JoinColumn(name = "IdPrograma", referencedColumnName = "IDPrograma")
    @ManyToOne
    private Programas idPrograma;
    @JoinColumn(name = "IdArticulo", referencedColumnName = "IDArticulo")
    @ManyToOne
    private Articulos idArticulo;
    @JoinColumn(name = "IdArea", referencedColumnName = "IDArea")
    @ManyToOne
    private Areas idArea;

    public DisEntradas() {
    }

    public DisEntradas(Integer iDDisEntradas) {
        this.iDDisEntradas = iDDisEntradas;
    }

    public String getFolioMov() {
        return folioMov;
    }

    public void setFolioMov(String folioMov) {
        this.folioMov = folioMov;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIDDisEntradas() {
        return iDDisEntradas;
    }

    public void setIDDisEntradas(Integer iDDisEntradas) {
        this.iDDisEntradas = iDDisEntradas;
    }

    public Programas getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Programas idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Articulos getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Articulos idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Areas getIdArea() {
        return idArea;
    }

    public void setIdArea(Areas idArea) {
        this.idArea = idArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDDisEntradas != null ? iDDisEntradas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisEntradas)) {
            return false;
        }
        DisEntradas other = (DisEntradas) object;
        if ((this.iDDisEntradas == null && other.iDDisEntradas != null) || (this.iDDisEntradas != null && !this.iDDisEntradas.equals(other.iDDisEntradas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.DisEntradas[ iDDisEntradas=" + iDDisEntradas + " ]";
    }
    
}
