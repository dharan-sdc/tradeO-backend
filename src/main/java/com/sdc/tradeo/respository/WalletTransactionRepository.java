package com.sdc.tradeo.respository;


import com.sdc.tradeo.model.Wallet;
import com.sdc.tradeo.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
    List<WalletTransaction> findByWallet(Wallet wallet);
}


