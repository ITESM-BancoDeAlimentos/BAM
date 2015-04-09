package com.caritas.controller;

import com.caritas.entity.TipoDon;
import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.facade.TipoDonFacade;

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

@ManagedBean(name = "tipoDonController")
@SessionScoped
public class TipoDonController implements Serializable {

    private TipoDon current;
    private DataModel items = null;
    @EJB
    private com.caritas.facade.TipoDonFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public TipoDonController() {
    }

    public TipoDon getSelected() {
        if (current == null) {
            current = new TipoDon();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TipoDonFacade getFacade() {
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

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (TipoDon) getItems().getRowData();

        checkIfRemoved();

        if (current == null) {
            return "View";
        } else {
            selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
            return "View";
        }
    }

    public String prepareViewNoRow() {
        current = null;
        return "View";
    }

    public String prepareCreate() {
        current = new TipoDon();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            if (ejbFacade.findEqual(current.getIDTipoDon()) > 0) {
                JsfUtil.addErrorMessage("El Tipo de Donante ya existe (coincidencia en IDTipoDon)");
                return null;
            } else {
                current.setIDTipoDon(current.getIDTipoDon().toUpperCase());
                current.setTipoDon(current.getTipoDon().toUpperCase());
                getFacade().create(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TipoDonCreated"));
                recreateModel();
                recreatePagination();
                return prepareCreate();
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TipoDon) getItems().getRowData();
        checkIfRemoved();

        if (current == null) {
            return "Edit";
        } else {
            selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
            return "Edit";
        }
    }

    public String prepareEditNoRow() {
        if (current.getIDTipoDon() != null && !current.getIDTipoDon().equals(" ")) {
            checkIfRemoved();

        if (current == null) {
            JsfUtil.addErrorMessage("Favor de seleccionar el IDTipoDon que desea Editar");
        }
        } else {
            JsfUtil.addErrorMessage("Favor de seleccionar el IDTipoDon que desea Editar");
        return "Edit";
    }
        return "Edit";
    }

    public String update() {
        try {
            if (current.getIDTipoDon() != null && !current.getIDTipoDon().equals(" ")) {

                checkIfRemoved();

                current.setIDTipoDon(current.getIDTipoDon().toUpperCase());
                current.setTipoDon(current.getTipoDon().toUpperCase());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TipoDonUpdated"));
                recreateModel();
                recreatePagination();
            return "View";
            } else {
                return prepareEditNoRow();
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (TipoDon) getItems().getRowData();
        selectedItemIndex = getPagination().getPageFirstItem() + getItems().getRowIndex();

        checkIfRemoved();

        if (current != null) {
            if (current.getDonantesCollection().isEmpty()) {
                performDestroy();
                recreateModel();
                recreatePagination();
                current = null;
                return "List";
            } else {
                JsfUtil.addErrorMessage("No se puede dar de baja ya que está asociado a, al menos, un Donante");
                recreateModel();
                current = null;
                return null;
            }
        } else {
            return prepareList();
        }
    }

    public String destroyNoRow() {
        if (current.getIDTipoDon() != null) {
            checkIfRemoved();

            if (current == null) {
                JsfUtil.addErrorMessage("Seleccione un dato a dar de Baja");
                return null;
            } else {
                if (current.getDonantesCollection().isEmpty()) {
                    performDestroy();
                    recreateModel();
                    recreatePagination();
                    current = null;
                    return "List";
                } else {
                    JsfUtil.addErrorMessage("No se puede dar de baja ya que está asociado a, al menos, un Donante");
                    recreateModel();
                    current = null;
                    return "";
                }
            }
        } else {
            JsfUtil.addErrorMessage("Seleccione un dato a dar de Baja");
            return "";
        }
    }

    public void view(String IDTipoDon) {
        checkIfRemoved();

        if (current != null) {
            if (current.getIDTipoDon() != null && !current.getIDTipoDon().equals(" ")) {
                current = ejbFacade.findByID(IDTipoDon);
            } 
        }
        //return "View";

        /*
         * if (current != null) { return "View"; } else { current = null; return
         * null; }
         */
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TipoDonDeleted"));
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

    public SelectItem[] getItemsAvailableSelectID() {
        return JsfUtil.getSelectItems(ejbFacade.findID(), true);
    }

    public SelectItem[] getItemsAvailableSelectIDNoNull() {
        return JsfUtil.getSelectItems(ejbFacade.findID(), false);
    }

    private void checkIfRemoved() {
        boolean isRemoved;

        isRemoved = ejbFacade.findRemoved(current.getIDTipoDon());

        if (isRemoved) {
            JsfUtil.addErrorMessage("El Tipo de Donante ya no existe.");
            current = null;
        }
    }

    @FacesConverter(forClass = TipoDon.class)
    public static class TipoDonControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoDonController controller = (TipoDonController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoDonController");
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
            if (object instanceof TipoDon) {
                TipoDon o = (TipoDon) object;
                return getStringKey(o.getIDTipoDon());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TipoDonController.class.getName());
            }
        }
    }
}
