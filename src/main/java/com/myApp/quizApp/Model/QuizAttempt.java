package com.myApp.quizApp.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    private Quiz quiz;

    private int score;
    private int totalQuestions;
    private LocalDateTime attemptedAt;
}
