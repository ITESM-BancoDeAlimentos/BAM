/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "EncSocioNutricia_Fam")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncSocioNutriciaFam.findAll", query = "SELECT e FROM EncSocioNutriciaFam e"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByExpediente", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.encSocioNutriciaFamPK.expediente = :expediente"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByAreaGeo", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.encSocioNutriciaFamPK.areaGeo = :areaGeo"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP1R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p1R = :p1R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP2R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p2R = :p2R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP3R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p3R = :p3R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP4NumCuartos", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p4NumCuartos = :p4NumCuartos"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP5R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p5R = :p5R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP6R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p6R = :p6R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP7R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p7R = :p7R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP8R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p8R = :p8R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP9R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p9R = :p9R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP10R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p10R = :p10R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP11R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p11R = :p11R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP12R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p12R = :p12R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP13R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p13R = :p13R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP14R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p14R = :p14R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP15ComercioInformal", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p15ComercioInformal = :p15ComercioInformal"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP16ServDomesticos", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p16ServDomesticos = :p16ServDomesticos"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP17ApoyoFederal", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p17ApoyoFederal = :p17ApoyoFederal"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP18Otros", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p18Otros = :p18Otros"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP19Alimentacion", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p19Alimentacion = :p19Alimentacion"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP20Luz", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p20Luz = :p20Luz"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP21Agua", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p21Agua = :p21Agua"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP22Gas", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p22Gas = :p22Gas"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP23Otros", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p23Otros = :p23Otros"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP24R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p24R = :p24R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP25R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p25R = :p25R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP26NoBasicos", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p26NoBasicos = :p26NoBasicos"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP27NoConsHumano", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p27NoConsHumano = :p27NoConsHumano"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP28DestinoAgricola", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p28DestinoAgricola = :p28DestinoAgricola"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP29R", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p29R = :p29R"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP30Ganado", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p30Ganado = :p30Ganado"),
    @NamedQuery(name = "EncSocioNutriciaFam.findByP31DestinoGanado", query = "SELECT e FROM EncSocioNutriciaFam e WHERE e.p31DestinoGanado = :p31DestinoGanado")})
