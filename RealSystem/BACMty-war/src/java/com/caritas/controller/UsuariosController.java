package com.caritas.controller;

import com.caritas.controller.abstractController.AbstractController;
import com.caritas.controller.util.JsfUtil;
import com.caritas.entity.Accesos;
import com.caritas.entity.Formularios;
import com.caritas.entity.NivelAcceso;
import com.caritas.entity.Usuarios;
import com.caritas.facade.AccesosFacade;
import com.caritas.facade.UsuariosFacade;
import com.caritas.menu.MenuFactory;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.MenuModel;
import org.primefaces.model.SortOrder;

@ManagedBean(name = "usuariosController")
@SessionScoped
public class UsuariosController extends AbstractController<Usuarios> implements Serializable {

    @ManagedProperty("#{usuariosSelected}")
    private UsuariosSelected us;
    @EJB
    private UsuariosFacade ejbFacade;
    private LazyDataModel<Usuarios> items = new LazyDataModel<Usuarios>() {
        @Override
        public List<Usuarios> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
            return getFacade().findRange(new int[]{first, first + pageSize});
        }
    };
    //<editor-fold defaultstate="collapsed" desc="Init">

    public UsuariosController() {
    }

    @PostConstruct
    private void initialize() {
        items.setRowCount(getFacade().count());
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getset">
    public void setUs(UsuariosSelected us) {
        this.us = us;
    }

    public Usuarios getSelected() {
        return getCurrent();
    }

    public void setSelected(Usuarios selected) {
        setCurrent(selected);
    }

    private UsuariosFacade getFacade() {
        return ejbFacade;
    }
    private SelectItem[] itemsAvailableSelectMany = null;

    public SelectItem[] getItemsAvailableSelectMany() {
        if (itemsAvailableSelectMany == null) {
            itemsAvailableSelectMany = JsfUtil.getSelectItems(ejbFacade.findAll(), false);
        }
        return itemsAvailableSelectMany;
    }
    private SelectItem[] itemsAvailableSelectOne = null;

    public SelectItem[] getItemsAvailableSelectOne() {
        if (itemsAvailableSelectOne == null) {
            itemsAvailableSelectOne = JsfUtil.getSelectItems(ejbFacade.findAll(), true);
        }
        return itemsAvailableSelectOne;
    }

    public DataModel getItems() {
        return items;
    }

    public Usuarios getCurrent() {
        return us.getCurrent();
    }

    public void setCurrent(Usuarios current) {
        us.setCurrent(current);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Commit">
    public String prepareCreate() {
        setCurrent(new Usuarios());
        return "Create";
    }

    /**
     * TODO: Validaciones
     */
    public String create() {
        try {
            getFacade().create(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuariosCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String update() {
        try {
            getFacade().edit(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuariosUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    @Override
    protected String performDestroy(Usuarios u) {
        try {
            getFacade().remove(u);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuariosDeleted"));
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    //</editor-fold>

    @FacesConverter(forClass = Usuarios.class)
    public static class UsuariosControllerConverter implements Converter {

        @SuppressWarnings("unchecked")
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuariosController controller = (UsuariosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuariosController");
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
            if (object instanceof Usuarios) {
                Usuarios o = (Usuarios) object;
                return getStringKey(o.getIDUsuario());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UsuariosController.class.getName());
            }
        }
    }

    @ManagedBean(name = "usuariosSelected")
    @SessionScoped
    public static class UsuariosSelected implements Serializable {

        private Usuarios current;

        public Usuarios getCurrent() {
            return current;
        }

        public void setCurrent(Usuarios current) {
            this.current = current;
        }
    }

    @ManagedBean(name = "usuariosAuth")
    @SessionScoped
    public static class UsuariosAuth implements Serializable {

        @ManagedProperty("#{menuFactory}")
        private MenuFactory menuFactory;
        @EJB
        private UsuariosFacade facade;
        @EJB
        private AccesosFacade a;
        private transient MenuModel sMenuModel = null;
        private String IDUsuarioTemp;
        private String passwordTemp;
        private static final String CLASS_NAME = UsuariosAuth.class.getName();

        public String logOut() {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "/login?faces-redirect=true";
        }

        public String logIn() {
            Usuarios usuario;
            try {
                usuario = facade.validateUser(IDUsuarioTemp);
            } catch (Exception e) {
                String className = UsuariosAuth.class.getName();
                Logger l = Logger.getLogger(className);
                l.logp(Level.SEVERE, className, "LogIn", "", e);
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("Error_Generico"));
                return null;
            }
            if ((usuario == null)
                    || (!IDUsuarioTemp.equalsIgnoreCase(usuario.getIDUsuario()))
                    || (!passwordTemp.equals(usuario.getPassword()))) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ErrorLogin"));
                return null;
            } else {
                setLoggedUser(usuario);
                setAccessMaps();
                return "/index?faces-redirect=true";
            }
        }

        public MenuModel getSystemMenu() {
            if (sMenuModel == null) {
                sMenuModel = buildMenu();
            }
            return sMenuModel;
        }

        //<editor-fold defaultstate="collapsed" desc="Get/set">
        public void setMenuFactory(MenuFactory menuFactory) {
            this.menuFactory = menuFactory;
        }

        private void setLoggedUser(Usuarios usuario) {
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();
            sessionMap.put("username", usuario);
        }

        public Usuarios getLoggedUser() {
            return JsfUtil.getLoggedUser();
        }

        public String getIDUsuarioTemp() {
            return IDUsuarioTemp;
        }

        public void setIDUsuarioTemp(String IDUsuarioTemp) {
            this.IDUsuarioTemp = IDUsuarioTemp;
        }

        public String getPasswordTemp() {
            return passwordTemp;
        }

        public void setPasswordTemp(String passwordTemp) {
            this.passwordTemp = passwordTemp;
        }
        //</editor-fold>

        private MenuModel buildMenu() {
            return (getLoggedUser() == null)
                    ? new DefaultMenuModel()
                    : menuFactory.buildNormalMenu(JsfUtil.getMenuAccessMap().keySet());
        }

        public void setAccessMaps() {
            Usuarios usr = getLoggedUser();
            if (usr == null) {
                /* No se pueden construir los mapas de accesos si no hay usuario */
                setAccessMaps(null, null);
                return;
            }
            Map<String, Accesos> menuAccessMap = new HashMap<String, Accesos>(15);
            Map<String, Accesos> formAccessMap = new HashMap<String, Accesos>(15);
            NivelAcceso nivel = usr.getIDNivel();
            for (Accesos accesos : a.findByIDNivelWeb(nivel)) {
                if (!filterAccesos(accesos, nivel)) {
                    continue;
                }
                String idForm = accesos.getIDForm().getIDForm();
                if (accesos.getSubMenu()) {
                    menuAccessMap.put(idForm, accesos);
                    formAccessMap.put(idForm, accesos);
                } else {
                    formAccessMap.put(idForm, accesos);
                }
            }
            setAccessMaps(menuAccessMap, formAccessMap);
        }

        private void setAccessMaps(Map<String, Accesos> menuAccessMap, Map<String, Accesos> formAccessMap) {
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();
            sessionMap.put("menuAccessMap", menuAccessMap);
            sessionMap.put("formAccessMap", formAccessMap);
        }

        private boolean filterAccesos(Accesos accesos, NivelAcceso nivel) {
            Logger l = Logger.getLogger(CLASS_NAME);
            if (accesos == null) {
                l.logp(Level.WARNING, CLASS_NAME, "setAccessMaps",
                        "{0}: Accesso nulo en idNivel = {1}. "
                        + "Probablemente tiene un ID foraneo invalido en BD.",
                        new Object[] {CLASS_NAME, nivel.getIDNivel()});
                return false;
            }
            Formularios idForm = accesos.getIDForm();
            if (idForm == null) {
                l.logp(Level.WARNING, CLASS_NAME, "setAccessMaps",
                        "{0}: Formulario nulo en idAcceso {1}. "
                        + "Probablemente tiene un ID foraneo invalido en BD.",
                        new Object[] {CLASS_NAME, accesos.getIDAccesos()});
                return false;
            }
            Short tipo = idForm.getTipo();
            if (tipo == null) {
                l.logp(Level.WARNING, CLASS_NAME, "setAccessMaps",
                        "{0}: Tipo nulo en idForm {1}. "
                        + "Necesario que los Formularios para web sean tipo=2",
                        new Object[]{CLASS_NAME, idForm.getIDForm()});
                return false;
            }
            if (tipo != 2) {
                return false;
            } else {
                return true;
            }
        }
    }
}
