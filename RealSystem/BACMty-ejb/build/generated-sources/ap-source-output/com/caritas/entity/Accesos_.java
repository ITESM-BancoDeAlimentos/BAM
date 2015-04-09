package com.caritas.entity;

import com.caritas.entity.Formularios;
import com.caritas.entity.NivelAcceso;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Accesos.class)
public class Accesos_ { 

    public static volatile SingularAttribute<Accesos, Boolean> imprimir;
    public static volatile SingularAttribute<Accesos, Boolean> editar;
    public static volatile SingularAttribute<Accesos, Boolean> cancelar;
    public static volatile SingularAttribute<Accesos, Boolean> nuevo;
    public static volatile SingularAttribute<Accesos, Boolean> borrar;
    public static volatile SingularAttribute<Accesos, Boolean> subMenu;
    public static volatile SingularAttribute<Accesos, Integer> iDAccesos;
    public static volatile SingularAttribute<Accesos, Boolean> grabar;
    public static volatile SingularAttribute<Accesos, NivelAcceso> iDNivel;
    public static volatile SingularAttribute<Accesos, Formularios> iDForm;

}