package com.example.PaymentGatewayService.PaymentGateways;

public interface IPaymentGateway {

    public String getPaymentLink(Long amount,String orderId,String phoneNo,String customerName);
}
