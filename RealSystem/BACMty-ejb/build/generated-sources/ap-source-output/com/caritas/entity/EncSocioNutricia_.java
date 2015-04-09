package com.caritas.entity;

import com.caritas.entity.EncSocioNutriciaFam;
import com.caritas.entity.EncSocioNutriciaInd;
import com.caritas.entity.EncSocioNutriciaPK;
import com.caritas.entity.EncSocioNutriciaSeg;
import com.caritas.entity.Instituciones;
import com.caritas.entity.Usuarios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(EncSocioNutricia.class)
public class EncSocioNutricia_ { 

    public static volatile SingularAttribute<EncSocioNutricia, Instituciones> iDInstitucion;
    public static volatile SingularAttribute<EncSocioNutricia, Boolean> status;
    public static volatile SingularAttribute<EncSocioNutricia, Usuarios> identificador;
    public static volatile SingularAttribute<EncSocioNutricia, Date> fechaLev;
    public static volatile CollectionAttribute<EncSocioNutricia, EncSocioNutriciaInd> encSocioNutriciaIndCollection;
    public static volatile SingularAttribute<EncSocioNutricia, String> unidad;
    public static volatile SingularAttribute<EncSocioNutricia, String> entrevistado;
    public static volatile SingularAttribute<EncSocioNutricia, EncSocioNutriciaPK> encSocioNutriciaPK;
    public static volatile SingularAttribute<EncSocioNutricia, EncSocioNutriciaSeg> encSocioNutriciaSeg;
    public static volatile SingularAttribute<EncSocioNutricia, EncSocioNutriciaFam> encSocioNutriciaFam;
    public static volatile SingularAttribute<EncSocioNutricia, String> jefeFamilia;
    public static volatile SingularAttribute<EncSocioNutricia, Date> fechaCaptura;

}