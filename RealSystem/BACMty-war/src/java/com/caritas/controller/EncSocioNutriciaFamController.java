package com.caritas.controller;

import com.caritas.entity.EncSocioNutriciaFam;
import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.entity.EncSocioNutriciaFamPK;
import com.caritas.entity.EncSocioNutriciaPK;
import com.caritas.facade.EncSocioNutriciaFamFacade;

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

@ManagedBean(name = "encSocioNutriciaFamController")
@ViewScoped
public class EncSocioNutriciaFamController implements Serializable {

    private EncSocioNutriciaFam current;
    private DataModel items = null;
    @EJB
    private com.caritas.facade.EncSocioNutriciaFamFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public EncSocioNutriciaFamController() {
    }

    public EncSocioNutriciaFam getSelected() {
        if (current == null) {
            current = new EncSocioNutriciaFam();
            //selectedItemIndex = -1;
        }
        return current;
    }

    private EncSocioNutriciaFamFacade getFacade() {
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
        current = (EncSocioNutriciaFam) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new EncSocioNutriciaFam();
        current.setEncSocioNutriciaFamPK(new EncSocioNutriciaFamPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getEncSocioNutriciaFamPK().setAreaGeo(current.getEncSocioNutricia().getEncSocioNutriciaPK().getAreaGeo());
            current.getEncSocioNutriciaFamPK().setExpediente(current.getEncSocioNutricia().getEncSocioNutriciaPK().getExpediente());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaFamCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (EncSocioNutriciaFam) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getEncSocioNutriciaFamPK().setAreaGeo(current.getEncSocioNutricia().getEncSocioNutriciaPK().getAreaGeo());
            current.getEncSocioNutriciaFamPK().setExpediente(current.getEncSocioNutricia().getEncSocioNutriciaPK().getExpediente());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaFamUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (EncSocioNutriciaFam) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaFamDeleted"));
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

    public EncSocioNutriciaFam getEncSocioNutriciaFam(EncSocioNutriciaFamPK id) {
        return ejbFacade.find(id);
    }

    void createFromEncSocNut(EncSocioNutriciaFam aux) {
        current = null;
        
        getSelected().setEncSocioNutriciaFamPK(aux.getEncSocioNutriciaFamPK());
        getSelected().setP1R(aux.getP1R());
        getSelected().setP2R(aux.getP2R());
        getSelected().setP3R(aux.getP3R());
        getSelected().setP4NumCuartos(aux.getP4NumCuartos());
        getSelected().setP5R(aux.isP5R());
        getSelected().setP6R(aux.getP6R());
        getSelected().setP7R(aux.getP7R());
        getSelected().setP8R(aux.getP8R());
        getSelected().setP9R(aux.getP9R());
        getSelected().setP10R(aux.getP10R());
        getSelected().setP11R(aux.getP11R());
        getSelected().setP12R(aux.getP12R());
        getSelected().setP13R(aux.getP13R());
        getSelected().setP14R(aux.isP14R());
        getSelected().setP15ComercioInformal(aux.getP15ComercioInformal());
        getSelected().setP16ServDomesticos(aux.getP16ServDomesticos());
        getSelected().setP17ApoyoFederal(aux.getP17ApoyoFederal());
        getSelected().setP18Otros(aux.getP18Otros());
        getSelected().setP19Alimentacion(aux.getP19Alimentacion());
        getSelected().setP20Luz(aux.getP20Luz());
        getSelected().setP21Agua(aux.getP21Agua());
        getSelected().setP22Gas(aux.getP22Gas());
        getSelected().setP23Otros(aux.getP23Otros());
        getSelected().setP24R(aux.isP24R());
        getSelected().setP25R(aux.getP25R());
        getSelected().setP26NoBasicos(aux.getP26NoBasicos());
        getSelected().setP27NoConsHumano(aux.getP27NoConsHumano());
        getSelected().setP28DestinoAgricola(aux.getP28DestinoAgricola());
        getSelected().setP29R(aux.isP29R());
        getSelected().setP30Ganado(aux.getP30Ganado());
        getSelected().setP31DestinoGanado(aux.getP31DestinoGanado());
        
        getFacade().create(current);
    }

    void updateFromEncSocNut(EncSocioNutriciaFam aux) {
        current = null;
        
        getSelected().setEncSocioNutriciaFamPK(aux.getEncSocioNutriciaFamPK());
        getSelected().setP1R(aux.getP1R());
        getSelected().setP2R(aux.getP2R());
        getSelected().setP3R(aux.getP3R());
        getSelected().setP4NumCuartos(aux.getP4NumCuartos());
        getSelected().setP5R(aux.isP5R());
        getSelected().setP6R(aux.getP6R());
        getSelected().setP7R(aux.getP7R());
        getSelected().setP8R(aux.getP8R());
        getSelected().setP9R(aux.getP9R());
        getSelected().setP9Otro(aux.getP9Otro());
        getSelected().setP10R(aux.getP10R());
        getSelected().setP11R(aux.getP11R());
        getSelected().setP12R(aux.getP12R());
        getSelected().setP12Otro(aux.getP12Otro());
        getSelected().setP13R(aux.getP13R());
        getSelected().setP14R(aux.isP14R());
        getSelected().setP15ComercioInformal(aux.getP15ComercioInformal());
        getSelected().setP16ServDomesticos(aux.getP16ServDomesticos());
        getSelected().setP17ApoyoFederal(aux.getP17ApoyoFederal());
        getSelected().setP18Otros(aux.getP18Otros());
        getSelected().setP18OtrosString(aux.getP18OtrosString());
        getSelected().setP19Alimentacion(aux.getP19Alimentacion());
        getSelected().setP20Luz(aux.getP20Luz());
        getSelected().setP21Agua(aux.getP21Agua());
        getSelected().setP22Gas(aux.getP22Gas());
        getSelected().setP23Otros(aux.getP23Otros());
        getSelected().setP24R(aux.isP24R());
        getSelected().setP25R(aux.getP25R());
        getSelected().setP26NoBasicos(aux.getP26NoBasicos());
        getSelected().setP27NoConsHumano(aux.getP27NoConsHumano());
        getSelected().setP28DestinoAgricola(aux.getP28DestinoAgricola());
        getSelected().setP29R(aux.isP29R());
        getSelected().setP30Ganado(aux.getP30Ganado());
        getSelected().setP30GanadoOtro(aux.getP30GanadoOtro());
        getSelected().setP31DestinoGanado(aux.getP31DestinoGanado());
        
        getFacade().edit(current);
    }

    void removeFromEncSocNut(EncSocioNutriciaFamPK id) {
        current = null;
        
        current = getEncSocioNutriciaFam(id);
        
        getFacade().remove(current);
    }

    @FacesConverter(forClass = EncSocioNutriciaFam.class)
    public static class EncSocioNutriciaFamControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EncSocioNutriciaFamController controller = (EncSocioNutriciaFamController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "encSocioNutriciaFamController");
            return controller.getEncSocioNutriciaFam(getKey(value));
        }

        com.caritas.entity.EncSocioNutriciaFamPK getKey(String value) {
            com.caritas.entity.EncSocioNutriciaFamPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.caritas.entity.EncSocioNutriciaFamPK();
            key.setExpediente(Integer.parseInt(values[0]));
            key.setAreaGeo(values[1].charAt(0));
            return key;
        }

        String getStringKey(com.caritas.entity.EncSocioNutriciaFamPK value) {
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
            if (object instanceof EncSocioNutriciaFam) {
                EncSocioNutriciaFam o = (EncSocioNutriciaFam) object;
                return getStringKey(o.getEncSocioNutriciaFamPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + EncSocioNutriciaFam.class.getName());
            }
        }
    }
}
