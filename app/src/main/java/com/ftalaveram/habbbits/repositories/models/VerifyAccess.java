package com.ftalaveram.habbbits.repositories.models;

public class VerifyAccess {

    private boolean isAuthenticated;

    public VerifyAccess(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public boolean isAuthenticated() {
        try {
            return isAuthenticated;
        } catch (NullPointerException e){
            return  false;
        }

    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}
