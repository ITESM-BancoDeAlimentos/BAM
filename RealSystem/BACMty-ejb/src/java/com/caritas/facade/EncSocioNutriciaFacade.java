/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.facade;

import com.caritas.entity.EncSocioNutricia;
import com.caritas.entity.EncSocioNutriciaPK;
import com.caritas.entity.Instituciones;
import com.caritas.entity.Preguntas;
import com.caritas.entity.Respuestas;
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
public class EncSocioNutriciaFacade extends AbstractFacade<EncSocioNutricia> {

    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EncSocioNutriciaFacade() {
        super(EncSocioNutricia.class);
    }

    @Override
    public List<EncSocioNutricia> findAll() {
        Query q = getEntityManager().createNamedQuery("EncSocioNutricia.findByStatus");

        q.setParameter("status", true);

        return q.getResultList();
    }

    public EncSocioNutricia find(EncSocioNutriciaPK id, boolean status) {
        Query q = getEntityManager().createNamedQuery("EncSocioNutricia.findByIdStatus");

        q.setParameter("id", id);
        q.setParameter("status", status);

        try {
            return (EncSocioNutricia) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            return (EncSocioNutricia) q.getResultList().get(0);
        }
    }

    public String findPregFam(int idPregunta) {
        Query q = getEntityManager().createNamedQuery("Preguntas.findByIDPregunta");
        q.setParameter("iDPregunta", idPregunta);

        Preguntas pregunta;

        try {
            pregunta = (Preguntas) q.getSingleResult();
        } catch (NoResultException nre) {
            return "Pregunta " + idPregunta;
        }

        return pregunta.getDescripcion();
    }

    public String findRespFam(int idPregunta, int idRespuesta) {
        Query q = getEntityManager().createNamedQuery("Respuestas.findByIDPreguntaIDRespuesta");
        q.setParameter("iDPregunta", idPregunta);
        q.setParameter("iDRespuesta", idRespuesta);

        Respuestas respuesta;

        try {
            respuesta = (Respuestas) q.getSingleResult();
        } catch (NoResultException nre) {
            return "Respuesta " + idRespuesta;
        }

        return respuesta.getDescripcion();

    }

    public List<Integer> findLike(String query, char areaGeo, boolean status) {
        int expediente = Integer.valueOf(query);

        Query q = getEntityManager().createNamedQuery("EncSocioNutricia.findLike");

        q.setParameter("expediente", expediente + "%");
        q.setParameter("areaGeo", areaGeo);
        q.setParameter("status", status);

        return q.getResultList();
    }

    public List<String> findAllIDEncuestas() {
        Query q = getEntityManager().createNamedQuery("EncSocioNutricia.findAllIds");

        return q.getResultList();
    }

    public int findLastExp(char area) {
        Query q = getEntityManager().createNamedQuery("EncSocioNutricia.findLastExpByArea");

        q.setParameter("areaGeo", area);
        q.setMaxResults(1);
        return (Integer) q.getSingleResult();
    }

    public List<Instituciones> findInstituciones() {
        Query q = getEntityManager().createNamedQuery("Instituciones.findByActivo");
        q.setParameter("activo", true);
        
        return q.getResultList();
    }

    public List<EncSocioNutricia> findByInstitucion(Instituciones IDInstitucion, char areaGeo) {
        Query q = getEntityManager().createNamedQuery("EncSocioNutricia.findByInstitucionAndAreaGeo");
        q.setParameter("iDInstitucion", IDInstitucion);
        q.setParameter("areaGeo", areaGeo);
        q.setParameter("status", true);
        return q.getResultList();
    }

    public List<Instituciones> findInstitucionActivo() {
        return getEntityManager()
                .createNamedQuery("EncSocioNutricia.findInstitucionActivo")
                .getResultList();
    }

    public List<Instituciones> findInstitucionByAreaGeo(char AG) {
        Query q = getEntityManager().createNamedQuery("EncSocioNutricia.findInstitucionByAreaGeo");
        q.setParameter("areaGeo", AG);
        q.setParameter("activo", true);
        q.setParameter("status", true);
        return q.getResultList();
    }

    public EncSocioNutricia findByJefeDeFamilia(EncSocioNutricia jefaFamilia) {
        Query q = getEntityManager().createNamedQuery("EncSocioNutricia.findByJefeFamilia");
        q.setParameter("jefeFamilia", jefaFamilia.getJefeFamilia());
        return (EncSocioNutricia) q.getSingleResult();
    }

    //<editor-fold defaultstate="collapsed" desc="paginado/like">
    public List<Instituciones> findInstitucionActivo(int first, int pageSize) {
        return getEntityManager()
                .createNamedQuery("EncSocioNutricia.findInstitucionActivo")
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Instituciones> findInstitucionActivoLike(int first, int pageSize, String query) {
        if (query.isEmpty()) {
            return findInstitucionActivo(first, pageSize);
        }
        return getEntityManager()
                .createNamedQuery("EncSocioNutricia.findInstitucionLike")
                .setParameter("query", "%" + query + "%")
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Instituciones> findInstitucionByAreaGeoLike(char AG, String query) {
        if (query.isEmpty()) {
            return findInstitucionByAreaGeo(AG);
        }
        return getEntityManager()
                .createNamedQuery("EncSocioNutricia.findInstitucionByAreaGeoLike")
                .setParameter("areaGeo", AG)
                .setParameter("activo", true)
                .setParameter("status", true)
                .setParameter("query", "%" + query + "%")
                .getResultList();
    }

    public List<String> findCiudadActive(int first, int pageSize) {
        return getEntityManager()
                .createNamedQuery("EncSocioNutricia.findCiudadActivo")
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<String> findCiudadLike(int first, int pageSize, String query) {
        if (query.isEmpty()) {
            return findCiudadActive(first, pageSize);
        }
        return getEntityManager()
                .createNamedQuery("EncSocioNutricia.findCiudadLike")
                .setParameter("query", "%" + query + "%")
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }
    //</editor-fold>
}
