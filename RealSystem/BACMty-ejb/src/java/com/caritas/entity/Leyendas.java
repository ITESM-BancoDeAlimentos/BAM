/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "Leyendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Leyendas.findAll", query = "SELECT l FROM Leyendas l"),
    @NamedQuery(name = "Leyendas.findByLeyenda1", query = "SELECT l FROM Leyendas l WHERE l.leyenda1 = :leyenda1"),
    @NamedQuery(name = "Leyendas.findByLeyenda2", query = "SELECT l FROM Leyendas l WHERE l.leyenda2 = :leyenda2"),
    @NamedQuery(name = "Leyendas.findByLeyenda3", query = "SELECT l FROM Leyendas l WHERE l.leyenda3 = :leyenda3"),
    @NamedQuery(name = "Leyendas.findByIDLeyenda", query = "SELECT l FROM Leyendas l WHERE l.iDLeyenda = :iDLeyenda")})
public class Leyendas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "Leyenda1")
    private String leyenda1;
    @Size(max = 255)
    @Column(name = "Leyenda2")
    private String leyenda2;
    @Size(max = 255)
    @Column(name = "Leyenda3")
    private String leyenda3;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDLeyenda")
    private Integer iDLeyenda;

    public Leyendas() {
    }

    public Leyendas(Integer iDLeyenda) {
        this.iDLeyenda = iDLeyenda;
    }

    public String getLeyenda1() {
        return leyenda1;
    }

    public void setLeyenda1(String leyenda1) {
        this.leyenda1 = leyenda1;
    }

    public String getLeyenda2() {
        return leyenda2;
    }

    public void setLeyenda2(String leyenda2) {
        this.leyenda2 = leyenda2;
    }

    public String getLeyenda3() {
        return leyenda3;
    }

    public void setLeyenda3(String leyenda3) {
        this.leyenda3 = leyenda3;
    }

    public Integer getIDLeyenda() {
        return iDLeyenda;
    }

    public void setIDLeyenda(Integer iDLeyenda) {
        this.iDLeyenda = iDLeyenda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDLeyenda != null ? iDLeyenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Leyendas)) {
            return false;
        }
        Leyendas other = (Leyendas) object;
        if ((this.iDLeyenda == null && other.iDLeyenda != null) || (this.iDLeyenda != null && !this.iDLeyenda.equals(other.iDLeyenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.Leyendas[ iDLeyenda=" + iDLeyenda + " ]";
    }
    
}
