package com.example.PaymentGatewayService.PaymentGateways;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentGateway implements IPaymentGateway{

    @Value("${stripe.apikey}")
    private String stripeApiKey;

    @Override
    public String getPaymentLink(Long amount, String orderId, String phoneNo, String customerName) {

        try{
            // Set your secret key. Remember to switch to your live secret key in production.
// See your keys here: https://dashboard.stripe.com/apikeys
            Stripe.apiKey = this.stripeApiKey;
            Price price = getPrice(amount);
            PaymentLinkCreateParams params =
                    PaymentLinkCreateParams.builder()
                            .addLineItem(
                                    PaymentLinkCreateParams.LineItem.builder()
                                            .setPrice(price.getId())
                                            .setQuantity(1L)
                                            .build()
                            ).setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
                                    .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                    .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder().setUrl("http://scaler.com")
                                            .build())
                                    .build())
                            //First we are specifying the type of After completion
                            // Then we are creating Redirect object and specifying the URL where we want to go after completion.
                            .build();

            PaymentLink paymentLink = PaymentLink.create(params);
            return paymentLink.getUrl();
        }catch (StripeException exception){
            throw new RuntimeException(exception.getMessage());
        }

    }

private Price getPrice(Long amount) {
try {
    PriceCreateParams params =
            PriceCreateParams.builder()
                    .setCurrency("usd")
                    .setUnitAmount(amount)
                    .setRecurring(
                            PriceCreateParams.Recurring.builder()
                                    .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                    .build()
                    )
                    .setProductData(
                            PriceCreateParams.ProductData.builder().setName("Gold Plan").build()
                    )
                    .build();
    Price price = Price.create(params);
    return price;

    }catch (StripeException exception){
    throw  new RuntimeException(exception.getMessage());
    }
  }

}
//After the purchase is complete
//after_completion.type
//enum
//The specified behavior after the purchase is complete.
//Possible enum values
//hosted_confirmation : Displays a message on the hosted surface after the purchase is complete.
//redirect : Redirects the customer to the specified url after the purchase is complete.