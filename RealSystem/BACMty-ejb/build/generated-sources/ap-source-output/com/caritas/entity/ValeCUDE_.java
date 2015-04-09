package com.caritas.entity;

import com.caritas.entity.Donantes;
import com.caritas.entity.Sucursales;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(ValeCUDE.class)
public class ValeCUDE_ { 

    public static volatile SingularAttribute<ValeCUDE, Date> fecha;
    public static volatile SingularAttribute<ValeCUDE, String> folio;
    public static volatile SingularAttribute<ValeCUDE, String> elabora;
    public static volatile SingularAttribute<ValeCUDE, Integer> iDValeCUDE;
    public static volatile SingularAttribute<ValeCUDE, Sucursales> iDSucursales;
    public static volatile SingularAttribute<ValeCUDE, Donantes> iDDonante;

}