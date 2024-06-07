package org.example.bullsandcowsapi.controller;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.bullsandcowsapi.entity.User;
import org.example.bullsandcowsapi.reponse.BaseResponse;
import org.example.bullsandcowsapi.reponse.LoginResponse;
import org.example.bullsandcowsapi.repository.UserCrudRepository;
import org.example.bullsandcowsapi.request.AuthorizationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

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
            try{
                repository.findByLogin(user.login);
                return new BaseResponse("FAIL", "Логин занят!");
            }
            catch (Exception ex){
                //
            }

            repository.create(user);
            return new BaseResponse("OK", null);
        }
        catch (Exception ex){
            var msg = ex.getMessage();
            System.out.println(msg);
            var stack = ex.getStackTrace();
            System.out.println(Arrays.toString(stack));
            return new BaseResponse("FAIL", msg);
        }
    }

    @PostMapping("/login")
    public BaseResponse Login(HttpServletResponse response, @RequestBody AuthorizationRequestDto user){
        try{
            var findUser = repository.findByLoginAndPassword(user.login(), user.password());

            var cookie = new Cookie("userId", findUser.id.toString());
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            response.setContentType("text/plain");

            return new LoginResponse("OK", null, findUser.id);
        }
        catch (Exception ex){
            return new BaseResponse("FAIL", ex.getMessage());
        }
    }
}
