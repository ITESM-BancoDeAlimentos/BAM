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
@Table(name = "MovtoCajas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovtoCajas.findAll", query = "SELECT m FROM MovtoCajas m"),
    @NamedQuery(name = "MovtoCajas.findByFecha", query = "SELECT m FROM MovtoCajas m WHERE m.fecha = :fecha"),
    @NamedQuery(name = "MovtoCajas.findByStatus", query = "SELECT m FROM MovtoCajas m WHERE m.status = :status"),
    @NamedQuery(name = "MovtoCajas.findByIDMovtoCajas", query = "SELECT m FROM MovtoCajas m WHERE m.iDMovtoCajas = :iDMovtoCajas")})
public class MovtoCajas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 10)
    @Column(name = "Status")
    private String status;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDMovtoCajas")
    private Integer iDMovtoCajas;
    @JoinColumn(name = "IDCaja", referencedColumnName = "IDCaja")
    @ManyToOne
    private Cajas iDCaja;

    public MovtoCajas() {
    }

    public MovtoCajas(Integer iDMovtoCajas) {
        this.iDMovtoCajas = iDMovtoCajas;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIDMovtoCajas() {
        return iDMovtoCajas;
    }

    public void setIDMovtoCajas(Integer iDMovtoCajas) {
        this.iDMovtoCajas = iDMovtoCajas;
    }

    public Cajas getIDCaja() {
        return iDCaja;
    }

    public void setIDCaja(Cajas iDCaja) {
        this.iDCaja = iDCaja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDMovtoCajas != null ? iDMovtoCajas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovtoCajas)) {
            return false;
        }
        MovtoCajas other = (MovtoCajas) object;
        if ((this.iDMovtoCajas == null && other.iDMovtoCajas != null) || (this.iDMovtoCajas != null && !this.iDMovtoCajas.equals(other.iDMovtoCajas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.MovtoCajas[ iDMovtoCajas=" + iDMovtoCajas + " ]";
    }
    
}
