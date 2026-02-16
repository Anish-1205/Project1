package com.quizapp.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "responses")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "attempt_id")
    private UserAttempt attempt;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @ManyToOne
    @JoinColumn(name = "selected_option_id")
    private Option selectedOption;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public UserAttempt getAttempt() { return attempt; }
    public void setAttempt(UserAttempt attempt) { this.attempt = attempt; }
    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }
    public Option getSelectedOption() { return selectedOption; }
    public void setSelectedOption(Option selectedOption) { this.selectedOption = selectedOption; }
}
