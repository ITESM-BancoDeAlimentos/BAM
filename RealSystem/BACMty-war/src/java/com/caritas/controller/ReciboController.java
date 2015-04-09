package com.caritas.controller;

import com.caritas.entity.TablaArticulos;
import com.caritas.entity.Recibo;
import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.entity.Areas;
import com.caritas.entity.Articulos;
import com.caritas.entity.Donantes;
import com.caritas.entity.Programas;
import com.caritas.entity.ReciboDet;
import com.caritas.entity.Sucursales;
import com.caritas.entity.Unidad;
import com.caritas.entity.Usuarios;
import com.caritas.facade.ReciboFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
//import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "reciboController")
@ViewScoped
public class ReciboController implements Serializable {

    private Recibo current;
    private DataModel items = null;
    @EJB
    private com.caritas.facade.ReciboFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Date fechaCaducidadArticulo;
    private Date fechaRecibo;
    private List<Articulos> listaArticulos;
    private List<TablaArticulos> listaAImprimir;
    private boolean programaEnabled = false;
    private boolean sucursalEnabled = false;
    private boolean editMode = false;
    private Date hoy;
    private int folioAnterior;
    @ManagedProperty(value = "#{reciboDetController}")
    private ReciboDetController reciboDetController;
    @ManagedProperty(value = "#{foliosEntradaController}")
    private FoliosEntradaController foliosEntradaController;
    // Identificadores de pantalla actual
    private final short TIENDAS = 1;
    private final short AMBA = 2;
    private short pantallaActual = 0;

    public ReciboController() {
    }

    @PostConstruct
    private void inicio() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String path = request.getRequestURI();

        if (path.endsWith("RecDonTie.xhtml") || path.endsWith("RecDonAMBA.xhtml")) {
            prepareRecDonCreate();
        } else if (path.endsWith("EditTie.xhtml") || path.endsWith("EditAMBA.xhtml")) {
            prepareRecDonEdit();
        } else if (path.endsWith("ViewTie.xhtml") || path.endsWith("ViewAMBA.xhtml")) {
            prepareRecDonView();
        } else {
            pantallaNueva();
        }

