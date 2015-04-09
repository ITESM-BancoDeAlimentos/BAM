package com.caritas.controller.util;

import com.caritas.entity.Donantes;
import com.caritas.facade.DonantesFacade;
import com.caritas.facade.ParametrosFacade;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author software
 */
@ManagedBean
@ApplicationScoped
public class ParametersController {

    @EJB
    private DonantesFacade donantesFacade;
    @EJB
    private ParametrosFacade p;
    private Donantes DONANTE_HEB;
    private Donantes DONANTE_SORIANA;
    private Donantes DONANTE_WALMART;
    private Donantes DONANTE_SAMS;
    private Donantes DONANTE_AURRERA;
    private final int suggestionLength = 20;

    public int getSuggestionLength() {
        return suggestionLength;
    }
    
    public Donantes getDonanteHeb() {
        if (DONANTE_HEB == null) {
            DONANTE_HEB = donantesFacade.find(p.find("HEB_ID"));
        }
        return DONANTE_HEB;
    }

    public Donantes getDonanteSoriana() {
        if (DONANTE_SORIANA == null) {
            DONANTE_SORIANA = donantesFacade.find(p.find("SORIANA_ID"));
        }
        return DONANTE_SORIANA;
    }

    public Donantes getDonanteWalmart() {
        if (DONANTE_WALMART == null) {
            DONANTE_WALMART = donantesFacade.find(p.find("WALMART_ID"));
        }
        return DONANTE_WALMART;
    }

    private void getDonanteSams() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Donantes getDonanteAurrera() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
