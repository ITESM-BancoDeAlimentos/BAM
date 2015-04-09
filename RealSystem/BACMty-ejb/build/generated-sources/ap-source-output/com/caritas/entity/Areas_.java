package com.caritas.entity;

import com.caritas.entity.Movimientos;
import com.caritas.entity.Recibo;
import com.caritas.entity.ReciboDet;
import com.caritas.entity.SubAreas;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Areas.class)
public class Areas_ { 

    public static volatile CollectionAttribute<Areas, ReciboDet> reciboDetCollection;
    public static volatile CollectionAttribute<Areas, SubAreas> subAreasCollection;
    public static volatile SingularAttribute<Areas, String> area;
    public static volatile SingularAttribute<Areas, Double> distribucion;
    public static volatile CollectionAttribute<Areas, Recibo> reciboCollection;
    public static volatile CollectionAttribute<Areas, Movimientos> movimientosCollection;
    public static volatile SingularAttribute<Areas, String> iDArea;

}