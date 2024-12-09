package com.PB.ParkingBay.DTO;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Login (email or mobile) is mandatory")
    private String login;  // Could be email or mobile

    @NotBlank(message = "Password is mandatory")
    private String password;

    // Getters and Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
