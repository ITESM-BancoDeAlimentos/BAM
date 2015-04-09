package com.caritas.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "Articulos", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulos.findAll", query = "SELECT a FROM Articulos a"),
    @NamedQuery(name = "Articulos.findByIDArticulo", query = "SELECT a FROM Articulos a WHERE a.iDArticulo = :iDArticulo"),
    @NamedQuery(name = "Articulos.findByArticulo", query = "SELECT a FROM Articulos a WHERE a.articulo = :articulo"),
    @NamedQuery(name = "Articulos.findByAlmacen", query = "SELECT a FROM Articulos a WHERE a.almacen = :almacen"),
    @NamedQuery(name = "Articulos.findByLocalizacion", query = "SELECT a FROM Articulos a WHERE a.localizacion = :localizacion"),
    @NamedQuery(name = "Articulos.findByCostoBenef1", query = "SELECT a FROM Articulos a WHERE a.costoBenef1 = :costoBenef1"),
    @NamedQuery(name = "Articulos.findByCostoBenef2", query = "SELECT a FROM Articulos a WHERE a.costoBenef2 = :costoBenef2"),
    @NamedQuery(name = "Articulos.findByCuotaRecup1", query = "SELECT a FROM Articulos a WHERE a.cuotaRecup1 = :cuotaRecup1"),
    @NamedQuery(name = "Articulos.findByCuotaRecup2", query = "SELECT a FROM Articulos a WHERE a.cuotaRecup2 = :cuotaRecup2"),
    @NamedQuery(name = "Articulos.findByReorden1", query = "SELECT a FROM Articulos a WHERE a.reorden1 = :reorden1"),
    @NamedQuery(name = "Articulos.findByReorden2", query = "SELECT a FROM Articulos a WHERE a.reorden2 = :reorden2"),
    @NamedQuery(name = "Articulos.findByCantidadUni", query = "SELECT a FROM Articulos a WHERE a.cantidadUni = :cantidadUni"),
    @NamedQuery(name = "Articulos.findByPeso", query = "SELECT a FROM Articulos a WHERE a.peso = :peso"),
    @NamedQuery(name = "Articulos.findByFactor", query = "SELECT a FROM Articulos a WHERE a.factor = :factor"),
    @NamedQuery(name = "Articulos.findByPMercado1", query = "SELECT a FROM Articulos a WHERE a.pMercado1 = :pMercado1"),
    @NamedQuery(name = "Articulos.findByPMercado2", query = "SELECT a FROM Articulos a WHERE a.pMercado2 = :pMercado2"),
    @NamedQuery(name = "Articulos.findLike", query = "SELECT a FROM Articulos a WHERE a.iDArticulo LIKE :query OR a.articulo LIKE :query"),
    @NamedQuery(name = "Articulos.countLike", query = "SELECT COUNT(a) FROM Articulos a WHERE a.iDArticulo LIKE :query OR a.articulo LIKE :query"),
    @NamedQuery(name = "Articulos.countId", query = "SELECT COUNT(a) FROM Articulos a WHERE TRIM(a.iDArticulo) = :query"),
    @NamedQuery(name = "Articulos.findArticulos", query = "SELECT a.articulo FROM Articulos a ORDER BY a.articulo")})
