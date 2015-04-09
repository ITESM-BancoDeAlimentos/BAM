package com.caritas.controller;

import com.caritas.entity.EncSocioNutriciaInd;
import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.entity.EncSocioNutricia;
import com.caritas.entity.EncSocioNutriciaIndPK;
import com.caritas.facade.EncSocioNutriciaIndFacade;

import java.io.Serializable;
import java.util.List;
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

@ManagedBean(name = "encSocioNutriciaIndController")
@ViewScoped
public class EncSocioNutriciaIndController implements Serializable {

    private EncSocioNutriciaInd current;
    private DataModel items = null;
    @EJB
    private com.caritas.facade.EncSocioNutriciaIndFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public EncSocioNutriciaIndController() {
    }

    public EncSocioNutriciaInd getSelected() {
        if (current == null) {
            current = new EncSocioNutriciaInd();
            current.setEncSocioNutriciaIndPK(new com.caritas.entity.EncSocioNutriciaIndPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private EncSocioNutriciaIndFacade getFacade() {
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
        current = (EncSocioNutriciaInd) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new EncSocioNutriciaInd();
        current.setEncSocioNutriciaIndPK(new com.caritas.entity.EncSocioNutriciaIndPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getEncSocioNutriciaIndPK().setExpediente(current.getEncSocioNutricia().getEncSocioNutriciaPK().getExpediente());
            current.getEncSocioNutriciaIndPK().setAreaGeo(current.getEncSocioNutricia().getEncSocioNutriciaPK().getAreaGeo());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (EncSocioNutriciaInd) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getEncSocioNutriciaIndPK().setExpediente(current.getEncSocioNutricia().getEncSocioNutriciaPK().getExpediente());
            current.getEncSocioNutriciaIndPK().setAreaGeo(current.getEncSocioNutricia().getEncSocioNutriciaPK().getAreaGeo());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (EncSocioNutriciaInd) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndDeleted"));
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

    public EncSocioNutriciaInd getEncSocioNutriciaInd(EncSocioNutriciaIndPK id) {
        return ejbFacade.find(id);
    }

    public List<EncSocioNutriciaInd> getListEncSocioNutriciaInd(int exp, char areaGeo) {
        return ejbFacade.findByIDEnc(exp, areaGeo);
    }

    void createFromEncSocNut(EncSocioNutriciaInd aux, EncSocioNutricia encSocNut) {
        current = null;

        getSelected().setEncSocioNutriciaIndPK(new EncSocioNutriciaIndPK(encSocNut.getEncSocioNutriciaPK().getExpediente(),
                encSocNut.getEncSocioNutriciaPK().getAreaGeo(), aux.getEncSocioNutriciaIndPK().getNombre()));
        getSelected().setParentesco(aux.getParentesco());
        getSelected().setCurp(aux.getCurp());
        getSelected().setFechaNacimiento(aux.getFechaNacimiento());
        getSelected().setGenero(aux.getGenero());
        getSelected().setPeso(aux.getPeso());
        getSelected().setTalla(aux.getTalla());
        getSelected().setEstadoCivil(aux.getEstadoCivil());
        getSelected().setEscolaridad(aux.getEscolaridad());
        getSelected().setCondLaboral(aux.getCondLaboral());
        getSelected().setRamaActiv(aux.getRamaActiv());
        getSelected().setSalario(aux.getSalario());
        getSelected().setSeguroSocial(aux.getSeguroSocial());
        getSelected().setProbSalud(aux.getProbSalud());
        getSelected().setAyudaAlim(aux.getAyudaAlim());
        getSelected().setEtapaEstFis(aux.getEtapaEstFis());
        getSelected().setSemEmbarazo(aux.getSemEmbarazo());
        getSelected().setPesoPreg(aux.getPesoPreg());
        getSelected().setGrupoEtnico(aux.getGrupoEtnico());

        getFacade().create(current);
    }
    
    

    void updateFromEncSocNut(EncSocioNutriciaInd aux, EncSocioNutricia encSocNut) {
        current = null;

        getSelected().setEncSocioNutriciaIndPK(new EncSocioNutriciaIndPK(encSocNut.getEncSocioNutriciaPK().getExpediente(),
                encSocNut.getEncSocioNutriciaPK().getAreaGeo(), aux.getEncSocioNutriciaIndPK().getNombre()));
        getSelected().setParentesco(aux.getParentesco());
        getSelected().setCurp(aux.getCurp());
        getSelected().setFechaNacimiento(aux.getFechaNacimiento());
        getSelected().setGenero(aux.getGenero());
        getSelected().setPeso(aux.getPeso());
        getSelected().setTalla(aux.getTalla());
        getSelected().setEstadoCivil(aux.getEstadoCivil());
        getSelected().setEscolaridad(aux.getEscolaridad());
        getSelected().setCondLaboral(aux.getCondLaboral());
        getSelected().setRamaActiv(aux.getRamaActiv());
        getSelected().setSalario(aux.getSalario());
        getSelected().setSeguroSocial(aux.getSeguroSocial());
        getSelected().setProbSalud(aux.getProbSalud());
        getSelected().setAyudaAlim(aux.getAyudaAlim());
        getSelected().setEtapaEstFis(aux.getEtapaEstFis());
        getSelected().setSemEmbarazo(aux.getSemEmbarazo());
        getSelected().setPesoPreg(aux.getPesoPreg());
        getSelected().setGrupoEtnico(aux.getGrupoEtnico());

        getFacade().edit(current);
    }

    void removeFromEncSocNut(int expediente, char areaGeo) {
        List<EncSocioNutriciaInd> aBorrarList = getFacade().findByIDEnc(expediente, areaGeo);

        if (aBorrarList != null) {
            for (EncSocioNutriciaInd aBorrar : aBorrarList) {
                getFacade().remove(aBorrar);
            }
        }
    }

    @FacesConverter(forClass = EncSocioNutriciaInd.class)
    public static class EncSocioNutriciaIndControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EncSocioNutriciaIndController controller = (EncSocioNutriciaIndController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "encSocioNutriciaIndController");
            return controller.getEncSocioNutriciaInd(getKey(value));
        }

        com.caritas.entity.EncSocioNutriciaIndPK getKey(String value) {
            com.caritas.entity.EncSocioNutriciaIndPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.caritas.entity.EncSocioNutriciaIndPK();
            key.setExpediente(Integer.parseInt(values[0]));
            key.setAreaGeo(values[1].charAt(0));
            key.setNombre(values[2]);
            return key;
        }

        String getStringKey(com.caritas.entity.EncSocioNutriciaIndPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getExpediente());
            sb.append(SEPARATOR);
            sb.append(value.getAreaGeo());
            sb.append(SEPARATOR);
            sb.append(value.getNombre());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof EncSocioNutriciaInd) {
                EncSocioNutriciaInd o = (EncSocioNutriciaInd) object;
                return getStringKey(o.getEncSocioNutriciaIndPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + EncSocioNutriciaInd.class.getName());
            }
        }
    }
}
