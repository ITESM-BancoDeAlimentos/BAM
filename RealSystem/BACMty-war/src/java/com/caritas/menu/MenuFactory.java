package com.caritas.menu;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.separator.Separator;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

@ManagedBean(name = "menuFactory")
@ApplicationScoped
public class MenuFactory implements Serializable {

    private String yamlConfigPath = "/config.yaml";
    private List<SubmenuAdaptor> mainSubmenus = null;

    public MenuModel buildFullMenu() {
        return buildMenu(new FullSubAdder(), getMainSubmenus());
    }

    public MenuModel buildNormalMenu(Set<String> allowed) {
        return buildMenu(new RestrictedSubAdder(allowed), getMainSubmenus());
    }

    public List<SubmenuAdaptor> getMainSubmenus() {
        if (mainSubmenus == null) {
            try {
                InputStream input = Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream(yamlConfigPath);
                mainSubmenus = readMainSubmenus(input);
            } catch (IOException ex) {
                Logger.getLogger(MenuFactory.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return mainSubmenus;
    }

    private MenuModel buildMenu(AbstractSubAdder add, List<SubmenuAdaptor> mainSubmenus) {
        MenuModel sMenuModel = new DefaultMenuModel();
        for (SubmenuAdaptor msubMenu : mainSubmenus) {
            Submenu submenu = msubMenu.buildSubmenu();
            add.addLinks(submenu, msubMenu.getForms());
            add.addSubmenus(submenu, msubMenu.getSubs());
            if (!submenu.getChildren().isEmpty()) {
                sMenuModel.addSubmenu(submenu);
            }
        }
        return sMenuModel;
    }

    private ArrayList<SubmenuAdaptor> readMainSubmenus(InputStream input) throws IOException {
        YamlReader yr = new YamlReader(new InputStreamReader(input));
        yr.getConfig().setClassTag("Submenu", SubmenuAdaptor.class);
        yr.getConfig().setPropertyElementType(SubmenuAdaptor.class, "forms", MenuLinkAdaptor.class);
        yr.getConfig().setPropertyElementType(SubmenuAdaptor.class, "subs", SubmenuAdaptor.class);
        Object read = yr.read();
        return (ArrayList<SubmenuAdaptor>) read;
    }

    private void exportMainSubmenus(List<SubmenuAdaptor> mainSubmenus, OutputStream baos) throws YamlException, IOException {
        YamlWriter yw = new YamlWriter(new OutputStreamWriter(baos));
        yw.write(Arrays.asList(mainSubmenus));
        yw.close();
    }
}

//<editor-fold defaultstate="collapsed" desc="MenuBuilders">
//<editor-fold defaultstate="collapsed" desc="Restriction">
class RestrictedSubAdder extends AbstractSubAdder {

    private Collection<String> allowedForms;

    RestrictedSubAdder(Collection<String> FormNamesAllowed) {
        super(true);
        this.allowedForms = FormNamesAllowed;
    }

    public void addAccessableSubmenusOnly(Submenu submenu, List<SubmenuAdaptor> subs) {
        addSubmenus(submenu, subs);
    }

    public void addAccessableLinksOnly(Submenu submenu, List<MenuLinkAdaptor> forms) {
        if (submenu == null || forms == null) {
            return;
        }
        for (MenuLinkAdaptor formAdaptor : forms) {
            boolean allowed = allowedForms.contains(formAdaptor.getName());
            boolean separator = formAdaptor.isSeparator();
            if (separator && submenu.getChildCount() > 0) {
                if (!(submenu.getChildren().get(submenu.getChildCount() - 1) instanceof Separator)) {
                    submenu.getChildren().add(new Separator());
                }
            }
            if (allowed && !formAdaptor.isDisabled()) {
                MenuItem menuItem = formAdaptor.buildMenuItem();
                submenu.getChildren().add(menuItem);
            }
        }
    }

    @Override
    public void addLinks(Submenu submenu1, List<MenuLinkAdaptor> forms) {
        addAccessableLinksOnly(submenu1, forms);
    }
}
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="No Restriction">

class FullSubAdder extends AbstractSubAdder {

    FullSubAdder() {
        super(false);
    }

    public void addAllLinks(Submenu submenu, List<MenuLinkAdaptor> forms) {
        if (submenu == null || forms == null) {
            return;
        }
        for (MenuLinkAdaptor formAdaptor : forms) {
            submenu.getChildren().add(formAdaptor.isSeparator()
                    ? (UIComponent) new Separator()
                    : (UIComponent) formAdaptor.buildMenuItem());
        }
    }

    @Override
    public void addLinks(Submenu submenu1, List<MenuLinkAdaptor> forms) {
        addAllLinks(submenu1, forms);
    }
}
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Common">
abstract class AbstractSubAdder {

    private boolean accessableOnly;

    AbstractSubAdder(boolean accessableOnly) {
        this.accessableOnly = accessableOnly;
    }

    public void addSubmenus(Submenu submenu, List<SubmenuAdaptor> subs) {
        if ((submenu == null) || (subs == null)) {
            return;
        }
        for (SubmenuAdaptor submenuA : subs) {
            Submenu submenu1 = submenuA.buildSubmenu();
            addLinks(submenu1, submenuA.getForms());
            addSubmenus(submenu1, submenuA.getSubs());
            if (!submenu1.getChildren().isEmpty() || !accessableOnly) {
                submenu.getChildren().add(submenu1);
            }
        }

    }

    public abstract void addLinks(Submenu submenu1, List<MenuLinkAdaptor> forms);
}
//</editor-fold>
//</editor-fold>
