package com.caritas.entity;

import com.caritas.entity.Respuestas;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Preguntas.class)
public class Preguntas_ { 

    public static volatile SingularAttribute<Preguntas, Integer> iDPregunta;
    public static volatile SingularAttribute<Preguntas, String> descripcion;
    public static volatile ListAttribute<Preguntas, Respuestas> respuestasList;

}