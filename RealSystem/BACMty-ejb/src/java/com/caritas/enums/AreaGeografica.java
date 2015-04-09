package com.caritas.enums;

/**
 * Corresponde al campo de Area Geografica de la Encuesta Socio Nutricia
 *
 * @see com.caritas.entity.EncSocioNutricia
 * @author software
 */
public enum AreaGeografica {

    RURAL('R'),
    URBANA('U'),
    SUB_URBANA('S');
    private char codigo;

    private AreaGeografica(char codigo) {
        this.codigo = codigo;
    }

    public char getCodigo() {
        return codigo;
    }
    
    public String getLabelKey() {
        return "AreaGeografica_" + getCodigo();
    }
}
