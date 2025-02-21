package com.sdc.tradeo.model;

import com.sdc.tradeo.domain.PaymentMethod;
import com.sdc.tradeo.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@Entity
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;

    private PaymentOrderStatus status;

    private PaymentMethod paymentMethod;

    @ManyToOne
    private User user;
}
