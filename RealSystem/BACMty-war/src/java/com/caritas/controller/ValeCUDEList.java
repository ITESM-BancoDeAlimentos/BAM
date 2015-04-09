package com.caritas.controller;

import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.pdf.ValeCudePdf;
import com.caritas.entity.ValeCUDE;
import com.caritas.facade.ValeCUDEFacade;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author software
 */
@ManagedBean
@ViewScoped
public class ValeCUDEList {

    @EJB
    private ValeCUDEFacade valeCUDEFacade;
    private boolean tableRender = false;
    private Date initialDate;
    private Date finalDate;
    private LazyDataModel<ValeCUDE> items = new LazyDataModel<ValeCUDE>() {
        @Override
        public List<ValeCUDE> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
            setRowCount(valeCUDEFacade.countByDateRange(initialDate, finalDate));
            return valeCUDEFacade.findRangeByDateRange(initialDate, finalDate, first, pageSize);
        }
    };

    public void exportCude() throws DocumentException, IOException {
        ValeCudePdf vcp = new ValeCudePdf(valeCUDEFacade.findByDateRange(initialDate, finalDate));
        JsfUtil.downloadFile("Control" + ".pdf", vcp.createPdf());
    }

    public ValeCUDEList() {
    }

    @PostConstruct
    public void initialize() {
        initialDate = (JsfUtil.startDate(new Date()));
        finalDate = (JsfUtil.endDate(new Date()));
        items.setRowCount(valeCUDEFacade.countByDateRange(initialDate, finalDate));
    }

    public void setToday() {
        initialDate = (JsfUtil.startDate(new Date()));
        finalDate = (JsfUtil.endDate(new Date()));
    }

    public void setWeek() {
        initialDate = (JsfUtil.firstInWeek(new Date()));
        finalDate = (JsfUtil.lastInWeek(new Date()));
    }

    public void setMonth() {
        initialDate = (JsfUtil.firstInMonth(new Date()));
        finalDate = (JsfUtil.lastInMonth(new Date()));
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = JsfUtil.startDate(initialDate);
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = JsfUtil.endDate(finalDate);
    }

    public LazyDataModel<ValeCUDE> getItems() {
        return items;
    }

    public void setItems(LazyDataModel<ValeCUDE> items) {
        this.items = items;
    }

    public boolean isTableRender() {
        return tableRender;
    }

    public void setTableRender(boolean tableRender) {
        this.tableRender = tableRender;
    }
}
