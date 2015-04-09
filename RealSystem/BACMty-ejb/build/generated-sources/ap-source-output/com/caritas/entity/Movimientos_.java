package com.caritas.entity;

import com.caritas.entity.Areas;
import com.caritas.entity.Donantes;
import com.caritas.entity.GrupoRef;
import com.caritas.entity.Instituciones;
import com.caritas.entity.MovtosDet;
import com.caritas.entity.Programas;
import com.caritas.entity.Proveedores;
import com.caritas.entity.Sucursales;
import com.caritas.entity.Usuarios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Movimientos.class)
public class Movimientos_ { 

    public static volatile SingularAttribute<Movimientos, Integer> integrantes;
    public static volatile CollectionAttribute<Movimientos, MovtosDet> movtosDetCollection;
    public static volatile SingularAttribute<Movimientos, Double> paquete;
    public static volatile SingularAttribute<Movimientos, Instituciones> iDInstitucion;
    public static volatile SingularAttribute<Movimientos, String> factura;
    public static volatile SingularAttribute<Movimientos, Date> fechaSist;
    public static volatile SingularAttribute<Movimientos, Areas> iDArea;
    public static volatile SingularAttribute<Movimientos, String> iDFolio;
    public static volatile SingularAttribute<Movimientos, Date> fechaMov;
    public static volatile SingularAttribute<Movimientos, Donantes> iDDonante;
    public static volatile SingularAttribute<Movimientos, String> tipoMov;
    public static volatile SingularAttribute<Movimientos, String> folioEntrada;
    public static volatile SingularAttribute<Movimientos, String> iDSucursal;
    public static volatile SingularAttribute<Movimientos, Double> descuento;
    public static volatile SingularAttribute<Movimientos, Integer> familias;
    public static volatile SingularAttribute<Movimientos, Proveedores> iDProveedor;
    public static volatile SingularAttribute<Movimientos, GrupoRef> iDGrupoRef;
    public static volatile SingularAttribute<Movimientos, Programas> iDPrograma;
    public static volatile SingularAttribute<Movimientos, String> statusMov;
    public static volatile SingularAttribute<Movimientos, Integer> iDMovimientos;
    public static volatile SingularAttribute<Movimientos, String> folioDon;
    public static volatile SingularAttribute<Movimientos, Usuarios> iDUsuario;
    public static volatile SingularAttribute<Movimientos, Sucursales> iDSucursales;
    public static volatile SingularAttribute<Movimientos, String> origen;

}