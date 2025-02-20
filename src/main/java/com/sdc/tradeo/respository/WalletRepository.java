package com.sdc.tradeo.respository;

import com.sdc.tradeo.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByUserId(Long userId);

}
