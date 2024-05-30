package org.example.bullsandcowsapi.entity;

import jakarta.persistence.Entity;

@Entity
public class User {
    public String login;
    public String password;
}
