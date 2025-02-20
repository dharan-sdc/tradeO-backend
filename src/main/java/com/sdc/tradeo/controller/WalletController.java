package com.sdc.tradeo.controller;

import com.sdc.tradeo.Service.OrderService;
import com.sdc.tradeo.Service.UserService;
import com.sdc.tradeo.Service.WalletService;
import com.sdc.tradeo.Service.WalletServiceImpl;
import com.sdc.tradeo.model.Order;
import com.sdc.tradeo.model.User;
import com.sdc.tradeo.model.Wallet;
import com.sdc.tradeo.model.WalletTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/wallet/${walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTranfer(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long walletId,
            @RequestBody WalletTransaction req) throws Exception{
        User senderUser = userService.findUserProfileByJwt(jwt);
        Wallet receiverWallet = walletService.findWalletById(walletId);
        Wallet wallet=walletService.walletToWalletTransfer(
                senderUser,receiverWallet,req.getAmount());
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/wallet/order/${orderId}/transfer")
    public ResponseEntity<Wallet> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Order order = OrderService.getOrderById(orderId);
        Wallet wallet = walletService.payOrderPayment(order, user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
}
