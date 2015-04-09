/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "EncSocioNutricia_Ind")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncSocioNutriciaInd.findAll", query = "SELECT e FROM EncSocioNutriciaInd e"),
    @NamedQuery(name = "EncSocioNutriciaInd.findAllActivo", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.encSocioNutricia.status = true"),
    @NamedQuery(name = "EncSocioNutriciaInd.countAllActivo", query = "SELECT COUNT(e) FROM EncSocioNutriciaInd e WHERE e.encSocioNutricia.status = true"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByExpedienteArea", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.encSocioNutriciaIndPK.expediente = :expediente AND e.encSocioNutriciaIndPK.areaGeo = :areaGeo"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByExpedienteAreaParentesco", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.encSocioNutriciaIndPK.expediente = :expediente AND e.encSocioNutriciaIndPK.areaGeo = :areaGeo AND e.parentesco = :parentesco"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByExpedienteAreaParentescoActivo", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.encSocioNutriciaIndPK.expediente = :expediente AND e.encSocioNutriciaIndPK.areaGeo = :areaGeo AND e.parentesco = :parentesco AND e.encSocioNutricia.status = true"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByExpediente", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.encSocioNutriciaIndPK.expediente = :expediente"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByAreaGeo", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.encSocioNutriciaIndPK.areaGeo = :areaGeo"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByNombre", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.encSocioNutriciaIndPK.nombre = :nombre"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByParentesco", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.parentesco = :parentesco"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByCurp", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.curp = :curp"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByFechaNacimiento", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByGenero", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.genero = :genero"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByPeso", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.peso = :peso"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByTalla", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.talla = :talla"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByEstadoCivil", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.estadoCivil = :estadoCivil"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByEscolaridad", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.escolaridad = :escolaridad"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByCondLaboral", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.condLaboral = :condLaboral"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByRamaActiv", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.ramaActiv = :ramaActiv"),
    @NamedQuery(name = "EncSocioNutriciaInd.findBySalario", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.salario = :salario"),
    @NamedQuery(name = "EncSocioNutriciaInd.findBySeguroSocial", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.seguroSocial = :seguroSocial"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByProbSalud", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.probSalud = :probSalud"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByAyudaAlim", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.ayudaAlim = :ayudaAlim"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByEtapaEstFis", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.etapaEstFis = :etapaEstFis"),
    @NamedQuery(name = "EncSocioNutriciaInd.findBySemEmbarazo", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.semEmbarazo = :semEmbarazo"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByPesoPreg", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.pesoPreg = :pesoPreg"),
    @NamedQuery(name = "EncSocioNutriciaInd.findByGrupoEtnico", query = "SELECT e FROM EncSocioNutriciaInd e WHERE e.grupoEtnico = :grupoEtnico")})
public class EncSocioNutriciaInd implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EncSocioNutriciaIndPK encSocioNutriciaIndPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 21)
    @Column(name = "Parentesco")
    private String parentesco;
    @Size(min = 0, max = 20)
    @Column(name = "CURP")
    private String curp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FechaNacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "Genero")
    private String genero;
    @Basic(optional = false)
    @Min(value = 1)
    @Max(value = 300)
    @NotNull
    @Column(name = "Peso")
    private double peso;
    @Basic(optional = false)
    @Min(value = 1)
    @Max(value = 300)
    @NotNull
    @Column(name = "Talla")
    private double talla;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "EstadoCivil")
    private String estadoCivil;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "Escolaridad")
    private String escolaridad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CondLaboral")
    private String condLaboral;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 31)
    @Column(name = "RamaActiv")
    private String ramaActiv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Salario")
    private double salario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 31)
    @Column(name = "SeguroSocial")
    private String seguroSocial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "ProbSalud")
    private String probSalud;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 51)
    @Column(name = "AyudaAlim")
    private String ayudaAlim;
    @Size(max = 30)
    @Column(name = "EtapaEstFis")
    private String etapaEstFis;
    @Column(name = "SemEmbarazo")
    private Integer semEmbarazo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PesoPreg")
    private Double pesoPreg;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 21)
    @Column(name = "GrupoEtnico")
    private String grupoEtnico;
    @JoinColumns({
        @JoinColumn(name = "Expediente", referencedColumnName = "Expediente", insertable = false, updatable = false),
        @JoinColumn(name = "AreaGeo", referencedColumnName = "AreaGeo", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private EncSocioNutricia encSocioNutricia;

    public EncSocioNutriciaInd() {
    }

    public EncSocioNutriciaInd(EncSocioNutriciaIndPK encSocioNutriciaIndPK) {
        this.encSocioNutriciaIndPK = encSocioNutriciaIndPK;
    }

    public EncSocioNutriciaInd(EncSocioNutriciaIndPK encSocioNutriciaIndPK, String parentesco, String curp, Date fechaNacimiento, String genero, double peso, double talla, String estadoCivil, String escolaridad, String condLaboral, String ramaActiv, double salario, String seguroSocial, String probSalud, String ayudaAlim, String grupoEtnico) {
        this.encSocioNutriciaIndPK = encSocioNutriciaIndPK;
        this.parentesco = parentesco;
        this.curp = curp;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.peso = peso;
        this.talla = talla;
        this.estadoCivil = estadoCivil;
        this.escolaridad = escolaridad;
        this.condLaboral = condLaboral;
        this.ramaActiv = ramaActiv;
        this.salario = salario;
        this.seguroSocial = seguroSocial;
        this.probSalud = probSalud;
        this.ayudaAlim = ayudaAlim;
        this.grupoEtnico = grupoEtnico;
    }

    public EncSocioNutriciaInd(int expediente, char areaGeo, String nombre) {
        this.encSocioNutriciaIndPK = new EncSocioNutriciaIndPK(expediente, areaGeo, nombre);
    }

    public EncSocioNutriciaIndPK getEncSocioNutriciaIndPK() {
        return encSocioNutriciaIndPK;
    }

    public void setEncSocioNutriciaIndPK(EncSocioNutriciaIndPK encSocioNutriciaIndPK) {
        this.encSocioNutriciaIndPK = encSocioNutriciaIndPK;
    }

    public String getParentesco() {
        if (parentesco == null) {
            parentesco = "";
        }
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getCurp() {
        if (curp == null) {
            curp = "";
        }
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp.toUpperCase();
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getTalla() {
        return talla;
    }

    public void setTalla(double talla) {
        this.talla = talla;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEscolaridad() {
        return escolaridad;
    }

    public void setEscolaridad(String escolaridad) {
        this.escolaridad = escolaridad;
    }

    public String getCondLaboral() {
        return condLaboral;
    }

    public void setCondLaboral(String condLaboral) {
        this.condLaboral = condLaboral;
    }

    public String getRamaActiv() {
        return ramaActiv;
    }

    public void setRamaActiv(String ramaActiv) {
        this.ramaActiv = ramaActiv;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getSeguroSocial() {
        return seguroSocial;
    }

    public void setSeguroSocial(String seguroSocial) {
        this.seguroSocial = seguroSocial;
    }

    public String getProbSalud() {
        return probSalud;
    }

    public void setProbSalud(String probSalud) {
        this.probSalud = probSalud;
    }

    public String getAyudaAlim() {
        return ayudaAlim;
    }

    public void setAyudaAlim(String ayudaAlim) {
        this.ayudaAlim = ayudaAlim;
    }

    public String getEtapaEstFis() {
        return etapaEstFis;
    }

    public void setEtapaEstFis(String etapaEstFis) {
        this.etapaEstFis = etapaEstFis;
    }

    public Integer getSemEmbarazo() {
        if (semEmbarazo == null) {
            semEmbarazo = 0;
        }
        return semEmbarazo;
    }

    public void setSemEmbarazo(Integer semEmbarazo) {
        this.semEmbarazo = semEmbarazo;
    }

    public Double getPesoPreg() {
        if (pesoPreg == null) {
            pesoPreg = 0.0;
        }
        return pesoPreg;
    }

    public void setPesoPreg(Double pesoPreg) {
        this.pesoPreg = pesoPreg;
    }

    public String getGrupoEtnico() {
        return grupoEtnico;
    }

    public void setGrupoEtnico(String grupoEtnico) {
        this.grupoEtnico = grupoEtnico;
    }

    public EncSocioNutricia getEncSocioNutricia() {
        return encSocioNutricia;
    }

    public void setEncSocioNutricia(EncSocioNutricia encSocioNutricia) {
        this.encSocioNutricia = encSocioNutricia;
    }

    /**
     * Retorna la edad actual en años. getMeses() da los meses restantes.
     *
     * @return int con la edad actual del integrante en años
     * @see #getMeses()
     */
    public int getAnios() {
        int anios;
        int diaAnio;
        Calendar fechaHoy = Calendar.getInstance();
        Calendar fechaNac = new GregorianCalendar();
        fechaNac.setTime(getFechaNacimiento());

        anios = fechaHoy.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
        diaAnio = fechaHoy.get(Calendar.DAY_OF_YEAR) - fechaNac.get(Calendar.DAY_OF_YEAR);

        if (diaAnio < 0) {
            anios--;
        }

        return anios;
    }

    /**
     * Junto con getAnios(), retorna la edad del integrante.
     *
     * @return Meses restantes para la edad actual.
     * @see #getAnios()
     */
    public int getMeses() {
        int meses;
        int diaMes;

        Calendar fechaHoy = Calendar.getInstance();
        Calendar fechaNac = new GregorianCalendar();
        fechaNac.setTime(getFechaNacimiento());

        meses = fechaHoy.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH);
        diaMes = fechaHoy.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);

        if (diaMes < 0) {
            meses--;
        }

        if (meses < 0) {
            meses += 12;
        }

        return meses;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (encSocioNutriciaIndPK != null ? encSocioNutriciaIndPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncSocioNutriciaInd)) {
            return false;
        }
        EncSocioNutriciaInd other = (EncSocioNutriciaInd) object;
        if ((this.encSocioNutriciaIndPK == null && other.encSocioNutriciaIndPK != null) || (this.encSocioNutriciaIndPK != null && !this.encSocioNutriciaIndPK.equals(other.encSocioNutriciaIndPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.EncSocioNutriciaInd[ encSocioNutriciaIndPK=" + encSocioNutriciaIndPK + " ]";
    }
}
