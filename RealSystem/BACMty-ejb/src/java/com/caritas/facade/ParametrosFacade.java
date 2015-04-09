package com.caritas.facade;

import com.caritas.entity.Parametros;
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
public class ParametrosFacade extends AbstractFacade<Parametros> {

    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    public ParametrosFacade() {
        super(Parametros.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public String find(String param) {
        return em.find(Parametros.class, param).getValor();
    }

    public boolean findRemoved(String parametro) {
        Query q = getEntityManager().createNamedQuery("Parametros.findByParametro");
        q.setParameter("parametro", parametro);
        
        return q.getResultList().size() < 1;
    }

    public List<String> findParametros() {
        Query q = getEntityManager().createNamedQuery("Parametros.findParametros");
        
        return q.getResultList();
    }
}
