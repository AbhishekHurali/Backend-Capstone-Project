package com.example.PaymentGatewayService.Controllers;

import com.example.PaymentGatewayService.Dtos.InitiatePaymentDto;
import com.example.PaymentGatewayService.Services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @GetMapping("/print/{id}")
    public String print(@PathVariable Long id){
        System.out.println(id);
        return "Yes, you are working great!";
    }

    @PostMapping("/initiate")
    public String initiatePayment(@RequestBody InitiatePaymentDto initiatePaymentDto){

        return paymentService.initiatePayment(initiatePaymentDto.getAmount(),
                initiatePaymentDto.getOrderId(),
                initiatePaymentDto.getPhoneNo(),
                initiatePaymentDto.getCustomerName());

    }
}
