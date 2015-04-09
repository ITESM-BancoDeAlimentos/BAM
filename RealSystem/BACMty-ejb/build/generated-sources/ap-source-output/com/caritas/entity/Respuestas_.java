package com.caritas.entity;

import com.caritas.entity.Preguntas;
import com.caritas.entity.RespuestasPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Respuestas.class)
public class Respuestas_ { 

    public static volatile SingularAttribute<Respuestas, Preguntas> preguntas;
    public static volatile SingularAttribute<Respuestas, String> descripcion;
    public static volatile SingularAttribute<Respuestas, RespuestasPK> respuestasPK;

}