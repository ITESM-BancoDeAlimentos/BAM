/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author software
 */
public final class EjbUtil {
    public static final String EJB_CONTEXT = "java:global/BACMty/BACMty-ejb/";

    public static <T> T lookup(Class<T> ejbClass) {
        try {
            Context c = new InitialContext();
            String name = ejbClass.getSimpleName();
            return (T) c.lookup(EJB_CONTEXT + name);
        } catch (NamingException ne) {
            Logger.getLogger(EjbUtil.class.getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private  EjbUtil() {
    }
}
