package com.example.PaymentGatewayService.Services;

public interface IPaymentService {

    public String initiatePayment(Long amount,String orderId,String phoneNo,String customerName);


}
