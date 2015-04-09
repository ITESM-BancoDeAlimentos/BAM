package com.caritas.controller;

import com.caritas.controller.util.JsfUtil;
import com.caritas.entity.EncSocioNutricia;
import com.caritas.entity.EncSocioNutriciaInd;
import com.caritas.entity.EncSocioNutriciaPK;
import com.caritas.entity.Instituciones;
import com.caritas.facade.EncSocioNutriciaFacade;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@ManagedBean(name = "encSocioNutriciaExpController")
@ViewScoped
public class EncSocioNutriciaExpController implements Serializable {

    private EncSocioNutricia current;
    private List<EncSocioNutricia> currentList;
    @EJB
    private com.caritas.facade.EncSocioNutriciaFacade ejbFacade;
    private List<EncSocioNutriciaInd> integrantes;
    @ManagedProperty(value = "#{encSocioNutriciaFamController}")
    private EncSocioNutriciaFamController famController;
    @ManagedProperty(value = "#{encSocioNutriciaIndController}")
    private EncSocioNutriciaIndController indController;
    @ManagedProperty(value = "#{encSocioNutriciaSegController}")
    private EncSocioNutriciaSegController segController;
    private boolean idSelected = false;
    private boolean areaGeoSelected = false;
    private Instituciones localidad;
    private Date fechaReporte;
    private int noFamilias = 0;
    private Integer noPaquetes = 0;
    private int noInstituciones;
    private List<EncSocioNutriciaReporteMensual> datos = new ArrayList<EncSocioNutriciaReporteMensual>();
    private List<EncSocioNutriciaReporteMensual> datosPorArea = new ArrayList<EncSocioNutriciaReporteMensual>();
    private List<Instituciones> instituciones = new ArrayList<Instituciones>();

