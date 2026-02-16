package com.quizapp.repository;
import com.quizapp.entity.Option;
import com.quizapp.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByQuestion(Question question);
    Optional<Option> findByQuestionAndIsCorrect(Question question, boolean isCorrect);
}
