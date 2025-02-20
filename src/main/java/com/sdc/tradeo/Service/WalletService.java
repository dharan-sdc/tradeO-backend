package com.sdc.tradeo.Service;

import com.sdc.tradeo.model.Order;
import com.sdc.tradeo.model.User;
import com.sdc.tradeo.model.Wallet;
import jakarta.transaction.Transaction;

public interface WalletService {
    Wallet getUserWallet(User user);

    Wallet addBalance(Wallet wallet, Long money);

    Wallet findWalletById(Long id) throws Exception;

    Wallet walletToWalletTransfer(User sender,Wallet receiverWallet,Long amount) throws Exception;

    Wallet payOrderPayment(Order order, User user) throws Exception;



}
