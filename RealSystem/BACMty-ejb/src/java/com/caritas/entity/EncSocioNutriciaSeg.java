/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
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

@Entity
@Table(name = "EncSocioNutricia_Seg")
@NamedQueries({
    @NamedQuery(name = "EncSocioNutriciaSeg.findAll", query = "SELECT e FROM EncSocioNutriciaSeg e")})
public class EncSocioNutriciaSeg implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EncSocioNutriciaSegPK encSocioNutriciaSegPK;
    @Column(name = "Fase")
    private Integer fase;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_01")
    private boolean pregunta01;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_02")
    private boolean pregunta02;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_03")
    private boolean pregunta03;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_04")
    private boolean pregunta04;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_05")
    private boolean pregunta05;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_06")
    private boolean pregunta06;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_07")
    private boolean pregunta07;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_08")
    private boolean pregunta08;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_09")
    private boolean pregunta09;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_10")
    private boolean pregunta10;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_11")
    private boolean pregunta11;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_12")
    private boolean pregunta12;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_13")
    private boolean pregunta13;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_14")
    private boolean pregunta14;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_15")
    private boolean pregunta15;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pregunta_16")
    private boolean pregunta16;
    @Size(max = 10)
    @Column(name = "Pregunta_16_Mes")
    private String pregunta16Mes;
    @JoinColumns({
        @JoinColumn(name = "Expediente", referencedColumnName = "Expediente", insertable = false, updatable = false),
        @JoinColumn(name = "AreaGeo", referencedColumnName = "AreaGeo", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private EncSocioNutricia encSocioNutricia;

    public EncSocioNutriciaSeg() {
    }

    public EncSocioNutriciaSeg(EncSocioNutriciaSegPK encSocioNutriciaSegPK) {
        this.encSocioNutriciaSegPK = encSocioNutriciaSegPK;
    }

    public EncSocioNutriciaSeg(EncSocioNutriciaSegPK encSocioNutriciaSegPK, boolean pregunta01, boolean pregunta02, boolean pregunta03, boolean pregunta04, boolean pregunta05, boolean pregunta06, boolean pregunta07, boolean pregunta08, boolean pregunta09, boolean pregunta10, boolean pregunta11, boolean pregunta12, boolean pregunta13, boolean pregunta14, boolean pregunta15, boolean pregunta16) {
        this.encSocioNutriciaSegPK = encSocioNutriciaSegPK;
        this.pregunta01 = pregunta01;
        this.pregunta02 = pregunta02;
        this.pregunta03 = pregunta03;
        this.pregunta04 = pregunta04;
        this.pregunta05 = pregunta05;
        this.pregunta06 = pregunta06;
        this.pregunta07 = pregunta07;
        this.pregunta08 = pregunta08;
        this.pregunta09 = pregunta09;
        this.pregunta10 = pregunta10;
        this.pregunta11 = pregunta11;
        this.pregunta12 = pregunta12;
        this.pregunta13 = pregunta13;
        this.pregunta14 = pregunta14;
        this.pregunta15 = pregunta15;
        this.pregunta16 = pregunta16;
    }

    public EncSocioNutriciaSeg(int expediente, char areaGeo) {
        this.encSocioNutriciaSegPK = new EncSocioNutriciaSegPK(expediente, areaGeo);
    }

    public EncSocioNutriciaSegPK getEncSocioNutriciaSegPK() {
        return encSocioNutriciaSegPK;
    }

    public void setEncSocioNutriciaSegPK(EncSocioNutriciaSegPK encSocioNutriciaSegPK) {
        this.encSocioNutriciaSegPK = encSocioNutriciaSegPK;
    }

    public Integer getFase() {
        return fase;
    }

    public void setFase(Integer fase) {
        this.fase = fase;
    }

    public boolean getPregunta01() {
        return pregunta01;
    }

    public void setPregunta01(boolean pregunta01) {
        this.pregunta01 = pregunta01;
    }

    public boolean getPregunta02() {
        return pregunta02;
    }

    public void setPregunta02(boolean pregunta02) {
        this.pregunta02 = pregunta02;
    }

    public boolean getPregunta03() {
        return pregunta03;
    }

    public void setPregunta03(boolean pregunta03) {
        this.pregunta03 = pregunta03;
    }

    public boolean getPregunta04() {
        return pregunta04;
    }

    public void setPregunta04(boolean pregunta04) {
        this.pregunta04 = pregunta04;
    }

    public boolean getPregunta05() {
        return pregunta05;
    }

    public void setPregunta05(boolean pregunta05) {
        this.pregunta05 = pregunta05;
    }

    public boolean getPregunta06() {
        return pregunta06;
    }

    public void setPregunta06(boolean pregunta06) {
        this.pregunta06 = pregunta06;
    }

    public boolean getPregunta07() {
        return pregunta07;
    }

    public void setPregunta07(boolean pregunta07) {
        this.pregunta07 = pregunta07;
    }

    public boolean getPregunta08() {
        return pregunta08;
    }

    public void setPregunta08(boolean pregunta08) {
        this.pregunta08 = pregunta08;
    }

    public boolean getPregunta09() {
        return pregunta09;
    }

    public void setPregunta09(boolean pregunta09) {
        this.pregunta09 = pregunta09;
    }

    public boolean getPregunta10() {
        return pregunta10;
    }

    public void setPregunta10(boolean pregunta10) {
        this.pregunta10 = pregunta10;
    }

    public boolean getPregunta11() {
        return pregunta11;
    }

    public void setPregunta11(boolean pregunta11) {
        this.pregunta11 = pregunta11;
    }

    public boolean getPregunta12() {
        return pregunta12;
    }

    public void setPregunta12(boolean pregunta12) {
        this.pregunta12 = pregunta12;
    }

    public boolean getPregunta13() {
        return pregunta13;
    }

    public void setPregunta13(boolean pregunta13) {
        this.pregunta13 = pregunta13;
    }

    public boolean getPregunta14() {
        return pregunta14;
    }

    public void setPregunta14(boolean pregunta14) {
        this.pregunta14 = pregunta14;
    }

    public boolean getPregunta15() {
        return pregunta15;
    }

    public void setPregunta15(boolean pregunta15) {
        this.pregunta15 = pregunta15;
    }

    public boolean getPregunta16() {
        return pregunta16;
    }

    public void setPregunta16(boolean pregunta16) {
        this.pregunta16 = pregunta16;
    }

    public String getPregunta16Mes() {
        return pregunta16Mes;
    }

    public void setPregunta16Mes(String pregunta16Mes) {
        this.pregunta16Mes = pregunta16Mes;
    }

    public EncSocioNutricia getEncSocioNutricia() {
        return encSocioNutricia;
    }

    public void setEncSocioNutricia(EncSocioNutricia encSocioNutricia) {
        this.encSocioNutricia = encSocioNutricia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (encSocioNutriciaSegPK != null ? encSocioNutriciaSegPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncSocioNutriciaSeg)) {
            return false;
        }
        EncSocioNutriciaSeg other = (EncSocioNutriciaSeg) object;
        if ((this.encSocioNutriciaSegPK == null && other.encSocioNutriciaSegPK != null) || (this.encSocioNutriciaSegPK != null && !this.encSocioNutriciaSegPK.equals(other.encSocioNutriciaSegPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.EncSocioNutriciaSeg[ encSocioNutriciaSegPK=" + encSocioNutriciaSegPK + " ]";
    }
    
}
