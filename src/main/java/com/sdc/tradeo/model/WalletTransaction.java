package com.sdc.tradeo.model;

import com.sdc.tradeo.domain.WalletTransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@Entity
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = true) // Allow NULL values
    private Wallet wallet;


    @Enumerated(EnumType.STRING) // Ensure proper storage for enum
    private WalletTransactionType transactionType;

    private LocalDateTime transactionDate; // Changed from LocalDate to LocalDateTime
    private Long transferId;
    private String purpose;
    private Long amount;
    private Timestamp timestamp; // Ensure this is correctly mapped

    // Getters and Setters
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timeMillis) {
        this.timestamp = new Timestamp(timeMillis); // Convert long to Timestamp
    }

    public WalletTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(WalletTransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
