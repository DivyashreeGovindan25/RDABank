package com.RDABank.RDABank.Repository;

import com.RDABank.RDABank.Models.AccountDetails;
import com.RDABank.RDABank.Models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardDetailsRepository extends JpaRepository<Card,Long> {
    @Query(value = "SELECT c FROM Card c WHERE c.accountNo =:accountNo")
    public Card findCardBasedOnAccountNo(@Param("accountNo") AccountDetails accountNo);
}
