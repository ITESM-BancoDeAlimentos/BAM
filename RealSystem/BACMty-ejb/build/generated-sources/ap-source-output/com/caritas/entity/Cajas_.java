package com.caritas.entity;

import com.caritas.entity.Areas;
import com.caritas.entity.MovtoCajas;
import com.caritas.entity.PartidasVales;
import com.caritas.entity.PartidasValesCajas;
import com.caritas.entity.Programas;
import com.caritas.entity.Variedad;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Cajas.class)
public class Cajas_ { 

    public static volatile SingularAttribute<Cajas, Date> fechaPesado;
    public static volatile CollectionAttribute<Cajas, MovtoCajas> movtoCajasCollection;
    public static volatile SingularAttribute<Cajas, Variedad> iDVariedad;
    public static volatile SingularAttribute<Cajas, String> status;
    public static volatile SingularAttribute<Cajas, Date> fechaSal;
    public static volatile SingularAttribute<Cajas, Double> costo;
    public static volatile CollectionAttribute<Cajas, PartidasVales> partidasValesCollection;
    public static volatile SingularAttribute<Cajas, String> iDCaja;
    public static volatile SingularAttribute<Cajas, String> iDLote;
    public static volatile SingularAttribute<Cajas, String> iDFolio;
    public static volatile SingularAttribute<Cajas, Double> peso;
    public static volatile SingularAttribute<Cajas, String> idArticuloCla;
    public static volatile SingularAttribute<Cajas, Programas> iDPrograma;
    public static volatile CollectionAttribute<Cajas, PartidasValesCajas> partidasValesCajasCollection;
    public static volatile SingularAttribute<Cajas, Areas> idArea;
    public static volatile SingularAttribute<Cajas, String> asignado;

}