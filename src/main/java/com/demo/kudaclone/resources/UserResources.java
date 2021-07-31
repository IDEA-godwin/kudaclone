package com.demo.kudaclone.resources;

import com.demo.kudaclone.models.User;
import com.demo.kudaclone.repositories.UserRepository;
import com.demo.kudaclone.security.SecurityUtils;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
@Api(tags = "users")
public class UserResources {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResources(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<?> viewProfile() {
        String username = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User Not logged in"));
        System.out.println(username);
        return new ResponseEntity<>(username, HttpStatus.OK);
    }
}
