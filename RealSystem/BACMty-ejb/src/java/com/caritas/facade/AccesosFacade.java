/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.Accesos;
import com.caritas.entity.Formularios;
import com.caritas.entity.NivelAcceso;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AccesosFacade extends AbstractFacade<Accesos> {

    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccesosFacade() {
        super(Accesos.class);
    }

    @Override
    public void create(Accesos entity) {
        super.create(entity);
        em.flush();
    }

    @Override
    public void edit(Accesos entity) {
        super.edit(entity);
        em.flush();
    }

    @Override
    public void remove(Accesos entity) {
        super.remove(entity);
        em.flush();
    }

    public List<Accesos> findByIDNivelWeb(NivelAcceso nivel) {
        Query q = getEntityManager().createNamedQuery("Accesos.findByNivelWeb");

        q.setParameter("iDNivel", nivel);
        q.setParameter("iDForm", "web%");

        return q.getResultList();
    }

    public List<Formularios> findFormulariosWeb(NivelAcceso nivel) {
        Query q = getEntityManager().createNamedQuery("Formularios.findLikeIDForm");

        q.setParameter("iDForm", "web%");

        List<Formularios> formularios = q.getResultList();
        List<Accesos> accesos = findByIDNivelWeb(nivel);

        List<Formularios> returnForms = null;

        for (Formularios forma : formularios) {
            boolean repetido = false;

            for (Accesos acceso : accesos) {
                if (forma.getIDForm().equals(acceso.getIDForm().getIDForm())) {
                    repetido = true;
                    break;
                }
            }

            if (!repetido) {
                if (returnForms == null) {
                    returnForms = new ArrayList<Formularios>();
                }
                returnForms.add(forma);
            }
        }

        if (returnForms == null) {
            return new ArrayList<Formularios>();
        } else {
            return returnForms;
        }
    }

    public List<NivelAcceso> findNiveles() {
        Query q = getEntityManager().createNamedQuery("NivelAcceso.findAll");

        return q.getResultList();
    }
}
