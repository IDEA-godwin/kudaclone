package com.demo.kudaclone.resources;

import com.demo.kudaclone.DTO.PasswordReqDTO;
import com.demo.kudaclone.models.User;
import com.demo.kudaclone.repositories.UserRepository;
import com.demo.kudaclone.security.SecurityUtils;
import com.demo.kudaclone.services.PasswordReset;
import com.demo.kudaclone.services.UserService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.Instant;

@RestController
@RequestMapping("api/v1/users")
@Api(tags = "users")
public class UserResources {

    private final UserService userService;
    private final PasswordReset passwordReset;

    public UserResources(UserService userService, PasswordReset passwordReset) {
        this.userService = userService;
        this.passwordReset = passwordReset;
    }

    @PostMapping("/register")
    @ApiOperation("User registration")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "registration successful"),
            @ApiResponse(code = 400, message = "bad request: request parameter error"),
            @ApiResponse(code = 500, message = "internal error")
    })
    public ResponseEntity<User> registerUser(@Valid @ApiParam(name = "user info")
                                                 @RequestBody User user) {
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.OK);
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam(name = "key") String activationKey) {
        try {
            userService.activateAccount(activationKey);
            return ResponseEntity.ok("Account has been activated");
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        }
    }

    @GetMapping("/me")
    @ApiOperation(value = "Get logged in User profile", authorizations = { @Authorization("apikey") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "registration successful"),
            @ApiResponse(code = 403, message = "Access denied: forbidden"),
            @ApiResponse(code = 422, message = "Access denied: user not logged in"),
            @ApiResponse(code = 500, message = "internal error")
    })
    public ResponseEntity<User> viewProfile() {
        return new ResponseEntity<>(userService.viewProfile(), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> requestPasswordReset(@RequestBody String email) {
        return new ResponseEntity<>(passwordReset.requestPasswordReset(email), HttpStatus.OK);
    }

    @PostMapping("/reset-password?resetKey={resetKey}")
    public ResponseEntity<User> resetPassword(@PathVariable String resetKey, @RequestBody PasswordReqDTO passwordReq) {
        return new ResponseEntity<>(passwordReset.resetPassword(resetKey, passwordReq.getPassword(), passwordReq.getRePassword()),
                            HttpStatus.OK);
    }
}
