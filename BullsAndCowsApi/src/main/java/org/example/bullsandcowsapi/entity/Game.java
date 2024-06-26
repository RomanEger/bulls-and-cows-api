package org.example.bullsandcowsapi.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    public int number;

    public String rule;

    public UUID session;

    public UUID userSession;
}