    public EncSocioNutriciaExpController() {
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

    public Date Hoy() {
        return new Date();
    }

    public List<EncSocioNutriciaInd> getIntegrantes() {
        if (integrantes == null) {
            integrantes = new ArrayList<EncSocioNutriciaInd>();
        }
        return integrantes;
    }

    public int getAnios(EncSocioNutriciaInd integrante) {
        return integrante.getAnios();
    }

//<editor-fold defaultstate="collapsed" desc="gets sets ReporteMensual">
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

    public Instituciones getLocalidad() {
        if (localidad == null) {
            localidad = new Instituciones();
        }
        return localidad;
    }

    public void setLocalidad(Instituciones localidad) {
        this.localidad = localidad;
    }

    public Date getFechaReporte() {
        if (fechaReporte == null) {
            fechaReporte = new Date();
        }
        return fechaReporte;
    }

    public void setFechaReporte(Date fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public int getNoFamilias() {
        return noFamilias;
    }

    public void setNoFamilias(int noFamilias) {
        this.noFamilias = noFamilias;
    }

    public Integer getNoPaquetes() {
        return noPaquetes;
    }

    public void setNoPaquetes(Integer noPaquetes) {
        this.noPaquetes = noPaquetes;
    }

    public List<EncSocioNutriciaReporteMensual> getDatos() {
        return datos;
    }

    public void setDatos(List<EncSocioNutriciaReporteMensual> datos) {
        this.datos = datos;
    }

    public List<Instituciones> getInstituciones() {
        return instituciones;
    }

    public void setInstituciones(List<Instituciones> instituciones) {
        this.instituciones = instituciones;
    }

    public int getNoInstituciones() {
        return noInstituciones;
    }

    public void setNoInstituciones(int noInstituciones) {
        this.noInstituciones = noInstituciones;
    }

    public List<EncSocioNutriciaReporteMensual> getDatosPorArea() {
        return datosPorArea;
    }

    public void setDatosPorArea(List<EncSocioNutriciaReporteMensual> datosPorArea) {
        this.datosPorArea = datosPorArea;
    }

    //</editor-fold>
    public void limpiarReporte() {
        datos = new ArrayList<EncSocioNutriciaReporteMensual>();
        datosPorArea = new ArrayList<EncSocioNutriciaReporteMensual>();
        localidad = new Instituciones();
        noFamilias = 0;
        noPaquetes = 0;
    }

    public List<EncSocioNutriciaReporteMensual> reporteMensual() {
        List<EncSocioNutricia> familiasPorLocalidad = ejbFacade.findByInstitucion(localidad,
                current.getEncSocioNutriciaPK().getAreaGeo());
        datos = new ArrayList<EncSocioNutriciaReporteMensual>();
        int totalMiembros = 0;
        for (EncSocioNutricia encSocioNutricia : familiasPorLocalidad) {
            integrantes = indController.getListEncSocioNutriciaInd(
                    encSocioNutricia.getEncSocioNutriciaPK().getExpediente(),
                    encSocioNutricia.getEncSocioNutriciaPK().getAreaGeo());
            totalMiembros += integrantes.size();
        }

        for (EncSocioNutricia encSocioNutricia : familiasPorLocalidad) {

            int[] edadMujeres = {0, 0, 0, 0};
            int[] edadHombres = {0, 0, 0, 0};

            EncSocioNutriciaReporteMensual familia = new EncSocioNutriciaReporteMensual();
            integrantes = indController.getListEncSocioNutriciaInd(
                    encSocioNutricia.getEncSocioNutriciaPK().getExpediente(),
                    encSocioNutricia.getEncSocioNutriciaPK().getAreaGeo());

            familia.setMiembros(integrantes.size());
            familia.setJefeFamilia(encSocioNutricia.getJefeFamilia());
            familia.setComunidad(localidad.getInstitucion());
            familia.setMunicipio(localidad.getCiudad());
            familia.setNoExpediente(encSocioNutricia.getEncSocioNutriciaPK().getExpediente());
            familia.setTotalMiembros(totalMiembros);
            for (int i = 0; i < integrantes.size(); i++) {
                int edad = getAnios(integrantes.get(i));

                if (integrantes.get(i).getGenero().compareTo("F") == 0) {
                    if (edad < 13) {
                        edadMujeres[0]++;
                    } else if (edad < 18) {
                        edadMujeres[1]++;
                    } else if (edad < 60) {
                        edadMujeres[2]++;
                    } else {
                        edadMujeres[3]++;
                    }
                } else {
                    if (edad < 13) {
                        edadHombres[0]++;
                    } else if (edad < 18) {
                        edadHombres[1]++;
                    } else if (edad < 60) {
                        edadHombres[2]++;
                    } else {
                        edadHombres[3]++;
                    }
                }
            }
            familia.setEdadHombres(edadHombres);
            familia.setPaquetes(1);
            noPaquetes += familia.getPaquetes();
            familia.setEdadMujeres(edadMujeres);

            datos.add(familia);
            familia.setNoFamilia(familiasPorLocalidad.size());
        }
        setNoFamilias(datos.size());
        return datos;
    }

    public void sumaPaquetes(){
        noPaquetes = 0;
        for (EncSocioNutriciaReporteMensual familia : datos) {
            if (familia.getPaquetes() < 0) {
                familia.setPaquetes(0);
            }
            noPaquetes += familia.getPaquetes();
        }
    }

    public void reporteMensualInstituciones() {
        limpiarReporte();

        instituciones = ejbFacade.findInstitucionByAreaGeo(
                current.getEncSocioNutriciaPK().getAreaGeo());
        for (Instituciones institucion : instituciones) {
            localidad = institucion;
            datosPorArea.addAll(reporteMensual());
        }
        setNoInstituciones(instituciones.size());
        datos = new ArrayList<EncSocioNutriciaReporteMensual>();
        datos.addAll(datosPorArea);
        setNoFamilias(datos.size());
        noPaquetes = null;
        for (EncSocioNutriciaReporteMensual familia : datos) {
            familia.setPaquetes(null);
        }
    }

    public HashMap parametros() throws IOException {
        String LOGO_PATH = "/com/caritas/reporte/jasperReports/balimentos_logo.jpg";
        String LOGO_PATH2 = "/com/caritas/reporte/jasperReports/caritasMonterrey.jpg";
        BufferedImage logoBanco = getLogo(LOGO_PATH);
        BufferedImage logoCaritas = getLogo(LOGO_PATH2);

        HashMap<String, Object> parametro = new HashMap();
        parametro.put("Fecha", fechaReporte);
        parametro.put("LogoBAlimento", logoBanco);
        parametro.put("CaritasMonterrey", logoCaritas);
        parametro.put("noPaquetes", noPaquetes);
        return parametro;
    }

    public BufferedImage getLogo(String LOGO_PATH) throws IOException{    
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(LOGO_PATH);
        if (resourceAsStream == null) {
            resourceAsStream = new ByteArrayInputStream(new byte[0]);
            Logger.getLogger(EncSocioNutriciaExpController.class.getName()).log(Level.SEVERE,
                    "No se pudo cargar imagen {0}", new Object[]{LOGO_PATH});
        }
        return ImageIO.read(resourceAsStream);
    }

    public void PDF() throws JRException, IOException {
        String jasper = "reporteMensual";
        String nombreReporte = "reporteMensual";

        URL stream = this.getClass().getResource("/com/caritas/reporte/jasperReports/reporteMensual.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(datos);
        if (!datos.isEmpty()) {
            JasperPrint jasperPrint;
            jasperPrint = JasperFillManager.fillReport(jasperReport, parametros(), beanCollectionDataSource);
            imprimir(jasper, nombreReporte, jasperPrint);
        }else{
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("EncSocioNutriciaReporte_ListaVacia"));
        }
    }

    public void imprimir(String jasper, String nombreReporte, JasperPrint jasperPrint) throws IOException, JRException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.
                getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/pdf");
        httpServletResponse.setHeader("Content-disposition", "inline;"
                + " filename=" + nombreReporte + ".pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public int edadMeses(Date fechaNacimiento){
        Calendar fecha = new GregorianCalendar();
        fecha.setTime(fechaNacimiento);
        Calendar fechaHoy = Calendar.getInstance();
        int anios = fechaHoy.get(Calendar.YEAR) - fecha.get(Calendar.YEAR);
        int meses = 0;
        for (int i = 0; i < anios; i++) {
            meses +=12;
        }
        meses -= (fecha.get(Calendar.MONTH)+1);
        meses -= (12-(fechaHoy.get(Calendar.MONTH)+1));
        if(meses == 0){
            meses = fechaHoy.get(Calendar.MONTH)-fecha.get(Calendar.MONTH);
            meses += 1;
        }
        return meses;
    }

    public void reporte(){
        
    }

    @FacesConverter(forClass = EncSocioNutricia.class)
    public static class EncSocioNutriciaExpControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EncSocioNutriciaExpController controller = (EncSocioNutriciaExpController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "encSocioNutriciaExpController");
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
