/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "EncSocioNutricia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncSocioNutricia.findAll", query = "SELECT e FROM EncSocioNutricia e"),
    @NamedQuery(name = "EncSocioNutricia.findByExpediente", query = "SELECT e FROM EncSocioNutricia e WHERE e.encSocioNutriciaPK.expediente = :expediente"),
    @NamedQuery(name = "EncSocioNutricia.findLike",
            query = "SELECT e.encSocioNutriciaPK.expediente FROM EncSocioNutricia e WHERE e.encSocioNutriciaPK.expediente LIKE :expediente AND e.encSocioNutriciaPK.areaGeo = :areaGeo AND e.status = :status"),
    @NamedQuery(name = "EncSocioNutricia.findLastExpByArea",
            query = "SELECT e.encSocioNutriciaPK.expediente FROM EncSocioNutricia e WHERE e.encSocioNutriciaPK.areaGeo = :areaGeo ORDER BY e.encSocioNutriciaPK.expediente DESC"),
    @NamedQuery(name = "EncSocioNutricia.findByAreaGeo", query = "SELECT e FROM EncSocioNutricia e WHERE e.encSocioNutriciaPK.areaGeo = :areaGeo"),
    @NamedQuery(name = "EncSocioNutricia.findInstitucionActivo", query = "SELECT DISTINCT e.iDInstitucion        FROM EncSocioNutricia e WHERE e.iDInstitucion.activo = true AND e.status = true"),
    @NamedQuery(name = "EncSocioNutricia.findInstitucionLike",   query = "SELECT DISTINCT e.iDInstitucion        FROM EncSocioNutricia e WHERE e.iDInstitucion.activo = true AND e.status = true AND (e.iDInstitucion.iDInstitucion LIKE :query OR e.iDInstitucion.institucion LIKE :query)"),
    @NamedQuery(name = "EncSocioNutricia.findCiudadActivo",      query = "SELECT DISTINCT e.iDInstitucion.ciudad FROM EncSocioNutricia e WHERE e.iDInstitucion.activo = true AND e.status = true"),
    @NamedQuery(name = "EncSocioNutricia.findCiudadLike",        query = "SELECT DISTINCT e.iDInstitucion.ciudad FROM EncSocioNutricia e WHERE e.iDInstitucion.activo = true AND e.status = true AND (e.iDInstitucion.ciudad LIKE :query)"),
    @NamedQuery(name = "EncSocioNutricia.findInstitucionByAreaGeo",     query = "SELECT DISTINCT e.iDInstitucion FROM EncSocioNutricia e WHERE e.iDInstitucion.activo = :activo AND e.status = :status AND e.encSocioNutriciaPK.areaGeo = :areaGeo"),
    @NamedQuery(name = "EncSocioNutricia.findInstitucionByAreaGeoLike", query = "SELECT DISTINCT e.iDInstitucion FROM EncSocioNutricia e WHERE e.iDInstitucion.activo = :activo AND e.status = :status AND e.encSocioNutriciaPK.areaGeo = :areaGeo AND (e.iDInstitucion.iDInstitucion LIKE :query OR e.iDInstitucion.institucion LIKE :query)"),
    @NamedQuery(name = "EncSocioNutricia.findByFechaCaptura", query = "SELECT e FROM EncSocioNutricia e WHERE e.fechaCaptura = :fechaCaptura"),
    @NamedQuery(name = "EncSocioNutricia.findByFechaLev", query = "SELECT e FROM EncSocioNutricia e WHERE e.fechaLev = :fechaLev"),
    @NamedQuery(name = "EncSocioNutricia.findByUnidad", query = "SELECT e FROM EncSocioNutricia e WHERE e.unidad = :unidad"),
    @NamedQuery(name = "EncSocioNutricia.findByJefeFamilia", query = "SELECT e FROM EncSocioNutricia e WHERE e.jefeFamilia = :jefeFamilia"),
    @NamedQuery(name = "EncSocioNutricia.findByEntrevistado", query = "SELECT e FROM EncSocioNutricia e WHERE e.entrevistado = :entrevistado"),
    @NamedQuery(name = "EncSocioNutricia.findByIdStatus", query = "SELECT e FROM EncSocioNutricia e WHERE e.encSocioNutriciaPK = :id AND e.status = :status"),
    @NamedQuery(name = "EncSocioNutricia.findByInstitucionAndAreaGeo", query = "SELECT e FROM EncSocioNutricia e WHERE e.iDInstitucion = :iDInstitucion AND e.encSocioNutriciaPK.areaGeo = :areaGeo AND e.status = :status"),
    @NamedQuery(name = "EncSocioNutricia.findByInstitucion", query = "SELECT e FROM EncSocioNutricia e WHERE e.iDInstitucion = :iDInstitucion"),
    @NamedQuery(name = "EncSocioNutricia.findByStatus", query = "SELECT e FROM EncSocioNutricia e WHERE e.status = :status AND e.encSocioNutriciaPK.expediente > 0")})
