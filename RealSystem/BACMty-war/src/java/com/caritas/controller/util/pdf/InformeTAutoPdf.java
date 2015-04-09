package com.caritas.controller.util.pdf;

import com.caritas.entity.RegistroInformeTA;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Collection;

/**
 *
 * @author software
 */
public class InformeTAutoPdf extends PdfTemplate {

    private static final String VAR_TITLE = "% Var";
    private static final BaseColor MYCOLOR1 = new BaseColor(204, 192, 218);
    private static final BaseColor MYCOLOR2 = new BaseColor(252, 213, 180);
    private static final BaseColor MYCOLOR3 = new BaseColor(182, 221, 232);
    private static final String TITULO = "Cáritas de Monterrey A.B.P" + "\n"
            + "Subdireccion de Servicios de Alimentación y Nutrición" + "\n"
            + "Área de Procuración" + "\n"
            + "Informe de Tiendas de Autoservicio";
    private Collection<RegistroInformeTA.Pair> registros;
    private int formerYear = 0;
    private int latterYear = 0;
    private RegistroInformeTA.Pair total = null;

    public InformeTAutoPdf(Collection<RegistroInformeTA.Pair> r, int f, int l) throws DocumentException, IOException {
        super();
        registros = r;
        formerYear = f;
        latterYear = l;
    }

    @Override
    protected Element createTable() {
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);

        table.addCell(dnnteHeader("Donante"));
        table.addCell(titleHeader1("Tiendas"));
        table.addCell(titleHeader2("Kilogramos"));
        table.addCell(titleHeader3("Costo"));

        table.addCell(subTh1(Integer.toString(formerYear)));
        table.addCell(subTh1(Integer.toString(latterYear)));
        table.addCell(subTh1(VAR_TITLE));

        table.addCell(subTh2(Integer.toString(formerYear)));
        table.addCell(subTh2(Integer.toString(latterYear)));
        table.addCell(subTh2(VAR_TITLE));

        table.addCell(subTh3(Integer.toString(formerYear)));
        table.addCell(subTh3(Integer.toString(latterYear)));
        table.addCell(subTh3(VAR_TITLE));

        addValues(table);

        table.addCell(totalTh("Total"));
        addTotals(table);

