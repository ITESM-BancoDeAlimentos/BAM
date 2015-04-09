package com.caritas.enums;

public enum Almacen {

    Almacen      ("A"),
    Clasificacion("C");

    private Almacen(String code) {
        this.code = code;
    }
    
    private String code;

    public String getCode() {
        return code;
    }
     
}
