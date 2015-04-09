package com.caritas.controller;

import com.caritas.entity.EncSocioNutriciaSeg;
import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.entity.EncSocioNutricia;
import com.caritas.entity.EncSocioNutriciaSegPK;
import com.caritas.facade.EncSocioNutriciaSegFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "encSocioNutriciaSegController")
@ViewScoped
public class EncSocioNutriciaSegController implements Serializable {

    private EncSocioNutriciaSeg current;
    private DataModel items = null;
    @EJB
    private com.caritas.facade.EncSocioNutriciaSegFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public EncSocioNutriciaSegController() {
    }

    public EncSocioNutriciaSeg getSelected() {
        if (current == null) {
            current = new EncSocioNutriciaSeg();
            current.setEncSocioNutriciaSegPK(new com.caritas.entity.EncSocioNutriciaSegPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private EncSocioNutriciaSegFacade getFacade() {
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
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (EncSocioNutriciaSeg) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new EncSocioNutriciaSeg();
        current.setEncSocioNutriciaSegPK(new com.caritas.entity.EncSocioNutriciaSegPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getEncSocioNutriciaSegPK().setAreaGeo(current.getEncSocioNutricia().getEncSocioNutriciaPK().getAreaGeo());
            current.getEncSocioNutriciaSegPK().setExpediente(current.getEncSocioNutricia().getEncSocioNutriciaPK().getExpediente());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaSegCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (EncSocioNutriciaSeg) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getEncSocioNutriciaSegPK().setAreaGeo(current.getEncSocioNutricia().getEncSocioNutriciaPK().getAreaGeo());
            current.getEncSocioNutriciaSegPK().setExpediente(current.getEncSocioNutricia().getEncSocioNutriciaPK().getExpediente());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaSegUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (EncSocioNutriciaSeg) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaSegDeleted"));
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

    void createFromEncSegNut(EncSocioNutriciaSeg aux) {
        current = null;

        current = new EncSocioNutriciaSeg(aux.getEncSocioNutriciaSegPK());
//        getSelected().setEncSocioNutriciaSegPK(aux.getEncSocioNutriciaSegPK());
        getSelected().setPregunta01(aux.getPregunta01());
        getSelected().setPregunta02(aux.getPregunta02());
        getSelected().setPregunta03(aux.getPregunta03());
        getSelected().setPregunta04(aux.getPregunta04());
        getSelected().setPregunta05(aux.getPregunta05());
        getSelected().setPregunta06(aux.getPregunta06());
        getSelected().setPregunta07(aux.getPregunta07());
        getSelected().setPregunta08(aux.getPregunta08());
        getSelected().setPregunta09(aux.getPregunta09());
        getSelected().setPregunta10(aux.getPregunta10());
        getSelected().setPregunta11(aux.getPregunta11());
        getSelected().setPregunta12(aux.getPregunta12());
        getSelected().setPregunta13(aux.getPregunta13());
        getSelected().setPregunta14(aux.getPregunta14());
        getSelected().setPregunta15(aux.getPregunta15());
        getSelected().setPregunta16(aux.getPregunta16());
        getSelected().setPregunta16Mes(aux.getPregunta16Mes());

        getFacade().create(current);
    }

    void updateFromEncSegNut(EncSocioNutriciaSeg aux) {
        current = null;
        getSelected().setEncSocioNutriciaSegPK(aux.getEncSocioNutriciaSegPK());
        getSelected().setPregunta01(aux.getPregunta01());
        getSelected().setPregunta02(aux.getPregunta02());
        getSelected().setPregunta03(aux.getPregunta03());
        getSelected().setPregunta04(aux.getPregunta04());
        getSelected().setPregunta05(aux.getPregunta05());
        getSelected().setPregunta06(aux.getPregunta06());
        getSelected().setPregunta07(aux.getPregunta07());
        getSelected().setPregunta08(aux.getPregunta08());
        getSelected().setPregunta09(aux.getPregunta09());
        getSelected().setPregunta10(aux.getPregunta10());
        getSelected().setPregunta11(aux.getPregunta11());
        getSelected().setPregunta12(aux.getPregunta12());
        getSelected().setPregunta13(aux.getPregunta13());
        getSelected().setPregunta14(aux.getPregunta14());
        getSelected().setPregunta15(aux.getPregunta15());
        getSelected().setPregunta16(aux.getPregunta16());
        getSelected().setPregunta16Mes(aux.getPregunta16Mes());

        getFacade().edit(current);
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

    public EncSocioNutriciaSeg getEncSocioNutriciaSeg(com.caritas.entity.EncSocioNutriciaSegPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = EncSocioNutriciaSeg.class)
    public static class EncSocioNutriciaSegControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EncSocioNutriciaSegController controller = (EncSocioNutriciaSegController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "encSocioNutriciaSegController");
            return controller.getEncSocioNutriciaSeg(getKey(value));
        }

        com.caritas.entity.EncSocioNutriciaSegPK getKey(String value) {
            com.caritas.entity.EncSocioNutriciaSegPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.caritas.entity.EncSocioNutriciaSegPK();
            key.setExpediente(Integer.parseInt(values[0]));
            key.setAreaGeo(values[1].charAt(0));
            return key;
        }

        String getStringKey(com.caritas.entity.EncSocioNutriciaSegPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getExpediente());
            sb.append(SEPARATOR);
            sb.append(value.getAreaGeo());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof EncSocioNutriciaSeg) {
                EncSocioNutriciaSeg o = (EncSocioNutriciaSeg) object;
                return getStringKey(o.getEncSocioNutriciaSegPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + EncSocioNutriciaSeg.class.getName());
            }
        }
    }
}
