package org.example.bullsandcowsapi.entity;

import jakarta.persistence.*;
import org.aspectj.lang.annotation.RequiredTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    public int number;

    public String rule;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game", cascade = CascadeType.MERGE)
    public List<Attempt> attemptList;
}
