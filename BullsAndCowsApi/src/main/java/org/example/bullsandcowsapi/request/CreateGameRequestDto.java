package org.example.bullsandcowsapi.request;

import java.util.UUID;

public class CreateGameRequestDto extends BaseGameRequestDto {
    public String gameRules;
    public UUID session;

    public CreateGameRequestDto(int number, String gameRules, UUID session){
        super(number);
        this.gameRules = gameRules;
        this.session = session;
    }
}
