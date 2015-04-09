/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.GrupoAlim;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tecnologia
 */
@Stateless
public class GrupoAlimFacade extends AbstractFacade<GrupoAlim> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GrupoAlimFacade() {
        super(GrupoAlim.class);
    }
    
    public List<GrupoAlim> findLike(String query, int length) {
        return em.createNamedQuery("GrupoAlim.findLike")
                .setParameter("query", "%" + query + "%")
                .setMaxResults(length)
                .getResultList();
    }
}
