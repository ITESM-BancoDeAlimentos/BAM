package com.caritas.entity;

import com.caritas.entity.Donantes;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(ReciboDeducible.class)
public class ReciboDeducible_ { 

    public static volatile SingularAttribute<ReciboDeducible, BigDecimal> kilos;
    public static volatile SingularAttribute<ReciboDeducible, String> observacion;
    public static volatile SingularAttribute<ReciboDeducible, Date> fecha;
    public static volatile SingularAttribute<ReciboDeducible, BigDecimal> cantidad;
    public static volatile SingularAttribute<ReciboDeducible, BigDecimal> piezas;
    public static volatile SingularAttribute<ReciboDeducible, String> cantLetra;
    public static volatile SingularAttribute<ReciboDeducible, String> iDFolio;
    public static volatile SingularAttribute<ReciboDeducible, Donantes> iDDonante;

}