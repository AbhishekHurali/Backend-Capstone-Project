package com.example.UserAuthenticationService.Controllers;

import com.example.UserAuthenticationService.Dtos.LoginRequestDto;
import com.example.UserAuthenticationService.Dtos.SignUpRequestDto;
import com.example.UserAuthenticationService.Dtos.UserDto;
import com.example.UserAuthenticationService.Exceptions.IncorrectPassword;
import com.example.UserAuthenticationService.Exceptions.UserAlreadyExistsException;
import com.example.UserAuthenticationService.Models.User;
import com.example.UserAuthenticationService.Models.ValidateTokenRequest;
import com.example.UserAuthenticationService.Services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        try{
            User user = authService.signup(signUpRequestDto.getEmail(),signUpRequestDto.getPassword());
            return from(user);
        }catch(UserAlreadyExistsException exception){
            //Handle the exception handler
            throw exception;

        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto){
        try{
            Pair<User, MultiValueMap<String,String>> response = authService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword());
            UserDto userDto = from(response.a);
            return new ResponseEntity<>(userDto,response.b, HttpStatusCode.valueOf(200));
        }catch(IncorrectPassword exception){
            //Handle the exception handler
            throw exception;
        }

    }

    @PostMapping("/validateToken")
    public Boolean validateTokenRequest(@RequestBody ValidateTokenRequest validateTokenRequest){
        return authService.validToken(validateTokenRequest.getUserId(),validateTokenRequest.getToken());
    }

    public UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        //userDto.setRoles(user.getRoles());
        return userDto;
    }



}
