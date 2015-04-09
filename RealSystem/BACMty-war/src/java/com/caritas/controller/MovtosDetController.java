package com.caritas.controller;

import com.caritas.entity.MovtosDet;
import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.entity.Articulos;
import com.caritas.entity.Movimientos;
import com.caritas.entity.TablaArticulos;
import com.caritas.facade.MovtosDetFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "movtosDetController")
@SessionScoped
public class MovtosDetController implements Serializable {

    private MovtosDet current;
    private DataModel items = null;
    @EJB
    private com.caritas.facade.MovtosDetFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    @ManagedProperty(value = "#{existenciasController}")
    private ExistenciasController existenciasController;

    public MovtosDetController() {
    }

    public MovtosDet getSelected() {
        if (current == null) {
            current = new MovtosDet();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MovtosDetFacade getFacade() {
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
        current = (MovtosDet) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new MovtosDet();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovtosDetCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (MovtosDet) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovtosDetUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (MovtosDet) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovtosDetDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
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

    void destroyFromMovtos(String idFolio) {
        List<MovtosDet> listToRemove = getFacade().findEntByIDFolio(idFolio);
        
        for(MovtosDet temp : listToRemove) {
            getFacade().remove(temp);
        }
    }

    void createFromMovimientos(Articulos temp, TablaArticulos itemActual, Movimientos MovtosCurrent) {
        current = new MovtosDet();
        
        current.setIDFolio(MovtosCurrent.getIDFolio());
        current.setIDArea(MovtosCurrent.getIDArea());
        current.setIDPrograma(MovtosCurrent.getIDPrograma());
        current.setTipoMov(MovtosCurrent.getTipoMov());
        current.setIDArticulo(temp);
        current.setFechaCad(itemActual.getFechaCaducidad());
        current.setCantidad(itemActual.getCantidad());
        current.setPeso(temp.getPeso());
        current.setMerma(itemActual.getMerma());
        current.setCostoBenef(temp.getCostoBenef1());
        current.setCuotaRecup(temp.getCuotaRecup1());
        
        getFacade().create(current);
        
        getExistenciasController().updateExistenciasFromMovtos_Det_ENT(current);
    }

    public ExistenciasController getExistenciasController() {
        return existenciasController;
    }

    public void setExistenciasController(ExistenciasController existenciasController) {
        this.existenciasController = existenciasController;
    }

//    void updateFromMovimientos(Articulos temp, TablaArticulos itemActual, Movimientos MovtosCurrent, int indice) {
//        current = ejbFacade.findByIDFolio(MovtosCurrent.getIDFolio()).get(indice);
//        
//        current.setIDFolio(MovtosCurrent.getIDFolio());
//        current.setIDArea(MovtosCurrent.getIDArea());
//        current.setIDPrograma(MovtosCurrent.getIDPrograma());
//        current.setTipoMov(MovtosCurrent.getTipoMov());
//        current.setIDArticulo(temp);
//        current.setFechaCad(itemActual.getFechaCaducidad());
//        current.setCantidad(itemActual.getCantidad());
//        current.setPeso(temp.getPeso());
//        current.setMerma(itemActual.getMerma());
//        current.setCostoBenef(temp.getCostoBenef1());
//        current.setCuotaRecup(temp.getCuotaRecup1());
//        
//        getFacade().edit(current);
//    }

    @FacesConverter(forClass = MovtosDet.class)
    public static class MovtosDetControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MovtosDetController controller = (MovtosDetController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "movtosDetController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof MovtosDet) {
                MovtosDet o = (MovtosDet) object;
                return getStringKey(o.getIDMovtosDet());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + MovtosDetController.class.getName());
            }
        }
    }
}
