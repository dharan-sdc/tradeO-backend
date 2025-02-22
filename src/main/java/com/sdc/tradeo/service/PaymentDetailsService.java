package com.sdc.tradeo.service;

import com.sdc.tradeo.model.PaymentDetails;
import com.sdc.tradeo.model.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolderName,
                                            String ifsc,
                                            String bankName,
                                            User user);

    public PaymentDetails getUsersPaymentDetails(User user);
}
