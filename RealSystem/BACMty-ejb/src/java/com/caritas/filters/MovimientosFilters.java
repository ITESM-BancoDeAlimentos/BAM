package com.caritas.filters;

import com.caritas.entity.Areas;
import com.caritas.entity.Donantes;
import com.caritas.entity.Instituciones;
import com.caritas.entity.Movimientos;
import com.caritas.entity.Movimientos_;
import com.caritas.entity.Programas;
import com.caritas.entity.Proveedores;
import com.caritas.entity.Sucursales;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author software
 */
public class MovimientosFilters implements Serializable {

    private Donantes filtroDonantes = null;
    private Sucursales filtroSucursales = null;
    private Areas filtroAreas = null;
    private Proveedores filtroProveedores = null;
    private Instituciones filtroInstituciones = null;
    private Programas filtroProgramas = null;
    private String filtroTipoMov = null;
    private Date filtroFechaInicial = null;
    private Date filtroFechaFinal = null;
    private String filtroOrigen = null;
    private static final int CANTIDAD_FILTROS = 10;

    public Predicate[] ToPredicateArray(CriteriaBuilder cb, Path<Movimientos> from) {
        ArrayList<Predicate> andClause = new ArrayList<Predicate>(CANTIDAD_FILTROS);
        if (null != filtroDonantes) {
            andClause.add(cb.equal(from.get(Movimientos_.iDDonante), filtroDonantes));
        }
        if (null != filtroSucursales) {
            andClause.add(cb.equal(from.get(Movimientos_.iDSucursal), filtroSucursales.getIDSucursal()));
        }
        if (null != filtroAreas) {
            andClause.add(cb.equal(from.get(Movimientos_.iDArea), filtroAreas));
        }
        if (null != filtroProveedores) {
            andClause.add(cb.equal(from.get(Movimientos_.iDProveedor), filtroProveedores));
        }
        if (null != filtroInstituciones) {
            andClause.add(cb.equal(from.get(Movimientos_.iDInstitucion), filtroInstituciones));
        }
        if (null != filtroProgramas) {
            andClause.add(cb.equal(from.get(Movimientos_.iDPrograma), filtroProgramas));
        }
        if (null != filtroTipoMov) {
            andClause.add(cb.equal(from.get(Movimientos_.tipoMov), filtroTipoMov));
        }
        if (null != filtroFechaInicial) {
            andClause.add(cb.greaterThanOrEqualTo(from.get(Movimientos_.fechaMov), filtroFechaInicial));
        }
        if (null != filtroFechaFinal) {
            andClause.add(cb.lessThanOrEqualTo(from.get(Movimientos_.fechaMov), filtroFechaFinal));
        }
        if (null != filtroOrigen) {
            andClause.add(cb.equal(from.get(Movimientos_.origen), filtroOrigen));
        }
        Predicate[] predicates = new Predicate[andClause.size()];
        Predicate[] toArray = andClause.toArray(predicates);
        return toArray;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters Setters">
    public Donantes getFiltroDonantes() {
        return filtroDonantes;
    }

    public void setFiltroDonantes(Donantes filtroDonantes) {
        this.filtroDonantes = filtroDonantes;
    }

    public Sucursales getFiltroSucursales() {
        return filtroSucursales;
    }

    public void setFiltroSucursales(Sucursales filtroSucursales) {
        this.filtroSucursales = filtroSucursales;
    }

    public Areas getFiltroAreas() {
        return filtroAreas;
    }

    public void setFiltroAreas(Areas filtroAreas) {
        this.filtroAreas = filtroAreas;
    }

    public Proveedores getFiltroProveedores() {
        return filtroProveedores;
    }

    public void setFiltroProveedores(Proveedores filtroProveedores) {
        this.filtroProveedores = filtroProveedores;
    }

    public Instituciones getFiltroInstituciones() {
        return filtroInstituciones;
    }

    public void setFiltroInstituciones(Instituciones filtroInstituciones) {
        this.filtroInstituciones = filtroInstituciones;
    }

    public String getFiltroTipoMov() {
        return filtroTipoMov;
    }

    public void setFiltroTipoMov(String filtroTipoMov) {
        this.filtroTipoMov = filtroTipoMov;
    }

    public Date getFiltroFechaInicial() {
        return filtroFechaInicial;
    }

    public void setFiltroFechaInicial(Date filtroFechaInicial) {
        this.filtroFechaInicial = filtroFechaInicial;
    }

    public Date getFiltroFechaFinal() {
        return filtroFechaFinal;
    }

    public void setFiltroFechaFinal(Date filtroFechaFinal) {
        this.filtroFechaFinal = filtroFechaFinal;
    }

    public Programas getFiltroProgramas() {
        return filtroProgramas;
    }

    public void setFiltroProgramas(Programas filtroProgramas) {
        this.filtroProgramas = filtroProgramas;
    }

    public String getFiltroOrigen() {
        return filtroOrigen;
    }

    public void setFiltroOrigen(String filtroOrigen) {
        this.filtroOrigen = filtroOrigen;
    }
    //</editor-fold>
}