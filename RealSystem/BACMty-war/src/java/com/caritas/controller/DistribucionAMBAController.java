package com.caritas.controller;

import com.caritas.entity.DistribucionAMBA;
import com.caritas.controller.util.JsfUtil;
import com.caritas.controller.util.pdf.DistAmbaPdf;
import com.caritas.entity.BancosDeAlimentos;
import com.caritas.entity.DistribucionAMBADet;
import com.caritas.entity.Donantes;
import com.caritas.entity.Movimientos;
import com.caritas.entity.Sucursales;
import com.caritas.facade.DistribucionAMBAFacade;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
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

@ManagedBean(name = "distribucionAMBAController")
@ViewScoped
public class DistribucionAMBAController implements Serializable {

    private DistribucionAMBA current;
    @EJB
    private com.caritas.facade.DistribucionAMBAFacade ejbFacade;
    private ArrayList<DistribucionAMBA_Tabla> items;
    private boolean folioSelected;
    private boolean nuevaDistribucion;
    private List<BancosDeAlimentos> bancosDeAlimentos;
    @ManagedProperty(value = "#{distribucionAMBADetController}")
    private DistribucionAMBADetController distribucionAMBADetController;

    public DistribucionAMBAController() {
    }

    @PostConstruct
    public void Inicio() {
        prepareDistribucionAMBA();
    }

    private void prepareDistribucionAMBA() {
        current = null;
        getSelected().setIDDonante(new Donantes());
        getSelected().setIDSucursales(new Sucursales());

        GregorianCalendar cal = new GregorianCalendar();
        getSelected().setFechaMov(cal.getTime());

        setBancosDeAlimentos(getFacade().findBancosDeAlimentos());

        folioSelected = false;
        nuevaDistribucion = false;
    }

    private void NewItems() {
        getSelected().setTotalPoblacion(0);
        getSelected().setTotalKilogramos(0.0);
        getSelected().setTotalPiezas(0);
        getSelected().setTotalFlete(0.0);

        items = new ArrayList<DistribucionAMBA_Tabla>();
        // Recorremos toda la lista de bancos de alimentos
        for (BancosDeAlimentos banco : bancosDeAlimentos) {

            // Por cada banco de alimentos creamos un item con
            // su nombre, población y valores predeterminados
            DistribucionAMBA_Tabla item =
                    new DistribucionAMBA_Tabla(false, "", banco.getNombre(), banco.getPoblacion(), 0.0, 0.0, 0, 0.0, "");

            // Agregamos la distribución a la lista que se mostrará en la Tabla
            getItems().add(item);
        }
    }

    private void RecreateItems() {
        items = new ArrayList<DistribucionAMBA_Tabla>();

        List<DistribucionAMBADet> detalles =
                getFacade().findDetalle(getSelected().getIDFolio());

        for (DistribucionAMBADet detalle : detalles) {

            DistribucionAMBA_Tabla item = new DistribucionAMBA_Tabla(
                    detalle.getRecibe(), detalle.getRemision(),
                    detalle.getBancosDeAlimentos().getNombre(),
                    detalle.getBancosDeAlimentos().getPoblacion(),
                    0.0, 0.0, 0,
                    detalle.getFlete(), detalle.getObservaciones());

            items.add(item);
        }

        CalculaPorcentajes();
    }

    public DistribucionAMBA getSelected() {
        if (current == null) {
            current = new DistribucionAMBA();
            current.setIDFolio("");
            getSelected().setCedisOrigen("");
        }
        return current;
    }

    private DistribucionAMBAFacade getFacade() {
        return ejbFacade;
    }

    public void create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DistribucionAMBACreated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DistribucionAMBAUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    public void destroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DistribucionAMBARemoved"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

//    public List<String> CompleteFolios(String query) {
//        try {
//            List<String> results = getFacade().findFoliosEntAmbaLike(query);
//            return results;
//        } catch (Exception e) {
//            return null;
//        }
//    }
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public DistribucionAMBA getDistribucionAMBA(java.lang.String id) {
        return ejbFacade.find(id);
    }
    
