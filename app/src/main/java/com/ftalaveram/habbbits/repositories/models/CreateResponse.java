package com.ftalaveram.habbbits.repositories.models;

public class CreateResponse {

    private boolean success;

    public CreateResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
