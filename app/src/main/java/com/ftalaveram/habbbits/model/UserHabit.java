package com.ftalaveram.habbbits.model;

import java.util.Date;

public class UserHabit extends Habit{
    private Long id;
    private Date fechaNuevaActualizacion;
    private boolean publico;

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
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }
}
