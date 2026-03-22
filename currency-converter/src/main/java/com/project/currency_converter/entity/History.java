package com.project.currency_converter.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversion_history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "from_currency")
    private String fromCurrency;

    @Column(name = "to_currency")
    private String toCurrency;

    private BigDecimal amount;

    @Column(name = "converted_amount")
    private BigDecimal convertedAmount;

    @Column(name = "create_timestamp")
    private LocalDateTime createdTimestamp;

}
