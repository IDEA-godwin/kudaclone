package com.demo.kudaclone.resources;

import com.demo.kudaclone.models.User;
import com.demo.kudaclone.repositories.UserRepository;
import com.demo.kudaclone.security.SecurityUtils;
import io.swagger.annotations.*;
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
    @ApiOperation("User registration")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "registration successful"),
            @ApiResponse(code = 400, message = "bad request: request parameter error"),
            @ApiResponse(code = 500, message = "internal error")
    })
    @ApiParam(name = "user info")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    @GetMapping("/me")
    @ApiOperation(value = "Get logged in User profile", authorizations = { @Authorization("apikey") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "registration successful"),
            @ApiResponse(code = 403, message = "Access denied: forbidden"),
            @ApiResponse(code = 422, message = "Access denied: user not logged in"),
            @ApiResponse(code = 500, message = "internal error")
    })
    public ResponseEntity<?> viewProfile() {
        User user = SecurityUtils.getCurrentUserLogin()
                .map( username -> userRepository.findByLoginOrEmail(username, username).get())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User Not logged in"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
