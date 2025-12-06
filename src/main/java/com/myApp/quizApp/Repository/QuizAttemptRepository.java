package com.myApp.quizApp.Repository;

import com.myApp.quizApp.Model.Quiz;
import com.myApp.quizApp.Model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt,Integer> {

    List<QuizAttempt> findByUserUsername(String username);
    List<QuizAttempt> findByQuizId(int quizId);
}
