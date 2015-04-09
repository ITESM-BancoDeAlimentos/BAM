package com.caritas.facade;

import com.caritas.entity.Articulos;
import com.caritas.filters.ArticulosFilters;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author tecnologia
 */
@Stateless
public class ArticulosFacade extends AbstractFacade<Articulos> {

    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArticulosFacade() {
        super(Articulos.class);
    }

    public int count(ArticulosFilters f) {
        f = (f == null) ? new ArticulosFilters() : f;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Articulos> from = cq.from(Articulos.class);
        Predicate[] clauses = f.ToPredicateArray(cb, from);
        cq.select(cb.count(from));
        cq.where(clauses);
        return em.createQuery(cq).getSingleResult().intValue();
    }

    public List<Articulos> find(ArticulosFilters f, int first, int pageSize) {
        f = (f == null) ? new ArticulosFilters() : f;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Articulos> q = cb.createQuery(Articulos.class);
        Root<Articulos> articulos = q.from(Articulos.class);
        q.where(f.ToPredicateArray(cb, articulos));
        List<Articulos> resultList = em.createQuery(q)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
        return resultList;
    }

    public List<String> findArticulos() {
        Query q = getEntityManager().createNamedQuery("Articulos.findArticulos");

        return q.getResultList();
    }

    public List<Articulos> findLike(String query, int maxResults) {
        List<Articulos> resultList = em.createNamedQuery("Articulos.findLike")
                .setParameter("query", "%" + query + "%")
                .setMaxResults(maxResults)
                .getResultList();
        return resultList;
    }
    
    /**
     * Revisa si el id (trim) esta en uso.
     * @param id String se busca. Se le hace .trim() dentro de la funcion.
     * @return true si ya esta en uso, false si no encontro
     */
    public boolean isIdUsed(String id) {
        return em.createNamedQuery("Articulos.countId", Long.class)
                .setParameter("query", id.trim())
                .getSingleResult() > 0;
    }
}
