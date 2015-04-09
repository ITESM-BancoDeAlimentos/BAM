package com.caritas.entity;

import com.caritas.entity.Movimientos;
import com.caritas.entity.Recibo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Proveedores.class)
public class Proveedores_ { 

    public static volatile SingularAttribute<Proveedores, String> direccion;
    public static volatile SingularAttribute<Proveedores, String> mail;
    public static volatile SingularAttribute<Proveedores, String> contacto;
    public static volatile SingularAttribute<Proveedores, String> ciudad;
    public static volatile SingularAttribute<Proveedores, String> estado;
    public static volatile SingularAttribute<Proveedores, String> iDProveedor;
    public static volatile SingularAttribute<Proveedores, String> fax;
    public static volatile SingularAttribute<Proveedores, String> telefono;
    public static volatile SingularAttribute<Proveedores, Date> fechaAlta;
    public static volatile CollectionAttribute<Proveedores, Recibo> reciboCollection;
    public static volatile SingularAttribute<Proveedores, String> proveedor;
    public static volatile CollectionAttribute<Proveedores, Movimientos> movimientosCollection;
    public static volatile SingularAttribute<Proveedores, String> rfc;
    public static volatile SingularAttribute<Proveedores, String> colonia;

}