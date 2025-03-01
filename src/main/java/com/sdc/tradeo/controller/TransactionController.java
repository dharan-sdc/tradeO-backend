package com.sdc.tradeo.controller;

import com.sdc.tradeo.model.User;
import com.sdc.tradeo.model.Wallet;
import com.sdc.tradeo.model.WalletTransaction;
import com.sdc.tradeo.service.TransactionService;
import com.sdc.tradeo.service.UserService;
import com.sdc.tradeo.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WalletService walletService;

    @GetMapping("/api/transactions")
    public ResponseEntity<List<WalletTransaction>> getUserWallet(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);

        List<WalletTransaction> transactionList = transactionService.getTransactionsByWallet(wallet);
        System.out.println("Transactions fetched: " + transactionList);

        return new ResponseEntity<>(transactionList, HttpStatus.ACCEPTED);
    }
}
