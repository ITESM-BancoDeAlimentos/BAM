package com.caritas.controller.util.pdf;

import com.caritas.entity.Donantes;
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
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author tecnologia
 */
public class WalMartSamsAurrPdf extends PdfTemplate {

    static private String TITULO = "Asociacion Mexicana de Alimentos";
    static private String SUBTITULO1 = "Banco de Alimentos de Cáritas de Monterrey  ABP";
    static private String SUBTITULO2 = "Fundacion Wal-Mart de México";
    static private String SUBTITULO3 = "Producto seleccionado";
    static private String BANCO = "Banco de Alimentos Monterrey";
    private Collection<RegistroProductos> registros;
    private String elaboro;

    public WalMartSamsAurrPdf(Collection<RegistroProductos> registros) throws DocumentException, IOException {
        super();
        this.registros = registros;
        this.elaboro = elaboro;
    }

    @Override
    public void writeTo(OutputStream pdf) throws DocumentException, IOException {
        Document document = new Document(PageSize.LETTER.rotate());
        PdfWriter pdfWriter = PdfWriter.getInstance(document, pdf);
        document.open();
        document.setMargins(getMarginLeft(), getMarginRight(), getMarginTop(), getMarginBottom());
        document.setMarginMirroring(true);
        document.add(fonts.mainHeader(getHeader() + "\n" + getSubtitulo2() + "\n" + getSubtitulo3()));

        Date fechaDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yy");
        String fechaStr = sdf.format(fechaDate);
        document.add(fonts.normal("Fecha: " + fechaStr));

        document.add(fonts.normal("Elaboro: " + elaboro));
        document.add(fonts.normal("NOMBRE DEL BANCO: " + BANCO));
        document.add(fonts.normal("\n"));

        document.add(createTable());
        document.close();
    }

    @Override
    protected Element createTable() {
        PdfPTable table = new PdfPTable(15);
        table.setWidthPercentage(100);
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        addHeaders(table);

        Donantes donanteAnterior = null;
        Donantes donanteActual;
        HashMap<String, Double> SubTotales = new HashMap<String, Double>();
        HashMap<String, Double> Totales = new HashMap<String, Double>();
        
        vaciaMap(SubTotales);
        vaciaMap(Totales);

        for (RegistroProductos registro : registros) {
            donanteActual = registro.getDonante();

            if (donanteAnterior != donanteActual) {
                if (donanteAnterior != null) {
                    addTotales(table, SubTotales, "SUB-TOTAL");
                    addHeaders(table);
                    table.addCell(fonts.normal(getTiendaString(registro)));
                    vaciaMap(SubTotales);
                } else {
                    table.addCell(fonts.normal(getTiendaString(registro)));
                }
            } else {
                table.addCell(fonts.normal(""));
            }

            addRegistroRow(table, registro, SubTotales, Totales);
            donanteAnterior = donanteActual;
        }
        addTotales(table, SubTotales, "SUB-TOTAL");
        addTotales(table, Totales, "TOTAL");
        return table;
    }
    
    private void vaciaMap(HashMap<String, Double> returnMap) {
        //HashMap returnMap = new HashMap<String, Double>();

        returnMap.put("A. Animal", 0.0);
        returnMap.put("Bolsa", 0.0);
        returnMap.put("Botella", 0.0);
        returnMap.put("Limpieza", 0.0);
        returnMap.put("Lateria", 0.0);
        returnMap.put("Pan", 0.0);
        returnMap.put("P Y H", 0.0);
        returnMap.put("Papel", 0.0);
        returnMap.put("Semilla", 0.0);
        returnMap.put("Varios", 0.0);
        returnMap.put("Verduras", 0.0);
        returnMap.put("Totales", 0.0);
        returnMap.put("Merma", 0.0);

        //return returnMap;
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
        pdfPCell.getPhrase().getFont().setStyle(Font.BOLD);
        return fonts.centerCell(pdfPCell);
    }

//    private PdfPCell tiendaHeader(String s) {
//        PdfPCell newRSpanCell = fonts.rSpanCell(s, 2);
//        newRSpanCell.getPhrase().getFont().setStyle(Font.BOLD);
//        return fonts.centerCell(newRSpanCell);
//    }
    private void addHeaders(PdfPTable table) {
        table.addCell(subHeaderCell("Tienda"));
        table.addCell(subHeaderCell("Sucursal"));
        table.addCell(subHeaderCell("A. ANIMAL"));
        table.addCell(subHeaderCell("BOLSA"));
        table.addCell(subHeaderCell("BOTELLA"));
        table.addCell(subHeaderCell("LIMPIEZA"));
        table.addCell(subHeaderCell("LATERIA"));
        table.addCell(subHeaderCell("PAN"));
        table.addCell(subHeaderCell("P Y H"));
        table.addCell(subHeaderCell("PAPEL"));
        table.addCell(subHeaderCell("SEMILLA"));
        table.addCell(subHeaderCell("VARIOS"));
        table.addCell(subHeaderCell("VERDURAS"));
        table.addCell(subHeaderCell("TOTAL"));
        table.addCell(subHeaderCell("MERMA"));
    }

