package com.caritas.enums;

public enum Genero {
    
    MASCULINO("M", "Genero_M"), 
    FEMENINO ("F", "Genero_F");
    
    private final String codigo;
    private final String labelKey;

    public String getCodigo() {
        return codigo;
    }

    public String getLabelKey() {
        return labelKey;
    }

    private Genero(String code, String labelKey) {
        this.codigo = code;
        this.labelKey = labelKey;
    }
    
}
