package org.example.bullsandcowsapi.reponse;

public class AttemptResponse extends BaseResponse {
    public int bulls;
    public int cows;

    public AttemptResponse(String status, String errorMessage, int bulls, int cows) {
        super(status, errorMessage);
        this.bulls = bulls;
        this.cows = cows;
    }
}
