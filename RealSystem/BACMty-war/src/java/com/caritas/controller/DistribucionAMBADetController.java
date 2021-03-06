package com.caritas.controller;

import com.caritas.entity.DistribucionAMBADet;
import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.entity.BancosDeAlimentos;
import com.caritas.entity.DistribucionAMBADetPK;
import com.caritas.facade.DistribucionAMBADetFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "distribucionAMBADetController")
@ViewScoped
public class DistribucionAMBADetController implements Serializable {

    private DistribucionAMBADet current;
    private DataModel items = null;
    @EJB
    private com.caritas.facade.DistribucionAMBADetFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public DistribucionAMBADetController() {
    }

    public DistribucionAMBADet getSelected() {
        if (current == null) {
            current = new DistribucionAMBADet();
            current.setDistribucionAMBADetPK(new com.caritas.entity.DistribucionAMBADetPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private DistribucionAMBADetFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (DistribucionAMBADet) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new DistribucionAMBADet();
        current.setDistribucionAMBADetPK(new com.caritas.entity.DistribucionAMBADetPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getDistribucionAMBADetPK().setIDBancoDeAlimentos(current.getBancosDeAlimentos().getIDBancoDeAlimentos());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DistribucionAMBADetCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle1").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void createFromDistAmba(String idFolio, DistribucionAMBA_Tabla item) {
        BancosDeAlimentos banco = getFacade().findBancoDeAlimentos(item.getBancoDeAlimentos());

        current = null;
        getSelected().getDistribucionAMBADetPK().setIDFolio(idFolio);
        getSelected().setRecibe(item.getRecibe());
        getSelected().setRemision(item.getRemision());
        getSelected().setBancosDeAlimentos(banco);
        getSelected().setPoblacion(item.getPoblacion());
        getSelected().setPorcentaje(item.getPorcentaje());
        getSelected().setKilogramos(item.getKilogramos());
        getSelected().setPiezas(item.getPiezas());
        getSelected().setFlete(item.getFlete());
        getSelected().setObservaciones(item.getObservaciones());

        current.getDistribucionAMBADetPK().setIDBancoDeAlimentos(current.getBancosDeAlimentos().getIDBancoDeAlimentos());
        getFacade().create(current);
    }

    public void updateFromDistAmba(String idFolio, DistribucionAMBA_Tabla item) {
        BancosDeAlimentos banco = getFacade().findBancoDeAlimentos(item.getBancoDeAlimentos());
        
        current = null;
        getSelected().getDistribucionAMBADetPK().setIDFolio(idFolio);
        getSelected().setRecibe(item.getRecibe());
        getSelected().setRemision(item.getRemision());
        getSelected().setBancosDeAlimentos(banco);
        getSelected().setPoblacion(item.getPoblacion());
        getSelected().setPorcentaje(item.getPorcentaje());
        getSelected().setKilogramos(item.getKilogramos());
        getSelected().setPiezas(item.getPiezas());
        getSelected().setFlete(item.getFlete());
        getSelected().setObservaciones(item.getObservaciones());

        current.getDistribucionAMBADetPK().setIDBancoDeAlimentos(current.getBancosDeAlimentos().getIDBancoDeAlimentos());
        getFacade().edit(current);
    }
    
    void destroyFromDistAmba(String idFolio, DistribucionAMBA_Tabla item) {
        BancosDeAlimentos banco = getFacade().findBancoDeAlimentos(item.getBancoDeAlimentos());
        
        current = new DistribucionAMBADet(new DistribucionAMBADetPK(idFolio, banco.getIDBancoDeAlimentos()));
        
        current = getFacade().find(getSelected().getDistribucionAMBADetPK());
        
        getFacade().remove(current);
    }

    public String prepareEdit() {
        current = (DistribucionAMBADet) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getDistribucionAMBADetPK().setIDBancoDeAlimentos(current.getBancosDeAlimentos().getIDBancoDeAlimentos());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle1").getString("DistribucionAMBADetUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle1").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (DistribucionAMBADet) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle1").getString("DistribucionAMBADetDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle1").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public DistribucionAMBADet getDistribucionAMBADet(com.caritas.entity.DistribucionAMBADetPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = DistribucionAMBADet.class)
    public static class DistribucionAMBADetControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DistribucionAMBADetController controller = (DistribucionAMBADetController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "distribucionAMBADetController");
            return controller.getDistribucionAMBADet(getKey(value));
        }

        com.caritas.entity.DistribucionAMBADetPK getKey(String value) {
            com.caritas.entity.DistribucionAMBADetPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.caritas.entity.DistribucionAMBADetPK();
            key.setIDFolio(values[0]);
            key.setIDBancoDeAlimentos(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.caritas.entity.DistribucionAMBADetPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIDFolio());
            sb.append(SEPARATOR);
            sb.append(value.getIDBancoDeAlimentos());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof DistribucionAMBADet) {
                DistribucionAMBADet o = (DistribucionAMBADet) object;
                return getStringKey(o.getDistribucionAMBADetPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + DistribucionAMBADet.class.getName());
            }
        }
    }
}
