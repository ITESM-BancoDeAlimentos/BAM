package com.caritas.controller;

import com.caritas.controller.util.JsfUtil;
import com.caritas.entity.EncSocioNutriciaInd;
import com.caritas.enums.AreaGeografica;
import com.caritas.facade.EncSocioNutriciaIndFacade;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.StreamedContent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;

/**
 *
 * @author software
 */
@ManagedBean
@ViewScoped
public class CredencialController implements Serializable {

    public static final String PARENTESCO_JEFE = "Jefe de la Familia";
    private static final String NOT_FOUND__EXCEPTION = "Error al buscar EncSocioNutriciaInd: exp: {0}, areaGeo: {1} parentesco: {2}";
    @ManagedProperty("#{impresionCredencial}")
    private ImpresionCredencial ic;
    @EJB
    private EncSocioNutriciaIndFacade esnif;
    @ManagedProperty("#{previewCredencial}")
    private PreviewCredencial preview;
    private int expediente = 0;
    private AreaGeografica areaGeo;
    private EncSocioNutriciaInd jefeInd;

    public void seleccionaJefe() {
        try {
            EncSocioNutriciaInd findByIDEncActivo = esnif.findByIDEncActivo(expediente, areaGeo.getCodigo(), PARENTESCO_JEFE);
            jefeInd = findByIDEncActivo;
        } catch (Exception e) {
            jefeInd = null;
            logException(e);
            JsfUtil.addErrorMessage(e);
        }
        if (jefeInd == null) {
            JsfUtil.addErrorMessage("No se pudo encontrar el expediente. Intente otro");
        }
    }

