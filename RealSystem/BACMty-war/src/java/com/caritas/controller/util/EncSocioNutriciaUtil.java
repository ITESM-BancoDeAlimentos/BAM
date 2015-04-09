package com.caritas.controller.util;

public class EncSocioNutriciaUtil {

    public static final int PARENTESCO = 1;
    public static final int ESTADO_CIVIL = 2;
    public static final int ESCOLARIDAD = 3;
    public static final int CONDICION_LABORAL = 4;
    public static final int RAMA_ACTIVIDAD = 5;
    public static final int SEGURO_SOCIAL = 6;
    public static final int AYUDA_ALIMENTARIA = 7;
    public static final int ETAPA_EDO_FISIO = 8;
    
    private static  final String[] parentescoLabels = {
        "01. Jefe de la Familia",
        "02. Esposo(a)",
        "03. Hijo(a)",
        "04. Nieto(a)",
        "05. Padre o Madre",
        "06. Abuelos",
        "07. Hermano(a)",
        "08. Sobrino(a)",
        "09. Cuñado(a)",
        "10. Primo",
        "11. Otro (Especifique)"
    };
    private static  final String[] estadoCivilLabels = {
        "00. No procede",
        "01. Soltero",
        "02. Casado",
        "03. Union Libre",
        "04. Divorciado",
        "05. Separado",
        "06. Viudo",
        "07. Madre Soltera"
    };
    private static  final String[] escolaridadLabels = {
        "00. No procede",
        "01. Analfabeta",
        "02. Sabe leer y escribir",
        "03. Preescolar",
        "04. Cursa Primaria",
        "05. Primaria incompleta",
        "06. Primaria completa",
        "07. Cursa Secundaria",
        "08. Secundaria incompleta",
        "09. Secundaria completa",
        "10. Preparatoria",
        "11. Carrera tecnica",
        "12. Profesional o mas"
    };
    private static  final String[] condLaboralLabels = {
        "00. No procede",
        "01. Asalariado",
        "02. No asalariado",
        "03. Propietario no empleador",
        "04. Propietario empleador",
        "05. Ama de casa",
        "06. Estudiante",
        "07. Desempleado",
        "08. Jubilado o Pensionado"
    };
    private static  final String[] ramaActividadLabels = {
        "00. No procede",
        "01. Agricultura",
        "02. Ganadería",
        "03. Silvicultura",
        "04. Pesca",
        "05. Artesanía",
        "06. Manufactura",
        "07. Comercio formal",
        "08. Comercio informal",
        "09. Construcción",
        "10. Servicios",
        "11. Otros (especifique)"
    };
    private static  final String[] seguridadSocialLabels = {
        "00. Ninguno",
        "01. SSA",
        "02. IMSS",
        "03. ISSSTE",
        "04. DIF",
        "05. SEDENA",
        "06. PEMEX",
        "07. Particular",
        "08. Otros (especifique)"
    };
    private static  final String[] ayudaAlimentariaLabels = {
        "00. Ninguno",
        "01. DIF: PASAF",
        "02. DIF: COPUSI",
        "03. DIF: Desayunos Escolares",
        "04. Programa de Despensas Cáritas o Bco. de Alimentos",
        "05. Programa de Organismos Federal",
        "06. Otros (especifique)"
    };
    private static  final String[] etapaEstadoFisiologicoLabels = {
        "00. No aplica",
        "01. Embarazo",
        "02. Lactancia",
        "03. Capacidades diferentes"
    };

    public static String[] getLabels(int identificador) {
        switch (identificador) {
            case PARENTESCO:
                return parentescoLabels;
            case ESTADO_CIVIL:
                return estadoCivilLabels;
            case ESCOLARIDAD:
                return escolaridadLabels;
            case CONDICION_LABORAL:
                return condLaboralLabels;
            case RAMA_ACTIVIDAD:
                return ramaActividadLabels;
            case SEGURO_SOCIAL:
                return seguridadSocialLabels;
            case AYUDA_ALIMENTARIA:
                return ayudaAlimentariaLabels;
            case ETAPA_EDO_FISIO:
                return etapaEstadoFisiologicoLabels;
            default:
                return null;
        }
    }
    
    public static String getValue(int identificador, String valor) {
        double dbl = Double.parseDouble(valor);
        int val = (int) dbl;
        return getLabels(identificador)[val].substring(4);
    }
}
