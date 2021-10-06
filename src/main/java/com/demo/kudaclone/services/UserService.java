package com.demo.kudaclone.services;

import com.demo.kudaclone.models.User;
import com.demo.kudaclone.repositories.UserRepository;
import com.demo.kudaclone.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivationKey(SecurityUtils.generateActivationKey());
        user.setCreateDate(Instant.now());
        return userRepository.save(user);
    }

    public void activateAccount(String key) {
        System.out.println(key);
        User account = userRepository.findByActivationKey(key);
        Assert.notNull(account, "Activation key invalid");
        account.setActivated(true);
        account.setActivationKey(null);
        userRepository.save(account);
    }

    public User viewProfile() {
        return SecurityUtils.getCurrentUserLogin()
                .map( email -> userRepository.findByEmailIgnoreCase(email).get())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User Not logged in"));
    }

    public String resetPassword(String password) {
        return "";
    }

    private String generateAccountNumber() {
        return "0441030119";
    }

}
