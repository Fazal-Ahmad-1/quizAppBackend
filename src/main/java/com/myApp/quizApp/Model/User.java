package com.myApp.quizApp.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;


@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,unique = true)
    @NotBlank(message = "Username is required field")
    @Size(min=3,max=30,message = "Username must be 3-30 characters long!")
    private String username;

    @NotBlank(message = "Password is required field")
    @Size(min = 5,message = "password must be atleast 5 character long")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Role Required!")
    private String role;

}
