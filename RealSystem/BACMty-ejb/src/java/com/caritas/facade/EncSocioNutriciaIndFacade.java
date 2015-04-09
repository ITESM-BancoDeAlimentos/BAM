package com.caritas.facade;

import com.caritas.entity.EncSocioNutricia;
import com.caritas.entity.EncSocioNutriciaInd;
import com.caritas.entity.EncSocioNutriciaIndPK;
import com.caritas.entity.EncSocioNutriciaIndPK_;
import com.caritas.entity.EncSocioNutriciaInd_;
import com.caritas.entity.EncSocioNutricia_;
import com.caritas.entity.Movimientos;
import com.caritas.entity.Movimientos_;
import com.caritas.filters.EncSocioNutriciaFilters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author tecnologia
 */
@Stateless
public class EncSocioNutriciaIndFacade extends AbstractFacade<EncSocioNutriciaInd> {

    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EncSocioNutriciaIndFacade() {
        super(EncSocioNutriciaInd.class);
    }

    public List<EncSocioNutriciaInd> findByIDEnc(int exp, char areaGeo) {
        Query q = getEntityManager().createNamedQuery("EncSocioNutriciaInd.findByExpedienteArea");

        q.setParameter("expediente", exp);
        q.setParameter("areaGeo", areaGeo);

        return q.getResultList();
    }

    public EncSocioNutriciaInd findByIDEnc(int exp, char areaGeo, String parentesco) {
        TypedQuery<EncSocioNutriciaInd> q = getEntityManager()
                .createNamedQuery(
                "EncSocioNutriciaInd.findByExpedienteAreaParentesco",
                EncSocioNutriciaInd.class);

        q.setParameter("expediente", exp);
        q.setParameter("areaGeo", areaGeo);
        q.setParameter("parentesco", parentesco);

        return q.getResultList().size() > 0 ? q.getSingleResult() : null;
    }

    public EncSocioNutriciaInd findByIDEncActivo(int exp, char areaGeo, String parentesco) {
        TypedQuery<EncSocioNutriciaInd> q = getEntityManager()
                .createNamedQuery(
                "EncSocioNutriciaInd.findByExpedienteAreaParentescoActivo",
                EncSocioNutriciaInd.class);
        q.setParameter("expediente", exp);
        q.setParameter("areaGeo", areaGeo);
        q.setParameter("parentesco", parentesco);

        return q.getResultList().size() > 0 ? q.getSingleResult() : null;
    }

    public List<EncSocioNutriciaInd> findAllActivo() {
        TypedQuery<EncSocioNutriciaInd> createNamedQuery = getEntityManager().
                createNamedQuery("EncSocioNutriciaInd.findAllActivo",
                EncSocioNutriciaInd.class);
        return createNamedQuery.getResultList();
    }

    public List<EncSocioNutriciaInd> findAllActivo(int first, int pageSize) {
        TypedQuery<EncSocioNutriciaInd> createNamedQuery = getEntityManager().
                createNamedQuery("EncSocioNutriciaInd.findAllActivo",
                EncSocioNutriciaInd.class);
        return createNamedQuery.setFirstResult(first).
                setMaxResults(pageSize).
                getResultList();
    }

    public int countAllActivo() {
        TypedQuery<Long> createNamedQuery = getEntityManager().
                createNamedQuery("EncSocioNutriciaInd.countAllActivo",
                Long.class);
        return createNamedQuery.getSingleResult().intValue();
    }

    public List<EncSocioNutriciaInd> find(EncSocioNutriciaFilters f) {
        return new ArrayList<EncSocioNutriciaInd>(0);
    }

    public List<EncSocioNutriciaInd> find(EncSocioNutriciaFilters f, int first, int pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EncSocioNutriciaInd> query = cb.createQuery(EncSocioNutriciaInd.class);

        Root<EncSocioNutriciaInd> from = query.from(EncSocioNutriciaInd.class);
        Path<EncSocioNutriciaIndPK> pk = from.get(EncSocioNutriciaInd_.encSocioNutriciaIndPK);

        query.where(f.ToPredicateArray(cb, from));
        query.orderBy(cb.asc(pk));

        List<EncSocioNutriciaInd> resultList = em.createQuery(query)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
        return resultList;
    }

    public int count(EncSocioNutriciaFilters f) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<EncSocioNutriciaInd> from = cq.from(EncSocioNutriciaInd.class);
        cq.select(cb.count(from));
        cq.where(f.ToPredicateArray(cb, from));
        return em.createQuery(cq).getSingleResult().intValue();
    }

    public int countActivo(EncSocioNutriciaFilters f) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<EncSocioNutriciaInd> from = cq.from(EncSocioNutriciaInd.class);
        Path<EncSocioNutricia> esn = from.get(EncSocioNutriciaInd_.encSocioNutricia);
        Path<Boolean> status = esn.get(EncSocioNutricia_.status);
        cq.select(
                cb.count(from))
            .where(
                cb.and(f.ToPredicateArray(cb, from)),
                cb.equal(status, true));
        return em.createQuery(cq).getSingleResult().intValue();
    }

    public List<EncSocioNutriciaInd> findActivo(EncSocioNutriciaFilters f, int first, int pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EncSocioNutriciaInd> cq = cb.createQuery(EncSocioNutriciaInd.class);

        Root<EncSocioNutriciaInd> from = cq.from(EncSocioNutriciaInd.class);
        Path<EncSocioNutriciaIndPK> pk = from.get(EncSocioNutriciaInd_.encSocioNutriciaIndPK);
        Path<EncSocioNutricia> esn = from.get(EncSocioNutriciaInd_.encSocioNutricia);
        Path<Boolean> status = esn.get(EncSocioNutricia_.status);

        cq.where(
                cb.and(f.ToPredicateArray(cb, from)),
                cb.equal(status, true));
        cq.orderBy(
                cb.asc(pk.get(EncSocioNutriciaIndPK_.areaGeo)),
                cb.asc(pk.get(EncSocioNutriciaIndPK_.expediente)));

        List<EncSocioNutriciaInd> resultList = em.createQuery(cq)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
        return resultList;
    }
}