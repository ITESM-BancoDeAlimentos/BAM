package com.caritas.controller.util;

import com.caritas.entity.Accesos;
import com.caritas.entity.Usuarios;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class JsfUtil {

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", ResourceBundle.getBundle("/Bundle").getString("SelectOne"));
            items[0].setNoSelectionOption(true);
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    /**
     * Muestra <code>getLocalizedMessage()</code> como mensaje, o sino muestra
     * un error generico.
     * @param ex Excepcion recibida
     */
    public static void addErrorMessage(Throwable ex) {
        addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("Error_Generico"));
    }

    /**
     * Muestra al usuario el <code>getLocalizedMessage()</code> del throwable, o
     * si no muestra <code>defaultString</code> como mensaje.
     * @param ex
     * @param defaultMsg 
     */
    public static void addErrorMessage(Throwable ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public static MethodExpression createMethodExpression(String expression,
            Class<?> returnType,
            Class<?>... parameterTypes) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        return facesContext
                .getApplication()
                .getExpressionFactory()
                .createMethodExpression(elContext, expression, returnType, parameterTypes);
    }

    /**
     * Manda a descargar al cliente el contenido de baos como un archivo. <br>
     * Este asume que sera un 'application/pdf', por lo que filename debe llevar
     * '.pdf' al final.
     *
     * @param filename String con el nombre del archivo incluyendo extension.
     * @param baos Outputstream con el contenido del archivo a mandar.
     */
    public static void downloadFile(String filename, ByteArrayOutputStream baos) {
        downloadFile(filename, baos, "application/pdf");
    }

    /**
     * Manda a descargar al cliente el contenido de baos como un archivo.
     *
     * @param filename String con el nombre del archivo incluyendo extension.
     * @param baos Outputstream con el contenido del archivo a mandar.
     * @param contentType String con el contenido de la cabecera Content-Type.
     */
    public static void downloadFile(String filename, ByteArrayOutputStream baos, String contentType) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response;
        try {
            ServletOutputStream browserStream;
            response = (HttpServletResponse) externalContext.getResponse();
            response.reset();
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.setHeader("Cache-Control", "no-cache");
            response.setContentLength(baos.size());
            browserStream = response.getOutputStream();
            baos.writeTo(browserStream);
            browserStream.flush();
            facesContext.responseComplete();
        } catch (Exception ex) {
            Logger.getLogger(JsfUtil.class.getName()).log(Level.SEVERE, "downloadFile", ex);
            JsfUtil.addErrorMessage("No fue posible descargar el archivo");
        }
    }

    /**
     * El usuario autenticado en esta session. Esta guardado bajo
     * <code>"username"</code> en el mapa de session.
     *
     * @return El usuario o <code>null</code> si no se ha loggeado ninguno en la
     * session.
     */
    public static Usuarios getLoggedUser() {
        FacesContext ci = FacesContext.getCurrentInstance();
        ExternalContext ec = ci.getExternalContext();
        Map<String, Object> session = ec.getSessionMap();
        Usuarios loggedUser = (Usuarios) session.get("username");
        return loggedUser;
    }

    /**
     * El mapa con los accesos del usuario autenticado. De:
     * <code>LoggedUser.getIDNivel.getAccesosCollection</code> Solo los que
     * tienen
     * <code>Submenu == true;</code>
     *
     * @return Mapa de accesos o <code>null</code> si no se ha loggeado ninguno
     * en la session.
     */
    public static Map<String, Accesos> getMenuAccessMap() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        Map<String, Object> sessionMap = ec.getSessionMap();
        return (Map<String, Accesos>) sessionMap.get("menuAccessMap");
    }

    /**
     * El mapa con los accesos a pantallas del usuario autenticado.
     *
     * @return Mapa o <code>null</code> si no se ha loggeado ninguno en la
     * session.
     */
    public static Map<String, Accesos> getFormAccessMap() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        Map<String, Object> sessionMap = ec.getSessionMap();
        return (Map<String, Accesos>) sessionMap.get("formAccessMap");
    }

    public static Date firstInMonth(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        return cal.getTime();
    }

    public static Date lastInMonth(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        return cal.getTime();
    }

    public static Date firstInWeek(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        return cal.getTime();
    }

    public static Date lastInWeek(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        return cal.getTime();
    }

    public static Date startDate(Date initialDate) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(initialDate);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        return cal.getTime();
    }

    public static Date endDate(Date finalDate) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(finalDate);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        return cal.getTime();
    }

    public static Map<String, Object> getSessionMap() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        return ec.getSessionMap();
    }
}