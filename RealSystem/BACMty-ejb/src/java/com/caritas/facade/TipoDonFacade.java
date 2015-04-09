/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.TipoDon;
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
public class TipoDonFacade extends AbstractFacade<TipoDon> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoDonFacade() {
        super(TipoDon.class);
    }
    
    public List<String> findID() {
        Query q = getEntityManager().createNamedQuery("TipoDon.findAllIDTipoDon");
        return q.getResultList();
}

    public TipoDon findByID(String idTipoDon) {
        Query q = getEntityManager().createNamedQuery("TipoDon.findByIDTipoDon");
        q.setParameter("iDTipoDon", idTipoDon);
        return (TipoDon) q.getSingleResult();
    }

    public int findEqual(String idTipoDon) {
        Query q = getEntityManager().createNamedQuery("TipoDon.findByIDTipoDon");
        q.setParameter("iDTipoDon", idTipoDon);
        return q.getResultList().size();
    }

    public boolean findRemoved(String idTipoDon) {
        Query q = getEntityManager().createNamedQuery("TipoDon.findByIDTipoDon");
        q.setParameter("iDTipoDon", idTipoDon);
        
        return q.getResultList().size() < 1;
    }
    
}
