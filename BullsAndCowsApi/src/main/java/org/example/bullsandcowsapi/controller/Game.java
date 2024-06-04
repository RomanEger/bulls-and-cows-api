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

    private int[] arrBulls;

    @Autowired
    public Game(GameCrudRepository repository){
        this.repository = repository;
    }

    @PostMapping("/create")
    public BaseResponse Create(@RequestBody CreateGameRequestDto createGameRequestDto){
        var game = new org.example.bullsandcowsapi.entity.Game();
        game.number = createGameRequestDto.number;
        game.id = UUID.randomUUID();
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
        var gameStatus = gameMap.get(id);
        if(gameStatus == null)
            return new BaseResponse("FAIL", "Игра не найдена");
        var bullsAndCows = new org.example.Game().getBullsAndCows(arrBulls, IntToIntArrayService.toIntArray(number));
        var bulls = bullsAndCows.getFirst();
        var cows = bullsAndCows.getSecond();
        return new AttemptResponse("OK", null, bulls, cows);
    }
}
