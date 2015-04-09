/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.controller.reporte;

import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.pdf.ValeCudePdf;
import com.caritas.entity.ValeCUDE;
import com.caritas.facade.ValeCUDEFacade;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
public class CudeController {

    private static final Logger LOG = Logger.getLogger(CudeController.class.getName());
    @EJB
    private ValeCUDEFacade valeCUDEFacade;
    private Date start;
    private Date end;

    /**
     * Creates a new instance of CudeController
     */
    public CudeController() {
    }

    public void export() throws IOException, DocumentException {
        try {
            String pdfFileName = "ControlUnicoDonantivosEspecie" + ".pdf";
            List<ValeCUDE> byRange = valeCUDEFacade.findByDateRange(getStart(), getEnd());
            ValeCudePdf valeCudePdf = new ValeCudePdf(byRange);
            JsfUtil.downloadFile(pdfFileName, valeCudePdf.createPdf());
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            JsfUtil.addErrorMessage("No se pudo crear el archivo");
        }
    }

    @PostConstruct
    private void defaultSetting() {
        Date today = new Date();

        GregorianCalendar gcs = new GregorianCalendar();
        GregorianCalendar gce = new GregorianCalendar();

        gcs.setTime(today);
        gce.setTime(today);

        int monthMin = gcs.getActualMinimum(Calendar.DAY_OF_MONTH);
        int monthMax = gce.getActualMaximum(Calendar.DAY_OF_MONTH);
        gcs.set(Calendar.DAY_OF_MONTH,  monthMin);
        gce.set(Calendar.DAY_OF_MONTH, monthMax);

        start = gcs.getTime();
        end   = gce.getTime();
    }


    /**
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Date end) {
        this.end = end;
    }
}
