package com.caritas.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "Parametros", catalog = "BAlimentos", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametros.findAll", query = "SELECT p FROM Parametros p"),
    @NamedQuery(name = "Parametros.findByParametro", query = "SELECT p FROM Parametros p WHERE p.parametro = :parametro"),
    @NamedQuery(name = "Parametros.findParametros", query = "SELECT p.parametro FROM Parametros p"),
    @NamedQuery(name = "Parametros.findByValor", query = "SELECT p FROM Parametros p WHERE p.valor = :valor")})
public class Parametros implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "Parametro")
    private String parametro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Valor")
    private String valor;

    public Parametros() {
    }

    public Parametros(String parametro) {
        this.parametro = parametro;
    }

    public Parametros(String parametro, String valor) {
        this.parametro = parametro;
        this.valor = valor;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parametro != null ? parametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametros)) {
            return false;
        }
        Parametros other = (Parametros) object;
        if ((this.parametro == null && other.parametro != null) || (this.parametro != null && !this.parametro.equals(other.parametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return parametro +" : " +valor;
    }

}
