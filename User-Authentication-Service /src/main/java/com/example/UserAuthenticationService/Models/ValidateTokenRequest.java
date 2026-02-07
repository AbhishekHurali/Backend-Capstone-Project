package com.example.UserAuthenticationService.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenRequest {

    private Long userId;

    private String token;
}
