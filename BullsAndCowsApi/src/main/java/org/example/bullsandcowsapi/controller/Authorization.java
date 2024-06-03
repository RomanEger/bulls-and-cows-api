package org.example.bullsandcowsapi.controller;

import org.example.bullsandcowsapi.entity.User;
import org.example.bullsandcowsapi.reponse.BaseResponse;
import org.example.bullsandcowsapi.repository.UserRepository;
import org.example.bullsandcowsapi.request.AuthorizationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class Authorization {

    private final UserRepository repository;

    @Autowired
    public Authorization(UserRepository repository){
        this.repository = repository;
    }

    @PostMapping("/register")
    public BaseResponse Registration(@RequestBody AuthorizationRequestDto requestUser){
        var user = new User();
        user.login = requestUser.login();
        user.password = requestUser.password();
        repository.save(user);
        return new BaseResponse("OK", null);
    }

    @PostMapping("/login")
    public BaseResponse Login(@RequestBody AuthorizationRequestDto requestUser){
        /*
        //query to db
        var querySelectByLogin = "select * from Users where login=user.login"
        var result = ...(querySelectByLogin);
        if(result == null){
            return new BaseResponse("FAIL", "неверный логин");
        }

        var querySelectByLoginAndPassword = "select * from Users where login=user.login and password=user.password
        var result = ...(querySelectByLoginAndPassword);
        if(result == null){
            return new BaseResponse("FAIL", "неверный пароль");
        }
        */
        var user = new User();
        user.login = requestUser.login();
        user.password = requestUser.password();
        var result = repository.findByPersonalData(user);
        if(Objects.equals(result, Optional.empty())){
            return new BaseResponse("FAIL", "ошибка входа");
        }
        return new BaseResponse("OK", null);
    }
}
