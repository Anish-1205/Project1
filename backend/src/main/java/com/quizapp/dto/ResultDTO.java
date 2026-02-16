package com.quizapp.dto;
import java.util.List;

public class ResultDTO {
    public Long attemptId;
    public int score;
    public int correctAnswers;
    public int totalQuestions;
    public int incorrectAnswers;
    public List<DetailedResponseDTO> details;

    public ResultDTO(Long attemptId, int score, int correctAnswers, int totalQuestions, int incorrectAnswers, List<DetailedResponseDTO> details) {
        this.attemptId = attemptId;
        this.score = score;
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
        this.incorrectAnswers = incorrectAnswers;
        this.details = details;
    }
}
