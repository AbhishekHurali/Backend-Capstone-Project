package com.example.PaymentGatewayService.PaymentGateways;

import com.razorpay.PaymentLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.json.JSONObject;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Component
public class RazorpayPaymentGateway implements IPaymentGateway{


    @Autowired
    private RazorpayClient razorpayClient;


    public String getPaymentLink(Long amount,String orderId,String phoneNo,String customerName) {
        try {
            //RazorpayClient razorpay = new RazorpayClient("[YOUR_KEY_ID]", "[YOUR_KEY_SECRET]");
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("accept_partial", true);
            paymentLinkRequest.put("first_min_partial_amount", 100);
            paymentLinkRequest.put("expire_by", 2053617492);
            paymentLinkRequest.put("reference_id", orderId);
            paymentLinkRequest.put("description", "Payment for policy no #23456");
            JSONObject customer = new JSONObject();
            customer.put("name", phoneNo);
            customer.put("contact", customerName);
            customer.put("email", "gaurav.kumar@example.com");
            paymentLinkRequest.put("customer", customer);
            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);
            paymentLinkRequest.put("reminder_enable", true);
            JSONObject notes = new JSONObject();
            notes.put("policy_name", "Life Insurance Policy");
            paymentLinkRequest.put("notes", notes);
            paymentLinkRequest.put("callback_url", "https://example-callback-url.com/");
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
            return payment.get("short_url").toString(); // Taken from the document of Razorpay
            // because we are only interested payment Link.
        }catch (RazorpayException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }
}
//output
//Success JSON response
//{
//        "accept_partial": true,
//        "amount": 1000,
//        "amount_paid": 0,
//        "callback_method": "get",
//        "callback_url": "https://example-callback-url.com/",
//        "cancelled_at": 0,
//        "created_at": 1591097057,
//        "currency": "<currency>",
//        "customer": {
//        "contact": "<phone>",
//        "email": "<email>",
//        "name": "<name>"
//        },
//        "description": "Payment for policy no #23456",
//        "expire_by": 1691097057,
//        "expired_at": 0,
//        "first_min_partial_amount": 100,
//        "id": "plink_ExjpAUN3gVHrPJ",
//        "notes": {
//        "policy_name": "Jeevan Bima"
//        },
//        "notify": {
//        "email": true,
//        "sms": true
//        },
//        "payments": null,
//        "reference_id": "TS1989",
//        "reminder_enable": true,
//        "reminders": [],
//        "short_url": "https://rzp.io/i/nxrHnLJ",
//        "status": "created",
//        "updated_at": 1591097057,
//        "user_id": ""
//        }

//Failure JSON response
//{
//        "error": {
//        "code": "BAD_REQUEST_ERROR",
//        "description": "The api key provided is invalid",
//        "source": "NA",
//        "step": "NA",
//        "reason": "NA",
//        "metadata": {}
//        }
//        }