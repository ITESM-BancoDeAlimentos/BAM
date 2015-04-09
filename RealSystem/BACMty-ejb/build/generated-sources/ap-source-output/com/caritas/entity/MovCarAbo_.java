package com.caritas.entity;

import com.caritas.entity.Areas;
import com.caritas.entity.Instituciones;
import com.caritas.entity.Programas;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(MovCarAbo.class)
public class MovCarAbo_ { 

    public static volatile SingularAttribute<MovCarAbo, BigDecimal> importe;
    public static volatile SingularAttribute<MovCarAbo, BigDecimal> bonificacion;
    public static volatile SingularAttribute<MovCarAbo, Instituciones> iDInstitucion;
    public static volatile SingularAttribute<MovCarAbo, String> status;
    public static volatile SingularAttribute<MovCarAbo, Integer> iDMovCarAbo;
    public static volatile SingularAttribute<MovCarAbo, String> iDFolioCarAbo;
    public static volatile SingularAttribute<MovCarAbo, Areas> iDArea;
    public static volatile SingularAttribute<MovCarAbo, String> iDFolio;
    public static volatile SingularAttribute<MovCarAbo, Date> fechaMov;
    public static volatile SingularAttribute<MovCarAbo, BigDecimal> saldo;
    public static volatile SingularAttribute<MovCarAbo, String> tipoMov;
    public static volatile SingularAttribute<MovCarAbo, String> referencia;
    public static volatile SingularAttribute<MovCarAbo, Programas> iDPrograma;
    public static volatile SingularAttribute<MovCarAbo, BigDecimal> donativo;
    public static volatile SingularAttribute<MovCarAbo, BigDecimal> incobrable;
    public static volatile SingularAttribute<MovCarAbo, BigDecimal> abono;

}