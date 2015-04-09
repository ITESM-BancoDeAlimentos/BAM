
package com.caritas.controller;

public class ValeCUDE_Tabla {
    
    private String folioAImprimir;
    private String donanteAImprimir;
    private String sucursalAImprimir;
    private String fechaAImprimir;
    private String direccionAImprimir;
    private String coloniaAImprimir;
    private String ciudadAImprimir;
    private boolean direccionEnabled;

    public ValeCUDE_Tabla(){
    }
    
    public ValeCUDE_Tabla(String folioAImprimir, String donanteAImprimir, String fechaAImprimir,
            String direccionAImprimir, String sucursalAImprimir, String coloniaAImprimir, String ciudadAImprimir, boolean direccionEnabled) {
        this.folioAImprimir = folioAImprimir;
        this.donanteAImprimir = donanteAImprimir;
        this.fechaAImprimir = fechaAImprimir;
        this.direccionAImprimir = direccionAImprimir;
        this.sucursalAImprimir = sucursalAImprimir;
        this.coloniaAImprimir = coloniaAImprimir;
        this.ciudadAImprimir = ciudadAImprimir;
        this.direccionEnabled = direccionEnabled;
    }
    
    public String getFolioAImprimir() {
        return folioAImprimir;
    }

    public void setFolioAImprimir(String folioAImprimir) {
        this.folioAImprimir = folioAImprimir;
    }

    public String getDonanteAImprimir() {
        return donanteAImprimir;
    }

    public void setDonanteAImprimir(String donanteAImprimir) {
        this.donanteAImprimir = donanteAImprimir;
    }

    public String getFechaAImprimir() {
        return fechaAImprimir;
    }

    public void setFechaAImprimir(String fechaAImprimir) {
        this.fechaAImprimir = fechaAImprimir;
    }

    public String getDireccionAImprimir() {
        return direccionAImprimir;
    }

    public void setDireccionAImprimir(String direccionAImprimir) {
        this.direccionAImprimir = direccionAImprimir;
    }

    public String getSucursalAImprimir() {
        return sucursalAImprimir;
    }

    public void setSucursalAImprimir(String sucursalAImprimir) {
        this.sucursalAImprimir = sucursalAImprimir;
    }

    public String getColoniaAImprimir() {
        return coloniaAImprimir;
    }

    public void setColoniaAImprimir(String coloniaAImprimir) {
        this.coloniaAImprimir = coloniaAImprimir;
    }

    public String getCiudadAImprimir() {
        return ciudadAImprimir;
    }

    public void setCiudadAImprimir(String ciudadAImprimir) {
        this.ciudadAImprimir = ciudadAImprimir;
    }

    public boolean isDireccionEnabled() {
        return direccionEnabled;
    }

    public void setDireccionEnabled(boolean direccionEnabled) {
        this.direccionEnabled = direccionEnabled;
    }
}
