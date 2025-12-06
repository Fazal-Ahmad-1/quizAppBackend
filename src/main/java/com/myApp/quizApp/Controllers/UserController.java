package com.myApp.quizApp.Controllers;

import com.myApp.quizApp.Model.User;
import com.myApp.quizApp.Model.UserDTO;
import com.myApp.quizApp.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://quiz-app-frontend-eta-five.vercel.app/")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("create")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user){
        return userService.createUser(user);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){
        return userService.login(userDTO.getUsername(),userDTO.getPassword());
    }

    @GetMapping("{username}/attempts")
    public ResponseEntity<?> getUserAttempts(@PathVariable String username){
        return userService.getUserAttempts(username);
    }
    @GetMapping("{username}/stats")
    public ResponseEntity<?> getUserStats(@PathVariable String username){
        return userService.getUserStats(username);
    }
}
