package com.caritas.menu;

import com.caritas.controller.util.JsfUtil;
import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.event.MethodExpressionActionListener;
import org.primefaces.component.menuitem.MenuItem;

public class MenuLinkAdaptor extends MenuElementAdaptor implements Serializable {
    private static final Logger LOG = Logger.getLogger(MenuLinkAdaptor.class.getName());
    private static final String DEFAULT_OUTCOME = "#";
    private static final String DEFAULT_ICON = "ui-icon-close";

    private String outcome;
    private boolean disabled;
    private String actionListener;
    private String icon;
    private String string;

    public MenuLinkAdaptor() {
        this.outcome = DEFAULT_OUTCOME;
        this.icon = DEFAULT_ICON;
        this.actionListener = "";
        this.disabled = false;
    }

    public String getActionListener() {
        return actionListener;
    }

    public void setActionListener(String actionListener) {
        this.actionListener = actionListener;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public boolean isDisabled() {
        return !isValidOutCome() || disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private boolean isValidOutCome() {
        /*
         * TODO: Hacer que realmente revise el outcome.
         * Cuando se le da un outcome no valido al menuitem, toda la aplicacion
         * crashea. Es un problema del mismo glassfish.
         */
        return !getOutcome().equalsIgnoreCase(DEFAULT_OUTCOME);
    }

    public MenuItem buildMenuItem() {
        MenuItem menuItem = new MenuItem();

        menuItem.setDisabled(isDisabled());
        menuItem.setValue(manageLabel());
        manageOutputField(menuItem);
        return menuItem;
    }

    private void setActionListener(MenuItem menuItem) {
        try {
            MethodExpression me = JsfUtil.createMethodExpression(getActionListener(), null);
            MethodExpressionActionListener meal = new MethodExpressionActionListener(me);
            menuItem.addActionListener(meal);
            buttonize(menuItem);
        } catch (ELException e) {
            LOG.severe("MenuLink: " + name + " Outcome: " + outcome + " threw an ELException");
        } catch (NullPointerException e) {
            LOG.severe("MenuLink: " + name + " Outcome: " + outcome + " threw an NullPointerException");
        }
    }

    private void setActionExpression(MenuItem menuItem) {
        try {
            MethodExpression me = JsfUtil.createMethodExpression(getOutcome(), null);
            menuItem.setActionExpression(me);
            buttonize(menuItem);
        } catch (Exception e) {
            LOG.severe("MenuLink: " + name + " Outcome: " + outcome + " threw an Exception");
        }
    }

    private void manageOutputField(MenuItem menuItem) {
        if (!actionListener.isEmpty()) {
            setActionListener(menuItem);
        }
        if (getOutcome().matches("^#\\{.+\\}$")) {
            setActionExpression(menuItem);
        } else if (isValidOutCome()) {
            menuItem.setOutcome(getOutcome());
        }
    }

    private void buttonize(MenuItem menuItem) {
        menuItem.setImmediate(true);
        menuItem.setAjax(false);
        menuItem.setIcon(getIcon());
    }
}