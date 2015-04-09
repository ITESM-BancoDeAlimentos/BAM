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
@Table(name = "ValeCUDE", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValeCUDE.findAll", query = "SELECT v FROM ValeCUDE v"),
    @NamedQuery(name = "ValeCUDE.findByIDValeCUDE", query = "SELECT v FROM ValeCUDE v WHERE v.iDValeCUDE = :iDValeCUDE"),
    @NamedQuery(name = "ValeCUDE.findByFolio", query = "SELECT v FROM ValeCUDE v WHERE v.folio = :folio"),
    @NamedQuery(name = "ValeCUDE.findByFecha", query = "SELECT v FROM ValeCUDE v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "ValeCUDE.findByFechaRange", query = "SELECT v  FROM ValeCUDE v  WHERE v.fecha >= :fechaI  AND v.fecha <= :fechaF"),
    @NamedQuery(name = "ValeCUDE.countByFechaRange", query = "SELECT COUNT(v) FROM ValeCUDE v  WHERE v.fecha >= :fechaI  AND v.fecha <= :fechaF"),
    @NamedQuery(name = "ValeCUDE.findByElabora", query = "SELECT v FROM ValeCUDE v WHERE v.elabora = :elabora"),
    @NamedQuery(name = "ValeCUDE.findDonantes", query = "SELECT d.donante FROM Donantes d ORDER BY d.donante"),
    @NamedQuery(name = "ValeCUDE.findSucursales", query = "SELECT s.iDSucursal FROM Sucursales s WHERE s.iDDonante = :iDDonante ORDER BY s.iDSucursal"),
    @NamedQuery(name = "ValeCude.findByIDSucursalIDDonante", query = "SELECT s FROM Sucursales s WHERE s.iDSucursal = :iDSucursal AND s.iDDonante = :iDDonante")})
public class ValeCUDE implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDValeCUDE", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iDValeCUDE;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "Folio", nullable = false, length = 10)
    private String folio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 50)
    @Column(name = "Elabora", length = 50)
    private String elabora;
    @JoinColumn(name = "IDSucursales", referencedColumnName = "IDSucursales")
    @ManyToOne
    private Sucursales iDSucursales;
    @JoinColumn(name = "IDDonante", referencedColumnName = "IDDonante", nullable = false)
    @ManyToOne(optional = false)
    private Donantes iDDonante;

    public ValeCUDE() {
    }

    public ValeCUDE(Integer iDValeCUDE) {
        this.iDValeCUDE = iDValeCUDE;
    }

    public ValeCUDE(Integer iDValeCUDE, String folio, Date fecha) {
        this.iDValeCUDE = iDValeCUDE;
        this.folio = folio;
        this.fecha = fecha;
    }

    public Integer getIDValeCUDE() {
        return iDValeCUDE;
    }

    public void setIDValeCUDE(Integer iDValeCUDE) {
        this.iDValeCUDE = iDValeCUDE;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getElabora() {
        return elabora;
    }

    public void setElabora(String elabora) {
        this.elabora = elabora;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDValeCUDE != null ? iDValeCUDE.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValeCUDE)) {
            return false;
        }
        ValeCUDE other = (ValeCUDE) object;
        if ((this.iDValeCUDE == null && other.iDValeCUDE != null) || (this.iDValeCUDE != null && !this.iDValeCUDE.equals(other.iDValeCUDE))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.ValeCUDE[ iDValeCUDE=" + iDValeCUDE + " ]";
    }
    
}
