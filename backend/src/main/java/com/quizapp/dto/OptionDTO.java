package com.quizapp.dto;

public class OptionDTO {
    public Long id;
    public String text;
    public Boolean isCorrect;

    public OptionDTO(Long id, String text, Boolean isCorrect) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
    }
}
