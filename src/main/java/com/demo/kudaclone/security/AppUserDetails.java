package com.demo.kudaclone.security;

import com.demo.kudaclone.models.User;
import com.demo.kudaclone.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class AppUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetails(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if(userRepository.findByEmailIgnoreCase(email).isPresent()) {
            User user = userRepository.findByEmailIgnoreCase(email).get();
            return buildCustomUser(user);
        }

        throw new UsernameNotFoundException("user not found");
    }

    private UserDetails buildCustomUser(User user) {
        return new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(), new HashSet<>());
    }
}
