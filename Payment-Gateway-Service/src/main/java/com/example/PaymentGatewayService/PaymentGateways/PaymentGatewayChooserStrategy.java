package com.example.PaymentGatewayService.PaymentGateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayChooserStrategy {//Strategy design pattern

    @Autowired
    private RazorpayPaymentGateway razorpayPaymentGateway;

    @Autowired
    private StripePaymentGateway stripePaymentGateway;



    //We can have strategy here
    //Either we can have odd OR even rule
    //OR selecting random paymentGateway
    //Or based on some metrics ex: Success rate
    // This is to avoid dependency on one payment gateway
    public IPaymentGateway getBestPaymentGateway(){

        //return razorpayPaymentGateway;

        return stripePaymentGateway;
    }

}
//Notes
//Payment Gateway Integration: Razorpay and Stripe
//        Introduction
//In the class, we covered how to integrate payment gateways into an application using Razorpay and Stripe. Razorpay is commonly used in India, whereas Stripe is more prevalent in the US market. The focus was on understanding the flow from order creation to handling callback URLs and setting up webhooks to ensure reliable confirmation of payment transactions.
//
//Key Concepts and Steps
//Overview of Payment Flow
//Order Creation: Initiates the payment process.
//Payment Gateway Invocation: Call a payment gateway with a high success rate to create a payment link.
//Payment Link Generation: The link is returned to the frontend.
//Handling Callbacks: Based on success or failure, update payment and order status.
//Razorpay Integration Recap
//During the last session, Razorpay was integrated where the focus was on creating and handling payment links via their API.
//Stripe Integration
//Similar steps as Razorpay, focusing on leveraging Stripe's create payment link API.
//Ensure to navigate through Stripe's documentation and find relevant endpoints.
//NGROK for Local Development
//NGROK: Tool to expose localhost to the internet, useful for testing webhooks without deploying to a cloud environment.
//Helps create a public endpoint for localhost services, allowing services like Stripe to send events to the local machine.
//Configuring Payment Completion
//Use after completion parameters to define behaviors post-purchase such as redirecting users or confirming on a hosted page.
//Webhooks: Reliability in Payment Confirmation
//Purpose: Webhooks listen for events from the payment gateway and call specified endpoints.
//        Setup: Register an endpoint on the Stripe dashboard. This endpoint will be called on specific events like payment completion.
//Usage: Provides an extra layer of confirmation beyond immediate response handling.
//Handling Callbacks and Redirects
//Implement callback URLs to handle immediate post-payment user journeys.
//Redirect users to specified URLs upon payment success or failure.
//Practical Exercises and Demonstrations
//Creating Payment Links: Tested Stripe's API to generate payment links.
//Webhook Event Handling: Monitored webhook call logs and tested event handling using Stripe's dashboard.
//Use of Test Cards: Stripe provides test card numbers for simulating payment processes without using real credit card data.
//Summary
//APIs rarely guarantee 100% success. Webhooks serve as a reliable fallback mechanism to ensure payments are confirmed even if the callback during a payment session fails.
//Both Razorpay and Stripe provide robust solutions for payment processing, each suited to different geographical requirements.