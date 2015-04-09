package com.caritas.entity;

import com.caritas.entity.Instituciones;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(TablaTmpDon.class)
public class TablaTmpDon_ { 

    public static volatile SingularAttribute<TablaTmpDon, String> programa;
    public static volatile SingularAttribute<TablaTmpDon, String> institucion;
    public static volatile SingularAttribute<TablaTmpDon, String> area;
    public static volatile SingularAttribute<TablaTmpDon, String> iDFolioCArAbo;
    public static volatile SingularAttribute<TablaTmpDon, Instituciones> iDInstitucion;
    public static volatile SingularAttribute<TablaTmpDon, BigDecimal> donativo;
    public static volatile SingularAttribute<TablaTmpDon, Integer> iDTablaTmpDon;
    public static volatile SingularAttribute<TablaTmpDon, String> iDFolio;
    public static volatile SingularAttribute<TablaTmpDon, Date> fechaMov;

}