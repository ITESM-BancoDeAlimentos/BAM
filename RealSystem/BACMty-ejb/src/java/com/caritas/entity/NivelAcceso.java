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
@Table(name = "Nivel_Acceso", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NivelAcceso.findAll", query = "SELECT n FROM NivelAcceso n"),
    @NamedQuery(name = "NivelAcceso.findByIDNivel", query = "SELECT n FROM NivelAcceso n WHERE n.iDNivel = :iDNivel"),
    @NamedQuery(name = "NivelAcceso.findByNivel", query = "SELECT n FROM NivelAcceso n WHERE n.nivel = :nivel")})
public class NivelAcceso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "IDNivel", nullable = false, length = 4)
    private String iDNivel;
    @Size(max = 50)
    @Column(name = "Nivel", length = 50)
    private String nivel;
    @OneToMany(mappedBy = "iDNivel")
    private Collection<Usuarios> usuariosCollection;
    @OneToMany(mappedBy = "iDNivel")
    private Collection<Accesos> accesosCollection;
    
    public NivelAcceso() {
    }
    
    public NivelAcceso(String iDNivel) {
        this.iDNivel = iDNivel;
    }

    public String getIDNivel() {
        return iDNivel;
    }

    public void setIDNivel(String iDNivel) {
        this.iDNivel = iDNivel;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @XmlTransient
    public Collection<Accesos> getAccesosCollection() {
        return accesosCollection;
    }

    public void setAccesosCollection(Collection<Accesos> accesosCollection) {
        this.accesosCollection = accesosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDNivel != null ? iDNivel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NivelAcceso)) {
            return false;
        }
        NivelAcceso other = (NivelAcceso) object;
        if ((this.iDNivel == null && other.iDNivel != null) || (this.iDNivel != null && !this.iDNivel.equals(other.iDNivel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNivel();
    }
    
}
