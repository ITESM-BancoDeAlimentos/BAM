package com.caritas.enums;

import java.util.HashMap;
import java.util.Map;

public enum Origen {

    DONANTE("D", "Donante", TipoMov.ENTRADA),
    COMPRA("C", "Compra", TipoMov.ENTRADA),
    AMBA("A", "Amba", TipoMov.ENTRADA),
    DONATIVO("1", "Donativo", TipoMov.SALIDA),
    VENTA("2", "Venta", TipoMov.SALIDA);
    private String codigo;
    private String nombre;
    private TipoMov tipoMov;
    private static final Map<String, Origen> codeMap;

    private Origen(String codigo, String nombre, TipoMov tipoMov) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipoMov = tipoMov;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoMov getTipoMov() {
        return tipoMov;
    }

    static {
        Origen[] values = values();
        Map<String, Origen> codeMap2 = new HashMap<String, Origen>(values.length);
        for (Origen o : values) {
            codeMap2.put(o.getCodigo(), o);
        }
        codeMap = codeMap2;
    }

    public static Origen valueByCodigo(String codigo) {
        return codeMap.get(codigo);
    }
}
