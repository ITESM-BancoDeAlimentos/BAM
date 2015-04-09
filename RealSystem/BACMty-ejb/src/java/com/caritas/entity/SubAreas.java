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
@Table(name = "SubAreas", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubAreas.findAll", query = "SELECT s FROM SubAreas s"),
    @NamedQuery(name = "SubAreas.findByIDSubArea", query = "SELECT s FROM SubAreas s WHERE s.iDSubArea = :iDSubArea"),
    @NamedQuery(name = "SubAreas.findBySubArea", query = "SELECT s FROM SubAreas s WHERE s.subArea = :subArea")})
public class SubAreas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "IDSubArea", nullable = false, length = 10)
    private String iDSubArea;
    @Size(max = 50)
    @Column(name = "SubArea", length = 50)
    private String subArea;
    @JoinColumn(name = "IDArea", referencedColumnName = "IDArea")
    @ManyToOne
    private Areas iDArea;
    @OneToMany(mappedBy = "iDSubArea")
    private Collection<Instituciones> institucionesCollection;

    public SubAreas() {
    }

    public SubAreas(String iDSubArea) {
        this.iDSubArea = iDSubArea;
    }

    public String getIDSubArea() {
        return iDSubArea;
    }

    public void setIDSubArea(String iDSubArea) {
        this.iDSubArea = iDSubArea;
    }

    public String getSubArea() {
        return subArea;
    }

    public void setSubArea(String subArea) {
        this.subArea = subArea;
    }

    public Areas getIDArea() {
        return iDArea;
    }

    public void setIDArea(Areas iDArea) {
        this.iDArea = iDArea;
    }

    @XmlTransient
    public Collection<Instituciones> getInstitucionesCollection() {
        return institucionesCollection;
    }

    public void setInstitucionesCollection(Collection<Instituciones> institucionesCollection) {
        this.institucionesCollection = institucionesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDSubArea != null ? iDSubArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubAreas)) {
            return false;
        }
        SubAreas other = (SubAreas) object;
        if ((this.iDSubArea == null && other.iDSubArea != null) || (this.iDSubArea != null && !this.iDSubArea.equals(other.iDSubArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.SubAreas[ iDSubArea=" + iDSubArea + " ]";
    }
    
}
