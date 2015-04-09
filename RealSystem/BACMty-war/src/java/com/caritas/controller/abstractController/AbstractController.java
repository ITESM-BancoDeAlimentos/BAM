/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caritas.controller.abstractController;

import javax.faces.model.DataModel;

/**
 *
 * @param <T>
 * @author talento
 */
public abstract class AbstractController<T> implements CrudController {

    public abstract T getCurrent();

    public abstract void setCurrent(T current);

    public abstract DataModel<T> getItems();

    protected abstract String performDestroy(T rowData);

    @Override
    public String prepareView() {
        setCurrent(getItems().getRowData());
        return "View";
    }

    @Override
    public String prepareEdit() {
        setCurrent(getItems().getRowData());
        return "Edit";
    }

    @Override
    public String prepareViewNoRow() {
        return "View";
    }

    @Override
    public String prepareEditNoRow() {
        return "Edit";
    }

    @Override
    public String prepareList() {
        return "List";
    }

    @Override
    public String destroyNoRow() {
        T current = getCurrent();
        return performDestroy(current);
    }

    @Override
    public String destroy() {
        T rowData = getItems().getRowData();
        return performDestroy(rowData);
    }
}
