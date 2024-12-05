package com.example.fortunecookie.data;

public enum tipoSorte {
    SORTE,
    DESORTE,
    SORTE_DESORTE;

    tipoSorte() {
    }

    public static tipoSorte getTipoSorte(int tipo) {
        return tipoSorte.values()[tipo];
    }

    public static int getTipoSorte(tipoSorte tipo) {
        return tipo.ordinal();
    }

    public static String getTipoSorte(tipoSorte tipo, String prefix) {
        return prefix + tipo.toString();
    }

    public static String getTipoSorte(int tipo, String prefix) {
        return prefix + tipoSorte.values()[tipo].toString();
    }

    public static String getTipoSorte(String tipo, String prefix) {
        return prefix + tipoSorte.valueOf(tipo).toString();
    }

    public static String getTipoSorte(String tipo, String prefix, String suffix) {
        return prefix + tipoSorte.valueOf(tipo).toString() + suffix;
    }
}
