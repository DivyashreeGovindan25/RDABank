package com.RDABank.RDABank.Repository;

import com.RDABank.RDABank.Models.AccountDetails;
import com.RDABank.RDABank.Models.PersonalDetails;
import com.RDABank.RDABank.Models.UPIDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails, Long> {
    @Query(value = "SELECT p FROM PersonalDetails p WHERE p.accountNo = :accountNo")
    public PersonalDetails getPersonalDetailsByAccountNo(@Param("accountNo") AccountDetails accountNo);
}
