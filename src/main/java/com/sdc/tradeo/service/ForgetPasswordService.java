package com.sdc.tradeo.service;

import com.sdc.tradeo.domain.VerificationType;
import com.sdc.tradeo.model.ForgetPasswordToken;
import com.sdc.tradeo.model.User;

public interface ForgetPasswordService {

    ForgetPasswordToken createToken(User user,
                                    String id, String otp, VerificationType verificationType,
                                    String sendTo);

    ForgetPasswordToken findById(String id);

    ForgetPasswordToken findByUser(Long userId);

    void deleteToken(ForgetPasswordToken token);

}
