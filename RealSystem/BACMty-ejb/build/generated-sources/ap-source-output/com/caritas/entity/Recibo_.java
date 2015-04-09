package com.caritas.entity;

import com.caritas.entity.Areas;
import com.caritas.entity.Donantes;
import com.caritas.entity.GrupoRef;
import com.caritas.entity.Instituciones;
import com.caritas.entity.Programas;
import com.caritas.entity.Proveedores;
import com.caritas.entity.Sucursales;
import com.caritas.entity.Usuarios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Recibo.class)
public class Recibo_ { 

    public static volatile SingularAttribute<Recibo, Integer> integrantes;
    public static volatile SingularAttribute<Recibo, Double> paquete;
    public static volatile SingularAttribute<Recibo, Instituciones> iDInstitucion;
    public static volatile SingularAttribute<Recibo, String> factura;
    public static volatile SingularAttribute<Recibo, Date> fechaSist;
    public static volatile SingularAttribute<Recibo, Areas> iDArea;
    public static volatile SingularAttribute<Recibo, String> iDFolio;
    public static volatile SingularAttribute<Recibo, Date> fechaMov;
    public static volatile SingularAttribute<Recibo, Donantes> iDDonante;
    public static volatile SingularAttribute<Recibo, String> tipoMov;
    public static volatile SingularAttribute<Recibo, String> folioEntrada;
    public static volatile SingularAttribute<Recibo, String> iDSucursal;
    public static volatile SingularAttribute<Recibo, Double> descuento;
    public static volatile SingularAttribute<Recibo, Integer> familias;
    public static volatile SingularAttribute<Recibo, Proveedores> iDProveedor;
    public static volatile SingularAttribute<Recibo, GrupoRef> iDGrupoRef;
    public static volatile SingularAttribute<Recibo, Programas> iDPrograma;
    public static volatile SingularAttribute<Recibo, Integer> iDRecibo;
    public static volatile SingularAttribute<Recibo, String> statusMov;
    public static volatile SingularAttribute<Recibo, String> folioDon;
    public static volatile SingularAttribute<Recibo, Usuarios> iDUsuario;
    public static volatile SingularAttribute<Recibo, Sucursales> iDSucursales;
    public static volatile SingularAttribute<Recibo, String> origen;

}