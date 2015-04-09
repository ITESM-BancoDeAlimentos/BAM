/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.Unidad;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tecnologia
 */
@Stateless
public class UnidadFacade extends AbstractFacade<Unidad> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UnidadFacade() {
        super(Unidad.class);
    }
    
    public List<Unidad> findLike(String query, int length) {
        return em.createNamedQuery("Unidad.findLike")
                .setParameter("query", "%" + query + "%")
                .setMaxResults(length)
                .getResultList();
    }
    
}
