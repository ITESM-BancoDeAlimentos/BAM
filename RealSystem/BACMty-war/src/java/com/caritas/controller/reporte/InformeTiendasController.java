package com.caritas.controller.reporte;

import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.pdf.InformeTAutoPdf;
import com.caritas.entity.Donantes;
import com.caritas.entity.Movimientos;
import com.caritas.entity.RegistroInformeTA;
import com.caritas.enums.TipoMov;
import com.caritas.facade.DonantesFacade;
import com.caritas.facade.MovimientosFacade;
import com.caritas.facade.TipoDonFacade;
import com.caritas.reporte.InformeTA;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author software
 */
@ManagedBean
@ViewScoped
public class InformeTiendasController implements Serializable {

    private static final Logger LOG = Logger.getLogger(InformeTiendasController.class.getName());
    private static List<Donantes> tiendasDonantesDefault = null;
    private Date former = new Date();
    private Date latter = new Date();
    private List<Donantes> tiendasDonantes = null;
    @EJB
    private MovimientosFacade movimientosFacade;
    @EJB
    private DonantesFacade donantesFacade;
    @EJB
    private TipoDonFacade tipoDonFacade;

    /**
     * Creates a new instance of InformeTiendasController
     */
    public InformeTiendasController() {
    }

    @PostConstruct
    private void defaultSetting() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        int year = gc.get(Calendar.YEAR);
        setLatterYear(year);
        setFormerYear(year - 1);
    }

    private List<Movimientos> getMovimientosOfYear(int y) {
        List<Movimientos> entradasByFechaMovDonantes = movimientosFacade.
                entradasByFechaMovDonantes(
                getDateOfYear(y),
                getDateOfYear(y + 1),
                getTiendasDonantes()
                .toArray(new Donantes[getTiendasDonantes().size()]));
        LOG.log(Level.INFO, "{0} Movimientos para el a\u00f1o {1}",
                new Object[]{entradasByFechaMovDonantes.size(), y});
        return entradasByFechaMovDonantes;
    }

    public void export() throws DocumentException, IOException {
        InformeTA informeTA = new InformeTA(
                getMovimientosOfYear(getFormerYear()),
                getMovimientosOfYear(getLatterYear()),
                getTiendasDonantes(),
                getFormerYear(),
                getLatterYear());
        Collection<RegistroInformeTA.Pair> pairs = informeTA.getOPairs();
        InformeTAutoPdf pdf = new InformeTAutoPdf(pairs,
                getFormerYear(),
                getLatterYear());
        JsfUtil.downloadFile("Informe.pdf", pdf.createPdf());
    }

    private List<Movimientos> getYearMovs(int dateYear) {
        List<Movimientos> movs = getMovimientosFacade()
                .findByFechaMovRangeTipoMov(
                getDateOfYear(dateYear),
                getDateOfYear(dateYear + 1),
                TipoMov.ENTRADA.getCodigo());
        return movs;
    }

    /**
     * Consigue el conjunto de movimientos que ocurren dentro del año del a
     * fecha dada.
     *
     * @param date Date con el año con en el que se buscaran los Movimientos
     * @return Lista con el resultado
     */
    private List<Movimientos> getYearMovs(Date date) {
        int dateYear = getYearOf(date);
        return getYearMovs(dateYear);
    }

    /**
     * @return the former
     */
    public Date getFormer() {
        return former;
    }

    /**
     * @param former the former to set
     */
    public void setFormer(Date former) {
        this.former = former;
    }

    /**
     * @return the latter
     */
    public Date getLatter() {
        return latter;
    }

    /**
     * @param latter the latter to set
     */
    public void setLatter(Date latter) {
        this.latter = latter;
    }

    public void setFormerYear(int value) {
        setFormer(getDateOfYear(value));
    }

    public int getFormerYear() {
        return getYearOf(getFormer());
    }

    public void setLatterYear(int value) {
        setLatter(getDateOfYear(value));
    }

    public int getLatterYear() {
        return getYearOf(getLatter());
    }

    public MovimientosFacade getMovimientosFacade() {
        return movimientosFacade;
    }

    private int getYearOf(Date date) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    private Date getDateOfYear(int year) {
        GregorianCalendar c = new GregorianCalendar();
        int FIRST_MONTH_DAY = 1;
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, FIRST_MONTH_DAY);
        return c.getTime();
    }

    public List<Donantes> getTiendasDonantes() {
        if (tiendasDonantes == null) {
            tiendasDonantes = getTiendasDonantesDefault();
        }
        return tiendasDonantes;
    }

    public void setTiendasDonantes(List<Donantes> tiendasDonantes) {
        this.tiendasDonantes = tiendasDonantes;
    }

    private List<Donantes> getTiendasDonantesDefault() {
        if (tiendasDonantesDefault == null) {
            tiendasDonantesDefault = getDonantesFacade().findAutoServicios();
        }
        return tiendasDonantesDefault;
    }

    /**
     * @param movimientosFacade the movimientosFacade to set
     */
    public void setMovimientosFacade(MovimientosFacade movimientosFacade) {
        this.movimientosFacade = movimientosFacade;
    }

    /**
     * @return the donantesFacade
     */
    public DonantesFacade getDonantesFacade() {
        return donantesFacade;
    }

    /**
     * @param donantesFacade the donantesFacade to set
     */
    public void setDonantesFacade(DonantesFacade donantesFacade) {
        this.donantesFacade = donantesFacade;
    }

    /**
     * @return the tipoDonFacade
     */
    public TipoDonFacade getTipoDonFacade() {
        return tipoDonFacade;
    }

    /**
     * @param tipoDonFacade the tipoDonFacade to set
     */
    public void setTipoDonFacade(TipoDonFacade tipoDonFacade) {
        this.tipoDonFacade = tipoDonFacade;
    }
}
