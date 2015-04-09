/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.controller.util.pdf;

import com.caritas.controller.DistribucionAMBA_Tabla;
import com.caritas.entity.DistribucionAMBA;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author tecnologia
 */
public class DistAmbaPdf extends PdfTemplate {
    private static final String TITULO = "ASOCIACIÓN MEXICANA DE BANCOS DE ALIMENTOS, A.C.";
    private static final String SUBTITULO = "Distribución de producto acopiado por región";
    private String folio;
    private String cedis;
    private String donante;
    private String sucursal;
    private boolean tieneSucursal = false;
    private String fechaRep;
    
    private List<DistribucionAMBA_Tabla> items;
    private HashMap<String, Object> map;

    public DistAmbaPdf(List<DistribucionAMBA_Tabla> items, HashMap<String, Object> map) throws IOException, DocumentException {
        this.items = items;
        this.map = map;
        
        this.folio = map.get("FOLIO").toString();
        this.cedis = map.get("CEDIS").toString();
        this.donante = map.get("DONANTE").toString();
        if (map.containsKey("SUCURSAL")) {
            this.sucursal = map.get("SUCURSAL").toString();
            this.tieneSucursal = true;
        }
        this.fechaRep = map.get("FECHA").toString();
    }
    
    @Override
    protected Element createTable() {
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        addHeaders(table);
        for (DistribucionAMBA_Tabla item : items) {
            addRegistroRow(table, item);
        }
        addTotals(table, map);
        
        return table;
    }
    
    private void addHeaders(PdfPTable table) {
        table.addCell(fonts.centerCell(fonts.headerP("Remisión")));
        table.addCell(fonts.centerCell(fonts.headerP("Banco de Alimentos")));
        table.addCell(fonts.centerCell(fonts.headerP("Población")));
        table.addCell(fonts.centerCell(fonts.headerP(" % ")));
        table.addCell(fonts.centerCell(fonts.headerP("KILOGRAMOS")));
        table.addCell(fonts.centerCell(fonts.headerP("PIEZAS")));
        table.addCell(fonts.centerCell(fonts.headerP("Flete 1")));
        table.addCell(fonts.centerCell(fonts.headerP("OBSERVACIONES")));
    }
    
    private void addRegistroRow(PdfPTable table, DistribucionAMBA_Tabla item) {
        table.addCell(fonts.centerCell(fonts.normal(item.getRemision())));
        table.addCell(fonts.centerCell(fonts.normal(item.getBancoDeAlimentos())));
        table.addCell(fonts.centerCell(fonts.normal(String.valueOf(item.getPoblacionTabla()))));
        table.addCell(fonts.centerCell(fonts.normal(item.getPorcentajeString())));
        table.addCell(fonts.centerCell(fonts.normal(item.getKilogramosString())));
        table.addCell(fonts.centerCell(fonts.normal(String.valueOf(item.getPiezas()))));
        table.addCell(fonts.centerCell(fonts.normal(item.getFleteString())));
        table.addCell(fonts.centerCell(fonts.normal(item.getObservaciones())));
    }
    
    private void addTotals(PdfPTable table, HashMap<String, Object> totales) {
        table.addCell(fonts.cSpanCell(fonts.centerCell(fonts.normal("SUMAS")), 2));
        table.addCell(fonts.centerCell(fonts.normal(totales.get("POBLACION").toString())));
        table.addCell(fonts.centerCell(fonts.normal(totales.get("PORCENTAJE").toString())));
        table.addCell(fonts.centerCell(fonts.normal(totales.get("KILOGRAMOS").toString())));
        table.addCell(fonts.centerCell(fonts.normal(totales.get("PIEZAS").toString())));
        table.addCell(fonts.centerCell(fonts.normal(totales.get("FLETES").toString())));
        table.addCell(fonts.centerCell(fonts.normal(" ")));
    }
    
    @Override
    protected Rectangle getPageSize() {
        Rectangle rotate = PageSize.LETTER.rotate();
        return rotate;
    }

    @Override
    protected String getHeader() {
        return TITULO + "\n" + SUBTITULO + "\n\n" 
                + "Donante: " + donante
                + (tieneSucursal ? "\nSucursal: " + sucursal : "") + "\n"
                + "Folio: " + folio + " - Cedis de origen: " + cedis + "\n"
                + "Fecha: " + fechaRep;
    }
}
