/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tecnologia
 */
@Entity
@Table(name = "Accesos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accesos.findAll", query = "SELECT a FROM Accesos a"),
    @NamedQuery(name = "Accesos.findByNivelWeb", query = "SELECT a FROM Accesos a WHERE a.iDNivel = :iDNivel AND a.iDForm.iDForm LIKE :iDForm ORDER BY a.iDForm.orden"),
    @NamedQuery(name = "Accesos.findBySubMenu", query = "SELECT a FROM Accesos a WHERE a.subMenu = :subMenu"),
    @NamedQuery(name = "Accesos.findByNuevo", query = "SELECT a FROM Accesos a WHERE a.nuevo = :nuevo"),
    @NamedQuery(name = "Accesos.findByEditar", query = "SELECT a FROM Accesos a WHERE a.editar = :editar"),
    @NamedQuery(name = "Accesos.findByBorrar", query = "SELECT a FROM Accesos a WHERE a.borrar = :borrar"),
    @NamedQuery(name = "Accesos.findByGrabar", query = "SELECT a FROM Accesos a WHERE a.grabar = :grabar"),
    @NamedQuery(name = "Accesos.findByCancelar", query = "SELECT a FROM Accesos a WHERE a.cancelar = :cancelar"),
    @NamedQuery(name = "Accesos.findByImprimir", query = "SELECT a FROM Accesos a WHERE a.imprimir = :imprimir"),
    @NamedQuery(name = "Accesos.findByIDAccesos", query = "SELECT a FROM Accesos a WHERE a.iDAccesos = :iDAccesos")})
public class Accesos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SubMenu")
    private boolean subMenu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Nuevo")
    private boolean nuevo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Editar")
    private boolean editar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Borrar")
    private boolean borrar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Grabar")
    private boolean grabar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cancelar")
    private boolean cancelar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Imprimir")
    private boolean imprimir;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDAccesos")
    private Integer iDAccesos;
    @JoinColumn(name = "IDNivel", referencedColumnName = "IDNivel")
    @ManyToOne
    private NivelAcceso iDNivel;
    @JoinColumn(name = "IDForm", referencedColumnName = "IDForm")
    @ManyToOne(optional = false)
    private Formularios iDForm;

    public Accesos() {
    }

    public Accesos(Integer iDAccesos) {
        this.iDAccesos = iDAccesos;
    }

    public Accesos(Integer iDAccesos, boolean subMenu, boolean nuevo, boolean editar, boolean borrar, boolean grabar, boolean cancelar, boolean imprimir) {
        this.iDAccesos = iDAccesos;
        this.subMenu = subMenu;
        this.nuevo = nuevo;
        this.editar = editar;
        this.borrar = borrar;
        this.grabar = grabar;
        this.cancelar = cancelar;
        this.imprimir = imprimir;
    }
    
    public Accesos(NivelAcceso iDNivel) {
        this.iDNivel = iDNivel;
    }

    public boolean getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(boolean subMenu) {
        this.subMenu = subMenu;
    }

    public boolean getNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public boolean getEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public boolean getBorrar() {
        return borrar;
    }

    public void setBorrar(boolean borrar) {
        this.borrar = borrar;
    }

    public boolean getGrabar() {
        return grabar;
    }

    public void setGrabar(boolean grabar) {
        this.grabar = grabar;
    }

    public boolean getCancelar() {
        return cancelar;
    }

    public void setCancelar(boolean cancelar) {
        this.cancelar = cancelar;
    }

    public boolean getImprimir() {
        return imprimir;
    }

    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }

    public Integer getIDAccesos() {
        return iDAccesos;
    }

    public void setIDAccesos(Integer iDAccesos) {
        this.iDAccesos = iDAccesos;
    }

    public NivelAcceso getIDNivel() {
        return iDNivel;
    }

    public void setIDNivel(NivelAcceso iDNivel) {
        this.iDNivel = iDNivel;
    }

    public Formularios getIDForm() {
        return iDForm;
    }

    public void setIDForm(Formularios iDForm) {
        this.iDForm = iDForm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDAccesos != null ? iDAccesos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accesos)) {
            return false;
        }
        Accesos other = (Accesos) object;
        if ((this.iDAccesos == null && other.iDAccesos != null) || (this.iDAccesos != null && !this.iDAccesos.equals(other.iDAccesos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caritas.entity.Accesos[ iDAccesos=" + iDAccesos + " ]";
    }
    
}
