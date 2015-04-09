/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.Variedad;
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
public class VariedadFacade extends AbstractFacade<Variedad> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VariedadFacade() {
        super(Variedad.class);
    }

    public List<String> findVariedades() {
        Query q = getEntityManager().createNamedQuery("Variedad.findVariedad");
        return q.getResultList();
    }
    
    public List<Variedad> findLike(String query, int length) {
        return em.createNamedQuery("Variedad.findLike")
                .setParameter("query", "%" + query + "%")
                .setMaxResults(length)
                .getResultList();
    }

}
