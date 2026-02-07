package com.example.UserAuthenticationService.Services;

import com.example.UserAuthenticationService.Models.User;

import org.antlr.v4.runtime.misc.Pair;
import org.springframework.util.MultiValueMap;

public interface IAuthService {

    User signup(String email, String password);

    Pair<User, MultiValueMap<String,String>> login(String email, String password);

    public Boolean validToken(Long userId,String token);
}
