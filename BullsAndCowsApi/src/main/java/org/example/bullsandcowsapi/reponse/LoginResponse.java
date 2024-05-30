package org.example.bullsandcowsapi.reponse;

import java.util.UUID;

public class LoginResponse extends BaseResponse {
    public UUID session;

    public LoginResponse(String status, String errorMessage, UUID session) {
        super(status, errorMessage);
        this.session = session;
    }
}
