/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.Areas;
import com.caritas.entity.Existencias;
import com.caritas.entity.Programas;
import com.caritas.entity.Articulos;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author tecnologia
 */
@Stateless
public class ExistenciasFacade extends AbstractFacade<Existencias> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExistenciasFacade() {
        super(Existencias.class);
    }

    public Existencias findExistencia(Articulos idArt, Date fechaCad, Areas idArea, Programas idPrograma) {
        Query q = getEntityManager().createNamedQuery("Existencias.findByArtFecArePro");
        
        q.setParameter("iDArticulo", idArt);
        q.setParameter("fechaCad", fechaCad);
        q.setParameter("iDArea", idArea);
        q.setParameter("iDPrograma", idPrograma);
        
        try {
            return (Existencias) q.getSingleResult();
        } catch (NoResultException nre) {
            return new Existencias();
        } catch (NonUniqueResultException nure) {
            return (Existencias) q.getResultList().get(0);
        }
    }
}
