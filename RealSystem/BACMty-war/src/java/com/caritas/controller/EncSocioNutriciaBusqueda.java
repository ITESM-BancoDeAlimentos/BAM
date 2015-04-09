package com.caritas.controller;

import com.caritas.entity.EncSocioNutriciaInd;
import com.caritas.entity.Instituciones;
import com.caritas.facade.EncSocioNutriciaFacade;
import com.caritas.facade.EncSocioNutriciaIndFacade;
import com.caritas.filters.EncSocioNutriciaFilters;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * Controller para /encSocioNutricia/Busqueda.xhtml. Maneja las columnas activas
 * de tabla. Maneja los filtros activos de tabla.
 *
 * @author software
 */
@ManagedBean
@ViewScoped
public class EncSocioNutriciaBusqueda implements Serializable {

    private ResourceBundle bundle = ResourceBundle.getBundle("/Bundle");
    /**
     * Columnas activas de la tabla de resultados
     */
    private Map<String, Boolean> columns = new HashMap<String, Boolean>(21);
    private EncSocioNutriciaFilters filtro;
    @EJB
    private EncSocioNutriciaIndFacade esnif;
    @EJB
    private EncSocioNutriciaFacade esnf;
    private LazyDataModel<EncSocioNutriciaInd> items = new LazyDataModel<EncSocioNutriciaInd>() {
        @Override
        public List<EncSocioNutriciaInd> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
            List<EncSocioNutriciaInd> results = esnif.findActivo(filtro, first, pageSize);
            int count = esnif.countActivo(filtro);
            items.setRowCount(count);
            return results;
        }
    };
    private boolean tableRender = false;

    public void filter() {
        tableRender = true;
    }

    public void clear() {
        initializeFiltro();
    }

    public String areaGeoOf(EncSocioNutriciaInd e) {
        final String BundleBaseKey = "AreaGeografica_";
        if (e == null) {
            return "";
        }
        if (e.getEncSocioNutriciaIndPK() == null) {
            return "";
        }
        String bundleKey = BundleBaseKey + e.getEncSocioNutriciaIndPK().getAreaGeo();
        if (bundle.containsKey(bundleKey)) {
            return bundle.getString(bundleKey);
        }
        return "???" + bundleKey + "???";
    }

    public List<Instituciones> completeComunidad(String query) {
        if (filtro.getAreaGeo() != null) {
            return esnf.findInstitucionByAreaGeoLike(filtro.getAreaGeo().getCodigo(), query);
        } else {
            return esnf.findInstitucionActivoLike(0, 20, query);
        }
    }
    
    public List<String> completeMunicipio(String query) {
        return esnf.findCiudadLike(0, 20, query);
    }
    

    //<editor-fold defaultstate="collapsed" desc="initialize">
    @PostConstruct
    private void initialize() {
        initializeFiltro();
        initializecolumnSettings();
    }

    public EncSocioNutriciaBusqueda() {
    }

    public void initializeFiltro() {
        filtro = new EncSocioNutriciaFilters();
    }

    private void initializecolumnSettings() {
        for (String activeColumn : defaultColumns) {
            columns.put(activeColumn, Boolean.TRUE);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">

    public String getFilename() {
        return "Beneficiarios_" + DateFormat.getDateInstance().format(new Date());
    }

    public boolean getTableRender() {
        return tableRender;
    }

    public LazyDataModel<EncSocioNutriciaInd> getItems() {
        return items;
    }

    public EncSocioNutriciaFilters getFiltro() {
        return filtro;
    }

    public Map<String, Boolean> getColumns() {
        return columns;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="DefaultColumns">
    private static final String[] defaultColumns = new String[]{
        "nombre",
        "parentesco",
        "fechaNacimiento",
        "anios",
        "meses",
        "genero",
        "estadoCivil",
        "escolaridad",
        "condLaboral",
        "ramaActiv",
        "salario",
        "seguroSocial",
        "probSalud",
        "ayudaAlim",
        "etapaEstFis",};
    //</editor-fold>
}
