package com.sdc.tradeo.service;

import com.sdc.tradeo.domain.WalletTransactionType;
import com.sdc.tradeo.model.Wallet;
import com.sdc.tradeo.model.WalletTransaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    WalletTransaction createTransaction(
            Wallet wallet,
            WalletTransactionType transactionType,
            Long transferId,
            String purpose,
            Long amount,
            LocalDateTime transactionDate
    );

    List<WalletTransaction> getTransactionsByWallet(Wallet wallet);


}
