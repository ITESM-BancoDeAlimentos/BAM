package com.caritas.entity;

import com.caritas.entity.Areas;
import com.caritas.entity.Articulos;
import com.caritas.entity.Programas;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Existencias.class)
public class Existencias_ { 

    public static volatile SingularAttribute<Existencias, Date> fechaCad;
    public static volatile SingularAttribute<Existencias, Double> canPedido;
    public static volatile SingularAttribute<Existencias, Articulos> iDArticulo;
    public static volatile SingularAttribute<Existencias, Programas> iDPrograma;
    public static volatile SingularAttribute<Existencias, Integer> iDExistencias;
    public static volatile SingularAttribute<Existencias, Double> salida;
    public static volatile SingularAttribute<Existencias, Double> entrada;
    public static volatile SingularAttribute<Existencias, Double> existencia;
    public static volatile SingularAttribute<Existencias, Double> asignado;
    public static volatile SingularAttribute<Existencias, Double> merma;
    public static volatile SingularAttribute<Existencias, Areas> iDArea;

}