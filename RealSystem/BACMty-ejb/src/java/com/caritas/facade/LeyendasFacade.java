/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.Leyendas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tecnologia
 */
@Stateless
public class LeyendasFacade extends AbstractFacade<Leyendas> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LeyendasFacade() {
        super(Leyendas.class);
    }
    
}
