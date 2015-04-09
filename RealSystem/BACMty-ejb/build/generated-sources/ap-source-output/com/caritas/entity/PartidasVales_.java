package com.caritas.entity;

import com.caritas.entity.Articulos;
import com.caritas.entity.Cajas;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(PartidasVales.class)
public class PartidasVales_ { 

    public static volatile SingularAttribute<PartidasVales, Date> fechaCad;
    public static volatile SingularAttribute<PartidasVales, Articulos> iDArticulo;
    public static volatile SingularAttribute<PartidasVales, Double> cuotaRecup;
    public static volatile SingularAttribute<PartidasVales, String> status;
    public static volatile SingularAttribute<PartidasVales, Double> cantidad;
    public static volatile SingularAttribute<PartidasVales, Double> costoBenef;
    public static volatile SingularAttribute<PartidasVales, Cajas> iDCaja;
    public static volatile SingularAttribute<PartidasVales, String> iDFolio;
    public static volatile SingularAttribute<PartidasVales, Integer> iDPartidasVales;

}