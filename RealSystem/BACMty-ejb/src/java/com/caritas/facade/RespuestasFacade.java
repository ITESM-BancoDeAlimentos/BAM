/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.caritas.facade;

import com.caritas.entity.Preguntas;
import com.caritas.entity.Respuestas;
import java.util.Collection;
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
public class RespuestasFacade extends AbstractFacade<Respuestas> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    public RespuestasFacade() {
        super(Respuestas.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Respuestas> findByIDPregunta(Preguntas p) {
        Query q = em.createNamedQuery("Respuestas.findByIDPregunta");
        q.setParameter("iDPregunta", p.getIDPregunta());
        return q.getResultList();
    }
}
