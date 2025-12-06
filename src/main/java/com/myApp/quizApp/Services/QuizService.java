package com.myApp.quizApp.Services;

import com.myApp.quizApp.Model.*;
import com.myApp.quizApp.Repository.QuestionRepository;
import com.myApp.quizApp.Repository.QuizAttemptRepository;
import com.myApp.quizApp.Repository.QuizRepository;
import com.myApp.quizApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    QuizAttemptRepository quizAttemptRepository;

    private boolean isAdmin(String username){
        User user=userRepository.findByUsername(username);
        if(user==null || !user.getRole().equalsIgnoreCase("ADMIN"))return false;
        return true;
    }

    public ResponseEntity<?> createQuiz(String username,String category, int numQ, String title) {
        if(!isAdmin(username))
            return new ResponseEntity<>("Only ADMINS allowed!",HttpStatus.FORBIDDEN);
        List<Question>questions=questionRepository.findRandomQuestionByCategory(category,numQ);
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);

    }
    public ResponseEntity<?> getAllQuizzes(){
        try{
            return new ResponseEntity<>(quizRepository.findAll(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> getQuiz(int id) {
        Quiz quiz=quizRepository.findById(id).orElse(null);
        if(quiz==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            List<Question> questionsFromDB = quiz.getQuestions();
            List<QuestionWrapper> questionsForUser=new ArrayList<>();
            for(Question q:questionsFromDB)
            {
                QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getCategory(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
                questionsForUser.add(qw);
            }
            return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
        }
    }

    public ResponseEntity<?> calculateResult(int id,String username,List<UserResponse> responses) {
        Quiz quiz=quizRepository.findById(id).orElse(null);

        if(quiz==null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        User user=userRepository.findByUsername(username);
        if(user==null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Question> questions = quiz.getQuestions();
        int total = 0;
        int i = 0;
        for (UserResponse response : responses) {
            if (response.getAnswer().equals(questions.get(i).getRightAnswer()))
                    total += 1;
                i++;
        }

        QuizAttempt quizAttempt=new QuizAttempt();
        quizAttempt.setUser(user);
        quizAttempt.setQuiz(quiz);
        quizAttempt.setScore(total);
        quizAttempt.setTotalQuestions(i);
        quizAttempt.setAttemptedAt(LocalDateTime.now());

        quizAttemptRepository.save(quizAttempt);

        QuizResult result=new QuizResult(total,i);
        return new ResponseEntity<>(result,HttpStatus.OK);

    }

    public ResponseEntity<?> deleteQuiz(int id,String username) {
        if(!isAdmin(username))
            return new ResponseEntity<>("Only ADMINS allowed!",HttpStatus.FORBIDDEN);
        try{
            quizRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
