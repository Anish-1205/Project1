package com.quizapp.service;
import com.quizapp.dto.*;
import com.quizapp.entity.*;
import com.quizapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuizService {
    @Autowired private QuizRepository quizRepository;
    @Autowired private QuestionRepository questionRepository;
    @Autowired private OptionRepository optionRepository;

    public List<QuizListDTO> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(quiz -> new QuizListDTO(quiz.getId(), quiz.getTitle(), quiz.getDescription(), quiz.getDuration(), Math.toIntExact(questionRepository.countByQuiz(quiz))))
                .collect(Collectors.toList());
    }

    public QuizDetailDTO getQuizDetail(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        List<QuestionDTO> questions = questionRepository.findByQuiz(quiz).stream()
                .map(question -> new QuestionDTO(question.getId(), question.getText(), 
                        optionRepository.findByQuestion(question).stream()
                        .map(option -> new OptionDTO(option.getId(), option.getText(), null))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new QuizDetailDTO(quiz.getId(), quiz.getTitle(), quiz.getDuration(), questions);
    }
}
