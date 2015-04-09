package com.caritas.controller;

import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.abstractController.CrudController;
import com.caritas.entity.Donantes;
import com.caritas.entity.Usuarios;
import com.caritas.facade.DonantesFacade;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
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
import javax.persistence.EntityExistsException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean(name = "donantesController")
@ViewScoped
public class DonantesController implements Serializable, CrudController {

    @EJB
    private com.caritas.facade.DonantesFacade ejbFacade;
    @ManagedProperty(value = "#{donantesSelected}")
    private DonantesSelected selectedEjb;
    private LazyDataModel<Donantes> items = new LazyDataModel<Donantes>() {
        @Override
        public List<Donantes> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
            return ejbFacade.findRange(new int[]{first, first + pageSize});
        }
    };

    //<editor-fold defaultstate="collapsed" desc="Init">
    public DonantesController() {
    }

    @PostConstruct
    private void initialize() {
        items.setRowCount(getFacade().count());
    }

    private Donantes newDefaultDonantes() {
        Donantes defaultDonantes = new Donantes();
        Usuarios loggedUser = JsfUtil.getLoggedUser();
        defaultDonantes.setUsuarioAlta(loggedUser.getNombre());
        defaultDonantes.setFechaAlta(new Date());
        defaultDonantes.setFrecuencia("O");
        defaultDonantes.setMail("@");
        return defaultDonantes;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter/setter">

    public Donantes getCurrent() {
        return selectedEjb.getSelected();
    }

    public void setCurrent(Donantes current) {
        selectedEjb.setSelected(current);
    }

    public Donantes getSelected() {
        return getCurrent();
    }

    public void setSelected(Donantes selected) {
        setCurrent(selected);
    }

    public void setSelectedEjb(DonantesSelected selectedEjb) {
        this.selectedEjb = selectedEjb;
    }

    private DonantesFacade getFacade() {
        return ejbFacade;
    }

    public DataModel<Donantes> getItems() {
        return items;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getItemsAvailableSucursalesSelectOne(boolean sucursales) {
        return JsfUtil.getSelectItems(ejbFacade.findBySucursales(sucursales), false);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="prepare">
    @Override
    public String prepareViewNoRow() {
        return "View";
    }

    @Override
    public String prepareEditNoRow() {
        return "Edit";
    }

    @Override
    public String prepareList() {
        setCurrent(null);
        return "List";
    }

    public String prepareView() {
        setCurrent(getItems().getRowData());
        return "View";
    }

    public String prepareCreate() {
        setCurrent(newDefaultDonantes());
        return "Create";
    }

    public String prepareEdit() {
        setCurrent((Donantes) getItems().getRowData());
        return "Edit";
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="commit">
    public String create() {
        Donantes current = getCurrent();
        current.setTipoDonante(current.getIDTipoDon().getIDTipoDon());
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DonantesCreated"));
            return prepareCreate();
        } catch (EntityExistsException eee) {
            JsfUtil.addErrorMessage(eee, ResourceBundle.getBundle("/Bundle").getString("El registro ya existe"));
            return null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String update() {
        Donantes current = getCurrent();
        if (current == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RequiredMessage_NullSelected_Key"));
            return null;
        }
        if (getFacade().find(current.getIDDonante()) == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_RowNoLongerExists"));
            return "List";
        }
        getCurrent().setTipoDonante(getCurrent().getIDTipoDon().getIDTipoDon());
        try {
            getFacade().edit(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DonantesUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getLocalizedMessage());
            return null;
        }
    }

    private String performDestroy(Donantes current) {
        if (current == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RequiredMessage_NullSelected_Key"));
            return null;
        }
        if (getFacade().find(current.getIDDonante()) == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_RowNoLongerExists"));
            return "List";
        }
        try {
            getFacade().remove(current);
            setCurrent(null);
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getLocalizedMessage());
            return null;
        }
    }

    public String destroy() {
        Donantes current = getItems().getRowData();
        return performDestroy(current);
    }

    @Override
    public String destroyNoRow() {
        Donantes current = getCurrent();
        return performDestroy(current);
    }
    //</editor-fold>

    @FacesConverter(forClass = Donantes.class)
    public static class DonantesControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DonantesController controller = (DonantesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "donantesController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Donantes) {
                Donantes o = (Donantes) object;
                return getStringKey(o.getIDDonante());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + DonantesController.class.getName());
            }
        }
    }

    @ManagedBean(name = "donantesSelected")
    @SessionScoped
    public static class DonantesSelected implements Serializable {

        private Donantes selected;

        public Donantes getSelected() {
            return selected;
        }

        public void setSelected(Donantes selected) {
            this.selected = selected;
        }
    }

    @ManagedBean(name = "donantesCompleter")
    @ApplicationScoped
    public static class DonantesCompleter implements Serializable {

        @EJB
        private DonantesFacade donantes;
        @ManagedProperty("#{parametersController.suggestionLength}")
        private int length;

        public List<Donantes> complete(String query) {
            return donantes.findLike(query, length);
        }

        public void setLength(int suggestionsLength) {
            this.length = suggestionsLength;
        }
    }
}
