/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.BancosDeAlimentos;
import com.caritas.entity.DistribucionAMBADet;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author tecnologia
 */
@Stateless
public class DistribucionAMBADetFacade extends AbstractFacade<DistribucionAMBADet> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DistribucionAMBADetFacade() {
        super(DistribucionAMBADet.class);
    }

    public BancosDeAlimentos findBancoDeAlimentos(String nombre) {
        Query q = getEntityManager().createNamedQuery("BancosDeAlimentos.findByNombre");
        
        q.setParameter("nombre", nombre);
        
        return (BancosDeAlimentos) q.getSingleResult();
    }
    
}