        return table;
    }

    private void addValues(PdfPTable table) {
        for (RegistroInformeTA.Pair pair : registros) {
            table.addCell(fonts.centerCell(fonts.headerP(pair.getDonante().getDonante())));
            addPairValue(table, pair);
        }
    }

    private PdfPCell centerCell(PdfPCell c) {
        return fonts.centerCell(c);
    }

    private PdfPCell titleHeader(String costo) {
        return centerCell(newCSpanCell(costo, 3));
    }

    private PdfPCell dnnteHeader(String donante) {
        return vCenterCell(centerCell(newRSpanCell(donante, 2)));
    }

    private PdfPCell vCenterCell(PdfPCell c) {
        c.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        return c;
    }

    private PdfPCell subTh(String s) {
        return fonts.centerCell(fonts.headerP(s));
    }

    private PdfPCell subTh1(String s) {
        PdfPCell subTh = subTh(s);
        subTh.setBackgroundColor(MYCOLOR1);
        return subTh;
    }

    private PdfPCell subTh2(String s) {
        PdfPCell subTh = subTh(s);
        subTh.setBackgroundColor(MYCOLOR2);
        return subTh;
    }

    private PdfPCell subTh3(String s) {
        PdfPCell subTh = subTh(s);
        subTh.setBackgroundColor(MYCOLOR3);
        return subTh;
    }

    private RegistroInformeTA.Pair getTotal() {
        if (total == null) {
            total = computeTotal();
        }
        return total;
    }

    private void addTotals(PdfPTable table) {
        addTotalPairValue(table, getTotal());
    }

    private PdfPCell totalTh(String total) {
        return fonts.centerCell(fonts.headerP(total));
    }

    private void addTotalPairValue(PdfPTable table, RegistroInformeTA.Pair pair) {
        table.addCell(centerCell(normalP1(pair.getFormer().getCantSucursales())));
        table.addCell(centerCell(normalP1(pair.getLatter().getCantSucursales())));
        table.addCell(centerCell(normalP1(pair.getCantSucursalesVar())));

        table.addCell(centerCell(normalP2(pair.getFormer().getKilogramos())));
        table.addCell(centerCell(normalP2(pair.getLatter().getKilogramos())));
        table.addCell(centerCell(normalP2(pair.getKilogramosVar())));

        table.addCell(centerCell(normalP3(pair.getFormer().getCosto())));
        table.addCell(centerCell(normalP3(pair.getLatter().getCosto())));
        table.addCell(centerCell(normalP3(pair.getCostoVar())));
    }

    private void addPairValue(PdfPTable table, RegistroInformeTA.Pair pair) {
        table.addCell(centerCell(normalP(pair.getFormer().getCantSucursales())));
        table.addCell(centerCell(normalP(pair.getLatter().getCantSucursales())));
        table.addCell(centerCell(normalP(pair.getCantSucursalesVar())));

        table.addCell(centerCell(normalP(pair.getFormer().getKilogramos())));
        table.addCell(centerCell(normalP(pair.getLatter().getKilogramos())));
        table.addCell(centerCell(normalP(pair.getKilogramosVar())));

        table.addCell(centerCell(normalP(pair.getFormer().getCosto())));
        table.addCell(centerCell(normalP(pair.getLatter().getCosto())));
        table.addCell(centerCell(normalP(pair.getCostoVar())));
    }

    private PdfPCell normalP1(String toString) {
        Paragraph normalP = fonts.normal(toString);
        PdfPCell pdfPCell = new PdfPCell(normalP);
        pdfPCell.setBackgroundColor(MYCOLOR1);
        return pdfPCell;
    }

    private PdfPCell normalP2(String toString) {
        Paragraph normalP = fonts.normal(toString);
        PdfPCell pdfPCell = new PdfPCell(normalP);
        pdfPCell.setBackgroundColor(MYCOLOR2);
        return pdfPCell;
    }

    private PdfPCell normalP3(String toString) {
        Paragraph normalP = fonts.normal(toString);
        PdfPCell pdfPCell = new PdfPCell(normalP);
        pdfPCell.setBackgroundColor(MYCOLOR3);
        return pdfPCell;
    }

    private PdfPCell titleHeader1(String costo) {
        PdfPCell titleHeader = titleHeader(costo);
        titleHeader.setBackgroundColor(MYCOLOR1);
        return titleHeader;
    }

    private PdfPCell titleHeader2(String costo) {
        PdfPCell titleHeader = titleHeader(costo);
        titleHeader.setBackgroundColor(MYCOLOR2);
        return titleHeader;
    }

    private PdfPCell normalP(int cantSucursales) {
        return new PdfPCell(fonts.normal(Integer.toString(cantSucursales)));
    }

    private PdfPCell normalP(double d) {
        String format = NumberFormat.getInstance().format(d);
        return new PdfPCell(fonts.normal(format));
    }

    private PdfPCell normalP1(int cantSucursales) {
        return normalP1((Integer.toString(cantSucursales)));
    }

    private PdfPCell normalP1(double d) {
        String format = String.format("%.2f", d);
        return normalP1(format);
    }

    private PdfPCell normalP2(int cantSucursales) {
        return normalP2((Integer.toString(cantSucursales)));
    }

    private PdfPCell normalP2(double d) {
        String format = String.format("%.2f", d);
        return normalP2(format);
    }

    private RegistroInformeTA.Pair computeTotal() {
        int cantSucursalesF = 0;
        int cantSucursalesL = 0;
        double kilosF = 0.0;
        double kilosL = 0.0;
        double costoF = 0.0;
        double costoL = 0.0;

        for (RegistroInformeTA.Pair pair : registros) {
            cantSucursalesF += pair.getFormer().getCantSucursales();
            cantSucursalesL += pair.getLatter().getCantSucursales();
            kilosF += pair.getFormer().getKilogramos();
            kilosL += pair.getLatter().getKilogramos();
            costoF += pair.getFormer().getCosto();
            costoL += pair.getLatter().getCosto();

        }
        RegistroInformeTA f = new RegistroInformeTA(null, cantSucursalesF, kilosF, costoF, formerYear);
        RegistroInformeTA l = new RegistroInformeTA(null, cantSucursalesL, kilosL, costoL, latterYear);
        return new RegistroInformeTA.Pair(f, l);
    }

    private PdfPCell titleHeader3(String costo) {
        PdfPCell titleHeader = titleHeader(costo);
        titleHeader.setBackgroundColor(MYCOLOR3);
        return titleHeader;
    }

    private PdfPCell normalP3(double d) {
        String format = String.format("%.2f", d);
        return normalP3(format);
    }

    @Override
    protected Rectangle getPageSize() {
        return PageSize.LETTER.rotate();
    }

    @Override
    protected String getHeader() {
        return TITULO;
    }

    private PdfPCell newCSpanCell(String costo, int i) {
        return fonts.cSpanCell(costo, i);
    }

    private PdfPCell newRSpanCell(String donante, int i) {
        return fonts.rSpanCell(donante, i);
    }
}
