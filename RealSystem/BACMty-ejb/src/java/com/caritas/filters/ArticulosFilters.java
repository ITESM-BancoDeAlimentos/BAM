package com.caritas.filters;

import com.caritas.entity.Articulos;
import com.caritas.entity.Articulos_;
import com.caritas.entity.GrupoAlim;
import com.caritas.entity.GrupoRef;
import com.caritas.entity.Variedad;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class ArticulosFilters implements Serializable {

    private String articulo;
    private String almacen;
    private String localizacion;
    private Variedad iDVariedad;
    private GrupoAlim iDGrupoAlim;
    private GrupoRef iDGrupoRef;
    private static final int CANTIDAD_FILTROS = 6;

    //<editor-fold defaultstate="collapsed" desc="GettersSetters">
    public GrupoRef getIDGrupoRef() {
        return iDGrupoRef;
    }

    public void setIDGrupoRef(GrupoRef iDGrupoRef) {
        this.iDGrupoRef = iDGrupoRef;
    }
    
    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public GrupoAlim getIDGrupoAlim() {
        return iDGrupoAlim;
    }

    public void setIDGrupoAlim(GrupoAlim iDGrupoAlim) {
        this.iDGrupoAlim = iDGrupoAlim;
    }

    public Variedad getIDVariedad() {
        return iDVariedad;
    }

    public void setIDVariedad(Variedad iDVariedad) {
        this.iDVariedad = iDVariedad;
    }
    //</editor-fold>

    public Predicate[] ToPredicateArray(CriteriaBuilder cb, Path<Articulos> from) {
        ArrayList<Predicate> predicates = new ArrayList<Predicate>(CANTIDAD_FILTROS);
        if (null != articulo && !articulo.isEmpty()) {
            String query = "%" + articulo + "%";
            predicates.add(cb.or(
                    cb.like(from.get(Articulos_.iDArticulo), query),
                    cb.like(from.get(Articulos_.articulo),   query)
            ));
        }
        if (null != almacen) {
            predicates.add(cb.equal(from.get(Articulos_.almacen), almacen));
        }
        if (null != localizacion) {
            predicates.add(cb.equal(from.get(Articulos_.localizacion), localizacion));
        }
        if (null != iDVariedad) {
            predicates.add(cb.equal(from.get(Articulos_.iDVariedad), iDVariedad));
        }
        if (null != iDGrupoAlim) {
            predicates.add(cb.equal(from.get(Articulos_.iDGrupoAlim), iDGrupoAlim));
        }
        if (null != iDGrupoRef) {
            predicates.add(cb.equal(from.get(Articulos_.iDGrupoRef), iDGrupoRef));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
