package com.sdc.tradeo.respository;


import com.sdc.tradeo.model.Wallet;
import com.sdc.tradeo.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<WalletTransaction, Long> {
    List<WalletTransaction> findByWallet(Wallet wallet);
}
