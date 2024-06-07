package org.example.bullsandcowsapi.request;

import java.util.UUID;

public class CreateGameRequestDto extends BaseGameRequestDto {
    public String gameRules;

    public CreateGameRequestDto(int number, String gameRules){
        super(number);
        this.gameRules = gameRules;
    }
}
