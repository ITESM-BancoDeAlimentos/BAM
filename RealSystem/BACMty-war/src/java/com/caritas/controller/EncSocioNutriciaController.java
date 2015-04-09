package com.caritas.controller;

import com.caritas.controller.util.EncSocioNutriciaUtil;
import com.caritas.entity.EncSocioNutricia;
import com.caritas.controller.util.JsfUtil;
import com.caritas.entity.EncSocioNutriciaFam;
import com.caritas.entity.EncSocioNutriciaFamPK;
import com.caritas.entity.EncSocioNutriciaInd;
import com.caritas.entity.EncSocioNutriciaIndPK;
import com.caritas.entity.EncSocioNutriciaPK;
import com.caritas.entity.EncSocioNutriciaSeg;
import com.caritas.entity.EncSocioNutriciaSegPK;
import com.caritas.entity.Instituciones;
import com.caritas.facade.EncSocioNutriciaFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "encSocioNutriciaController")
@ViewScoped
public class EncSocioNutriciaController implements Serializable {

    private EncSocioNutricia current;
    private List<EncSocioNutricia> currentList;
    @EJB
    private com.caritas.facade.EncSocioNutriciaFacade ejbFacade;
    private List<EncSocioNutriciaInd> integrantes;
    private EncSocioNutriciaInd integranteDialog;
    private EncSocioNutriciaFam pregRespFamiliares;
    private EncSocioNutriciaSeg seguridadDialog;
    private double totalEgresos;
    private EncSocioNutriciaInd integranteAux;
    private boolean embarazo;
    private boolean otroParentesco = false;
    private String otroParentescoString;
    private boolean otraRamaAct = false;
    private String otraRamaActString;
    private boolean otroSeguroSocial = false;
    private String otroSeguroSocialString;
    private boolean otraAyudaAlimentaria = false;
    private String otraAyudaAlimentariaString;
    private boolean otroGrupoEtnico = false;
    private String otroGrupoEtnicoString;
    private boolean otroPreg9 = false;
    private boolean otroPreg12 = false;
    private boolean otroPreg30 = false;
    private boolean Preg16Mes = false;
    @ManagedProperty(value = "#{encSocioNutriciaFamController}")
    private EncSocioNutriciaFamController famController;
    @ManagedProperty(value = "#{encSocioNutriciaIndController}")
    private EncSocioNutriciaIndController indController;
    @ManagedProperty(value = "#{encSocioNutriciaSegController}")
    private EncSocioNutriciaSegController segController;
    private boolean idSelected = false;
    private boolean areaGeoSelected = false;

    public EncSocioNutriciaController() {
    }

    @PostConstruct
    private void inicio() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String path = request.getRequestURI();

        Map<String, Object> params = JsfUtil.getSessionMap(); //FacesContext.getCurrentInstance()
//                .getExternalContext().getSessionMap();

