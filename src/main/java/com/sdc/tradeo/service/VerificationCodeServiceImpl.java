package com.sdc.tradeo.service;

import com.sdc.tradeo.domain.VerificationType;
import com.sdc.tradeo.model.User;
import com.sdc.tradeo.model.VerificationCode;
import com.sdc.tradeo.respository.VerificationCodeRepository;
import com.sdc.tradeo.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode verificationCode1 = new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateOTP());
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);
        return verificationCodeRepository.save(verificationCode1);

    }

    @Override
    public VerificationCode getVerificationCodebyId(Long id) throws Exception {
        Optional<VerificationCode> verificationCode = Optional.ofNullable(verificationCodeRepository.findByUserId(id));
        if (verificationCode.isPresent()) {
            return verificationCode.get();
        }
        throw new Exception("Verification code not found");

    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {
        return verificationCodeRepository.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);

    }
}
