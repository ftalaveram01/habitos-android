package com.ftalaveram.habbbits.repositories.models;

import java.util.Date;

public class UserHabit extends Habit{
    private Long id;
    private Date fechaNuevaActualizacion;
    private int publico;

    public UserHabit(String nombre, String descripcion, Long id, Date fechaNuevaActualizacion, int publico) {
        super(nombre, descripcion);
        this.id = id;
        this.fechaNuevaActualizacion = fechaNuevaActualizacion;
        this.publico = publico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaNuevaActualizacion() {
        return fechaNuevaActualizacion;
    }

    public void setFechaNuevaActualizacion(Date fechaNuevaActualizacion) {
        this.fechaNuevaActualizacion = fechaNuevaActualizacion;
    }

    public boolean isPublico() {
        return publico == 1;
    }

    public void setPublico(int publico) {
        this.publico = publico;
    }
}
