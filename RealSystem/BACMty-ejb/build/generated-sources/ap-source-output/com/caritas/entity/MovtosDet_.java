package com.caritas.entity;

import com.caritas.entity.Areas;
import com.caritas.entity.Articulos;
import com.caritas.entity.Movimientos;
import com.caritas.entity.Programas;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(MovtosDet.class)
public class MovtosDet_ { 

    public static volatile SingularAttribute<MovtosDet, Double> peso;
    public static volatile SingularAttribute<MovtosDet, Date> fechaCad;
    public static volatile SingularAttribute<MovtosDet, Articulos> iDArticulo;
    public static volatile SingularAttribute<MovtosDet, Programas> iDPrograma;
    public static volatile SingularAttribute<MovtosDet, Double> cuotaRecup;
    public static volatile SingularAttribute<MovtosDet, Double> costoBenef;
    public static volatile SingularAttribute<MovtosDet, Double> cantidad;
    public static volatile SingularAttribute<MovtosDet, Double> merma;
    public static volatile SingularAttribute<MovtosDet, Movimientos> movimientos;
    public static volatile SingularAttribute<MovtosDet, String> iDFolio;
    public static volatile SingularAttribute<MovtosDet, Areas> iDArea;
    public static volatile SingularAttribute<MovtosDet, Integer> iDMovtosDet;
    public static volatile SingularAttribute<MovtosDet, String> tipoMov;

}