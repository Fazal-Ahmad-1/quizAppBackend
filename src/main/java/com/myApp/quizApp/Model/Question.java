package com.myApp.quizApp.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data // to get Getters and Setters for all!
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @NotBlank(message = "Question title is required")
    private String questionTitle;

    @NotBlank(message = "Question category is required")
    private String category;
    @NotBlank(message = "Option1 is required")
    private String option1;
    @NotBlank(message = "Option2 is required")
    private String option2;
    @NotBlank(message = "Option3 is required")
    private String option3;
    @NotBlank(message = "Option4 is required")
    private String option4;
    @NotBlank(message = "Right Answer is required")
    private String rightAnswer;
    @NotBlank(message = "Difficulty Level is required")
    private String difficultyLevel;


}
