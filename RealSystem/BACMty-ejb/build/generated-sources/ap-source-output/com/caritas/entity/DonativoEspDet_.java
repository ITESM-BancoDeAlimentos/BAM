package com.caritas.entity;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(DonativoEspDet.class)
public class DonativoEspDet_ { 

    public static volatile SingularAttribute<DonativoEspDet, Integer> iDDonativoEspDet;
    public static volatile SingularAttribute<DonativoEspDet, BigDecimal> cantidad;
    public static volatile SingularAttribute<DonativoEspDet, String> descripcion;
    public static volatile SingularAttribute<DonativoEspDet, String> producto;
    public static volatile SingularAttribute<DonativoEspDet, BigDecimal> pesoTotal;
    public static volatile SingularAttribute<DonativoEspDet, BigDecimal> costoUnitario;
    public static volatile SingularAttribute<DonativoEspDet, String> iDFolio;

}