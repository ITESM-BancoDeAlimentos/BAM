/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.controller;

import com.caritas.controller.abstractController.CrudController;
import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.entity.Respuestas;
import com.caritas.entity.RespuestasPK;
import com.caritas.facade.RespuestasFacade;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 *
 * @author software
 */
@ManagedBean
@SessionScoped
public class RespuestasController implements Serializable, CrudController {

    private Respuestas current;
    private DataModel items = null;
    //private List<Respuestas> items = null;
    @EJB
    private com.caritas.facade.RespuestasFacade ejbFacade;
    private PaginationHelper pagination;

    public RespuestasController() {
    }

    public Respuestas getSelected() {
        if (current == null) {
            setSelected(new Respuestas());
        }
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setSelected(Respuestas current) {
        this.current = current;
    }

    private RespuestasFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(getFacade().count()) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    @Override
    public String prepareList() {
        recreateModel();
        return "List";
    }
    
    @Override
    public String prepareViewNoRow() {
        setSelected(new Respuestas());
        return "View";
    }
    
    @Override
    public String prepareEditNoRow() {
        setSelected(new Respuestas());
        return "Edit";
    }

    @Override
    public String prepareView() {
        setSelected((Respuestas) getItems().getRowData());
        return "View";
    }

    @Override
    public String prepareCreate() {
        setSelected(new Respuestas());
        return "Create";
    }

    @Override
    public String create() {
        try {
            current.getRespuestasPK().setIDPregunta(current.getPreguntas().getIDPregunta());
            getFacade().create(current);
            recreatePagination();
            recreateModel();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RespuestasCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            recreatePagination();
            recreateModel();
            return null;
        }
    }

    @Override
    public String prepareEdit() {
        setSelected((Respuestas) getItems().getRowData());
        return "Edit";
    }

    @Override
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RespuestasUpdated"));
            recreatePagination();
            recreateModel();
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            recreatePagination();
            recreateModel();
            return null;
        }
    }

    @Override
    public String destroy() {
        setSelected((Respuestas) getItems().getRowData());
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    @Override
    public String destroyNoRow() {
        return performDestroy(current);
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RespuestasDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    private String performDestroy(Respuestas selected) {
        try {
            getFacade().remove(selected);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RespuestasDeleted"));
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return "Destroy";
        }
    }
    
    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    
    public SelectItem[] getItemsAvailableSelectOneByPreguntaID() {
        return JsfUtil.getSelectItems(ejbFacade.findByIDPregunta(current.getPreguntas()), true);
    }

    @FacesConverter(forClass = Respuestas.class)
    public static class RespuestasControllerConverter implements Converter {
        
        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) { 
               return null;
            }
            RespuestasController controller = (RespuestasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "respuestasController");
            return controller.ejbFacade.find(getKey(value));
        }

        RespuestasPK getKey(String value) {
            RespuestasPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new RespuestasPK();
            key.setIDPregunta(Integer.parseInt(values[0]));
            key.setIDRespuesta(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(RespuestasPK value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value.getIDPregunta());
            sb.append(SEPARATOR);
            sb.append(value.getIDRespuesta());
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Respuestas) {
                Respuestas o = (Respuestas) object;
                return getStringKey(o.getRespuestasPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + RespuestasController.class.getName());
            }
        }
    }
}
