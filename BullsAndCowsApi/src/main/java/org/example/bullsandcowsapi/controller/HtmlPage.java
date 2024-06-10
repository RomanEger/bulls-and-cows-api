package org.example.bullsandcowsapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/")
public class HtmlPage {

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/game/create")
    public String createGame(HttpServletRequest request){
        var cookie = WebUtils.getCookie(request, "userId");
        try{
            var userId = cookie.getValue();
        }
        catch (Exception e){
            return "login";
        }
        return "create";
    }

    @GetMapping("/games")
    public String games(HttpServletRequest request){
        var cookie = WebUtils.getCookie(request, "userId");
        try{
            var userId = cookie.getValue();
        }
        catch (Exception e){
            return "login";
        }
        return "games";
    }

    @GetMapping("/{id}/attempt")
    public String attempt(HttpServletRequest request){
        var cookie = WebUtils.getCookie(request, "userId");
        try{
            var userId = cookie.getValue();
        }
        catch (Exception e){
            return "login";
        }
        return "attempt";
    }
}
