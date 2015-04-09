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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "Donantes", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Donantes.findAll", query = "SELECT d FROM Donantes d"),
    @NamedQuery(name = "Donantes.findByIDDonante", query = "SELECT d FROM Donantes d WHERE d.iDDonante = :iDDonante"),
    @NamedQuery(name = "Donantes.findByDonante", query = "SELECT d FROM Donantes d WHERE d.donante = :donante"),
    @NamedQuery(name = "Donantes.findByNombre", query = "SELECT d FROM Donantes d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "Donantes.findByDireccion", query = "SELECT d FROM Donantes d WHERE d.direccion = :direccion"),
    @NamedQuery(name = "Donantes.findByColonia", query = "SELECT d FROM Donantes d WHERE d.colonia = :colonia"),
    @NamedQuery(name = "Donantes.findByCiudad", query = "SELECT d FROM Donantes d WHERE d.ciudad = :ciudad"),
    @NamedQuery(name = "Donantes.findByEstado", query = "SELECT d FROM Donantes d WHERE d.estado = :estado"),
    @NamedQuery(name = "Donantes.findByTelefono", query = "SELECT d FROM Donantes d WHERE d.telefono = :telefono"),
    @NamedQuery(name = "Donantes.findByFax", query = "SELECT d FROM Donantes d WHERE d.fax = :fax"),
    @NamedQuery(name = "Donantes.findByContacto", query = "SELECT d FROM Donantes d WHERE d.contacto = :contacto"),
    @NamedQuery(name = "Donantes.findByRfc", query = "SELECT d FROM Donantes d WHERE d.rfc = :rfc"),
    @NamedQuery(name = "Donantes.findBySucursales", query = "SELECT d FROM Donantes d WHERE d.sucursales = :sucursales"),
    @NamedQuery(name = "Donantes.findByFechaAlta", query = "SELECT d FROM Donantes d WHERE d.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "Donantes.findByFrecuencia", query = "SELECT d FROM Donantes d WHERE d.frecuencia = :frecuencia"),
    @NamedQuery(name = "Donantes.findByMail", query = "SELECT d FROM Donantes d WHERE d.mail = :mail"),
    @NamedQuery(name = "Donantes.findByTipoDonante", query = "SELECT d FROM Donantes d WHERE d.tipoDonante = :tipoDonante"),
    @NamedQuery(name = "Donantes.findLike", query = "SELECT d FROM Donantes d WHERE d.iDDonante LIKE :query OR d.donante LIKE :query OR d.nombre LIKE :query"),
    @NamedQuery(name = "Donantes.findByUsuarioAlta", query = "SELECT d FROM Donantes d WHERE d.usuarioAlta = :usuarioAlta")})
public class Donantes implements Serializable {

