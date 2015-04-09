package com.caritas.entity;

import com.caritas.entity.Areas;
import com.caritas.entity.Articulos;
import com.caritas.entity.Programas;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(ReciboDet.class)
public class ReciboDet_ { 

    public static volatile SingularAttribute<ReciboDet, Double> peso;
    public static volatile SingularAttribute<ReciboDet, Date> fechaCad;
    public static volatile SingularAttribute<ReciboDet, Articulos> iDArticulo;
    public static volatile SingularAttribute<ReciboDet, Programas> iDPrograma;
    public static volatile SingularAttribute<ReciboDet, Integer> iDReciboDet;
    public static volatile SingularAttribute<ReciboDet, Double> cuotaRecup;
    public static volatile SingularAttribute<ReciboDet, Double> costoBenef;
    public static volatile SingularAttribute<ReciboDet, Double> cantidad;
    public static volatile SingularAttribute<ReciboDet, Double> merma;
    public static volatile SingularAttribute<ReciboDet, String> iDFolio;
    public static volatile SingularAttribute<ReciboDet, Areas> iDArea;
    public static volatile SingularAttribute<ReciboDet, String> tipoMov;

}