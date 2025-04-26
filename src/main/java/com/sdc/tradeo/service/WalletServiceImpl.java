package com.sdc.tradeo.service;

import com.sdc.tradeo.domain.OrderType;
import com.sdc.tradeo.domain.WalletTransactionType;
import com.sdc.tradeo.model.Order;
import com.sdc.tradeo.model.User;
import com.sdc.tradeo.model.Wallet;
import com.sdc.tradeo.model.WalletTransaction;
import com.sdc.tradeo.respository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepository.findByUserId(user.getId());
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setUser(user);
            wallet.setBalance(BigDecimal.ZERO);
            wallet = walletRepository.save(wallet);

        }
        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long money) {
        if (wallet.getBalance() == null) {
            wallet.setBalance(BigDecimal.ZERO);
        }
        BigDecimal newBalance = wallet.getBalance().add(BigDecimal.valueOf(money));
        wallet.setBalance(newBalance);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) throws Exception {
        return walletRepository.findById(id)
                .orElseThrow(() -> new Exception("Wallet not Found"));
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception {
        Wallet senderWallet = getUserWallet(sender);

        if (senderWallet == null) {
            throw new Exception("Sender wallet not found.");
        }

        if (senderWallet.getBalance() == null) {
            senderWallet.setBalance(BigDecimal.ZERO);
        }

        if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new Exception("Insufficient balance.");
        }

        // Deduct from sender
        senderWallet.setBalance(senderWallet.getBalance().subtract(BigDecimal.valueOf(amount)));

        // Add to receiver
        receiverWallet.setBalance(receiverWallet.getBalance().add(BigDecimal.valueOf(amount)));

        // Save updated wallets
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        // Record transaction for sender
        WalletTransaction senderTransaction = transactionService.createTransaction(
                senderWallet,
                WalletTransactionType.TRANSFER,
                receiverWallet.getId(), // Store receiver wallet ID as transferId
                "Wallet-2-Wallet Transfer \n Wallet ID: " + receiverWallet.getId(),
                amount,
                LocalDate.now().atStartOfDay()
        );

        // Record transaction for receiver
        WalletTransaction receiverTransaction = transactionService.createTransaction(
                receiverWallet,
                WalletTransactionType.RECEIVE,
                senderWallet.getId(), // Store sender wallet ID as transferId
                "Received transfer from Wallet ID: " + senderWallet.getId(),
                amount,
                LocalDateTime.now()
        );

        return senderWallet; // Return updated sender wallet
    }



    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet = getUserWallet(user);

        if (order.getOrderType().equals(OrderType.BUY)) {
            BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new Exception("Insufficient funds for this transaction");
            }
            wallet.setBalance(newBalance);
        } else {
            wallet.setBalance(wallet.getBalance().add(order.getPrice()));
        }
        return walletRepository.save(wallet);
    }

}
