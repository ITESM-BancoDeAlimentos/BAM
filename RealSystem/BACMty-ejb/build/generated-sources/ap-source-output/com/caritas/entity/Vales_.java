package com.caritas.entity;

import com.caritas.entity.Areas;
import com.caritas.entity.Instituciones;
import com.caritas.entity.Programas;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Vales.class)
public class Vales_ { 

    public static volatile SingularAttribute<Vales, Double> total;
    public static volatile SingularAttribute<Vales, Date> fechaSistema;
    public static volatile SingularAttribute<Vales, Date> fecha;
    public static volatile SingularAttribute<Vales, Integer> paquetes;
    public static volatile SingularAttribute<Vales, String> status;
    public static volatile SingularAttribute<Vales, String> folioMov;
    public static volatile SingularAttribute<Vales, Double> fleteCom;
    public static volatile SingularAttribute<Vales, Double> otrosCargos;
    public static volatile SingularAttribute<Vales, Instituciones> idInstitucion;
    public static volatile SingularAttribute<Vales, String> iDFolio;
    public static volatile SingularAttribute<Vales, Double> saldo;
    public static volatile SingularAttribute<Vales, Double> flete;
    public static volatile SingularAttribute<Vales, String> observacion;
    public static volatile SingularAttribute<Vales, Double> descuento;
    public static volatile SingularAttribute<Vales, Programas> iDPrograma;
    public static volatile SingularAttribute<Vales, Integer> personas;
    public static volatile SingularAttribute<Vales, Double> precioPaq;
    public static volatile SingularAttribute<Vales, Short> tipo;
    public static volatile SingularAttribute<Vales, Areas> idArea;
    public static volatile SingularAttribute<Vales, Double> familia;

}