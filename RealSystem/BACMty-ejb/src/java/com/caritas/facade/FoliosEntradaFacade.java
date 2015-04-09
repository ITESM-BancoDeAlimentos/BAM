/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.FoliosEntrada;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tecnologia
 */
@Stateless
public class FoliosEntradaFacade extends AbstractFacade<FoliosEntrada> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FoliosEntradaFacade() {
        super(FoliosEntrada.class);
    }
    
}
