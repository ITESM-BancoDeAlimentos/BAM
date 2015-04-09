package com.caritas.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "Areas", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Areas.findAll", query = "SELECT a FROM Areas a ORDER BY a.area"),
    @NamedQuery(name = "Areas.findByIDArea", query = "SELECT a FROM Areas a WHERE a.iDArea = :iDArea"),
    @NamedQuery(name = "Areas.findByArea", query = "SELECT a FROM Areas a WHERE a.area = :area"),
    @NamedQuery(name = "Areas.findByDistribucion", query = "SELECT a FROM Areas a WHERE a.distribucion = :distribucion")})
public class Areas implements Serializable {
    @OneToMany(mappedBy = "iDArea")
    private Collection<Movimientos> movimientosCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "IDArea", nullable = false, length = 6)
    private String iDArea;
    @Size(max = 50)
    @Column(name = "Area", length = 50)
    private String area;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Distribucion", precision = 53)
    private Double distribucion;
    @OneToMany(mappedBy = "iDArea")
    private Collection<ReciboDet> reciboDetCollection;
    @OneToMany(mappedBy = "iDArea")
    private Collection<SubAreas> subAreasCollection;
    @OneToMany(mappedBy = "iDArea")
    private Collection<Recibo> reciboCollection;

    public Areas() {
    }

    public Areas(String iDArea) {
        this.iDArea = iDArea;
    }

    public String getIDArea() {
        return iDArea;
    }

    public void setIDArea(String iDArea) {
        this.iDArea = iDArea;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Double getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(Double distribucion) {
        this.distribucion = distribucion;
    }

    @XmlTransient
    public Collection<ReciboDet> getReciboDetCollection() {
        return reciboDetCollection;
    }

    public void setReciboDetCollection(Collection<ReciboDet> reciboDetCollection) {
        this.reciboDetCollection = reciboDetCollection;
    }

    @XmlTransient
    public Collection<SubAreas> getSubAreasCollection() {
        return subAreasCollection;
    }

    public void setSubAreasCollection(Collection<SubAreas> subAreasCollection) {
        this.subAreasCollection = subAreasCollection;
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
        hash += (iDArea != null ? iDArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Areas)) {
            return false;
        }
        Areas other = (Areas) object;
        if ((this.iDArea == null && other.iDArea != null) || (this.iDArea != null && !this.iDArea.equals(other.iDArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return iDArea + " : " + area;
    }

    @XmlTransient
    public Collection<Movimientos> getMovimientosCollection() {
        return movimientosCollection;
    }

    public void setMovimientosCollection(Collection<Movimientos> movimientosCollection) {
        this.movimientosCollection = movimientosCollection;
    }
    
}
