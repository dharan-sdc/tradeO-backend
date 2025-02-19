package com.sdc.tradeo.respository;

import com.sdc.tradeo.model.ForgetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgetPasswordRepository extends JpaRepository<ForgetPasswordToken,String> {
    ForgetPasswordToken findByUserId(Long userId);
}