        if (path.endsWith("View.xhtml") || path.endsWith("Edit.xhtml")) {

            if (params.get("encuesta") != null) {
                int exp = ((EncSocioNutriciaPK) params.get("encuesta")).getExpediente();
                char area = ((EncSocioNutriciaPK) params.get("encuesta")).getAreaGeo();

                current = new EncSocioNutricia(exp, area);
                viewSelectId(true);
                areaGeoSelected = true;
            }
        }
    }

    public EncSocioNutricia getSelected() {
        if (current == null) {
            current = new EncSocioNutricia();
            current.setEncSocioNutriciaPK(new EncSocioNutriciaPK());
            current.setIDInstitucion(new Instituciones());
        }

        return current;
    }

    public List<EncSocioNutricia> getCurrentList() {
        if (currentList == null) {
            currentList = getFacade().findAll();
        }
        return currentList;
    }

    private EncSocioNutriciaFacade getFacade() {
        return ejbFacade;
    }

    public String prepareList() {
        return "List?faces-redirect=true";
    }

    public String prepareView() {
        Map<String, Object> map = JsfUtil.getSessionMap();
        map.put("encuesta", getSelected().getEncSocioNutriciaPK());

        return "View?faces-redirect=true";
    }
    
    public String prepareViewFromListRow(EncSocioNutricia encuesta) {
        Map<String, Object> map = JsfUtil.getSessionMap();
        map.put("encuesta", encuesta.getEncSocioNutriciaPK());

        return "View?faces-redirect=true";
    }

    public String prepareCreate() {
        return "Create?faces-redirect=true";
    }

    public String prepareEdit() {
        Map<String, Object> map = JsfUtil.getSessionMap();
        map.put("encuesta", getSelected().getEncSocioNutriciaPK());

        return "Edit?faces-redirect=true";
    }

    public String prepareEditFromListRow(EncSocioNutricia encuesta) {
        Map<String, Object> map = JsfUtil.getSessionMap();
        map.put("encuesta", encuesta.getEncSocioNutriciaPK());

        return "Edit?faces-redirect=true";
    }

    public String prepareReactivar() {
        return "ReactivarEncuesta?faces-redirect=true";
    }

    public void destroy() {
        if (getSelected().getEncSocioNutriciaPK() != null) {
            current = getEncSocioNutricia(new EncSocioNutriciaPK(
                    current.getEncSocioNutriciaPK().getExpediente(), current.getEncSocioNutriciaPK().getAreaGeo()), true);
            if (current != null) {
                deactivateEnc();
            }
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RequiredMessage_NullSelected_Key"));
        }
    }
    
