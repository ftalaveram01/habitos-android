package com.ftalaveram.habbbits.repositories.models;

public class CreateRequest {

    private String nombre;
    private String descripcion;
    private String creadoEn;
    private String fechaInicio;
    private int horasIntervalo;
    private boolean publico;

    public CreateRequest(String nombre, String descripcion, String creadoEn, String fechaInicio, int horasIntervalo, boolean publico) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creadoEn = creadoEn;
        this.fechaInicio = fechaInicio;
        this.horasIntervalo = horasIntervalo;
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

    public String getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(String creadoEn) {
        this.creadoEn = creadoEn;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getHorasIntervalo() {
        return horasIntervalo;
    }

    public void setHorasIntervalo(int horasIntervalo) {
        this.horasIntervalo = horasIntervalo;
    }

    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }
}