public class EncSocioNutriciaFam implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EncSocioNutriciaFamPK encSocioNutriciaFamPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P1_R")
    private int p1R;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P2_R")
    private int p2R;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P3_R")
    private int p3R;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P5_R")
    private boolean p5R;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P6_R")
    private int p6R;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P7_R")
    private int p7R;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P8_R")
    private int p8R;
    @Column(name = "P9_R")
    private Integer p9R;
    @Size(max = 30)
    @Column(name = "P9_Otro")
    private String p9Otro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P10_R")
    private int p10R;
    @Basic(optional = false)
    @NotNull()
    @Column(name = "P11_R")
    private int p11R;
    @Column(name = "P12_R")
    private Integer p12R;
    @Size(max = 30)
    @Column(name = "P12_Otro")
    private String p12Otro;
    @Basic(optional = false)
    @NotNull()
    @Column(name = "P13_R")
    private int p13R;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P14_R")
    private boolean p14R;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "P15_ComercioInformal")
    private BigDecimal p15ComercioInformal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "P16_ServDomesticos")
    private BigDecimal p16ServDomesticos;
    @Column(name = "P17_ApoyoFederal")
    private BigDecimal p17ApoyoFederal;
    @Column(name = "P18_Otros")
    private BigDecimal p18Otros;
    @Size(max = 30)
    @Column(name = "P18_OtrosString")
    private String p18OtrosString;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P19_Alimentacion")
    private BigDecimal p19Alimentacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P20_Luz")
    private BigDecimal p20Luz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P21_Agua")
    private BigDecimal p21Agua;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P22_Gas")
    private BigDecimal p22Gas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P23_Otros")
    private BigDecimal p23Otros;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P24_R")
    private boolean p24R;
    @Column(name = "P25_R")
    private Integer p25R;
    @Column(name = "P28_DestinoAgricola")
    private Integer p28DestinoAgricola;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P29_R")
    private boolean p29R;
    @Column(name = "P30_Ganado")
    private Integer p30Ganado;
    @Size(max = 30)
    @Column(name = "P30_GanadoOtro")
    private String p30GanadoOtro;
    @Column(name = "P31_DestinoGanado")
    private Integer p31DestinoGanado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "P4_NumCuartos")
    private int p4NumCuartos;
    @Size(max = 30)
    @Column(name = "P26_NoBasicos")
    private String p26NoBasicos;
    @Size(max = 30)
    @Column(name = "P27_NoConsHumano")
    private String p27NoConsHumano;
    @JoinColumns({
        @JoinColumn(name = "Expediente", referencedColumnName = "Expediente", insertable = false, updatable = false),
        @JoinColumn(name = "AreaGeo", referencedColumnName = "AreaGeo", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private EncSocioNutricia encSocioNutricia;

    public EncSocioNutriciaFam() {
    }

    public EncSocioNutriciaFam(EncSocioNutriciaFamPK encSocioNutriciaFamPK) {
        this.encSocioNutriciaFamPK = encSocioNutriciaFamPK;
    }

    public EncSocioNutriciaFam(int expediente, char areaGeo) {
        this.encSocioNutriciaFamPK = new EncSocioNutriciaFamPK(expediente, areaGeo);
    }

    public EncSocioNutriciaFamPK getEncSocioNutriciaFamPK() {
        return encSocioNutriciaFamPK;
    }

    public void setEncSocioNutriciaFamPK(EncSocioNutriciaFamPK encSocioNutriciaFamPK) {
        this.encSocioNutriciaFamPK = encSocioNutriciaFamPK;
    }

    public EncSocioNutricia getEncSocioNutricia() {
        return encSocioNutricia;
    }

    public void setEncSocioNutricia(EncSocioNutricia encSocioNutricia) {
        this.encSocioNutricia = encSocioNutricia;
    }

    public int getP1R() {
        return p1R;
    }

    public void setP1R(int p1R) {
        this.p1R = p1R;
    }

    public int getP2R() {
        return p2R;
    }

    public void setP2R(int p2R) {
        this.p2R = p2R;
    }

    public int getP3R() {
        return p3R;
    }

    public void setP3R(int p3R) {
        this.p3R = p3R;
    }

    public void setP5R(boolean p5R) {
        this.p5R = p5R;
    }

    public int getP6R() {
        return p6R;
    }

    public void setP6R(int p6R) {
        this.p6R = p6R;
    }

    public int getP7R() {
        return p7R;
    }

    public void setP7R(int p7R) {
        this.p7R = p7R;
    }

    public int getP8R() {
        return p8R;
    }

    public void setP8R(int p8R) {
        this.p8R = p8R;
    }

    public Integer getP9R() {
        return p9R;
    }

    public void setP9R(Integer p9R) {
        this.p9R = p9R;
    }

    public String getP9Otro() {
        if (p9Otro == null) {
            p9Otro = "";
        }
        return p9Otro;
    }

    public void setP9Otro(String p9Otro) {
        this.p9Otro = p9Otro;
    }

    public int getP10R() {
        return p10R;
    }

    public void setP10R(int p10R) {
        this.p10R = p10R;
    }

    public int getP11R() {
        return p11R;
    }

    public void setP11R(int p11R) {
        this.p11R = p11R;
    }

    public Integer getP12R() {
        return p12R;
    }

    public void setP12R(Integer p12R) {
        this.p12R = p12R;
    }

    public String getP12Otro() {
        if (p12Otro == null) {
            p12Otro = "";
        }
        return p12Otro;
    }

    public void setP12Otro(String p12Otro) {
        this.p12Otro = p12Otro;
    }

    public int getP13R() {
        return p13R;
    }

    public void setP13R(int p13R) {
        this.p13R = p13R;
    }

    public void setP14R(boolean p14R) {
        this.p14R = p14R;
    }

    public BigDecimal getP15ComercioInformal() {
        if (p15ComercioInformal == null) {
            p15ComercioInformal = new BigDecimal(0);
        }
        return p15ComercioInformal;
    }

    public void setP15ComercioInformal(BigDecimal p15ComercioInformal) {
        this.p15ComercioInformal = p15ComercioInformal;
    }

    public BigDecimal getP16ServDomesticos() {
        if (p16ServDomesticos == null) {
             p16ServDomesticos = new BigDecimal(0);
        }
        return p16ServDomesticos;
    }

    public void setP16ServDomesticos(BigDecimal p16ServDomesticos) {
        this.p16ServDomesticos = p16ServDomesticos;
    }

    public BigDecimal getP17ApoyoFederal() {
        if (p17ApoyoFederal == null) {
            p17ApoyoFederal = new BigDecimal(0);
        }
        return p17ApoyoFederal;
    }

    public void setP17ApoyoFederal(BigDecimal p17ApoyoFederal) {
        this.p17ApoyoFederal = p17ApoyoFederal;
    }

    public BigDecimal getP18Otros() {
        if (p18Otros == null) {
             p18Otros = new BigDecimal(0);
        }
        return p18Otros;
    }

    public void setP18Otros(BigDecimal p18Otros) {
        this.p18Otros = p18Otros;
    }

    public String getP18OtrosString() {
        if (p18OtrosString == null) {
            p18OtrosString = "";
        }
        return p18OtrosString;
    }

    public void setP18OtrosString(String p18OtrosString) {
        this.p18OtrosString = p18OtrosString;
    }

    public BigDecimal getP19Alimentacion() {
        if (p19Alimentacion == null) {
            p19Alimentacion = new BigDecimal(0);
        }
        return p19Alimentacion;
    }

    public void setP19Alimentacion(BigDecimal p19Alimentacion) {
        this.p19Alimentacion = p19Alimentacion;
    }

    public BigDecimal getP20Luz() {
        if (p20Luz == null) {
             p20Luz = new BigDecimal(0);
        }
        return p20Luz;
    }

    public void setP20Luz(BigDecimal p20Luz) {
        this.p20Luz = p20Luz;
    }

    public BigDecimal getP21Agua() {
        if (p21Agua == null) {
            p21Agua = new BigDecimal(0);
        }
        return p21Agua;
    }

    public void setP21Agua(BigDecimal p21Agua) {
        this.p21Agua = p21Agua;
    }

    public BigDecimal getP22Gas() {
        if (p22Gas == null) {
            p22Gas = new BigDecimal(0);
        }
        return p22Gas;
    }

    public void setP22Gas(BigDecimal p22Gas) {
        this.p22Gas = p22Gas;
    }

    public BigDecimal getP23Otros() {
        if (p23Otros == null) {
            p23Otros = new BigDecimal(0);
        }
        return p23Otros;
    }

    public void setP23Otros(BigDecimal p23Otros) {
        this.p23Otros = p23Otros;
    }

    public void setP24R(boolean p24R) {
        this.p24R = p24R;
    }

    public Integer getP25R() {
        return p25R;
    }

    public void setP25R(Integer p25R) {
        this.p25R = p25R;
    }

    public Integer getP28DestinoAgricola() {
        return p28DestinoAgricola;
    }

    public void setP28DestinoAgricola(Integer p28DestinoAgricola) {
        this.p28DestinoAgricola = p28DestinoAgricola;
    }

    public void setP29R(boolean p29R) {
        this.p29R = p29R;
    }

    public Integer getP30Ganado() {
        return p30Ganado;
    }

    public void setP30Ganado(Integer p30Ganado) {
        this.p30Ganado = p30Ganado;
    }

    public String getP30GanadoOtro() {
        if (p30GanadoOtro == null) {
            p30GanadoOtro = "";
        }
        return p30GanadoOtro;
    }

    public void setP30GanadoOtro(String p30GanadoOtro) {
        this.p30GanadoOtro = p30GanadoOtro;
    }

    public Integer getP31DestinoGanado() {
        return p31DestinoGanado;
    }

    public void setP31DestinoGanado(Integer p31DestinoGanado) {
        this.p31DestinoGanado = p31DestinoGanado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (encSocioNutriciaFamPK != null ? encSocioNutriciaFamPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncSocioNutriciaFam)) {
            return false;
        }
        EncSocioNutriciaFam other = (EncSocioNutriciaFam) object;
        if ((this.encSocioNutriciaFamPK == null && other.encSocioNutriciaFamPK != null) || (this.encSocioNutriciaFamPK != null && !this.encSocioNutriciaFamPK.equals(other.encSocioNutriciaFamPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.EncSocioNutriciaFam[ encSocioNutriciaFamPK=" + encSocioNutriciaFamPK + " ]";
    }

    public boolean isP5R() {
        return p5R;
    }

    public boolean isP14R() {
        return p14R;
    }

    public boolean isP24R() {
        return p24R;
    }

    public boolean isP29R() {
        return p29R;
    }

    public int getP4NumCuartos() {
        return p4NumCuartos;
    }

    public void setP4NumCuartos(int p4NumCuartos) {
        this.p4NumCuartos = p4NumCuartos;
    }

    public String getP26NoBasicos() {
        if (p26NoBasicos == null) {
            p26NoBasicos = "";
        }
        return p26NoBasicos;
    }

    public void setP26NoBasicos(String p26NoBasicos) {
        this.p26NoBasicos = p26NoBasicos;
    }

    public String getP27NoConsHumano() {
        if (p27NoConsHumano == null) {
            p27NoConsHumano = "";
        }
        return p27NoConsHumano;
    }

    public void setP27NoConsHumano(String p27NoConsHumano) {
        this.p27NoConsHumano = p27NoConsHumano;
    }
}
