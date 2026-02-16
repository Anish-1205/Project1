package com.quizapp.dto;

public class ResponseSubmitDTO {
    public Long responseId;
    public boolean isCorrect;
    public String message;

    public ResponseSubmitDTO(Long responseId, boolean isCorrect, String message) {
        this.responseId = responseId;
        this.isCorrect = isCorrect;
        this.message = message;
    }
}
