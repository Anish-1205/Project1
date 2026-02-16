package com.quizapp.repository;
import com.quizapp.entity.Question;
import com.quizapp.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuiz(Quiz quiz);
    Long countByQuiz(Quiz quiz);
}