        String vistaActual = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        if (vistaActual.endsWith("RecDonTie.xhtml")
                || vistaActual.endsWith("ViewTie.xhtml")
                || vistaActual.endsWith("EditTie.xhtml")) {
            pantallaActual = TIENDAS;
        } else if (vistaActual.endsWith("RecDonAMBA.xhtml")
                || vistaActual.endsWith("ViewAMBA.xhtml")
                || vistaActual.endsWith("EditAMBA.xhtml")) {
            pantallaActual = AMBA;
        }
    }

    public Recibo getSelected() {
        if (current == null) {
            current = new Recibo();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ReciboFacade getFacade() {
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
        current = (Recibo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ViewTie";
    }

    public String prepareCreate() {
        current = new Recibo();
        selectedItemIndex = -1;
        return "Create";
    }

    public String limpiaRecDonTieCreate() {
        return "RecDonTie?faces-redirect=true";
    }

    public String limpiaRecDonAMBACreate() {
        return "RecDonAMBA?faces-redirect=true";
    }

    public String limpiaRecDonTieEdit() {
        return "EditTie?faces-redirect=true";
    }

    public String limpiaRecDonAMBAEdit() {
        return "EditAMBA?faces-redirect=true";
    }

    public String limpiaRecDonTieView() {
        return "ViewTie?faces-redirect=true";
    }

    public String limpiaRecDonAMBAView() {
        return "ViewAMBA?faces-redirect=true";
    }

    private void pantallaNueva() {
        current = new Recibo();
        GregorianCalendar cal = new GregorianCalendar();
        fechaRecibo = cal.getTime();
        fechaCaducidadArticulo = cal.getTime();
        current.setIDArea(new Areas());
        current.setIDPrograma(new Programas());
        current.setIDDonante(new Donantes());
        current.setIDSucursales(new Sucursales());
        programaEnabled = false;
        sucursalEnabled = false;
        limpiaTabla();
    }

    public void prepareRecDonCreate() {
        pantallaNueva();
        editMode = false;

        try {
            String folioStr = getFacade().findLastFolioENT();
            folioAnterior = Integer.parseInt(folioStr.trim());
            getSelected().setIDFolio(String.valueOf(folioAnterior + 1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //return "RecDonTie?faces-redirect=true";
    }

    public void prepareRecDonEdit() {
        editMode = true;

        boolean folioVacio = false;
        if (getSelected().getIDFolio() == null) {
            folioVacio = true;
        } else if (getSelected().getIDFolio().trim().isEmpty()) {
            folioVacio = true;
        }

        if (folioVacio) {
            pantallaNueva();
            //return "EditTie?faces-redirect=true";
        } else {
            buscaPorFolio();
        }
    }

    public void prepareRecDonView() {
        current = new Recibo();
        listaAImprimir = new ArrayList<TablaArticulos>();
        listaArticulos = new ArrayList<Articulos>();
        editMode = false;
//        return "ViewTie?faces-redirect=true";
    }

    private void limpiaTabla() {
//        selectOneMenuArticulos = new ArrayList<String>();
        listaArticulos = new ArrayList<Articulos>();
        listaAImprimir = new ArrayList<TablaArticulos>();
        if (!editMode) {
            listaAImprimir.add(new TablaArticulos("", " ", new Unidad(), 1.0, 0.0, 0.0, 0.0, fechaRecibo, 0.0, 0.0));
        }
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void createRowRecibo() {
        getFacade().create(current);
    }

    public void editRowRecibo() {
        getFacade().edit(current);
    }

    /**
     * Función que manda a llamar el facade de Recibo_Det e inserta el detalle
     * de un articulo de recibo
     *
     * @param tempArticulo Variable temporal de articulos
     * @param itemActual Articulo en la lista
     * @param art
     */
    public void createRowRecibo_Det(Articulos temp, TablaArticulos itemActual) {
        reciboDetController.createFromRecibo(temp, itemActual, current);
    }

    public void recuperarRecibos_Det(List<ReciboDet> listaRecuperacion) {
        reciboDetController.recuperarLista(listaRecuperacion);
    }

//    public void editRowRecibo_Det(Articulos temp, TablaArticulos itemActual, int indice) {
//        reciboDetController.editFromRecibo(temp, itemActual, current, indice);
//    }
    public void deleteReciboDet(String idFolio) {
        reciboDetController.destroyFromRecibo(idFolio);
    }

//    public String prepareEdit() {
//        current = (Recibo) getItems().getRowData();
//        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
//        return "EditTie";
//    }
//    public String update() {
//        try {
//            getFacade().edit(current);
//            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboUpdated"));
//            return "ViewTie";
//        } catch (Exception e) {
//            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
//            return null;
//        }
//    }
    public String destroy() {
        current = (Recibo) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboDeleted"));
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

    public SelectItem[] getItemsAvailableSelectByOrigen() {
        return JsfUtil.getSelectItems(ejbFacade.findRecibosByOrigen(pantallaActual), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getItemsAvailableSelectAreas() {
        return JsfUtil.getSelectItems(ejbFacade.findAreas(), false);
    }

    public SelectItem[] getItemsAvailableSelectProgramas() {
        return JsfUtil.getSelectItems(ejbFacade.findProgramas(current.getIDArea()), false);
    }

    public SelectItem[] getItemsAvailableSelectSucursales() {
        return JsfUtil.getSelectItems(ejbFacade.findSucursales(current.getIDDonante()), false);
    }

    public SelectItem[] getItemsAvailableSelectDonantesTiendas() {
        return JsfUtil.getSelectItems(ejbFacade.findDonantesTiendas(), false);
    }

    public SelectItem[] getItemsAvailableSelectDonantesAMBA() {
        return JsfUtil.getSelectItems(ejbFacade.findDonantesAMBA(), false);
    }

    public SelectItem[] getItemsAvailableSelectArticulos() {
        return JsfUtil.getSelectItems(ejbFacade.findArticulos(), false);
    }

    public void viewIDArea() {
        if (current.getIDArea() != null) {

            // Se verifica que el select no sea el "Seleccione uno"
            if (!current.getIDArea().getArea().equals(" ")) {
                current.setIDArea(ejbFacade.findIDArea(current.getIDArea().getArea()));
                current.setIDPrograma(new Programas());

                programaEnabled = true;
            } else {
                current.setIDArea(new Areas());
                current.setIDPrograma(new Programas());
                programaEnabled = false;
            }
        } else {
            programaEnabled = false;
        }

//        if (editMode) {
//            return "EditTie";
//        } else {
//            return "RecDonTie";
//        }
    }

    public void viewIDProgramas() {
        if (current.getIDPrograma() != null) {

            // Se verifica que esté o no seleccionado "Seleccione uno"
            if (!current.getIDPrograma().getPrograma().equals(" ")) {

                current.setIDPrograma(ejbFacade.findIDPrograma(current.getIDPrograma().getPrograma()));
            } else {
                current.setIDPrograma(new Programas());
            }
        } else {
            current.setIDPrograma(new Programas());
        }

//        if (editMode) {
//            return "EditTie";
//        } else {
//            return "RecDonTie";
//        }
    }

    public void viewIDDonantes() {
        if (current.getIDDonante() != null) {

            // Se verifica que el select no sea el "Seleccione uno"
            if (!current.getIDDonante().getDonante().equals(" ")) {
                // Obtenemos los donantes referenciados al nombre seleccionado
                current.setIDDonante(ejbFacade.findIDDonante(current.getIDDonante().getDonante()));
                current.setIDSucursales(new Sucursales());
                current.setIDSucursal(null);

                // Se habilita o deshabilita sucursales dependiendo si el donante tiene o no sucursales
                sucursalEnabled = current.getIDDonante().getSucursales();
            } else {
                current.setIDDonante(new Donantes());
                current.setIDSucursales(new Sucursales());
            }
        }

//        if (editMode) {
//            return "EditTie";
//        } else {
//            return "RecDonTie";
//        }
    }

    public void viewIDSucursales(String sucursal) {
        if (current.getIDSucursales() != null) {

            // Se verifica que esté o no seleccionado "Seleccione uno"
            if (!current.getIDSucursales().getIDSucursal().equals(" ")) {

                // Establece el objeto de sucursales dependiendo la sucursal y el donante seleccionado
                current.setIDSucursales(ejbFacade.findIDSucursales(sucursal, current.getIDDonante()));

                // Se guarda este campo para uso del antiguo sistema SIBAAC
                current.setIDSucursal(current.getIDSucursales().getIDSucursal());
            } else {
                current.setIDSucursales(new Sucursales());
            }
        }

//        if (editMode) {
//            return "EditTie";
//        } else {
//            return "RecDonTie";
//        }
    }

    public void agregarArticulo(TablaArticulos item) {
        try {
            if (item != null) {
                int indice = listaAImprimir.indexOf(item);

                if (!item.getDescripcion().trim().isEmpty()) {
                    Articulos articuloNuevo = ejbFacade.findArticulos(item.getDescripcion());
                    TablaArticulos articuloNuevo_tabla;

                    if (indice > listaArticulos.size() - 1) {
                        listaArticulos.add(articuloNuevo);
                    } else {
                        listaArticulos.set(indice, articuloNuevo);
                    }

                    articuloNuevo_tabla = new TablaArticulos(articuloNuevo.getIDArticulo(), articuloNuevo.getArticulo(),
                            articuloNuevo.getUnidadMed1(), 1.0, articuloNuevo.getPeso(), articuloNuevo.getPeso(), articuloNuevo.getCostoBenef1(),
                            fechaCaducidadArticulo, articuloNuevo.getCostoBenef1(), articuloNuevo.getCuotaRecup1());

                    listaAImprimir.set(indice, articuloNuevo_tabla);
                    if (listaAImprimir.size() == listaArticulos.size()) {
                        listaAImprimir.add(new TablaArticulos("", " ", new Unidad(), 0.0, 0.0, 0.0, 0.0, fechaCaducidadArticulo, 0.0, 0.0));
                    }
                } else {
                    item = new TablaArticulos("", " ", new Unidad(), 0.0, 0.0, 0.0, 0.0, fechaCaducidadArticulo, 0.0, 0.0);

                    listaAImprimir.set(indice, item);
                    listaArticulos.set(indice, new Articulos());
                }
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboError_ArticuloBD"));
        }
//        return "RecDonTie";
    }

    public void actualizaArticulo(TablaArticulos item) {
        if (item != null) {
            if (!item.getDescripcion().trim().isEmpty()) {
                int indice = listaAImprimir.indexOf(item);
                listaAImprimir.get(indice).setTotal(Double.parseDouble(item.getCostoEntrada()) * item.getCantidad());
                listaAImprimir.get(indice).setPesoTotal(Double.parseDouble(item.getPesoUnitario()) * item.getCantidad());
            }
        }
    }

    /**
     * Función para filtrar dependiendo una cadena dada
     *
     * @param query La cadena para filtrar
     * @return La lista de resultados del query
     */
    public List<String> completeArts(String query) {
        List<String> results;// = new ArrayList<String>();
        try {
            results = ejbFacade.findArticulosLike(query);
        } catch (Exception e) {
            return null;
        }
        return results;
    }

    public void eliminarFila(TablaArticulos item) {
        if (item != null) {
            int indice = listaAImprimir.indexOf(item);

            if (indice != listaAImprimir.size() - 1) {
                listaAImprimir.remove(indice);
                listaArticulos.remove(indice);
            }
        }
//        return "RecDonTie";
    }

    public void guardarRecibo() {
        boolean isOtroFolio = false;
        try {
            if (checkRequireds()) {
                // Verifica que no esté repetido el folio junto con el tipoMov = "ENT" en la B.D.

                Usuarios elabora = ejbFacade.getUsuarioNombre(JsfUtil.getLoggedUser().getIDUsuario());

                current.setTipoMov("ENT");
                current.setFechaMov(fechaRecibo);
                current.setStatusMov("RECIBO");
                current.setFechaSist(new Date());
                current.setIDUsuario(elabora);

                switch (pantallaActual) {
                    case TIENDAS:
                        current.setOrigen("D");
                        break;
                    case AMBA:
                        current.setOrigen("A");
                        break;
                }

                for (Articulos temp : listaArticulos) {
                    int indice = listaArticulos.indexOf(temp);
                    TablaArticulos itemActual = listaAImprimir.get(indice);

                    if (current.getIDArea().getIDArea() == null) {
                        current.setIDArea(null);
                    }
                    if (current.getIDPrograma().getIDPrograma() == null) {
                        current.setIDPrograma(null);
                    }

                    if (current.getIDSucursales().getIDSucursales() == null) {
                        current.setIDSucursales(null);
                        current.setIDSucursal(null);
                    }

                    createRowRecibo_Det(temp, itemActual);

                    if (current.getIDArea() == null) {
                        current.setIDArea(new Areas());
                    }
                    if (current.getIDPrograma() == null) {
                        current.setIDPrograma(new Programas());
                    }
                    if (current.getIDSucursales() == null) {
                        current.setIDSucursales(new Sucursales());
                    }
                }

                if (current.getIDArea().getIDArea() == null) {
                    current.setIDArea(null);
                }
                if (current.getIDPrograma().getIDPrograma() == null) {
                    current.setIDPrograma(null);
                }
                if (current.getIDSucursales().getIDSucursales() == null) {
                    current.setIDSucursales(null);
                    current.setIDSucursal(null);
                }

                // Se revisa que no se haya guardado un recibo 
                // con el mismo folio durante el proceso anterior
                if (!isRegistroUnico() && !editMode) {
                    // Si no es registro único, entonces se agrega un folio válido
                    String folioStr = getFacade().findLastFolioENT();
                    folioAnterior = Integer.parseInt(folioStr.trim());
                    getSelected().setIDFolio(String.valueOf(folioAnterior + 1));
                    String folio10 = "          ".concat(current.getIDFolio().trim()); // Folio con 10 caracteres, agregando espacios a la izquierda
                    folio10 = folio10.substring(folio10.length() - 10, folio10.length()); // Obtenemos 10 caracteres, contando desde la derecha
                    current.setIDFolio(folio10);
                    isOtroFolio = true;
                }

                // Si no hubo errores, se almacena el recibo en la BD
                createRowRecibo();

                getFoliosEntradaController().updateFromMovs(current.getIDFolio(), String.valueOf(folioAnterior));
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Success"));
                // Indica que se utilizó un folio distinto al seleccionado para guardar
                if (isOtroFolio) {
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Success_OtroFolio") + (folioAnterior + 1));
                }
                prepareRecDonCreate();
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboError_AltaRecibo"));
            deleteReciboDet(current.getIDFolio());
            prepareRecDonCreate();
        }
    }

    /**
     * Verifica que el folio sea único, esto es, que no haya un recibo con el
     * mismo folio.
     *
     * @return <code>true</code> de no estar repetido y <code>false</code> en
     * caso contrario
     */
    private boolean isRegistroUnico() {
        // Si encuentra un folio repetido, entonces no es registro único
        return ejbFacade.findFolioRepetido(
                getSelected().getIDFolio()) ? false : true;
    }

    public void editarRecibo() {
        // Esta lista sirve cuando hay un error, se recuperan los registros que
        // habían antes de que ocurriera el error
        List<ReciboDet> detalleAnterior = getFacade().findReciboDetByFolio(current.getIDFolio());

        try {
            if (checkRequireds()) {
                Usuarios edita = ejbFacade.getUsuarioNombre(JsfUtil.getLoggedUser().getIDUsuario());

                deleteReciboDet(current.getIDFolio());

                current.setTipoMov("ENT");
                current.setFechaMov(fechaRecibo);
                current.setStatusMov("RECIBO");
                current.setFechaSist(new Date());
                current.setIDUsuario(edita);

                switch (pantallaActual) {
                    case TIENDAS:
                        current.setOrigen("D");
                        break;
                    case AMBA:
                        current.setOrigen("A");
                        break;
                }

                for (Articulos temp : listaArticulos) {
                    int indice = listaArticulos.indexOf(temp);
                    TablaArticulos itemActual = listaAImprimir.get(indice);

                    if (current.getIDArea().getIDArea() == null) {
                        current.setIDArea(null);
                    }
                    if (current.getIDPrograma().getIDPrograma() == null) {
                        current.setIDPrograma(null);
                    }

                    if (current.getIDSucursales().getIDSucursales() == null) {
                        current.setIDSucursales(null);
                        current.setIDSucursal(null);
                    }

                    createRowRecibo_Det(temp, itemActual);
                    //editRowRecibo_Det(temp, itemActual, indice);

                    if (current.getIDArea() == null) {
                        current.setIDArea(new Areas());
                    }
                    if (current.getIDPrograma() == null) {
                        current.setIDPrograma(new Programas());
                    }
                    if (current.getIDSucursales() == null) {
                        current.setIDSucursales(new Sucursales());
                    }
                }

                if (current.getIDArea().getIDArea() == null) {
                    current.setIDArea(null);
                }
                if (current.getIDPrograma().getIDPrograma() == null) {
                    current.setIDPrograma(null);
                }
                if (current.getIDSucursales().getIDSucursales() == null) {
                    current.setIDSucursales(null);
                    current.setIDSucursal(null);
                }

                // Si no hubo errores, se almacenan los cambios en la BD
                editRowRecibo();
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle(("/Bundle")).getString("Success"));
                prepareRecDonEdit();
                //editMode = false;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle(("/Bundle")).getString("ReciboError_EditRecibo"));
            deleteReciboDet(current.getIDFolio());
            recuperarRecibos_Det(detalleAnterior);
            prepareRecDonEdit();
        }
    }

    private boolean checkRequireds() {
        boolean esValido = true;

        // Verifica que no esté vacío
        if (getSelected().getIDFolio() != null) {
            if (getSelected().getIDFolio().trim().isEmpty()) {
                // Verifica que, si no está vacío, entonces que no sean espacios solamente
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboRequiredMessage_IDFolio"));
                esValido = false;
            } else if (!getSelected().getIDFolio().trim().matches("[0-9]+")) {
                // Verifica que sea numérico
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboValidatorMessage_folio"));
                esValido = false;
            } else {
                String folio10 = "          ".concat(current.getIDFolio().trim()); // Folio con 10 caracteres, agregando espacios a la izquierda
                folio10 = folio10.substring(folio10.length() - 10, folio10.length()); // Obtenemos 10 caracteres, contando desde la derecha
                current.setIDFolio(folio10); // Le asignamos el nuevo valor al folio del current
            }

            // Verifica que se seleccione donante
            if (current.getIDDonante().getIDDonante() == null) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboRequiredMessage_IDDonante"));

                esValido = false;
            }

            // Si tiene sucursal, verifica que se haya seleccionado 
            if (sucursalEnabled && current.getIDSucursales().getIDSucursales() == null) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboRequiredMessage_Sucursal"));
                esValido = false;
            }

            // Verifica que la lista de articulos no esté vacía
            if (listaArticulos.isEmpty()) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboRequiredMessage_ArticulosEmpty"));
                esValido = false;
            }

            if (pantallaActual == AMBA) {
                if (current.getFolioDon().trim().isEmpty()) {
                    JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboRequiredMessage_FolioDonEmpty"));
                    esValido = false;
                }
            }

        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboRequiredMessage_IDFolio"));
            esValido = false;
        }

        return esValido;
    }

    public void buscaPorFolio() {
        listaAImprimir = new ArrayList<TablaArticulos>();

        String folio10 = "          ".concat(current.getIDFolio().trim()); // Folio con 10 caracteres, agregando espacios a la izquierda
        folio10 = folio10.substring(folio10.length() - 10, folio10.length()); // Obtenemos 10 caracteres, contando desde la derecha
        current.setIDFolio(folio10); // Le asignamos el nuevo valor al folio del current

        current = getFacade().findByFolio(current.getIDFolio(), pantallaActual);

        if (current.getIDRecibo() != null) {

            List<ReciboDet> tablaArts = getFacade().findReciboDetByFolio(current.getIDFolio());

            listaArticulos = getFacade().findArticulosByFolio(current.getIDFolio(), tablaArts);

            for (Articulos tempArticulo : listaArticulos) {
                int indice = listaArticulos.indexOf(tempArticulo);

                ReciboDet tempRecDet = tablaArts.get(indice);

                Double cantidad = tempRecDet.getCantidad();

                listaAImprimir.add(new TablaArticulos(tempArticulo.getIDArticulo(), tempArticulo.getArticulo(), tempArticulo.getUnidadMed1(),
                        cantidad, tempArticulo.getPeso(), tempArticulo.getPeso() * cantidad, tempArticulo.getCostoBenef1(), tempRecDet.getFechaCad(),
                        tempArticulo.getCostoBenef1() * cantidad, tempArticulo.getCuotaRecup1()));
            }

            if (editMode) {
                listaAImprimir.add(new TablaArticulos("", " ", new Unidad(), 0.0, 0.0, 0.0, 0.0, fechaCaducidadArticulo, 0.0, 0.0));
                if (current.getIDArea() == null) {
                    current.setIDArea(new Areas());
                } else {
                    programaEnabled = true;
                }
                if (current.getIDPrograma() == null) {
                    current.setIDPrograma(new Programas());
                }
                if (current.getIDSucursales() == null) {
                    current.setIDSucursales(new Sucursales());
                }

                sucursalEnabled = current.getIDDonante().getSucursales();
                programaEnabled = current.getIDArea().getIDArea() != null;
                //return "EditTie?faces-redirect=true";
            }
            //return null;
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ReciboErrorMessage_NotFound"));
        }
    }

    public List<String> completeFolio(String query) {
        try {
            List<String> results = ejbFacade.findRecibosLike(query, pantallaActual);
            return results;
        } catch (Exception e) {
            return null;
        }
    }

    public Date getFechaCaducidadArticulo() {
        return fechaCaducidadArticulo;
    }

    public void setFechaCaducidadArticulo(Date fechaCaducidadArticulo) {
        this.fechaCaducidadArticulo = fechaCaducidadArticulo;
    }

    public boolean isAreaSelected() {
        return programaEnabled;
    }

    public boolean isSucursalEnabled() {
        return sucursalEnabled;
    }

    public List<TablaArticulos> getListaAImprimir() {
        return listaAImprimir;
    }

    public void setListaAImprimir(List<TablaArticulos> listaAImprimir) {
        this.listaAImprimir = listaAImprimir;
    }

    public Date getFechaRecibo() {
        return fechaRecibo;
    }

    public void setFechaRecibo(Date fechaRecibo) {
        this.fechaRecibo = fechaRecibo;
    }

    public ReciboDetController getReciboDetController() {
        return reciboDetController;
    }

    public void setReciboDetController(ReciboDetController RecDetController) {
        this.reciboDetController = RecDetController;


    }

    public boolean isEditMode() {
        return editMode;
    }

    void updateFromMovtos(String folio, String status, short pantallaMovsActual) {
        current = getFacade().findByFolio(folio, pantallaMovsActual);
        current.setStatusMov(status);

        getFacade().edit(current);
    }

    public Date getToday() {
        hoy = new Date();
        return hoy;
    }

    public FoliosEntradaController getFoliosEntradaController() {
        return foliosEntradaController;
    }

    public void setFoliosEntradaController(FoliosEntradaController foliosEntradaController) {
        this.foliosEntradaController = foliosEntradaController;
    }

    @FacesConverter(forClass = Recibo.class)
    public static class ReciboControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReciboController controller = (ReciboController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "reciboController");
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
            if (object instanceof Recibo) {
                Recibo o = (Recibo) object;
                return getStringKey(o.getIDRecibo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Recibo.class.getName());
            }
        }
    }
}
