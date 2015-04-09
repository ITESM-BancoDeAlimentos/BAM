package com.caritas.controller.reporte;

import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.pdf.WalMartSamsAurrPdf;
import com.caritas.entity.Donantes;
import com.caritas.entity.RegistroProductos;
import com.caritas.facade.MovimientosFacade;
import com.caritas.reporte.ReporteProductos;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
public class ReporteWalmartSamsAurreraController implements Serializable {

    @EJB
    private MovimientosFacade movimientosFacade;
    private Date initDate = new Date();
    private Date endDate = new Date();
    @ManagedProperty(value = "#{parametersController.donanteWalmart}")
    private Donantes DONANTE_WALMART;
    private Donantes DONANTE_SAMS;
//    private Donantes DONANTE_AURRERA;
    private String elaboro;

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

    public void setDONANTE_WALMART(Donantes DONANTE_WALMART) {
        this.DONANTE_WALMART = DONANTE_WALMART;
    }
    
    public Donantes getDONANTE_SAMS() {
        return DONANTE_SAMS;
    }
    
    public void setDONANTE_SAMS(Donantes DONANTE_SAMS) {
        this.DONANTE_SAMS = DONANTE_SAMS;
    }

    public void exportar() throws DocumentException, IOException {
        Collection<RegistroProductos> registros = new ReporteProductos(
                movimientosFacade.entradasByFechaMovDonantes(
                getInitDate(),
                getEndDate(),
                DONANTE_WALMART), "HEB").getRegistros();
        ByteArrayOutputStream createPdf = new WalMartSamsAurrPdf(registros).createPdf();
        JsfUtil.downloadFile("Reporte.pdf", createPdf);
    }

    public void setElaboro(String elaboro) {
        this.elaboro = elaboro;
    }
    
    public String getElaboro() {
        return this.elaboro;
    }
}
