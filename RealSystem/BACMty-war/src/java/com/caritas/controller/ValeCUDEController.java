package com.caritas.controller;

import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.PaginationHelper;
import com.caritas.controller.abstractController.CrudController;
import com.caritas.controller.util.pdf.ValeCudePdf;
import com.caritas.entity.Donantes;
import com.caritas.entity.Sucursales;
import com.caritas.entity.ValeCUDE;
import com.caritas.facade.ValeCUDEFacade;
import com.itextpdf.text.DocumentException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.commons.io.IOUtils;
import org.primefaces.util.Constants;

/**
 *
 * @author software
 */
@ManagedBean(name = "valeCUDEController")
@SessionScoped
public class ValeCUDEController implements Serializable, CrudController {

    private static transient final Logger LOG = Logger.getLogger(ValeCUDEController.class.getName());
    private DataModel items = null;
    @EJB
    private com.caritas.facade.ValeCUDEFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private boolean direccionEnabled = false;
    private boolean sucursalEnabled = false;
    private boolean agregarEnabled = false;
    private boolean viewAgregarEnabled = false;
    private boolean pantallaView = false;
    private String direccion;
    private String colonia;
    private String ciudad;
    private List<ValeCUDE> paginationList = null;
    //private ArrayList<ValeCUDE> currentTempList;
    //private ValeCUDE_Tabla selectedRowTemp;
    private ArrayList<ValeCUDE_Tabla> listaAImprimir = new ArrayList<ValeCUDE_Tabla>();
    private Date fecha;
    private String direccionAImprimir;
    private String donanteAImprimir;
    private String sucursalAImprimir;
    private ValeCUDE current;
    //private boolean esFolioVacio = true;

    /**
     *
     */
    public ValeCUDEController() {
        inicio();
    }

    private void inicio() {
        limpiaImprimir();
    }

    public void exportCude() throws DocumentException, IOException {
        List<ValeCUDE> vales;
        if (paginationList.size() < 2000) {
            vales = new ArrayList<ValeCUDE>(paginationList);
            Collections.sort(vales, new FolioComparator());
        } else {
            vales = paginationList;
        }
        ValeCudePdf vcp = new ValeCudePdf(vales);
        JsfUtil.downloadFile("Control" + ".pdf", vcp.createPdf());
    }

    /**
     *
     * @return
     */
    public ValeCUDE getSelected() {
        if (getCurrent() == null) {
            setCurrent(new ValeCUDE());
            selectedItemIndex = -1;
        }
        return getCurrent();
    }

    private ValeCUDEFacade getFacade() {
        return ejbFacade;
    }

