package com.example.routinegrowth.controller;

import com.example.routinegrowth.DTO.UserRequest;
import com.example.routinegrowth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userReqeust) {

    return ResponseEntity.ok(userService.createUser(userReqeust));
  }
}
