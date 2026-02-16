package com.quizapp.dto;
import java.util.List;

public class QuestionDTO {
    public Long id;
    public String text;
    public List<OptionDTO> options;

    public QuestionDTO(Long id, String text, List<OptionDTO> options) {
        this.id = id;
        this.text = text;
        this.options = options;
    }
}
