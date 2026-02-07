package com.example.UserAuthenticationService.Services;

import com.example.UserAuthenticationService.Clients.KafkaProducerClient;
import com.example.UserAuthenticationService.Dtos.EmailDto;
import com.example.UserAuthenticationService.Exceptions.IncorrectPassword;
import com.example.UserAuthenticationService.Exceptions.UserAlreadyExistsException;
import com.example.UserAuthenticationService.Exceptions.UserDoesNotExistsException;
import com.example.UserAuthenticationService.Models.Status;
import com.example.UserAuthenticationService.Models.User;
import com.example.UserAuthenticationService.Models.UserSession;
import com.example.UserAuthenticationService.Repositories.SessionRepo;
import com.example.UserAuthenticationService.Repositories.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private SecretKey secretKey;

    @Autowired
    private KafkaProducerClient kafkaProducerClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public User signup(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistsException("User Already Exists! Try With Different email!!");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepo.save(user);

//        //Send an Email signed-up User
        try{
            EmailDto emailDto = new EmailDto();
            emailDto.setTo(email);
            emailDto.setFrom("abhishekhurali19@gmail.com");
            emailDto.setSubject("Welcome Email!");
            emailDto.setBody("Welcome onboard! Hope you have a great future here!");
            kafkaProducerClient.sendMessage("new_user",objectMapper.writeValueAsString(emailDto));
        }catch (JsonProcessingException e){
            throw new RuntimeException(e.getMessage());
        }
        
        return user;
    }

    @Override
    public Pair<User,MultiValueMap<String,String>> login(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new UserDoesNotExistsException("Please Signup First!!");
        }
        if(!bCryptPasswordEncoder.matches(password,optionalUser.get().getPassword())){// matches will first encode the first parameter and compare with the password stored in the database.
            throw new IncorrectPassword("Password Didn't Match!!");
        }

        Map<String,Object> userClaims = new HashMap<>();
        userClaims.put("userId",optionalUser.get().getId());
        userClaims.put("permissions",optionalUser.get().getRoles());
        Long currentTimeInMillis = System.currentTimeMillis();
        userClaims.put("iat",currentTimeInMillis);
        userClaims.put("exp",currentTimeInMillis+8640000);
        userClaims.put("issuer","scaler");

        //Separately defined in the config class with @bean
//        MacAlgorithm algorithm = Jwts.SIG.HS256; // Finding the algorithm
//        SecretKey secretKey = algorithm.key().build();//Building the secretKey. Gives different secretKey everytime when we use these lines
        String token = Jwts.builder().claims(userClaims).signWith(secretKey).compact(); // Building the token using Header, Payload and Signature
        //We have Map<String,String> hence we are using .claims()
        //Otherwise we can use content() when we have string data converted into byte[]
        //String message = {content in json like userId, roles etc}
        //Example : byte[] content = message.getBytes(StandardCharsets.UTF_8)
        MultiValueMap<String,String> headers  = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE,token);

        //Persisted token for validation purpose
        UserSession userSession = new UserSession();
        userSession.setToken(token);
        userSession.setUser(optionalUser.get());
        userSession.setStatus(Status.ACTIVE);
        sessionRepo.save(userSession);

        Pair<User,MultiValueMap<String,String>> response = new Pair<>(optionalUser.get(),headers);
        return response;
    }

    public Boolean validToken(Long userId,String token){

        //There will be only one session associated with one token
        Optional<UserSession> optionalUserSession = sessionRepo.findByTokenAndUser_Id(token, userId);

        if(optionalUserSession.isEmpty()){
            return false;
        }

        UserSession userSession = optionalUserSession.get();

        String persistedToken = userSession.getToken();

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(persistedToken).getPayload();
        Long expiryStoredInToken = (Long)claims.get("exp");
        Long currentTime = System.currentTimeMillis();

        System.out.println(expiryStoredInToken);
        System.out.println(currentTime);

        if(currentTime > expiryStoredInToken){
            userSession.setStatus(Status.INACTIVE);
            sessionRepo.save(userSession);
            return false;
        }

        return true;
    }
}
//Key Concepts and Implementation Steps
//1. JWT Token Generation
//User Claims: To generate a JWT token, it is crucial to create user claims that encapsulate the user's information, such as user ID, roles, and token expiry information【4:18†transcript.txt】. These claims are typically stored in a Map<String, Object>, which allows dynamic data representation.
//Byte Conversion: Once the user claims are structured, they are converted into byte format to be used in token generation.
//Token Creation: Using JSON Web Tokens (JWT) library, you construct a token by leveraging the builder design pattern. You pass the byte array of claims to JWT's builder and compact it to generate the token.

//2. HTTP Headers and Cookies
//Storing Token: The JWT token should be stored in cookies for the purpose of being used as metadata for authentication in client-server communication. This approach prevents exposure of the token directly in the front-end code.
//Metadata in Headers: Headers, which can be represented as a Multi-Value Map, contain vital metadata, such as tokens, which allow the server to authenticate users without exposing sensitive information.

//3. User Sessions
//Session Management: An effective session management involves storing sessions in a persistent storage medium (such as a database table dedicated to user sessions) to track active and inactive sessions.

//4. Token Expiry and Security
//Token Expiry Handling: The token's expiry is embedded within the payload. When validating a token, it is essential to check both the existence in the database and whether it is expired
//Security Considerations: To ensure security, personal information like email should be protected. As a result, user identifiers rather than explicit emails are embedded within tokens.

//5. Validation of Signature and Token
//Signature Use: The signature field in the token ensures that the token is verified and authenticated correctly by the resource server. This ensures that any request with the token is genuine.
//Token Validation Process: The class involves a demonstration of validation checks necessary for confirming token authenticity. These checks involve verifying both the signature and expiry.