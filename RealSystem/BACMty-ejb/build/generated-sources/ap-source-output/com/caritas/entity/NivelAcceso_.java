package com.caritas.entity;

import com.caritas.entity.Accesos;
import com.caritas.entity.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(NivelAcceso.class)
public class NivelAcceso_ { 

    public static volatile CollectionAttribute<NivelAcceso, Accesos> accesosCollection;
    public static volatile CollectionAttribute<NivelAcceso, Usuarios> usuariosCollection;
    public static volatile SingularAttribute<NivelAcceso, String> iDNivel;
    public static volatile SingularAttribute<NivelAcceso, String> nivel;

}