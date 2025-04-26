package com.sdc.tradeo.model;

import com.sdc.tradeo.domain.WalletTransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_transaction_seq")
    @SequenceGenerator(name = "wallet_transaction_seq", sequenceName = "wallet_transaction_seq", allocationSize = 1, initialValue = 1000)
    private Long id;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    private BigDecimal balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    @Enumerated(EnumType.STRING)
    private WalletTransactionType transactionType;

    private Long transferId;
    private String purpose;
    private Long amount;
    private Long timestamp;
}
