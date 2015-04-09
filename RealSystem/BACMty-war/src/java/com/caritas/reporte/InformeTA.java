/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.reporte;

import com.caritas.entity.Donantes;
import com.caritas.entity.Movimientos;
import com.caritas.entity.MovtosDet;
import com.caritas.entity.RegistroInformeTA;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author software
 */
public class InformeTA {
    private static final Logger LOG = Logger.getLogger(InformeTA.class.getName());

    private List<Movimientos> formerYearMovs;
    private List<Movimientos> latterYearMovs;
    private List<Donantes> tiendasDonantes;

    private int formerYear;
    private int latterYear;

    public InformeTA() {
        formerYearMovs = new ArrayList(0);
        latterYearMovs = new ArrayList(0);
        tiendasDonantes = new ArrayList(0);
        formerYear = 0;
        latterYear = 0;
    }

    public InformeTA(List<Movimientos> formerYearMovs, List<Movimientos> latterYearMovs, List<Donantes> tiendasDonantes, int formerYear, int latterYear) {
        this.formerYearMovs = formerYearMovs;
        this.latterYearMovs = latterYearMovs;
        this.tiendasDonantes = tiendasDonantes;
        this.formerYear = formerYear;
        this.latterYear = latterYear;
    }

    public Collection<RegistroInformeTA.Pair> getPairs() {
        LinkedList<RegistroInformeTA.Pair> pairs;
        pairs = new LinkedList<RegistroInformeTA.Pair>();
        for (Donantes d : getTiendasDonantes()) {
            pairs.add(getPairFor(d));
        }
        return pairs;
    }

    public Collection<RegistroInformeTA.Pair> getOPairs() {
        Map<String, RegistroInformeTA.Pair> m = buildDonanteRPairMap();
        assignLatterYear(m);
        assignFormerYear(m);
        return m.values();
    }

    private RegistroInformeTA.Pair getPairFor(Donantes d) {
        RegistroInformeTA.Pair pair = new RegistroInformeTA.Pair();
        pair.setLatter(getLatterYearFor(d));
        pair.setFormer(getFormerYearFor(d));
        return pair;
    }

    private RegistroInformeTA getLatterYearFor(Donantes d) {
        return getRITAFor(d, getLatterYearMovs(), getLatterYear());
    }

    private RegistroInformeTA getFormerYearFor(Donantes d) {
        return getRITAFor(d, getFormerYearMovs(), getFormerYear());
    }

    private RegistroInformeTA getRITAFor(Donantes d, List<Movimientos> movs, int givenYear) {
        double peso;
        double cantidad;
        double costoBenef;
        double kilogramos = 0.0;
        double costo = 0.0;
        HashSet<String> sucursales = new HashSet<String>(30);
        
        for (Movimientos m : movs) try {
            if (!m.getIDDonante().equals(d)) { continue; }
            
            Collection<MovtosDet> mds = m.getMovtosDetCollection();
            for (MovtosDet md : mds) {
                peso        = (md.getIDArticulo().getPeso() == null) ? 1.0 : md.getPeso();
                cantidad    = (md.getCantidad());
                costoBenef  = (md.getCostoBenef() == null) ? 0.0 : md.getCostoBenef();

                kilogramos  += peso * cantidad;
                costo       += costoBenef;
            }
            sucursales.add(m.getIDSucursal());
        } catch (NullPointerException e) {
            logNPE(e, m, d, givenYear);
        }
        return new RegistroInformeTA(d, sucursales, kilogramos, costo, givenYear);
    }
    
    /**
     * @return the formerYearMovs
     */
    public List<Movimientos> getFormerYearMovs() {
        return formerYearMovs;
    }
    
    /**
     * @param formerYearMovs the formerYearMovs to set
     */
    public void setFormerYearMovs(List<Movimientos> formerYearMovs) {
        this.formerYearMovs = formerYearMovs;
    }
    
    /**
     * @return the latterYearMovs
     */
    public List<Movimientos> getLatterYearMovs() {
        return latterYearMovs;
    }
    
