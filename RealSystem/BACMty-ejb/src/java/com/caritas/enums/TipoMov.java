package com.caritas.enums;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum TipoMov {

    ENTRADA("ENT", "Entrada"),
    SALIDA("SAL", "Salida");
    private String codigo;
    private String nombre;
    private static Map<TipoMov, Set<Origen>> classMap = null;
    private static final Map<String, TipoMov> codeMap;

    private TipoMov(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public static TipoMov valueByCodigo(String codigo) {
        return codeMap.get(codigo);
    }

    public Set<Origen> getOrigenSet() {
        return getClassMap().get(this);
    }

    static Map<TipoMov, Set<Origen>> getClassMap() {
        if (classMap == null) {
            initializeClassMap();
        }
        return classMap;
    }

    private static void initializeClassMap() {
        TipoMov[] tvalues = TipoMov.values();
        Origen[] ovalues = Origen.values();
        Map<TipoMov, Set<Origen>> classMap2 = new EnumMap<TipoMov, Set<Origen>>(TipoMov.class);
        for (TipoMov t : tvalues) {
            Set<Origen> set = EnumSet.noneOf(Origen.class);
            classMap2.put(t, set);
        }
        for (Origen o : ovalues) {
            classMap2.get(o.getTipoMov()).add(o);
        }
        classMap = classMap2;
    }

    static {
        TipoMov[] values = TipoMov.values();
        Map<String, TipoMov> codeMap2 = new HashMap<String, TipoMov>(values.length);
        for (TipoMov t : values) {
            codeMap2.put(t.codigo, t);
        }
        codeMap = codeMap2;
    }

}