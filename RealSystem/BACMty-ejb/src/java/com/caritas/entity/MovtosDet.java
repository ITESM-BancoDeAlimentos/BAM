package com.caritas.entity;

import com.caritas.facade.MovimientosFacade;
import com.caritas.util.EjbUtil;
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
@Table(name = "Movtos_Det", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"iDFolio", "tipoMov"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovtosDet.findAll", query = "SELECT m FROM MovtosDet m"),
    @NamedQuery(name = "MovtosDet.findByIDFolio", query = "SELECT m FROM MovtosDet m WHERE m.iDFolio = :iDFolio"),
    @NamedQuery(name = "MovtosDet.findByIDFolioTipo", query = "SELECT m FROM MovtosDet m WHERE m.iDFolio = :iDFolio AND m.tipoMov = :tipoMov"),
    @NamedQuery(name = "MovtosDet.findByTipoMov", query = "SELECT m FROM MovtosDet m WHERE m.tipoMov = :tipoMov"),
    @NamedQuery(name = "MovtosDet.findByFechaCad", query = "SELECT m FROM MovtosDet m WHERE m.fechaCad = :fechaCad"),
    @NamedQuery(name = "MovtosDet.findByCantidad", query = "SELECT m FROM MovtosDet m WHERE m.cantidad = :cantidad"),
    @NamedQuery(name = "Movto3.1.2.2sDet.findByPeso", query = "SELECT m FROM MovtosDet m WHERE m.peso = :peso"),
    @NamedQuery(name = "MovtosDet.findByMerma", query = "SELECT m FROM MovtosDet m WHERE m.merma = :merma"),
    @NamedQuery(name = "MovtosDet.findByCostoBenef", query = "SELECT m FROM MovtosDet m WHERE m.costoBenef = :costoBenef"),
    @NamedQuery(name = "MovtosDet.findByCuotaRecup", query = "SELECT m FROM MovtosDet m WHERE m.cuotaRecup = :cuotaRecup"),
    @NamedQuery(name = "MovtosDet.findByIDMovtosDet", query = "SELECT m FROM MovtosDet m WHERE m.iDMovtosDet = :iDMovtosDet")})
public class MovtosDet implements Serializable {
    private static final long serialVersionUID = 1L;
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
    @Column(name = "FechaCad")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cantidad")
    private double cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Peso")
    private Double peso;
    @Column(name = "Merma")
    private Double merma;
    @Column(name = "CostoBenef")
    private Double costoBenef;
    @Column(name = "CuotaRecup")
    private Double cuotaRecup;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDMovtos_Det")
    private Integer iDMovtosDet;
    @JoinColumn(name = "IDPrograma", referencedColumnName = "IDPrograma")
    @ManyToOne
    private Programas iDPrograma;
    @JoinColumn(name = "IDArticulo", referencedColumnName = "IDArticulo")
    @ManyToOne(optional = false)
    private Articulos iDArticulo;
    @JoinColumn(name = "IDArea", referencedColumnName = "IDArea")
    @ManyToOne
    private Areas iDArea;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
        @JoinColumn(name = "IDFolio", referencedColumnName = "IDFolio", insertable = false, updatable = false),
        @JoinColumn(name = "TipoMov", referencedColumnName = "TipoMov", insertable = false, updatable = false)
    })
    private Movimientos movimientos;

    public MovtosDet() {
    }

    public MovtosDet(Integer iDMovtosDet) {
        this.iDMovtosDet = iDMovtosDet;
    }

    
    public MovtosDet(Integer iDMovtosDet, String iDFolio, String tipoMov, Date fechaCad, double cantidad) {
        this.iDMovtosDet = iDMovtosDet;
        this.iDFolio = iDFolio;
        this.tipoMov = tipoMov;
        this.fechaCad = fechaCad;
        this.cantidad = cantidad;
    }

    public MovtosDet(String iDFolio, String tipoMov, Date fechaCad, double cantidad, Double peso,
            Double merma, Double costoBenef, Double cuotaRecup, Programas iDPrograma, Articulos iDArticulo, Areas iDArea) {
        this.iDFolio = iDFolio;
        this.tipoMov = tipoMov;
        this.fechaCad = fechaCad;
        this.cantidad = cantidad;
        this.peso = peso;
        this.merma = merma;
        this.costoBenef = costoBenef;
        this.cuotaRecup = cuotaRecup;
        this.iDPrograma = iDPrograma;
        this.iDArticulo = iDArticulo;
        this.iDArea = iDArea;
    }

    public Movimientos getMovimientos() {
        if (movimientos == null) {
            movimientos = EjbUtil.lookup(MovimientosFacade.class)
                    .findByIDFolioTipo(iDFolio, tipoMov);
        }
        return movimientos;
    }

    public void setMovimientos(Movimientos movimientos) {
        this.movimientos = movimientos;
        setIDFolio(movimientos.getIDFolio());
        setTipoMov(movimientos.getTipoMov());
    }

    public String getIDFolio() {
        return iDFolio;
    }

    public void setIDFolio(String iDFolio) {
        this.iDFolio = iDFolio;
        movimientos = null;
    }

    public String getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
        movimientos = null;
    }

    public Date getFechaCad() {
        return fechaCad;
    }

    public void setFechaCad(Date fechaCad) {
        this.fechaCad = fechaCad;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getMerma() {
        return merma;
    }

    public void setMerma(Double merma) {
        this.merma = merma;
    }

    public Double getCostoBenef() {
        return costoBenef;
    }

    public void setCostoBenef(Double costoBenef) {
        this.costoBenef = costoBenef;
    }

    public Double getCuotaRecup() {
        return cuotaRecup;
    }

    public void setCuotaRecup(Double cuotaRecup) {
        this.cuotaRecup = cuotaRecup;
    }

    public Integer getIDMovtosDet() {
        return iDMovtosDet;
    }

    public void setIDMovtosDet(Integer iDMovtosDet) {
        this.iDMovtosDet = iDMovtosDet;
    }

    public Programas getIDPrograma() {
        return iDPrograma;
    }

    public void setIDPrograma(Programas iDPrograma) {
        this.iDPrograma = iDPrograma;
    }

    public Articulos getIDArticulo() {
        return iDArticulo;
    }

    public void setIDArticulo(Articulos iDArticulo) {
        this.iDArticulo = iDArticulo;
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
        hash += (iDMovtosDet != null ? iDMovtosDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovtosDet)) {
            return false;
        }
        MovtosDet other = (MovtosDet) object;
        if ((this.iDMovtosDet == null && other.iDMovtosDet != null) || (this.iDMovtosDet != null && !this.iDMovtosDet.equals(other.iDMovtosDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.MovtosDet[ iDMovtosDet=" + iDMovtosDet + " ]";
    }
    
}
