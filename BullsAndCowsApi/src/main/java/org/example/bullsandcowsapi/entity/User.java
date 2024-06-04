package org.example.bullsandcowsapi.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;
    public String login;
    public String password;
}
