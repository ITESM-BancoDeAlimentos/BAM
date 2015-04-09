package com.caritas.entity;

import com.caritas.entity.DistribucionAMBA;
import com.caritas.entity.Donantes;
import com.caritas.entity.Movimientos;
import com.caritas.entity.Recibo;
import com.caritas.entity.ValeCUDE;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Sucursales.class)
public class Sucursales_ { 

    public static volatile SingularAttribute<Sucursales, String> direccion;
    public static volatile SingularAttribute<Sucursales, String> mail;
    public static volatile CollectionAttribute<Sucursales, DistribucionAMBA> distribucionAMBACollection;
    public static volatile SingularAttribute<Sucursales, String> ciudad;
    public static volatile SingularAttribute<Sucursales, String> fax;
    public static volatile SingularAttribute<Sucursales, String> telefono;
    public static volatile CollectionAttribute<Sucursales, Recibo> reciboCollection;
    public static volatile SingularAttribute<Sucursales, Donantes> iDDonante;
    public static volatile SingularAttribute<Sucursales, String> colonia;
    public static volatile SingularAttribute<Sucursales, String> iDSucursal;
    public static volatile SingularAttribute<Sucursales, String> contacto;
    public static volatile SingularAttribute<Sucursales, String> estado;
    public static volatile CollectionAttribute<Sucursales, ValeCUDE> valeCUDECollection;
    public static volatile CollectionAttribute<Sucursales, Movimientos> movimientosCollection;
    public static volatile SingularAttribute<Sucursales, Integer> iDSucursales;

}