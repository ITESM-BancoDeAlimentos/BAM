package com.caritas.controller;

import com.caritas.controller.util.JsfUtil;
import com.caritas.entity.Areas;
import com.caritas.entity.Donantes;
import com.caritas.entity.Instituciones;
import com.caritas.entity.MovtosDet;
import com.caritas.entity.Programas;
import com.caritas.entity.Proveedores;
import com.caritas.entity.Sucursales;
import com.caritas.enums.Origen;
import com.caritas.enums.TipoMov;
import com.caritas.facade.MovtosDetFacade;
import com.caritas.filters.MovimientosFilters;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author software
 */
@ManagedBean
@ViewScoped
public class MovimientosBusqueda implements Serializable {

    @ManagedProperty("#{bundle}")
    private transient ResourceBundle bundle;
    @EJB
    private transient MovtosDetFacade movtosdet;
    private Map<String, Boolean> renderColumns = new HashMap<String, Boolean>(8);
    private MovimientosFilters filtro;
    private boolean tableRender = false;
    private LazyDataModel<MovtosDet> dataModel = new LazyDataModel<MovtosDet>() {
        @Override
        public List<MovtosDet> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
            int count = movtosdet.countByMovimientos(filtro);
            List<MovtosDet> movtosDets = movtosdet.findByMovimientosRanged(first, pageSize, filtro);
            setRowCount(count);
            return movtosDets;
        }
    };

    public MovimientosBusqueda() {
        initializeRenderColumns();
        initializeFilters();
    }

    public void validateFilter() {
        if (filtro.getFiltroFechaInicial().after(filtro.getFiltroFechaFinal())) {
            JsfUtil.addErrorMessage(bundle.getString("ErrorFecha_InicialMayorFinal"));
            filtro.setFiltroFechaInicial(JsfUtil.firstInMonth(filtro.getFiltroFechaFinal()));
            tableRender = false;
        } else {
            tableRender = true;
        }
    }

    public void cleanFilters() {
        initializeFilters();
    }

    //<editor-fold defaultstate="collapsed" desc="Initializers">
    private void initializeFilters() {
        filtro = new MovimientosFilters();
        initializeFechas();
    }

    private void initializeFechas() {
        filtro.setFiltroFechaFinal(new Date());
        filtro.setFiltroFechaInicial(JsfUtil.firstInMonth(filtro.getFiltroFechaFinal()));
    }

    private void initializeRenderColumns() {
        for (String key : columnasActivadas) {
            renderColumns.put(key, Boolean.TRUE);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Filters Getters/Setters">
    public Origen getFiltroOrigen() {
        return Origen.valueByCodigo(this.filtro.getFiltroOrigen());
    }

    public void setFiltroOrigen(Origen value) {
        if (value == null) {
            filtro.setFiltroOrigen(null);
        } else {
            filtro.setFiltroOrigen(value.getCodigo());
            setFiltroTipoMov(value.getTipoMov().getCodigo());
        }
    }

    public Map<String, Boolean> getRenderColumns() {
        return renderColumns;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public Donantes getFiltroDonantes() {
        return filtro.getFiltroDonantes();
    }

    public void setFiltroDonantes(Donantes filtroDonantes) {
        this.filtro.setFiltroDonantes(filtroDonantes);
        filtro.setFiltroSucursales(null);
    }

    public Sucursales getFiltroSucursales() {
        return filtro.getFiltroSucursales();
    }

    public void setFiltroSucursales(Sucursales filtroSucursales) {
        this.filtro.setFiltroSucursales(filtroSucursales);
    }

    public Areas getFiltroAreas() {
        return filtro.getFiltroAreas();
    }

    public void setFiltroAreas(Areas filtroAreas) {
        this.filtro.setFiltroAreas(filtroAreas);
    }

    public Proveedores getFiltroProveedores() {
        return filtro.getFiltroProveedores();
    }

    public void setFiltroProveedores(Proveedores filtroProveedores) {
        this.filtro.setFiltroProveedores(filtroProveedores);
    }

    public Instituciones getFiltroInstituciones() {
        return filtro.getFiltroInstituciones();
    }

    public void setFiltroInstituciones(Instituciones filtroInstituciones) {
        this.filtro.setFiltroInstituciones(filtroInstituciones);
    }

    public String getFiltroTipoMov() {
        return filtro.getFiltroTipoMov();
    }

    public void setFiltroTipoMov(String filtroTipoMov) {
        this.filtro.setFiltroTipoMov(filtroTipoMov);
    }

    public Date getFiltroFechaInicial() {
        return filtro.getFiltroFechaInicial();
    }

    public void setFiltroFechaInicial(Date filtroFechaInicial) {
        this.filtro.setFiltroFechaInicial(JsfUtil.startDate(filtroFechaInicial));
    }

    public Date getFiltroFechaFinal() {
        return filtro.getFiltroFechaFinal();
    }

    public void setFiltroFechaFinal(Date filtroFechaFinal) {
        this.filtro.setFiltroFechaFinal(JsfUtil.endDate(filtroFechaFinal));
    }

    public Programas getFiltroProgramas() {
        return this.filtro.getFiltroProgramas();
    }

    public void setFiltroProgramas(Programas value) {
        this.filtro.setFiltroProgramas(value);
    }

    public LazyDataModel<MovtosDet> getDataModel() {
        return dataModel;
    }

    public boolean isTableRender() {
        return tableRender;
    }

    public void setTableRender(boolean tableRender) {
        this.tableRender = tableRender;
    }
    //</editor-fold>

    public String getFirstSucursal(Donantes d) {
        if (d != null) {
            return d.getSucursalesCollection().iterator().next().getIDSucursal();
        } else {
            return "";
        }
    }

    public String getFilename() {
        return "MovimientosDet_" + DateFormat.getDateInstance().format(new Date());
    }
    private static final String[] columnasActivadas = new String[]{
        "iDFolio",
        "iDArea",
        "iDArticulo",
        "programa",
        "fechaCad",
        "cantidad",
        "peso",
        "merma",
        "costoBenef",
        "cuotaRecup",
        "tipoMov",
        "fechaMov"
    };

    public List<Origen> completeOrigen(String query) {
        if (getFiltroTipoMov() == null) {
            return Arrays.asList(Origen.values());
        } else {
            return new ArrayList<Origen>(TipoMov.valueByCodigo(getFiltroTipoMov()).getOrigenSet());
        }
    }
}
