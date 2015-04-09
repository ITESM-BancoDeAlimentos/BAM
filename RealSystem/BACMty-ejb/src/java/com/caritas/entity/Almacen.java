/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "Almacen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Almacen.findAll", query = "SELECT a FROM Almacen a"),
    @NamedQuery(name = "Almacen.findByIDAlmacen", query = "SELECT a FROM Almacen a WHERE a.iDAlmacen = :iDAlmacen"),
    @NamedQuery(name = "Almacen.findByNombre", query = "SELECT a FROM Almacen a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Almacen.findByDireccion", query = "SELECT a FROM Almacen a WHERE a.direccion = :direccion"),
    @NamedQuery(name = "Almacen.findByTelefono", query = "SELECT a FROM Almacen a WHERE a.telefono = :telefono"),
    @NamedQuery(name = "Almacen.findByFax", query = "SELECT a FROM Almacen a WHERE a.fax = :fax"),
    @NamedQuery(name = "Almacen.findByRfc", query = "SELECT a FROM Almacen a WHERE a.rfc = :rfc"),
    @NamedQuery(name = "Almacen.findByEMail", query = "SELECT a FROM Almacen a WHERE a.eMail = :eMail"),
    @NamedQuery(name = "Almacen.findByFolioValesSal", query = "SELECT a FROM Almacen a WHERE a.folioValesSal = :folioValesSal"),
    @NamedQuery(name = "Almacen.findByFolioMovAlm", query = "SELECT a FROM Almacen a WHERE a.folioMovAlm = :folioMovAlm"),
    @NamedQuery(name = "Almacen.findByLocalizacion", query = "SELECT a FROM Almacen a WHERE a.localizacion = :localizacion"),
    @NamedQuery(name = "Almacen.findByCosteo", query = "SELECT a FROM Almacen a WHERE a.costeo = :costeo"),
    @NamedQuery(name = "Almacen.findByFolioCap", query = "SELECT a FROM Almacen a WHERE a.folioCap = :folioCap"),
    @NamedQuery(name = "Almacen.findByFolioCaja", query = "SELECT a FROM Almacen a WHERE a.folioCaja = :folioCaja"),
    @NamedQuery(name = "Almacen.findByFolioMovCla", query = "SELECT a FROM Almacen a WHERE a.folioMovCla = :folioMovCla"),
    @NamedQuery(name = "Almacen.findByFechaIni", query = "SELECT a FROM Almacen a WHERE a.fechaIni = :fechaIni"),
    @NamedQuery(name = "Almacen.findByFechaFin", query = "SELECT a FROM Almacen a WHERE a.fechaFin = :fechaFin"),
    @NamedQuery(name = "Almacen.findByFolioTxt", query = "SELECT a FROM Almacen a WHERE a.folioTxt = :folioTxt"),
    @NamedQuery(name = "Almacen.findByDesc40", query = "SELECT a FROM Almacen a WHERE a.desc40 = :desc40"),
    @NamedQuery(name = "Almacen.findByFechaTablaTmp", query = "SELECT a FROM Almacen a WHERE a.fechaTablaTmp = :fechaTablaTmp")})
public class Almacen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "IDAlmacen")
    private String iDAlmacen;
    @Size(max = 50)
    @Column(name = "Nombre")
    private String nombre;
    @Size(max = 50)
    @Column(name = "Direccion")
    private String direccion;
    @Size(max = 50)
    @Column(name = "Telefono")
    @Pattern(regexp = "^[\\d\\s()-]*$", message = "{telefono.Pattern}")
    private String telefono;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "Fax")
    @Pattern(regexp = "^[\\d\\s()-]*$", message = "{fax.Pattern}")
    private String fax;
    @Size(max = 15)
    @Column(name = "Rfc")
    private String rfc;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMail")
    private String eMail;
    @Column(name = "FolioValesSal")
    private Integer folioValesSal;
    @Column(name = "FolioMovAlm")
    private Integer folioMovAlm;
    @Size(max = 30)
    @Column(name = "Localizacion")
    private String localizacion;
    @Size(max = 10)
    @Column(name = "Costeo")
    private String costeo;
    @Column(name = "FolioCap")
    private Integer folioCap;
    @Column(name = "FolioCaja")
    private Integer folioCaja;
    @Column(name = "FolioMovCla")
    private Integer folioMovCla;
    @Column(name = "FechaIni")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIni;
    @Column(name = "FechaFin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Size(max = 20)
    @Column(name = "FolioTxt")
    private String folioTxt;
    @Size(max = 40)
    @Column(name = "Desc40")
    private String desc40;
    @Column(name = "FechaTablaTmp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTablaTmp;

    public Almacen() {
    }

    public Almacen(String iDAlmacen) {
        this.iDAlmacen = iDAlmacen;
    }

    public String getIDAlmacen() {
        return iDAlmacen;
    }

    public void setIDAlmacen(String iDAlmacen) {
        this.iDAlmacen = iDAlmacen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public Integer getFolioValesSal() {
        return folioValesSal;
    }

    public void setFolioValesSal(Integer folioValesSal) {
        this.folioValesSal = folioValesSal;
    }

    public Integer getFolioMovAlm() {
        return folioMovAlm;
    }

    public void setFolioMovAlm(Integer folioMovAlm) {
        this.folioMovAlm = folioMovAlm;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getCosteo() {
        return costeo;
    }

    public void setCosteo(String costeo) {
        this.costeo = costeo;
    }

    public Integer getFolioCap() {
        return folioCap;
    }

    public void setFolioCap(Integer folioCap) {
        this.folioCap = folioCap;
    }

    public Integer getFolioCaja() {
        return folioCaja;
    }

    public void setFolioCaja(Integer folioCaja) {
        this.folioCaja = folioCaja;
    }

    public Integer getFolioMovCla() {
        return folioMovCla;
    }

    public void setFolioMovCla(Integer folioMovCla) {
        this.folioMovCla = folioMovCla;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFolioTxt() {
        return folioTxt;
    }

    public void setFolioTxt(String folioTxt) {
        this.folioTxt = folioTxt;
    }

    public String getDesc40() {
        return desc40;
    }

    public void setDesc40(String desc40) {
        this.desc40 = desc40;
    }

    public Date getFechaTablaTmp() {
        return fechaTablaTmp;
    }

    public void setFechaTablaTmp(Date fechaTablaTmp) {
        this.fechaTablaTmp = fechaTablaTmp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDAlmacen != null ? iDAlmacen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Almacen)) {
            return false;
        }
        Almacen other = (Almacen) object;
        if ((this.iDAlmacen == null && other.iDAlmacen != null) || (this.iDAlmacen != null && !this.iDAlmacen.equals(other.iDAlmacen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.Almacen[ iDAlmacen=" + iDAlmacen + " ]";
    }
    
}