    /**
     * @param latterYearMovs the latterYearMovs to set
     */
    public void setLatterYearMovs(List<Movimientos> latterYearMovs) {
        this.latterYearMovs = latterYearMovs;
    }
    
    /**
     * @return the tiendasDonantes
     */
    public List<Donantes> getTiendasDonantes() {
        return tiendasDonantes;
    }
    
    /**
     * @param tiendasDonantes the tiendasDonantes to set
     */
    public void setTiendasDonantes(List<Donantes> tiendasDonantes) {
        this.tiendasDonantes = tiendasDonantes;
    }
    
    /**
     * @return the formerYear
     */
    public int getFormerYear() {
        return formerYear;
    }
    
    /**
     * @param formerYear the formerYear to set
     */
    public void setFormerYear(int formerYear) {
        this.formerYear = formerYear;
    }
    
    /**
     * @return the latterYear
     */
    public int getLatterYear() {
        return latterYear;
    }
    
    /**
     * @param latterYear the latterYear to set
     */
    public void setLatterYear(int latterYear) {
        this.latterYear = latterYear;
    }
    
    private Map<String, RegistroInformeTA.Pair> buildDonanteRPairMap() {
        Map<String, RegistroInformeTA.Pair> m = new TreeMap<String, RegistroInformeTA.Pair>();
        for(Donantes d : getTiendasDonantes()) {
            RegistroInformeTA.Pair p = new RegistroInformeTA.Pair();
            RegistroInformeTA former = new RegistroInformeTA();
            RegistroInformeTA latter = new RegistroInformeTA();
            former.setDonante(d);
            latter.setDonante(d);
            p.setFormer(former);
            p.setLatter(latter);
            m.put(d.getIDDonante(), p);
        }
        return m;
    }
    
    private void assignMap(Map<String, RegistroInformeTA.Pair> map, boolean isLatter) {
        double peso;
        double cantidad;
        double costoBenef;
        double tPeso;
        Donantes donante  = null;
        String idSucursal = null;
        RegistroInformeTA latter = null;
        List<Movimientos> movs;
        
        movs = (isLatter) ? latterYearMovs : formerYearMovs;
        for (Movimientos m : movs) try {
            donante = m.getIDDonante();
            idSucursal = m.getIDSucursal();
            latter = (isLatter) ? map.get(donante.getIDDonante()).getLatter()
                    : map.get(donante.getIDDonante()).getFormer();
            
            Collection<MovtosDet> movtosDetCollection = m.getMovtosDetCollection();
            for (MovtosDet md : movtosDetCollection) {
                peso        = (md.getIDArticulo().getPeso() == null) ? 1.0 : md.getPeso();
                cantidad    = md.getCantidad();
                costoBenef  = (md.getCostoBenef() == null) ? 0.0 : md.getCostoBenef();
                tPeso       = (peso * cantidad);

                latter.setKilogramos(latter.getKilogramos() + tPeso);
                latter.setCosto(latter.getCosto() + costoBenef);
            }
            latter.addSucursal(idSucursal);
        } catch (NullPointerException e) {
            logNPE(e, m, donante, getLatterYear());
        }
    }
    
    private void assignLatterYear(Map<String, RegistroInformeTA.Pair> m) {
        assignMap(m, true);
    }

    private void assignFormerYear(Map<String, RegistroInformeTA.Pair> m) {
        assignMap(m, false);
    }
    
    private void logNPE(NullPointerException e, Movimientos m, Donantes d, int givenYear) {
        LOG.warning(e.toString());
        if (m == null) {
            LOG.warning("En getRITAFor uno de los movimientos dados es null");
            return;
        }
        if (d == null) {
            LOG.warning("En getRITAFor uno de los donantes dados es null");
            return;
        }
        LOG.log(Level.WARNING, "NPE Movimiento: {0} Donante: {1} year: {2}", new Object[]{ m.getIDFolio(), d.getIDDonante(), givenYear});
    }
    
}
