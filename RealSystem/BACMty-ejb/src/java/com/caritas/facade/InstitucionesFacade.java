/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.Instituciones;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tecnologia
 */
@Stateless
public class InstitucionesFacade extends AbstractFacade<Instituciones> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InstitucionesFacade() {
        super(Instituciones.class);
    }

    public List<Instituciones> findLike(String query, int suggestionsLength) {
        List<Instituciones> resultList =  em.createNamedQuery("Instituciones.findLike")
                .setParameter("query", "%" + query + "%")
                .setMaxResults(suggestionsLength)
                .getResultList();
        return resultList;
    }
    
}
