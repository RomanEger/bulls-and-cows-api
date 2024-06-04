package org.example.bullsandcowsapi.controller;

import org.example.bullsandcowsapi.Attempt;
import org.example.bullsandcowsapi.reponse.AttemptResponse;
import org.example.bullsandcowsapi.reponse.GameResponse;
import org.example.bullsandcowsapi.reponse.BaseResponse;
import org.example.bullsandcowsapi.reponse.GameStatusReponse;
import org.example.bullsandcowsapi.repository.GameCrudRepository;
import org.example.bullsandcowsapi.request.CreateGameRequestDto;
import org.example.bullsandcowsapi.service.IntToIntArrayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class Game {

    private final GameCrudRepository repository;

    @Autowired
    public Game(GameCrudRepository repository){
        this.repository = repository;
    }

    @PostMapping("/create")
    public BaseResponse Create(@RequestBody CreateGameRequestDto createGameRequestDto){
        var game = new org.example.bullsandcowsapi.entity.Game();
        game.number = createGameRequestDto.number;
        game.id = UUID.randomUUID();
        game.rule = createGameRequestDto.gameRules;
        try{
            repository.create(game);
            var result = repository.findById(game.id);

            return new GameResponse("OK", null, result.id);
        }
        catch (Exception ex){
            return new BaseResponse("FAIL", ex.getMessage());
        }
    }

    @GetMapping("/{id}/status")
    public BaseResponse Status(@PathVariable UUID id){
        try {
            var attempts = repository.findAttemptsByGameId(id);
            var resultAttempts = new ArrayList<Attempt>();
            for(var a : attempts){
                resultAttempts.add(new Attempt(a.number, a.bulls, a.cows));
            }
            return new GameStatusReponse("IN_PROGRESS", null, resultAttempts);
        }
        catch (Exception ex){
            return new BaseResponse("FAIL", "Игра не найдена");
        }
    }

    @PostMapping("/{id}/attempt")
    public BaseResponse Attempt(@PathVariable UUID id, @RequestBody int number){
        try{
            var attempt = new org.example.bullsandcowsapi.entity.Attempt();
            var game = new org.example.Game();
            var arrAttempt = IntToIntArrayService.toIntArray(number);
            var arrBulls = IntToIntArrayService.toIntArray(repository.findById(id).number);
            var tuple = game.getBullsAndCows(arrBulls, arrAttempt);
            repository.addAttempt(attempt);
            return new AttemptResponse("OK", null, tuple.getFirst(), tuple.getSecond());
        }
        catch (Exception ex){
            return new BaseResponse("FAIL", ex.getMessage());
        }
    }
}
