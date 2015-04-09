/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "Instituciones", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Instituciones.findAll", query = "SELECT i FROM Instituciones i"),
    @NamedQuery(name = "Instituciones.findByIDInstitucion", query = "SELECT i FROM Instituciones i WHERE i.iDInstitucion = :iDInstitucion"),
    @NamedQuery(name = "Instituciones.findByInstitucion", query = "SELECT i FROM Instituciones i WHERE i.institucion = :institucion"),
    @NamedQuery(name = "Instituciones.findByDireccion", query = "SELECT i FROM Instituciones i WHERE i.direccion = :direccion"),
    @NamedQuery(name = "Instituciones.findByColonia", query = "SELECT i FROM Instituciones i WHERE i.colonia = :colonia"),
    @NamedQuery(name = "Instituciones.findByCiudad", query = "SELECT i FROM Instituciones i WHERE i.ciudad = :ciudad"),
    @NamedQuery(name = "Instituciones.findByEstado", query = "SELECT i FROM Instituciones i WHERE i.estado = :estado"),
    @NamedQuery(name = "Instituciones.findByTelefono", query = "SELECT i FROM Instituciones i WHERE i.telefono = :telefono"),
    @NamedQuery(name = "Instituciones.findByFax", query = "SELECT i FROM Instituciones i WHERE i.fax = :fax"),
    @NamedQuery(name = "Instituciones.findByContacto", query = "SELECT i FROM Instituciones i WHERE i.contacto = :contacto"),
    @NamedQuery(name = "Instituciones.findByFechaAlta", query = "SELECT i FROM Instituciones i WHERE i.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "Instituciones.findByTipoInstitucion", query = "SELECT i FROM Instituciones i WHERE i.tipoInstitucion = :tipoInstitucion"),
    @NamedQuery(name = "Instituciones.findByMail", query = "SELECT i FROM Instituciones i WHERE i.mail = :mail"),
    @NamedQuery(name = "Instituciones.findBySaldo", query = "SELECT i FROM Instituciones i WHERE i.saldo = :saldo"),
    @NamedQuery(name = "Instituciones.findByHorario", query = "SELECT i FROM Instituciones i WHERE i.horario = :horario"),
    @NamedQuery(name = "Instituciones.findByObservacion", query = "SELECT i FROM Instituciones i WHERE i.observacion = :observacion"),
    @NamedQuery(name = "Instituciones.findByFamilia", query = "SELECT i FROM Instituciones i WHERE i.familia = :familia"),
    @NamedQuery(name = "Instituciones.findByDirector", query = "SELECT i FROM Instituciones i WHERE i.director = :director"),
    @NamedQuery(name = "Instituciones.findByDireccionFis", query = "SELECT i FROM Instituciones i WHERE i.direccionFis = :direccionFis"),
    @NamedQuery(name = "Instituciones.findByColoniaFis", query = "SELECT i FROM Instituciones i WHERE i.coloniaFis = :coloniaFis"),
    @NamedQuery(name = "Instituciones.findByCiudadFis", query = "SELECT i FROM Instituciones i WHERE i.ciudadFis = :ciudadFis"),
    @NamedQuery(name = "Instituciones.findByEstadoFis", query = "SELECT i FROM Instituciones i WHERE i.estadoFis = :estadoFis"),
    @NamedQuery(name = "Instituciones.findByRfc", query = "SELECT i FROM Instituciones i WHERE i.rfc = :rfc"),
    @NamedQuery(name = "Instituciones.findByPersonas", query = "SELECT i FROM Instituciones i WHERE i.personas = :personas"),
    @NamedQuery(name = "Instituciones.findByRazonFis", query = "SELECT i FROM Instituciones i WHERE i.razonFis = :razonFis"),
    @NamedQuery(name = "Instituciones.findByActivo", query = "SELECT i FROM Instituciones i WHERE i.activo = :activo"),
    @NamedQuery(name = "Instituciones.findByDescuento", query = "SELECT i FROM Instituciones i WHERE i.descuento = :descuento"),
    @NamedQuery(name = "Instituciones.findBySaldoPermitido", query = "SELECT i FROM Instituciones i WHERE i.saldoPermitido = :saldoPermitido"),
    @NamedQuery(name = "Instituciones.findLike", query = "SELECT d FROM Instituciones d WHERE d.iDInstitucion LIKE :query OR d.institucion LIKE :query"),
    @NamedQuery(name = "Instituciones.findByDonativoPermitido", query = "SELECT i FROM Instituciones i WHERE i.donativoPermitido = :donativoPermitido")})
public class Instituciones implements Serializable {

