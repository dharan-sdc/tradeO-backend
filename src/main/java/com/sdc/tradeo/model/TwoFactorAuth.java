package com.sdc.tradeo.model;

import com.sdc.tradeo.domain.VertifcationType;
import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnabled=false;
    private VertifcationType sendTo;
}
