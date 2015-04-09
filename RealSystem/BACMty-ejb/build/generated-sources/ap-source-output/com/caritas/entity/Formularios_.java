package com.caritas.entity;

import com.caritas.entity.Accesos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Formularios.class)
public class Formularios_ { 

    public static volatile SingularAttribute<Formularios, Short> orden;
    public static volatile SingularAttribute<Formularios, Short> tipo;
    public static volatile SingularAttribute<Formularios, String> descripcion;
    public static volatile CollectionAttribute<Formularios, Accesos> accesosCollection;
    public static volatile SingularAttribute<Formularios, String> iDForm;

}