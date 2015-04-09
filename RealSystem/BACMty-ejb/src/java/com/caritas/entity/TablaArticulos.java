package com.caritas.entity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TablaArticulos {

    private String clave;
    private String descripcion;
    private Unidad unidadMedida;
    private Double cantidad;
    private Double pesoUnitario;
    private Double pesoTotal;
    private Double costoEntrada;
    private Date fechaCaducidad = new Date();
    private Double total;
    private String fechaCaducidadAImprimir;
    private DecimalFormat df = new DecimalFormat("#.##");
    private Double cuotaRecup;
    private Double merma;

    public TablaArticulos() {
    }

    public TablaArticulos(String clave, String descripcion, Unidad unidadMedida, Double cantidad,
            Double pesoUnitario, Double pesoTotal, Double costoEntrada, Date fechaCaducidad, Double total, Double cuotaRecup) {
        this.clave = clave;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.cantidad = cantidad;
        this.pesoUnitario = pesoUnitario;
        this.pesoTotal = pesoTotal;
        this.costoEntrada = costoEntrada;
        this.fechaCaducidad = fechaCaducidad;
        this.total = total;
        this.cuotaRecup = cuotaRecup;
    }
    
    public TablaArticulos(String clave, String descripcion, Unidad unidadMedida, Double cantidad,
            Double pesoUnitario, Double pesoTotal, Double costoEntrada, Date fechaCaducidad, Double total, Double cuotaRecup, Double merma) {
        this.clave = clave;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.cantidad = cantidad;
        this.pesoUnitario = pesoUnitario;
        this.pesoTotal = pesoTotal;
        this.costoEntrada = costoEntrada;
        this.fechaCaducidad = fechaCaducidad;
        this.total = total;
        this.cuotaRecup = cuotaRecup;
        this.merma = merma;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Unidad getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(Unidad unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getPesoUnitario() {
        return df.format(pesoUnitario);
    }

    public void setPesoUnitario(Double pesoUnitario) {
        this.pesoUnitario = pesoUnitario;
    }

    public String getPesoTotal() {
        return df.format(pesoTotal);
    }

    public void setPesoTotal(Double pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public String getCostoEntrada() {
        return df.format(costoEntrada);
    }

    public void setCostoEntrada(Double costoEntrada) {
        this.costoEntrada = costoEntrada;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getTotal() {
        return df.format(total);
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getFechaCaducidadAImprimir() {
        if (clave.trim().isEmpty()) {
            return "";
        }
        if (fechaCaducidad != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            fechaCaducidadAImprimir = format.format(fechaCaducidad);
        }
        return fechaCaducidadAImprimir;
    }

    public void setFechaCaducidadAImprimir(String fechaCaducidadAImprimir) {
        this.fechaCaducidadAImprimir = fechaCaducidadAImprimir;
    }

    public Double getCuotaRecup() {
        return cuotaRecup;
    }

    public void setCuotaRecup(Double cuotaRecup) {
        this.cuotaRecup = cuotaRecup;
    }

    public Double getMerma() {
        return merma;
    }

    public void setMerma(Double merma) {
        this.merma = merma;
    }
}
