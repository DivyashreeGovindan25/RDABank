package com.RDABank.RDABank.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "CARD_DETAILS")
public class Card {
    @Id
    @Column(name = "CARD_NO",length = 11,nullable = false)
    private Long cardNo;
    @Column(name = "NAME_ON_THE_CARD",nullable = false)
    private String nameOnTheCard;
    @Column(name = "CVV",length = 2,nullable = false)
    private Integer cvv;
    @Column(name = "EXPIRY",nullable = false)
    private Date expiryDate;
    @OneToOne
    @JoinColumn(name = "ACCOUNT_NO")
    private AccountDetails accountNo;
}
