package com.quizapp.dto;

public class DetailedResponseDTO {
    public Long questionId;
    public String questionText;
    public String userAnswer;
    public String correctAnswer;
    public boolean isCorrect;

    public DetailedResponseDTO(Long questionId, String questionText, String userAnswer, String correctAnswer, boolean isCorrect) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.isCorrect = isCorrect;
    }
}
