package com.sdc.tradeo.respository;

import com.sdc.tradeo.model.VerificationCode;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {
    public VerificationCode findByUserId(Long userId);
}
