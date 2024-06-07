package org.example.bullsandcowsapi.reponse;

import org.example.bullsandcowsapi.dto.AttemptDto;
import java.util.List;

public class GameStatusReponse extends BaseResponse {

    public List<AttemptDto> attempts;

    public GameStatusReponse(String status, String errorMessage, List<AttemptDto> attempts) {
        super(status, errorMessage);
        this.attempts = attempts;
    }
}
