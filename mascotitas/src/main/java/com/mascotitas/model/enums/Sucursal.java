package com.mascotitas.model.enums;

/*
 * Enum: Sucursal
 * Representa las sucursales donde se encuentra el negocio
 */
public enum Sucursal {
    CENTRO("Centro"),
    NORTE("Norte"),
    SUR("Sur"),
    ESTE("Este"),
    OESTE("Oeste");

    private final String nombreZona;

    Sucursal(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    @Override
    public String toString() {
        return nombreZona;
    }
}
