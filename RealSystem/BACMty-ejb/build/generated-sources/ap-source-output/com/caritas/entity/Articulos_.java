package com.caritas.entity;

import com.caritas.entity.Donantes;
import com.caritas.entity.GrupoAlim;
import com.caritas.entity.GrupoRef;
import com.caritas.entity.ReciboDet;
import com.caritas.entity.Unidad;
import com.caritas.entity.Variedad;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Articulos.class)
public class Articulos_ { 

    public static volatile SingularAttribute<Articulos, Double> pMercado1;
    public static volatile SingularAttribute<Articulos, Double> cantidadUni;
    public static volatile SingularAttribute<Articulos, Double> pMercado2;
    public static volatile SingularAttribute<Articulos, String> articulo;
    public static volatile SingularAttribute<Articulos, Variedad> iDVariedad;
    public static volatile SingularAttribute<Articulos, Unidad> unidadMed2;
    public static volatile SingularAttribute<Articulos, Unidad> unidadMed1;
    public static volatile SingularAttribute<Articulos, Double> factor;
    public static volatile SingularAttribute<Articulos, Donantes> iDDonante;
    public static volatile CollectionAttribute<Articulos, ReciboDet> reciboDetCollection;
    public static volatile SingularAttribute<Articulos, Double> peso;
    public static volatile SingularAttribute<Articulos, String> iDArticulo;
    public static volatile SingularAttribute<Articulos, GrupoRef> iDGrupoRef;
    public static volatile SingularAttribute<Articulos, Double> costoBenef1;
    public static volatile SingularAttribute<Articulos, GrupoAlim> iDGrupoAlim;
    public static volatile SingularAttribute<Articulos, Double> costoBenef2;
    public static volatile SingularAttribute<Articulos, String> localizacion;
    public static volatile SingularAttribute<Articulos, Double> cuotaRecup2;
    public static volatile SingularAttribute<Articulos, Double> cuotaRecup1;
    public static volatile SingularAttribute<Articulos, String> almacen;
    public static volatile SingularAttribute<Articulos, Double> reorden1;
    public static volatile SingularAttribute<Articulos, Double> reorden2;

}