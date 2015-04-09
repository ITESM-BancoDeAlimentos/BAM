package com.caritas.controller;

import com.caritas.entity.TablaArticulos;
import com.caritas.entity.ReciboDet;
import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.entity.Articulos;
import com.caritas.entity.Recibo;
import com.caritas.facade.ReciboDetFacade;

import java.io.Serializable;
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

@ManagedBean(name = "reciboDetController")
@SessionScoped
public class ReciboDetController implements Serializable {

    private ReciboDet current;
    private DataModel items = null;
    @EJB
    private com.caritas.facade.ReciboDetFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ReciboDetController() {
    }

    public ReciboDet getSelected() {
        if (current == null) {
            current = new ReciboDet();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ReciboDetFacade getFacade() {
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
        current = (ReciboDet) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ReciboDet();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboDetCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void createFromRecibo(Articulos temp, TablaArticulos itemActual, Recibo reciboCurrent) {
        current = new ReciboDet();

        current.setIDFolio(reciboCurrent.getIDFolio());
        current.setIDArea(reciboCurrent.getIDArea());
        current.setIDPrograma(reciboCurrent.getIDPrograma());
        current.setTipoMov(reciboCurrent.getTipoMov());
        current.setIDArticulo(temp);
        current.setFechaCad(itemActual.getFechaCaducidad());
        current.setCantidad(itemActual.getCantidad());
        current.setPeso(temp.getPeso());
        current.setMerma(null);
        current.setCostoBenef(temp.getCostoBenef1());
        current.setCuotaRecup(temp.getCuotaRecup1());

        getFacade().create(current);
    }

//    public void editFromRecibo(Articulos temp, TablaArticulos itemActual, Recibo reciboCurrent, int indice) {
//        current = getFacade().findByIDFolio(reciboCurrent.getIDFolio()).get(indice);
//        
//        current.setIDReciboDet(reciboCurrent.getIDRecibo());
//        current.setIDFolio(reciboCurrent.getIDFolio());
//        current.setIDArea(reciboCurrent.getIDArea());
//        current.setIDPrograma(reciboCurrent.getIDPrograma());
//        current.setTipoMov(reciboCurrent.getTipoMov());
//        current.setIDArticulo(temp);
//        current.setFechaCad(itemActual.getFechaCaducidad());
//        current.setCantidad(itemActual.getCantidad());
//        current.setPeso(temp.getPeso());
//        current.setMerma(null);
//        current.setCostoBenef(temp.getCostoBenef1());
//        current.setCuotaRecup(temp.getCuotaRecup1());
//        
//        getFacade().edit(current);
//    }
    public String prepareEdit() {
        current = (ReciboDet) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboDetUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ReciboDet) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public void destroyFromRecibo(String idFolio) {
        List<ReciboDet> listToRemove = getFacade().findByIDFolio(idFolio);

        for (ReciboDet temp : listToRemove) {
            getFacade().remove(temp);
        }
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboDetDeleted"));
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

    public void recuperarLista(List<ReciboDet> listaRecuperacion) {
        int indiceError = -1;

        for (ReciboDet temp : listaRecuperacion) {
            try {
                indiceError = listaRecuperacion.indexOf(temp);
                getFacade().create(temp);
            } catch (Exception e) {
                // Se supone que no debe entrar aquí, el msj es para el Debug
                System.out.println("Fallo al agregar el item en el índice #" + indiceError);
            }
        }
    }

    @FacesConverter(forClass = ReciboDet.class)
    public static class ReciboDetControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReciboDetController controller = (ReciboDetController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "reciboDetController");
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
            if (object instanceof ReciboDet) {
                ReciboDet o = (ReciboDet) object;
                return getStringKey(o.getIDReciboDet());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ReciboDet.class.getName());
            }
        }
    }
}