    private void addTotales(PdfPTable table, HashMap<String, Double> mapa, String title) {
        table.addCell(fonts.normal(title));
        table.addCell(fonts.normal(" "));
        table.addCell(normFloat(mapa.get("A. Animal")));
        table.addCell(normFloat(mapa.get("Bolsa")));
        table.addCell(normFloat(mapa.get("Botella")));
        table.addCell(normFloat(mapa.get("Limpieza")));
        table.addCell(normFloat(mapa.get("Lateria")));
        table.addCell(normFloat(mapa.get("Pan")));
        table.addCell(normFloat(mapa.get("P Y H")));
        table.addCell(normFloat(mapa.get("Papel")));
        table.addCell(normFloat(mapa.get("Semilla")));
        table.addCell(normFloat(mapa.get("Varios")));
        table.addCell(normFloat(mapa.get("Verduras")));
        table.addCell(normFloat(mapa.get("Totales")));
        table.addCell(normFloat(mapa.get("Merma")));
    }

    private void addRegistroRow(PdfPTable table, RegistroProductos registro, HashMap<String, Double> SubTotales, HashMap<String, Double> Totales) {
        Collection<RegistroProductos.CantidadMermaValue> values = registro.getCells().values();
        table.addCell(fonts.normal(getSucursalString(registro)));
        Double TotalOneRow = 0.0;
        Double MermaOneRow = 0.0;
        double valorCant;
        double valorMerma;

        valorCant = registro.getCantidad("Alimentos");
        table.addCell(normFloat(valorCant));
        TotalOneRow += valorCant;
        valorMerma = registro.getMerma("Alimentos");
        MermaOneRow += valorMerma;
        SubTotales.put("A. Animal", SubTotales.get("A. Animal") + valorCant);
        Totales.put("A. Animal", Totales.get("A. Animal") + valorCant);

        valorCant = registro.getCantidad("Pan Barra");
        table.addCell(normFloat(valorCant));
        TotalOneRow += valorCant;
        valorMerma = registro.getMerma("Pan Barra");
        MermaOneRow += valorMerma;
        SubTotales.put("Bolsa", SubTotales.get("Bolsa") + valorCant);
        Totales.put("Bolsa", Totales.get("Bolsa") + valorCant);

        valorCant = registro.getCantidad("Lacteos");
        table.addCell(normFloat(valorCant));
        TotalOneRow += valorCant;
        valorMerma = registro.getMerma("Lacteos");
        MermaOneRow += valorMerma;
        SubTotales.put("Botella", SubTotales.get("Botella") + valorCant);
        Totales.put("Botella", Totales.get("Botella") + valorCant);

        valorCant = registro.getCantidad("Refresco");
        table.addCell(normFloat(valorCant));
        TotalOneRow += valorCant;
        valorMerma = registro.getMerma("Refresco");
        MermaOneRow += valorMerma;
        SubTotales.put("Limpieza", SubTotales.get("Limpieza") + valorCant);
        Totales.put("Limpieza", Totales.get("Limpieza") + valorCant);

        valorCant = registro.getCantidad("Panaderia Dulce");
        table.addCell(normFloat(valorCant));
        TotalOneRow += valorCant;
        valorMerma = registro.getMerma("Panaderia Dulce");
        MermaOneRow += valorMerma;
        SubTotales.put("Lateria", SubTotales.get("Lateria") + valorCant);
        Totales.put("Lateria", Totales.get("Lateria") + valorCant);

        valorCant = registro.getCantidad("Panaderia Pastel");
        table.addCell(normFloat(valorCant));
        TotalOneRow += valorCant;
        valorMerma = registro.getMerma("Panaderia Pastel");
        MermaOneRow += valorMerma;
        SubTotales.put("Pan", SubTotales.get("Pan") + valorCant);
        Totales.put("Pan", Totales.get("Pan") + valorCant);

        valorCant = registro.getCantidad("Panaderia Sal");
        table.addCell(normFloat(valorCant));
        TotalOneRow += valorCant;
        valorMerma = registro.getMerma("Panaderia Sal");
        MermaOneRow += valorMerma;
        SubTotales.put("P Y H", SubTotales.get("P Y H") + valorCant);
        Totales.put("P Y H", Totales.get("P Y H") + valorCant);

        valorCant = registro.getCantidad("Panaderia Tortilla");
        table.addCell(normFloat(valorCant));
        TotalOneRow += valorCant;
        valorMerma = registro.getMerma("Panaderia Tortilla");
        MermaOneRow += valorMerma;
        SubTotales.put("Papel", SubTotales.get("Papel") + valorCant);
        Totales.put("Papel", Totales.get("Papel") + valorCant);

        valorCant = registro.getCantidad("Panaderia Molido");
        table.addCell(normFloat(valorCant));
        TotalOneRow += valorCant;
        valorMerma = registro.getMerma("Panaderia Molido");
        MermaOneRow += valorMerma;
        SubTotales.put("Semilla", SubTotales.get("Semilla") + valorCant);
        Totales.put("Semilla", Totales.get("Semilla") + valorCant);

        valorCant = registro.getCantidad("Varios");
        table.addCell(normFloat(valorCant));
        TotalOneRow += valorCant;
        valorMerma = registro.getMerma("Varios");
        MermaOneRow += valorMerma;
        SubTotales.put("Varios", SubTotales.get("Varios") + valorCant);
        Totales.put("Varios", Totales.get("Varios") + valorCant);

        valorCant = registro.getCantidad("Alimentos");
        table.addCell(normFloat(valorCant));
        TotalOneRow += valorCant;
        valorMerma = registro.getMerma("Alimentos");
        MermaOneRow += valorMerma;
        SubTotales.put("Verduras", SubTotales.get("Verduras") + valorCant);
        Totales.put("Verduras", Totales.get("Verduras") + valorCant);

        table.addCell(normFloat(TotalOneRow));
        table.addCell(normFloat(MermaOneRow));
        SubTotales.put("Totales", SubTotales.get("Totales") + TotalOneRow);
        SubTotales.put("Merma", SubTotales.get("Merma") + MermaOneRow);
        Totales.put("Totales", Totales.get("Totales") + TotalOneRow);
        Totales.put("Merma", Totales.get("Merma") + MermaOneRow);

    }

    private Paragraph normFloat(double r) {
        Paragraph p = fonts.normal(String.format("%.2f", r));
        return p;
    }

    private String getTiendaString(RegistroProductos registro) {
        return registro.getDonante().getNombre();
    }

    private String getSucursalString(RegistroProductos registro) {
        return registro.getSucursal();
    }

    @Override
    protected Rectangle getPageSize() {
        return PageSize.LETTER.rotate();
    }

    public String getTitulo() {
        return TITULO;
    }

    public String getSubtitulo1() {
        return SUBTITULO1;
    }

    public String getSubtitulo2() {
        return SUBTITULO2;
    }

    public String getSubtitulo3() {
        return SUBTITULO3;
    }

    @Override
    protected String getHeader() {
        return getTitulo() + "\n" + getSubtitulo1();
    }
}
