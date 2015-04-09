package com.caritas.controller.abstractController;

/*
 * Para utilizar con el composite component create/edit/view/list tools
 */
public interface CrudController {

    public String prepareList();

    public String prepareCreate();

    public String prepareView();

    public String prepareEdit();

    public String prepareViewNoRow();

    public String prepareEditNoRow();

    public String destroyNoRow();

    public String create();

    public String update();

    public String destroy();
}