/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.BancosDeAlimentos;
import com.caritas.entity.DistribucionAMBA;
import com.caritas.entity.DistribucionAMBADet;
import com.caritas.entity.Donantes;
import com.caritas.entity.Movimientos;
import com.caritas.entity.MovtosDet;
import java.util.ArrayList;
import java.util.List;
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
public class DistribucionAMBAFacade extends AbstractFacade<DistribucionAMBA> {
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DistribucionAMBAFacade() {
        super(DistribucionAMBA.class);
    }
    
    public List<BancosDeAlimentos> findBancosDeAlimentos() {
        Query q = getEntityManager().createNamedQuery("BancosDeAlimentos.findAll");
        
        return q.getResultList();
    }
    
    public Movimientos findMovimientoEntradaAMBA(String iDFolio) {
        Query q = getEntityManager().createNamedQuery("Movimientos.findByIDFolioTipoAMBA");

        q.setParameter("iDFolio", iDFolio);
        q.setParameter("tipoMov", "ENT");
        q.setParameter("origen", "A");

        try {
            return (Movimientos) q.getSingleResult();
        } catch (NoResultException nre) {
            return new Movimientos();
        } catch (NonUniqueResultException nue) {
            return new Movimientos();
        }
    }
    
    public double findKilosMovtosDet(String iDFolio) {
        Query q = getEntityManager().createNamedQuery("BancosDeAlimentos.findMovtoDetByFolio");
        
        q.setParameter("iDFolio", iDFolio);
        q.setParameter("tipoMov", "ENT");
        
        List<MovtosDet> movtosDet = q.getResultList();
        
        double kilosTotales = 0.0;
        
        for (MovtosDet movDet : movtosDet) {
            kilosTotales += movDet.getCantidad() * movDet.getPeso();
        }
        
        return kilosTotales;
    }
    
    public int findPiezasMovtosDet(String iDFolio) {
        Query q = getEntityManager().createNamedQuery("BancosDeAlimentos.findMovtoDetByFolio");
        
        q.setParameter("iDFolio", iDFolio);
        q.setParameter("tipoMov", "ENT");
        
        List<MovtosDet> movtosDet = q.getResultList();
        
        int piezasTotales = 0;
        
        for (MovtosDet movDet : movtosDet) {
            piezasTotales += movDet.getCantidad();
        }
        
        return piezasTotales;
    }
    
    public List<String> findFoliosEntAmbaLike(String query) {
        Query q = getEntityManager().createNamedQuery("Movimientos.findFoliosLike");
        q.setParameter("tipoMov", "ENT");
        q.setParameter("iDArea", "AMBA");
        q.setParameter("query", "%" +query +"%");
        
        return q.getResultList();
    }
    
    public List<String> findAllDonantes() {
        Query q = getEntityManager().createNamedQuery("DistribucionAMBA.findAllDonantes");
        
        return q.getResultList();
    }
    
    public List<String> findSucursales(Donantes donante) {
        Query q = getEntityManager().createNamedQuery("DistribucionAMBA.findSucursales");
        q.setParameter("iDDonante", donante);

        return q.getResultList();
    }

    public ArrayList<DistribucionAMBA> findItemsByFolio(String iDFolio) {
        Query q = getEntityManager().createNamedQuery("DistribucionAMBA.findByIDFolio");
        q.setParameter("iDFolio", iDFolio);
        
        try {
            return (ArrayList<DistribucionAMBA>) q.getResultList();
        } catch (Exception e) {
            return new ArrayList<DistribucionAMBA>();
        }
    }

    public boolean findRepetido(String iDFolio) {
        Query q = getEntityManager().createNamedQuery("DistribucionAMBA.findByIDFolio");
        q.setParameter("iDFolio", iDFolio);
        try {
            q.getSingleResult();
            // Si encuentra mínimo uno, está repetido
            return true;
        } catch (NonUniqueResultException nure) {
            // Si encuentra más de uno, está repetido
            return true;
        } catch (NoResultException nre) {
            // Si no encuentra ninguno, no está repetido (duh)
            return false;
        } 
    }

    public List<DistribucionAMBADet> findDetalle(String iDFolio) {
        Query q = getEntityManager().createNamedQuery("DistribucionAMBADet.findByIDFolio");
        q.setParameter("iDFolio", iDFolio);
        
        return q.getResultList();
    }
    
}
