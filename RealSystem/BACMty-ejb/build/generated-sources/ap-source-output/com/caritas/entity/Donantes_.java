package com.caritas.entity;

import com.caritas.entity.Articulos;
import com.caritas.entity.DistribucionAMBA;
import com.caritas.entity.Movimientos;
import com.caritas.entity.Recibo;
import com.caritas.entity.Sucursales;
import com.caritas.entity.TipoDon;
import com.caritas.entity.ValeCUDE;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Donantes.class)
public class Donantes_ { 

    public static volatile SingularAttribute<Donantes, String> direccion;
    public static volatile SingularAttribute<Donantes, String> mail;
    public static volatile SingularAttribute<Donantes, String> ciudad;
    public static volatile CollectionAttribute<Donantes, DistribucionAMBA> distribucionAMBACollection;
    public static volatile SingularAttribute<Donantes, String> fax;
    public static volatile CollectionAttribute<Donantes, Articulos> articulosCollection;
    public static volatile SingularAttribute<Donantes, String> telefono;
    public static volatile SingularAttribute<Donantes, TipoDon> iDTipoDon;
    public static volatile CollectionAttribute<Donantes, Recibo> reciboCollection;
    public static volatile SingularAttribute<Donantes, String> frecuencia;
    public static volatile SingularAttribute<Donantes, String> tipoDonante;
    public static volatile SingularAttribute<Donantes, String> iDDonante;
    public static volatile SingularAttribute<Donantes, String> colonia;
    public static volatile SingularAttribute<Donantes, String> rfc;
    public static volatile SingularAttribute<Donantes, String> nombre;
    public static volatile SingularAttribute<Donantes, Boolean> sucursales;
    public static volatile SingularAttribute<Donantes, String> contacto;
    public static volatile SingularAttribute<Donantes, String> estado;
    public static volatile CollectionAttribute<Donantes, Sucursales> sucursalesCollection;
    public static volatile CollectionAttribute<Donantes, ValeCUDE> valeCUDECollection;
    public static volatile SingularAttribute<Donantes, Date> fechaAlta;
    public static volatile SingularAttribute<Donantes, String> donante;
    public static volatile SingularAttribute<Donantes, String> usuarioAlta;
    public static volatile CollectionAttribute<Donantes, Movimientos> movimientosCollection;

}