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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author software
 */
@Entity
@Table(name = "Respuestas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Respuestas.findAll", query = "SELECT r FROM Respuestas r ORDER BY r.preguntas.iDPregunta ASC, r.respuestasPK.iDRespuesta"),
    @NamedQuery(name = "Respuestas.findByIDRespuesta", query = "SELECT r FROM Respuestas r WHERE r.respuestasPK.iDRespuesta = :iDRespuesta"),
    @NamedQuery(name = "Respuestas.findByIDPregunta", query = "SELECT r FROM Respuestas r WHERE r.respuestasPK.iDPregunta = :iDPregunta"),
    @NamedQuery(name = "Respuestas.findByIDPreguntaIDRespuesta", query = "SELECT r FROM Respuestas r WHERE r.respuestasPK.iDPregunta = :iDPregunta AND r.respuestasPK.iDRespuesta = :iDRespuesta"),
    @NamedQuery(name = "Respuestas.findByDescripcion", query = "SELECT r FROM Respuestas r WHERE r.descripcion = :descripcion")})
public class Respuestas implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RespuestasPK respuestasPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Descripcion")
    private String descripcion;
    @JoinColumn(name = "IDPregunta", referencedColumnName = "IDPregunta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Preguntas preguntas;

    public Respuestas(){
        respuestasPK = new RespuestasPK(0,0);
        preguntas = new Preguntas(0);
    }

    public Respuestas(RespuestasPK respuestasPK) {
        this.respuestasPK = respuestasPK;
    }

    public Respuestas(RespuestasPK respuestasPK, String descripcion) {
        this.respuestasPK = respuestasPK;
        this.descripcion = descripcion;
    }

    public Respuestas(int iDRespuesta, int iDPregunta) {
        this.respuestasPK = new RespuestasPK(iDRespuesta, iDPregunta);
    }

    public RespuestasPK getRespuestasPK() {
        return respuestasPK;
    }

    public void setRespuestasPK(RespuestasPK respuestasPK) {
        this.respuestasPK = respuestasPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Preguntas getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(Preguntas preguntas) {
        this.preguntas = preguntas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respuestasPK != null ? respuestasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Respuestas)) {
            return false;
        }
        Respuestas other = (Respuestas) object;
        if ((this.respuestasPK == null && other.respuestasPK != null) || (this.respuestasPK != null && !this.respuestasPK.equals(other.respuestasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return preguntas.getDescripcion() + " - " + descripcion;
    }

}
