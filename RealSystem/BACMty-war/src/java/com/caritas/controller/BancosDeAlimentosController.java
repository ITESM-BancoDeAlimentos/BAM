package com.caritas.controller;

import com.caritas.controller.abstractController.AbstractController;
import com.caritas.controller.abstractController.CrudController;
import com.caritas.controller.util.JsfUtil;
import com.caritas.entity.BancosDeAlimentos;
import com.caritas.facade.BancosDeAlimentosFacade;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean(name = "bancosDeAlimentosController")
@ViewScoped
public class BancosDeAlimentosController extends AbstractController<BancosDeAlimentos> implements Serializable, CrudController {

    @EJB
    private com.caritas.facade.BancosDeAlimentosFacade ejbFacade;
    @ManagedProperty("#{bancosDeAlimentosSelected}")
    private BancosDeAlimentosSelected bdas;
    private LazyDataModel<BancosDeAlimentos> items = new LazyDataModel<BancosDeAlimentos>() {
        @Override
        public List<BancosDeAlimentos> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
            return ejbFacade.findRange(new int[]{first, first + pageSize});
        }
    };

    //<editor-fold defaultstate="collapsed" desc="init">
    public BancosDeAlimentosController() {
    }

    @PostConstruct
    private void initialize() {
        items.setRowCount(ejbFacade.count());
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter/Setter">

    @Override
    public BancosDeAlimentos getCurrent() {
        return getSelected();
    }

    @Override
    public void setCurrent(BancosDeAlimentos current) {
        setSelected(current);
    }

    public void setBdas(BancosDeAlimentosSelected bdas) {
        this.bdas = bdas;
    }

    public void setSelected(BancosDeAlimentos selected) {
        bdas.setSelected(selected);
    }

    public BancosDeAlimentos getSelected() {
        return bdas.getCurrent();
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

    public BancosDeAlimentos getBancosDeAlimentos(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Prepare">
    public String prepareCreate() {
        setSelected(new BancosDeAlimentos());
        return "Create";
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Commit">

    public String create() {
        BancosDeAlimentos s = getSelected();
        if (s == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RequiredMessage_NullSelected_Key"));
            return null;
        }
        try {
            ejbFacade.create(s);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BancosDeAlimentosCreated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e);
            return null;
        }
    }

    public String update() {
        BancosDeAlimentos s = getSelected();
        if (s == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RequiredMessage_NullSelected_Key"));
            return null;
        }
        if (ejbFacade.find(s.getIDBancoDeAlimentos()) == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_RowNoLongerExists"));
            return null;
        }
        try {
            ejbFacade.edit(getSelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BancosDeAlimentosUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    protected String performDestroy(BancosDeAlimentos s) {
        if (s == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RequiredMessage_NullSelected_Key"));
            return null;
        }
        if (ejbFacade.find(s.getIDBancoDeAlimentos()) == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_RowNoLongerExists"));
            return null;
        }
        try {
            ejbFacade.remove(s);
            setSelected(null);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BancosDeAlimentosDeleted"));
            return "List";
        } catch (Exception e) {
            String className = this.getClass().getCanonicalName();
            Logger l = Logger.getLogger(className);
            l.logp(Level.WARNING, className, "performDestroy", "", e);
            JsfUtil.addErrorMessage(e);
            return null;
        }
    }

    //</editor-fold>
    @FacesConverter(forClass = BancosDeAlimentos.class)
    public static class BancosDeAlimentosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BancosDeAlimentosController controller = (BancosDeAlimentosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "bancosDeAlimentosController");
            return controller.getBancosDeAlimentos(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof BancosDeAlimentos) {
                BancosDeAlimentos o = (BancosDeAlimentos) object;
                return getStringKey(o.getIDBancoDeAlimentos());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + BancosDeAlimentos.class.getName());
            }
        }
    }

    @ManagedBean(name = "bancosDeAlimentosCompleter")
    @ApplicationScoped
    public static class BancosDeAlimentosCompleter {

        @EJB
        private BancosDeAlimentosFacade bancos;
        @ManagedProperty("#{parametersController.suggestionLength}")
        private int length;

        public List<BancosDeAlimentos> complete(String query) {
            return bancos.findLike(query, length);
        }

        public void setLength(int length) {
            this.length = length;
        }
    }

    @ManagedBean(name = "bancosDeAlimentosSelected")
    @SessionScoped
    public static class BancosDeAlimentosSelected implements Serializable {

        private BancosDeAlimentos current;

        public BancosDeAlimentos getCurrent() {
            return current;
        }

        public void setSelected(BancosDeAlimentos current) {
            this.current = current;
        }
    }
}
