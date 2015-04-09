package com.caritas.facade;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author tecnologia
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;
    private final Logger LOG = Logger.getLogger(this.getClass().getName());

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        try {
            getEntityManager().persist(entity);
        } catch (javax.validation.ConstraintViolationException e) {
            logContraintViolation(e);
            throw e;
        } catch (RuntimeException e) {
            String sourceMethod = "create";
            logException(sourceMethod, entity, e);
            throw e;
        }
    }

    public void edit(T entity) {
        try {
            getEntityManager().merge(entity);
        } catch (javax.validation.ConstraintViolationException e) {
            logContraintViolation(e);
            throw e;
        } catch (RuntimeException e) {
            String sourceMethod = "create";
            logException(sourceMethod, entity, e);
            throw e;
        }
    }

    public void remove(T entity) {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
        } catch (javax.validation.ConstraintViolationException e) {
            logContraintViolation(e);
            throw e;
        } catch (RuntimeException e) {
            String sourceMethod = "create";
            logException(sourceMethod, entity, e);
            throw e;
        }
    }

    public T find(Object id) {
        try {
            return getEntityManager().find(entityClass, id);
        } catch (javax.validation.ConstraintViolationException e) {
            logContraintViolation(e);
            return null;
        } catch (RuntimeException e) {
            String sourceMethod = "create";
            logException(sourceMethod, id, e);
            return null;
        }
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    private void logContraintViolation(ConstraintViolationException e) {
        ArrayList<String> messages = new ArrayList<String>(1);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> cv : constraintViolations) {
            messages.add(MessageFormat.format("({0}: {1}), ({2}: {3})",
                    cv.getPropertyPath(),
                    cv.getInvalidValue(),
                    cv.getConstraintDescriptor(),
                    cv.getConstraintDescriptor().getAnnotation()));
        }
        LOG.log(Level.SEVERE, "{0}: ConstraintViolations: {1}",
                new Object[]{
            this.getClass().getCanonicalName(),
            Arrays.deepToString(messages.toArray())
        });
    }

    private void logException(String sourceMethod, Object entity, RuntimeException e) {
        String sourceParam = "Selected = {0}";
        String sourceClass = getClass().getCanonicalName();
        Logger l = Logger.getLogger(sourceClass);
        l.logp(Level.WARNING, sourceClass, sourceMethod, sourceParam, entity);
        l.logp(Level.WARNING, sourceClass, sourceMethod, "", e);
    }
}
