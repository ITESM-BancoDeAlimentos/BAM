/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.caritas.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author software
 */
@Embeddable
public class RespuestasPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDRespuesta")
    private int iDRespuesta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPregunta")
    private int iDPregunta;

    public RespuestasPK(){
    }

    public RespuestasPK(int iDRespuesta, int iDPregunta) {
        this.iDRespuesta = iDRespuesta;
        this.iDPregunta = iDPregunta;
    }

    public int getIDRespuesta() {
        return iDRespuesta;
    }

    public void setIDRespuesta(int iDRespuesta) {
        this.iDRespuesta = iDRespuesta;
    }

    public int getIDPregunta() {
        return iDPregunta;
    }

    public void setIDPregunta(int iDPregunta) {
        this.iDPregunta = iDPregunta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iDRespuesta;
        hash += (int) iDPregunta;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestasPK)) {
            return false;
        }
        RespuestasPK other = (RespuestasPK) object;
        if (this.iDRespuesta != other.iDRespuesta) {
            return false;
        }
        if (this.iDPregunta != other.iDPregunta) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.RespuestasPK[ iDRespuesta=" + iDRespuesta + ", iDPregunta=" + iDPregunta + " ]";
    }

}
