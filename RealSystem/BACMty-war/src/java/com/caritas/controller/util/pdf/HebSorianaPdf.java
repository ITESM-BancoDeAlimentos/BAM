package com.caritas.controller.util.pdf;

import com.caritas.entity.RegistroProductos;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author software
 */
public class HebSorianaPdf extends PdfTemplate {

    private static final String TITULO = "Reporte de Productos Recibidos de Tiendas de Autoservicio";
    private static final String SUBTITULO = "HEB Soriana";
    private Collection<RegistroProductos> registros;

    public HebSorianaPdf(Collection<RegistroProductos> registros) throws DocumentException, IOException {
        this.registros = registros;
    }

    /**
     *
     * @return
     */
    @Override
    protected Element createTable() {
        PdfPTable table = new PdfPTable(17);
        table.setWidthPercentage(100);
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        addHeaders(table);
        for (RegistroProductos registro : registros) {
            addRegistroRow(table, registro);
        }
        return table;
    }

    protected PdfPCell panaderiaHeader(String s) {
        PdfPCell newCSpanCell = fonts.cSpanCell(s, 6);
        return fonts.centerCell(newCSpanCell);
    }

    protected PdfPCell claseHeader(String p) {
        PdfPCell newCSpanCell = fonts.cSpanCell(p, 2);
        return fonts.centerCell(newCSpanCell);
    }

    public PdfPCell subHeaderCell(String s) {
        PdfPCell pdfPCell = new PdfPCell(fonts.normal(s));
        return fonts.centerCell(pdfPCell);
    }

    private PdfPCell tiendaHeader(String s) {
        PdfPCell newRSpanCell = fonts.rSpanCell(s, 2);
        newRSpanCell.getPhrase().getFont().setStyle(Font.BOLD);
        return fonts.centerCell(newRSpanCell);
    }

    private void addHeaders(PdfPTable table) {
        table.addCell(tiendaHeader("Tienda"));
        table.addCell(claseHeader("Alimentos"));
        table.addCell(claseHeader("Pan Barra"));
        table.addCell(panaderiaHeader("Panaderia"));
        table.addCell(claseHeader("Lacteos"));
        table.addCell(claseHeader("Refresco"));
        table.addCell(claseHeader("Varios"));
        //<editor-fold defaultstate="collapsed" desc="Headers de Apto y Merma">
        // Alimentos
        table.addCell(subHeaderCell("Apto"));
        table.addCell(subHeaderCell("Merma"));
        // Pan Blanco
        table.addCell(subHeaderCell("Apto"));
        table.addCell(subHeaderCell("Merma"));
        // Panaderia
        table.addCell(subHeaderCell("Dulce"));     // Apto
        table.addCell(subHeaderCell("Sal"));       // Apto
        table.addCell(subHeaderCell("Pastel"));    // Apto
        table.addCell(subHeaderCell("Tortilla"));  // Apto  
        table.addCell(subHeaderCell("Molido"));    // Apto
        table.addCell(subHeaderCell("Merma"));
        // Lacteos
        table.addCell(subHeaderCell("Apto"));
        table.addCell(subHeaderCell("Merma"));
        // Refresco
        table.addCell(subHeaderCell("Apto"));
        table.addCell(subHeaderCell("Merma"));
        // Varios
        table.addCell(subHeaderCell("Apto"));
        table.addCell(subHeaderCell("Merma"));
        //</editor-fold>
    }

    private void addRegistroRow(PdfPTable table, RegistroProductos registro) {
        Collection<RegistroProductos.CantidadMermaValue> values = registro.getCells().values();
        table.addCell(fonts.normal(getTiendaString(registro)));
        table.addCell(normFloat(registro.getCantidad("Alimentos")));
        table.addCell(normFloat(registro.getMerma("Alimentos")));
        table.addCell(normFloat(registro.getCantidad("Pan Barra")));
        table.addCell(normFloat(registro.getMerma("Pan Barra")));

        table.addCell(normFloat(registro.getCantidad("Panaderia Dulce")));
        table.addCell(normFloat(registro.getCantidad("Panaderia Pastel")));
        table.addCell(normFloat(registro.getCantidad("Panaderia Sal")));
        table.addCell(normFloat(registro.getCantidad("Panaderia Tortilla")));
        table.addCell(normFloat(registro.getCantidad("Panaderia Molido")));
        table.addCell(normFloat(registro.getMermaPanaderia()));

        table.addCell(normFloat(registro.getCantidad("Lacteos")));
        table.addCell(normFloat(registro.getMerma("Lacteos")));
        table.addCell(normFloat(registro.getCantidad("Refresco")));
        table.addCell(normFloat(registro.getMerma("Refresco")));
        table.addCell(normFloat(registro.getCantidad("Varios")));
        table.addCell(normFloat(registro.getMerma("Varios")));
    }

    private Paragraph normFloat(double r) {
        Paragraph p = fonts.normal(String.format("%.2f", r));
        return p;
    }

    private String getTiendaString(RegistroProductos registro) {
        String tiendaStr = registro.getDonante().getNombre() + "\n" + registro.getSucursal();
        return tiendaStr;
    }

    @Override
    protected Rectangle getPageSize() {
        Rectangle rotate = PageSize.LETTER.rotate();
        return rotate;
    }

    @Override
    protected String getHeader() {
        return TITULO + "\n" + SUBTITULO;
    }
}
