package com.caritas.controller.util.pdf;

import com.caritas.entity.ValeCUDE;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.IOException;
import java.text.DateFormat;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author software
 */
public class ValeCudePdf extends PdfTemplate {

    private static final String TITULO = ResourceBundle.getBundle("/Bundle").getString("ValeCUDETitle");
    private static final float[] relativeWidths = {
        8.8f, // "Folio"
        11.11f,// "Fecha"
        25.0f, // "Donante"
        16.5f, // "Elabora"
        17.0f, // "Recibi"
        22.0f // "Observaciones"
    };
    private static final String OBSERVACIONES = "Observaciones";
    private static final String RECIBI = "Recibí";
    private static final String ELABORA = "Elabora";
    private static final String DONANTE = "Donante";
    private static final String FECHA = "Fecha";
    private static final String FOLIO = "Folio";
    private static final float WIDTH = 100.0f;
    private static final DateFormat dateInstance = DateFormat.getDateInstance(DateFormat.SHORT);
    private final List<ValeCUDE> vales;

    public ValeCudePdf(List<ValeCUDE> valeCudes) throws DocumentException, IOException {
        this.vales = valeCudes;
    }

    @Override
    protected Element createTable() {
        PdfPTable table = new PdfPTable(relativeWidths);
        table.setWidthPercentage(WIDTH);
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(fonts.headerP(FOLIO));
        table.addCell(fonts.headerP(FECHA));
        table.addCell(fonts.headerP(DONANTE));
        table.addCell(fonts.headerP(ELABORA));
        table.addCell(fonts.headerP(RECIBI));
        table.addCell(fonts.headerP(OBSERVACIONES));

        for (ValeCUDE valeCUDE : vales) {
            table.addCell(fonts.newUppercase(valeCUDE.getFolio()));
            table.addCell(fonts.newUppercase(dateInstance.format(valeCUDE.getFecha())));
            table.addCell(fonts.newUppercase(valeCUDE.getIDDonante().getDonante()));
            table.addCell(fonts.newUppercase(valeCUDE.getElabora()));
            table.addCell(""); // Celda vacia. Se llena despues a mano.
            table.addCell(""); // Celda vacia. Se llena despues a mano.
        }
        return table;
    }

    @Override
    protected Rectangle getPageSize() {
        return PageSize.LETTER;
    }

    @Override
    protected String getHeader() {
        return TITULO;
    }
}
