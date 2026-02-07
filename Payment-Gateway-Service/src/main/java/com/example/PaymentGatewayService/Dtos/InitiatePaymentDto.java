package com.example.PaymentGatewayService.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePaymentDto {

    private Long amount;

    private String orderId;

    private String phoneNo;

    private String customerName;

}
