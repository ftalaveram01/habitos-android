package com.ftalaveram.habbbits.repositories.models;

import java.util.Date;

public class Achievements {
    private int id;
    private int idHabito;
    private Date fechaRegistro;
    private boolean puntual;
    private int puntuacion;
    private String nombre;

    public Achievements(int id, int idHabito, Date fechaRegistro, boolean puntual, int puntuacion, String nombre) {
        this.id = id;
        this.idHabito = idHabito;
        this.fechaRegistro = fechaRegistro;
        this.puntual = puntual;
        this.puntuacion = puntuacion;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHabito() {
        return idHabito;
    }

    public void setIdHabito(int idHabito) {
        this.idHabito = idHabito;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isPuntual() {
        return puntual;
    }

    public void setPuntual(boolean puntual) {
        this.puntual = puntual;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
