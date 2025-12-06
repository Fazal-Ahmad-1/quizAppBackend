package com.myApp.quizApp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserStats {

    private String username;
    private int totalQuizzesTaken;
    private int totalScore;
    private int totalQuestions;
    private int bestScore;
    private double averageScorePerQuiz;
    private double averagePercentage;
}
