package com.caritas.controller;

import com.caritas.entity.FoliosEntrada;
import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.facade.FoliosEntradaFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "foliosEntradaController")
@SessionScoped
public class FoliosEntradaController implements Serializable {

    private FoliosEntrada current;
    private DataModel items = null;
    @EJB
    private com.caritas.facade.FoliosEntradaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public FoliosEntradaController() {
    }

    public FoliosEntrada getSelected() {
        if (current == null) {
            current = new FoliosEntrada();
            selectedItemIndex = -1;
        }
        return current;
    }

    private FoliosEntradaFacade getFacade() {
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
        current = (FoliosEntrada) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new FoliosEntrada();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FoliosEntradaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void updateFromMovs(String folioEntrada, String folioAnterior) {
        try {
            folioEntrada = folioEntrada.trim();

            while (folioEntrada.length() < 10) {
                folioEntrada = folioEntrada.concat(" ");
            }

            while (folioAnterior.length() < 10) {
                folioAnterior = folioAnterior.concat(" ");
            }

            //FoliosEntrada anterior = new FoliosEntrada(folioAnterior);
            FoliosEntrada anterior = getFacade().find(folioAnterior);

            current = new FoliosEntrada(folioEntrada);
            getFacade().create(current);
            getFacade().remove(anterior);
            
        } catch (Exception e) {
            // Nunca debería entrar aquí, si lo hace, hay un error en la BD ó el código
            System.out.println("Error! Depurar para comprobar --> FoliosEntradaController.updateFromMovs(String,String)"); 
        }
    }

    public String prepareEdit() {
        current = (FoliosEntrada) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FoliosEntradaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (FoliosEntrada) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FoliosEntradaDeleted"));
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

    @FacesConverter(forClass = FoliosEntrada.class)
    public static class FoliosEntradaControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FoliosEntradaController controller = (FoliosEntradaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "foliosEntradaController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof FoliosEntrada) {
                FoliosEntrada o = (FoliosEntrada) object;
                return getStringKey(o.getFolioEntrada());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FoliosEntradaController.class.getName());
            }
        }
    }
}
