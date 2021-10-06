package com.demo.kudaclone.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.Random;

public class SecurityUtils {

    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    public static String generateActivationKey() {
        StringBuilder key = new StringBuilder();
        Random random = new Random();
        String pi = Double.toString(Math.PI);
        for (int i = 0; i < 6; i++) {
            int randomInt = random.nextInt(pi.length());
            if(Character.toString(pi.charAt(randomInt)).equals("."))
                key.append(random.nextInt());
            key.append(pi.charAt(randomInt));
        }
        return key.toString();
    }

}
