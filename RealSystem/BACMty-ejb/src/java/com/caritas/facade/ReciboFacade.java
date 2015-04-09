/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.Areas;
import com.caritas.entity.Articulos;
import com.caritas.entity.Donantes;
import com.caritas.entity.FoliosEntrada;
import com.caritas.entity.Programas;
import com.caritas.entity.Recibo;
import com.caritas.entity.ReciboDet;
import com.caritas.entity.Sucursales;
import com.caritas.entity.Usuarios;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author tecnologia
 */
@Stateless
public class ReciboFacade extends AbstractFacade<Recibo> {

    private final short TIENDAS = 1;
    private final short AMBA = 2;
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;
    private Query q;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReciboFacade() {
        super(Recibo.class);
    }

    public List<String> findRecibos() {
        q = getEntityManager().createNamedQuery("Recibo.findFolios");

        return q.getResultList();
    }

    public List<String> findRecibosByOrigen(short pantallaActual) {
        q = getEntityManager().createNamedQuery("Recibo.findFoliosByOrigen");

        String origen = null;

        switch (pantallaActual) {
            case TIENDAS:
                origen = "D";
                break;
            case AMBA:
                origen = "A";
                break;
        }

        q.setParameter("origen", origen);

        return q.getResultList();
    }

    public List<String> findAreas() {
        q = getEntityManager().createNamedQuery("Recibo.findAreas");

        return q.getResultList();
    }

    public Areas findIDArea(String area) {
        q = getEntityManager().createNamedQuery("Areas.findByArea");
        q.setParameter("area", area);

        return (Areas) q.getSingleResult();
    }

    public List<String> findProgramas(Areas area) {
        q = getEntityManager().createNamedQuery("Recibo.findProgramas");
        q.setParameter("iDArea", area);

        return q.getResultList();
    }

    public Programas findIDPrograma(String programa) {
        q = getEntityManager().createNamedQuery("Programas.findByPrograma");
        q.setParameter("programa", programa);

        return (Programas) q.getSingleResult();
    }

    public List<String> findDonantesTiendas() {
        q = getEntityManager().createNamedQuery("Recibo.findDonantesTie");
        q.setParameter("tipoDonante", "AUTO");

        return q.getResultList();
    }
    
    public List<String> findDonantesAMBA() {
        q = getEntityManager().createNamedQuery("Recibo.findDonantesAMBA");
        
        return q.getResultList();
    }

    public Donantes findIDDonante(String donante) {
        q = getEntityManager().createNamedQuery("Donantes.findByDonante");
        q.setParameter("donante", donante);

        return (Donantes) q.getSingleResult();
    }

    public List<String> findSucursales(Donantes donante) {
        q = getEntityManager().createNamedQuery("Recibo.findSucursales");
        q.setParameter("iDDonante", donante);

        return q.getResultList();
    }

    public Sucursales findIDSucursales(String sucursal, Donantes donante) {
        q = getEntityManager().createNamedQuery("Recibo.findByIDSucursalIDDonante");
        q.setParameter("iDSucursal", sucursal);
        q.setParameter("iDDonante", donante);

        return (Sucursales) q.getSingleResult();
    }

    public Articulos findArticulos(String descripcion) {
        q = getEntityManager().createNamedQuery("Recibo.findArticuloByArticulo");
        q.setParameter("articulo", descripcion);

        try {
            return (Articulos) q.getSingleResult();
        } catch (NonUniqueResultException nure) {
            List<Articulos> lista = q.getResultList();

            // regresa el primer artículo de la lista
            return lista.get(0);
        }
    }

    public List<String> findArticulos() {
        q = getEntityManager().createNamedQuery("Articulos.findArticulos");

        return q.getResultList();
    }

    public List<Articulos> findArticulosByFolio(String iDFolio, List<ReciboDet> listaReciboDet) {
//        q = getEntityManager().createNamedQuery("ReciboDet.findByIDFolioTipo");
//        q.setParameter("iDFolio", iDFolio);
//        q.setParameter("tipoMov", "ENT");
//        List<ReciboDet> listaReciboDet = findReciboDetByFolio(iDFolio); //q.getResultList();

        List<Articulos> listaArticulos = new ArrayList<Articulos>();

        for (ReciboDet temp : listaReciboDet) {
            listaArticulos.add(temp.getIDArticulo());
        }

        return listaArticulos;
    }

    public List<ReciboDet> findReciboDetByFolio(String iDFolio) {
        q = getEntityManager().createNamedQuery("ReciboDet.findByIDFolioTipo");
        q.setParameter("iDFolio", iDFolio);
        q.setParameter("tipoMov", "ENT");

        return q.getResultList();
    }

    public List<String> findArticulosLike(String query) {
        q = getEntityManager().createNamedQuery("Recibo.findArticulosLike");
        q.setParameter("query", "%" +query +"%");

        return q.getResultList();
    }

    public Usuarios getUsuarioNombre(String idUsuario) {
        q = getEntityManager().createNamedQuery("Usuarios.findByIDUsuario");
        q.setParameter("iDUsuario", idUsuario);

        return (Usuarios) q.getSingleResult();
    }

    /**
     * Función que verifica si se encuentra un folio de tipo Entrada en la tabla
     * de Recibo y/o Movimientos
     *
     * @param idFolio El folio a buscar
     * @return <code>true</code> si está repetido, en caso      * contrario, <code>False</code>
     */
    public boolean findFolioRepetido(String idFolio) {
        q = getEntityManager().createNamedQuery("Recibo.findByIDFolioTipoNoOrigen");
        q.setParameter("iDFolio", idFolio);
        q.setParameter("tipoMov", "ENT");
        
        Query r = getEntityManager().createNamedQuery("Movimientos.findByIDFolioTipo");
        r.setParameter("iDFolio", idFolio);
        r.setParameter("tipoMov", "ENT");
        
        return (q.getResultList().size() > 0 || r.getResultList().size() > 0);
    }

    public Recibo findByFolio(String idFolio, short pantallaActual) {
        try {
            q = getEntityManager().createNamedQuery("Recibo.findByIDFolioTipo");
            q.setParameter("iDFolio", idFolio);
            q.setParameter("tipoMov", "ENT");

            String origen = null;

            switch (pantallaActual) {
                case TIENDAS:
                    origen = "D";
                    break;
                case AMBA:
                    origen = "A";
                    break;
            }
            q.setParameter("origen", origen);

            return (Recibo) q.getSingleResult();
        } catch (NoResultException nre) {
            return new Recibo();
        }
    }

    public List<String> findRecibosLike(String query, short pantallaActual) {
        Query q = getEntityManager().createNamedQuery("Recibo.findRecibosLike");
        q.setParameter("iDFolio", "%" + query + "%");
        q.setParameter("tipoMov", "ENT");

        String origen = null;

        switch (pantallaActual) {
            case TIENDAS:
                origen = "D";
                break;
            case AMBA:
                origen = "A";
                break;
        }
        q.setParameter("origen", origen);

        return q.getResultList();
    }

    public String findLastFolioENT() {
        Query q = getEntityManager().createNamedQuery("FoliosEntrada.findAll");

        return ((FoliosEntrada) q.getSingleResult()).getFolioEntrada();
    }
}
