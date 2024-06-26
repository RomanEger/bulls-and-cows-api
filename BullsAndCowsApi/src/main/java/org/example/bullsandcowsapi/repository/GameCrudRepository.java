package org.example.bullsandcowsapi.repository;

import org.example.bullsandcowsapi.dto.AttemptDto;
import org.example.bullsandcowsapi.dto.GameDto;
import org.example.bullsandcowsapi.entity.Attempt;
import org.example.bullsandcowsapi.entity.Game;

import java.util.List;
import java.util.UUID;

public interface GameCrudRepository {
    UUID create(Game game);

    List<Game> findAll();

    Game findById(UUID id);

    List<AttemptDto> findAttemptsByGameId(UUID id);

    void addAttempt(Attempt attempt);

    void delete(int id);
}
