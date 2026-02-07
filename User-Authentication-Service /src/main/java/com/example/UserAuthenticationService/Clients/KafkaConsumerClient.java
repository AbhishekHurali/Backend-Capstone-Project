package com.example.UserAuthenticationService.Clients;

import com.example.UserAuthenticationService.Utils.EmailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.example.UserAuthenticationService.Dtos.EmailDto;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Component
public class KafkaConsumerClient {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "new_user",groupId = "emailService")
    //This sendEmail method will be running in many servers for the scalability.
    //We don't want to send the email multiple times because it is deployed in many servers.
    //By using groupId if one method starts processing then other servers will not entertain it.
    public void sendEmail(String message){

        try{
            System.out.println("Message listened!");

            EmailDto emailDto = objectMapper.readValue(message, EmailDto.class);
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailDto.getFrom(), "ofakrlljvucqtmno");
                }
            };
            Session session = Session.getInstance(props, auth);

            EmailUtil.sendEmail(session, emailDto.getTo(),emailDto.getSubject(), emailDto.getBody());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
