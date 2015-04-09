/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.EncSocioNutriciaSeg;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tecnologia
 */
@Stateless
public class EncSocioNutriciaSegFacade extends AbstractFacade<EncSocioNutriciaSeg> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EncSocioNutriciaSegFacade() {
        super(EncSocioNutriciaSeg.class);
    }
    
}