    private static final long serialVersionUID = 1L;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDDonante")
    private Collection<Movimientos> movimientosCollection;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "IDDonante", nullable = false, length = 8)
    private String iDDonante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Donante", nullable = false, length = 50)
    private String donante;
    @Size(max = 15)
    @Column(name = "Nombre", length = 15)
    private String nombre;
    @Size(max = 50)
    @Column(name = "Direccion", length = 50)
    private String direccion;
    @Size(max = 50)
    @Column(name = "Colonia", length = 50)
    private String colonia;
    @Size(max = 30)
    @Column(name = "Ciudad", length = 30)
    private String ciudad;
    @Size(max = 15)
    @Column(name = "Estado", length = 15)
    private String estado;
    @Size(max = 15)
    @Column(name = "Telefono", length = 15)
    @Pattern(regexp = "^[\\d\\s()-]*$", message = "{telefono.Pattern}")
    private String telefono;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 15)
    @Column(name = "Fax", length = 15)
    @Pattern(regexp = "^[\\d\\s()-]*$", message = "{fax.Pattern}")
    private String fax;
    @Size(max = 50)
    @Column(name = "Contacto", length = 50)
    private String contacto;
    @Size(max = 15)
    @Column(name = "Rfc", length = 15)
    private String rfc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Sucursales", nullable = false)
    private boolean sucursales;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FechaAlta", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Size(max = 1)
    @Column(name = "Frecuencia", length = 1)
    private String frecuencia;
    @Size(max = 50)
    @Column(name = "Mail", length = 50)
    private String mail;
    @Size(max = 8)
    @Column(name = "TipoDonante", length = 8)
    private String tipoDonante;
    @Size(max = 30)
    @Column(name = "UsuarioAlta", length = 30)
    private String usuarioAlta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDDonante")
    private Collection<Sucursales> sucursalesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDDonante")
    private Collection<Recibo> reciboCollection;
    @OneToMany(mappedBy = "iDDonante")
    private Collection<Articulos> articulosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDDonante")
    private Collection<ValeCUDE> valeCUDECollection;
    @JoinColumn(name = "IDTipoDon", referencedColumnName = "IDTipoDon", nullable = false)
    @ManyToOne(optional = false)
    private TipoDon iDTipoDon;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDDonante")
    private Collection<DistribucionAMBA> distribucionAMBACollection;

    public Donantes() {
    }

    public Donantes(String iDDonante) {
        this.iDDonante = iDDonante;
    }

    public Donantes(String iDDonante, String donante, boolean sucursales, Date fechaAlta) {
        this.iDDonante = iDDonante;
        this.donante = donante;
        this.sucursales = sucursales;
        this.fechaAlta = fechaAlta;
    }

    public String getIDDonante() {
        return iDDonante;
    }

    public void setIDDonante(String iDDonante) {
        this.iDDonante = iDDonante;
    }

    public String getDonante() {
        return donante;
    }

    public void setDonante(String donante) {
        this.donante = donante;
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

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public boolean getSucursales() {
        return sucursales;
    }

    public void setSucursales(boolean sucursales) {
        this.sucursales = sucursales;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTipoDonante() {
        return tipoDonante;
    }

    public void setTipoDonante(String tipoDonante) {
        this.tipoDonante = tipoDonante;
    }

    public String getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(String usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    @XmlTransient
    public Collection<Sucursales> getSucursalesCollection() {
        return sucursalesCollection;
    }

    public void setSucursalesCollection(Collection<Sucursales> sucursalesCollection) {
        this.sucursalesCollection = sucursalesCollection;
    }

    @XmlTransient
    public Collection<Recibo> getReciboCollection() {
        return reciboCollection;
    }

    @XmlTransient
    public Collection<ValeCUDE> getValeCUDECollection() {
        return valeCUDECollection;
    }

    public void setValeCUDECollection(Collection<ValeCUDE> valeCUDECollection) {
        this.valeCUDECollection = valeCUDECollection;
    }

    public void setReciboCollection(Collection<Recibo> reciboCollection) {
        this.reciboCollection = reciboCollection;
    }

    @XmlTransient
    public Collection<Articulos> getArticulosCollection() {
        return articulosCollection;
    }

    public void setArticulosCollection(Collection<Articulos> articulosCollection) {
        this.articulosCollection = articulosCollection;
    }

    public TipoDon getIDTipoDon() {
        return iDTipoDon;
    }

    public void setIDTipoDon(TipoDon iDTipoDon) {
        this.iDTipoDon = iDTipoDon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDDonante != null ? iDDonante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Donantes)) {
            return false;
        }
        Donantes other = (Donantes) object;
        if ((this.iDDonante == null && other.iDDonante != null) || (this.iDDonante != null && !this.iDDonante.equals(other.iDDonante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return iDDonante + " : " + donante + " : " + nombre;
    }

    @XmlTransient
    public Collection<Movimientos> getMovimientosCollection() {
        return movimientosCollection;
    }

    public void setMovimientosCollection(Collection<Movimientos> movimientosCollection) {
        this.movimientosCollection = movimientosCollection;
    }

    @XmlTransient
    public Collection<DistribucionAMBA> getDistribucionAMBACollection() {
        return distribucionAMBACollection;
    }

    public void setDistribucionAMBACollection(Collection<DistribucionAMBA> distribucionAMBACollection) {
        this.distribucionAMBACollection = distribucionAMBACollection;
    }
}
