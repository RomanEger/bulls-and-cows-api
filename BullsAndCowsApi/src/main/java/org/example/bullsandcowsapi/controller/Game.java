package org.example.bullsandcowsapi.controller;

import org.example.bullsandcowsapi.Attempt;
import org.example.bullsandcowsapi.reponse.AttemptResponse;
import org.example.bullsandcowsapi.reponse.GameResponse;
import org.example.bullsandcowsapi.reponse.BaseResponse;
import org.example.bullsandcowsapi.reponse.GameStatusReponse;
import org.example.bullsandcowsapi.request.CreateGameRequestDto;
import org.example.bullsandcowsapi.service.IntToIntArrayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class Game {

    private HashMap<UUID, List<Attempt>> gameMap;

    private int[] arrBulls;

    @Autowired
    public Game(HashMap<UUID, List<Attempt>> gameMap){
        this.gameMap = gameMap;
    }

    @PostMapping("create")
    public BaseResponse Create(@RequestBody CreateGameRequestDto createGameRequestDto){
        var gameSession = UUID.randomUUID();
        gameMap.put(gameSession, null);
        arrBulls = IntToIntArrayService.toIntArray(createGameRequestDto.number);

        return new GameResponse("OK", null, gameSession);
    }

    @GetMapping("{id}/status")
    public BaseResponse Status(@PathVariable UUID id){
        var gameStatus = gameMap.get(id);
        if(gameStatus == null)
            return new BaseResponse("FAIL", "Игра не найдена");

        return new GameStatusReponse("IN_PROGRESS", null, gameStatus);
    }

    @PostMapping("{id}/attempt")
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
