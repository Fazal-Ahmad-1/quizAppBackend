package com.myApp.quizApp.Repository;

import com.myApp.quizApp.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM QUESTION Q WHERE Q.category=:category ORDER BY RAND() LIMIT :numQ ", nativeQuery = true)
    List<Question> findRandomQuestionByCategory(String category, int numQ);

    // List<Question> getAllQuestions();
}
