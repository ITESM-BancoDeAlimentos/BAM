package com.caritas.controller;

import com.caritas.entity.Accesos;
import com.caritas.controller.util.JsfUtil;
import com.caritas.facade.AccesosFacade;
import com.caritas.controller.UsuariosController.UsuariosAuth;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

@ManagedBean(name = "accesosController")
@ViewScoped
public class AccesosController implements Serializable {

    private Accesos current;
    @EJB
    private com.caritas.facade.AccesosFacade ejbFacade;
    private List<Accesos> currentList;
    private boolean nivelSelected = false;
    private boolean formaSelected = false;
    private boolean formasNull = false;
    private UsuariosAuth usuariosAuth;

    public AccesosController() {
    }

    public Accesos getSelected() {
        if (current == null) {
            current = new Accesos();
        }
        return current;
    }

    private AccesosFacade getFacade() {
        return ejbFacade;
    }

    public String prepareCreate() {
        return "Create";
    }

    public String prepareEdit() {
        return "Edit";
    }

    public void create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AccesosCreated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void GuardarAcceso() {
        try {
            if (getSelected().getIDForm() != null) {
                getFacade().create(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AccesosCreated"));

                // Si hicimos modificaciones sobre el nivel en que estamos, recreamos el mapa
                if (getSelected().getIDNivel() == getUsuariosAuth().getLoggedUser().getIDNivel()) {
                    getUsuariosAuth().setAccessMaps();
                }

                current = new Accesos(getSelected().getIDNivel());
                formaSelected = false;

                if (ejbFacade.findFormulariosWeb(getSelected().getIDNivel()).isEmpty()) {
                    formasNull = true;
                } else {
                    formasNull = false;
                }

            } else {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("AccesosRequiredMessage_Formulario"));
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void ActualizarAcceso() {
        try {
            for (Accesos actual : currentList) {
                getFacade().edit(actual);
            }

            // Si hicimos modificaciones sobre el nivel en que estamos, recreamos el mapa
            if (getSelected().getIDNivel().getIDNivel()
                    .equals(getUsuariosAuth().getLoggedUser().getIDNivel().getIDNivel())) {
                getUsuariosAuth().setAccessMaps();
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AccesosUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }

    }

    public void CambiaNivelEdit() {
        if (getSelected().getIDNivel() != null) {
            nivelSelected = true;
            currentList = getFacade().findByIDNivelWeb(getSelected().getIDNivel());
        } else {
            nivelSelected = false;
            currentList = null;
        }
    }

    public void CambiaNivelCreate() {
        if (getSelected().getIDNivel() != null) {
            nivelSelected = true;
            if (ejbFacade.findFormulariosWeb(getSelected().getIDNivel()).isEmpty()) {
                formasNull = true;
            } else {
                formasNull = false;
            }
        } else {
            nivelSelected = false;
            getSelected().setIDForm(null);
        }
    }

    public void CambiaFormulario() {
        if (getSelected().getIDForm() != null) {
            formaSelected = true;
        } else {
            formaSelected = false;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="SETTERS & GETTERS">
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getItemsAvailableSelectNiveles() {
        return JsfUtil.getSelectItems(ejbFacade.findNiveles(), false);
    }

    public SelectItem[] getItemsAvailableSelectFormulariosWeb() {
        return JsfUtil.getSelectItems(ejbFacade.findFormulariosWeb(getSelected().getIDNivel()), false);
    }

    public List<Accesos> getCurrentList() {
        return currentList;
    }

    public void setCurrentList(List<Accesos> currentList) {
        this.currentList = currentList;
    }

    public boolean isNivelSelected() {
        return nivelSelected;
    }

    public boolean isFormasNull() {
        return formasNull;
    }

    public boolean isFormaSelected() {
        return formaSelected;
    }

    public UsuariosController.UsuariosAuth getUsuariosAuth() {
        if (usuariosAuth == null) {
            usuariosAuth = new UsuariosController.UsuariosAuth();
        }
        return usuariosAuth;
    }

    public void setUsuariosAuth(UsuariosController.UsuariosAuth usuariosAuth) {
        this.usuariosAuth = usuariosAuth;
    }
    //</editor-fold>

    @FacesConverter(forClass = Accesos.class)
    public static class AccesosControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AccesosController controller = (AccesosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "accesosController");
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
            if (object instanceof Accesos) {
                Accesos o = (Accesos) object;
                return getStringKey(o.getIDAccesos());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + AccesosController.class.getName());
            }
        }
    }
}
