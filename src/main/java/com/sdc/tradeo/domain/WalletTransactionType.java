package com.sdc.tradeo.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

 // Ensures enum is stored as String
public enum WalletTransactionType {
     @Enumerated(EnumType.STRING)
     DEPOSIT,
    WITHDRAWAL, // Ensure this exists
    TRANSFER,
    RECEIVE
}
