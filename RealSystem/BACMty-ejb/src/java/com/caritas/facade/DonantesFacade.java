/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.Donantes;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author tecnologia
 */
@Stateless
public class DonantesFacade extends AbstractFacade<Donantes> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    public DonantesFacade() {
        super(Donantes.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Donantes> findBySucursales(boolean sucursales) {
        Query cq = getEntityManager().createNamedQuery("Donantes.findBySucursales");
        cq.setParameter("sucursales", true);
        return cq.getResultList();
    }

    public List<Donantes> findByTipoDonante(String tipo) {
        List resultList = getEntityManager()
                .createNamedQuery("Donantes.findByTipoDonante")
                .setParameter("tipoDonante", tipo)
                .getResultList();
        return resultList;
    }

    public List<Donantes> findAutoServicios() {
        List resultList = getEntityManager()
                .createQuery("SELECT d FROM Donantes d WHERE d.tipoDonante = :tipoDonante AND d.iDDonante != :dAuto")
                .setParameter("tipoDonante", "AUTO")
                .setParameter("dAuto", "DON009")
                .getResultList();
        return resultList;
    }

    public List<Donantes> findLike(String query, int maxResults) {
        List<Donantes> resultList =  em.createNamedQuery("Donantes.findLike")
                .setParameter("query", "%" + query + "%")
                .setMaxResults(maxResults)
                .getResultList();
        return resultList;
    }
}
