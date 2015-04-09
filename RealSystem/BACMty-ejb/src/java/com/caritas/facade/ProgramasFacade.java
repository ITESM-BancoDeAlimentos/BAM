/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.Programas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tecnologia
 */
@Stateless
public class ProgramasFacade extends AbstractFacade<Programas> {

    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProgramasFacade() {
        super(Programas.class);
    }

    public List<Programas> findLike(String query, int length) {
        String name = "%" + query + "%";
        return em.createNamedQuery("Programas.findLike")
                .setParameter("iDPrograma", name)
                .setParameter("programa", name)
                .setMaxResults(length)
                .getResultList();
    }
}
