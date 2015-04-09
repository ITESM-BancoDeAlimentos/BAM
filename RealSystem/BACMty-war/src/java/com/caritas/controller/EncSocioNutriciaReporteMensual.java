/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.controller;

/**
 *
 * @author software
 */
public class EncSocioNutriciaReporteMensual {

    private int miembros;
    private int noExpediente;
    private Integer paquetes;
    private int edadMujeres[] = {0, 0, 0, 0};
    private int edadHombres[] = {0, 0, 0, 0};
    private String jefeFamilia;
    private String municipio;
    private String comunidad;
    private int noFamilia;
    private int totalMiembros;

    public EncSocioNutriciaReporteMensual() {
    }

    public EncSocioNutriciaReporteMensual(int miembros, int noExpediente,
            Integer paquetes, int[] edadMujeres, int[] edadHombres,
            String jefeFamilia, String comunidad, String municipio,
            int noFamilia, int totalMiembros) {

        this.miembros = miembros;
        this.noExpediente = noExpediente;
        this.paquetes = paquetes;
        this.edadMujeres = edadMujeres;
        this.edadHombres = edadHombres;

        this.jefeFamilia = jefeFamilia;
        this.comunidad = comunidad;
        this.municipio = municipio;
        
        this.noFamilia = noFamilia;
        this.totalMiembros = totalMiembros;
    }
    //<editor-fold defaultstate="collapsed" desc="gets sets ReporteMensual">

    public int getMiembros() {
        return miembros;
    }

    public void setMiembros(int miembros) {
        this.miembros = miembros;
    }

    public String getJefeFamilia() {
        return jefeFamilia;
    }

    public void setJefeFamilia(String jefeFamilia) {
        this.jefeFamilia = jefeFamilia;
    }

    public int[] getEdadHombres() {
        return edadHombres;
    }

    public void setEdadHombres(int[] edadHombres) {
        this.edadHombres = edadHombres;
    }

    public int[] getEdadMujeres() {
        return edadMujeres;
    }

    public void setEdadMujeres(int[] edadMujeres) {
        this.edadMujeres = edadMujeres;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public int getNoFamilia() {
        return noFamilia;
    }

    public void setNoFamilia(int noFamilia) {
        this.noFamilia = noFamilia;
    }

    public int getNoExpediente() {
        return noExpediente;
    }

    public void setNoExpediente(int noExpediente) {
        this.noExpediente = noExpediente;
    }

    public int getTotalMiembros() {
        return totalMiembros;
    }

    public void setTotalMiembros(int totalMiembros) {
        this.totalMiembros = totalMiembros;
    }
    
    public Integer getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(Integer paquetes) {
        this.paquetes = paquetes;
    }
    //</editor-fold>
}
