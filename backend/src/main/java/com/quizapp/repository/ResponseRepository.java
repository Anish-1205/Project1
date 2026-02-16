package com.quizapp.repository;
import com.quizapp.entity.Response;
import com.quizapp.entity.UserAttempt;
import com.quizapp.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByAttempt(UserAttempt attempt);
    Optional<Response> findByAttemptAndQuestion(UserAttempt attempt, Question question);
}
