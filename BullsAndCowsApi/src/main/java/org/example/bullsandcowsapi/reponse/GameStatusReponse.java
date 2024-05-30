package org.example.bullsandcowsapi.reponse;

import org.example.bullsandcowsapi.Attempt;

import java.util.List;

public class GameStatusReponse extends BaseResponse {

    public List<Attempt> attempts;

    public GameStatusReponse(String status, String errorMessage, List<Attempt> attempts) {
        super(status, errorMessage);
        this.attempts = attempts;
    }
}
