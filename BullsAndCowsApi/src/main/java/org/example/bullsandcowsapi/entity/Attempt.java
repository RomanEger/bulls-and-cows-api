package org.example.bullsandcowsapi.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    public int number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameId")
    public Game game;
}