    public void IDFolioChange() {
        String auxFolio = getSelected().getIDFolio();
        
        prepareDistribucionAMBA();
        NewItems();
        
        getSelected().setIDFolio(auxFolio);
        
        if (!getSelected().getIDFolio().trim().isEmpty()) {
            if (getSelected().getIDFolio().length() < 10) {
                String folio10 = "          ".concat(getSelected().getIDFolio().trim());
                folio10 = folio10.substring(folio10.length() - 10, folio10.length());
                getSelected().setIDFolio(folio10);
            }

            DistribucionAMBA currentTemp = getFacade().find(getSelected().getIDFolio());

            // Si no existe la distribucion
            // Se creará una nueva en la BD
            if (currentTemp == null) {
                Movimientos movEntradaAMBA = ejbFacade.findMovimientoEntradaAMBA(getSelected().getIDFolio());
                if (movEntradaAMBA.getIDFolio() != null) {

                    nuevaDistribucion = true;

                    folioSelected = true;

                    getSelected().setTotalKilogramos(getFacade().findKilosMovtosDet(movEntradaAMBA.getIDFolio()));
                    getSelected().setTotalPiezas(getFacade().findPiezasMovtosDet(movEntradaAMBA.getIDFolio()));

                    getSelected().setIDDonante(movEntradaAMBA.getIDDonante());
                    if (movEntradaAMBA.getIDDonante().getSucursales()) {
                        getSelected().setIDSucursales(movEntradaAMBA.getIDSucursales());
                    } else {
                        getSelected().setIDSucursales(null);
                    }
                    
                } else {
                    JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("DistribucionAMBAError_FolioNoExiste"));
                    prepareDistribucionAMBA();
                }
            } else {
                current = getFacade().find(current.getIDFolio());
                nuevaDistribucion = false;
                RecreateItems();
                folioSelected = true;
            }
        }
//        else {
//            prepareDistribucionAMBA();
//        }
    }

    public void GuardarDistribucion() {
        if (getSelected().getIDFolio() != null) {
            if (!getSelected().getIDFolio().trim().isEmpty()) {
                try {
                    if (isNuevaDistribucion()) {
                        for (DistribucionAMBA_Tabla item : items) {
                            distribucionAMBADetController.createFromDistAmba(
                                    getSelected().getIDFolio(), item);
                        }
                        create();
//                        prepareDistribucionAMBA();
//                        NewItems();
                    } else {
                        for (DistribucionAMBA_Tabla item : items) {
                            distribucionAMBADetController.updateFromDistAmba(
                                    getSelected().getIDFolio(), item);
                        }
                        update();
//                        prepareDistribucionAMBA();
//                        NewItems();
                    }
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("Error_Generico"));
                }
            } else {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("DistribucionAMBARequiredMessage_Folio"));
            }
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("DistribucionAMBARequiredMessage_Folio"));
        }
    }
    
    public void EliminarDistribucion() {
        for (DistribucionAMBA_Tabla item : items) {
            distribucionAMBADetController.destroyFromDistAmba(getSelected().getIDFolio(), item);
        }
        destroy();
        
        items = null;
        prepareDistribucionAMBA();
    }

    public void exportar() throws IOException, DocumentException {
        if (!getTotalPorcentaje().equals("0.0 %")) {

            ByteArrayOutputStream createPdf = new DistAmbaPdf(items, getReportMap()).createPdf();

            JsfUtil.downloadFile("Dist_Amba_" + getFechaString() + "_(" + getSelected().getIDFolio().trim() + ").pdf", createPdf);
            GuardarDistribucion();
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("DistribucionAMBARequiredMessage_NoSeleccionados"));
        }
    }

    private HashMap<String, Object> getReportMap() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("FOLIO", getSelected().getIDFolio().trim());
        map.put("CEDIS", getSelected().getCedisOrigen());
        map.put("DONANTE", getSelected().getIDDonante().getDonante());
        if (getSelected().getIDDonante().getSucursales()) {
            // Se agrega la sucursal solo si tiene
            map.put("SUCURSAL", getSelected().getIDSucursales().getIDSucursal());
        }
        map.put("FECHA", getFechaString());
        map.put("POBLACION", getSelected().getTotalPoblacion());
        map.put("PORCENTAJE", getTotalPorcentaje());
        map.put("KILOGRAMOS", getKilosTotalesString());
        map.put("PIEZAS", getSelected().getTotalPiezas());
        map.put("FLETES", getFletesTotalesString());

        return map;
    }

    public void CalculaItems() {
        CalculaPoblacion();
        CalculaFletes();
        CalculaPorcentajes();
    }

    private void CalculaPoblacion() {
        getSelected().setTotalPoblacion(0);

        for (DistribucionAMBA_Tabla item : items) {
            if (item.getRecibe()) {
                getSelected().setTotalPoblacion(
                        getSelected().getTotalPoblacion()
                        + item.getPoblacion());
            }
        }
    }

    public void CalculaFletes() {
        getSelected().setTotalFlete(0.0);

        for (DistribucionAMBA_Tabla item : items) {
            if (item.getRecibe()) {
                getSelected().setTotalFlete(
                        getSelected().getTotalFlete()
                        + item.getFlete());
            } else {
                item.setFlete(0.0);
            }
        }
    }

    public void CalculaPorcentajes() {
        double unPorcientoPoblacion = getSelected().getTotalPoblacionDouble() / 100.0;

        for (DistribucionAMBA_Tabla item : items) {
            if (item.getRecibe()) {
                item.setPorcentaje(item.getPoblacion() / unPorcientoPoblacion);
                item.setKilogramos(getSelected().getTotalKilogramos() * item.getPorcentaje() / 100);
                item.setPiezasDouble(getSelected().getTotalPiezas() * item.getPorcentaje() / 100);
            } else {
                item.setPorcentaje(0.0);
                item.setKilogramos(0.0);
                item.setPiezas(0);
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="SETTERS & GETTERS">
    public List<DistribucionAMBA_Tabla> getItems() {
        return items;
    }

    public void setItems(ArrayList<DistribucionAMBA_Tabla> items) {
        this.items = items;
    }

    public void setBancosDeAlimentos(List<BancosDeAlimentos> bancosDeAlimentos) {
        this.bancosDeAlimentos = bancosDeAlimentos;
    }

    public boolean isFolioSelected() {
        return folioSelected;
    }

    public DistribucionAMBADetController getDistribucionAMBADetController() {
        return distribucionAMBADetController;
    }

    public void setDistribucionAMBADetController(DistribucionAMBADetController distribucionAMBADetController) {
        this.distribucionAMBADetController = distribucionAMBADetController;
    }

    public String getTotalPorcentaje() {
        for (DistribucionAMBA_Tabla item : items) {
            if (item.getRecibe()) {
                return "100.0 %";
            }
        }

        return "0.0 %";
    }

    public String getKilosTotalesString() {
        BigDecimal big = new BigDecimal(getSelected().getTotalKilogramos());
        big = big.setScale(2, RoundingMode.HALF_UP);

        return big.toString();
    }

    public String getFletesTotalesString() {
        if (getSelected().getTotalFlete() == 0.0) {
            return "$ - ";
        } else {
            BigDecimal big = new BigDecimal(getSelected().getTotalFlete());
            big = big.setScale(2, RoundingMode.HALF_UP);

            return "$ " + big.toString();
        }
    }

    public String getFechaString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        return df.format(getSelected().getFechaMov());
    }
    
    public boolean isNuevaDistribucion() {
        return nuevaDistribucion;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Converter">
    @FacesConverter(forClass = DistribucionAMBA.class)
    public static class DistribucionAMBAControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DistribucionAMBAController controller = (DistribucionAMBAController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "distribucionAMBAController");
            return controller.getDistribucionAMBA(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof DistribucionAMBA) {
                DistribucionAMBA o = (DistribucionAMBA) object;
                return getStringKey(o.getIDFolio());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + DistribucionAMBA.class.getName());
            }
        }
    }
    //</editor-fold>
}
