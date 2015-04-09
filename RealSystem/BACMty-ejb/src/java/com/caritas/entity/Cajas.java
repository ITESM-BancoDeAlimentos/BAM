/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "Cajas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cajas.findAll", query = "SELECT c FROM Cajas c"),
    @NamedQuery(name = "Cajas.findByIDCaja", query = "SELECT c FROM Cajas c WHERE c.iDCaja = :iDCaja"),
    @NamedQuery(name = "Cajas.findByIDLote", query = "SELECT c FROM Cajas c WHERE c.iDLote = :iDLote"),
    @NamedQuery(name = "Cajas.findByPeso", query = "SELECT c FROM Cajas c WHERE c.peso = :peso"),
    @NamedQuery(name = "Cajas.findByFechaPesado", query = "SELECT c FROM Cajas c WHERE c.fechaPesado = :fechaPesado"),
    @NamedQuery(name = "Cajas.findByAsignado", query = "SELECT c FROM Cajas c WHERE c.asignado = :asignado"),
    @NamedQuery(name = "Cajas.findByStatus", query = "SELECT c FROM Cajas c WHERE c.status = :status"),
    @NamedQuery(name = "Cajas.findByIDFolio", query = "SELECT c FROM Cajas c WHERE c.iDFolio = :iDFolio"),
    @NamedQuery(name = "Cajas.findByFechaSal", query = "SELECT c FROM Cajas c WHERE c.fechaSal = :fechaSal"),
    @NamedQuery(name = "Cajas.findByIdArticuloCla", query = "SELECT c FROM Cajas c WHERE c.idArticuloCla = :idArticuloCla"),
    @NamedQuery(name = "Cajas.findByCosto", query = "SELECT c FROM Cajas c WHERE c.costo = :costo")})
public class Cajas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "IDCaja")
    private String iDCaja;
    @Size(max = 10)
    @Column(name = "IDLote")
    private String iDLote;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Peso")
    private Double peso;
    @Column(name = "FechaPesado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPesado;
    @Size(max = 50)
    @Column(name = "Asignado")
    private String asignado;
    @Size(max = 10)
    @Column(name = "Status")
    private String status;
    @Size(max = 10)
    @Column(name = "IDFolio")
    private String iDFolio;
    @Column(name = "FechaSal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSal;
    @Size(max = 13)
    @Column(name = "IdArticuloCla")
    private String idArticuloCla;
    @Column(name = "Costo")
    private Double costo;
    @OneToMany(mappedBy = "iDCaja")
    private Collection<MovtoCajas> movtoCajasCollection;
    @OneToMany(mappedBy = "iDCaja")
    private Collection<PartidasValesCajas> partidasValesCajasCollection;
    @JoinColumn(name = "IDVariedad", referencedColumnName = "IDVariedad")
    @ManyToOne
    private Variedad iDVariedad;
    @JoinColumn(name = "IDPrograma", referencedColumnName = "IDPrograma")
    @ManyToOne
    private Programas iDPrograma;
    @JoinColumn(name = "IdArea", referencedColumnName = "IDArea")
    @ManyToOne
    private Areas idArea;
    @OneToMany(mappedBy = "iDCaja")
    private Collection<PartidasVales> partidasValesCollection;

    public Cajas() {
    }

    public Cajas(String iDCaja) {
        this.iDCaja = iDCaja;
    }

    public String getIDCaja() {
        return iDCaja;
    }

    public void setIDCaja(String iDCaja) {
        this.iDCaja = iDCaja;
    }

    public String getIDLote() {
        return iDLote;
    }

    public void setIDLote(String iDLote) {
        this.iDLote = iDLote;
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

    public String getAsignado() {
        return asignado;
    }

    public void setAsignado(String asignado) {
        this.asignado = asignado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIDFolio() {
        return iDFolio;
    }

    public void setIDFolio(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public Date getFechaSal() {
        return fechaSal;
    }

    public void setFechaSal(Date fechaSal) {
        this.fechaSal = fechaSal;
    }

    public String getIdArticuloCla() {
        return idArticuloCla;
    }

    public void setIdArticuloCla(String idArticuloCla) {
        this.idArticuloCla = idArticuloCla;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    @XmlTransient
    public Collection<MovtoCajas> getMovtoCajasCollection() {
        return movtoCajasCollection;
    }

    public void setMovtoCajasCollection(Collection<MovtoCajas> movtoCajasCollection) {
        this.movtoCajasCollection = movtoCajasCollection;
    }

    @XmlTransient
    public Collection<PartidasValesCajas> getPartidasValesCajasCollection() {
        return partidasValesCajasCollection;
    }

    public void setPartidasValesCajasCollection(Collection<PartidasValesCajas> partidasValesCajasCollection) {
        this.partidasValesCajasCollection = partidasValesCajasCollection;
    }

    public Variedad getIDVariedad() {
        return iDVariedad;
    }

    public void setIDVariedad(Variedad iDVariedad) {
        this.iDVariedad = iDVariedad;
    }

    public Programas getIDPrograma() {
        return iDPrograma;
    }

    public void setIDPrograma(Programas iDPrograma) {
        this.iDPrograma = iDPrograma;
    }

    public Areas getIdArea() {
        return idArea;
    }

    public void setIdArea(Areas idArea) {
        this.idArea = idArea;
    }

    @XmlTransient
    public Collection<PartidasVales> getPartidasValesCollection() {
        return partidasValesCollection;
    }

    public void setPartidasValesCollection(Collection<PartidasVales> partidasValesCollection) {
        this.partidasValesCollection = partidasValesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDCaja != null ? iDCaja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cajas)) {
            return false;
        }
        Cajas other = (Cajas) object;
        if ((this.iDCaja == null && other.iDCaja != null) || (this.iDCaja != null && !this.iDCaja.equals(other.iDCaja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.Cajas[ iDCaja=" + iDCaja + " ]";
    }
    
}
