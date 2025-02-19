package com.sdc.tradeo.model;

import com.sdc.tradeo.domain.VerificationType;
import lombok.Data;


@Data

public class TwoFactorAuth {
    private boolean isEnabled=false;
    private VerificationType sendTo;

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }
//    // Getter method
//    public VerificationType getSendTo() {
//        return sendTo;
//    }
//
//    // Setter method
//    public void setSendTo(VerificationType sendTo) {
//        this.sendTo = sendTo;
//    }


}
