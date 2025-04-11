package com.ftalaveram.habbbits.repositories.models;

public class LoginData {

    private boolean success;
    private UserData userData;
    private String token;

    private class UserData {
        private Long id;
        private String email;

        public UserData(Long id, String email) {
            this.id = id;
            this.email = email;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public LoginData(boolean success, UserData userData, String token) {
        this.success = success;
        this.userData = userData;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "success=" + success +
                ", userData=" + userData +
                ", token='" + token + '\'' +
                '}';
    }
}
