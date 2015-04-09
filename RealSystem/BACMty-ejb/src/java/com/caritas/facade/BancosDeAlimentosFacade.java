/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.BancosDeAlimentos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tecnologia
 */
@Stateless
public class BancosDeAlimentosFacade extends AbstractFacade<BancosDeAlimentos> {

    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BancosDeAlimentosFacade() {
        super(BancosDeAlimentos.class);
    }

    public List<BancosDeAlimentos> findLike(String query, int length) {
        if (query == null || query.isEmpty()) {
            return em.createNamedQuery("BancosDeAlimentos.findAll", BancosDeAlimentos.class)
                    .setMaxResults(length)
                    .getResultList();
        } else {
            return em.createNamedQuery("BancosDeAlimentos.findLike", BancosDeAlimentos.class)
                    .setParameter("query", "%" + query + "%")
                    .setMaxResults(length)
                    .getResultList();

        }
    }
}
