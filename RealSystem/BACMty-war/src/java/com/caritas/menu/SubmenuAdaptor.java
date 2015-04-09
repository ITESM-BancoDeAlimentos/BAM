/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.component.submenu.Submenu;

/**
 *
 * @author software
 */
public class SubmenuAdaptor extends MenuElementAdaptor implements Serializable {
    private List<SubmenuAdaptor>  subs;
    private List<MenuLinkAdaptor> forms;
    
    public SubmenuAdaptor() {
        this.subs  = new ArrayList<SubmenuAdaptor>(0) ;
        this.forms = new ArrayList<MenuLinkAdaptor>(0);
    }

    public Submenu buildSubmenu () {
        Submenu submenu = new Submenu();
        submenu.setLabel(manageLabel());
        return submenu;
    }

    /**
     * @return the subs
     */
    public List<SubmenuAdaptor> getSubs() {
        return subs;
    }

    /**
     * @param subs the subs to set
     */
    public void setSubs(List<SubmenuAdaptor> subs) {
        this.subs = subs;
    }

    /**
     * @return the forms
     */
    public List<MenuLinkAdaptor> getForms() {
        return forms;
    }

    /**
     * @param forms the forms to set
     */
    public void setForms(List<MenuLinkAdaptor> forms) {
        this.forms = forms;
    }
}
