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
@Table(name = "Proveedores", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedores.findAll", query = "SELECT p FROM Proveedores p"),
    @NamedQuery(name = "Proveedores.findByIDProveedor", query = "SELECT p FROM Proveedores p WHERE p.iDProveedor = :iDProveedor"),
    @NamedQuery(name = "Proveedores.findByProveedor", query = "SELECT p FROM Proveedores p WHERE p.proveedor = :proveedor"),
    @NamedQuery(name = "Proveedores.findByDireccion", query = "SELECT p FROM Proveedores p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Proveedores.findByColonia", query = "SELECT p FROM Proveedores p WHERE p.colonia = :colonia"),
    @NamedQuery(name = "Proveedores.findByCiudad", query = "SELECT p FROM Proveedores p WHERE p.ciudad = :ciudad"),
    @NamedQuery(name = "Proveedores.findByEstado", query = "SELECT p FROM Proveedores p WHERE p.estado = :estado"),
    @NamedQuery(name = "Proveedores.findByTelefono", query = "SELECT p FROM Proveedores p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Proveedores.findByFax", query = "SELECT p FROM Proveedores p WHERE p.fax = :fax"),
    @NamedQuery(name = "Proveedores.findByContacto", query = "SELECT p FROM Proveedores p WHERE p.contacto = :contacto"),
    @NamedQuery(name = "Proveedores.findByRfc", query = "SELECT p FROM Proveedores p WHERE p.rfc = :rfc"),
    @NamedQuery(name = "Proveedores.findByFechaAlta", query = "SELECT p FROM Proveedores p WHERE p.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "Proveedores.findByMail", query = "SELECT p FROM Proveedores p WHERE p.mail = :mail")})
public class Proveedores implements Serializable {
    @OneToMany(mappedBy = "iDProveedor")
    private Collection<Movimientos> movimientosCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "IDProveedor", nullable = false, length = 8)
    private String iDProveedor;
    @Size(max = 50)
    @Column(name = "Proveedor", length = 50)
    private String proveedor;
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
    @Column(name = "FechaAlta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Size(max = 50)
    @Column(name = "Mail", length = 50)
    private String mail;
    @OneToMany(mappedBy = "iDProveedor")
    private Collection<Recibo> reciboCollection;

    public Proveedores() {
    }

    public Proveedores(String iDProveedor) {
        this.iDProveedor = iDProveedor;
    }

    public String getIDProveedor() {
        return iDProveedor;
    }

    public void setIDProveedor(String iDProveedor) {
        this.iDProveedor = iDProveedor;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
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

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
        hash += (iDProveedor != null ? iDProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedores)) {
            return false;
        }
        Proveedores other = (Proveedores) object;
        if ((this.iDProveedor == null && other.iDProveedor != null) || (this.iDProveedor != null && !this.iDProveedor.equals(other.iDProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return iDProveedor + " : " + proveedor;
    }

    @XmlTransient
    public Collection<Movimientos> getMovimientosCollection() {
        return movimientosCollection;
    }

    public void setMovimientosCollection(Collection<Movimientos> movimientosCollection) {
        this.movimientosCollection = movimientosCollection;
    }
    
}
