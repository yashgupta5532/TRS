package com.trs.TRS.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trs.TRS.models.User;
import com.trs.TRS.repositories.UserRepository;
import com.trs.TRS.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // make the dependency injection as final with all the arguments
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ResponseEntity<?> signup(User user) {
        User existedUser = userRepository.findByUsername(user.getUsername());
        Map<String, Object> response = new HashMap<>();
        if (existedUser != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = userRepository.save(user);
        response.put("user", createdUser);
        response.put("message", "User Registered Successfully");
        return ResponseEntity.ok(response);

    }

    public ResponseEntity<?> login(String input,String password){
        User existingUser = findByUsernameOrEmail(input);
        Map<String, Object> response = new HashMap<>();

        if (existingUser == null) {
            response.put("message", "");
            return ResponseEntity.badRequest().body("SignUp first! We could not find your account");
        }

        boolean isPasswordMatch = checkPassword(password, existingUser.getPassword());
        if (isPasswordMatch) {
            String token = jwtUtil.generateToken(existingUser.getUsername());
            response.put("token", token);
            response.put("user", existingUser);
            response.put("message", existingUser.getUsername() + " Logged In Successfully");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(403).body("Invalid Password");
        }
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsernameOrEmail(String input) {
        if (isEmail(input)) {
            return findByEmail(input);
        } else {
            return findByUsername(input);
        }
    }

    public boolean isEmail(String input) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return input.matches(emailRegex);
    }
}
