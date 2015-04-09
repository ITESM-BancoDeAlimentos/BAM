package com.caritas.controller;

import com.caritas.controller.abstractController.AbstractController;
import com.caritas.controller.util.JsfUtil;
import com.caritas.entity.NivelAcceso;
import com.caritas.facade.NivelAccesoFacade;
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

@ManagedBean(name = "nivelAccesoController")
@ViewScoped
public class NivelAccesoController extends AbstractController<NivelAcceso> implements Serializable {

    @EJB
    private NivelAccesoFacade ejbFacade;
    @ManagedProperty("#{nivelAccesoSelected}")
    private NivelAccesoSelected nas;
    private List<NivelAcceso> accesosAll = null;
    private LazyDataModel<NivelAcceso> items = new LazyDataModel< NivelAcceso>() {
        @Override
        public List<NivelAcceso> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
            return ejbFacade.findRange(new int[]{first, first + pageSize});
        }
    };

    public NivelAccesoController() {
    }

    @PostConstruct
    private void initialize() {
        items.setRowCount(getFacade().count());
    }

    public String prepareCreate() {
        setCurrent(new NivelAcceso());
        return "Create";
    }

    public String create() {
        NivelAcceso current = getCurrent();
        if (current == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RequiredMessage_NullSelected_Key"));
            return null;
        }
        if (null != getFacade().find(current.getIDNivel())) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("NivelAccesoErrorMessage_IDNivelNotUnique"));
            return null;
        }
        try {
            getFacade().create(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("NivelAccesoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String update() {
        NivelAcceso current = getCurrent();
        if (null == current) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RequiredMessage_NullSelected_Key"));
            return null;
        }
        if (null == ejbFacade.find(current.getIDNivel())) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_RowNoLongerExists"));
            return null;
        }
        try {
            getFacade().edit(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("NivelAccesoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    @Override
    protected String performDestroy(NivelAcceso rowData) {
        if (rowData == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RequiredMessage_NullSelected_Key"));
            return null;
        }
        if (ejbFacade.find(rowData.getIDNivel()) == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_RowNoLongerExists"));
            return "List";
        }
        try {
            getFacade().remove(rowData);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("NivelAccesoDeleted"));
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="GetSet">
    public NivelAcceso getSelected() {
        return getCurrent();
    }

    public void setSelected(NivelAcceso a) {
        setCurrent(a);
    }

    private NivelAccesoFacade getFacade() {
        return ejbFacade;
    }
    private SelectItem[] iasm = null;

    public SelectItem[] getItemsAvailableSelectMany() {
        if (iasm == null) {
            iasm = JsfUtil.getSelectItems(getAccesosAll(), false);
        }
        return iasm;
    }
    private SelectItem[] iaso = null;

    public SelectItem[] getItemsAvailableSelectOne() {
        if (iaso == null) {
            iaso = JsfUtil.getSelectItems(getAccesosAll(), true);
        }
        return iaso;
    }

    @Override
    public NivelAcceso getCurrent() {
        return nas.getCurrent();
    }

    @Override
    public void setCurrent(NivelAcceso current) {
        nas.setCurrent(current);
    }

    @Override
    public DataModel<NivelAcceso> getItems() {
        return items;
    }

    /**
     * @param nas the nas to set
     */
    public void setNas(NivelAccesoSelected nas) {
        this.nas = nas;
    }

    public List<NivelAcceso> getAccesosAll() {
        if (accesosAll == null) {
            accesosAll = ejbFacade.findAll();
        }
        return accesosAll;
    }

    //</editor-fold>
    @FacesConverter(forClass = NivelAcceso.class)
    public static class NivelAccesoControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NivelAccesoController controller = (NivelAccesoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "nivelAccesoController");
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
            if (object instanceof NivelAcceso) {
                NivelAcceso o = (NivelAcceso) object;
                return getStringKey(o.getIDNivel());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + NivelAccesoController.class.getName());
            }
        }
    }

    @ManagedBean(name = "nivelAccesoSelected")
    @SessionScoped
    public static class NivelAccesoSelected implements Serializable {

        private NivelAcceso current;

        /**
         * @return the current
         */
        public NivelAcceso getCurrent() {
            return current;
        }

        /**
         * @param current the current to set
         */
        public void setCurrent(NivelAcceso current) {
            this.current = current;
        }
    }
}
