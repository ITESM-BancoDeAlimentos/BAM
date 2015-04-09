/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "Existencias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Existencias.findAll", query = "SELECT e FROM Existencias e"),
    @NamedQuery(name = "Existencias.findByArtFecArePro", query = "SELECT e FROM Existencias e WHERE e.iDArticulo = :iDArticulo AND e.fechaCad = :fechaCad AND e.iDArea = :iDArea AND e.iDPrograma = :iDPrograma"),
    @NamedQuery(name = "Existencias.findByFechaCad", query = "SELECT e FROM Existencias e WHERE e.fechaCad = :fechaCad"),
    @NamedQuery(name = "Existencias.findByExistencia", query = "SELECT e FROM Existencias e WHERE e.existencia = :existencia"),
    @NamedQuery(name = "Existencias.findByAsignado", query = "SELECT e FROM Existencias e WHERE e.asignado = :asignado"),
    @NamedQuery(name = "Existencias.findByCanPedido", query = "SELECT e FROM Existencias e WHERE e.canPedido = :canPedido"),
    @NamedQuery(name = "Existencias.findByMerma", query = "SELECT e FROM Existencias e WHERE e.merma = :merma"),
    @NamedQuery(name = "Existencias.findByEntrada", query = "SELECT e FROM Existencias e WHERE e.entrada = :entrada"),
    @NamedQuery(name = "Existencias.findBySalida", query = "SELECT e FROM Existencias e WHERE e.salida = :salida"),
    @NamedQuery(name = "Existencias.findByIDExistencias", query = "SELECT e FROM Existencias e WHERE e.iDExistencias = :iDExistencias")})
public class Existencias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "FechaCad")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Existencia")
    private Double existencia;
    @Column(name = "Asignado")
    private Double asignado;
    @Column(name = "CanPedido")
    private Double canPedido;
    @Column(name = "Merma")
    private Double merma;
    @Column(name = "Entrada")
    private Double entrada;
    @Column(name = "Salida")
    private Double salida;
    @Id
    @Basic(optional = false)
    @Column(name = "IDExistencias")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iDExistencias;
    @JoinColumn(name = "IDPrograma", referencedColumnName = "IDPrograma")
    @ManyToOne
    private Programas iDPrograma;
    @JoinColumn(name = "IDArticulo", referencedColumnName = "IDArticulo")
    @ManyToOne
    private Articulos iDArticulo;
    @JoinColumn(name = "IDArea", referencedColumnName = "IDArea")
    @ManyToOne
    private Areas iDArea;

    public Existencias() {
    }

    public Existencias(Integer iDExistencias) {
        this.iDExistencias = iDExistencias;
    }
    
    public Existencias(Articulos iDArticulo, Areas iDArea, Programas iDPrograma, 
            Date fechaCad, Double cantidad, double salida, double asignado) {
        this.iDArticulo = iDArticulo;
        this.iDArea = iDArea;
        this.iDPrograma = iDPrograma;
        this.fechaCad = fechaCad;
        this.entrada = cantidad;
        this.salida = salida;
        this.asignado = asignado;
    }

    public Date getFechaCad() {
        return fechaCad;
    }

    public void setFechaCad(Date fechaCad) {
        this.fechaCad = fechaCad;
    }

    public Double getExistencia() {
        return existencia;
    }

    public void setExistencia(Double existencia) {
        this.existencia = existencia;
    }

    public Double getAsignado() {
        return asignado;
    }

    public void setAsignado(Double asignado) {
        this.asignado = asignado;
    }

    public Double getCanPedido() {
        return canPedido;
    }

    public void setCanPedido(Double canPedido) {
        this.canPedido = canPedido;
    }

    public Double getMerma() {
        return merma;
    }

    public void setMerma(Double merma) {
        this.merma = merma;
    }

    public Double getEntrada() {
        return entrada;
    }

    public void setEntrada(Double entrada) {
        this.entrada = entrada;
    }

    public Double getSalida() {
        return salida;
    }

    public void setSalida(Double salida) {
        this.salida = salida;
    }

    public Integer getIDExistencias() {
        return iDExistencias;
    }

    public void setIDExistencias(Integer iDExistencias) {
        this.iDExistencias = iDExistencias;
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
        hash += (iDExistencias != null ? iDExistencias.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Existencias)) {
            return false;
        }
        Existencias other = (Existencias) object;
        if ((this.iDExistencias == null && other.iDExistencias != null) || (this.iDExistencias != null && !this.iDExistencias.equals(other.iDExistencias))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.Existencias[ iDExistencias=" + iDExistencias + " ]";
    }
    
}
