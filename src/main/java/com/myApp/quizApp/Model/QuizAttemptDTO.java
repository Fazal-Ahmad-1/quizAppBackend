package com.myApp.quizApp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizAttemptDTO {
    private int attemptId;
    private int quizId;
    private String quizTitle;
    private int score;
    private int totalQuestions;
    private LocalDateTime attemptedAt;
}
