package com.ftalaveram.habbbits.repositories.models;

public class UpdateRequest {

    private String nombre;
    private String descripcion;
    private boolean publico;

    public UpdateRequest(String nombre, String descripcion, boolean publico) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.publico = publico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }
}
