package com.sdc.tradeo.service;

import com.sdc.tradeo.model.TwoFactorOTP;
import com.sdc.tradeo.model.User;

public interface TwoFactorOtpService {
    TwoFactorOTP createTwoFactoreOtp(User user, String otp, String jwt);

    TwoFactorOTP findByUser(Long userId);

    TwoFactorOTP findById(String id);

    boolean verifyTwoFactorOtp(TwoFactorOTP twofactorotp, String otp);

    void deleteTwoFactoryOtp(TwoFactorOTP twoFactorOTP);
}
