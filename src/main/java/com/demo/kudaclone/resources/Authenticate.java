package com.demo.kudaclone.resources;

import com.demo.kudaclone.DTO.LoginReq;
import com.demo.kudaclone.models.User;
import com.demo.kudaclone.repositories.UserRepository;
import com.demo.kudaclone.security.SecurityUtils;
import com.demo.kudaclone.security.TokenProvider;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import liquibase.pro.packaged.S;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class Authenticate {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public Authenticate(UserRepository userRepository, TokenProvider tokenProvider,
                        AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/login")
    @ApiOperation("Login User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "registration successful"),
            @ApiResponse(code = 400, message = "bad request: request parameter error"),
            @ApiResponse(code = 500, message = "internal error")
    })
    @ApiParam(name = "login details")
    public ResponseEntity<Map<String, Object>> authenticate(@Valid @RequestBody LoginReq loginReq) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginReq.getLogin(),
                loginReq.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(loginReq.getLogin());
        User user = userRepository.findByEmailIgnoreCase(SecurityUtils.getCurrentUserLogin().get()).get();
        return new ResponseEntity<>(prepareResponse(jwt, user), HttpStatus.OK);
    }

    private Map<String, Object> prepareResponse(String token, User user) {
        Map<String, Object> loginRes = new HashMap<>();
        loginRes.put("token", token);
        loginRes.put("body", user);
        return loginRes;
    }
}
