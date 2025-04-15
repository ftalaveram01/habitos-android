package com.ftalaveram.habbbits.repositories.models;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Achievements {
    private int id;
    @SerializedName("habito_id")
    private int idHabito;
    @SerializedName("fecha_registro")
    private String fechaRegistroRaw;
    private int puntual;
    private int puntuacion;
    private String nombre;

    public Achievements(int id, int idHabito, String fechaRegistroRaw, int puntual, int puntuacion, String nombre) {
        this.id = id;
        this.idHabito = idHabito;
        this.fechaRegistroRaw = fechaRegistroRaw;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return sdf.parse(fechaRegistroRaw);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setFechaRegistro(String fechaRegistroRaw) {
        this.fechaRegistroRaw = fechaRegistroRaw;
    }

    public boolean isPuntual() {
        return puntual == 1;
    }

    public void setPuntual(int puntual) {
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
