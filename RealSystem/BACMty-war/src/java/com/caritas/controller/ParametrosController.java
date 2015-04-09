package com.caritas.controller;

import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.entity.Parametros;
import com.caritas.facade.ParametrosFacade;
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

@ManagedBean(name = "parametrosController")
@SessionScoped
public class ParametrosController implements Serializable {

    private DataModel items = null;
    @EJB
    private com.caritas.facade.ParametrosFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Parametros current;

    public ParametrosController() {
    }

    public Parametros getSelected() {
        if (getCurrent() == null) {
            setCurrent(new Parametros());
            selectedItemIndex = -1;
        }
        return getCurrent();
    }

    private ParametrosFacade getFacade() {
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
        setCurrent((Parametros) getItems().getRowData());
        selectedItemIndex = getPagination().getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareViewNoRow() {
        setCurrent(new Parametros());
        return "View";
    }
    
    public String prepareEditNoRow() {
        setCurrent(new Parametros());
        return "Edit";
    }

    public String prepareCreate() {
        setCurrent(new Parametros());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ParametrosCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        setCurrent((Parametros) getItems().getRowData());
        selectedItemIndex = getPagination().getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            if (!current.getParametro().equals(" ")) {
                getFacade().edit(getCurrent());
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ParametrosUpdated"));
                return "View";
            } else {
                setCurrent(new Parametros());
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RequiredMessage_NullSelected_Key"));
                return null;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        setCurrent((Parametros) getItems().getRowData());
        selectedItemIndex = getPagination().getPageFirstItem() + getItems().getRowIndex();
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
            getFacade().remove(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ParametrosDeleted"));
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
            if (getPagination().getPageFirstItem() >= count) {
                getPagination().previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            setCurrent(getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0));
        }
    }

    public DataModel getItems() {
        if (items == null) {
            //items = getPagination().createPageDataModel();
            items = new ListDataModel(ejbFacade.findAll());
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

    public SelectItem[] getItemsAvailableSelectParametros() {
        return JsfUtil.getSelectItems(ejbFacade.findParametros(), false);
    }

    public void viewParametros() {
        checkIfRemoved();

        if (getSelected().getParametro() != null && !getSelected().getParametro().equals(" ")) {
            getCurrent().setValor(ejbFacade.find(getCurrent().getParametro()));
        } else {
            setCurrent(new Parametros());
        }
        //return "View";
    }

    private void checkIfRemoved() {
        boolean isRemoved;

        isRemoved = ejbFacade.findRemoved(getCurrent().getParametro());

        if (isRemoved) {
            JsfUtil.addErrorMessage("El parámetro ya no existe.");
            setCurrent(null);
        }
    }

    public Parametros getCurrent() {
        return current;
    }

    public void setCurrent(Parametros current) {
        this.current = current;
    }

    @FacesConverter(forClass = Parametros.class)
    public static class ParametrosControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ParametrosController controller = (ParametrosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "parametrosController");
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
            if (object instanceof Parametros) {
                Parametros o = (Parametros) object;
                return getStringKey(o.getParametro());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Parametros.class.getName());
            }
        }
    }
}
