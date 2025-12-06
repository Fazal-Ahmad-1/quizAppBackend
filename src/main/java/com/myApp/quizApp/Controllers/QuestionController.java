package com.myApp.quizApp.Controllers;

import com.myApp.quizApp.Model.Question;
import com.myApp.quizApp.Services.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://quiz-app-frontend-eta-five.vercel.app/")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("getAllQuestions")
    public ResponseEntity<?> getAllQuestions(){
        return questionService.getAllQuestions();
    }
    @GetMapping("getQuestionsByCategory/{category}")
    public ResponseEntity<?> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<?> addQuestion(@RequestParam String username,@Valid @RequestBody Question question){
        return questionService.addQuestion(username,question);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@RequestParam String username,@PathVariable int id){
        return questionService.deleteById(id,username);
    }
    @PutMapping("update")
    public ResponseEntity<?>updateQuestion(@RequestParam String username,@Valid @RequestBody Question question){
        return questionService.updateQuestion(username,question);
    }
}
