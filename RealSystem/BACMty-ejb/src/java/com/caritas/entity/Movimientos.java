package com.caritas.entity;

import com.caritas.facade.MovtosDetFacade;
import com.caritas.util.EjbUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "Movimientos", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"iDMovimientos"}),
    @UniqueConstraint(columnNames = {"iDFolio", "tipoMov"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movimientos.findAll", query = "SELECT m FROM Movimientos m"),
    @NamedQuery(name = "Movimientos.findByIDFolio", query = "SELECT m FROM Movimientos m WHERE m.iDFolio = :iDFolio"),
    @NamedQuery(name = "Movimientos.findByIDFolioTipo", query = "SELECT m FROM Movimientos m WHERE m.iDFolio = :iDFolio AND m.tipoMov = :tipoMov"),
    @NamedQuery(name = "Movimientos.findByIDFolioTipoAMBA", query = "SELECT m FROM Movimientos m WHERE m.iDFolio = :iDFolio AND m.tipoMov = :tipoMov AND m.origen = :origen"),
    @NamedQuery(name = "Movimientos.findByTipoMov", query = "SELECT m FROM Movimientos m WHERE m.tipoMov = :tipoMov"),
    @NamedQuery(name = "Movimientos.findByTipoMovArea", query = "SELECT m.iDFolio FROM Movimientos m WHERE m.tipoMov = :tipoMov AND m.iDArea.iDArea = :iDArea ORDER BY m.iDFolio"),
    @NamedQuery(name = "Movimientos.findByFechaMov", query = "SELECT m FROM Movimientos m WHERE m.fechaMov = :fechaMov"),
    @NamedQuery(name = "Movimientos.findByFechaMovRange", query = "SELECT m FROM Movimientos m WHERE m.fechaMov >= :fechaMov1 AND m.fechaMov <= :fechaMov2"),
    @NamedQuery(name = "Movimientos.findByFechaMovRangeTipo", query = "SELECT m FROM Movimientos m WHERE m.fechaMov >= :fechaMov1 AND m.fechaMov <= :fechaMov2 AND m.tipoMov LIKE :tipoMov"),
    @NamedQuery(name = "Movimientos.findByFolioDon", query = "SELECT m FROM Movimientos m WHERE m.folioDon = :folioDon"),
    @NamedQuery(name = "Movimientos.findByFactura", query = "SELECT m FROM Movimientos m WHERE m.factura = :factura"),
    @NamedQuery(name = "Movimientos.findByIDSucursal", query = "SELECT m FROM Movimientos m WHERE m.iDSucursal = :iDSucursal"),
    @NamedQuery(name = "Movimientos.findByOrigen", query = "SELECT m FROM Movimientos m WHERE m.origen = :origen"),
    @NamedQuery(name = "Movimientos.findByStatusMov", query = "SELECT m.iDFolio FROM Movimientos m WHERE m.statusMov = :statusMov"),
    @NamedQuery(name = "Movimientos.findFoliosByStatusMov", query = "SELECT m.iDFolio FROM Movimientos m WHERE m.statusMov = :statusMov"),
    @NamedQuery(name = "Movimientos.findByFamilias", query = "SELECT m FROM Movimientos m WHERE m.familias = :familias"),
    @NamedQuery(name = "Movimientos.findByIntegrantes", query = "SELECT m FROM Movimientos m WHERE m.integrantes = :integrantes"),
    @NamedQuery(name = "Movimientos.findByFolioEntrada", query = "SELECT m FROM Movimientos m WHERE m.folioEntrada = :folioEntrada"),
    @NamedQuery(name = "Movimientos.findByDescuento", query = "SELECT m FROM Movimientos m WHERE m.descuento = :descuento"),
    @NamedQuery(name = "Movimientos.findByPaquete", query = "SELECT m FROM Movimientos m WHERE m.paquete = :paquete"),
    @NamedQuery(name = "Movimientos.findByFechaSist", query = "SELECT m FROM Movimientos m WHERE m.fechaSist = :fechaSist"),
    @NamedQuery(name = "Movimientos.findByIDMovimientos", query = "SELECT m FROM Movimientos m WHERE m.iDMovimientos = :iDMovimientos"),
    @NamedQuery(name = "Movimientos.findAreas", query = "SELECT a.area FROM Areas a ORDER BY a.area"),
    @NamedQuery(name = "Movimientos.findProgramas", query = "SELECT p.programa FROM Programas p, AreaPrograma ap WHERE ap.iDPrograma.iDPrograma = p.iDPrograma AND ap.iDArea = :iDArea ORDER BY p.programa"),
    @NamedQuery(name = "Movimientos.findDonantesTie", query = "SELECT d.donante FROM Donantes d WHERE d.tipoDonante = :tipoDonante ORDER BY d.donante"),
    @NamedQuery(name = "Movimientos.findAllDonantes", query = "SELECT d.donante FROM Donantes d ORDER BY d.donante"),
    @NamedQuery(name = "Movimientos.findSucursales", query = "SELECT s.iDSucursal FROM Sucursales s WHERE s.iDDonante = :iDDonante ORDER BY s.iDSucursal"),
    @NamedQuery(name = "Movimientos.findByIDSucursalIDDonante", query = "SELECT s FROM Sucursales s WHERE s.iDSucursal = :iDSucursal AND s.iDDonante = :iDDonante"),
    @NamedQuery(name = "Movimientos.findMovimientosLike", query = "SELECT m.iDFolio FROM Movimientos m WHERE m.iDFolio LIKE :query AND m.tipoMov = :tipoMov ORDER BY m.iDFolio"),
    @NamedQuery(name = "Movimientos.findArticulosLike", query = "SELECT ar.articulo FROM Articulos ar WHERE ar.articulo LIKE :query ORDER BY ar.articulo"),
    @NamedQuery(name = "Movimientos.findFoliosLike", query = "SELECT m.iDFolio FROM Movimientos m WHERE m.tipoMov = :tipoMov AND m.iDArea.iDArea = :iDArea AND m.iDFolio LIKE :query ORDER BY m.iDFolio"),
    @NamedQuery(name = "Movimientos.findArticuloByArticulo", query = "SELECT ar FROM Articulos ar WHERE ar.articulo = :articulo"),})
