package com.myApp.quizApp.Repository;

import com.myApp.quizApp.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Integer> {

}
