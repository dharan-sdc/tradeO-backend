package com.sdc.tradeo.controller;

import com.sdc.tradeo.domain.WalletTransactionType;
import com.sdc.tradeo.model.WalletTransaction;
import com.sdc.tradeo.service.TransactionService;
import com.sdc.tradeo.service.UserService;
import com.sdc.tradeo.service.WalletService;
import com.sdc.tradeo.service.WithdrawalService;
import com.sdc.tradeo.model.User;
import com.sdc.tradeo.model.Wallet;
import com.sdc.tradeo.model.Withdrawal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;
    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<?> withdrawalRequest(
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);

        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
        walletService.addBalance(userWallet, -withdrawal.getAmount());

        WalletTransaction walletTransaction=transactionService.createTransaction(
                userWallet,
                WalletTransactionType.WITHDRAWAL,null,
                "bank account withdrawal",
                withdrawal.getAmount(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }



    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(
            @PathVariable Long id,
            @PathVariable boolean accept,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Withdrawal withdrawal = withdrawalService.procedWithwithdrawal(id, accept);

        Wallet userWallet = walletService.getUserWallet(user);
        if (!accept) {
            walletService.addBalance(userWallet, withdrawal.getAmount());
        }
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/api/withdrawal")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        //getUserWithdrawalHistory
        List<Withdrawal> withdrawal = withdrawalService.getUserWithdrawalHistory(user);
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(
            @RequestHeader("Authorization") String jwt
    ) {
        List<Withdrawal> withdrawal = withdrawalService.getAllWithdrawalRequest();

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }
}
