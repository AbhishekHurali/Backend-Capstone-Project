package com.example.UserAuthenticationService.Configs;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
public class SpringSecurity {

        //@Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.cors().disable();
//        httpSecurity.csrf().disable();
//        httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
//        return httpSecurity.build();// We commented this code because it was overlapping while we are coding OAuth2 coding.
        //We wanted BcryptPasswordEncoder. So we added SpringBoot starter security.
        //But it has created the security wall between dispatcher servlet and controller.
        // Everytime it was asking for username and password.
        //To remove that security wall for now and just use the BcryptPasswordEncoder we have written the code.


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
        //There might be different constructors of bcryptPasswordEncoder
        //like many parameterized constructor in the implementation
        //But by defining the bean we are saying the spring to use the default constructor.
    }

    @Bean // We are defining the secret key here. There will be only one secret key used in the project now.
    public SecretKey getSecretKey(){
        MacAlgorithm algorithm = Jwts.SIG.HS256;
        SecretKey secretKey = algorithm.key().build();
        return secretKey;
    }// But this might also refresh the token whenever the server restarts again.
}
