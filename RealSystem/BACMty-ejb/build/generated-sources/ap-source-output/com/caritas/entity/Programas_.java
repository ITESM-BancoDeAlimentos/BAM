package com.caritas.entity;

import com.caritas.entity.AccesoPrograma;
import com.caritas.entity.AreaPrograma;
import com.caritas.entity.Cajas;
import com.caritas.entity.DisEntradas;
import com.caritas.entity.Distribucion;
import com.caritas.entity.Existencias;
import com.caritas.entity.MovCarAbo;
import com.caritas.entity.Movimientos;
import com.caritas.entity.MovtosDet;
import com.caritas.entity.Recibo;
import com.caritas.entity.ReciboDet;
import com.caritas.entity.TmpMovtoArt;
import com.caritas.entity.Vales;
import com.caritas.entity.ValesCajas;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Programas.class)
public class Programas_ { 

    public static volatile CollectionAttribute<Programas, Distribucion> distribucionCollection;
    public static volatile CollectionAttribute<Programas, MovtosDet> movtosDetCollection;
    public static volatile CollectionAttribute<Programas, MovCarAbo> movCarAboCollection;
    public static volatile CollectionAttribute<Programas, Vales> valesCollection;
    public static volatile CollectionAttribute<Programas, TmpMovtoArt> tmpMovtoArtCollection;
    public static volatile CollectionAttribute<Programas, DisEntradas> disEntradasCollection;
    public static volatile CollectionAttribute<Programas, Recibo> reciboCollection;
    public static volatile CollectionAttribute<Programas, AccesoPrograma> accesoProgramaCollection;
    public static volatile CollectionAttribute<Programas, ValesCajas> valesCajasCollection;
    public static volatile CollectionAttribute<Programas, ReciboDet> reciboDetCollection;
    public static volatile SingularAttribute<Programas, String> programa;
    public static volatile SingularAttribute<Programas, String> iDPrograma;
    public static volatile CollectionAttribute<Programas, AreaPrograma> areaProgramaCollection;
    public static volatile CollectionAttribute<Programas, Cajas> cajasCollection;
    public static volatile CollectionAttribute<Programas, Existencias> existenciasCollection;
    public static volatile CollectionAttribute<Programas, Movimientos> movimientosCollection;

}