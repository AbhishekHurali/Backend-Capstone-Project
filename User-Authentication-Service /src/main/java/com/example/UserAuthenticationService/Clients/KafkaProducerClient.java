package com.example.UserAuthenticationService.Clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerClient {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate; //In this we are not overriding the bean of kafka
    //as we did in the Redis -> like setting connection factory.

    public void sendMessage(String topic, String messageContent){
        System.out.println("Sending message to Kafka!");
        kafkaTemplate.send(topic,messageContent);
    }
}
//Comprehensive Revision Notes on Kafka Integration in a User Authentication Service
//These notes cover the integration of Apache Kafka into a user authentication service, as discussed in the class. The content is derived from the audio transcript provided and is intended for learners seeking to solidify their understanding of Kafka's practical applications in software engineering.
//
//Introduction to Kafka in Authentication Services
//Background
//In modern distributed systems, like those supporting online retail or media platforms, it's essential to manage asynchronous processes efficiently. Apache Kafka, a distributed streaming platform, is perfectly suited for handling such dynamic data workflows.
//
//Objectives
//The primary goal here is to integrate Kafka into a user authentication service to manage events such as user signups. This involves sending messages about user registrations to a Kafka topic, which can then be consumed by a notification service to send a welcome email.
//
//Key Concepts and Components
//User Signup Flow
//User Signup Detection: The system detects a new user signup event during the user authentication process.
//Database Entry: A new user entry is created in the database following successful authentication checks.
//Kafka Message Production: Post database entry, a message about the new user is sent to a Kafka topic.
//Message Structure
//The message sent to Kafka should include essential data such as:
//
//Event type (e.g., "New User Signup")
//User email
//Other metadata (as needed).
//Kafka Components Used
//Producer: Responsible for sending user signup information to Kafka.
//Consumer: Listens for messages and performs actions based on them, such as sending emails.
//Implementation Steps
//Setting Up Kafka Producer
//Configuration: Define Kafka producer configuration using Spring annotations.
//
//Use @Component or @Configuration annotations as appropriate.
//Employ KafkaTemplate for sending messages.
//Message Sending Logic:
//
//Encapsulate message details in a DTO (Data Transfer Object).
//Use KafkaTemplate to send messages to the specified topic.
//Setting Up Kafka Consumer
// Definition:
//Set up a consumer using @KafkaListener, specifying the topic to listen to.
//Process received messages to trigger further actions, such as sending an email.
//Practical Applications
//Use Case Scenarios
//Email Notification Service: Upon receiving a signup event, the notification service sends a welcome email. This is handled asynchronously, meaning the user service doesn't wait for the email sending process to complete, enhancing system efficiency.
//System Scalability: Kafka helps decouple user registration processes from notification services, enabling easier scaling and better resource utilization.
//Conclusion
//The integration of Kafka makes the system more responsive and robust by handling user registration events asynchronously. Kafka's distributed nature ensures reliability and scalability, especially under high user registration loads.
//
//These notes summarize how Kafka can be integrated into a service architecture to handle event-driven communication efficiently. By maintaining separation of concerns through dedicated components (i.e., producers and consumers), such systems can achieve high throughput and resilience.