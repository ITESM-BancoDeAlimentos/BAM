package com.caritas.entity;

import com.caritas.entity.BancosDeAlimentos;
import com.caritas.entity.DistribucionAMBADetPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(DistribucionAMBADet.class)
public class DistribucionAMBADet_ { 

    public static volatile SingularAttribute<DistribucionAMBADet, Double> porcentaje;
    public static volatile SingularAttribute<DistribucionAMBADet, String> remision;
    public static volatile SingularAttribute<DistribucionAMBADet, Integer> poblacion;
    public static volatile SingularAttribute<DistribucionAMBADet, DistribucionAMBADetPK> distribucionAMBADetPK;
    public static volatile SingularAttribute<DistribucionAMBADet, Boolean> recibe;
    public static volatile SingularAttribute<DistribucionAMBADet, Double> piezas;
    public static volatile SingularAttribute<DistribucionAMBADet, String> observaciones;
    public static volatile SingularAttribute<DistribucionAMBADet, BancosDeAlimentos> bancosDeAlimentos;
    public static volatile SingularAttribute<DistribucionAMBADet, Double> kilogramos;
    public static volatile SingularAttribute<DistribucionAMBADet, Double> flete;

}