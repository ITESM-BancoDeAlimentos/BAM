package com.caritas.entity;

import com.caritas.entity.Articulos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(GrupoAlim.class)
public class GrupoAlim_ { 

    public static volatile SingularAttribute<GrupoAlim, Double> precioRecupera;
    public static volatile CollectionAttribute<GrupoAlim, Articulos> articulosCollection;
    public static volatile SingularAttribute<GrupoAlim, String> idGrupoAlim;
    public static volatile SingularAttribute<GrupoAlim, String> grupoAlim;

}