package com.caritas.entity;

import com.caritas.entity.Donantes;
import com.caritas.entity.Sucursales;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(DistribucionAMBA.class)
public class DistribucionAMBA_ { 

    public static volatile SingularAttribute<DistribucionAMBA, Integer> totalPiezas;
    public static volatile SingularAttribute<DistribucionAMBA, Integer> totalPoblacion;
    public static volatile SingularAttribute<DistribucionAMBA, Double> totalKilogramos;
    public static volatile SingularAttribute<DistribucionAMBA, String> iDFolio;
    public static volatile SingularAttribute<DistribucionAMBA, Date> fechaMov;
    public static volatile SingularAttribute<DistribucionAMBA, Double> totalFlete;
    public static volatile SingularAttribute<DistribucionAMBA, Sucursales> iDSucursales;
    public static volatile SingularAttribute<DistribucionAMBA, Donantes> iDDonante;
    public static volatile SingularAttribute<DistribucionAMBA, String> cedisOrigen;

}