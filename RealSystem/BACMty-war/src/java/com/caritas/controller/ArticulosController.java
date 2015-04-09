package com.caritas.controller;

import com.caritas.controller.abstractController.AbstractController;
import com.caritas.controller.util.JsfUtil;
import com.caritas.entity.Articulos;
import com.caritas.facade.ArticulosFacade;
import com.caritas.facade.DonantesFacade;
import com.caritas.facade.GrupoAlimFacade;
import com.caritas.facade.GrupoRefFacade;
import com.caritas.facade.VariedadFacade;
import com.caritas.filters.ArticulosFilters;
import com.caritas.util.EjbUtil;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author software
 */
@ManagedBean(name = "articulosController")
@ViewScoped
public class ArticulosController extends AbstractController<Articulos> implements Serializable {

    @EJB
    private transient ArticulosFacade ejbFacade;
    @EJB
    private transient DonantesFacade donantesFacade;
    @EJB
    private transient GrupoAlimFacade grupoAlimFacade;
    @EJB
    private transient VariedadFacade variedadFacade;
    @EJB
    private transient GrupoRefFacade grupoRefFacade;
    @ManagedProperty("#{articulosSelected}")
    private ArticulosSelected selectedBean;
    private ArticulosFilters filtros;
    private LazyDataModel<Articulos> items = new LazyDataModel<Articulos>() {
        @Override
        public List<Articulos> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
            return ejbFacade.find(filtros, first, pageSize);
        }
    };

    //<editor-fold defaultstate="collapsed" desc="init">
    public ArticulosController() {
    }

    @PostConstruct
    private void initialize() {
        items.setRowCount(ejbFacade.count());
        filtros = new ArticulosFilters();
    }

    public void filter() {
        items.setRowCount(ejbFacade.count(filtros));
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public ArticulosFilters getFilters() {
        return filtros;
    }

    public void setFilters(ArticulosFilters filters) {
        this.filtros = filters;
    }

    public Articulos getSelected() {
        return selectedBean.getSelected();
    }

    public void setSelected(Articulos selected) {
        this.selectedBean.setSelected(selected);
    }

    public void setSelectedBean(ArticulosSelected selectedBean) {
        this.selectedBean = selectedBean;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getItemsAvailableSelectArticulos() {
        return JsfUtil.getSelectItems(ejbFacade.findArticulos(), false);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Prepare">

    public String prepareCreate() {
        Articulos articulos1 = new Articulos();
        articulos1.setIDDonante(donantesFacade.find("0"));
        articulos1.setIDGrupoAlim(grupoAlimFacade.find("0"));
        articulos1.setIDVariedad(variedadFacade.find("0"));
        articulos1.setIDGrupoRef(grupoRefFacade.find("0"));

        articulos1.setAlmacen(com.caritas.enums.Almacen.Almacen.getCode());

        articulos1.setCostoBenef1(0.0);
        articulos1.setCuotaRecup1(0.0);
        articulos1.setReorden1(0.0);

        articulos1.setCostoBenef2(0.0);
        articulos1.setCuotaRecup2(0.0);
        articulos1.setReorden2(0.0);

        articulos1.setCantidadUni(1.0);
        articulos1.setPeso(0.0);

        this.setSelected(articulos1);
        return "Create";
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Commit">

    public String create() {
        String idArticulo = getSelected().getIDArticulo().trim();
        if (ejbFacade.isIdUsed(idArticulo)) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ArticulosErrorMessage_IDArticuloNotUnique"));
            return null;
        }
        try {
            ejbFacade.create(getSelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArticulosCreated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String update() {
        Articulos selected = getSelected();
        if (null == selected) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RequiredMessage_NullSelected_Key"));
            return null;
        }
        if (null == ejbFacade.find(selected.getIDArticulo())) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_RowNoLongerExists"));
            return null;
        }
        try {
            ejbFacade.edit(getSelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArticulosUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    protected String performDestroy(Articulos s) {
        if (s == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RequiredMessage_NullSelected_Key"));
            return null;
        }
        if (ejbFacade.find(s.getIDArticulo()) == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_RowNoLongerExists"));
            return "List";
        }
        try {
            ejbFacade.remove(s);
            setSelected(null);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArticulosDeleted"));
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    //</editor-fold>

    @Override
    public Articulos getCurrent() {
        return getSelected();
    }

    @Override
    public void setCurrent(Articulos current) {
        setSelected(current);
    }

    @Override
    public DataModel<Articulos> getItems() {
        return items;
    }

    @ManagedBean(name = "articulosCompleter")
    @ApplicationScoped
    public static class ArticulosCompleter implements Serializable {

        @ManagedProperty("#{parametersController.suggestionLength}")
        private int length;
        @EJB
        private ArticulosFacade articulos;

        public List<Articulos> complete(String query) {
            return articulos.findLike(query, length);
        }

        public void setLength(int length) {
            this.length = length;
        }
    }

    @ManagedBean(name = "articulosSelected")
    @SessionScoped
    public static class ArticulosSelected implements Serializable {

        private Articulos selected;

        public Articulos getSelected() {
            return selected;
        }

        public void setSelected(Articulos selected) {
            this.selected = selected;
        }
    }

    @FacesConverter(forClass = Articulos.class)
    public static class ArticulosControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ArticulosController controller = (ArticulosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "articulosController");
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
            if (object instanceof Articulos) {
                Articulos o = (Articulos) object;
                return getStringKey(o.getIDArticulo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ArticulosController.class.getName());
            }
        }
    }

    @FacesValidator(value = "uniqueIDArticulosValidator")
    public static class UniqueIDArticulosValidator implements Validator {

        ArticulosFacade ejbFacade = EjbUtil.lookup(ArticulosFacade.class);

        @Override
        public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
            if (!(value instanceof String) || ejbFacade.isIdUsed((String) value)) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ArticulosErrorMessage_IDArticuloNotUnique"));
                throw new ValidatorException(new FacesMessage("No es ID Unico", (ResourceBundle.getBundle("/Bundle").getString("ArticulosErrorMessage_IDArticuloNotUnique"))));
            }
        }
    }
}
