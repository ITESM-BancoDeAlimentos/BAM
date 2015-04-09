package com.caritas.controller.reporte;

import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.ParametersController;
import com.caritas.controller.util.pdf.HebSorianaPdf;
import com.caritas.entity.Donantes;
import com.caritas.entity.Movimientos;
import com.caritas.entity.RegistroProductos;
import com.caritas.facade.MovimientosFacade;
import com.caritas.reporte.ReporteProductos;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author software
 */
@ManagedBean
@ViewScoped
public class ReporteHebSorianaController implements Serializable {

    @EJB
    private MovimientosFacade movimientosFacade;
    @ManagedProperty(value = "#{parametersController.donanteHeb}")
    private Donantes DONANTE_HEB;
    @ManagedProperty(value = "#{parametersController.donanteSoriana}")
    private Donantes DONANTE_SORIANA;
    private Date initDate = new Date();
    private Date endDate = new Date();
    private List<Movimientos> movs = null;

    public ReporteHebSorianaController() {
    }

    public void exportar() throws DocumentException, IOException {
        findMovs();
        ReporteProductos hsr = new ReporteProductos(movs, "HEB");
        ByteArrayOutputStream createPdf = new HebSorianaPdf(hsr.getRegistros()).createPdf();
        JsfUtil.downloadFile("Reporte.pdf", createPdf);
    }

    public void findMovs() {
        movs = movimientosFacade.entradasByFechaMovDonantes(initDate, endDate, DONANTE_HEB, DONANTE_SORIANA);
    }

    public List<Movimientos> getMovs() {
        return movs;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDONANTE_HEB(Donantes DONANTE_HEB) {
        this.DONANTE_HEB = DONANTE_HEB;
    }

    public void setDONANTE_SORIANA(Donantes DONANTE_SORIANA) {
        this.DONANTE_SORIANA = DONANTE_SORIANA;
    }
}
