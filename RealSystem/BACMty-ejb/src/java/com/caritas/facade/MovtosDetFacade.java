package com.caritas.facade;

import com.caritas.entity.Movimientos;
import com.caritas.entity.Movimientos_;
import com.caritas.entity.MovtosDet;
import com.caritas.entity.MovtosDet_;
import com.caritas.filters.MovimientosFilters;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author tecnologia
 */
@Stateless
public class MovtosDetFacade extends AbstractFacade<MovtosDet> {

    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    public MovtosDetFacade() {
        super(MovtosDet.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<MovtosDet> findEntByIDFolio(String idFolio) {
        Query q = getEntityManager().createNamedQuery("MovtosDet.findByIDFolioTipo");
        q.setParameter("iDFolio", idFolio);
        q.setParameter("tipoMov", "ENT");
        return q.getResultList();
    }

    public List<MovtosDet> findByIDFolioTipo(String idFolio, String tipoMov) {
        Query q = getEntityManager().createNamedQuery("MovtosDet.findByIDFolioTipo");
        q.setParameter("iDFolio", idFolio);
        q.setParameter("tipoMov", tipoMov);
        return q.getResultList();
    }

    public List<MovtosDet> findByMovimientosRanged(int first, int pageSize, MovimientosFilters f) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<MovtosDet> query = cb.createQuery(MovtosDet.class);
        Root<MovtosDet> mdetRoot = query.from(MovtosDet.class);
        Path<String> tipoMov = mdetRoot.get(MovtosDet_.tipoMov);
        Path<String> iDFolio = mdetRoot.get(MovtosDet_.iDFolio);
        Path<Movimientos> movimientos = mdetRoot.get(MovtosDet_.movimientos);
        Path<Date> fechaMov = movimientos.get(Movimientos_.fechaMov);

        query.where(f.ToPredicateArray(cb, movimientos));
        query.orderBy(cb.asc(fechaMov),cb.asc(iDFolio),cb.asc(tipoMov));
        query.groupBy(movimientos);

        return em.createQuery(query)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public int countByMovimientos(MovimientosFilters f) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<MovtosDet> mdetRoot = query.from(MovtosDet.class);
        Path<Movimientos> movimientos = mdetRoot.get(MovtosDet_.movimientos);

        query.select(cb.count(mdetRoot));
        query.where(f.ToPredicateArray(cb, movimientos));

        return em.createQuery(query).getSingleResult().intValue();
    }
}
