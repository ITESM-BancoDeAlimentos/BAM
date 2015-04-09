package com.caritas.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "BancosDeAlimentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BancosDeAlimentos.findAll", query = "SELECT b FROM BancosDeAlimentos b"),
    @NamedQuery(name = "BancosDeAlimentos.findByIDBancoDeAlimentos", query = "SELECT b FROM BancosDeAlimentos b WHERE b.iDBancoDeAlimentos = :iDBancoDeAlimentos"),
    @NamedQuery(name = "BancosDeAlimentos.findByNombre", query = "SELECT b FROM BancosDeAlimentos b WHERE b.nombre = :nombre"),
    @NamedQuery(name = "BancosDeAlimentos.findByPoblacion", query = "SELECT b FROM BancosDeAlimentos b WHERE b.poblacion = :poblacion"),
    @NamedQuery(name = "BancosDeAlimentos.findLike", query = "SELECT b FROM BancosDeAlimentos b WHERE b.nombre LIKE :query"),
    @NamedQuery(name = "BancosDeAlimentos.findMovtoDetByFolio", query = "SELECT m FROM MovtosDet m WHERE m.iDFolio = :iDFolio AND m.tipoMov = :tipoMov")})
public class BancosDeAlimentos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDBancoDeAlimentos")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iDBancoDeAlimentos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Poblacion")
    private int poblacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bancosDeAlimentos")
    private Collection<DistribucionAMBADet> distribucionAMBADetCollection;

    public BancosDeAlimentos() {
    }

    public BancosDeAlimentos(Integer iDBancoDeAlimentos) {
        this.iDBancoDeAlimentos = iDBancoDeAlimentos;
    }

    public BancosDeAlimentos(Integer iDBancoDeAlimentos, String nombre, int poblacion) {
        this.iDBancoDeAlimentos = iDBancoDeAlimentos;
        this.nombre = nombre;
        this.poblacion = poblacion;
    }

    public Integer getIDBancoDeAlimentos() {
        return iDBancoDeAlimentos;
    }

    public void setIDBancoDeAlimentos(Integer iDBancoDeAlimentos) {
        this.iDBancoDeAlimentos = iDBancoDeAlimentos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(int poblacion) {
        this.poblacion = poblacion;
    }

    @XmlTransient
    public Collection<DistribucionAMBADet> getDistribucionAMBADetCollection() {
        return distribucionAMBADetCollection;
    }

    public void setDistribucionAMBADetCollection(Collection<DistribucionAMBADet> distribucionAMBADetCollection) {
        this.distribucionAMBADetCollection = distribucionAMBADetCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDBancoDeAlimentos != null ? iDBancoDeAlimentos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BancosDeAlimentos)) {
            return false;
        }
        BancosDeAlimentos other = (BancosDeAlimentos) object;
        if ((this.iDBancoDeAlimentos == null && other.iDBancoDeAlimentos != null) || (this.iDBancoDeAlimentos != null && !this.iDBancoDeAlimentos.equals(other.iDBancoDeAlimentos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.BancosDeAlimentos[ iDBancoDeAlimentos=" + iDBancoDeAlimentos + " ]";
    }
    
}
