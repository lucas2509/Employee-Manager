package com.saam.employeemanager.model;

// Classe que representa a entity Account do banco de dados
public class Account {
    private final int id;
    private final String username;
    private final String email;
    private final String password;
    private final String salt;

    public Account(int id, String username, String email, String password, String salt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }
}
