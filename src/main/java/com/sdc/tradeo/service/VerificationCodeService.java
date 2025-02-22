package com.sdc.tradeo.service;

import com.sdc.tradeo.domain.VerificationType;
import com.sdc.tradeo.model.User;
import com.sdc.tradeo.model.VerificationCode;
import org.springframework.stereotype.Service;


@Service
public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodebyId(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long UserId);


    void deleteVerificationCodeById(VerificationCode verificationCode);


}
