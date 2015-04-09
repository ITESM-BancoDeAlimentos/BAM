package com.caritas.controller;

import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.entity.Areas;
import com.caritas.entity.Articulos;
import com.caritas.entity.Donantes;
import com.caritas.entity.Movimientos;
import com.caritas.entity.MovtosDet;
import com.caritas.entity.Programas;
import com.caritas.entity.Sucursales;
import com.caritas.entity.TablaArticulos;
import com.caritas.entity.Unidad;
import com.caritas.entity.Usuarios;
import com.caritas.facade.MovimientosFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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

@ManagedBean(name = "movimientosController")
@ViewScoped
public class MovimientosController implements Serializable {

    private transient static final Logger LOG = Logger.getLogger(MovimientosController.class.getName());
    private Movimientos current;
    private DataModel items = null;
    @EJB
    private com.caritas.facade.MovimientosFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    //private boolean ditMode = false;
    private ArrayList<TablaArticulos> listaAImprimir;
    private List<Articulos> listaArticulos;
    private Date fechaCaducidadArticulo;
    private Date fechaAlta;
    private boolean programaEnabled = false;
    private boolean sucursalEnabled = false;
    private boolean areaFechaEnabled = false;
    @ManagedProperty(value = "#{movtosDetController}")
    private MovtosDetController movtosDetController;
    private boolean createMode = false;
    //private List<String> selectOneMenuArticulos;
    @ManagedProperty(value = "#{reciboController}")
    private ReciboController reciboController;
    private Date hoy;
    @ManagedProperty(value = "#{foliosEntradaController}")
    private FoliosEntradaController foliosEntradaController;
    private int folioAnterior;
    private short pantallaActual = -1;
    
    // CONSTANTES 
    private final short TIENDAS = 1;
    private final short AMBA = 2;

    public MovimientosController() {
    }

    @PostConstruct
    private void inicio() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String path = request.getRequestURI();

        if (path.endsWith("AltaInvTie.xhtml") || path.endsWith("AltaInvAMBA.xhtml")) {
            prepareAltaInvCreate();
        } else if (path.endsWith("CreateTie.xhtml") || path.endsWith("CreateAMBA.xhtml")) {
            prepareInvCreate();
        } else {
            pantallaNueva();
        }
        
