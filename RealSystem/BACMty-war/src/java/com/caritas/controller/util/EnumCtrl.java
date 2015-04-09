/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.controller.util;

import com.caritas.enums.AreaGeografica;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author software
 */
@ManagedBean
@ApplicationScoped
public class EnumCtrl {

    ResourceBundle bundle = ResourceBundle.getBundle("/Bundle");

    public EnumCtrl() {
    }

    SelectItem[] areaGeograficaItems = null;
    public SelectItem[] getAreaGeograficaItems() {
        if (areaGeograficaItems == null) {
            areaGeograficaItems = loadAreaGeograficaItems();
        }
        return areaGeograficaItems;
    }
    
    private SelectItem[] loadAreaGeograficaItems() {
        SelectItem[] sis = new SelectItem[AreaGeografica.values().length + 1];
        int i = 0;
        sis[i++] = new SelectItem(null, bundle.getString("SelectOne"));
        for (AreaGeografica a : AreaGeografica.values()) {
            sis[i++] = new SelectItem(a, bundle.getString(a.getLabelKey()));
        }
        return sis;
    }

    /*
     private <T extends Enum<T>> SelectItem[] loadEnumTtems(Class<T> enumType) {
     T[] enumConstants = enumType.getEnumConstants();
     SelectItem[] items = new SelectItem[enumConstants.length];
     int i = 0;
     for (T g : enumConstants) {
     items[i++] = new SelectItem(g, bundle.getString(g.getLabelKey()));
     }
     return items;
     }
     * 
     */
}