    public void imprimeCredencial() {
        try {
            if (getPreview() == null) {
                ic.imprimeCredencial(getJefeInd(), new ByteArrayInputStream(getFoto()));
            } else {
                ic.imprimeCredencial(new ByteArrayInputStream(getPreview()));
            }
            JsfUtil.addSuccessMessage("Se esta imprimiendo la credencial.");
        } catch (NullPointerException ex) {
            Logger.getLogger(CredencialController.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage("Se necesita foto");
        } catch (Exception e) {
            logException(e);
            JsfUtil.addErrorMessage(e);
        }
    }

    public void cargaPreview() throws IOException, Exception {
        try {
            byte[] is = ic.generaCredencial(jefeInd, new ByteArrayInputStream(getFoto()));
            preview.cargaPreview(is, "application/pdf");
        } catch (Exception e) {
            logException(e);
            JsfUtil.addErrorMessage("No se pudo generar la credencial.");
            JsfUtil.addErrorMessage(e);
        }
    }

    public void listenFileUpload(FileUploadEvent e) {
        try {
            final UploadedFile file = e.getFile();
            int size = (int) file.getSize();
            byte[] buffer = new byte[size];
            IOUtils.readFully(file.getInputstream(), buffer);
            preview.cargaFoto(buffer, file.getContentType());
        } catch (IOException ex) {
            Logger.getLogger(CredencialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PostConstruct
    private void init() {
        preview.init();
    }

    //<editor-fold defaultstate="collapsed" desc="getset">
    private byte[] getPreview() {
        return preview.getPreviewFile();
    }

    private byte[] getFoto() {
        return preview.getFotoFile();
    }

    public int getExpediente() {
        return expediente;
    }

    public void setExpediente(int expediente) {
        this.expediente = expediente;
    }

    public AreaGeografica getAreaGeo() {
        return areaGeo;
    }

    public void setAreaGeo(AreaGeografica areaGeo) {
        this.areaGeo = areaGeo;
    }

    public EncSocioNutriciaInd getJefeInd() {
        return jefeInd;
    }

    public void setJefeInd(EncSocioNutriciaInd jefeInd) {
        this.jefeInd = jefeInd;
    }

    public void setPreview(PreviewCredencial preview) {
        this.preview = preview;
    }

    public void setIc(ImpresionCredencial ic) {
        this.ic = ic;
    }
    //</editor-fold>

    private void logException(Exception e) {
        Logger.getLogger(CredencialController.class.getCanonicalName()).log(Level.WARNING, null, e);
    }

    @ManagedBean(name = "previewCredencial")
    @SessionScoped
    public static class PreviewCredencial implements Serializable {

        transient private StreamedContent previewStream;
        transient private StreamedContent fotoStream;
        transient private byte[] previewFile;
        transient private String previewType;
        transient private byte[] fotoFile;
        transient private String fotoType;

        private void cargaFoto(byte[] buffer, String contentType) {
            fotoFile = buffer;
            fotoType = contentType;
            fotoStream = new DefaultStreamedContent(new ByteArrayInputStream(fotoFile), fotoType);
        }

        private void cargaPreview(byte[] buffer, String contentType) {
            previewFile = buffer;
            previewType = contentType;
            previewStream = new DefaultStreamedContent(new ByteArrayInputStream(previewFile), previewType);
        }

        private void init() {
            previewStream = null;
            fotoStream = null;
            previewFile = null;
            previewType = null;
            fotoFile = null;
            fotoType = null;
        }

        public StreamedContent getPreviewStream() {
            if (previewFile != null && previewType != null) {
                cargaPreview(previewFile, previewType);
            }
            return previewStream;
        }

        public StreamedContent getFotoStream() {
            if (fotoFile != null && fotoType != null) {
                cargaFoto(fotoFile, fotoType);
            }
            return fotoStream;
        }

        public void setPreviewFile(byte[] previewFile) {
            this.previewFile = previewFile;
        }

        public void setFotoFile(byte[] fotoFile) {
            this.fotoFile = fotoFile;
        }

        public byte[] getFotoFile() {
            return fotoFile;
        }

        public byte[] getPreviewFile() {
            return previewFile;
        }
    }

    @ManagedBean(name = "impresionCredencial")
    @ApplicationScoped
    public static class ImpresionCredencial implements Serializable {

        public static final String REPORT_PATH = "/com/caritas/reporte/jasperReports/credencial.jasper";
        public static final String LOGO_PATH = "/com/caritas/reporte/jasperReports/balimentos_logo.jpg";
        private static final Logger logger = Logger.getLogger(ImpresionCredencial.class.getName());
        private static final SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy");

        public byte[] generaCredencial(EncSocioNutriciaInd jefe, InputStream imagen) throws JRException {
            try {
                Map<String, Object> parameters = getParamsFrom(jefe, imagen);
                InputStream report = getReport();
                JasperReport loadObject = (JasperReport) JRLoader.loadObject(report);
                JasperPrint fillReport = JasperFillManager.fillReport(loadObject, parameters, new JREmptyDataSource());
                return exportPdf(fillReport);
            } catch (JRException ex) {
                Logger.getLogger(ImpresionCredencial.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

        void imprimeCredencial(EncSocioNutriciaInd jefeInd, InputStream inputstream) throws JRException {
            imprimeCredencial(new ByteArrayInputStream(generaCredencial(jefeInd, inputstream)));
        }

        void imprimeCredencial(InputStream stream) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private Map<String, Object> getParamsFrom(EncSocioNutriciaInd jefe, InputStream imagen) {
            Map<String, Object> parameters = new HashMap<String, Object>(5);
            BufferedImage logo = getLogo();
            BufferedImage foto = ToBufferedImage(imagen);
            String nombre = jefe.getEncSocioNutriciaIndPK().getNombre();
            String expediente = String.valueOf(
                    jefe.getEncSocioNutriciaIndPK().getExpediente())
                    + jefe.getEncSocioNutriciaIndPK().getAreaGeo();
            Date fecha = jefe.getEncSocioNutricia().getFechaCaptura();
            String year = sdFormatter.format(fecha);

            parameters.put("nombre", nombre);
            parameters.put("expediente", expediente);
            parameters.put("year", year);
            parameters.put("foto", foto);
            parameters.put("logo", logo);
            return parameters;
        }

        private InputStream getReport() {
            InputStream val = getClass().getClassLoader().getResourceAsStream(REPORT_PATH);
            if (val == null) {
                logger.log(Level.SEVERE, "No se pudo cargar el reporte {0}", new Object[]{REPORT_PATH});
            }
            return val;
        }

        private BufferedImage getLogo() {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(LOGO_PATH);
            if (resourceAsStream == null) {
                resourceAsStream = new ByteArrayInputStream(new byte[0]);
                logger.log(Level.SEVERE, "No se pudo cargar imagen {0}", new Object[]{LOGO_PATH});
            }
            return ToBufferedImage(resourceAsStream);
        }

        private BufferedImage ToBufferedImage(InputStream resourceAsStream) {
            try {
                return ImageIO.read(resourceAsStream);
            } catch (IOException ex) {
                Logger.getLogger(ImpresionCredencial.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                return ImageIO.read(new ByteArrayInputStream(new byte[0]));
            } catch (IOException ex) {
                Logger.getLogger(ImpresionCredencial.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

        private JasperPrint fillReport(InputStream report, Map<String, Object> parameters) throws JRException {
            JasperPrint fillReport = JasperFillManager.fillReport(report, parameters);
            return fillReport;
        }

        private byte[] exportPdf(JasperPrint fillReport) throws JRException {
            return JasperExportManager.exportReportToPdf(fillReport);
        }
    }
}
