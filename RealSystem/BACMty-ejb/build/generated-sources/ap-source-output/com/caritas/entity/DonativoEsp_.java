package com.caritas.entity;

import com.caritas.entity.Donantes;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(DonativoEsp.class)
public class DonativoEsp_ { 

    public static volatile SingularAttribute<DonativoEsp, String> iDSucursal;
    public static volatile SingularAttribute<DonativoEsp, String> horarioAcopio;
    public static volatile SingularAttribute<DonativoEsp, String> contacto;
    public static volatile SingularAttribute<DonativoEsp, String> horarioDescanso;
    public static volatile SingularAttribute<DonativoEsp, Date> fecha;
    public static volatile SingularAttribute<DonativoEsp, String> observaciones;
    public static volatile SingularAttribute<DonativoEsp, String> iDFolio;
    public static volatile SingularAttribute<DonativoEsp, Donantes> iDDonante;

}