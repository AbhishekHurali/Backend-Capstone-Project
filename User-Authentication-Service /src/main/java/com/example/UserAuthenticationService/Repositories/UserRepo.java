package com.example.UserAuthenticationService.Repositories;

import com.example.UserAuthenticationService.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

}
