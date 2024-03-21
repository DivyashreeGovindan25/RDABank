package com.RDABank.RDABank.Repository;

import com.RDABank.RDABank.Models.AccountDetails;
import com.RDABank.RDABank.Models.BankBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BankBalanceRepository extends JpaRepository<BankBalance, BankBalance.BankBalanceId> {
    @Query("SELECT b FROM BankBalance b WHERE b.id.accountNo = :accountNo " +
            "AND b.id.transactionDateTime = " +
            "(SELECT MAX(b2.id.transactionDateTime) FROM BankBalance b2 " +
            "WHERE b2.id.accountNo = :accountNo)")
    public BankBalance getCurrentBankBalanceByAccountNo(@Param(value = "accountNo") AccountDetails accountNo);

}
