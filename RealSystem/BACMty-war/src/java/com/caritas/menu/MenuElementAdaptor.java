package com.caritas.menu;

import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MenuElementAdaptor implements Serializable {

    private static final String DEFAULT_NAME = "DefaultName";
    private static final String DEFAULT_LABEL = "DefaultLabel";
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("/Bundle");
    private static final Logger LOG = Logger.getLogger(MenuElementAdaptor.class.getName());

    protected String name;
    protected String label;
    protected boolean separator;

    public MenuElementAdaptor() {
        name = DEFAULT_NAME;
        label = DEFAULT_LABEL;
        separator = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isSeparator() {
        return separator;
    }

    public void setSeparator(boolean separator) {
        this.separator = separator;
    }

    protected String manageLabel() {
        if (label.matches("^\\{.+\\}$")) {
            try {
                return BUNDLE.getString(label.substring(1, label.length() - 1));
            } catch (MissingResourceException e) {
                LOG.severe("MenuEl: " + name + " Label: " + label + "not in bundle");
            }
        }
        return label;
    }
}
