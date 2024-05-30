package org.example.bullsandcowsapi.controller;

import org.example.bullsandcowsapi.reponse.BaseResponse;
import org.example.bullsandcowsapi.request.AuthorizationRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Authorization {

    @PostMapping("/register")
    public BaseResponse Registration(@RequestBody AuthorizationRequestDto user){

        return new BaseResponse("OK", null);
    }

    @PostMapping("/login")
    public BaseResponse Login(@RequestBody AuthorizationRequestDto user){
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
        return new BaseResponse("OK", null);
    }
}
