package org.example.bullsandcowsapi.request;

public class CreateGameRequestDto extends BaseGameRequestDto {
    public String gameRules;

    public CreateGameRequestDto(int number, String gameRules){
        super(number);
        this.gameRules = gameRules;
    }
}
