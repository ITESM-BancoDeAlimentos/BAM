package com.caritas.entity;

import com.caritas.entity.Areas;
import com.caritas.entity.Articulos;
import com.caritas.entity.Programas;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(DisEntradas.class)
public class DisEntradas_ { 

    public static volatile SingularAttribute<DisEntradas, Articulos> idArticulo;
    public static volatile SingularAttribute<DisEntradas, Integer> iDDisEntradas;
    public static volatile SingularAttribute<DisEntradas, String> folioMov;
    public static volatile SingularAttribute<DisEntradas, Double> cantidad;
    public static volatile SingularAttribute<DisEntradas, Areas> idArea;
    public static volatile SingularAttribute<DisEntradas, Programas> idPrograma;

}