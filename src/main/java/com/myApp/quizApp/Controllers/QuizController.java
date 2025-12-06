package com.myApp.quizApp.Controllers;

import com.myApp.quizApp.Model.Question;
import com.myApp.quizApp.Model.QuestionWrapper;
import com.myApp.quizApp.Model.Quiz;
import com.myApp.quizApp.Model.UserResponse;
import com.myApp.quizApp.Repository.QuizRepository;
import com.myApp.quizApp.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("quiz")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://quiz-app-frontend-eta-five.vercel.app/")
public class QuizController {
    @Autowired
    QuizService quizService;
    @Autowired
    private QuizRepository quizRepository;

    @PostMapping("create")
    public ResponseEntity<?> createQuiz(@RequestParam String username,@RequestParam String category,@RequestParam int numQ, @RequestParam String title)
    {
        return quizService.createQuiz(username,category,numQ,title);
    }
    @GetMapping("all")
    public ResponseEntity<?>getAllQuizzes(){
        return quizService.getAllQuizzes();
    }
    @GetMapping("getQuiz/{id}")
    public ResponseEntity<?>getQuiz(@PathVariable int id){
        return quizService.getQuiz(id);
    }
    @PostMapping("submit/{id}")
    public ResponseEntity<?>submit(@PathVariable int id,@RequestParam String username,@RequestBody List<UserResponse>responses){
        return quizService.calculateResult(id,username,responses);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable int id,@RequestParam String username){
        return quizService.deleteQuiz(id,username);
    }

}