public class Articulos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "IDArticulo", nullable = false, length = 13)
    private String iDArticulo;
    @Size(max = 100)
    @Column(name = "Articulo", length = 100)
    private String articulo;
    @Size(max = 1)
    @Column(name = "Almacen", length = 1)
    private String almacen;
    @Size(max = 10)
    @Column(name = "Localizacion", length = 10)
    private String localizacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CostoBenef1", precision = 53)
    private Double costoBenef1;
    @Column(name = "CostoBenef2", precision = 53)
    private Double costoBenef2;
    @Column(name = "CuotaRecup1", precision = 53)
    private Double cuotaRecup1;
    @Column(name = "CuotaRecup2", precision = 53)
    private Double cuotaRecup2;
    @Column(name = "Reorden1", precision = 53)
    private Double reorden1;
    @Column(name = "Reorden2", precision = 53)
    private Double reorden2;
    @Column(name = "CantidadUni", precision = 53)
    private Double cantidadUni;
    @Column(name = "Peso", precision = 53)
    private Double peso;
    @Column(name = "Factor", precision = 53)
    private Double factor;
    @Column(name = "PMercado1", precision = 53)
    private Double pMercado1;
    @Column(name = "PMercado2", precision = 53)
    private Double pMercado2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDArticulo")
    private Collection<ReciboDet> reciboDetCollection;
    @JoinColumn(name = "IDVariedad", referencedColumnName = "IDVariedad")
    @ManyToOne
    private Variedad iDVariedad;
    @JoinColumn(name = "UnidadMed2", referencedColumnName = "IDUnidad")
    @ManyToOne
    private Unidad unidadMed2;
    @JoinColumn(name = "UnidadMed1", referencedColumnName = "IDUnidad")
    @ManyToOne
    private Unidad unidadMed1;
    @JoinColumn(name = "IDGrupoRef", referencedColumnName = "IDGrupoRef")
    @ManyToOne
    private GrupoRef iDGrupoRef;
    @JoinColumn(name = "IDGrupoAlim", referencedColumnName = "IdGrupoAlim")
    @ManyToOne
    private GrupoAlim iDGrupoAlim;
    @JoinColumn(name = "IDDonante", referencedColumnName = "IDDonante")
    @ManyToOne
    private Donantes iDDonante;

    public Articulos() {
    }

    public Articulos(String iDArticulo) {
        this.iDArticulo = iDArticulo;
    }

    public String getIDArticulo() {
        return iDArticulo;
    }

    public void setIDArticulo(String iDArticulo) {
        this.iDArticulo = iDArticulo;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo.toUpperCase();
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public Double getCostoBenef1() {
        return costoBenef1;
    }

    public void setCostoBenef1(Double costoBenef1) {
        this.costoBenef1 = costoBenef1;
    }

    public Double getCostoBenef2() {
        return costoBenef2;
    }

    public void setCostoBenef2(Double costoBenef2) {
        this.costoBenef2 = costoBenef2;
    }

    public Double getCuotaRecup1() {
        return cuotaRecup1;
    }

    public void setCuotaRecup1(Double cuotaRecup1) {
        this.cuotaRecup1 = cuotaRecup1;
    }

    public Double getCuotaRecup2() {
        return cuotaRecup2;
    }

    public void setCuotaRecup2(Double cuotaRecup2) {
        this.cuotaRecup2 = cuotaRecup2;
    }

    public Double getReorden1() {
        return reorden1;
    }

    public void setReorden1(Double reorden1) {
        this.reorden1 = reorden1;
    }

    public Double getReorden2() {
        return reorden2;
    }

    public void setReorden2(Double reorden2) {
        this.reorden2 = reorden2;
    }

    public Double getCantidadUni() {
        return cantidadUni;
    }

    public void setCantidadUni(Double cantidadUni) {
        this.cantidadUni = cantidadUni;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    public Double getPMercado1() {
        return pMercado1;
    }

    public void setPMercado1(Double pMercado1) {
        this.pMercado1 = pMercado1;
    }

    public Double getPMercado2() {
        return pMercado2;
    }

    public void setPMercado2(Double pMercado2) {
        this.pMercado2 = pMercado2;
    }

    @XmlTransient
    public Collection<ReciboDet> getReciboDetCollection() {
        return reciboDetCollection;
    }

    public void setReciboDetCollection(Collection<ReciboDet> reciboDetCollection) {
        this.reciboDetCollection = reciboDetCollection;
    }

    public Variedad getIDVariedad() {
        return iDVariedad;
    }

    public void setIDVariedad(Variedad iDVariedad) {
        this.iDVariedad = iDVariedad;
    }

    public Unidad getUnidadMed2() {
        return unidadMed2;
    }

    public void setUnidadMed2(Unidad unidadMed2) {
        this.unidadMed2 = unidadMed2;
    }

    public Unidad getUnidadMed1() {
        return unidadMed1;
    }

    public void setUnidadMed1(Unidad unidadMed1) {
        this.unidadMed1 = unidadMed1;
    }

    public GrupoRef getIDGrupoRef() {
        return iDGrupoRef;
    }

    public void setIDGrupoRef(GrupoRef iDGrupoRef) {
        this.iDGrupoRef = iDGrupoRef;
    }

    public GrupoAlim getIDGrupoAlim() {
        return iDGrupoAlim;
    }

    public void setIDGrupoAlim(GrupoAlim iDGrupoAlim) {
        this.iDGrupoAlim = iDGrupoAlim;
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
        hash += (iDArticulo != null ? iDArticulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulos)) {
            return false;
        }
        Articulos other = (Articulos) object;
        if ((this.iDArticulo == null && other.iDArticulo != null) || (this.iDArticulo != null && !this.iDArticulo.equals(other.iDArticulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.Articulos[ iDArticulo=" + iDArticulo + " ]";
    }
    
}
