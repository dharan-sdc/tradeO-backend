package com.sdc.tradeo.request;

import com.sdc.tradeo.domain.VerificationType;
import lombok.Data;

@Data
public class ForgetPasswordTokenRequest {
    private String sendTo;
    private VerificationType verificationType;
}
