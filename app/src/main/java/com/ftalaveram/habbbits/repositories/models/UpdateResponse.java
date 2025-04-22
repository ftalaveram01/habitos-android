package com.ftalaveram.habbbits.repositories.models;

public class UpdateResponse {

    private boolean success;

    public UpdateResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
