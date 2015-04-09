/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.GrupoRef;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tecnologia
 */
@Stateless
public class GrupoRefFacade extends AbstractFacade<GrupoRef> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GrupoRefFacade() {
        super(GrupoRef.class);
    }
    
    public List<GrupoRef> findLike(String query, int size) {
        return em.createNamedQuery("GrupoRef.findLike")
                .setParameter("query", "%" + query + "%")
                .setMaxResults(size)
                .getResultList();
    }

}
