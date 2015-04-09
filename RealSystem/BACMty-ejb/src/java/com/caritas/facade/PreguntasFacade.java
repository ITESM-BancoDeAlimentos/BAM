/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.caritas.facade;

import com.caritas.entity.Preguntas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author software
 */
@Stateless
public class PreguntasFacade extends AbstractFacade<Preguntas> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    public PreguntasFacade() {
        super(Preguntas.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Preguntas> findByIDPregunta(Preguntas preguntas) {
        try {
            Query q = em.createNamedQuery("Preguntas.findByIDPregunta");
            q.setParameter("iDPregunta", preguntas.getIDPregunta());
            return q.getResultList();
        } catch(Exception e) {
            return null;
        }
    }
}
