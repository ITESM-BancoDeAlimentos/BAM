package com.caritas.controller;

import com.caritas.entity.Accesos;
import java.io.Serializable;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author software
 */
@ManagedBean
@ApplicationScoped
public class AuthorizationController implements Serializable {

    public static Map<String, Accesos> getMenuAccessMap() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = ec.getSessionMap();
        return (Map<String, Accesos>) sessionMap.get("menuAccessMap");
    }

    public static Map<String, Accesos> getFormAccessMap() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = ec.getSessionMap();
        return (Map<String, Accesos>) sessionMap.get("formAccessMap");
    }
    private Accesos nullAccesos;

    public AuthorizationController() {
        nullAccesos = new Accesos();
        nullAccesos.setBorrar(false);
        nullAccesos.setCancelar(false);
        nullAccesos.setEditar(false);
        nullAccesos.setGrabar(false);
        nullAccesos.setImprimir(false);
        nullAccesos.setNuevo(false);
        nullAccesos.setSubMenu(false);
    }

    public Accesos getAccesos() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return getAccesosFor(viewId);
    }

    public Accesos getAccesosFor(String viewId) {
        ResourceBundle view2form;
        Map<String, Accesos> frmAccessMap;
        String frmId;
        Accesos returnVal;

        try {
            view2form = ResourceBundle.getBundle("/view2frm");
            frmId = view2form.getString(viewId);
            frmAccessMap = getFormAccessMap();
            Accesos pageAccesos = frmAccessMap.get(frmId);
            returnVal = (pageAccesos == null) ? nullAccesos : pageAccesos;
        } catch (MissingResourceException e) {
            returnVal = nullAccesos;
        }
        return returnVal;
    }
}
