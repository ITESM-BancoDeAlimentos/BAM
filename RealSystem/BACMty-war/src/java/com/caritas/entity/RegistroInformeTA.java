/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.entity;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author software
 */
public class RegistroInformeTA {
    private Donantes donante;
    private int cantSucursales;
    private double kilogramos;
    private double costo;
    private int year;
    private Set<String> sucursales;

    public RegistroInformeTA() {
    }
    
    public RegistroInformeTA(Donantes donante, int cantSucursales, double kilogramos, double costo, int year) {
        this.donante = donante;
        this.cantSucursales = cantSucursales;
        this.kilogramos = kilogramos;
        this.costo = costo;
        this.year = year;
    }

    public RegistroInformeTA(Donantes donante, Set<String> sucursales, double kilogramos, double costo, int year) {
        this.donante = donante;
        this.kilogramos = kilogramos;
        this.costo = costo;
        this.year = year;
        this.sucursales = sucursales;
    }


    /**
     * @return the donante
     */
    public Donantes getDonante() {
        return donante;
    }

    /**
     * @param donante the donante to set
     */
    public void setDonante(Donantes donante) {
        this.donante = donante;
    }

    /**
     * @return the cantSucursales
     */
    public int getCantSucursales() {
        if (sucursales == null) {
            return cantSucursales;
        }
        return sucursales.size();
    }

    /**
     * @param cantSucursales the cantSucursales to set
     */
    public void setCantSucursales(int cantSucursales) {
        this.cantSucursales = cantSucursales;
    }

    /**
     * @return the kilogramos
     */
    public double getKilogramos() {
        return kilogramos;
    }

    /**
     * @param kilogramos the kilogramos to set
     */
    public void setKilogramos(double kilogramos) {
        this.kilogramos = kilogramos;
    }

    /**
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(double costo) {
        this.costo = costo;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    public void addSucursal(String s) {
        getSucursales().add(s);
    }

    /**
     * @return the sucursales
     */
    private Set<String> getSucursales() {
        if (sucursales == null) {
            sucursales = new HashSet<String>(30);
        }
        return sucursales;
    }

    /**
     * @param sucursales the sucursales to set
     */
    public void setSucursales(Set<String> sucursales) {
        this.sucursales = sucursales;
    }
    

    public static class Pair {
        private RegistroInformeTA former;
        private RegistroInformeTA latter;

        public Pair() {
        }
        
        public Pair(RegistroInformeTA former, RegistroInformeTA latter) {
            this.former = former;
            this.latter = latter;
        }

        /**
         * @return the former
         */
        public RegistroInformeTA getFormer() {
            return former;
        }

        /**
         * @param former the former to set
         */
        public void setFormer(RegistroInformeTA former) {
            this.former = former;
        }

        /**
         * @return the latter
         */
        public RegistroInformeTA getLatter() {
            return latter;
        }

        /**
         * @param latter the latter to set
         */
        public void setLatter(RegistroInformeTA latter) {
            this.latter = latter;
        }

        public Donantes getDonante() {
            return former.getDonante();
        }

        public double getCantSucursalesVar() {
            double a = (double)former.getCantSucursales();
            double b = (double)latter.getCantSucursales();
            return var(b, a);
        }
        public double getKilogramosVar() {
            double a = (double)former.getKilogramos();
            double b = (double)latter.getKilogramos();
            return var(b, a);
        }
        public double getCostoVar() {
            double a = (double)former.getCosto();
            double b = (double)latter.getCosto();
            return var(b, a);
        }

        private double media(double... d) {
            double sum = 0.0;
            for (double double1 : d) {
                sum += double1;
            }
            double med = sum / ((double) d.length);
            return med;
        }

        private double varianza(double... d) {
            double m = media(d);
            double sum = 0.0;
            for (double e : d) {
                double dm = (e - m);
                sum = dm * dm;
            }
            double var = sum / ((double) (d.length));
            return var;
        }

        private double var(double b, double a) {
            if (a == 0.0) {
                return Math.abs(a * 100 / b - 100);
            } else {
                return Math.abs(b * 100 / a - 100);
            }
        }

    }

}