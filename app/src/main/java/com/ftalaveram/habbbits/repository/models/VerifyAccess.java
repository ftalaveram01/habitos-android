package com.ftalaveram.habbbits.repository.models;

public class VerifyAccess {

    private boolean isAuthenticated;

    public VerifyAccess(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}
