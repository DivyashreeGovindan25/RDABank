package com.RDABank.RDABank.Repository;

import com.RDABank.RDABank.Models.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails,Long> {
}
