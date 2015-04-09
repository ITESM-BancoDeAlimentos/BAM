package com.caritas.entity;

import com.caritas.entity.Areas;
import com.caritas.entity.Articulos;
import com.caritas.entity.Programas;
import com.caritas.entity.Usuarios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(TmpMovtoArt.class)
public class TmpMovtoArt_ { 

    public static volatile SingularAttribute<TmpMovtoArt, Integer> mov;
    public static volatile SingularAttribute<TmpMovtoArt, Date> fechaCad;
    public static volatile SingularAttribute<TmpMovtoArt, Articulos> iDArticulo;
    public static volatile SingularAttribute<TmpMovtoArt, Integer> iDTmpMovtoArt;
    public static volatile SingularAttribute<TmpMovtoArt, Programas> iDPrograma;
    public static volatile SingularAttribute<TmpMovtoArt, Double> cantidad;
    public static volatile SingularAttribute<TmpMovtoArt, Double> existencia;
    public static volatile SingularAttribute<TmpMovtoArt, String> iDFolio;
    public static volatile SingularAttribute<TmpMovtoArt, Date> fechaMov;
    public static volatile SingularAttribute<TmpMovtoArt, Areas> iDArea;
    public static volatile SingularAttribute<TmpMovtoArt, Usuarios> iDUsuario;
    public static volatile SingularAttribute<TmpMovtoArt, String> tipoMov;

}