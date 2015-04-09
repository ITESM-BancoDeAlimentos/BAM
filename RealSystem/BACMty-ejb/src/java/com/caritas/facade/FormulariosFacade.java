/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.Formularios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tecnologia
 */
@Stateless
public class FormulariosFacade extends AbstractFacade<Formularios> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FormulariosFacade() {
        super(Formularios.class);
    }
    
}
