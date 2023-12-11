package com.bandup.edutask;

public class Asignacion {
    private String nombre;
    private String fecha;

    public Asignacion(String nombre, String fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }
}
