package com.caritas.entity;

import com.caritas.entity.Areas;
import com.caritas.entity.Instituciones;
import com.caritas.entity.Programas;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(ValesCajas.class)
public class ValesCajas_ { 

    public static volatile SingularAttribute<ValesCajas, Double> total;
    public static volatile SingularAttribute<ValesCajas, Date> fechaSistema;
    public static volatile SingularAttribute<ValesCajas, Date> fecha;
    public static volatile SingularAttribute<ValesCajas, Integer> paquetes;
    public static volatile SingularAttribute<ValesCajas, String> status;
    public static volatile SingularAttribute<ValesCajas, String> folioMov;
    public static volatile SingularAttribute<ValesCajas, Double> fleteCom;
    public static volatile SingularAttribute<ValesCajas, Double> otrosCargos;
    public static volatile SingularAttribute<ValesCajas, Instituciones> idInstitucion;
    public static volatile SingularAttribute<ValesCajas, String> iDFolio;
    public static volatile SingularAttribute<ValesCajas, Double> saldo;
    public static volatile SingularAttribute<ValesCajas, Double> flete;
    public static volatile SingularAttribute<ValesCajas, String> observacion;
    public static volatile SingularAttribute<ValesCajas, Double> descuento;
    public static volatile SingularAttribute<ValesCajas, Programas> iDPrograma;
    public static volatile SingularAttribute<ValesCajas, Integer> personas;
    public static volatile SingularAttribute<ValesCajas, Double> precioPaq;
    public static volatile SingularAttribute<ValesCajas, Short> tipo;
    public static volatile SingularAttribute<ValesCajas, Areas> idArea;
    public static volatile SingularAttribute<ValesCajas, Double> familia;

}