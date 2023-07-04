package com.example.oxygen.controller;

import com.example.oxygen.entity.User;
import com.example.oxygen.model.UserDTO;
import com.example.oxygen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {

        return userService.getUsers();
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Validated UserDTO userDTO) {

        return ResponseEntity.ok(userService.saveUser(userDTO));
    }
}
