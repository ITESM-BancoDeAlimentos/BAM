package com.caritas.controller;

import com.caritas.entity.RelacionRepVar;
import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.entity.Variedad;
import com.caritas.facade.RelacionRepVarFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

@ManagedBean(name = "relacionRepVarController")
@SessionScoped
public class RelacionRepVarController implements Serializable {

    private RelacionRepVar current;
    private DataModel items = null;
    @EJB
    private com.caritas.facade.RelacionRepVarFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<RelacionRepVar> listaCurrents;

    public RelacionRepVarController() {
        current = new RelacionRepVar();
        current.setVariedad(new Variedad());
    }

    public RelacionRepVar getSelected() {
        if (current == null) {
            current = new RelacionRepVar();
            selectedItemIndex = -1;
        }
        return current;
    }

    private RelacionRepVarFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
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

    public String prepareList() {
        //recreateModel();
        current = new RelacionRepVar();
        return "List";
    }

    public String prepareView() {
        current = (RelacionRepVar) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }
    
    public String prepareView(RelacionRepVar item) {
        current = item;
//        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareViewNoRow() {
        current = new RelacionRepVar();
        return "View";
    }

    public String prepareCreate() {
        current = new RelacionRepVar();
        current.setVariedad(new Variedad());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RelacionRepVarCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (RelacionRepVar) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }
    
    public String prepareEdit(RelacionRepVar item) {
        current = item;
//        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RelacionRepVarUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (RelacionRepVar) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }
    
    public String destroy(RelacionRepVar item) {
        current = item;
        performDestroy();
        recreatePagination();
        recreateModel();
        current = new RelacionRepVar();
        return "List";
    }

    public String destroyAndView() {
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
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RelacionRepVarDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
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
    }

    public DataModel getItems() {
        if (items == null) {
            //items = getPagination().createPageDataModel();
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

    public void setVariedad() {
        if (!current.getVariedad().getVariedad().equals("")) {
            Variedad var = ejbFacade.findVariedad(current.getVariedad().getVariedad());

            current.setVariedad(var);
        } else {
            current.setVariedad(new Variedad());
        }
    }

    public String agregaFila() {
        if (current.getVariedad().getIDVariedad() == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RelacionRepVarRequiredMessage_variedad"));
            return null;
        } else {
            try {
                getFacade().create(current);
                getListaCurrents().add(current);
                
                current = new RelacionRepVar(null, current.getReporte(), current.getClase());
                current.setVariedad(new Variedad());
                
                return "Create";
            } catch (Exception e) {
                JsfUtil.addErrorMessage(e, "Error");
                return null;
            }
        }
    }

    public String eliminarFila(RelacionRepVar item) {
        try {
            getFacade().remove(item);
            listaCurrents.remove(item);
            return "Create";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error");
            return null;
        }
    }

    public List<RelacionRepVar> getListaCurrents() {
        if (listaCurrents == null) {
            listaCurrents = new ArrayList<RelacionRepVar>();
        }
        return listaCurrents;
    }
    
    public void limpiaTabla() {
        listaCurrents = null;
    }

    @FacesConverter(forClass = RelacionRepVar.class)
    public static class RelacionRepVarControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RelacionRepVarController controller = (RelacionRepVarController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "relacionRepVarController");
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
            if (object instanceof RelacionRepVar) {
                RelacionRepVar o = (RelacionRepVar) object;
                return getStringKey(o.getIDRelRepVar());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + RelacionRepVar.class.getName());
            }
        }
    }
}
