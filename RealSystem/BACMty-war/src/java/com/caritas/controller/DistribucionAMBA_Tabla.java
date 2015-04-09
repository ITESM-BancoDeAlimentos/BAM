package com.caritas.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DistribucionAMBA_Tabla {

    private boolean recibe;
    private String remision;
    private String bancoDeAlimentos;
    private int poblacion;
    private Double porcentaje;
    private Double kilogramos;
    private int piezas;
    private Double flete;
    private String observaciones;

    public DistribucionAMBA_Tabla() {
    }

    public DistribucionAMBA_Tabla(boolean recibe, String remision,
            String bancoDeAlimentos, int poblacion, Double porcentaje,
            Double kilogramos, int piezas, Double flete, String observaciones) {
        this.recibe = recibe;
        this.remision = remision;
        this.bancoDeAlimentos = bancoDeAlimentos;
        this.poblacion = poblacion;
        this.porcentaje = porcentaje;
        this.kilogramos = kilogramos;
        this.piezas = piezas;
        this.flete = flete;
        this.observaciones = observaciones;
    }

    /**
     * Redondea un double a la cantidad de decimales deseadas hacia arriba
     *
     * @param valor El valor a redondear
     * @param decimales La cantidad de decimales deseadas
     * @return El valor redondeado
     */
    public Double RedondearDouble(Double valor, int decimales) {
        BigDecimal big = new BigDecimal(valor);
        big = big.setScale(decimales, RoundingMode.HALF_UP);

        return big.doubleValue();
    }

    //<editor-fold defaultstate="collapsed" desc="SETTERS & GETTERS">
    public boolean getRecibe() {
        return recibe;
    }

    public void setRecibe(boolean recibe) {
        this.recibe = recibe;
    }

    public String getRemision() {
        return remision;
    }

    public void setRemision(String remision) {
        this.remision = remision;
    }

    public String getBancoDeAlimentos() {
        return bancoDeAlimentos;
    }

    public void setBancoDeAlimentos(String bancoDeAlimentos) {
        this.bancoDeAlimentos = bancoDeAlimentos;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public Double getPoblacionDouble() {
        return Double.valueOf(String.valueOf(poblacion));
    }

    public int getPoblacionTabla() {
        if (recibe) {
            return poblacion;
        } else {
            return 0;
        }
    }

    public void setPoblacion(int poblacion) {
        this.poblacion = poblacion;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public String getPorcentajeString() {
        Double temp = RedondearDouble(porcentaje, 1);

        return temp.toString();
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Double getKilogramos() {
        return kilogramos;
    }

    public String getKilogramosString() {
        Double temp = RedondearDouble(kilogramos, 2);

        return temp.toString();
    }

    public void setKilogramos(Double kilogramos) {
        this.kilogramos = kilogramos;
    }

    public int getPiezas() {
        return piezas;
    }

    public void setPiezas(int piezas) {
        this.piezas = piezas;
    }

    void setPiezasDouble(Double valor) {
        BigDecimal big = new BigDecimal(valor);
        big = big.setScale(0, RoundingMode.HALF_UP);

        this.piezas = big.intValue();
    }

    public Double getFlete() {
        if (flete == null) {
            flete = 0.0;
        }
        return flete;
    }

    public String getFleteString() {
        String fleteString = "$ ";
        if (flete == 0.0) {
            return fleteString + "- ";
        } else {
            Double temp = RedondearDouble(flete, 2);

            return fleteString + temp.toString();
        }
    }

    public void setFlete(Double flete) {
        if (getRecibe()) {
            this.flete = flete;
        } else {
            this.flete = 0.0;
        }
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    //</editor-fold>
}