    @OneToMany(mappedBy = "iDInstitucion")
    private Collection<Movimientos> movimientosCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "IDInstitucion", nullable = false, length = 10)
    private String iDInstitucion;
    @Size(max = 50)
    @Column(name = "Institucion", length = 50)
    private String institucion;
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
    @Column(name = "FechaAlta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Size(max = 10)
    @Column(name = "TipoInstitucion", length = 10)
    private String tipoInstitucion;
    @Size(max = 40)
    @Column(name = "Mail", length = 40)
    private String mail;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Saldo", precision = 18, scale = 2)
    private BigDecimal saldo;
    @Size(max = 20)
    @Column(name = "Horario", length = 20)
    private String horario;
    @Size(max = 150)
    @Column(name = "Observacion", length = 150)
    private String observacion;
    @Size(max = 10)
    @Column(name = "Familia", length = 10)
    private String familia;
    @Size(max = 50)
    @Column(name = "Director", length = 50)
    private String director;
    @Size(max = 50)
    @Column(name = "DireccionFis", length = 50)
    private String direccionFis;
    @Size(max = 50)
    @Column(name = "ColoniaFis", length = 50)
    private String coloniaFis;
    @Size(max = 50)
    @Column(name = "CiudadFis", length = 50)
    private String ciudadFis;
    @Size(max = 50)
    @Column(name = "EstadoFis", length = 50)
    private String estadoFis;
    @Size(max = 50)
    @Column(name = "Rfc", length = 50)
    private String rfc;
    @Column(name = "Personas")
    private Integer personas;
    @Size(max = 50)
    @Column(name = "RazonFis", length = 50)
    private String razonFis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Activo", nullable = false)
    private boolean activo;
    @Column(name = "Descuento", precision = 53)
    private Double descuento;
    @Column(name = "SaldoPermitido", precision = 53)
    private Double saldoPermitido;
    @Column(name = "DonativoPermitido", precision = 53)
    private Double donativoPermitido;
    @OneToMany(mappedBy = "iDInstitucion")
    private Collection<Recibo> reciboCollection;
    @JoinColumn(name = "IDSubArea", referencedColumnName = "IDSubArea")
    @ManyToOne
    private SubAreas iDSubArea;
    @JoinColumn(name = "IDGrupoInst", referencedColumnName = "IDGrupoInst")
    @ManyToOne
    private GrupoInst iDGrupoInst;

    public Instituciones() {
    }

    public Instituciones(String iDInstitucion) {
        this.iDInstitucion = iDInstitucion;
    }

    public Instituciones(String iDInstitucion, boolean activo) {
        this.iDInstitucion = iDInstitucion;
        this.activo = activo;
    }

    public String getIDInstitucion() {
        return iDInstitucion;
    }

    public void setIDInstitucion(String iDInstitucion) {
        this.iDInstitucion = iDInstitucion;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
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

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getTipoInstitucion() {
        return tipoInstitucion;
    }

    public void setTipoInstitucion(String tipoInstitucion) {
        this.tipoInstitucion = tipoInstitucion;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDireccionFis() {
        return direccionFis;
    }

    public void setDireccionFis(String direccionFis) {
        this.direccionFis = direccionFis;
    }

    public String getColoniaFis() {
        return coloniaFis;
    }

    public void setColoniaFis(String coloniaFis) {
        this.coloniaFis = coloniaFis;
    }

    public String getCiudadFis() {
        return ciudadFis;
    }

    public void setCiudadFis(String ciudadFis) {
        this.ciudadFis = ciudadFis;
    }

    public String getEstadoFis() {
        return estadoFis;
    }

    public void setEstadoFis(String estadoFis) {
        this.estadoFis = estadoFis;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Integer getPersonas() {
        return personas;
    }

    public void setPersonas(Integer personas) {
        this.personas = personas;
    }

    public String getRazonFis() {
        return razonFis;
    }

    public void setRazonFis(String razonFis) {
        this.razonFis = razonFis;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getSaldoPermitido() {
        return saldoPermitido;
    }

    public void setSaldoPermitido(Double saldoPermitido) {
        this.saldoPermitido = saldoPermitido;
    }

    public Double getDonativoPermitido() {
        return donativoPermitido;
    }

    public void setDonativoPermitido(Double donativoPermitido) {
        this.donativoPermitido = donativoPermitido;
    }

    @XmlTransient
    public Collection<Recibo> getReciboCollection() {
        return reciboCollection;
    }

    public void setReciboCollection(Collection<Recibo> reciboCollection) {
        this.reciboCollection = reciboCollection;
    }

    public SubAreas getIDSubArea() {
        return iDSubArea;
    }

    public void setIDSubArea(SubAreas iDSubArea) {
        this.iDSubArea = iDSubArea;
    }

    public GrupoInst getIDGrupoInst() {
        return iDGrupoInst;
    }

    public void setIDGrupoInst(GrupoInst iDGrupoInst) {
        this.iDGrupoInst = iDGrupoInst;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDInstitucion != null ? iDInstitucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Instituciones)) {
            return false;
        }
        Instituciones other = (Instituciones) object;
        if ((this.iDInstitucion == null && other.iDInstitucion != null) || (this.iDInstitucion != null && !this.iDInstitucion.equals(other.iDInstitucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return iDInstitucion + " : " + institucion;
    }

    @XmlTransient
    public Collection<Movimientos> getMovimientosCollection() {
        return movimientosCollection;
    }

    public void setMovimientosCollection(Collection<Movimientos> movimientosCollection) {
        this.movimientosCollection = movimientosCollection;
    }
}