    /**
     *
     * @return
     */
    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(getFacade().count()) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    paginationList = getFacade().findRangeOrderByFecha(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()});
                    return new ListDataModel(paginationList);
                }
            };
        }
        return pagination;
    }

    /**
     *
     * @return
     */
    public String prepareList() {
        recreateModel();
        return "List";
    }

    /**
     *
     * @return
     */
    public String prepareImprimir() {
        Calendar cal = new GregorianCalendar();
        fecha = cal.getTime();

        pantallaNueva();
        pantallaView = false;

        return "Imprimir";
    }

    private void pantallaNueva() {
        setCurrent(new ValeCUDE());
        getCurrent().setIDDonante(new Donantes());
        getCurrent().setIDSucursales(new Sucursales());
        agregarEnabled = false;
        direccionEnabled = false;
        sucursalEnabled = false;
        direccion = "";
        colonia = "";
        ciudad = "";
    }

    /**
     *
     * @return
     */
    public String prepareView() {
        pantallaNueva();
        pantallaView = true;
        return "View";
    }

    /**
     *
     * @return
     */
    public String limpiaImprimir() {
        limpiaTabla();
        return prepareImprimir();
    }

    public String limpiaView() {
        fecha = null;
        viewAgregarEnabled = false;
        limpiaTabla();
        return prepareView();
    }

    /**
     *
     * @return
     */
    public String prepareCreate() {
        setCurrent(new ValeCUDE());
        selectedItemIndex = -1;
        return "Create";
    }

    /**
     *
     * @return
     */
    public String create() {
        try {
            getFacade().create(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ValeCUDECreated"));
            recreateModel();
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *
     * @return
     */
    public String prepareEdit() {
        setCurrent((ValeCUDE) getItems().getRowData());
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *
     * @return
     */
    public void Imprimir() {
        if (getListaAImprimir().isEmpty()) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ValeCUDEEmpty_Actuales"));
        } else {

            try {
                ordenaListas();
                URL stream = this.getClass().getResource("/com/caritas/reporte/jasperReports/ValeCUDE1.jrxml");
                String source = stream.getFile();
                JasperReport jasperReport = JasperCompileManager.compileReport(source);

                JasperPrint jasperPrint = new JasperPrint();

                // Itera la lista de currents
                for (int j = 0; j < listaAImprimir.size(); j += 2) {
                    // Mapa de valores para pasar al reporteador.
                    Map params = new HashMap<String, Object>();

                    // Objetos temporales para agregar los parámetros
                    ValeCUDE_Tabla listaAImprimir_Temp1 = listaAImprimir.get(j);
                    ValeCUDE_Tabla listaAImprimir_Temp2;

                    if (j + 1 < listaAImprimir.size()) {
                        listaAImprimir_Temp2 = listaAImprimir.get(j + 1);
                    } else {
                        // Si son una cantidad impar de valores entonces se insertan valores en blanco a la última variable temporal par
                        listaAImprimir_Temp2 = new ValeCUDE_Tabla(" ", " ", " ", " ", " ", " ", " ", false);
                    }

                    // Parámetros del donante con número impar
                    params.put("Donante1", listaAImprimir_Temp1.getDonanteAImprimir());
                    if (listaAImprimir_Temp1.getSucursalAImprimir() != null) {
                        params.put("Sucursal1", listaAImprimir_Temp1.getSucursalAImprimir());
                    } else {
                        params.put("Sucursal1", " ");
                    }
                    params.put("Fecha1", listaAImprimir_Temp1.getFechaAImprimir());
                    if (listaAImprimir_Temp1.isDireccionEnabled()) {
                        params.put("Direccion1", listaAImprimir_Temp1.getDireccionAImprimir());
                        params.put("Colonia1", listaAImprimir_Temp1.getColoniaAImprimir());
                        params.put("Ciudad1", listaAImprimir_Temp1.getCiudadAImprimir());
                    } else {
                        params.put("Direccion1", " ");
                        params.put("Colonia1", " ");
                        params.put("Ciudad1", " ");
                    }

                    // Parámetros del donante con número "par" (puede no haber)
                    params.put("Donante2", listaAImprimir_Temp2.getDonanteAImprimir());
                    if (listaAImprimir_Temp2.getSucursalAImprimir() != null) {
                        params.put("Sucursal2", listaAImprimir_Temp2.getSucursalAImprimir());
                    } else {
                        params.put("Sucursal2", " ");
                    }
                    params.put("Fecha2", listaAImprimir_Temp2.getFechaAImprimir());
                    if (listaAImprimir_Temp2.isDireccionEnabled()) {
                        params.put("Direccion2", listaAImprimir_Temp2.getDireccionAImprimir());
                        params.put("Colonia2", listaAImprimir_Temp2.getColoniaAImprimir());
                        params.put("Ciudad2", listaAImprimir_Temp2.getCiudadAImprimir());
                    } else {
                        params.put("Direccion2", " ");
                        params.put("Colonia2", " ");
                        params.put("Ciudad2", " ");
                    }

                    if (j == 0) {
                        // Aquí se agrega la primer página del reporte
                        jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
                    } else {
                        // Aqui se llama la función que agrega al reporte los datos que siguen
                        agregaPagina(params, jasperPrint, jasperReport);
                    }

                } // End for

                FacesContext fc = FacesContext.getCurrentInstance();
                ExternalContext ec = fc.getExternalContext();

                String pdfName = "donante_fecha";
                String pdfPath = ec.getRealPath("/" + pdfName);
                JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);

                ec.responseReset();
                ec.setResponseContentType(ec.getMimeType(pdfPath));
                ec.setResponseHeader("Content-Disposition", "inline; filename=" + pdfName + ".pdf");
                ec.setResponseHeader("Expires", "0");
                ec.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                ec.setResponseHeader("Pragma", "public");
                ec.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", new HashMap<String, Object>());
                ec.responseFlushBuffer();

                InputStream input = new FileInputStream(pdfPath);
                OutputStream output = ec.getResponseOutputStream();
                IOUtils.copy(input, output);

                fc.responseComplete();

                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Success"));

            } catch (JRException exJre) {
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ValeCUDECancel_Imprimir"));
            } catch (Exception e) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ValeCUDEError_Imprimir"));
            }
        }
    }

    private void writePDFToResponse(ExternalContext externalContext, byte[] baos) {
        try {
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Expires", "0");
            externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            externalContext.setResponseHeader("Pragma", "public");
            String fileName = "donantes/fechas";
            externalContext.setResponseHeader("Content-disposition", "inline;filename=" + fileName + ".pdf");
            externalContext.setResponseContentLength(baos.length);
            externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", new HashMap<String, Object>());
            OutputStream out = externalContext.getResponseOutputStream();
            //baos.
            externalContext.responseFlushBuffer();
        } catch (IOException e) {
        }
    }

    private void ordenaListas() {
        // Ordena la lista para que se GUARDEN en la BD en orden por # de folio  >>> OLD
//        Collections.sort(currentTempList, new Comparator<ValeCUDE>() {
//            @Override
//            public int compare(ValeCUDE v1, ValeCUDE v2) {
//                return v1.getFolio().compareToIgnoreCase(v2.getFolio());
//            }
//        });

        // Ordena la lista para que se IMPRIMAN en orden por # de folio
        Collections.sort(listaAImprimir, new Comparator<ValeCUDE_Tabla>() {
            @Override
            public int compare(ValeCUDE_Tabla v1, ValeCUDE_Tabla v2) {
                return v1.getFolioAImprimir().compareToIgnoreCase(v2.getFolioAImprimir());
            }
        });
    }

    private void agregaPagina(Map parametros, JasperPrint jasperPrint, JasperReport jasperReport) throws JRException {
        JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

        List pages = jp.getPages();

        JRPrintPage paginaNueva = (JRPrintPage) pages.get(0);
        jasperPrint.addPage(paginaNueva);
    }

    private void limpiaTabla() {
//        currentTempList = new ArrayList<ValeCUDE>(); >>> OLD
        listaAImprimir = new ArrayList<ValeCUDE_Tabla>();
    }

    /**
     *
     * @return
     */
    public String update() {
        try {
            getFacade().edit(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ValeCUDEUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *
     * @return
     */
    public String destroy() {
        setCurrent((ValeCUDE) getItems().getRowData());
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    /**
     *
     * @return
     */
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
            getFacade().remove(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ValeCUDEDeleted"));
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
            setCurrent(getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0));
        }
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @return
     */
    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    /**
     *
     * @return
     */
    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    /**
     *
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *
     * @return
     */
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    /**
     *
     * @return
     */
    public SelectItem[] getItemsAvailableSelectDonantes() {
        return JsfUtil.getSelectItems(ejbFacade.findDonantes(), false);
    }

    /**
     *
     * @return
     */
    public SelectItem[] getItemsAvailableSelectSucursales() {
        return JsfUtil.getSelectItems(ejbFacade.findSucursales(getCurrent().getIDDonante()), false);
    }

    //    public void esCadenaVacia(){
    //        if (current.getFolio().isEmpty()) {
    //            esFolioVacio = true;
    //        } else {
    //            esFolioVacio = false;
    //        }
    //    }
    /**
     *
     * @return
     */
    public String viewIDDonante() {
        if (getCurrent().getIDDonante() != null) {

            // Se verifica que el select no sea el "Seleccione uno"
            if (!current.getIDDonante().getDonante().equals(" ")) {
                // Obtenemos los donantes referenciados al nombre seleccionado
                getCurrent().setIDDonante(ejbFacade.findIDDonante(getCurrent().getIDDonante().getDonante()));

                // Se habilita o deshabilita sucursales dependiendo si el donante tiene o no sucursales
                sucursalEnabled = getCurrent().getIDDonante().getSucursales();

                // Si no tiene sucursales, la direccion se obtiene de el objeto de donante
                // Si sí entonces se obtiene de la sucursal seleccionada
                if (!sucursalEnabled) {
                    direccion = getCurrent().getIDDonante().getDireccion();
                    colonia = getCurrent().getIDDonante().getColonia();
                    ciudad = getCurrent().getIDDonante().getCiudad();
                    agregarEnabled = true;
                } else {
                    // Como aun no se ha seleccionado sucursal, se establecen cadenas vacías
                    getCurrent().setIDSucursales(new Sucursales());
                    // Aquí se establece el valor de el primer item del selectOne de sucursales
                    //                    current.getIDSucursales().setIDSucursal(" ");
                    direccion = "";
                    colonia = "";
                    ciudad = "";
                    agregarEnabled = false;
                }
            } else {
                getCurrent().setIDDonante(new Donantes());
                getCurrent().setIDSucursales(new Sucursales());
                //                current.getIDSucursales().setIDSucursal(" ");
                sucursalEnabled = false;
                direccion = "";
                colonia = "";
                ciudad = "";
                agregarEnabled = false;
            }
        } else {
            sucursalEnabled = false;
            agregarEnabled = false;
        }

        return "Imprimir";
    }

    /**
     *
     * @param sucursal
     * @return
     */
    public String viewIDSucursal(String sucursal) {
        if (getCurrent().getIDSucursales() != null) {

            // Se verifica que esté o no seleccionado "Seleccione uno"
            if (!current.getIDSucursales().getIDSucursal().equals(" ")) {

                // Establece el objeto de sucursales dependiendo la sucursal y el donante seleccionado
                getCurrent().setIDSucursales(ejbFacade.findIDSucursales(sucursal, getCurrent().getIDDonante()));

                // Se establece la direccion
                direccion = getCurrent().getIDSucursales().getDireccion();
                colonia = getCurrent().getIDSucursales().getColonia();
                ciudad = getCurrent().getIDSucursales().getCiudad();
                agregarEnabled = true;
            } else {
                //sucursalObj = null;
                getCurrent().setIDSucursales(new Sucursales());
                direccion = "";
                colonia = "";
                ciudad = "";
                agregarEnabled = false;
            }
        } else {
            direccion = "";
            colonia = "";
            ciudad = "";
            agregarEnabled = false;
        }

        return "Imprimir";
    }

    /**
     *
     * @return
     */
    public String agregarFila() {
        if (getSelected().getFolio() == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ValeCUDERequiredMessage_folio"));
            return null;
        } else if (getSelected().getFolio().trim().isEmpty()) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ValeCUDERequiredMessage_folio"));
            return null;
        } else if (!getSelected().getFolio().trim().matches("[0-9]*")) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ValeCUDEValidatorMessage_folio"));
            return null;
        }

        if (ejbFacade.findRepetido(getCurrent().getFolio()) > 0) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ValeCUDE_FolioRepetido"));
            return null;
        }

        if (!listaAImprimir.isEmpty()) {
            // Iteramos la lista para verificar que no se repita el folio.
            for (ValeCUDE_Tabla temp : listaAImprimir) {
                if (temp.getFolioAImprimir().equals(getCurrent().getFolio())) {
                    JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ValeCUDE_FolioRepetido"));
                    return null;
                }
            }
        }

        construyeCurrent();
        construyeLista();
        //currentTempList.add(current); >>> OLD
        getFacade().create(getCurrent());

        return pantallaView ? prepareView() : prepareImprimir();
    }

    /**
     * Se crea el registro que se agregará a la base de datos.
     */
    public void construyeCurrent() {
        // Obtiene el nombre del usuario que inició sesión
        String elabora = ejbFacade.getUsuarioNombre(JsfUtil.getLoggedUser().getIDUsuario());

        // Establece la cadena de dirección dependiendo de si se desea agregar o no
        if (direccionEnabled) {
            direccionAImprimir = "Dirección: " + direccion + ". Colonia: " + colonia + ". Ciudad: " + ciudad + ".";
        } else {
            direccionAImprimir = "";
            colonia = "";
            ciudad = "";
        }

        // Obtiene el nombre a mostrar en el vale
        donanteAImprimir = getCurrent().getIDDonante().getDonante();

        getCurrent().setElabora(elabora);
        getCurrent().setFecha(fecha);

        if (sucursalEnabled) {
            // Agrega la sucursal al donante a mostrar en la tabla
            //donanteAImprimir = donanteAImprimir.concat(" - " + current.getIDSucursales().getIDSucursal());
            sucursalAImprimir = getCurrent().getIDSucursales().getIDSucursal();
        } else {
            getCurrent().setIDSucursales(null);
            sucursalAImprimir = null;
        }
    }

    /**
     * Se crea una lista de arreglos para agregar a la lista que usaremos para
     * Imprimir y mostrar temporalmente
     */
    public void construyeLista() {
        DateFormat df = new SimpleDateFormat(ResourceBundle.getBundle("/Bundle").getString("DateFormat"));
        String fechaAImprimir = df.format(fecha);

        if (!direccionEnabled) {
            direccion = "";
            colonia = "";
            ciudad = "";
        }

        ValeCUDE_Tabla camposAMostrar = new ValeCUDE_Tabla(getCurrent().getFolio(), donanteAImprimir, fechaAImprimir,
                direccion, sucursalAImprimir, colonia, ciudad, direccionEnabled);

        listaAImprimir.add(camposAMostrar);
    }

    /**
     * Quita el registro temporal seleccionado
     */
    public String eliminarFila(ValeCUDE_Tabla item) {
        int indice = listaAImprimir.indexOf(item);
        try {
            ValeCUDE itemToRemove = getFacade().findByFolio(item.getFolioAImprimir());

            if (itemToRemove != null) {
                listaAImprimir.remove(indice);
                //        currentTempList.remove(indice); >>> OLD
                getFacade().remove(itemToRemove);
            } else {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ValeCUDEError_FolioYaNoExiste"));
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_Eliminar"));
            return null;
        }
        return pantallaView ? "View" : "Imprimir";
    }

    public String buscaPorFecha() {
        limpiaTabla();
        List<ValeCUDE> currentList = getFacade().findByFecha(getFecha());
        direccionEnabled = true;

        if (!currentList.isEmpty()) {

            DateFormat df = new SimpleDateFormat(ResourceBundle.getBundle("/Bundle").getString("DateFormat"));
            String formatedDate = df.format(getFecha());

            for (ValeCUDE temp : currentList) {

                if (temp.getIDSucursales() == null) {
                    temp.setIDSucursales(new Sucursales());
                    direccion = temp.getIDDonante().getDireccion();
                    colonia = temp.getIDDonante().getColonia();
                    ciudad = temp.getIDDonante().getCiudad();
                } else {
                    direccion = temp.getIDSucursales().getDireccion();
                    colonia = temp.getIDSucursales().getColonia();
                    ciudad = temp.getIDSucursales().getCiudad();
                }

                ValeCUDE_Tabla tablaTemp = new ValeCUDE_Tabla(temp.getFolio(), temp.getIDDonante().getDonante(), formatedDate,
                        direccion, temp.getIDSucursales().getIDSucursal(), colonia, ciudad, direccionEnabled);

                listaAImprimir.add(tablaTemp);
            }
            viewAgregarEnabled = true;
        } else {
            viewAgregarEnabled = false;
            return limpiaView();
        }

        direccionEnabled = false;
        return "View";
    }

    public boolean viewAgregarEnabled() {
        if (agregarEnabled && viewAgregarEnabled) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return
     */
    public String getPage() {
        return "Imprimir";
    }

    /**
     *
     * @return
     */
    public boolean isSucursalEnabled() {
        return sucursalEnabled;
    }

    /**
     *
     * @param sucursalEnabled
     */
    public void setSucursalEnabled(boolean sucursalEnabled) {
        this.sucursalEnabled = sucursalEnabled;
    }

    /**
     *
     * @return
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     *
     * @return
     */
    public String getColonia() {
        return colonia;
    }

    /**
     *
     * @return
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     *
     * @return
     */
//    public List<ValeCUDE> getCurrentTempList() { >>> OLD
//        return Collections.unmodifiableList(currentTempList);
//    }
    /**
     *
     * @return
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     *
     * @return
     */
    public boolean isAgregarEnabled() {
        return agregarEnabled;
    }

    /**
     *
     * @param agregarEnabled
     */
    public void setAgregarEnabled(boolean agregarEnabled) {
        this.agregarEnabled = agregarEnabled;
    }

    /**
     *
     * @return
     */
    public boolean isDireccionEnabled() {
        return direccionEnabled;
    }

    /**
     *
     * @param direccionEnabled
     */
    public void setDireccionEnabled(boolean direccionEnabled) {
        this.direccionEnabled = direccionEnabled;
    }

    /**
     *
     * @return
     */
    public String getDireccionAImprimir() {
        return direccionAImprimir;
    }

    /**
     *
     * @return
     */
    public String getDonanteAImprimir() {
        return donanteAImprimir;
    }

    /**
     *
     * @return
     */
    public List<ValeCUDE_Tabla> getListaAImprimir() {
        //return Collections.unmodifiableList(listaAImprimir);
        return listaAImprimir;
    }

    public boolean isViewAgregarEnabled() {
        return viewAgregarEnabled;
    }

    public List<ValeCUDE> getPaginationList() {
        if (paginationList == null) {
            recreatePagination();
        }
        return paginationList;
    }

    public ValeCUDE getCurrent() {
        return current;
    }

    public void setCurrent(ValeCUDE current) {
        this.current = current;
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
    public String destroyNoRow() {
        return destroyAndView();
    }

    /**
     *
     */
    @FacesConverter(forClass = ValeCUDE.class)
    public static class ValeCUDEControllerConverter implements Converter {

        /**
         *
         * @param facesContext
         * @param component
         * @param value
         * @return
         */
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ValeCUDEController controller = (ValeCUDEController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "valeCUDEController");
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

        /**
         *
         * @param facesContext
         * @param component
         * @param object
         * @return
         */
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ValeCUDE) {
                ValeCUDE o = (ValeCUDE) object;
                return getStringKey(o.getIDValeCUDE());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ValeCUDEController.class.getName());
            }
        }
    }

    private class FolioComparator implements Comparator<ValeCUDE> {

        @Override
        public int compare(ValeCUDE o1, ValeCUDE o2) {
            return o1.getFolio().compareTo(o2.getFolio());
        }
    }
}
