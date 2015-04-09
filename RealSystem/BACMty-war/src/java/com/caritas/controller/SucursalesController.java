package com.caritas.controller;

import com.caritas.controller.abstractController.AbstractController;
import com.caritas.controller.abstractController.CrudController;
import com.caritas.controller.util.JsfUtil;
import com.caritas.entity.Sucursales;
import com.caritas.facade.SucursalesFacade;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean(name = "sucursalesController")
@ViewScoped
public class SucursalesController extends AbstractController<Sucursales> implements Serializable, CrudController {

    @EJB
    private com.caritas.facade.SucursalesFacade ejbFacade;
    @ManagedProperty(value = "#{sucursalesSelected}")
    private SucursalesSelected ss;
    private LazyDataModel<Sucursales> items = new LazyDataModel<Sucursales>() {
        @Override
        public List<Sucursales> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
            return getFacade().findRange(new int[]{first, first + pageSize});
        }
    };
    //<editor-fold defaultstate="collapsed" desc="init">

    @PostConstruct
    private void initialize() {
        items.setRowCount(getFacade().count());
    }

    public Sucursales newDefaultSucursales() {
        Sucursales sucursales = new Sucursales();
        sucursales.setMail("@");
        return sucursales;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter/setter">
    public void setSs(SucursalesSelected ss) {
        this.ss = ss;
    }

    @Override
    public Sucursales getCurrent() {
        return ss.getCurrent();
    }

    @Override
    public void setCurrent(Sucursales current) {
        ss.setCurrent(current);
    }

    public DataModel getItems() {
        return items;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public void setSelected(Sucursales selected) {
        setCurrent(selected);
    }

    public Sucursales getSelected() {
        return getCurrent();
    }

    private SucursalesFacade getFacade() {
        return ejbFacade;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="prepare">

    public String prepareCreate() {
        setCurrent(newDefaultSucursales());
        return "Create";
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="commit">

    @Override
    public String create() {
        try {
            getFacade().create(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SucursalesCreated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    @Override
    public String update() {
        try {
            getFacade().edit(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SucursalesUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    @Override
    protected String performDestroy(Sucursales s) {
        if (s == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RequiredMessage_NullSelected_Key"));
            return null;
        }
        if (getFacade().find(s.getIDSucursales()) == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_RowNoLongerExists"));
            return null;
        }
        try {
            ejbFacade.remove(s);
            setSelected(null);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SucursalesDeleted"));
            return "List";
        } catch (Exception e) {
            JsfUtil.addSuccessMessage(e.getLocalizedMessage());
            return null;
        }
    }
    //</editor-fold>

    @FacesConverter(forClass = Sucursales.class)
    public static class SucursalesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SucursalesController controller = (SucursalesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sucursalesController");
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
            if (object instanceof Sucursales) {
                Sucursales o = (Sucursales) object;
                return getStringKey(o.getIDSucursales());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SucursalesController.class.getName());
            }
        }
    }

    @ManagedBean(name = "sucursalesSelected")
    @SessionScoped
    public static class SucursalesSelected {

        private Sucursales current;

        public Sucursales getCurrent() {
            return current;
        }

        public void setCurrent(Sucursales current) {
            this.current = current;
        }
    }
}
