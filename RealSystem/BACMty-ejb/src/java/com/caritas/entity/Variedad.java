/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import com.caritas.facade.RelacionRepVarFacade;
import com.caritas.util.EjbUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
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
@Table(name = "Variedad", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Variedad.findAll", query = "SELECT v FROM Variedad v"),
    @NamedQuery(name = "Variedad.findLike", query = "SELECT v FROM Variedad v WHERE v.iDVariedad LIKE :query OR v.variedad LIKE :query"),
    @NamedQuery(name = "Variedad.findVariedad", query = "SELECT v.variedad FROM Variedad v"),
    @NamedQuery(name = "Variedad.findByIDVariedad", query = "SELECT v FROM Variedad v WHERE v.iDVariedad = :iDVariedad"),
    @NamedQuery(name = "Variedad.findByVariedad", query = "SELECT v FROM Variedad v WHERE v.variedad = :variedad"),
    @NamedQuery(name = "Variedad.findByCosto", query = "SELECT v FROM Variedad v WHERE v.costo = :costo")})
public class Variedad implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient static RelacionRepVarFacade facade = null;
    @OneToMany(mappedBy = "variedad", fetch = FetchType.LAZY)
    private List<RelacionRepVar> relacionRepVarList;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "IDVariedad", nullable = false, length = 8)
    private String iDVariedad;
    @Size(max = 50)
    @Column(name = "Variedad", length = 50)
    private String variedad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Costo", precision = 53)
    private Double costo;
    @OneToMany(mappedBy = "iDVariedad")
    private Collection<Articulos> articulosCollection;
    @OneToMany(mappedBy = "iDVariedad")
    private Collection<Cajas> cajasCollection;

    public Variedad() {
    }

    public Variedad(String iDVariedad) {
        this.iDVariedad = iDVariedad;
    }

    public String getIDVariedad() {
        return iDVariedad;
    }

    public void setIDVariedad(String iDVariedad) {
        this.iDVariedad = iDVariedad;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    @XmlTransient
    public Collection<Articulos> getArticulosCollection() {
        return articulosCollection;
    }

    public void setArticulosCollection(Collection<Articulos> articulosCollection) {
        this.articulosCollection = articulosCollection;
    }

    @XmlTransient
    public Collection<Cajas> getCajasCollection() {
        return cajasCollection;
    }

    public void setCajasCollection(Collection<Cajas> cajasCollection) {
        this.cajasCollection = cajasCollection;
    }
    /**
     * El RelacionRepVar del reporte dado para esta variedad.
     * @param reporte
     * @return 
     */
    @XmlTransient
    public RelacionRepVar findRepClase(String reporte) {
        return getFacade().findByReporteVariedad(reporte, this);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDVariedad != null ? iDVariedad.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Variedad)) {
            return false;
        }
        Variedad other = (Variedad) object;
        if ((this.iDVariedad == null && other.iDVariedad != null) || (this.iDVariedad != null && !this.iDVariedad.equals(other.iDVariedad))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "com.caritas.entity.Variedad[ iDVariedad=" + iDVariedad + ";" +
                "Variedad=" + getVariedad() +" ]";
    }
    
    @XmlTransient
    public List<RelacionRepVar> getRelacionRepVarList() {
        return relacionRepVarList;
    }
    
    public void setRelacionRepVarList(List<RelacionRepVar> relacionRepVarList) {
        this.relacionRepVarList = relacionRepVarList;
    }

    public static RelacionRepVarFacade getFacade() {
        if (facade == null) {
            facade = EjbUtil.lookup(RelacionRepVarFacade.class);
        }
        return facade;
    }


    
}
