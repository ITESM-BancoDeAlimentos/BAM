/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.EncSocioNutriciaFam;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tecnologia
 */
@Stateless
public class EncSocioNutriciaFamFacade extends AbstractFacade<EncSocioNutriciaFam> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EncSocioNutriciaFamFacade() {
        super(EncSocioNutriciaFam.class);
    }
    
}
