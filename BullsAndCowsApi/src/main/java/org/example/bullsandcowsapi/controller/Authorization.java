package org.example.bullsandcowsapi.controller;

import jakarta.persistence.NoResultException;
import org.example.bullsandcowsapi.entity.User;
import org.example.bullsandcowsapi.reponse.BaseResponse;
import org.example.bullsandcowsapi.repository.UserCrudRepository;
import org.example.bullsandcowsapi.repository.UserRepository;
import org.example.bullsandcowsapi.request.AuthorizationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class Authorization {

    private final UserCrudRepository repository;

    @Autowired
    public Authorization(UserCrudRepository repository){
        this.repository = repository;
    }

    @PostMapping("/register")
    public BaseResponse Registration(@RequestBody AuthorizationRequestDto requestUser){
        var user = new User();
        user.login = requestUser.login();
        user.password = requestUser.password();
        try{
            repository.create(user);
            repository.findAll();
        }
        catch (Exception ex){
            var msg = ex.getMessage();
            System.out.println(msg);
            var stack = ex.getStackTrace();
            System.out.println(Arrays.toString(stack));
        }

        return new BaseResponse("OK", null);
    }

    @PostMapping("/login")
    public BaseResponse Login(@RequestBody AuthorizationRequestDto user){
        var result = repository.findByLoginAndPassword(user.login(), user.password());
        if(Objects.equals(result, Optional.empty())){
            return new BaseResponse("FAIL", "ошибка входа");
        }
        return new BaseResponse("OK", null);
    }
}