        if (path.endsWith("AltaInvTie.xhtml")
                || path.endsWith("CreateTie.xhtml")) {
            pantallaActual = TIENDAS;
        } else if (path.endsWith("AltaInvAMBA.xhtml")
                || path.endsWith("CreateAMBA.xhtml")) {
            pantallaActual = AMBA;
        } 
    }

    public Movimientos getSelected() {
        if (current == null) {
            current = new Movimientos();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MovimientosFacade getFacade() {
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

    public String limpiaAltaInvTieCreate() {
        return "AltaInvTie?faces-redirect=true";
    }

    public String limpiaAltaInvAMBACreate() {
        return "AltaInvAMBA?faces-redirect=true";
    }

    public String limpiaTieCreate() {
        return "CreateTie?faces-redirect=true";
    }

    public String limpiaAMBACreate() {
        return "CreateAMBA?faces-redirect=true";
    }

    // Este es para dar de alta un Recibo
    public void prepareAltaInvCreate() {
        pantallaNueva();
        createMode = false;
    }

    // Este es para crear un nuevo Mov. que no haya pasado por Recibo
    public void prepareInvCreate() {
        pantallaNueva();
        createMode = true;

        try {
            String folioStr = getFacade().findLastFolioENT();
            folioAnterior = Integer.parseInt(folioStr.trim());
            getSelected().setIDFolio(String.valueOf(folioAnterior + 1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void pantallaNueva() {
        current = new Movimientos();
        GregorianCalendar cal = new GregorianCalendar();
        fechaAlta = cal.getTime();
        fechaCaducidadArticulo = cal.getTime();

        current.setIDArea(new Areas());
        current.setIDPrograma(new Programas());
        current.setIDDonante(new Donantes());
        current.setIDSucursales(new Sucursales());
        listaAImprimir = new ArrayList<TablaArticulos>();
        listaArticulos = new ArrayList<Articulos>();
        listaAImprimir.add(new TablaArticulos("", " ", new Unidad(), 0.0, 0.0, 0.0, 0.0, fechaCaducidadArticulo, 0.0, 0.0, 0.0));
        programaEnabled = false;
        sucursalEnabled = false;
        areaFechaEnabled = false;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Movimientos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Movimientos();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Movimientos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void editRowAltaInvTie() {
        getFacade().edit(current);
    }

    public String destroy() {
        current = (Movimientos) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosDeleted"));
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

    public SelectItem[] getItemsAvailableSelectMovtos(String status) {
        return JsfUtil.getSelectItems(ejbFacade.findMovimientos(status), false);
    }

    public SelectItem[] getItemsAvailableSelectRecibos() {
        String status = "RECIBO";
        return JsfUtil.getSelectItems(ejbFacade.findRecibos(status, pantallaActual), false);
    }

    public SelectItem[] getItemsAvailableSelectAreas() {
        return JsfUtil.getSelectItems(ejbFacade.findAreas(), false);
    }

    public SelectItem[] getItemsAvailableSelectProgramas() {
        return JsfUtil.getSelectItems(ejbFacade.findProgramas(current.getIDArea()), false);
    }

    public SelectItem[] getItemsAvailableSelectDonantesTiendas() {
        return JsfUtil.getSelectItems(ejbFacade.findDonantesTiendas(), false);
    }

    public SelectItem[] getItemsAvailableSelectAllDonantes() {
        return JsfUtil.getSelectItems(ejbFacade.findAllDonantes(), false);
    }

    public SelectItem[] getItemsAvailableSelectSucursales() {
        return JsfUtil.getSelectItems(ejbFacade.findSucursales(current.getIDDonante()), false);
    }

    public void createRowRecibo() {
        getFacade().create(current);
        // Si no es modo create, es porque se crea uno nuevo, por lo tanto 
        // no es necesario modificar ningún recibo
        if (!createMode) {
            reciboController.updateFromMovtos(current.getIDFolio(), current.getStatusMov(), pantallaActual);
        }
    }

    public void createRowMovto_Det(Articulos temp, TablaArticulos itemActual) {
        movtosDetController.createFromMovimientos(temp, itemActual, current);
    }

    public void deleteMovtosDet(String idFolio) {
        movtosDetController.destroyFromMovtos(idFolio);
    }

    public void altaInventario() {
        boolean isOtroFolio = false;
        try {
            if (checkRequireds()) {
                Usuarios elabora = ejbFacade.getUsuarioNombre(JsfUtil.getLoggedUser().getIDUsuario());

                current.setFechaMov(fechaAlta);
                current.setStatusMov("OK");
                current.setFechaSist(new Date());
                current.setIDUsuario(elabora);
                current.setTipoMov("ENT");

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

                    if (current.getIDArea() != null) {
                        if (current.getIDArea().getIDArea() == null) {
                            current.setIDArea(null);
                        }
                    }
                    if (current.getIDPrograma() != null) {
                        if (current.getIDPrograma().getIDPrograma() == null) {
                            current.setIDPrograma(null);
                        }
                    }

                    if (current.getIDSucursales() != null) {
                        if (current.getIDSucursales().getIDSucursales() == null) {
                            current.setIDSucursales(null);
                            current.setIDSucursal(null);
                        }
                    }

                    createRowMovto_Det(temp, itemActual);

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

                if (!isRegistroUnico() && createMode) {
                    // Si no es registro único, entonces se agrega un folio válido
                    String folioStr = getFacade().findLastFolioENT();
                    folioAnterior = Integer.parseInt(folioStr.trim());
                    getSelected().setIDFolio(String.valueOf(folioAnterior + 1));
                    String folio10 = "          ".concat(current.getIDFolio().trim()); // Folio con 10 caracteres, agregando espacios a la izquierda
                    folio10 = folio10.substring(folio10.length() - 10, folio10.length()); // Obtenemos 10 caracteres, contando desde la derecha
                    current.setIDFolio(folio10);
                    isOtroFolio = true;
                }

                // Si no hubo errores, se almacena el alta en la BD
                createRowRecibo();

                if (createMode) {
                    getFoliosEntradaController().updateFromMovs(current.getIDFolio(), String.valueOf(folioAnterior));
                }

                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Success"));
                if (isOtroFolio) {
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Success_OtroFolio") + (folioAnterior + 1));
                }

                if (createMode) {
                    prepareInvCreate();
                } else {
                    prepareAltaInvCreate();
                }
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosError_AltaInventario"));
            deleteMovtosDet(current.getIDFolio());
            if (createMode) {
                prepareInvCreate();
            } else {
                pantallaNueva();
            }
        }
    }

    private boolean checkRequireds() {
        boolean esValido = true;

        // Verifica que no esté vacío
        if (getSelected().getIDFolio() != null) {
            if (getSelected().getIDFolio().trim().isEmpty()) {
                // Verifica que, si no está vacío, entonces que no sean espacios solamente
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosRequiredMessage_IDFolio"));
                esValido = false;
            } else if (!getSelected().getIDFolio().trim().matches("[0-9]+")) {
                // Verifica que sea numérico
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosValidatorMessage_folio"));
                esValido = false;
            } else {
                String folio10 = "          ".concat(current.getIDFolio().trim()); // Folio con 10 caracteres, agregando espacios a la izquierda
                folio10 = folio10.substring(folio10.length() - 10, folio10.length()); // Obtenemos 10 caracteres, contando desde la derecha
                current.setIDFolio(folio10); // Le asignamos el nuevo valor al folio del current
            }

            // Verifica que se seleccione donante
            if (current.getIDDonante().getIDDonante() == null) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosRequiredMessage_IDDonante"));

                esValido = false;
            }

            // Si tiene sucursal, verifica que se haya seleccionado 
            if (sucursalEnabled && current.getIDSucursales().getIDSucursales() == null) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosRequiredMessage_Sucursal"));
                esValido = false;
            }

            // Verifica que se seleccione Area
            if (current.getIDArea().getArea() == null || current.getIDArea().getArea().trim().isEmpty()) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosRequiredMessage_IDArea"));
                esValido = false;
            }

            // Se verificará que haya seleccionado programa sólo si ya se seleccionó Area
            if (current.getIDPrograma().getIDPrograma() == null || current.getIDPrograma().getIDPrograma().trim().isEmpty()) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosRequiredMessage_IDPrograma"));
                esValido = false;
            }

            // Verifica que la lista de articulos no esté vacía
            if (listaArticulos.isEmpty()) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosRequiredMessage_ArticulosEmpty"));
                esValido = false;
            }

            if (pantallaActual == AMBA) {
                if (current.getFolioDon().trim().isEmpty()) {
                    JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosRequiredMessage_FolioDonEmpty"));
                    esValido = false;
                }
            }

        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("MovimientosRequiredMessage_IDFolio"));
            esValido = false;
        }

        return esValido;
    }

    private boolean isRegistroUnico() {
        // Si encuentra un folio repetido, entonces no es registro único
        return ejbFacade.findFolioRepetido(
                getSelected().getIDFolio()) > 0 ? false : true;
    }

    public void buscaPorFolio() {

        if (current.getIDFolio() != null) {
            if (current.getIDFolio().trim().isEmpty()) {
                prepareAltaInvCreate();
            } else {

                listaAImprimir = new ArrayList<TablaArticulos>();
                listaArticulos = new ArrayList<Articulos>();
                areaFechaEnabled = true;

                String folio10 = "          ".concat(current.getIDFolio().trim()); // Folio con 10 caracteres, agregando espacios a la izquierda
                folio10 = folio10.substring(folio10.length() - 10, folio10.length()); // Obtenemos 10 caracteres, contando desde la derecha
                current.setIDFolio(folio10); // Le asignamos el nuevo valor al folio del current

                current = getFacade().findByFolio(current.getIDFolio(), pantallaActual);

                if (current.getIDFolio() != null) {

                    List<MovtosDet> tablaArts = getFacade().findMovtoDetByFolio(current.getIDFolio());

                    for (MovtosDet temp : tablaArts) {
                        listaArticulos.add(temp.getIDArticulo());
                    }

                    for (Articulos tempArticulo : listaArticulos) {
                        int indice = listaArticulos.indexOf(tempArticulo);

                        MovtosDet tempMovtosDet = tablaArts.get(indice);

                        Double cantidad = tempMovtosDet.getCantidad();

                        listaAImprimir.add(new TablaArticulos(tempArticulo.getIDArticulo(), tempArticulo.getArticulo(), tempArticulo.getUnidadMed1(),
                                cantidad, tempArticulo.getPeso(), tempArticulo.getPeso() * cantidad, tempArticulo.getCostoBenef1(), tempMovtosDet.getFechaCad(),
                                tempArticulo.getCostoBenef1() * cantidad, tempArticulo.getCuotaRecup1(), 0.0));
                    }

                    listaAImprimir.add(new TablaArticulos("", " ", new Unidad(), 0.0, 0.0, 0.0, 0.0, getFechaCaducidadArticulo(), 0.0, 0.0, 0.0));

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
                } else {
                    prepareAltaInvCreate();
                }
            }
        } else {
            prepareAltaInvCreate();
        }
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
    }

    public void viewIDSucursales(String sucursal) {
        if (current.getIDSucursales() != null) {

            // Se verifica que esté o no seleccionado "Seleccione uno"
            if (!current.getIDSucursales().getIDSucursal().equals(" ")) {

                // Establece el objeto de sucursales dependiendo la sucursal y el donante seleccionado
                current.setIDSucursales(ejbFacade.findIDSucursales(sucursal, current.getIDDonante()));

                // EL antiguo sistema solo permite 10 caracteres para el nombre de IDSucursal
                if (current.getIDSucursales().getIDSucursal().length() > 10) {
                    String sucursal10 = current.getIDSucursales().getIDSucursal();
                    sucursal10 = sucursal10.substring(0, 9);
                    current.setIDSucursal(sucursal10);
                } else {
                    current.setIDSucursal(current.getIDSucursales().getIDSucursal());
                }
                // Se guarda este campo para uso del antiguo sistema SIBAAC

            } else {
                current.setIDSucursales(new Sucursales());
            }
        }
    }

    /*
     * Función para filtrar movimientos dependiendo una cadena dada
     *
     * @param query La cadena para filtrar
     * @return La lista de resultados del query
     */
//    public List<String> completeFolio(String query) {
//        try {
////            String query10 = "          ".concat(query.trim()); // query con 10 caracteres, agregando espacios a la izquierda
////            query10 = query10.substring(query10.length() - 10, query10.length()); // Obtenemos 10 caracteres, contando desde la derecha
//
//            List<String> results = ejbFacade.findMovimientosLike(query);
//            return results;
//        } catch (Exception e) {
//            return null;
//        }
//    }
    /**
     * Función para filtrar articulos dependiendo una cadena dada
     *
     * @param query La cadena para filtrar
     * @return La lista de resultados del query
     */
    public List<String> completeArts(String query) {
        try {
            List<String> results = ejbFacade.findArticulosLike(query);
            return results;
        } catch (Exception e) {
            return null;
        }
    }

    public void agregarArticulo(TablaArticulos item) {
        if (item != null) {
            int indice = listaAImprimir.indexOf(item);
            TablaArticulos articuloNuevo_tabla;

            if (!item.getDescripcion().trim().isEmpty()) {
                Articulos articuloNuevo = ejbFacade.findArticulos(item.getDescripcion());

                if (indice > listaArticulos.size() - 1) {
                    listaArticulos.add(articuloNuevo);
                } else {
                    listaArticulos.set(indice, articuloNuevo);
                }

                articuloNuevo_tabla = new TablaArticulos(articuloNuevo.getIDArticulo(), articuloNuevo.getArticulo(),
                        articuloNuevo.getUnidadMed1(), 1.0, articuloNuevo.getPeso(), articuloNuevo.getPeso(), articuloNuevo.getCostoBenef1(),
                        fechaCaducidadArticulo, articuloNuevo.getCostoBenef1(), articuloNuevo.getCuotaRecup1(), 0.0);

                listaAImprimir.set(indice, articuloNuevo_tabla);
                if (listaAImprimir.size() == listaArticulos.size()) {
                    listaAImprimir.add(new TablaArticulos("", " ", new Unidad(), 0.0, 0.0, 0.0, 0.0, fechaCaducidadArticulo, 0.0, 0.0, 0.0));
                }

            } else {
                articuloNuevo_tabla = new TablaArticulos("", " ", new Unidad(), 0.0, 0.0, 0.0, 0.0, fechaCaducidadArticulo, 0.0, 0.0, 0.0);

                listaAImprimir.set(indice, articuloNuevo_tabla);
                listaArticulos.set(indice, new Articulos());
            }
        }
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

    public void eliminarFila(TablaArticulos item) {
        if (item != null) {
            int indice = listaAImprimir.indexOf(item);

            if (indice != listaAImprimir.size() - 1) {
                listaAImprimir.remove(indice);
                listaArticulos.remove(indice);
            }
        }
    }

    private void createRowMovtos_Det(Articulos temp, TablaArticulos itemActual) {
        movtosDetController.createFromMovimientos(temp, itemActual, current);
    }

    public ArrayList<TablaArticulos> getListaAImprimir() {
        return listaAImprimir;
    }

    public Date getFechaCaducidadArticulo() {
        return fechaCaducidadArticulo;
    }

    public void setFechaCaducidadArticulo(Date fechaCaducidadArticulo) {
        this.fechaCaducidadArticulo = fechaCaducidadArticulo;
    }

    public boolean isProgramaEnabled() {
        return programaEnabled;
    }

    public boolean isSucursalEnabled() {
        return sucursalEnabled;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public MovtosDetController getMovtosDetController() {
        return movtosDetController;
    }

    public void setMovtosDetController(MovtosDetController movtosDetController) {
        this.movtosDetController = movtosDetController;
    }

    public boolean isCreateMode() {
        return createMode;
    }

    public ReciboController getReciboController() {
        return reciboController;
    }

    public void setReciboController(ReciboController reciboController) {
        this.reciboController = reciboController;
    }

    public boolean isAreaFechaEnabled() {
        return areaFechaEnabled;
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
    
    @FacesConverter(forClass = Movimientos.class)
    public static class MovimientosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MovimientosController controller = (MovimientosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "movimientosController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Movimientos) {
                Movimientos o = (Movimientos) object;
                return getStringKey(o.getIDMovimientos());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + MovimientosController.class.getName());
            }
        }
    }
}
