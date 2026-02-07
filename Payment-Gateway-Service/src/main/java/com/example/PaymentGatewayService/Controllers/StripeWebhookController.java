package com.example.PaymentGatewayService.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stripeWebhook")
public class StripeWebhookController {

    @PostMapping
    public void respondToEvents(@RequestBody String event){//Webhook takes the parameter in this manner, already decided by Stripe.
        //Here event is the complex object.
        //We have used public URL (ngrok). We have registered 4 events in the stripe
        // Once of them is Payment_link.created.
        //Hence, when ever the Payment_link.created stripe will trigger the endpoint configured on the stripe webhook.

        //output of the event
//        {
//            "id": "evt_1SvDL8BAUFQtRZBlbW3HrB4k",
//                "object": "event",
//                "api_version": "2026-01-28.clover",
//                "created": 1769762766,
//                "data": {
//            "object": {
//                "id": "plink_1SvDL8BAUFQtRZBl79luFbRC",
//                        "object": "payment_link",
//                        "active": true,
//                        "after_completion": {
//                    "redirect": {
//                        "url": "http://scaler.com"
//                    },
//                    "type": "redirect"
//                },
//                "allow_promotion_codes": false,
//                        "application": null,
//                        "application_fee_amount": null,
//                        "application_fee_percent": null,
//                        "automatic_tax": {
//                    "enabled": false,
//                            "liability": null
//                },
//                "billing_address_collection": "auto",
//                        "consent_collection": null,
//                        "currency": "usd",
//                        "custom_fields": [],
//                "custom_text": {
//                    "after_submit": null,
//                            "shipping_address": null,
//                            "submit": null,
//                            "terms_of_service_acceptance": null
//                },
//                "customer_creation": "if_required",
//                        "inactive_message": null,
//                        "invoice_creation": {
//                    "enabled": false,
//                            "invoice_data": {
//                        "account_tax_ids": null,
//                                "custom_fields": null,
//                                "description": null,
//                                "footer": null,
//                                "issuer": null,
//                                "metadata": {},
//                        "rendering_options": null
//                    }
//                },
//                "livemode": false,
//                        "metadata": {},
//                "on_behalf_of": null,
//                        "payment_intent_data": null,
//                        "payment_method_collection": "always",
//                        "payment_method_types": null,
//                        "phone_number_collection": {
//                    "enabled": false
//                },
//                "restrictions": null,
//                        "shipping_address_collection": null,
//                        "shipping_options": [],
//                "submit_type": "auto",
//                        "subscription_data": {
//                    "description": null,
//                            "invoice_settings": {
//                        "issuer": {
//                            "type": "self"
//                        }
//                    },
//                    "metadata": {},
//                    "trial_period_days": null,
//                            "trial_settings": {
//                        "end_behavior": {
//                            "missing_payment_method": "create_invoice"
//                        }
//                    }
//                },
//                "tax_id_collection": {
//                    "enabled": false,
//                            "required": "never"
//                },
//                "transfer_data": null,
//                        "url": "https://buy.stripe.com/test_dRm4gy51ccSc5Pd2bDfnO02"
//            }
//        },
//            "livemode": false,
//                "pending_webhooks": 1,
//                "request": {
//            "id": "req_lZyRBOXFLX4shb",
//                    "idempotency_key": "6b18e573-9128-4c03-9061-79cf416d5fe9"
//        },
//            "type": "payment_link.created"
//        }


        System.out.println(event);

    }
}
