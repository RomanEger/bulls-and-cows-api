package org.example.bullsandcowsapi.reponse;

import java.util.UUID;

public class GameResponse extends BaseResponse {
    public UUID gameSession;

    public GameResponse(String status, String errorMessage, UUID gameSession) {
        super(status, errorMessage);
        this.gameSession = gameSession;
    }
}
