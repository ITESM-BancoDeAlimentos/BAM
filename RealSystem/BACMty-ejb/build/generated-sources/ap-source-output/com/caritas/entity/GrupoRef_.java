package com.caritas.entity;

import com.caritas.entity.Articulos;
import com.caritas.entity.Movimientos;
import com.caritas.entity.Recibo;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(GrupoRef.class)
public class GrupoRef_ { 

    public static volatile SingularAttribute<GrupoRef, String> iDGrupoRef;
    public static volatile CollectionAttribute<GrupoRef, Articulos> articulosCollection;
    public static volatile CollectionAttribute<GrupoRef, Recibo> reciboCollection;
    public static volatile SingularAttribute<GrupoRef, String> grupoRef;
    public static volatile CollectionAttribute<GrupoRef, Movimientos> movimientosCollection;

}