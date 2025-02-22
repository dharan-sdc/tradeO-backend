package com.sdc.tradeo.service;

import com.sdc.tradeo.domain.WalletTransactionType;
import com.sdc.tradeo.model.Wallet;
import com.sdc.tradeo.model.WalletTransaction;
import com.sdc.tradeo.respository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public WalletTransaction createTransaction(Wallet wallet,
                                               WalletTransactionType transactionType,
                                               Long transferId,
                                               String purpose,
                                               Long amount) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setTransactionType(transactionType);
        transaction.setTransferId(transferId);
        transaction.setPurpose(purpose);
        transaction.setAmount(amount);
        transaction.setTimestamp(System.currentTimeMillis());

        return transactionRepository.save(transaction);
    }

    @Override
    public List<WalletTransaction> getTransactionsByWallet(Wallet wallet) {
        return transactionRepository.findByWallet(wallet);
    }
}
