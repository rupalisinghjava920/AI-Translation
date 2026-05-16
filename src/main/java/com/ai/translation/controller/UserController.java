package com.ai.translation.controller;

import com.ai.translation.Exception_handler.CustomException;
import com.ai.translation.config.JwtUtil;
import com.ai.translation.dto.LoginRequest;
import com.ai.translation.entity.Role;
import com.ai.translation.entity.User;
import com.ai.translation.repository.UserRepository;
import com.ai.translation.unit.Constant;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
//@CrossOrigin("http://localhost:3000/")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
//
//        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
//            throw new CustomException(Constant.USER_ALREADY_EXISTS);
//        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Constant.USER_ALREADY_EXISTS);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

       //default role set karo
        Role roles = new Role();
        roles.setRoles("USER");
        roles.setUser(user);

        user.setRoles(Set.of(roles));

        userRepository.save(user);

        return ResponseEntity.ok(Constant.USER_REGISTER_SUCCESS);
    }

    @PostMapping("/login")
    public ResponseEntity<?>  login(@Valid @RequestBody LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new CustomException(Constant.INVALID_CREDENTIALS);
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException("User not found"));

        List<String> roles = user.getRoles().stream()
                .map(Role::getRoles)
                .collect(Collectors.toList());

        String token = jwtUtil.generateToken(request.getUsername(), roles);

        return ResponseEntity.ok(Map.of(
                "message", Constant.USER_LOGIN_SUCCESS,
                "token", token,
                "userId", user.getId()
        ));
    }
}

