package com.caritas.entity;

import com.caritas.entity.Articulos;
import com.caritas.entity.Cajas;
import com.caritas.entity.RelacionRepVar;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Variedad.class)
public class Variedad_ { 

    public static volatile CollectionAttribute<Variedad, Articulos> articulosCollection;
    public static volatile SingularAttribute<Variedad, String> iDVariedad;
    public static volatile SingularAttribute<Variedad, String> variedad;
    public static volatile ListAttribute<Variedad, RelacionRepVar> relacionRepVarList;
    public static volatile CollectionAttribute<Variedad, Cajas> cajasCollection;
    public static volatile SingularAttribute<Variedad, Double> costo;

}