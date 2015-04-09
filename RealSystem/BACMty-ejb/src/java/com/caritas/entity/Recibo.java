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
@Table(name = "Recibo", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recibo.findAll", query = "SELECT r FROM Recibo r"),
    @NamedQuery(name = "Recibo.findFolios", query = "SELECT r.iDFolio FROM Recibo r"),
    @NamedQuery(name = "Recibo.findRecibosLike", query = "SELECT r.iDFolio FROM Recibo r WHERE r.iDFolio LIKE :iDFolio AND r.tipoMov = :tipoMov AND r.origen = :origen ORDER BY r.iDFolio "),
    @NamedQuery(name = "Recibo.findFoliosByOrigen", query = "SELECT r.iDFolio FROM Recibo r WHERE r.origen = :origen ORDER BY r.iDFolio"),
    @NamedQuery(name = "Recibo.findFoliosByStatus", query = "SELECT r.iDFolio FROM Recibo r WHERE r.statusMov = :statusMov AND r.origen = :origen"),
    @NamedQuery(name = "Recibo.findByIDFolio", query = "SELECT r FROM Recibo r WHERE r.iDFolio = :iDFolio"),
    @NamedQuery(name = "Recibo.findByFolioOrigen", query = "SELECT r FROM Recibo r WHERE r.iDFolio = :iDFolio AND r.origen = :origen"),
    @NamedQuery(name = "Recibo.findByIDFolioTipo", query = "SELECT r FROM Recibo r WHERE r.iDFolio = :iDFolio AND r.tipoMov = :tipoMov AND r.origen = :origen"),
    @NamedQuery(name = "Recibo.findByIDFolioTipoNoOrigen", query = "SELECT r FROM Recibo r WHERE r.iDFolio = :iDFolio AND r.tipoMov = :tipoMov"),
    @NamedQuery(name = "Recibo.findByTipoMov", query = "SELECT r FROM Recibo r WHERE r.tipoMov = :tipoMov"),
    @NamedQuery(name = "Recibo.findByFechaMov", query = "SELECT r FROM Recibo r WHERE r.fechaMov = :fechaMov"),
    @NamedQuery(name = "Recibo.findByFolioDon", query = "SELECT r FROM Recibo r WHERE r.folioDon = :folioDon"),
    @NamedQuery(name = "Recibo.findByFactura", query = "SELECT r FROM Recibo r WHERE r.factura = :factura"),
    @NamedQuery(name = "Recibo.findByIDSucursal", query = "SELECT r FROM Recibo r WHERE r.iDSucursal = :iDSucursal"),
    @NamedQuery(name = "Recibo.findByOrigen", query = "SELECT r FROM Recibo r WHERE r.origen = :origen"),
    @NamedQuery(name = "Recibo.findByStatusMov", query = "SELECT r FROM Recibo r WHERE r.statusMov = :statusMov"),
    @NamedQuery(name = "Recibo.findByFamilias", query = "SELECT r FROM Recibo r WHERE r.familias = :familias"),
    @NamedQuery(name = "Recibo.findByIntegrantes", query = "SELECT r FROM Recibo r WHERE r.integrantes = :integrantes"),
    @NamedQuery(name = "Recibo.findByFolioEntrada", query = "SELECT r FROM Recibo r WHERE r.folioEntrada = :folioEntrada"),
    @NamedQuery(name = "Recibo.findByDescuento", query = "SELECT r FROM Recibo r WHERE r.descuento = :descuento"),
    @NamedQuery(name = "Recibo.findByPaquete", query = "SELECT r FROM Recibo r WHERE r.paquete = :paquete"),
    @NamedQuery(name = "Recibo.findByFechaSist", query = "SELECT r FROM Recibo r WHERE r.fechaSist = :fechaSist"),
    @NamedQuery(name = "Recibo.findByIDRecibo", query = "SELECT r FROM Recibo r WHERE r.iDRecibo = :iDRecibo"),
    @NamedQuery(name = "Recibo.findAreas", query = "SELECT a.area FROM Areas a ORDER BY a.area"),
    @NamedQuery(name = "Recibo.findProgramas", query = "SELECT p.programa FROM Programas p, AreaPrograma ap WHERE ap.iDPrograma.iDPrograma = p.iDPrograma AND ap.iDArea = :iDArea ORDER BY p.programa"),
    @NamedQuery(name = "Recibo.findDonantesTie", query = "SELECT d.donante FROM Donantes d WHERE d.tipoDonante = :tipoDonante ORDER BY d.donante"),
    @NamedQuery(name = "Recibo.findDonantesAMBA", query = "SELECT d.donante FROM Donantes d ORDER BY d.donante"),
    @NamedQuery(name = "Recibo.findSucursales", query = "SELECT s.iDSucursal FROM Sucursales s WHERE s.iDDonante = :iDDonante ORDER BY s.iDSucursal"),
    @NamedQuery(name = "Recibo.findByIDSucursalIDDonante", query = "SELECT s FROM Sucursales s WHERE s.iDSucursal = :iDSucursal AND s.iDDonante = :iDDonante"),
    @NamedQuery(name = "Recibo.findArticuloByArticulo", query = "SELECT ar FROM Articulos ar WHERE ar.articulo = :articulo"),
    @NamedQuery(name = "Recibo.findArticulosLike", query = "SELECT ar.articulo FROM Articulos ar WHERE ar.articulo LIKE :query ORDER BY ar.articulo")})