public class EncSocioNutricia implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EncSocioNutriciaPK encSocioNutriciaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FechaCaptura")
    @Temporal(TemporalType.DATE)
    private Date fechaCaptura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FechaLev")
    @Temporal(TemporalType.DATE)
    private Date fechaLev;
    @JoinColumn(name = "IDInstitucion", referencedColumnName = "IDInstitucion")
    @ManyToOne(optional = false)
    private Instituciones iDInstitucion;
    @Size(max = 20)
    @Column(name = "Unidad")
    private String unidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "JefeFamilia")
    private String jefeFamilia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "Entrevistado")
    private String entrevistado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Status")
    private boolean status;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "encSocioNutricia")
    private EncSocioNutriciaFam encSocioNutriciaFam;
    @JoinColumn(name = "Identificador", referencedColumnName = "IDUsuario")
    @ManyToOne(optional = false)
    private Usuarios identificador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encSocioNutricia")
    private Collection<EncSocioNutriciaInd> encSocioNutriciaIndCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "encSocioNutricia")
    private EncSocioNutriciaSeg encSocioNutriciaSeg;

    public EncSocioNutricia() {
    }

    public EncSocioNutricia(EncSocioNutriciaPK encSocioNutriciaPK) {
        this.encSocioNutriciaPK = encSocioNutriciaPK;
    }

    public EncSocioNutricia(EncSocioNutriciaPK encSocioNutriciaPK, Date fechaCaptura, Date fechaLev, String jefeFamilia, String entrevistado, boolean status, Instituciones iDInstitucion) {
        this.encSocioNutriciaPK = encSocioNutriciaPK;
        this.fechaCaptura = fechaCaptura;
        this.fechaLev = fechaLev;
        this.iDInstitucion = iDInstitucion;
        this.jefeFamilia = jefeFamilia;
        this.entrevistado = entrevistado;
        this.status = status;
    }

    public EncSocioNutricia(int expediente, char areaGeo) {
        this.encSocioNutriciaPK = new EncSocioNutriciaPK(expediente, areaGeo);
    }

    public EncSocioNutriciaPK getEncSocioNutriciaPK() {
        return encSocioNutriciaPK;
    }

    public void setEncSocioNutriciaPK(EncSocioNutriciaPK encSocioNutriciaPK) {
        this.encSocioNutriciaPK = encSocioNutriciaPK;
    }

    public Date getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    public Date getFechaLev() {
        return fechaLev;
    }

    public void setFechaLev(Date fechaLev) {
        this.fechaLev = fechaLev;
    }

    public Instituciones getIDInstitucion() {
        if (iDInstitucion == null) {
            iDInstitucion = new Instituciones();
        }
        return iDInstitucion;
    }

    public void setIDInstitucion(Instituciones iDInstitucion) {
        this.iDInstitucion = iDInstitucion;
    }

    public String getDomicilio() {
        return getIDInstitucion().getDireccion();
    }

    public String getLocalidad() {
        return getIDInstitucion().getInstitucion();
    }

    public String getMunicipio() {
        return getIDInstitucion().getCiudad();
    }

    public String getEstado() {
        return getIDInstitucion().getEstado();
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad.toUpperCase();
    }

    public String getJefeFamilia() {
        return jefeFamilia;
    }

    public void setJefeFamilia(String jefeFamilia) {
        this.jefeFamilia = jefeFamilia.toUpperCase();
    }

    public String getEntrevistado() {
        return entrevistado;
    }

    public void setEntrevistado(String entrevistado) {
        this.entrevistado = entrevistado.toUpperCase();
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public EncSocioNutriciaFam getEncSocioNutriciaFam() {
        return encSocioNutriciaFam;
    }

    public void setEncSocioNutriciaFam(EncSocioNutriciaFam encSocioNutriciaFam) {
        this.encSocioNutriciaFam = encSocioNutriciaFam;
    }

    public Usuarios getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Usuarios identificador) {
        this.identificador = identificador;
    }

    @XmlTransient
    public Collection<EncSocioNutriciaInd> getEncSocioNutriciaIndCollection() {
        return encSocioNutriciaIndCollection;
    }

    public void setEncSocioNutriciaIndCollection(Collection<EncSocioNutriciaInd> encSocioNutriciaIndCollection) {
        this.encSocioNutriciaIndCollection = encSocioNutriciaIndCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (encSocioNutriciaPK != null ? encSocioNutriciaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncSocioNutricia)) {
            return false;
        }

        EncSocioNutricia other = (EncSocioNutricia) object;
        if ((this.encSocioNutriciaPK == null && other.encSocioNutriciaPK != null)
                || (this.encSocioNutriciaPK != null
                && !this.encSocioNutriciaPK.equals(other.encSocioNutriciaPK))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return encSocioNutriciaPK.toString();
    }

    public EncSocioNutriciaSeg getEncSocioNutriciaSeg() {
        return encSocioNutriciaSeg;
    }

    public void setEncSocioNutriciaSeg(EncSocioNutriciaSeg encSocioNutriciaSeg) {
        this.encSocioNutriciaSeg = encSocioNutriciaSeg;
    }
}
