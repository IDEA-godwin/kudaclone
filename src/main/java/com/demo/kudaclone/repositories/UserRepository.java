package com.demo.kudaclone.repositories;

import com.demo.kudaclone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    @Nullable
    User findByActivationKey(String key);

    @Nullable
    User findByResetKey(String key);
}
