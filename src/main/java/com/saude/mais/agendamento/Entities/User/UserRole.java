package com.saude.mais.agendamento.Entities.User;

public enum UserRole {
    ADMIN("admin"),
    WORKER("worker"),
    CUSTOMER("customer");


    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
