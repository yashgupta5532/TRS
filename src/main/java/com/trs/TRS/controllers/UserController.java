package com.trs.TRS.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trs.TRS.models.User;
import com.trs.TRS.services.UserService;
import com.trs.TRS.utils.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user/auth")
public class UserController {

    private UserService userService;
    private JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        userService.signup(user);

        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("message", "User Registered Successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        System.out.println("user is" + user.getUsername());
        User existingUser = userService.findByUsername(user.getUsername());
        System.out.println("existingUser is" + existingUser);
        Map<String, Object> response = new HashMap<>();

        if (existingUser == null) {
            response.put("message", "SignUp first! We could not find your account");
            return ResponseEntity.status(403).body(response);
        }

        boolean isPasswordMatch = userService.checkPassword(user.getPassword(), existingUser.getPassword());
        if (isPasswordMatch) {
            String token = jwtUtil.generateToken(existingUser.getUsername());
            response.put("token", token);
            response.put("user", existingUser);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid Password");
            return ResponseEntity.status(403).body(response);
        }
    }

}
