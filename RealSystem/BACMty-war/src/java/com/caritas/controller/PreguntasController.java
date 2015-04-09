/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.controller;

import com.caritas.controller.abstractController.CrudController;
import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.entity.Preguntas;
import com.caritas.facade.PreguntasFacade;
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
public class PreguntasController implements Serializable, CrudController {
    
    private Preguntas current;
    private DataModel items = null;
    @EJB
    private com.caritas.facade.PreguntasFacade ejbFacade;
    private PaginationHelper pagination;

    public PreguntasController() {
    }

    public Preguntas getSelected() {
        if (current == null) {
            current = new Preguntas();
        }
        return current;
    }
    
    public void setSelected(Preguntas selected) {
        current = selected;
    }

    private PreguntasFacade getFacade() {
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
        return "View";
    }
    
    @Override
    public String prepareEditNoRow() {
        return "Edit";
    }

    @Override
    public String prepareView() {
        current = (Preguntas) getItems().getRowData();
        return "View";
    }

    @Override
    public String prepareCreate() {
        current = new Preguntas();
        return "Create";
    }

    @Override
    public String create() {
        try {
            if(getFacade().findByIDPregunta(current).isEmpty()) {
                getFacade().create(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PreguntasCreated"));
                return prepareCreate();
            } else {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PreguntasError_DuplicatePK"));
                return prepareCreate();
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PreguntasError_DuplicatePK"));
            return null;
        }
    }

    @Override
    public String prepareEdit() {
        current = (Preguntas) getItems().getRowData();
        return "Edit";
    }

    @Override
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PreguntasUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    @Override
    public String destroy() {
        current = (Preguntas) getItems().getRowData();
        
        if(current.getRespuestasList().isEmpty()) {
            performDestroy();
            recreatePagination();
            recreateModel();
            return "List";
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PreguntasError_ChildRecords"));
            recreateModel();
            current = null;
            return null;
        }
    }

    /*public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }*/
    
    @Override
    public String destroyNoRow() {
        return performDestroy(current);
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PreguntasDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    private String performDestroy(Preguntas current) {
        if (current == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RequiredMessage_NullSelected_Key"));
            return null;
        }
        if (getFacade().find(current.getIDPregunta()) == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_RowNoLongerExists"));
            return "List";
        }
        try {
            getFacade().remove(current);
            setSelected(null);
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getLocalizedMessage());
            return null;
        }
    }

    /*private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }*/

    public DataModel getItems() {
        if (items == null) {
            /*items = getPagination().createPageDataModel();*/
            items = new ListDataModel(ejbFacade.findAll());
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

    @FacesConverter(forClass = Preguntas.class)
    public static class PreguntasControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) { 
               return null;
            }
            PreguntasController controller = (PreguntasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "preguntasController");
            return controller.ejbFacade.find(Integer.parseInt(value));
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
            if (object == null || ((Preguntas) object).getIDPregunta() == null) {
                return null;
            }
            if (object instanceof Preguntas) {
                Preguntas o = (Preguntas) object;
                return getStringKey(o.getIDPregunta().toString());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + PreguntasController.class.getName());
            }
        }
    }
}
