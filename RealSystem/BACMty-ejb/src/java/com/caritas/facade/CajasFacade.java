/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.Cajas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tecnologia
 */
@Stateless
public class CajasFacade extends AbstractFacade<Cajas> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CajasFacade() {
        super(Cajas.class);
    }
    
}
