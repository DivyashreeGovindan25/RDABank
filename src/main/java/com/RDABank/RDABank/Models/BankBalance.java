package com.RDABank.RDABank.Models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "BANK_BALANCE")
public class BankBalance {
    @Embeddable
    public static class BankBalanceId implements Serializable {
        @ManyToOne
        @JoinColumn(name = "ACCOUNT_NO")
        private AccountDetails accountNo;

        @Column(name = "TRANSACTION_DATETIMESTAMP")
        private LocalDateTime transactionDateTime;

        // Constructors, getters, and setters
        // Make sure to override equals() and hashCode() methods
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BankBalanceId that = (BankBalanceId) o;
            return Objects.equals(accountNo, that.accountNo) &&
                    Objects.equals(transactionDateTime, that.transactionDateTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(accountNo, transactionDateTime);
        }

        public BankBalanceId(AccountDetails accountNo, LocalDateTime transactionDateTime) {
            this.accountNo = accountNo;
            this.transactionDateTime = transactionDateTime;
        }

        public BankBalanceId() {
        }

        public AccountDetails getAccountNo() {
            return accountNo;
        }

        public LocalDateTime getTransactionDateTime() {
            return transactionDateTime;
        }

        public void setAccountNo(AccountDetails accountNo) {
            this.accountNo = accountNo;
        }

        public void setTransactionDateTime(LocalDateTime transactionDateTime) {
            this.transactionDateTime = transactionDateTime;
        }
    }
    @EmbeddedId
    private BankBalanceId id;
    @Column(name = "BALANCE",nullable = false)
    private Double balance;
    @Column(name = "TRANSACTION_TYPE",nullable = false)
    private String transactionType;
    @Column(name = "TRANSACTION_ID",nullable = false)
    private UUID transactionId;
}
