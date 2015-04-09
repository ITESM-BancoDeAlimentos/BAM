/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "Sucursales", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sucursales.findAll", query = "SELECT s FROM Sucursales s ORDER BY s.iDSucursal"),
    @NamedQuery(name = "Sucursales.findByIDDonante", query = "SELECT s FROM Sucursales s WHERE s.iDDonante = :iDDonante"),
    @NamedQuery(name = "Sucursales.findByIDSucursal", query = "SELECT s FROM Sucursales s WHERE s.iDSucursal = :iDSucursal"),
    @NamedQuery(name = "Sucursales.findByDireccion", query = "SELECT s FROM Sucursales s WHERE s.direccion = :direccion"),
    @NamedQuery(name = "Sucursales.findByColonia", query = "SELECT s FROM Sucursales s WHERE s.colonia = :colonia"),
    @NamedQuery(name = "Sucursales.findByCiudad", query = "SELECT s FROM Sucursales s WHERE s.ciudad = :ciudad"),
    @NamedQuery(name = "Sucursales.findByEstado", query = "SELECT s FROM Sucursales s WHERE s.estado = :estado"),
    @NamedQuery(name = "Sucursales.findByTelefono", query = "SELECT s FROM Sucursales s WHERE s.telefono = :telefono"),
    @NamedQuery(name = "Sucursales.findByFax", query = "SELECT s FROM Sucursales s WHERE s.fax = :fax"),
    @NamedQuery(name = "Sucursales.findByContacto", query = "SELECT s FROM Sucursales s WHERE s.contacto = :contacto"),
    @NamedQuery(name = "Sucursales.findByMail", query = "SELECT s FROM Sucursales s WHERE s.mail = :mail"),
    @NamedQuery(name = "Sucursales.findByIDSucursales", query = "SELECT s FROM Sucursales s WHERE s.iDSucursales = :iDSucursales")})
public class Sucursales implements Serializable {
    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy = "iDSucursales")
    private Collection<Movimientos> movimientosCollection;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "IDSucursal", nullable = false, length = 15)
    private String iDSucursal;
    @Size(max = 50)
    @Column(name = "Direccion", length = 50)
    private String direccion;
    @Size(max = 30)
    @Column(name = "Colonia", length = 30)
    private String colonia;
    @Size(max = 20)
    @Column(name = "Ciudad", length = 20)
    private String ciudad;
    @Size(max = 10)
    @Column(name = "Estado", length = 10)
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
    @Size(max = 30)
    @Column(name = "mail", length = 30)
    private String mail;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDSucursales", nullable = false)
    private Integer iDSucursales;
    @JoinColumn(name = "IDDonante", referencedColumnName = "IDDonante", nullable = false)
    @ManyToOne(optional = false)
    private Donantes iDDonante;
    @OneToMany(mappedBy = "iDSucursales")
    private Collection<ValeCUDE> valeCUDECollection;
    @OneToMany(mappedBy = "iDSucursales")
    private Collection<Recibo> reciboCollection;
    @OneToMany(mappedBy = "iDSucursales")
    private Collection<DistribucionAMBA> distribucionAMBACollection;

    public Sucursales() {
    }

    public Sucursales(Integer iDSucursales) {
        this.iDSucursales = iDSucursales;
    }

    public Sucursales(Integer iDSucursales, String iDSucursal) {
        this.iDSucursales = iDSucursales;
        this.iDSucursal = iDSucursal;
    }

    public String getIDSucursal() {
        return iDSucursal;
    }

    public void setIDSucursal(String iDSucursal) {
        this.iDSucursal = iDSucursal;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getIDSucursales() {
        return iDSucursales;
    }

    public void setIDSucursales(Integer iDSucursales) {
        this.iDSucursales = iDSucursales;
    }

    public Donantes getIDDonante() {
        return iDDonante;
    }

    public void setIDDonante(Donantes iDDonante) {
        this.iDDonante = iDDonante;
    }

    @XmlTransient
    public Collection<ValeCUDE> getValeCUDECollection() {
        return valeCUDECollection;
    }

    public void setValeCUDECollection(Collection<ValeCUDE> valeCUDECollection) {
        this.valeCUDECollection = valeCUDECollection;
    }

    @XmlTransient
    public Collection<Recibo> getReciboCollection() {
        return reciboCollection;
    }

    public void setReciboCollection(Collection<Recibo> reciboCollection) {
        this.reciboCollection = reciboCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDSucursales != null ? iDSucursales.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursales)) {
            return false;
        }
        Sucursales other = (Sucursales) object;
        if ((this.iDSucursales == null && other.iDSucursales != null) || (this.iDSucursales != null && !this.iDSucursales.equals(other.iDSucursales))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return iDDonante + ":" + iDSucursal;
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
