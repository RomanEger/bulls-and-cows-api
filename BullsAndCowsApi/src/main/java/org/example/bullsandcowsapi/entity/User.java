package org.example.bullsandcowsapi.entity;

import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
public class User {
    public UUID id;
    public String login;
    public String password;
}
