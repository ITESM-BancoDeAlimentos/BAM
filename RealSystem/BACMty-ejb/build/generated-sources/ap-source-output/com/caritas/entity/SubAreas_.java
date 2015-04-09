package com.caritas.entity;

import com.caritas.entity.Areas;
import com.caritas.entity.Instituciones;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(SubAreas.class)
public class SubAreas_ { 

    public static volatile SingularAttribute<SubAreas, String> subArea;
    public static volatile CollectionAttribute<SubAreas, Instituciones> institucionesCollection;
    public static volatile SingularAttribute<SubAreas, String> iDSubArea;
    public static volatile SingularAttribute<SubAreas, Areas> iDArea;

}