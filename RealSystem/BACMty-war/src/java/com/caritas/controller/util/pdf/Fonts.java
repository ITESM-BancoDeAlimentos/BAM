package com.caritas.controller.util.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import java.io.IOException;

/**
 *
 * @author software
 */
public class Fonts {

    private static Fonts instance = null;

    public static Fonts getInstance() throws DocumentException, IOException {
        if (instance == null) {
            instance = new Fonts();
        }
        return instance;
    }
    public final Font normalCellFont;
    public final Font documentHeaderFont;
    public final Font headerCellFont;

    public Fonts() throws DocumentException, IOException {
        headerCellFont = new Font(BaseFont.createFont(), 6.0f, Font.BOLD);
        normalCellFont = new Font(BaseFont.createFont(), 5.5f);
        documentHeaderFont = new Font(BaseFont.createFont(), 8.0f, Font.BOLD);
    }

    public Fonts(Font normalCellFont, Font documentHeaderFont, Font headerCellFont) {
        this.normalCellFont = normalCellFont;
        this.documentHeaderFont = documentHeaderFont;
        this.headerCellFont = headerCellFont;
    }

    public Paragraph headerP(String s) {
        Paragraph paragraph = new Paragraph(s.toUpperCase(), headerCellFont);
        return paragraph;
    }

    public PdfPCell centerCell(Paragraph s) {
        return centerCell(new PdfPCell(s));
    }

    public PdfPCell centerCell(PdfPCell cell) {
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }

    public PdfPCell rSpanCell(String p, int i) {
        PdfPCell pdfPCell = new PdfPCell(headerP(p));
        pdfPCell.setRowspan(i);
        return pdfPCell;
    }

    public PdfPCell rSpanCell(PdfPCell pdfPCell, int i) {
        pdfPCell.setRowspan(i);
        return pdfPCell;
    }

    public PdfPCell cSpanCell(String p, int i) {
        PdfPCell pdfPCell = new PdfPCell(headerP(p));
        pdfPCell.setColspan(i);
        return pdfPCell;
    }

    public PdfPCell cSpanCell(PdfPCell pdfPCell, int i) {
        pdfPCell.setColspan(i);
        return pdfPCell;
    }

    public Paragraph mainHeader(String s) {
        Paragraph paragraph = new Paragraph(s.toUpperCase(), documentHeaderFont);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        paragraph.setSpacingAfter(10.0F);
        return paragraph;
    }

    public Paragraph normal(String s) {
        return new Paragraph(s, normalCellFont);
    }

    public Paragraph newUppercase(String s) {
        Paragraph paragraph = new Paragraph(s.toUpperCase(), normalCellFont);
        return paragraph;
    }
}