public class Recibo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "IDFolio", nullable = false, length = 10)
    private String iDFolio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TipoMov", nullable = false, length = 10)
    private String tipoMov;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FechaMov", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMov;
    @Size(max = 10)
    @Column(name = "FolioDon", length = 10)
    private String folioDon;
    @Size(max = 10)
    @Column(name = "Factura", length = 10)
    private String factura;
    @Size(max = 15)
    @Column(name = "IDSucursal", length = 15)
    private String iDSucursal;
    @Size(max = 1)
    @Column(name = "Origen", length = 1)
    private String origen;
    @Size(max = 8)
    @Column(name = "StatusMov", length = 8)
    private String statusMov;
    @Column(name = "Familias")
    private Integer familias;
    @Column(name = "Integrantes")
    private Integer integrantes;
    @Size(max = 10)
    @Column(name = "FolioEntrada", length = 10)
    private String folioEntrada;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Descuento", precision = 53)
    private Double descuento;
    @Column(name = "Paquete", precision = 53)
    private Double paquete;
    @Column(name = "FechaSist")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSist;
    @Id
    @Basic(optional = false)
    @Column(name = "IDRecibo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iDRecibo;
    @JoinColumn(name = "IDUsuario", referencedColumnName = "IDUsuario")
    @ManyToOne
    private Usuarios iDUsuario;
    @JoinColumn(name = "IDSucursales", referencedColumnName = "IDSucursales")
    @ManyToOne
    private Sucursales iDSucursales;
    @JoinColumn(name = "IDProveedor", referencedColumnName = "IDProveedor")
    @ManyToOne
    private Proveedores iDProveedor;
    @JoinColumn(name = "IDPrograma", referencedColumnName = "IDPrograma")
    @ManyToOne
    private Programas iDPrograma;
    @JoinColumn(name = "IDInstitucion", referencedColumnName = "IDInstitucion")
    @ManyToOne
    private Instituciones iDInstitucion;
    @JoinColumn(name = "IDGrupoRef", referencedColumnName = "IDGrupoRef")
    @ManyToOne
    private GrupoRef iDGrupoRef;
    @JoinColumn(name = "IDDonante", referencedColumnName = "IDDonante", nullable = false)
    @ManyToOne(optional = false)
    private Donantes iDDonante;
    @JoinColumn(name = "IDArea", referencedColumnName = "IDArea")
    @ManyToOne
    private Areas iDArea;

    public Recibo() {
    }

    public Recibo(Integer iDRecibo) {
        this.iDRecibo = iDRecibo;
    }

    public Recibo(Integer iDRecibo, String iDFolio, String tipoMov, Date fechaMov) {
        this.iDRecibo = iDRecibo;
        this.iDFolio = iDFolio;
        this.tipoMov = tipoMov;
        this.fechaMov = fechaMov;
    }

    public String getIDFolio() {
        return iDFolio;
    }

    public void setIDFolio(String iDFolio) {
        this.iDFolio = iDFolio;
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

    public String getFolioDon() {
        return folioDon;
    }

    public void setFolioDon(String folioDon) {
        this.folioDon = folioDon;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getIDSucursal() {
        return iDSucursal;
    }

    public void setIDSucursal(String iDSucursal) {
        this.iDSucursal = iDSucursal;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getStatusMov() {
        return statusMov;
    }

    public void setStatusMov(String statusMov) {
        this.statusMov = statusMov;
    }

    public Integer getFamilias() {
        return familias;
    }

    public void setFamilias(Integer familias) {
        this.familias = familias;
    }

    public Integer getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(Integer integrantes) {
        this.integrantes = integrantes;
    }

    public String getFolioEntrada() {
        return folioEntrada;
    }

    public void setFolioEntrada(String folioEntrada) {
        this.folioEntrada = folioEntrada;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getPaquete() {
        return paquete;
    }

    public void setPaquete(Double paquete) {
        this.paquete = paquete;
    }

    public Date getFechaSist() {
        return fechaSist;
    }

    public void setFechaSist(Date fechaSist) {
        this.fechaSist = fechaSist;
    }

    public Integer getIDRecibo() {
        return iDRecibo;
    }

    public void setIDRecibo(Integer iDRecibo) {
        this.iDRecibo = iDRecibo;
    }

    public Usuarios getIDUsuario() {
        return iDUsuario;
    }

    public void setIDUsuario(Usuarios iDUsuario) {
        this.iDUsuario = iDUsuario;
    }

    public Sucursales getIDSucursales() {
        return iDSucursales;
    }

    public void setIDSucursales(Sucursales iDSucursales) {
        this.iDSucursales = iDSucursales;
    }

    public Proveedores getIDProveedor() {
        return iDProveedor;
    }

    public void setIDProveedor(Proveedores iDProveedor) {
        this.iDProveedor = iDProveedor;
    }

    public Programas getIDPrograma() {
        return iDPrograma;
    }

    public void setIDPrograma(Programas iDPrograma) {
        this.iDPrograma = iDPrograma;
    }

    public Instituciones getIDInstitucion() {
        return iDInstitucion;
    }

    public void setIDInstitucion(Instituciones iDInstitucion) {
        this.iDInstitucion = iDInstitucion;
    }

    public GrupoRef getIDGrupoRef() {
        return iDGrupoRef;
    }

    public void setIDGrupoRef(GrupoRef iDGrupoRef) {
        this.iDGrupoRef = iDGrupoRef;
    }

    public Donantes getIDDonante() {
        return iDDonante;
    }

    public void setIDDonante(Donantes iDDonante) {
        this.iDDonante = iDDonante;
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
        hash += (iDRecibo != null ? iDRecibo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recibo)) {
            return false;
        }
        Recibo other = (Recibo) object;
        if ((this.iDRecibo == null && other.iDRecibo != null) || (this.iDRecibo != null && !this.iDRecibo.equals(other.iDRecibo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return iDFolio;
    }
    
}
