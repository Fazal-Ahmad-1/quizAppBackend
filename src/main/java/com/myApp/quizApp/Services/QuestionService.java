package com.myApp.quizApp.Services;

import com.myApp.quizApp.Model.Question;
import com.myApp.quizApp.Model.User;
import com.myApp.quizApp.Repository.QuestionRepository;
import com.myApp.quizApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    UserRepository userRepository;


    private boolean isAdmin(String username){
        User user=userRepository.findByUsername(username);
        if(user==null || !user.getRole().equalsIgnoreCase("ADMIN"))return false;
        return true;
    }

    public ResponseEntity<?> getAllQuestions() {

        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getQuestionsByCategory(String category) {
        List<Question>questionList=questionRepository.findByCategory(category);
        if(questionList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(questionList,HttpStatus.FOUND);
    }

    public ResponseEntity<?> addQuestion(String username,Question question) {
        if(!isAdmin(username))
            return new ResponseEntity<>("Only ADMINS allowed!",HttpStatus.FORBIDDEN);
        try {
            questionRepository.save(question);
            return new ResponseEntity<>(question,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteById(int id,String username) {
        if(!isAdmin(username))
            return new ResponseEntity<>("Only ADMINS allowed!",HttpStatus.FORBIDDEN);
        try{
            questionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateQuestion(String username,Question question) {
        if(!isAdmin(username))
            return new ResponseEntity<>("Only ADMINS allowed!",HttpStatus.FORBIDDEN);
        try{
            questionRepository.save(question);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
