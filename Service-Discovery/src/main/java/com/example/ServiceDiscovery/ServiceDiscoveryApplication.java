package com.example.ServiceDiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDiscoveryApplication.class, args);
	}

}

//Notes
//Understanding Microservices and Their Interactions
//Microservices architecture is a method of developing software systems that focuses on building single-function modules with well-defined interfaces and operations. It enables the building of flexible and scalable applications.
//
//Key Microservices Discussed:
//Payment Service: Handles payment processing.
//Product Service: Manages product-related queries.
//User Authentication Service: Manages user authentication and authorization.
//Basic Workflow:
//Microservices communicate with each other. For instance, the product service might need to call the payment service API to fetch pricing information.
//Service Discovery with Eureka
//Service Discovery helps manage and locate microservices in a network. Netflix's Eureka is a service registry that allows microservices to register themselves and discover other registered services to communicate with them.
//
//Key Concepts:
//Eureka Server: Acts as a service registry.
//Eureka Client: Registers itself with the Eureka server so it can be discovered by other services.
//Setting Up Eureka:
//Add dependency spring-cloud-starter-netflix-eureka-server to pom.xml for the server.
//For services that need to register, add the spring-cloud-starter-netflix-eureka-client dependency.
//Configure necessary properties to enable service registration with the Eureka server.
//Key Features of Eureka
//Client-Side Load Balancing: With each microservice communicating with the Eureka server, load balancing is achieved as part of the internal client requests. Services can distribute requests among instances for redundancy and load sharing.
//Caching: Services cache the address/host list of instances from Eureka to reduce the number of network hops needed for communication. This caching decreases as confidence in service uptime and reduces requests to the discovery service, only updating when necessary.
//Rest Template and API Calls
//		In microservices, RESTful API calls often leverage RestTemplate for HTTP requests. This assists in making HTTP requests in a Spring application. It supports:
//
//getForEntity(): For GET HTTP calls.
//postForEntity(): For POST HTTP calls.
//Developers can configure RestTemplate to make calls from one microservice to another, leveraging endpoints configured in the Eureka registry.
//
//Key Challenges and Solutions
//Network Hops: Communicating through multiple network hops can slow the system. With service discovery and caching, the system minimizes network hops facilitating quicker interactions between services.
//
//Load Balancing: By using round-robin requests across instances, intrinsic load balancing is managed by each microservice's client configuration.
//
//Dynamic Scaling: Automatically manages service load, allowing instances to scale in and out seamlessly as demand changes.
//
//Conclusion
//This class focused on the significance of using Spring Cloud and Netflix's Eureka for efficient communication between microservices. By embedding service discovery and internal load balancing mechanisms, the architecture increases the robustness and scalability of the software systems. Understanding these systems is crucial for effectively building and maintaining modern, distributed applications.