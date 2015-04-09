/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.controller;

import com.caritas.controller.util.EncSocioNutriciaUtil;
import com.caritas.controller.util.JsfUtil;
import com.caritas.entity.EncSocioNutricia;
import com.caritas.entity.EncSocioNutriciaFam;
import com.caritas.entity.EncSocioNutriciaFamPK;
import com.caritas.entity.EncSocioNutriciaInd;
import com.caritas.entity.EncSocioNutriciaIndPK;
import com.caritas.entity.EncSocioNutriciaPK;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author software
 */
@ManagedBean
@SessionScoped
public class EncuestasUploadController implements Serializable {

    private UploadedFile file;
    @EJB
    private com.caritas.facade.EncSocioNutriciaFacade ejbFacade;

    @EJB
    private com.caritas.facade.InstitucionesFacade institucionesFacade;

    /**
     * Creates a new instance of EncuestasUploadController
     */
    public EncuestasUploadController() {
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        if (file != null) {
            try {
                System.out.println("Succesful" + file.getFileName() + " is uploaded.");
                HSSFWorkbook workbook = new HSSFWorkbook(file.getInputstream());
                processWorkbook(workbook);
            } catch (IOException ex) {
                JsfUtil.addErrorMessage("Hubo un error en la lectura del archivo. Verifique que sea un archivo Excel (.xls ó .xlsx) válido.");
            }
        }
    }

    private void processWorkbook(HSSFWorkbook workbook) {
        int number_of_sheets = workbook.getNumberOfSheets();
        for (int i = 0; i < number_of_sheets; i++) {
            HSSFSheet sheet = workbook.getSheetAt(i);
            processSheet(sheet);
        }
    }

    private void processSheet(HSSFSheet sheet) {
        int number_of_rows = sheet.getPhysicalNumberOfRows();
        ArrayList<EncSocioNutricia> errors = new ArrayList<EncSocioNutricia>();
        EncSocioNutricia encuesta = new EncSocioNutricia();
        boolean init = false;
        if (number_of_rows > 11) {
            for (int i = 10; i < number_of_rows; i++) {
                HSSFRow row = sheet.getRow(i);
                int member = (int) row.getCell(2).getNumericCellValue();
                if (member == 1) {
                    if (init) {
                        try {
                            ejbFacade.create(encuesta);
                        } catch (Exception ex) {
                            errors.add(encuesta);
                        }
                    }
                    encuesta = new EncSocioNutricia();
                    initEncuesta(encuesta, row);
                    processFamilia(encuesta, row);
                    init = true;
                }
                processIndividuo(row, encuesta);
            }
            ejbFacade.create(encuesta);
        }
        
        if(!errors.isEmpty())
            JsfUtil.addErrorMessage("Se detectaron errores en " + errors.size() + ", por favor verifique los datos.");
    }

    private void processIndividuo(HSSFRow row, EncSocioNutricia encuesta) {
        EncSocioNutriciaInd individuo = new EncSocioNutriciaInd();
        initIndividuo(individuo, encuesta);

        try {
            individuo.getEncSocioNutriciaIndPK().setNombre(row.getCell(3).getStringCellValue());
            individuo.setParentesco(EncSocioNutriciaUtil.getValue(EncSocioNutriciaUtil.PARENTESCO, String.valueOf(row.getCell(4).getNumericCellValue())));
            individuo.setGenero(getGenero(String.valueOf(row.getCell(8).getNumericCellValue())));
            individuo.setFechaNacimiento(row.getCell(5).getDateCellValue());
            individuo.setPeso(Double.valueOf(row.getCell(41).getNumericCellValue()) + 1.0);
            individuo.setTalla(Double.valueOf(row.getCell(42).getNumericCellValue()) + 1.0);
            individuo.setEstadoCivil(EncSocioNutriciaUtil.getValue(EncSocioNutriciaUtil.ESTADO_CIVIL, String.valueOf(row.getCell(9).getNumericCellValue())));
            individuo.setEscolaridad(EncSocioNutriciaUtil.getValue(EncSocioNutriciaUtil.ESCOLARIDAD, String.valueOf(row.getCell(10).getNumericCellValue())));
            individuo.setCondLaboral(EncSocioNutriciaUtil.getValue(EncSocioNutriciaUtil.CONDICION_LABORAL, String.valueOf(row.getCell(11).getNumericCellValue())));
            individuo.setRamaActiv(EncSocioNutriciaUtil.getValue(EncSocioNutriciaUtil.RAMA_ACTIVIDAD, String.valueOf(row.getCell(12).getNumericCellValue())));
            individuo.setSalario(Double.valueOf(row.getCell(13).getNumericCellValue()));
            individuo.setSeguroSocial(EncSocioNutriciaUtil.getValue(EncSocioNutriciaUtil.SEGURO_SOCIAL, String.valueOf(row.getCell(15).getNumericCellValue())));
        } catch (Exception ex) {
        }

        encuesta.getEncSocioNutriciaIndCollection().add(individuo);
    }

    private void initIndividuo(EncSocioNutriciaInd individuo, EncSocioNutricia encuesta) {
        EncSocioNutriciaPK pk = encuesta.getEncSocioNutriciaPK();
        individuo.setEncSocioNutriciaIndPK(new EncSocioNutriciaIndPK()); // Reemplazar cuando sepas bien que onda con el expediente
        individuo.getEncSocioNutriciaIndPK().setAreaGeo(pk.getAreaGeo());
        individuo.getEncSocioNutriciaIndPK().setExpediente(pk.getExpediente());
        individuo.setProbSalud("No");
        individuo.setAyudaAlim("Ninguno");
        individuo.setEtapaEstFis("No aplica");
        individuo.setSemEmbarazo(0);
        individuo.setPesoPreg(0.0);
        individuo.setGrupoEtnico("No");
    }

