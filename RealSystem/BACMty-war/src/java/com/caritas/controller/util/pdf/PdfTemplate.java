package com.caritas.controller.util.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author software
 */
public abstract class PdfTemplate {

    private static final boolean Mirroring = true;
    private static final float marginLeft = 0.5f;
    private static final float marginRight = 0.3f;
    private static final float marginTop = 0.0f;
    private static final float marginBottom = 0.3f;
    protected Fonts fonts;

    public PdfTemplate() throws DocumentException, IOException {
        this.fonts = Fonts.getInstance();
    }

    public ByteArrayOutputStream createPdf() throws DocumentException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writeTo(baos);
        return baos;
    }

    void writeTo(OutputStream pdf) throws DocumentException, IOException {
        Document document = new Document(getPageSize());
        PdfWriter pdfWriter = PdfWriter.getInstance(document, pdf);
        document.open();
        document.setMargins(getMarginLeft(), getMarginRight(), getMarginTop(), getMarginBottom());
        document.setMarginMirroring(isMirroring());
        document.add(fonts.mainHeader(getHeader()));
        document.add(createTable());
        document.close();
    }

    protected abstract Rectangle getPageSize();

    protected abstract String getHeader();

    protected abstract Element createTable();

    protected float getMarginLeft() {
        return marginLeft;
    }

    protected float getMarginRight() {
        return marginRight;
    }

    protected float getMarginTop() {
        return marginTop;
    }

    protected float getMarginBottom() {
        return marginBottom;
    }

    protected boolean isMirroring() {
        return Mirroring;
    }
}
