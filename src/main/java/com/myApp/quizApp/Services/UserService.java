package com.myApp.quizApp.Services;

import com.myApp.quizApp.Model.*;
import com.myApp.quizApp.Repository.QuizAttemptRepository;
import com.myApp.quizApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    QuizAttemptRepository quizAttemptRepository;

    public ResponseEntity<?>createUser(User user) {
        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Something cooked!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> login(String username, String password) {
        User user=userRepository.findByUsername(username);
        if(user==null){
            return new ResponseEntity<>("No such username",HttpStatus.NOT_FOUND);
        }

        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());

        if(!passwordMatches){
            return new ResponseEntity<>("Invalid password",HttpStatus.UNAUTHORIZED);
        }
        else{
            LoginDTO loginResponse=new LoginDTO();
            loginResponse.setUsername(user.getUsername());
            loginResponse.setRole(user.getRole());
            return new ResponseEntity<>(loginResponse,HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getUserAttempts(String username){
        User user=userRepository.findByUsername(username);
        if(user==null){
            return new ResponseEntity<>("No User Found",HttpStatus.NOT_FOUND);
        }
        List<QuizAttempt> attempts=quizAttemptRepository.findByUserUsername(username);
        List<QuizAttemptDTO>attemptsResponses=new ArrayList<>();
        for(QuizAttempt qa:attempts){
         QuizAttemptDTO attempResponse=new QuizAttemptDTO();
         attempResponse.setAttemptId(qa.getId());
         attempResponse.setQuizId(qa.getQuiz().getId());
         attempResponse.setQuizTitle(qa.getQuiz().getTitle());
         attempResponse.setScore(qa.getScore());
         attempResponse.setTotalQuestions(qa.getTotalQuestions());
         attempResponse.setAttemptedAt(qa.getAttemptedAt());
         attemptsResponses.add(attempResponse);
        }
        return new ResponseEntity<>(attemptsResponses,HttpStatus.OK);
    }
    public ResponseEntity<?> getUserStats(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        List<QuizAttempt> attempts = quizAttemptRepository.findByUserUsername(username);
        if (attempts.isEmpty()) {
            return new ResponseEntity<>("No attempts found for user", HttpStatus.OK);
        }

        int totalQuizzes = attempts.size();
        int totalScore = attempts.stream().mapToInt(QuizAttempt::getScore).sum();
        int totalQuestions = attempts.stream().mapToInt(QuizAttempt::getTotalQuestions).sum();
        int bestScore = attempts.stream().mapToInt(QuizAttempt::getScore).max().orElse(0);

        double averageScore = (double) totalScore / totalQuizzes;
        double averagePercentage = totalQuestions == 0 ? 0.0 :
                (double) totalScore * 100.0 / totalQuestions;

        UserStats stats = new UserStats(
                username,
                totalQuizzes,
                totalScore,
                totalQuestions,
                bestScore,
                averageScore,
                averagePercentage
        );

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
