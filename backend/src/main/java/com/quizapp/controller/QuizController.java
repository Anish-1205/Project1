package com.quizapp.controller;
import com.quizapp.dto.*;
import com.quizapp.service.QuizService;
import com.quizapp.service.AttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "*")
public class QuizController {
    @Autowired private QuizService quizService;
    @Autowired private AttemptService attemptService;

    @GetMapping
    public ResponseEntity<List<QuizListDTO>> getAllQuizzes() { return ResponseEntity.ok(quizService.getAllQuizzes()); }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDetailDTO> getQuizDetail(@PathVariable Long quizId) { return ResponseEntity.ok(quizService.getQuizDetail(quizId)); }

    @PostMapping("/{quizId}/attempt/start")
    public ResponseEntity<AttemptStartDTO> startAttempt(@PathVariable Long quizId, @RequestParam String userId) { return ResponseEntity.ok(attemptService.startAttempt(quizId, userId)); }

    @PostMapping("/attempt/{attemptId}/response")
    public ResponseEntity<ResponseSubmitDTO> submitResponse(@PathVariable Long attemptId, @RequestBody ResponseSubmitRequestDTO request) { return ResponseEntity.ok(attemptService.submitResponse(attemptId, request)); }

    @PostMapping("/attempt/{attemptId}/submit")
    public ResponseEntity<ResultDTO> submitQuiz(@PathVariable Long attemptId) { return ResponseEntity.ok(attemptService.submitQuiz(attemptId)); }

    @GetMapping("/attempt/{attemptId}/result")
    public ResponseEntity<ResultDTO> getResult(@PathVariable Long attemptId) { return ResponseEntity.ok(attemptService.getResult(attemptId)); }
}
