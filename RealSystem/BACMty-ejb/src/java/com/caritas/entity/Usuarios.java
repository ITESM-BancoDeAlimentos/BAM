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
@Table(name = "Usuarios", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByIDUsuario", query = "SELECT u FROM Usuarios u WHERE u.iDUsuario = :iDUsuario"),
    @NamedQuery(name = "Usuarios.findByNombre", query = "SELECT u FROM Usuarios u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuarios.findByPassword", query = "SELECT u FROM Usuarios u WHERE u.password = :password"),
    @NamedQuery(name = "Usuarios.findByIDZona", query = "SELECT u FROM Usuarios u WHERE u.iDZona = :iDZona")})
public class Usuarios implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "identificador")
    private Collection<EncSocioNutricia> encSocioNutriciaCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "IDUsuario", nullable = false, length = 15)
    private String iDUsuario;
    @Size(max = 50)
    @Column(name = "nombre", length = 50)
    private String nombre;
    @Size(max = 15)
    @Column(name = "password", length = 15)
    private String password;
    @Size(max = 7)
    @Column(name = "IDZona", length = 7)
    private String iDZona;
    @JoinColumn(name = "IDNivel", referencedColumnName = "IDNivel")
    @ManyToOne
    private NivelAcceso iDNivel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDUsuario")
    private Collection<AccesoPrograma> accesoProgramaCollection;
    @OneToMany(mappedBy = "iDUsuario")
    private Collection<Movimientos> movimientosCollection;
    @OneToMany(mappedBy = "iDUsuario")
    private Collection<TmpMovtoArt> tmpMovtoArtCollection;
    @OneToMany(mappedBy = "iDUsuario")
    private Collection<Recibo> reciboCollection;

    public Usuarios() {
    }

    public Usuarios(String iDUsuario) {
        this.iDUsuario = iDUsuario;
    }

    public String getIDUsuario() {
        return iDUsuario;
    }

    public void setIDUsuario(String iDUsuario) {
        this.iDUsuario = iDUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIDZona() {
        return iDZona;
    }

    public void setIDZona(String iDZona) {
        this.iDZona = iDZona;
    }

    public NivelAcceso getIDNivel() {
        return iDNivel;
    }

    public void setIDNivel(NivelAcceso iDNivel) {
        this.iDNivel = iDNivel;
    }

    @XmlTransient
    public Collection<AccesoPrograma> getAccesoProgramaCollection() {
        return accesoProgramaCollection;
    }

    @XmlTransient
    public Collection<Recibo> getReciboCollection() {
        return reciboCollection;
    }

    public void setAccesoProgramaCollection(Collection<AccesoPrograma> accesoProgramaCollection) {
        this.accesoProgramaCollection = accesoProgramaCollection;
    }

    public void setReciboCollection(Collection<Recibo> reciboCollection) {
        this.reciboCollection = reciboCollection;
    }

    @XmlTransient
    public Collection<Movimientos> getMovimientosCollection() {
        return movimientosCollection;
    }

    public void setMovimientosCollection(Collection<Movimientos> movimientosCollection) {
        this.movimientosCollection = movimientosCollection;
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
        hash += (iDUsuario != null ? iDUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.iDUsuario == null && other.iDUsuario != null) || (this.iDUsuario != null && !this.iDUsuario.equals(other.iDUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre();
    }

    @XmlTransient
    public Collection<EncSocioNutricia> getEncSocioNutriciaCollection() {
        return encSocioNutriciaCollection;
    }

    public void setEncSocioNutriciaCollection(Collection<EncSocioNutricia> encSocioNutriciaCollection) {
        this.encSocioNutriciaCollection = encSocioNutriciaCollection;
    }
    
}
