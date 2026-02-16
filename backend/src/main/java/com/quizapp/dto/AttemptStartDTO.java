package com.quizapp.dto;
import java.time.LocalDateTime;

public class AttemptStartDTO {
    public Long attemptId;
    public Long quizId;
    public LocalDateTime startTime;
    public LocalDateTime endTime;

    public AttemptStartDTO(Long attemptId, Long quizId, LocalDateTime startTime, LocalDateTime endTime) {
        this.attemptId = attemptId;
        this.quizId = quizId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
