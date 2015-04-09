/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "Programas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programas.findAll", query = "SELECT p FROM Programas p ORDER BY p.programa"),
    @NamedQuery(name = "Programas.findLike", query = "SELECT p FROM Programas p WHERE p.iDPrograma LIKE :iDPrograma OR p.programa LIKE :programa"),
    @NamedQuery(name = "Programas.findByIDPrograma", query = "SELECT p FROM Programas p WHERE p.iDPrograma = :iDPrograma"),
    @NamedQuery(name = "Programas.findByPrograma", query = "SELECT p FROM Programas p WHERE p.programa = :programa")})
public class Programas implements Serializable {
    @OneToMany(mappedBy = "iDPrograma")
    private Collection<ReciboDet> reciboDetCollection;
    @OneToMany(mappedBy = "iDPrograma")
    private Collection<Recibo> reciboCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "IDPrograma")
    private String iDPrograma;
    @Size(max = 50)
    @Column(name = "Programa")
    private String programa;
    @OneToMany(mappedBy = "iDPrograma")
    private Collection<Vales> valesCollection;
    @OneToMany(mappedBy = "idPrograma")
    private Collection<Distribucion> distribucionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDPrograma")
    private Collection<AccesoPrograma> accesoProgramaCollection;
    @OneToMany(mappedBy = "iDPrograma")
    private Collection<ValesCajas> valesCajasCollection;
    @OneToMany(mappedBy = "iDPrograma")
    private Collection<MovtosDet> movtosDetCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDPrograma")
    private Collection<AreaPrograma> areaProgramaCollection;
    @OneToMany(mappedBy = "iDPrograma")
    private Collection<Movimientos> movimientosCollection;
    @OneToMany(mappedBy = "iDPrograma")
    private Collection<Existencias> existenciasCollection;
    @OneToMany(mappedBy = "idPrograma")
    private Collection<DisEntradas> disEntradasCollection;
    @OneToMany(mappedBy = "iDPrograma")
    private Collection<Cajas> cajasCollection;
    @OneToMany(mappedBy = "iDPrograma")
    private Collection<MovCarAbo> movCarAboCollection;
    @OneToMany(mappedBy = "iDPrograma")
    private Collection<TmpMovtoArt> tmpMovtoArtCollection;

    public Programas() {
    }

    public Programas(String iDPrograma) {
        this.iDPrograma = iDPrograma;
    }

    public String getIDPrograma() {
        return iDPrograma;
    }

    public void setIDPrograma(String iDPrograma) {
        this.iDPrograma = iDPrograma;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    @XmlTransient
    public Collection<Vales> getValesCollection() {
        return valesCollection;
    }

    public void setValesCollection(Collection<Vales> valesCollection) {
        this.valesCollection = valesCollection;
    }

    @XmlTransient
    public Collection<Distribucion> getDistribucionCollection() {
        return distribucionCollection;
    }

    public void setDistribucionCollection(Collection<Distribucion> distribucionCollection) {
        this.distribucionCollection = distribucionCollection;
    }

    @XmlTransient
    public Collection<AccesoPrograma> getAccesoProgramaCollection() {
        return accesoProgramaCollection;
    }

    public void setAccesoProgramaCollection(Collection<AccesoPrograma> accesoProgramaCollection) {
        this.accesoProgramaCollection = accesoProgramaCollection;
    }

    @XmlTransient
    public Collection<ValesCajas> getValesCajasCollection() {
        return valesCajasCollection;
    }

    public void setValesCajasCollection(Collection<ValesCajas> valesCajasCollection) {
        this.valesCajasCollection = valesCajasCollection;
    }

    @XmlTransient
    public Collection<MovtosDet> getMovtosDetCollection() {
        return movtosDetCollection;
    }

    public void setMovtosDetCollection(Collection<MovtosDet> movtosDetCollection) {
        this.movtosDetCollection = movtosDetCollection;
    }

    @XmlTransient
    public Collection<AreaPrograma> getAreaProgramaCollection() {
        return areaProgramaCollection;
    }

    public void setAreaProgramaCollection(Collection<AreaPrograma> areaProgramaCollection) {
        this.areaProgramaCollection = areaProgramaCollection;
    }

    @XmlTransient
    public Collection<Movimientos> getMovimientosCollection() {
        return movimientosCollection;
    }

    public void setMovimientosCollection(Collection<Movimientos> movimientosCollection) {
        this.movimientosCollection = movimientosCollection;
    }

    @XmlTransient
    public Collection<Existencias> getExistenciasCollection() {
        return existenciasCollection;
    }

    public void setExistenciasCollection(Collection<Existencias> existenciasCollection) {
        this.existenciasCollection = existenciasCollection;
    }

    @XmlTransient
    public Collection<DisEntradas> getDisEntradasCollection() {
        return disEntradasCollection;
    }

    public void setDisEntradasCollection(Collection<DisEntradas> disEntradasCollection) {
        this.disEntradasCollection = disEntradasCollection;
    }

    @XmlTransient
    public Collection<Cajas> getCajasCollection() {
        return cajasCollection;
    }

    public void setCajasCollection(Collection<Cajas> cajasCollection) {
        this.cajasCollection = cajasCollection;
    }

    @XmlTransient
    public Collection<MovCarAbo> getMovCarAboCollection() {
        return movCarAboCollection;
    }

    public void setMovCarAboCollection(Collection<MovCarAbo> movCarAboCollection) {
        this.movCarAboCollection = movCarAboCollection;
    }

    @XmlTransient
    public Collection<TmpMovtoArt> getTmpMovtoArtCollection() {
        return tmpMovtoArtCollection;
    }

    public void setTmpMovtoArtCollection(Collection<TmpMovtoArt> tmpMovtoArtCollection) {
        this.tmpMovtoArtCollection = tmpMovtoArtCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDPrograma != null ? iDPrograma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programas)) {
            return false;
        }
        Programas other = (Programas) object;
        if ((this.iDPrograma == null && other.iDPrograma != null) || (this.iDPrograma != null && !this.iDPrograma.equals(other.iDPrograma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return iDPrograma + " : " + programa;
    }

    @XmlTransient
    public Collection<ReciboDet> getReciboDetCollection() {
        return reciboDetCollection;
    }

    public void setReciboDetCollection(Collection<ReciboDet> reciboDetCollection) {
        this.reciboDetCollection = reciboDetCollection;
    }

    @XmlTransient
    public Collection<Recibo> getReciboCollection() {
        return reciboCollection;
    }

    public void setReciboCollection(Collection<Recibo> reciboCollection) {
        this.reciboCollection = reciboCollection;
    }
    
}