//    public String destroyFromListRow(EncSocioNutricia encuesta) {
//        current = encuesta;
//        
//        destroy();
//        
//        return "List?faces-redirect=true";
//    }

    private void deactivateEnc() {
        current.setStatus(false);
        ejbFacade.edit(current);
    }

    public String deactivateFromListRow(EncSocioNutricia encuesta) {
        try {
            current = encuesta;

            deactivateEnc();

            return "List?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_Generico"));
            return null;
        }
    }

    public void reactivarEncuesta() {
        current.setStatus(true);
        ejbFacade.edit(current);
        limpiaPantalla();
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getItemsAvailableSelectInstituciones() {
        return JsfUtil.getSelectItems(ejbFacade.findInstituciones(), true);
    }

    public SelectItem[] getItemsInstitutionByAreaSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findInstitucionByAreaGeo(current.getEncSocioNutriciaPK().getAreaGeo()), true);//
    }

    public EncSocioNutricia getEncSocioNutricia(EncSocioNutriciaPK id, boolean status) {
        return ejbFacade.find(id, status);
    }

    public List<Integer> completeExpediente(String query) {
        return getFacade().findLike(query, getSelected().getEncSocioNutriciaPK().getAreaGeo(), true);
    }

    public List<Integer> completeExpedienteDesactivado(String query) {
        return getFacade().findLike(query, getSelected().getEncSocioNutriciaPK().getAreaGeo(), false);
    }

    public void viewSelectId(boolean status) {
        char areaGeoTemp = getSelected().getEncSocioNutriciaPK().getAreaGeo();
        current = getEncSocioNutricia(current.getEncSocioNutriciaPK(), status);
        if (current != null) {
            if (current.getStatus() == status) {
                idSelected = true;
                pregRespFamiliares = famController.getEncSocioNutriciaFam(
                        new EncSocioNutriciaFamPK(
                        current.getEncSocioNutriciaPK().getExpediente(), current.getEncSocioNutriciaPK().getAreaGeo()));

                integrantes = indController.getListEncSocioNutriciaInd(
                        current.getEncSocioNutriciaPK().getExpediente(), current.getEncSocioNutriciaPK().getAreaGeo());

                seguridadDialog = segController.getEncSocioNutriciaSeg(
                        new EncSocioNutriciaSegPK(current.getEncSocioNutriciaPK().getExpediente(), current.getEncSocioNutriciaPK().getAreaGeo()));

            } else {
                if (areaGeoSelected) {
                    limpiaPantalla();
                    getSelected().setEncSocioNutriciaPK(new EncSocioNutriciaPK(0, areaGeoTemp));
                    areaGeoSelected = true;
                } else {
                    limpiaPantalla();
                }
            }
        } else {
            if (areaGeoSelected) {
                limpiaPantalla();
                getSelected().setEncSocioNutriciaPK(new EncSocioNutriciaPK(0, areaGeoTemp));
                areaGeoSelected = true;
            } else {
                limpiaPantalla();
            }
        }
    }

    private void arreglaIntegranteOtro() {
        if (!integranteDialog.getParentesco().isEmpty()) {
            if (integranteDialog.getParentesco().startsWith("!")) {
                otroParentesco = true;
                otroParentescoString = integranteDialog.getParentesco().substring(1);
                integranteDialog.setParentesco("Otro (Especifique)");
            }
        }

        if (!integranteDialog.getRamaActiv().isEmpty()) {
            if (integranteDialog.getRamaActiv().startsWith("!")) {
                otraRamaAct = true;
                otraRamaActString = integranteDialog.getRamaActiv().substring(1);
                integranteDialog.setRamaActiv("Otros (especifique)");
            }
        }

        if (!integranteDialog.getSeguroSocial().isEmpty()) {
            if (integranteDialog.getSeguroSocial().startsWith("!")) {
                otroSeguroSocial = true;
                otroSeguroSocialString = integranteDialog.getSeguroSocial().substring(1);
                integranteDialog.setSeguroSocial("Otros (especifique)");
            }
        }

        if (!integranteDialog.getAyudaAlim().isEmpty()) {
            if (integranteDialog.getAyudaAlim().startsWith("!")) {
                otraAyudaAlimentaria = true;
                otraAyudaAlimentariaString = integranteDialog.getAyudaAlim().substring(1);
                integranteDialog.setAyudaAlim("Otros (especifique)");
            }
        }

        if (!integranteDialog.getGrupoEtnico().isEmpty()) {
            if (integranteDialog.getGrupoEtnico().startsWith("!")) {
                otroGrupoEtnico = true;
                otroGrupoEtnicoString = integranteDialog.getGrupoEtnico().substring(1);
                integranteDialog.setGrupoEtnico("Si");
            }
        }
    }

    private void limpiaPantalla() {
        limpiaOtrosInt();
        limpiaOtrosFam();

        idSelected = false;
        areaGeoSelected = false;
        totalEgresos = 0.0;
        seguridadDialog = null;

        // :v lol
        current = null;
        integrantes = null;
        integranteAux = null;
        integranteDialog = null;
        pregRespFamiliares = null;
    }

    private void limpiaOtrosInt() {
        otroParentesco = false;
        otroParentescoString = "";
        otraRamaAct = false;
        otraRamaActString = "";
        otroSeguroSocial = false;
        otroSeguroSocialString = "";
        otraAyudaAlimentaria = false;
        otraAyudaAlimentariaString = "";
        otroGrupoEtnico = false;
        otroGrupoEtnicoString = "";
    }

    private void limpiaOtrosFam() {
        otroPreg9 = false;
        otroPreg12 = false;
        otroPreg30 = false;
    }

    public boolean createEnc() {
        try {
            checkID();

            current.setFechaCaptura(Hoy());
            current.setStatus(true);
            current.setIdentificador(JsfUtil.getLoggedUser());
            getFacade().create(current);
            return true;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return false;
        }
    }

    public boolean createEncFam() {
        try {
            getFamController().createFromEncSocNut(pregRespFamiliares);

            return true;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return false;
        }
    }

    public boolean createEncInd() {
        for (EncSocioNutriciaInd miembro : integrantes) {
            try {
                getIndController().createFromEncSocNut(miembro,
                        getEncSocioNutricia(getSelected().getEncSocioNutriciaPK(), true));
            } catch (Exception e) {
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                return false;
            }
        }
        return true;
    }

    public boolean createEncSeg() {
        try {
            getSegController().createFromEncSegNut(getSeguridadDialog());
            return true;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return false;
        }
    }

    private boolean updateEnc() {
        try {
            getFacade().edit(current);
            return true;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return false;
        }
    }

    private boolean updateEncFam() {
        try {
            getFamController().updateFromEncSocNut(pregRespFamiliares);

            return true;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return false;
        }
    }

    private boolean updateEncInd() {
        for (EncSocioNutriciaInd miembro : integrantes) {
            try {
                getIndController().updateFromEncSocNut(miembro,
                        getEncSocioNutricia(getSelected().getEncSocioNutriciaPK(), true));
            } catch (Exception e) {
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                return false;
            }
        }
        return true;
    }

    private boolean updateEncSeg() {
        try {
            getSegController().updateFromEncSegNut(getSeguridadDialog());

            return true;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return false;
        }
    }

    public void destroyEnc() {
        try {
            EncSocioNutricia aBorrar = getEncSocioNutricia(getSelected().getEncSocioNutriciaPK(), true);
            getFacade().remove(aBorrar);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_Generico"));
        }
    }

    public void destroyEncFam() {
        try {
            getFamController().removeFromEncSocNut(
                    new EncSocioNutriciaFamPK(current.getEncSocioNutriciaPK().getExpediente(), current.getEncSocioNutriciaPK().getAreaGeo()));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_Generico"));
        }

        destroyEnc();
    }

    public void destroyEncInd() {
        try {
            getIndController().removeFromEncSocNut(current.getEncSocioNutriciaPK().getExpediente(), current.getEncSocioNutriciaPK().getAreaGeo());
        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_Generico"));
        }

        destroyEncFam();
        destroyEnc();
    }

    public void guardar() {
        try {
            getPregRespFamiliares().setEncSocioNutriciaFamPK(new EncSocioNutriciaFamPK(
                    current.getEncSocioNutriciaPK().getExpediente(), current.getEncSocioNutriciaPK().getAreaGeo()));

            getSeguridadDialog().setEncSocioNutriciaSegPK(new EncSocioNutriciaSegPK(
                    current.getEncSocioNutriciaPK().getExpediente(), current.getEncSocioNutriciaPK().getAreaGeo()));

            if (validaGuardar()) {
                if (createEnc()) {  // Se agregó correctamente a la BD el current
                    if (createEncFam()) { // Se agregó correctamente a la BD datos de familia
                        if (createEncInd()) { // Se agregaron correctamente a la BD los integrantes
                            if (createEncSeg()) {
                                limpiaPantalla();

                                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaSuccess_Create"));
                            } else {
                                destroyEncInd();
                            }
                        } else {
                            // Error al agregar integrantes
                            destroyEncInd();
                        }
                    } else {
                        // Error al agregar datos de Familia
                        destroyEncFam();
                    }
                } else {
                    // Error al agregar datos del current
                    destroyEnc();
                }
            }
        } catch (Exception e) {
//            arreglaPregsOtro();
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_Generico"));
        }
    }

    public void actualizar() {

        getSeguridadDialog().setEncSocioNutriciaSegPK(new EncSocioNutriciaSegPK(
                current.getEncSocioNutriciaPK().getExpediente(), current.getEncSocioNutriciaPK().getAreaGeo()));

        if (validaActualizar()) {
            if (updateEnc()) {
                if (updateEncFam()) {
                    if (updateEncInd()) {
                        if (seguridadDialog != null) {
                            updateEncSeg();
                            limpiaPantalla();
                            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaSuccess_Edit"));
                        } else {
                            createEncSeg();
                            limpiaPantalla();
                            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaSuccess_Edit"));
                        }
//                        if (updateEncSeg()) {
//                        limpiaPantalla();
//
//                        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaSuccess_Edit"));
//                    }
                    }
                }
            }
        }
    }

    private boolean validaGuardar() {
        boolean esValido = true;

        if (integrantes.isEmpty()) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaEmpty_tablaIntegrantes"));
        }

        return esValido;
    }

    private void checkID() {
        boolean cambioDeId = false;

        while (getEncSocioNutricia(getSelected().getEncSocioNutriciaPK(), true) != null) {
            cambioDeId = true;
            getSelected().setEncSocioNutriciaPK(new EncSocioNutriciaPK(
                    current.getEncSocioNutriciaPK().getExpediente() + 1, current.getEncSocioNutriciaPK().getAreaGeo()));
        }

        int expediente = current.getEncSocioNutriciaPK().getExpediente();

        if (cambioDeId) {

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaID_Cambio")
                    + expediente);
        }
    }

    private boolean validaActualizar() {
        boolean esValido = true;

        if (integrantes.isEmpty()) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaEmpty_tablaIntegrantes"));
        }

        return esValido;
    }

    public Date Hoy() {
        return new Date();
    }

    public void areaGeoChanged() {
        getSelected()
                .getEncSocioNutriciaPK()
                .setExpediente(getFacade()
                .findLastExp(getSelected().getEncSocioNutriciaPK().getAreaGeo()) + 1);
    }

    public void areaViewChanged() {
        areaGeoSelected = true;

        if (getSelected().getEncSocioNutriciaPK().getExpediente() > 0) {
            getSelected().getEncSocioNutriciaPK().setExpediente(0);
            idSelected = false;
        }
    }

    public void agregaIntegrante() {
        getIntegranteDialog().setEncSocioNutriciaIndPK(
                new EncSocioNutriciaIndPK(getSelected().getEncSocioNutriciaPK().getExpediente(), getSelected().getEncSocioNutriciaPK().getAreaGeo(), getIntegranteDialog().getEncSocioNutriciaIndPK().getNombre()));

        integranteAux = getIntegranteDialog();

        acomodaValoresIntegrante();

        if (validaAgregaIntegrante()) {
            getIntegrantes().add(integranteAux);

            integranteDialog = null;
            integranteAux = null;

            limpiaOtrosInt();

            RequestContext.getCurrentInstance().execute("agregarInt.hide()");
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndSuccess_AgregaIntegrante"));
        }
    }

    private void acomodaValoresIntegrante() {
        if (otroParentesco) {
            if (otroParentescoString == null) {
                otroParentescoString = "";
            } else {
                otroParentescoString = "!" + otroParentescoString;
            }
            integranteAux.setParentesco(otroParentescoString);
        }

        if (otraRamaAct) {
            if (otraRamaActString == null) {
                otraRamaActString = "";
            } else {
                otraRamaActString = "!" + otraRamaActString;
            }
            integranteAux.setRamaActiv(otraRamaActString);
        }

        if (otroSeguroSocial) {
            if (otroSeguroSocialString == null) {
                otroSeguroSocialString = "";
            } else {
                otroSeguroSocialString = "!" + otroSeguroSocialString;
            }
            integranteAux.setSeguroSocial(otroSeguroSocialString);
        }

        if (otraAyudaAlimentaria) {
            if (otraAyudaAlimentariaString == null) {
                otraAyudaAlimentariaString = "";
            } else {
                otraAyudaAlimentariaString = "!" + otraAyudaAlimentariaString;
            }
            integranteAux.setAyudaAlim(otraAyudaAlimentariaString);
        }

        if (otroGrupoEtnico) {
            if (otroGrupoEtnicoString == null) {
                otroGrupoEtnicoString = "";
            } else {
                otroGrupoEtnicoString = "!" + otroGrupoEtnicoString;
            }
            integranteAux.setGrupoEtnico(otroGrupoEtnicoString);
        }
    }

    private boolean validaAgregaIntegrante() {
        boolean esValido = true;

        if (integranteAux.getParentesco().trim().isEmpty()) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndRequired_OtroParentesco"));
        } else if (integranteAux.getParentesco().substring(1).contains("!")) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndError_InvalidoAdmiracion")
                    + ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndTitle_Parentesco"));
        }

        if (integranteAux.getGenero() == null) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndError_Genero"));
        }

        if (integranteAux.getPeso() <= 0) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndError_Peso"));
        }

        if (integranteAux.getTalla() <= 0) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndError_Talla"));
        }

        if (integranteAux.getRamaActiv().trim().isEmpty()) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndRequired_OtraRamaAct"));
        } else if (integranteAux.getRamaActiv().substring(1).contains("!")) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndError_InvalidoAdmiracion")
                    + ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndTitle_RamaActividad"));
        }

        if (integranteAux.getSeguroSocial().trim().isEmpty()) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndRequired_OtroSeguroSocial"));
        } else if (integranteAux.getSeguroSocial().substring(1).contains("!")) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndError_InvalidoAdmiracion")
                    + ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndTitle_SSocial"));
        }

        if (integranteAux.getProbSalud() == null) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndError_ProbSalud"));
        }

        if (integranteAux.getAyudaAlim().trim().isEmpty()) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndRequired_OtraAyudaAlim"));
        } else if (integranteAux.getAyudaAlim().substring(1).contains("!")) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndError_InvalidoAdmiracion")
                    + ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndTitle_AyudaAlim"));
        }

        if (integranteAux.getGrupoEtnico() == null) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndError_GpoEtnico"));
        } else if (integranteAux.getGrupoEtnico().substring(1).contains("!")) {
            esValido = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndError_InvalidoAdmiracion")
                    + ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndTitle_GpoEtnico"));
        }

        return esValido;
    }

    public void modificarIntegrante(EncSocioNutriciaInd integrante) {
        eliminarIntegrante(integrante);

        integranteDialog = integrante;

        arreglaIntegranteOtro();

        RequestContext.getCurrentInstance().execute("integrantesTabla.hide()");
        RequestContext.getCurrentInstance().execute("agregarInt.show()");
    }

    public void eliminarIntegrante(EncSocioNutriciaInd integrante) {
        getIntegrantes().remove(integrante);

        if (getIntegrantes().isEmpty()) {
            integrantes = null;
        }

        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaIndSuccess_Remove")
                + integrante.getEncSocioNutriciaIndPK().getNombre());
    }

    public void cambiaParentesco() {
        if (getIntegranteDialog().getParentesco().startsWith("Otro")) {
            otroParentesco = true;
        } else {
            otroParentescoString = "";
            otroParentesco = false;
        }
    }

    public void cambiaRamaAct() {
        if (getIntegranteDialog().getRamaActiv().startsWith("Otros")) {
            otraRamaAct = true;
        } else {
            otraRamaActString = "";
            otraRamaAct = false;
        }
    }

    public void cambiaSeguroSocial() {
        if (getIntegranteDialog().getSeguroSocial().startsWith("Otros")) {
            otroSeguroSocial = true;
        } else {
            otroSeguroSocialString = "";
            otroSeguroSocial = false;
        }
    }

    public void cambiaAyudaAlimentaria() {
        if (getIntegranteDialog().getAyudaAlim().startsWith("Otros")) {
            otraAyudaAlimentaria = true;
        } else {
            otraAyudaAlimentariaString = "";
            otraAyudaAlimentaria = false;
        }
    }

    public void cambiaEEstadoFis() {
        if (getIntegranteDialog().getEtapaEstFis().startsWith("Embarazo")) {
            embarazo = true;
        } else {
            getIntegranteDialog().setSemEmbarazo(0);
            getIntegranteDialog().setPesoPreg(0.0);
            embarazo = false;
        }
    }

    public void cambiaGrupoEtnico() {
        if (getIntegranteDialog().getGrupoEtnico().equals("Si")) {
            otroGrupoEtnico = true;
        } else {
            otroGrupoEtnicoString = "";
            otroGrupoEtnico = false;
        }
    }

    public EncSocioNutriciaInd getIntegranteDialog() {
        if (integranteDialog == null) {
            integranteDialog = new EncSocioNutriciaInd();
            integranteDialog.setEncSocioNutriciaIndPK(
                    new EncSocioNutriciaIndPK(
                    getSelected().getEncSocioNutriciaPK().getExpediente(), getSelected().getEncSocioNutriciaPK().getAreaGeo(), ""));
        }
        return integranteDialog;
    }

    public List<EncSocioNutriciaInd> getIntegrantes() {
        if (integrantes == null) {
            integrantes = new ArrayList<EncSocioNutriciaInd>();
        }
        return integrantes;
    }

    public boolean renderPreg9() {
        if (preg9Si()) {
            return true;
        } else {
            pregRespFamiliares.setP9R(null);
            return false;
        }
    }

    private boolean preg9Si() {
        return (pregRespFamiliares.getP8R() == 2
                || pregRespFamiliares.getP8R() == 3);
    }

    public void cambiaPreg9() {
        if (getPregRespFamiliares().getP9R() == 4) {
            otroPreg9 = true;
        } else {
            otroPreg9 = false;
        }
    }

    public boolean renderPreg12() {
        if (preg12Si()) {
            return true;
        } else {
            pregRespFamiliares.setP12R(null);
            return false;
        }
    }

    private boolean preg12Si() {
        return (pregRespFamiliares.getP11R() == 2
                || pregRespFamiliares.getP11R() == 3);
    }

    public void cambiaPreg12() {
        if (getPregRespFamiliares().getP12R() == 6) {
            otroPreg12 = true;
        } else {
            otroPreg12 = false;
        }
    }

    public boolean preg14Si() {
        return getPregRespFamiliares().isP14R();
    }

    public void calculaTotalEgresos() {
        totalEgresos =
                pregRespFamiliares.getP19Alimentacion().doubleValue()
                + pregRespFamiliares.getP20Luz().doubleValue()
                + pregRespFamiliares.getP21Agua().doubleValue()
                + pregRespFamiliares.getP22Gas().doubleValue()
                + pregRespFamiliares.getP23Otros().doubleValue();
    }

    public boolean preg24Si() {
        return pregRespFamiliares.isP24R();
    }

    public boolean preg29Si() {
        return pregRespFamiliares.isP29R();
    }

    public void cambiaPreg30() {
        if (getPregRespFamiliares().getP30Ganado() == 3) {
            otroPreg30 = true;
        } else {
            otroPreg30 = false;
        }
    }
    
    public void cambiaPreg16() {
        if (getSeguridadDialog().getPregunta16() == true) {
            Preg16Mes = true;
        } else {
            Preg16Mes = false;
        }
    }

    public int getAnios(EncSocioNutriciaInd integrante) {
        return integrante.getAnios();
    }

    public int getMeses(EncSocioNutriciaInd integrante) {
        return integrante.getMeses();
    }

    public int getIndexOfIntegrantes(EncSocioNutriciaInd integrante) {
        return integrantes.indexOf(integrante);
    }

    public String getPregunta(int indicePregunta) {
        return getFacade().findPregFam(indicePregunta);
    }

    public String getRespuesta(int indicePregunta, int indiceRespuesta) {
        return getFacade().findRespFam(indicePregunta, indiceRespuesta);
    }

    public SelectItem[] getComboBoxValue(int identificador) {
        EncSocioNutriciaUtil util = new EncSocioNutriciaUtil();

        String[] labels = util.getLabels(identificador);
        int labelsSize = labels.length;

        SelectItem[] items = new SelectItem[labelsSize];

        for (int i = 0; i < labelsSize; i++) {
            items[i] = new SelectItem(
                    labels[i].substring(4), // Value
                    labels[i]);             // Label
        }
        return items;
    }
    
    //<editor-fold defaultstate="collapsed" desc="gets sets ReporteMensual">
    public EncSocioNutriciaFam getPregRespFamiliares() {
        if (pregRespFamiliares == null) {
            pregRespFamiliares = new EncSocioNutriciaFam();
        }
        return pregRespFamiliares;
    }

    public boolean isOtroParentesco() {
        return otroParentesco;
    }

    public String getOtroParentescoString() {
        return otroParentescoString;
    }

    public void setOtroParentescoString(String otroParentescoString) {
        this.otroParentescoString = otroParentescoString;
    }

    public boolean isOtraRamaAct() {
        return otraRamaAct;
    }

    public String getOtraRamaActString() {
        return otraRamaActString;
    }

    public void setOtraRamaActString(String otraRamaActString) {
        this.otraRamaActString = otraRamaActString;
    }

    public boolean isOtroSeguroSocial() {
        return otroSeguroSocial;
    }

    public String getOtroSeguroSocialString() {
        return otroSeguroSocialString;
    }

    public void setOtroSeguroSocialString(String otroSeguroSocialString) {
        this.otroSeguroSocialString = otroSeguroSocialString;
    }

    public boolean isOtraAyudaAlimentaria() {
        return otraAyudaAlimentaria;
    }

    public String getOtraAyudaAlimentariaString() {
        return otraAyudaAlimentariaString;
    }

    public void setOtraAyudaAlimentariaString(String otraAyudaAlimentariaString) {
        this.otraAyudaAlimentariaString = otraAyudaAlimentariaString;
    }

    public boolean isEmbarazo() {
        return embarazo;
    }

    public boolean isOtroGrupoEtnico() {
        return otroGrupoEtnico;
    }

    public String getOtroGrupoEtnicoString() {
        return otroGrupoEtnicoString;
    }

    public void setOtroGrupoEtnicoString(String otroGrupoEtnicoString) {
        this.otroGrupoEtnicoString = otroGrupoEtnicoString;
    }

    public double getTotalEgresos() {
        return totalEgresos;
    }

    public boolean isOtroPreg9() {
        return otroPreg9;
    }

    public boolean isOtroPreg12() {
        return otroPreg12;
    }

    public boolean isOtroPreg30() {
        return otroPreg30;
    }

    public EncSocioNutriciaFamController getFamController() {
        return famController;
    }

    public void setFamController(EncSocioNutriciaFamController famController) {
        this.famController = famController;
    }

    public EncSocioNutriciaIndController getIndController() {
        return indController;
    }

    public void setIndController(EncSocioNutriciaIndController indController) {
        this.indController = indController;
    }

    public EncSocioNutriciaSegController getSegController() {
        return segController;
    }

    public void setSegController(EncSocioNutriciaSegController segController) {
        this.segController = segController;
    }

    public boolean isIdSelected() {
        return idSelected;
    }

    public boolean isAreaGeoSelected() {
        return areaGeoSelected;
    }
    //</editor-fold>

    public EncSocioNutriciaSeg getSeguridadDialog() {
        if (seguridadDialog == null) {
            seguridadDialog = new EncSocioNutriciaSeg();
        }
        return seguridadDialog;
    }

    public boolean isPreg16Mes() {
        return Preg16Mes;
    }

    public void setPreg16Mes(boolean Preg16Mes) {
        this.Preg16Mes = Preg16Mes;
    }

    @FacesConverter(forClass = EncSocioNutricia.class)
    public static class EncSocioNutriciaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EncSocioNutriciaController controller = (EncSocioNutriciaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "encSocioNutriciaController");
            return controller.getEncSocioNutricia(getKey(value), true);
        }

        com.caritas.entity.EncSocioNutriciaPK getKey(String value) {
            com.caritas.entity.EncSocioNutriciaPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.caritas.entity.EncSocioNutriciaPK();
            key.setExpediente(Integer.parseInt(values[0]));
            key.setAreaGeo(values[1].charAt(0));
            return key;
        }

        String getStringKey(com.caritas.entity.EncSocioNutriciaPK value) {
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
            if (object instanceof EncSocioNutricia) {
                EncSocioNutricia o = (EncSocioNutricia) object;
                return getStringKey(o.getEncSocioNutriciaPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName()
                        + "; expected type: " + EncSocioNutricia.class.getName());
            }
        }
    }
}
