package org.example.bullsandcowsapi.reponse;

public class BaseResponse {
    public String status;
    public String errorMessage;

    public BaseResponse(String status, String errorMessage){
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
