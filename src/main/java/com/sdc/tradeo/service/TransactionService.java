package com.sdc.tradeo.service;

import com.sdc.tradeo.domain.WalletTransactionType;
import com.sdc.tradeo.model.Wallet;
import com.sdc.tradeo.model.WalletTransaction;

import java.util.List;

public interface TransactionService {
    WalletTransaction createTransaction(
            Wallet wallet,
            WalletTransactionType transactionType,
            Long transferId,
            String purpose,
            Long amount
    );

    List<WalletTransaction> getTransactionsByWallet(Wallet wallet);
}
