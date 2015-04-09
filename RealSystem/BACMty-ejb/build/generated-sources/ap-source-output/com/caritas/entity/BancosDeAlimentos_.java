package com.caritas.entity;

import com.caritas.entity.DistribucionAMBADet;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(BancosDeAlimentos.class)
public class BancosDeAlimentos_ { 

    public static volatile SingularAttribute<BancosDeAlimentos, String> nombre;
    public static volatile SingularAttribute<BancosDeAlimentos, Integer> poblacion;
    public static volatile CollectionAttribute<BancosDeAlimentos, DistribucionAMBADet> distribucionAMBADetCollection;
    public static volatile SingularAttribute<BancosDeAlimentos, Integer> iDBancoDeAlimentos;

}