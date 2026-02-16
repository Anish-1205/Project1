package com.quizapp.repository;
import com.quizapp.entity.UserAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserAttemptRepository extends JpaRepository<UserAttempt, Long> {
    Optional<UserAttempt> findByUserIdAndQuizIdAndEndTimeIsNull(String userId, Long quizId);
}
