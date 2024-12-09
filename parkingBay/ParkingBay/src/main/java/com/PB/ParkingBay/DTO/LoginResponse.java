package com.PB.ParkingBay.DTO;

public class LoginResponse {
    private boolean login;
    private String token;

    public LoginResponse(boolean login, String token) {
        this.login = login;
        this.token = token;
    }

    // Getters and setters
    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
