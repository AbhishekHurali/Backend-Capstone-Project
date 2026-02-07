package com.example.PaymentGatewayService.Services;

import com.example.PaymentGatewayService.PaymentGateways.IPaymentGateway;
import com.example.PaymentGatewayService.PaymentGateways.PaymentGatewayChooserStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService{

    @Autowired
    private PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;

    @Override
    public String initiatePayment(Long amount, String orderId, String phoneNo, String customerName) {
        IPaymentGateway paymentGateway = paymentGatewayChooserStrategy.getBestPaymentGateway();
        return  paymentGateway.getPaymentLink(amount,orderId,phoneNo,customerName);
    }
}
