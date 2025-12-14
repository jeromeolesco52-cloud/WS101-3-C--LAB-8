package com.olesco.lab_7.controller;

import com.olesco.lab_7.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping
    public User getUser() {
        return User.builder()
                .username("blueJava")
                .password("thisIsAPassword!")
                .enabled(false)
                .uid(0)
                .build();
    }
}