package com.quizapp.service;
import com.quizapp.dto.*;
import com.quizapp.entity.*;
import com.quizapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttemptService {
    @Autowired private UserAttemptRepository userAttemptRepository;
    @Autowired private ResponseRepository responseRepository;
    @Autowired private QuizRepository quizRepository;
    @Autowired private QuestionRepository questionRepository;
    @Autowired private OptionRepository optionRepository;

    public AttemptStartDTO startAttempt(Long quizId, String userId) {
        userAttemptRepository.findByUserIdAndQuizIdAndEndTimeIsNull(userId, quizId).ifPresent(attempt -> { throw new RuntimeException("Active attempt exists"); });
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        UserAttempt attempt = new UserAttempt();
        attempt.setUserId(userId);
        attempt.setQuiz(quiz);
        attempt.setStartTime(LocalDateTime.now());
        attempt.setScore(0);
        UserAttempt saved = userAttemptRepository.save(attempt);
        return new AttemptStartDTO(saved.getId(), quiz.getId(), saved.getStartTime(), LocalDateTime.now().plusSeconds(quiz.getDuration() * 60));
    }

    public ResponseSubmitDTO submitResponse(Long attemptId, ResponseSubmitRequestDTO request) {
        UserAttempt attempt = userAttemptRepository.findById(attemptId).orElseThrow(() -> new RuntimeException("Attempt not found"));
        if (attempt.getEndTime() != null) throw new RuntimeException("Quiz already submitted");
        Optional<Response> existing = responseRepository.findByAttemptAndQuestion(attempt, questionRepository.findById(request.questionId).orElseThrow());
        if (existing.isPresent()) throw new RuntimeException("Response already exists");
        Option selectedOption = optionRepository.findById(request.selectedOptionId).orElseThrow();
        Response response = new Response();
        response.setAttempt(attempt);
        response.setQuestion(selectedOption.getQuestion());
        response.setSelectedOption(selectedOption);
        Response saved = responseRepository.save(response);
        return new ResponseSubmitDTO(saved.getId(), selectedOption.isCorrect(), "Recorded");
    }

    public ResultDTO submitQuiz(Long attemptId) {
        UserAttempt attempt = userAttemptRepository.findById(attemptId).orElseThrow();
        if (attempt.getEndTime() != null) throw new RuntimeException("Already submitted");
        attempt.setEndTime(LocalDateTime.now());
        List<Response> responses = responseRepository.findByAttempt(attempt);
        int totalQuestions = Math.toIntExact(questionRepository.countByQuiz(attempt.getQuiz()));
        int correctAnswers = (int) responses.stream().filter(r -> r.getSelectedOption().isCorrect()).count();
        int score = (totalQuestions > 0) ? (correctAnswers * 100) / totalQuestions : 0;
        attempt.setScore(score);
        userAttemptRepository.save(attempt);
        return buildResult(attempt, responses, totalQuestions, correctAnswers);
    }

    public ResultDTO getResult(Long attemptId) {
        UserAttempt attempt = userAttemptRepository.findById(attemptId).orElseThrow();
        if (attempt.getEndTime() == null) throw new RuntimeException("Not submitted");
        List<Response> responses = responseRepository.findByAttempt(attempt);
        int totalQuestions = Math.toIntExact(questionRepository.countByQuiz(attempt.getQuiz()));
        int correctAnswers = (int) responses.stream().filter(r -> r.getSelectedOption().isCorrect()).count();
        return buildResult(attempt, responses, totalQuestions, correctAnswers);
    }

    private ResultDTO buildResult(UserAttempt attempt, List<Response> responses, int totalQuestions, int correctAnswers) {
        List<DetailedResponseDTO> details = responses.stream().map(response -> {
            Option correct = optionRepository.findByQuestionAndIsCorrect(response.getQuestion(), true).orElse(null);
            return new DetailedResponseDTO(response.getQuestion().getId(), response.getQuestion().getText(), response.getSelectedOption().getText(), correct != null ? correct.getText() : "N/A", response.getSelectedOption().isCorrect());
        }).collect(Collectors.toList());
        return new ResultDTO(attempt.getId(), attempt.getScore(), correctAnswers, totalQuestions, totalQuestions - correctAnswers, details);
    }
}