public class Movimientos implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Movimiento de entrada, "ENT".
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "IDFolio")
    private String iDFolio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TipoMov")
    private String tipoMov;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FechaMov")
    @Temporal(TemporalType.DATE)
    private Date fechaMov;
    @Size(max = 10)
    @Column(name = "FolioDon")
    private String folioDon;
    @Size(max = 10)
    @Column(name = "Factura")
    private String factura;
    @Size(max = 15)
    @Column(name = "IDSucursal")
    private String iDSucursal;
    @Size(max = 1)
    @Column(name = "Origen")
    private String origen;
    @Size(max = 8)
    @Column(name = "StatusMov")
    private String statusMov;
    @Column(name = "Familias")
    private Integer familias;
    @Column(name = "Integrantes")
    private Integer integrantes;
    @Size(max = 10)
    @Column(name = "FolioEntrada")
    private String folioEntrada;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Descuento")
    private Double descuento;
    @Column(name = "Paquete")
    private Double paquete;
    @Column(name = "FechaSist")
    @Temporal(TemporalType.DATE)
    private Date fechaSist;
    @Id
    @Basic(optional = false)
    @Column(name = "IDMovimientos")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iDMovimientos;
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
    @JoinColumn(name = "IDDonante", referencedColumnName = "IDDonante")
    @ManyToOne
    private Donantes iDDonante;
    @JoinColumn(name = "IDArea", referencedColumnName = "IDArea")
    @ManyToOne
    private Areas iDArea;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "IDFolio", referencedColumnName = "IDFolio", insertable = false, updatable = false),
        @JoinColumn(name = "TipoMov", referencedColumnName = "TipoMov", insertable = false, updatable = false)
    })
    private Collection<MovtosDet> movtosDetCollection;

    public Movimientos() {
    }

    public Movimientos(Integer iDMovimientos) {
        this.iDMovimientos = iDMovimientos;
    }

    public Movimientos(Integer iDMovimientos, String iDFolio, String tipoMov, Date fechaMov) {
        this.iDMovimientos = iDMovimientos;
        this.iDFolio = iDFolio;
        this.tipoMov = tipoMov;
        this.fechaMov = fechaMov;
    }
    
    public Movimientos(String iDFolio, String tipoMov, String iDSucursal, String origen, String statusMov, Sucursales iDSucursales, Programas iDPrograma, Donantes iDDonante, Areas iDArea, Usuarios iDUsuario, String folioDon) {
        this.iDFolio = iDFolio;
        this.tipoMov = tipoMov;
        this.iDSucursal = iDSucursal;
        this.origen = origen;
        this.statusMov = statusMov;
        this.iDSucursales = iDSucursales;
        this.iDPrograma = iDPrograma;
        this.iDDonante = iDDonante;
        this.iDArea = iDArea;
        this.iDUsuario = iDUsuario;
        this.folioDon = folioDon;
    }

    @XmlTransient
    public Collection<MovtosDet> getMovtosDetCollection() {
        if (movtosDetCollection == null || movtosDetCollection.isEmpty()) {
            movtosDetCollection = EjbUtil.lookup(MovtosDetFacade.class)
                    .findByIDFolioTipo(iDFolio, tipoMov);
        }
        return movtosDetCollection;
    }

    public String getIDFolio() {
        return iDFolio;
    }

    public void setIDFolio(String iDFolio) {
        this.iDFolio = iDFolio;
        movtosDetCollection = null;
    }

    public String getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
        movtosDetCollection = null;
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

    public Integer getIDMovimientos() {
        return iDMovimientos;
    }

    public void setIDMovimientos(Integer iDMovimientos) {
        this.iDMovimientos = iDMovimientos;
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
        hash += (iDMovimientos != null ? iDMovimientos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimientos)) {
            return false;
        }
        Movimientos other = (Movimientos) object;
        if ((this.iDMovimientos == null && other.iDMovimientos != null) || (this.iDMovimientos != null && !this.iDMovimientos.equals(other.iDMovimientos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.Movimientos[ iDMovimientos=" + iDMovimientos
                + ", iDFolio=" + iDFolio + " ]";
    }
}
