package com.caritas.entity;

import com.caritas.entity.GrupoInst;
import com.caritas.entity.Movimientos;
import com.caritas.entity.Recibo;
import com.caritas.entity.SubAreas;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Instituciones.class)
public class Instituciones_ { 

    public static volatile SingularAttribute<Instituciones, String> ciudadFis;
    public static volatile SingularAttribute<Instituciones, String> direccion;
    public static volatile SingularAttribute<Instituciones, String> estadoFis;
    public static volatile SingularAttribute<Instituciones, GrupoInst> iDGrupoInst;
    public static volatile SingularAttribute<Instituciones, String> iDInstitucion;
    public static volatile CollectionAttribute<Instituciones, Recibo> reciboCollection;
    public static volatile SingularAttribute<Instituciones, SubAreas> iDSubArea;
    public static volatile SingularAttribute<Instituciones, String> rfc;
    public static volatile SingularAttribute<Instituciones, String> colonia;
    public static volatile SingularAttribute<Instituciones, String> observacion;
    public static volatile SingularAttribute<Instituciones, String> contacto;
    public static volatile SingularAttribute<Instituciones, Double> descuento;
    public static volatile SingularAttribute<Instituciones, Integer> personas;
    public static volatile SingularAttribute<Instituciones, String> familia;
    public static volatile SingularAttribute<Instituciones, Double> saldoPermitido;
    public static volatile SingularAttribute<Instituciones, Double> donativoPermitido;
    public static volatile SingularAttribute<Instituciones, String> mail;
    public static volatile SingularAttribute<Instituciones, String> ciudad;
    public static volatile SingularAttribute<Instituciones, String> horario;
    public static volatile SingularAttribute<Instituciones, String> fax;
    public static volatile SingularAttribute<Instituciones, String> telefono;
    public static volatile SingularAttribute<Instituciones, String> director;
    public static volatile SingularAttribute<Instituciones, String> coloniaFis;
    public static volatile SingularAttribute<Instituciones, String> direccionFis;
    public static volatile SingularAttribute<Instituciones, BigDecimal> saldo;
    public static volatile SingularAttribute<Instituciones, String> tipoInstitucion;
    public static volatile SingularAttribute<Instituciones, Boolean> activo;
    public static volatile SingularAttribute<Instituciones, String> razonFis;
    public static volatile SingularAttribute<Instituciones, String> institucion;
    public static volatile SingularAttribute<Instituciones, String> estado;
    public static volatile SingularAttribute<Instituciones, Date> fechaAlta;
    public static volatile CollectionAttribute<Instituciones, Movimientos> movimientosCollection;

}