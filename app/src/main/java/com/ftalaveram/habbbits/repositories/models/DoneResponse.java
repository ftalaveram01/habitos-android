package com.ftalaveram.habbbits.repositories.models;

public class DoneResponse {

    private String success;

    public DoneResponse(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