    private void processFamilia(EncSocioNutricia encuesta, HSSFRow row) {
        EncSocioNutriciaFam familia = new EncSocioNutriciaFam();
        initFamilia(familia, encuesta);

        try {
            familia.setP1R((int) row.getCell(21).getNumericCellValue());
            familia.setP2R((int) row.getCell(22).getNumericCellValue());
            familia.setP3R((int) row.getCell(23).getNumericCellValue());
            familia.setP4NumCuartos((int) row.getCell(24).getNumericCellValue());
            familia.setP6R((int) row.getCell(25).getNumericCellValue());
            familia.setP7R((int) row.getCell(26).getNumericCellValue());
            familia.setP8R((int) row.getCell(27).getNumericCellValue());

            if (familia.getP8R() != 1) {
                int p9R = (int) row.getCell(28).getNumericCellValue();
                familia.setP9R(p9R);
                if (p9R == 4) {
                } // cambiar cuando sepas que onda con el P9R_OTRO 
            } else {
                familia.setP9R(0);
            }

            familia.setP10R((int) row.getCell(29).getNumericCellValue());
            familia.setP11R((int) row.getCell(27).getNumericCellValue());

            if (familia.getP11R() != 1) {
                int p12R = (int) row.getCell(28).getNumericCellValue();
                familia.setP12R(p12R);
                if (p12R == 4) {
                } // cambiar cuando sepas que onda con el P12R_OTRO 
            } else {
                familia.setP12R(0);
            }

            familia.setP13R((int) row.getCell(32).getNumericCellValue());
            // Checar bien preguntas 14-18
            familia.setP17ApoyoFederal(new BigDecimal(row.getCell(14).getNumericCellValue()));

            familia.setP19Alimentacion(new BigDecimal(row.getCell(33).getNumericCellValue()));

            //Ganado
            boolean ganado = (int) row.getCell(37).getNumericCellValue() == 1;
            familia.setP29R(ganado);
            if (ganado) {
                if (row.getCell(38).getCellType() == Cell.CELL_TYPE_STRING) {
                    familia.setP30GanadoOtro(row.getCell(38).getStringCellValue());
                } else if (row.getCell(38).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    familia.setP30Ganado((int) row.getCell(38).getNumericCellValue());
                }
            }
            familia.setP31DestinoGanado((int) row.getCell(39).getNumericCellValue());

            // Cultivo
            familia.setP24R(row.getCell(34).getNumericCellValue() == 1.0);
            familia.setP25R((int) row.getCell(35).getNumericCellValue());
            familia.setP28DestinoAgricola((int) row.getCell(36).getNumericCellValue());
        } catch (Exception ex) {

        }

        encuesta.setEncSocioNutriciaFam(familia);
    }

    private void initFamilia(EncSocioNutriciaFam familia, EncSocioNutricia encuesta) {
        EncSocioNutriciaPK pk = encuesta.getEncSocioNutriciaPK();
        familia.setEncSocioNutriciaFamPK(new EncSocioNutriciaFamPK(pk.getExpediente(), pk.getAreaGeo()));
        familia.setP5R(false); // Sin cocina

        // Ingresos Adicionales
        familia.setP14R(true); // Con otros ingresos
        familia.setP15ComercioInformal(BigDecimal.ZERO);
        familia.setP16ServDomesticos(BigDecimal.ZERO);
        familia.setP18OtrosString(null);
        familia.setP18Otros(BigDecimal.ZERO);

        // Gastos Adicionales
        familia.setP20Luz(BigDecimal.ZERO);
        familia.setP21Agua(BigDecimal.ZERO);
        familia.setP22Gas(BigDecimal.ZERO);
        familia.setP23Otros(BigDecimal.ZERO);
    }

    private void initEncuesta(EncSocioNutricia encuesta, HSSFRow row) {
        encuesta.setFechaCaptura(new Date());
        encuesta.setFechaLev(new Date());
        encuesta.setStatus(true);
        String jefe = row.getCell(1).getStringCellValue();
        encuesta.setJefeFamilia(jefe);
        encuesta.setEntrevistado(jefe);
        encuesta.setEncSocioNutriciaIndCollection(new ArrayList<EncSocioNutriciaInd>());
        encuesta.setEncSocioNutriciaPK(new EncSocioNutriciaPK());
        encuesta.setIDInstitucion(institucionesFacade.findLike(row.getCell(0).getStringCellValue(), 1).get(0));
        encuesta.getEncSocioNutriciaPK().setAreaGeo(row.getCell(16).getStringCellValue().charAt(0));
        encuesta.getEncSocioNutriciaPK().setExpediente(ejbFacade.findLastExp(row.getCell(16).getStringCellValue().charAt(0)) + 1);
        encuesta.setIdentificador(JsfUtil.getLoggedUser());

    }

    private String getGenero(String value) {
        double dbl = Double.valueOf(value);
        int opcion = (int) dbl;
        switch (opcion) {
            case 1:
                return "M";
            case 2:
                return "F";
        }

        return "No procede";
    }
}
