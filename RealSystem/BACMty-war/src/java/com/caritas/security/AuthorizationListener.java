package com.caritas.security;

import com.caritas.controller.util.JsfUtil;
import com.caritas.entity.Accesos;
import com.caritas.entity.Usuarios;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class AuthorizationListener implements PhaseListener {

    //<editor-fold defaultstate="collapsed" desc="staticVars">
    public static final String CLASS_NAME = AuthorizationListener.class.getName();
    private static final Logger LOG = Logger.getLogger(CLASS_NAME);
    //<editor-fold defaultstate="collapsed" desc="sources">
    private static final ResourceBundle view2form = ResourceBundle.getBundle("/view2frm");
    private static final ResourceBundle msg = ResourceBundle.getBundle("/Bundle");
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="viewIds">
    private static final String LOGIN_REDIRECT = "/login.xhtml?faces-redirect=true";
    private static final String INDEX_REDIRECT = "/index.xhtml?faces-redirect=true";
    private static final String INDEX_XHTML = "/index.xhtml";
    private static final String LOGIN_XHTML = "/login.xhtml";
    private static final Set<String> globalAllowedIds = new HashSet<String>(
            Arrays.asList(new String[]{"/index.xhtml", "/login.xhtml"}));
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="msgs">
    public static final String ERROR_NO_AUTHORIZED = msg.getString("ErrorNotAuthorized");
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Sessionkeys">
    public static final String LOGGED_USER_KEY = "username";
    public static final String MENU_ACCESS_MAP = "menuAccessMap";
    public static final String FORM_ACCESS_MAP = "formAccessMap";
    //</editor-fold>
    //</editor-fold>

    private static boolean isAuthorized(FacesContext context) {
        String viewId = context.getViewRoot().getViewId();
        if (globalAllowedIds.contains(viewId)) {
            return true;
        }

        Map<String, Accesos> frmAccessMap = getFormAccessMap(context.getExternalContext());
        if (frmAccessMap == null) {
            LOG.log(Level.SEVERE,
                    "{0}: No hay frmAccessMapDefinido en el mapa de session."
                    + " La function isAuthorized() solo debe ser ejecutada "
                    + "cuando no hay usuario en sesion."
                    + " Si reentrar a sesion no funciona, "
                    + "revisar el codigo de inicio se sesion.", CLASS_NAME);
            context.getExternalContext().invalidateSession();
            return false;
        }

        if (!view2form.containsKey(viewId)) {
            LOG.log(Level.INFO,
                    "{0}: No se encontró FormID correspondiente para {1}. Revisar bundle.",
                    new Object[]{CLASS_NAME, viewId});
            return false;
        }

        String frmId = view2form.getString(viewId);
        if (!frmAccessMap.containsKey(frmId)) {
            LOG.log(Level.INFO,
                    "{0}: No se encontro el FormID {1}. En los accesos del usuario."
                    + " Accesos: {2}", new Object[]{CLASS_NAME, frmId,
                Arrays.toString(frmAccessMap.keySet().toArray())
            });
            return false;
        }
        return true;
    }
    //<editor-fold defaultstate="collapsed" desc="overrides">

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext fc = event.getFacesContext();
        ExternalContext ec = fc.getExternalContext();
        NavigationHandler nh = fc.getApplication().getNavigationHandler();
        String currentPage = fc.getViewRoot().getViewId();
        boolean isLoginPage = (LOGIN_XHTML.equals(currentPage));
        boolean isUserLogged = (getCurrentUser(ec) == null);
        NavCase navCase = NavCase.calcCase(isLoginPage, !isUserLogged);

        switch (navCase) {
            case NoLoggedNoLogin:
                LOG.log(Level.INFO,
                        "{0}: Usuario no loggeado intentó entrar directo a {1}."
                        + " Redirigido a {2}.",
                        new Object[]{CLASS_NAME, currentPage, LOGIN_REDIRECT});
                nh.handleNavigation(fc, null, LOGIN_REDIRECT);
                break;

            case LoggedInLogin:
                LOG.log(Level.INFO,
                        "{0}: Usuario loggeado intentó entrar directo a login ({1})"
                        + " Redirigido a {2}.",
                        new Object[]{CLASS_NAME, currentPage, LOGIN_REDIRECT});
                nh.handleNavigation(fc, null, INDEX_REDIRECT);
                break;

            case LoggedNoLogin:
                if (!isAuthorized(fc)) {
                    LOG.log(Level.INFO,
                            "{0}: Usuario loggeado se le rehusó entrada a {1}"
                            + " Redirigido a {2}.",
                            new Object[]{CLASS_NAME, currentPage, INDEX_XHTML});
                    JsfUtil.addErrorMessage(ERROR_NO_AUTHORIZED);
                    nh.handleNavigation(fc, null, INDEX_XHTML);
                }
                break;
            case NoLoggedInLogin:
                break;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GetSet">

    private Usuarios getCurrentUser(ExternalContext ec) {
        Map<String, Object> sm = ec.getSessionMap();
        return (sm == null) ? null : (Usuarios) sm.get(LOGGED_USER_KEY);
    }

    public static Map<String, Accesos> getMenuAccessMap(ExternalContext ec) {
        Map<String, Object> sm = ec.getSessionMap();
        return (sm == null) ? null : (Map<String, Accesos>) sm.get(MENU_ACCESS_MAP);
    }

    public static Map<String, Accesos> getFormAccessMap(ExternalContext ec) {
        Map<String, Object> sm = ec.getSessionMap();
        return (sm == null) ? null : (Map<String, Accesos>) sm.get(FORM_ACCESS_MAP);
    }
    //</editor-fold>
}

//<editor-fold defaultstate="collapsed" desc="NavCases">
enum NavCase {

    NoLoggedNoLogin, LoggedInLogin, LoggedNoLogin, NoLoggedInLogin;

    static NavCase calcCase(boolean loginPage, boolean loggedUser) {
        if (!loginPage && loggedUser) {
            return NavCase.LoggedNoLogin;
        } else if (loginPage && !loggedUser) {
            return NavCase.NoLoggedInLogin;
        } else if (!loginPage && !loggedUser) {
            return NavCase.NoLoggedNoLogin;
        } else if (loginPage && loggedUser) {
            return NavCase.LoggedInLogin;
        } else {
            return NavCase.NoLoggedNoLogin;
        }
    }
}
    //</editor-fold>
