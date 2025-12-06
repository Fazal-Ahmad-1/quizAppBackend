package com.myApp.quizApp.Model;

import lombok.Data;

@Data
public class QuestionWrapper {
    private int Id;
    private String category;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public QuestionWrapper(int id, String category,String questionTitle, String option1, String option2, String option3, String option4) {
        Id = id;
        this.category = category;
        this.questionTitle=questionTitle;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }
}
