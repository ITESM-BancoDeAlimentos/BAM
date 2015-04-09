/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.Usuarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author tecnologia
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }
    
    public Usuarios validateUser(String username) {
        try {
        TypedQuery<Usuarios> q = getEntityManager().createNamedQuery("Usuarios.findByIDUsuario", Usuarios.class);
        q.setParameter("iDUsuario", username);
        Usuarios user = q.getSingleResult();
        return user;
        } catch (javax.persistence.NoResultException noResult) {
            System.out.println(noResult.getMessage());
            return null;
        }
    }
    
}
