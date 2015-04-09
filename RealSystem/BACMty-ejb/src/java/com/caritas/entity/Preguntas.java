/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.caritas.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author software
 */
@Entity
@Table(name = "Preguntas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preguntas.findAll", query = "SELECT p FROM Preguntas p"),
    @NamedQuery(name = "Preguntas.findByIDPregunta", query = "SELECT p FROM Preguntas p WHERE p.iDPregunta = :iDPregunta"),
    @NamedQuery(name = "Preguntas.findByDescripcion", query = "SELECT p FROM Preguntas p WHERE p.descripcion = :descripcion")})
public class Preguntas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPregunta")
    private Integer iDPregunta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preguntas")
    private List<Respuestas> respuestasList;

    public Preguntas(){
    }

    public Preguntas(Integer iDPregunta) {
        this.iDPregunta = iDPregunta;
    }

    public Preguntas(Integer iDPregunta, String descripcion) {
        this.iDPregunta = iDPregunta;
        this.descripcion = descripcion;
    }

    public Integer getIDPregunta() {
        return iDPregunta;
    }

    public void setIDPregunta(Integer iDPregunta) {
        this.iDPregunta = iDPregunta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Respuestas> getRespuestasList() {
        return respuestasList;
    }

    public void setRespuestasList(List<Respuestas> respuestasList) {
        this.respuestasList = respuestasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDPregunta != null ? iDPregunta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preguntas)) {
            return false;
        }
        Preguntas other = (Preguntas) object;
        if ((this.iDPregunta == null && other.iDPregunta != null) || (this.iDPregunta != null && !this.iDPregunta.equals(other.iDPregunta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return iDPregunta + " - " + descripcion;
    }

}
