/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.RelacionRepVar;
import com.caritas.entity.Variedad;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author software
 */
@Stateless
public class RelacionRepVarFacade extends AbstractFacade<RelacionRepVar> {
    private static final Logger LOG = Logger.getLogger(RelacionRepVarFacade.class.getName());
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;
    
    public RelacionRepVarFacade() {
        super(RelacionRepVar.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public RelacionRepVar findByReporteVariedad(String reporte, Variedad variedad) {
        List<RelacionRepVar> resultList = em
                .createNamedQuery("RelacionRepVar.findByReporteVariedad")
                .setParameter("reporte", reporte)
                .setParameter("variedad", variedad)
                .getResultList();
        if (resultList.size() > 1) {
            LOG.log(Level.WARNING, "{0} tiene mas de una clase para {1}", new Object[]{reporte, variedad});
        }
        return resultList.get(0);
    }

    public Variedad findVariedad(String variedad) {
        Query q = getEntityManager().createNamedQuery("Variedad.findByVariedad");
        
        q.setParameter("variedad", variedad);
        return (Variedad) q.getSingleResult();
    }
    
}
