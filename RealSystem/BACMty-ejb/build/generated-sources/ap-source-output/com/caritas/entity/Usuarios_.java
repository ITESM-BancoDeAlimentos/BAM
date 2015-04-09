package com.caritas.entity;

import com.caritas.entity.AccesoPrograma;
import com.caritas.entity.EncSocioNutricia;
import com.caritas.entity.Movimientos;
import com.caritas.entity.NivelAcceso;
import com.caritas.entity.Recibo;
import com.caritas.entity.TmpMovtoArt;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2015-02-21T12:34:28")
@StaticMetamodel(Usuarios.class)
public class Usuarios_ { 

    public static volatile SingularAttribute<Usuarios, String> nombre;
    public static volatile SingularAttribute<Usuarios, String> iDZona;
    public static volatile CollectionAttribute<Usuarios, TmpMovtoArt> tmpMovtoArtCollection;
    public static volatile CollectionAttribute<Usuarios, Recibo> reciboCollection;
    public static volatile CollectionAttribute<Usuarios, AccesoPrograma> accesoProgramaCollection;
    public static volatile CollectionAttribute<Usuarios, EncSocioNutricia> encSocioNutriciaCollection;
    public static volatile SingularAttribute<Usuarios, String> password;
    public static volatile CollectionAttribute<Usuarios, Movimientos> movimientosCollection;
    public static volatile SingularAttribute<Usuarios, String> iDUsuario;
    public static volatile SingularAttribute<Usuarios, NivelAcceso> iDNivel;

}