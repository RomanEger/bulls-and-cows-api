package org.example.bullsandcowsapi.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "attempts")
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    public int number;

    public int bulls;

    public int cows;

    public int gameId;
}
