/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.Donantes;
import com.caritas.entity.Sucursales;
import com.caritas.entity.ValeCUDE;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Root;

/**
 *
 * @author tecnologia
 */
@Stateless
public class ValeCUDEFacade extends AbstractFacade<ValeCUDE> {

    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    public ValeCUDEFacade() {
        super(ValeCUDE.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<ValeCUDE> findRangeOrderByFecha(int[] range) {
        javax.persistence.Query q = getEntityManager().createQuery("SELECT v FROM ValeCUDE v ORDER BY v.fecha ASC");
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<ValeCUDE> findRangeByDateRange(Date start, Date end, int first, int pageSize) {
        List<ValeCUDE> resultList = getEntityManager()
                .createNamedQuery("ValeCUDE.findByFechaRange")
                .setParameter("fechaI", start)
                .setParameter("fechaF", end)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
        return resultList;
    }
    
    public int countByDateRange(Date start, Date end) {
        return getEntityManager()
                .createNamedQuery("ValeCUDE.countByFechaRange", Long.class)
                .setParameter("fechaI", start)
                .setParameter("fechaF", end)
                .getSingleResult()
                .intValue();
    }

    public List<ValeCUDE> findByDateRange(Date start, Date end) {
        List<ValeCUDE> resultList = getEntityManager()
                .createNamedQuery("ValeCUDE.findByFechaRange")
                .setParameter("fechaI", start)
                .setParameter("fechaF", end)
                .getResultList();
        return resultList;
    }

    public List<String> findDonantes() {
        Query q = getEntityManager().createNamedQuery("ValeCUDE.findDonantes");

        return q.getResultList();
    }

    public Donantes findIDDonante(String donante) {
        Query q = getEntityManager().createNamedQuery("Donantes.findByDonante");
        q.setParameter("donante", donante);

        return (Donantes) q.getSingleResult();
    }

    public List<String> findSucursales(Donantes donante) {
        Query q = getEntityManager().createNamedQuery("ValeCUDE.findSucursales");
        q.setParameter("iDDonante", donante);

        return q.getResultList();
    }

//    public Sucursales findIDSucursales(String sucursal) {
//        Query q = getEntityManager().createNamedQuery("Sucursales.findByIDSucursal");
//        q.setParameter("iDSucursal", sucursal);
//        
//        return (Sucursales) q.getSingleResult();
//    }
    public Sucursales findIDSucursales(String sucursal, Donantes donante) {
        Query q = getEntityManager().createNamedQuery("ValeCude.findByIDSucursalIDDonante");
        q.setParameter("iDSucursal", sucursal);
        q.setParameter("iDDonante", donante);

        return (Sucursales) q.getSingleResult();
    }

    public String getUsuarioNombre(String idUsuario) {
        Query q = getEntityManager().createQuery("SELECT u.nombre FROM Usuarios u WHERE u.iDUsuario = :iDUsuario");
        q.setParameter("iDUsuario", idUsuario);

        return q.getSingleResult().toString();
    }

    public int findRepetido(String folio) {
        Query q = getEntityManager().createNamedQuery("ValeCUDE.findByFolio");
        q.setParameter("folio", folio);

        return q.getResultList().size();
    }

    public List<ValeCUDE> findByFecha(Date fecha) {
        Query q = getEntityManager().createNamedQuery("ValeCUDE.findByFecha");
        q.setParameter("fecha", fecha);

        return q.getResultList();
    }

    public ValeCUDE findByFolio(String folio) {
        try {
            Query q = getEntityManager().createNamedQuery("ValeCUDE.findByFolio");
            q.setParameter("folio", folio);

            return (ValeCUDE) q.getSingleResult();
        } catch (NoResultException nre) {
            return new ValeCUDE();
        }
    }
}
