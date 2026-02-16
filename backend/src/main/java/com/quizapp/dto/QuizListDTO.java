package com.quizapp.dto;

public class QuizListDTO {
    public Long id;
    public String title;
    public String description;
    public int duration;
    public int questionCount;

    public QuizListDTO(Long id, String title, String description, int duration, int questionCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.questionCount = questionCount;
    }
}
