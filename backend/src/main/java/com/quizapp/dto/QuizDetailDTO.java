package com.quizapp.dto;
import java.util.List;

public class QuizDetailDTO {
    public Long id;
    public String title;
    public int duration;
    public List<QuestionDTO> questions;

    public QuizDetailDTO(Long id, String title, int duration, List<QuestionDTO> questions) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.questions = questions;
    }
}
