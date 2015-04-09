package com.caritas.entity;

import com.caritas.entity.Cajas;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(PartidasValesCajas.class)
public class PartidasValesCajas_ { 

    public static volatile SingularAttribute<PartidasValesCajas, Double> peso;
    public static volatile SingularAttribute<PartidasValesCajas, Date> fechaPesado;
    public static volatile SingularAttribute<PartidasValesCajas, Double> costo;
    public static volatile SingularAttribute<PartidasValesCajas, Cajas> iDCaja;
    public static volatile SingularAttribute<PartidasValesCajas, String> iDFolio;
    public static volatile SingularAttribute<PartidasValesCajas, Integer> iDPartidasValesCajas;

}