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
@Table(name = "UPI_DETAILS")
public class UPIDetails {
    @Id
    @Column(name = "UPI_ID")
    private String upiId;
    @Column(name = "UPI_PIN",nullable = false)
    private Integer upiPin;
    @OneToOne
    @JoinColumn(name = "ACCOUNT_NO")
    private AccountDetails accountNo;
}
