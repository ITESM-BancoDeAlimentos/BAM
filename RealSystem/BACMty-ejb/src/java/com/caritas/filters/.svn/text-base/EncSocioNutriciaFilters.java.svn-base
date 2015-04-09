package com.caritas.filters;

import com.caritas.entity.EncSocioNutricia;
import com.caritas.entity.EncSocioNutriciaInd;
import com.caritas.entity.EncSocioNutriciaIndPK;
import com.caritas.entity.EncSocioNutriciaIndPK_;
import com.caritas.entity.EncSocioNutriciaInd_;
import com.caritas.entity.EncSocioNutricia_;
import com.caritas.entity.Instituciones;
import com.caritas.entity.Instituciones_;
import com.caritas.enums.AreaGeografica;
import com.caritas.enums.Genero;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class EncSocioNutriciaFilters implements Serializable {

    private Integer expediente = null;
    private AreaGeografica areaGeo = null;
    private String localidad = null;
    private String municipio = null;
    private String apellidos = null;
    private Date fechaNacimientoEarly = null;
    private Date fechaNacimientoLater = null;
    private Genero genero = null;
    private Double peso = null;
    private Instituciones institucion = null;
    private static final int CANTIDAD_FILTROS = 9;

    public Predicate[] ToPredicateArray(CriteriaBuilder cb, Path<EncSocioNutriciaInd> from) {
        ArrayList<Predicate> andClause = new ArrayList<Predicate>(CANTIDAD_FILTROS);
        Path<EncSocioNutriciaIndPK> pk = from.get(EncSocioNutriciaInd_.encSocioNutriciaIndPK);
        Path<EncSocioNutricia> esn = from.get(EncSocioNutriciaInd_.encSocioNutricia);
        Path<Instituciones> idi = esn.get(EncSocioNutricia_.iDInstitucion);

        if (fechaNacimientoEarly != null) {
            Path<Date> fechaNacimiento_ = from.get(EncSocioNutriciaInd_.fechaNacimiento);
            andClause.add(cb.greaterThanOrEqualTo(fechaNacimiento_, fechaNacimientoEarly));
        }
        if (fechaNacimientoLater != null) {
            Path<Date> fechaNacimiento_ = from.get(EncSocioNutriciaInd_.fechaNacimiento);
            andClause.add(cb.lessThanOrEqualTo(fechaNacimiento_, fechaNacimientoLater));
        }
        if (expediente != null) {
            Path<Integer> expediente_ = pk
                    .get(EncSocioNutriciaIndPK_.expediente);
            andClause.add(cb.equal(expediente_, expediente));
        }
        if (areaGeo != null) {
            Path<Character> areaGeo_ = pk.get(EncSocioNutriciaIndPK_.areaGeo);
            andClause.add(cb.equal(areaGeo_, areaGeo.getCodigo()));
        }
        if (municipio != null && !municipio.isEmpty()) {
            Path<String> municipio_ = idi.get(Instituciones_.ciudad);
            andClause.add(cb.like(municipio_, "%" + municipio + "%"));
        }
        if (apellidos != null && !apellidos.isEmpty()) {
            Path<String> apellidos_ = esn.get(EncSocioNutricia_.jefeFamilia);
            andClause.add(cb.like(apellidos_, "%" + apellidos + "%"));
        }
        if (genero != null) {
            Path<String> genero_ = from.get(EncSocioNutriciaInd_.genero);
            andClause.add(cb.equal(genero_, genero.getCodigo()));
        }
        if (peso != null) {
            Path<Double> peso_ = from.get(EncSocioNutriciaInd_.peso);
            andClause.add(cb.equal(peso_, peso));
        }
        if (institucion != null) {
            Path<Instituciones> institucion_ = esn.get(EncSocioNutricia_.iDInstitucion);
            andClause.add(cb.equal(institucion_, institucion));
        }

        Predicate[] predicates = new Predicate[andClause.size()];
        Predicate[] toArray = andClause.toArray(predicates);
        return toArray;
    }
    
    /**
     * TODO: No la sordeo nada.
     * @param d base date before offset
     * @param oYear offset Year
     * @param oMonth offset Month
     * @return 
     */
    Date offSetDate(Date d, int oYear, int oMonth) {
        int bYear;
        int bMonth;
        int bDay;
        
        int cYear;
        int cMonth;
        
        int rYear;
        int rMonth;
        int rDay;

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(d);

        bYear = c.get(GregorianCalendar.YEAR);
        bMonth = c.get(GregorianCalendar.MONTH);
        bDay = c.get(GregorianCalendar.DAY_OF_MONTH);
        
        rDay = bDay;
        rMonth = bMonth + oMonth;
        rYear = bYear + oYear;
        
        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="GetSet">
    public Integer getExpediente() {
        return expediente;
    }

    public void setExpediente(Integer expediente) {
        this.expediente = expediente;
    }

    public AreaGeografica getAreaGeo() {
        return areaGeo;
    }

    public void setAreaGeo(AreaGeografica areaGeo) {
        this.areaGeo = areaGeo;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNacimientoEarly() {
        return fechaNacimientoEarly;
    }

    public void setFechaNacimientoEarly(Date fechaNacimientoEarly) {
        this.fechaNacimientoEarly = fechaNacimientoEarly;
    }

    public Date getFechaNacimientoLater() {
        return fechaNacimientoLater;
    }

    public void setFechaNacimientoLater(Date fechaNacimientoLater) {
        this.fechaNacimientoLater = fechaNacimientoLater;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Instituciones getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Instituciones institucion) {
        this.institucion = institucion;
    }
    //</editor-fold>
}
