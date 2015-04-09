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
@Table(name = "DonativoEsp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DonativoEsp.findAll", query = "SELECT d FROM DonativoEsp d"),
    @NamedQuery(name = "DonativoEsp.findByIDFolio", query = "SELECT d FROM DonativoEsp d WHERE d.iDFolio = :iDFolio"),
    @NamedQuery(name = "DonativoEsp.findByFecha", query = "SELECT d FROM DonativoEsp d WHERE d.fecha = :fecha"),
    @NamedQuery(name = "DonativoEsp.findByIDSucursal", query = "SELECT d FROM DonativoEsp d WHERE d.iDSucursal = :iDSucursal"),
    @NamedQuery(name = "DonativoEsp.findByContacto", query = "SELECT d FROM DonativoEsp d WHERE d.contacto = :contacto"),
    @NamedQuery(name = "DonativoEsp.findByHorarioAcopio", query = "SELECT d FROM DonativoEsp d WHERE d.horarioAcopio = :horarioAcopio"),
    @NamedQuery(name = "DonativoEsp.findByHorarioDescanso", query = "SELECT d FROM DonativoEsp d WHERE d.horarioDescanso = :horarioDescanso"),
    @NamedQuery(name = "DonativoEsp.findByObservaciones", query = "SELECT d FROM DonativoEsp d WHERE d.observaciones = :observaciones")})
public class DonativoEsp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "IDFolio")
    private String iDFolio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 15)
    @Column(name = "IDSucursal")
    private String iDSucursal;
    @Size(max = 50)
    @Column(name = "Contacto")
    private String contacto;
    @Size(max = 20)
    @Column(name = "HorarioAcopio")
    private String horarioAcopio;
    @Size(max = 20)
    @Column(name = "HorarioDescanso")
    private String horarioDescanso;
    @Size(max = 255)
    @Column(name = "Observaciones")
    private String observaciones;
    @JoinColumn(name = "IDDonante", referencedColumnName = "IDDonante")
    @ManyToOne(optional = false)
    private Donantes iDDonante;

    public DonativoEsp() {
    }

    public DonativoEsp(String iDFolio) {
        this.iDFolio = iDFolio;
    }

    public DonativoEsp(String iDFolio, Date fecha) {
        this.iDFolio = iDFolio;
        this.fecha = fecha;
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

    public String getIDSucursal() {
        return iDSucursal;
    }

    public void setIDSucursal(String iDSucursal) {
        this.iDSucursal = iDSucursal;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getHorarioAcopio() {
        return horarioAcopio;
    }

    public void setHorarioAcopio(String horarioAcopio) {
        this.horarioAcopio = horarioAcopio;
    }

    public String getHorarioDescanso() {
        return horarioDescanso;
    }

    public void setHorarioDescanso(String horarioDescanso) {
        this.horarioDescanso = horarioDescanso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
        if (!(object instanceof DonativoEsp)) {
            return false;
        }
        DonativoEsp other = (DonativoEsp) object;
        if ((this.iDFolio == null && other.iDFolio != null) || (this.iDFolio != null && !this.iDFolio.equals(other.iDFolio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.DonativoEsp[ iDFolio=" + iDFolio + " ]";
    }
    
}
