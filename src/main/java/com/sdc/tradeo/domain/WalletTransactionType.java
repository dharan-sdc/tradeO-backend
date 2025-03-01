package com.sdc.tradeo.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

//
//public enum WalletTransactionType {
//    WITHDRAWAL,
//    WALLET_TRANSFER,
//    ADD_MONEY,
//    BUY_ASSET,
//    SELL_ASSET,
//    DEPOSIT,
//    TRANSFER
//}
//

 // Ensures enum is stored as String
public enum WalletTransactionType {
     @Enumerated(EnumType.STRING)
     DEPOSIT,
    WITHDRAWAL, // Ensure this exists
    TRANSFER,
    RECEIVE
}
