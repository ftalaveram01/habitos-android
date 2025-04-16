package com.ftalaveram.habbbits.repositories.models;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("nombre")
    private String name;
    private String email;
    @SerializedName("numeroHabitos")
    private int habitsNumber;

    public ProfileResponse(String name, String email, int habitsNumber) {
        this.name = name;
        this.email = email;
        this.habitsNumber = habitsNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHabitsNumber() {
        return habitsNumber;
    }

    public void setHabitsNumber(int habitsNumber) {
        this.habitsNumber = habitsNumber;
    }
}
