/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author software
 */
public class RegistroProductos {

    private static final String[] CLASES_PANADERIA = {
        "Panaderia Dulce",
        "Panaderia Pastel",
        "Panaderia Sal",
        "Panaderia Tortilla",
        "Panaderia Molido",
    };
    private final DonanteSucursalKey dsk;
    private final Map<String, CantidadMermaValue> cells;

    public RegistroProductos(DonanteSucursalKey dsk, Map<String, CantidadMermaValue> cells) {
        this.dsk = dsk;
        this.cells = cells;
    }
    public RegistroProductos(DonanteSucursalKey k, String clase, CantidadMermaValue v) {
        this.dsk = k;
        this.cells = new TreeMap<String, CantidadMermaValue>();
        cells.put(clase, v);
    }
    
    public void addToCell(String clase, CantidadMermaValue v) {
        if (getCells().containsKey(clase)) {
            getCells().get(clase).add(v);
        } else {
            getCells().put(clase, v);
        }
    }
    public boolean containsKey(String clase) {
        return getCells().containsKey(clase);
    }
    public CantidadMermaValue get(String clase) {
        if (cells.containsKey(clase)) {
            return new CantidadMermaValue();
        } 
        return cells.get(clase);
    }
    public double getCantidad(String clase) {
        if (cells.containsKey(clase)) {
            return cells.get(clase).cantidad;
        } else {
            return 0.0;
        }
    }
    public double getMerma(String clase) {
        if (cells.containsKey(clase)) {
            return cells.get(clase).merma;
        } else {
            return 0.0;
        }

    }

    public Map<String, CantidadMermaValue> getCells() {
        return cells;
    }

    public Donantes getDonante() {
        return dsk.donante;
    }
    public String getSucursal() {
        return dsk.sucursal;
    }

    @Override
    public String toString() {
        return "RegistroProductos{" + "dsk=" + dsk + ", cells=" + getCells() + '}';
    }

    public double getMermaPanaderia() {
        double suma = 0;
        for (String clase : CLASES_PANADERIA) {
            if (cells.containsKey(clase)) {
                suma += cells.get(clase).merma;
            }
        }
        return suma;
    }

    //<editor-fold defaultstate="collapsed" desc="Clases">
    /**
     *
     * @author software
     */
    public static class CantidadMermaValue {

        public double cantidad;
        public double merma;

        public CantidadMermaValue() {
            super();
            this.cantidad = 0.0;
            this.merma = 0.0;
        }

        public CantidadMermaValue(Double cantidad, Double merma) {
            super();
            this.cantidad = cantidad == null ? 0.0 : cantidad;
            this.merma = merma == null ? 0.0 : merma;
        }

        public void add(CantidadMermaValue o) {
            addCantidad(o.cantidad);
            addMerma(o.merma);
        }

        public void addCantidad(Double extra) {
            cantidad += (extra == null) ? 0 : extra.doubleValue();
        }

        public void addMerma(Double extra) {
            cantidad += (extra == null) ? 0 : extra.doubleValue();
        }

        @Override
        public String toString() {
            return "CantidadMermaValue{" + "cantidad=" + cantidad + ", merma=" + merma + '}';
        }
    }

    /**
     *
     * @author software
     */
    public static class DonanteSucursalKey implements Comparable<DonanteSucursalKey> {

        public Donantes donante;
        public String sucursal = "";

        public DonanteSucursalKey() {
            super();
        }

        public DonanteSucursalKey(Donantes donante, String sucursal) {
            super();
            this.donante = donante;
            this.sucursal = (sucursal == null) ? "" : sucursal;
        }

        @Override
        public int compareTo(DonanteSucursalKey other) {
            int compareSucursales = sucursal.compareTo(other.sucursal);
            int compareDonantes = donante.getIDDonante().compareTo(other.donante.getIDDonante());
            if (compareDonantes != 0) {
                return compareDonantes;
            } else {
                return compareSucursales;
            }
        }

        @Override
        public String toString() {
            return "SucursalKey{" + "donante=" + donante + ", sucursal=" + sucursal + '}';
        }
    }
    //</editor-fold>

}
