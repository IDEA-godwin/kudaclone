package com.demo.kudaclone.services;

import com.demo.kudaclone.models.User;
import com.demo.kudaclone.repositories.UserRepository;
import com.demo.kudaclone.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
public class PasswordReset {

    UserRepository userRepository;

    public PasswordReset(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String requestPasswordReset(String email) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email Not Found"));
        user.setResetKey(SecurityUtils.generateActivationKey());
        user.setResetDate(Instant.now());
        userRepository.save(user);
        return user.getResetKey();
    }

    public User resetPassword(String resetKey, String password, String rePassword) {
        User user = userRepository.findByResetKey(resetKey);
        Assert.notNull(user, "Reset Key Invalid");
        Assert.isTrue(password.equals(rePassword), "Passwords most match");
        user.setPassword(password);
        return userRepository.save(user);
    }
}
