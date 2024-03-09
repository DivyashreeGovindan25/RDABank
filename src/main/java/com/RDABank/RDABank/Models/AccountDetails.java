package com.RDABank.RDABank.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ACCOUNT_DETAILS")
public class AccountDetails {
    @Id
    @Column(name = "ACCOUNT_NO", length = 11)
    private Long accountNo;
    @Column(name = "CIF_NO", length = 11,nullable = false)
    private Long cifNo;
    @Column(name = "BRANCH_NAME", nullable = false)
    private String branchName;
    @Column(name = "MPIN", length = 4,nullable = false)
    private Integer mpin;
    @Column(name = "LASTUPDDTTM", nullable = false)
    private LocalDateTime lastUpddtm;
    @Column(name = "ACCOUNT_TYPE",nullable = false)
    private String accountType;
    @OneToOne(mappedBy = "accountNo",cascade = CascadeType.ALL)
    private Card card;
    @OneToOne(mappedBy = "accountNo",cascade = CascadeType.ALL)
    private UPIDetails upiDetails;
    @OneToOne(mappedBy = "accountNo",cascade = CascadeType.ALL)
    private PersonalDetails personalDetails;
    @OneToMany(mappedBy = "id.accountNo",cascade = CascadeType.ALL)
    private List<BankBalance> bankBalanceList;
//    @OneToMany(mappedBy = "ACCOUNT_DETAILS")
//    private NameDetails nameDetails;
//    @OneToMany(mappedBy = "ACCOUNT_DETAILS")
//    private Address address;
}
