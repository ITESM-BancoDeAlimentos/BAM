/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.Articulos;
import com.caritas.entity.ReciboDet;
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
public class ReciboDetFacade extends AbstractFacade<ReciboDet> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReciboDetFacade() {
        super(ReciboDet.class);
    }

    public Articulos findArticulo(String iDArticulo) {
        Query q = getEntityManager().createNamedQuery("ReciboDet.findArticulo");
        
        q.setParameter("iDArticulo", iDArticulo);
        
        return (Articulos) q.getSingleResult();
    }
    
    public List<ReciboDet> findByIDFolio(String iDFolio) {
        Query q = getEntityManager().createNamedQuery("ReciboDet.findByIDFolioTipo");
        q.setParameter("iDFolio", iDFolio);
        q.setParameter("tipoMov", "ENT");
        
        return q.getResultList();
    }
    
}
