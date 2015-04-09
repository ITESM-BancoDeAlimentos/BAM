package com.caritas.facade;

import com.caritas.entity.Areas;
import com.caritas.entity.Articulos;
import com.caritas.entity.Donantes;
import com.caritas.entity.FoliosEntrada;
import com.caritas.entity.Movimientos;
import com.caritas.entity.Movimientos_;
import com.caritas.entity.MovtosDet;
import com.caritas.entity.Programas;
import com.caritas.entity.Recibo;
import com.caritas.entity.ReciboDet;
import com.caritas.entity.Sucursales;
import com.caritas.entity.Usuarios;
import com.caritas.enums.TipoMov;
import com.caritas.filters.MovimientosFilters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author tecnologia
 */
@Stateless
public class MovimientosFacade extends AbstractFacade<Movimientos> {

    private final short TIENDAS = 1;
    private final short AMBA = 2;
    @PersistenceContext(unitName = "BACMty-ejbPU")
    private EntityManager em;

    public MovimientosFacade() {
        super(Movimientos.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Retorna los movimientos entre dos fechas. En base a fechaMov
     *
     * @param fechaP Date que es Menor Igual.
     * @param fechaD Date que es Mayor Igual.
     * @return Lista de resultados
     */
    public List<Movimientos> findByDateRange(Date fechaP, Date fechaD) {
        return getEntityManager()
                .createNamedQuery("Movimientos.findByFechaMovRange")
                .setParameter("fechaMov1", fechaP)
                .setParameter("fechaMov2", fechaD)
                .getResultList();
    }

    /**
     * Retorna los movimientos entre dos fechas. En base a fechaMov
     *
     * @param fechaP Date que es Menor Igual.
     * @param fechaD Date que es Mayor Igual.
     * @param tipoMov String con el tipo de movimiento ('ENT' o 'SAL')
     * @return Lista de resultados
     */
    public List<Movimientos> findByFechaMovRangeTipoMov(Date fechaP, Date fechaD, String tipoMov) {
        return getEntityManager()
                .createNamedQuery("Movimientos.findByFechaMovRangeTipo")
                .setParameter("fechaMov1", fechaP)
                .setParameter("fechaMov2", fechaD)
                .setParameter("tipoMov", tipoMov)
                .getResultList();
    }

    /**
     *
     * @param fechaP
     * @param fechaD
     * @param donantes
     * @return
     */
    public List<Movimientos> entradasByFechaMovDonantes(Date fechaP, Date fechaD, Donantes... donantes) {
        final int andLength = 4;
        final int orLength = donantes.length;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movimientos> cq = cb.createQuery(Movimientos.class);
        Root<Movimientos> fromMovimientos = cq.from(Movimientos.class);

        List<Predicate> andStatements = new ArrayList<Predicate>(andLength);
        List<Predicate> orStatements = new ArrayList<Predicate>(orLength);

        andStatements.add(cb.like(fromMovimientos.<String>get("tipoMov"),
                TipoMov.ENTRADA.getCodigo()));
        andStatements.add(cb.greaterThanOrEqualTo(fromMovimientos
                .<Date>get("fechaMov"), fechaP));
        andStatements.add(cb.lessThanOrEqualTo(fromMovimientos
                .<Date>get("fechaMov"), fechaD));

        for (Donantes donante : donantes) {
            orStatements.add(cb.equal(fromMovimientos
                    .<Donantes>get("iDDonante"), donante));
        }
        andStatements.add(cb.or(orStatements.toArray(new Predicate[orLength])));

        cq.select(fromMovimientos)
                .where(andStatements.toArray(new Predicate[andLength]));
        List<Movimientos> resultList = em.createQuery(cq).getResultList();
        return resultList;
    }

    public List<String> findRecibos(String status, short pantallaActual) {
        Query q = getEntityManager().createNamedQuery("Recibo.findFoliosByStatus");
        q.setParameter("statusMov", status);

        String origen = null;
        switch (pantallaActual) {
            case TIENDAS:
                origen = "D";
                break;
            case AMBA:
                origen = "A";
                break;
        }

        q.setParameter("origen", origen);

        return q.getResultList();
    }

    public Movimientos findByIDFolioTipo(String idFolio, String tipoMov) {
        Query setParameter = getEntityManager()
                .createNamedQuery("Movimientos.findByIDFolioTipo")
                .setParameter("iDFolio", idFolio)
                .setParameter("tipoMov", tipoMov);
        try {
            return (Movimientos) setParameter.getSingleResult();
        } catch (Exception e) {
            List<Movimientos> resultList = setParameter.getResultList();
            LOG.severe("Result List sizea:" + resultList.size());
            for (Movimientos movimientos : resultList) {
                System.out.println("movimientos = " + movimientos);
            }
            return (Movimientos) resultList.get(0);
        }
    }
    private static final Logger LOG = Logger.getLogger(MovimientosFacade.class.getName());

    /**
     * Busca en una u otra tabla (Recibo o Movimientos) un registro por folio.
     * La tabla dependerá de si está en modo edición o no.
     *
     * @param idFolio El folio a buscar
     * @param tablaMovs Indica si se buscará el registro en la tabla de
     * Movimientos o no
     * @return Un registro de tipo <code>Movimientos</code> ó un registro vacio
     * en caso de no encontrar nada
     */
    public Movimientos findByFolio(String idFolio, short pantallaActual) {
        try {
            Query q = getEntityManager().createNamedQuery("Recibo.findByIDFolioTipo");
            q.setParameter("iDFolio", idFolio);
            q.setParameter("tipoMov", "ENT");

            String origen = null;
            switch (pantallaActual) {
                case TIENDAS:
                    origen = "D";
                    break;
                case AMBA:
                    origen = "A";
                    break;
            }
            q.setParameter("origen", origen);

            Recibo reciboEncontrado = (Recibo) q.getSingleResult();

            return new Movimientos(reciboEncontrado.getIDFolio(), reciboEncontrado.getTipoMov(), reciboEncontrado.getIDSucursal(),
                    reciboEncontrado.getOrigen(), reciboEncontrado.getStatusMov(), reciboEncontrado.getIDSucursales(), reciboEncontrado.getIDPrograma(),
                    reciboEncontrado.getIDDonante(), reciboEncontrado.getIDArea(), reciboEncontrado.getIDUsuario(), reciboEncontrado.getFolioDon());
        } catch (NoResultException nre) {
            return new Movimientos();
        }
    }

    public List<MovtosDet> findMovtoDetByFolio(String iDFolio) {
        Query q = getEntityManager().createNamedQuery("ReciboDet.findByIDFolioTipo");
        q.setParameter("iDFolio", iDFolio);
        q.setParameter("tipoMov", "ENT");

        List<ReciboDet> listaEncontrada = q.getResultList();
        List<MovtosDet> resultado = new ArrayList<MovtosDet>(listaEncontrada.size());

        for (ReciboDet temp : listaEncontrada) {
            resultado.add(new MovtosDet(temp.getIDFolio(), temp.getTipoMov(), temp.getFechaCad(), temp.getCantidad(), temp.getPeso(),
                    temp.getMerma(), temp.getCostoBenef(), temp.getCuotaRecup(), temp.getIDPrograma(), temp.getIDArticulo(), temp.getIDArea()));
        }

        return resultado;
    }

    public Usuarios getUsuarioNombre(String idUsuario) {
        Query q = getEntityManager().createNamedQuery("Usuarios.findByIDUsuario");
        q.setParameter("iDUsuario", idUsuario);

        return (Usuarios) q.getSingleResult();
    }

    public Movimientos findMovimientoEntrada(String iDFolio) {
        Query q = getEntityManager().createNamedQuery("Movimientos.findByIDFolioTipo");

        q.setParameter("iDFolio", iDFolio);
        q.setParameter("tipoMov", "ENT");

        try {
            return (Movimientos) q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (NonUniqueResultException nue) {
            return null;
        }
    }

    public List<String> findAreas() {
        Query q = getEntityManager().createNamedQuery("Movimientos.findAreas");

        return q.getResultList();
    }

    public Areas findIDArea(String area) {
        Query q = getEntityManager().createNamedQuery("Areas.findByArea");
        q.setParameter("area", area);

        return (Areas) q.getSingleResult();
    }

    public List<String> findProgramas(Areas idArea) {
        Query q = getEntityManager().createNamedQuery("Movimientos.findProgramas");
        q.setParameter("iDArea", idArea);

        return q.getResultList();
    }
    
    public Programas findIDPrograma(String programa) {
        Query q = getEntityManager().createNamedQuery("Programas.findByPrograma");
        try {
            q.setParameter("programa", programa);

            return (Programas) q.getSingleResult();
        } catch (NonUniqueResultException nure) {
            // si hay mas de un resultado (no deberia haber) se devuelve el primero
            return (Programas) q.getResultList().get(0);
        }
    }

    public int findFolioRepetido(String iDFolio) {
        Query q = getEntityManager().createNamedQuery("Movimientos.findByIDFolioTipo");

        q.setParameter("iDFolio", iDFolio);
        q.setParameter("tipoMov", "ENT");

        return q.getResultList().size();
    }

    public List<String> findDonantesTiendas() {
        Query q = getEntityManager().createNamedQuery("Movimientos.findDonantesTie");
        q.setParameter("tipoDonante", "AUTO");

        return q.getResultList();
    }
    
    public List<String> findAllDonantes() {
        Query q = getEntityManager().createNamedQuery("Movimientos.findAllDonantes");
        
        return q.getResultList();
    }

    public Donantes findIDDonante(String donante) {
        Query q = getEntityManager().createNamedQuery("Donantes.findByDonante");
        q.setParameter("donante", donante);

        return (Donantes) q.getSingleResult();
    }

    public Sucursales findIDSucursales(String sucursal, Donantes donante) {
        Query q = getEntityManager().createNamedQuery("Movimientos.findByIDSucursalIDDonante");
        q.setParameter("iDSucursal", sucursal);
        q.setParameter("iDDonante", donante);

        return (Sucursales) q.getSingleResult();
    }

    public List<String> findSucursales(Donantes donante) {
        Query q = getEntityManager().createNamedQuery("Movimientos.findSucursales");
        q.setParameter("iDDonante", donante);

        return q.getResultList();
    }

    public List<String> findMovimientosLike(String query) {
//        String query10 = "          ".concat(query.trim()); // Query con 10 caracteres, agregando espacios a la izquierda
//        query10 = query10.substring(query10.length() - 10, query10.length());

        Query q = getEntityManager().createNamedQuery("Movimientos.findMovimientosLike");
        q.setParameter("query", "%" + query + "%");
        q.setParameter("tipoMov", "ENT");

        return q.getResultList();
    }

    public List<String> findArticulosLike(String query) {
        Query q = getEntityManager().createNamedQuery("Movimientos.findArticulosLike");
        q.setParameter("query", "%" +query +"%");

        return q.getResultList();
    }

    public List<String> findArticulos() {
        Query q = getEntityManager().createNamedQuery("Articulos.findArticulos");

        return q.getResultList();
    }

    public Articulos findArticulos(String descripcion) {
        Query q = getEntityManager().createNamedQuery("Movimientos.findArticuloByArticulo");
        q.setParameter("articulo", descripcion);

        return (Articulos) q.getSingleResult();
    }

    public List<String> findMovimientos(String status) {
        Query q = getEntityManager().createNamedQuery("Movimientos.findByStatusMov");

        q.setParameter("statusMov", status);

        return q.getResultList();
    }
    
    public List<String> findMovimientosEntAMBA() {
        Query q = getEntityManager().createNamedQuery("Movimientos.findByTipoMovArea");
        
        q.setParameter("tipoMov", "ENT");
        q.setParameter("iDArea", "AMBA");
        
        return q.getResultList();
    }

    public List<Movimientos> findDonanteYear(Donantes donante, Date time, Date time1) {
        return getEntityManager().createQuery(
                "SELECT m FROM Movimientos"
                + "WHERE m.iDDonante = :iDDonante"
                + "  AND m.fechaMov >= :fechaMov1"
                + "  AND m.fechaMov  < :fechaMov2")
                .setParameter(":iDDonante", donante)
                .setParameter(":fechaMov1", time)
                .setParameter(":fechaMov2", time1)
                .getResultList();
    }

    public String findLastFolioENT() {
        Query q = getEntityManager().createNamedQuery("FoliosEntrada.findAll");

        return ((FoliosEntrada) q.getSingleResult()).getFolioEntrada();
    }

    public List<Movimientos> find(MovimientosFilters f, int first, int pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movimientos> query = cb.createQuery(Movimientos.class);

        Root<Movimientos> movimientos = query.from(Movimientos.class);
        Path<Date> fechaMov  = movimientos.get(Movimientos_.fechaMov);
        Path<String> iDFolio = movimientos.get(Movimientos_.iDFolio);
        Path<String> tipoMov = movimientos.get(Movimientos_.tipoMov);

        query.where(f.ToPredicateArray(cb, movimientos));
        query.orderBy(cb.desc(fechaMov),cb.desc(iDFolio),cb.asc(tipoMov));
        query.groupBy(movimientos);
        
        List<Movimientos> resultList = em.createQuery(query)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
        return resultList;
    }

    public int count(MovimientosFilters f) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Movimientos> from = cq.from(Movimientos.class);
        cq.select(cb.count(from));
        cq.where(f.ToPredicateArray(cb, from));
        return em.createQuery(cq).getSingleResult().intValue();
    }
}
