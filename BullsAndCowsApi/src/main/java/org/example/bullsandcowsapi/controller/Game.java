package org.example.bullsandcowsapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.bullsandcowsapi.dto.AttemptDto;
import org.example.bullsandcowsapi.entity.User;
import org.example.bullsandcowsapi.reponse.AttemptResponse;
import org.example.bullsandcowsapi.reponse.GameResponse;
import org.example.bullsandcowsapi.reponse.BaseResponse;
import org.example.bullsandcowsapi.reponse.GameStatusReponse;
import org.example.bullsandcowsapi.repository.GameCrudRepository;
import org.example.bullsandcowsapi.repository.UserCrudRepository;
import org.example.bullsandcowsapi.request.CreateGameRequestDto;
import org.example.bullsandcowsapi.service.IntToIntArrayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class Game {

    private final GameCrudRepository gameRepository;
    private final UserCrudRepository userRepository;
    @Autowired
    public Game(GameCrudRepository repository, UserCrudRepository userRepository){
        this.gameRepository = repository;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public BaseResponse Create(HttpServletRequest request, @RequestBody CreateGameRequestDto createGameRequestDto){
        User user = null;
        UUID userId = null;
        try{
            var cookie = WebUtils.getCookie(request, "userId");
            userId = UUID.fromString(cookie.getValue());
            user = userRepository.findById(userId);
        }
        catch (Exception ex){
            //
        }
        if(user == null)
            return new BaseResponse("FAIL", "Пользователь не найден");

        var game = new org.example.bullsandcowsapi.entity.Game();
        game.number = createGameRequestDto.number;
        game.rule = createGameRequestDto.gameRules;
        game.session = UUID.randomUUID();
        game.userSession = userId;
        try{
            gameRepository.create(game);
            var result = gameRepository.findById(game.session);

            return new GameResponse("OK", null, result.session);
        }
        catch (Exception ex){
            return new BaseResponse("FAIL", ex.getMessage());
        }
    }

    @GetMapping("/{id}/status")
    public BaseResponse Status(@PathVariable UUID id){
        try {
            List<AttemptDto> attempts = gameRepository.findAttemptsByGameId(id);
            return new GameStatusReponse("IN_PROGRESS", null, attempts);
        }
        catch (Exception ex){
            return new BaseResponse("FAIL", "Игра не найдена");
        }
    }

    @PostMapping("/{id}/attempt")
    public BaseResponse Attempt(HttpServletRequest request, @PathVariable UUID id, @RequestBody int number){
        try{
            var attempt = new org.example.bullsandcowsapi.entity.Attempt();
            var game = new org.example.Game();
            var arrAttempt = IntToIntArrayService.toIntArray(number);
            var arrBulls = IntToIntArrayService.toIntArray(gameRepository.findById(id).number);
            var tuple = game.getBullsAndCows(arrBulls, arrAttempt);
            var gameObj = gameRepository.findById(id);
            var cookie = WebUtils.getCookie(request, "userId");
            var userId = cookie.getValue();
            if (userId.equals(gameObj.userSession.toString())){
                return new BaseResponse("FAIL", "Попытка записать попытку в свою игру");
            }

            attempt.number = number;
            attempt.bulls = tuple.getFirst();
            attempt.cows = tuple.getSecond();
            attempt.gameId = gameObj.id;

            gameRepository.addAttempt(attempt);
            return new AttemptResponse("OK", null, tuple.getFirst(), tuple.getSecond());
        }
        catch (Exception ex){
            return new BaseResponse("FAIL", "Игра не найдена");
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAll(HttpServletRequest request){
        var cookie = WebUtils.getCookie(request, "userId");
        try{
            var userId = cookie.getValue();
        }
        catch(Exception ex){
            return ResponseEntity.status(401).body("Unauthorized");
        }
        var games = gameRepository.findAll();
        return ResponseEntity.ok(games);
    }
}
