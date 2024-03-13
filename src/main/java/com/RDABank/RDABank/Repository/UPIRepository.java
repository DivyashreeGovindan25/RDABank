package com.RDABank.RDABank.Repository;

import com.RDABank.RDABank.Models.AccountDetails;
import com.RDABank.RDABank.Models.PersonalDetails;
import com.RDABank.RDABank.Models.UPIDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UPIRepository extends JpaRepository<UPIDetails,String> {
    @Query(value = "SELECT u FROM UPIDetails u WHERE u.upiId = :email")
    public UPIDetails getUPIWithTheProvidedUPIId(@Param("email") String email);
    @Query(value = "SELECT u FROM UPIDetails u WHERE u.accountNo = :accountNo")
    public UPIDetails getUPIByAccountNo(@Param("accountNo") AccountDetails accountNo);
    @Modifying
    @Transactional
    @Query(value = "UPDATE UPIDetails u SET u.upiPin = :upiPin WHERE u.upiId = :upiId")
    public void updateUPIPin(@Param("upiId") String upiId,@Param("upiPin") Integer upiPin);
}
