package com.sdc.tradeo.service;

import com.razorpay.RazorpayException;
import com.sdc.tradeo.domain.PaymentMethod;
import com.sdc.tradeo.model.PaymentOrder;
import com.sdc.tradeo.model.User;
import com.sdc.tradeo.response.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean ProceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;

    //those-who-india-use-razorPaymentGateway
    PaymentResponse createRazorpayPaymentLink(User user, Long amount,Long orderId) throws RazorpayException;

    //those-who-non-india-use-stripePaymentGateway
    PaymentResponse createStripePaymentLink(User user, Long amount,Long orderId) throws StripeException;


}
