package org.example.bullsandcowsapi.repository;

import org.example.bullsandcowsapi.entity.Attempt;
import org.example.bullsandcowsapi.entity.Game;

import java.util.List;
import java.util.UUID;

public interface GameCrudRepository {
    void create(Game game);

    Game findById(UUID id);

    List<Attempt> findAttemptsByGameId(UUID id);

    void addAttempt(Attempt attempt);
}
