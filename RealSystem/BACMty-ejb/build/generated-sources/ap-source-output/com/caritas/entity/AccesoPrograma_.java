package com.caritas.entity;

import com.caritas.entity.Areas;
import com.caritas.entity.Programas;
import com.caritas.entity.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(AccesoPrograma.class)
public class AccesoPrograma_ { 

    public static volatile SingularAttribute<AccesoPrograma, Programas> iDPrograma;
    public static volatile SingularAttribute<AccesoPrograma, Areas> iDArea;
    public static volatile SingularAttribute<AccesoPrograma, Usuarios> iDUsuario;
    public static volatile SingularAttribute<AccesoPrograma, Integer> iDAccesoPrograma;

}