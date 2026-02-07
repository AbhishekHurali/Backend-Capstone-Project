package com.example.UserAuthenticationService.Controllers;

import com.example.UserAuthenticationService.Dtos.UserDto;
import com.example.UserAuthenticationService.Models.User;
import com.example.UserAuthenticationService.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    public UserDto getUserDetails(@PathVariable Long id){
        User user = userService.getUserDetails(id);

        return from(user);
    }

    public UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        //userDto.setRoles(user.getRoles());
        return userDto;
    }
}
