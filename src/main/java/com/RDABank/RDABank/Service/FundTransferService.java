package com.RDABank.RDABank.Service;

import com.RDABank.RDABank.DTO.UpiFundTransferDTO;
import com.RDABank.RDABank.Exception.*;
import com.RDABank.RDABank.Models.AccountDetails;
import com.RDABank.RDABank.Models.BankBalance;
import com.RDABank.RDABank.Models.UPIDetails;
import com.RDABank.RDABank.Repository.AccountDetailsRepository;
import com.RDABank.RDABank.Repository.BankBalanceRepository;
import com.RDABank.RDABank.Repository.UPIRepository;
import com.RDABank.RDABank.Utils.UPIUtils.UPIValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.RDABank.RDABank.Utils.RDALogger.fundTransferLoggerUpi;

@Service
public class FundTransferService {
    @Autowired
    AccountDetailsRepository accountDetailsRepository;
    @Autowired
    UPIRepository upiRepository;
    @Autowired
    BankBalanceRepository bankBalanceRepository;
    public String upiTransfer(UpiFundTransferDTO upiFundTransferDTO) throws InvalidAccountNumberException, InvalidEmailException ,
            RegisteredForUPIOrNotException, InvalidUPIPinException, LowBalanceException, AccountDoesnotExistException {
        AccountDetails accountDetails;
        UPIDetails upiDetails;
        UPIValidations.accountLenValidation(upiFundTransferDTO.getAccountNo());
        fundTransferLoggerUpi.info("Passed Account length validation");
        upiDetails = upiRepository.findById(upiFundTransferDTO.getUpiId()).orElse(null);
        if(!UPIValidations.UPIaccountExistOrNot(upiDetails))
            throw new RegisteredForUPIOrNotException(String.format("The provided Upi Id %s isn't registered, Recheck",upiFundTransferDTO.getUpiId()));
        fundTransferLoggerUpi.info("Passed valid UPI validation");
        accountDetails = accountDetailsRepository.findById(upiFundTransferDTO.getAccountNo()).orElse(null);
        if(!UPIValidations.UPIaccountExistOrNot(accountDetails))
            throw new AccountDoesnotExistException(String.format("The provided account number %d is invalid",upiFundTransferDTO.getAccountNo()));
        fundTransferLoggerUpi.info("Passed valid Account number validation");
        if(!Objects.equals(accountDetails.getAccountNo(), upiDetails.getAccountNo().getAccountNo()))
            throw new InvalidAccountNumberException(String.format("The UPI Id - %s is not associated with the given account number %d",upiFundTransferDTO.getUpiId(),upiFundTransferDTO.getAccountNo()));
        fundTransferLoggerUpi.info("Passed Account number linked with UPI Id validation");
        UPIValidations.UPIPinLenValidation(upiFundTransferDTO.getUpiPin());
        fundTransferLoggerUpi.info("Passed UPI pin length validation");
        UPIValidations.UPIPinCompareValidation(upiFundTransferDTO.getUpiPin(),upiDetails.getUpiPin(),"Incorrect UPI Pin");
        fundTransferLoggerUpi.info("Passed valid UPI Pin verification validation");
        BankBalance bankBalance = bankBalanceRepository.getCurrentBankBalanceByAccountNo(accountDetails);
        if(accountDetails.getAccountType().equals("SALARY")) {
            if (upiFundTransferDTO.getTransferAmount() > bankBalance.getBalance()) {
                fundTransferLoggerUpi.info("Encountered Insufficient Balance exception - salary");
                throw new LowBalanceException(String.format("Unable to transfer fund as the bank balance is insufficient, Balance available is %.2f ", bankBalance.getBalance()));
            }
        }
        else {
            if (upiFundTransferDTO.getTransferAmount() > (bankBalance.getBalance() - 1000.0)) {
                fundTransferLoggerUpi.info("Encountered Insufficient Balance exception other account");
                throw new LowBalanceException(String.format("Unable to transfer fund as the minimum balance is 1000 and your bank balance is insufficient, Balance available is %.2f ", bankBalance.getBalance()));
            }
        }
        BankBalance bankBalance1 = new BankBalance();
        bankBalance1.setId(new BankBalance.BankBalanceId(accountDetails, LocalDateTime.now()));
        bankBalance1.setBalance(bankBalance.getBalance()-upiFundTransferDTO.getTransferAmount());
        bankBalance1.setTransactionType("UPI");
        UUID uuid = UUID.randomUUID();
        bankBalance1.setTransactionId(uuid);
        bankBalanceRepository.save(bankBalance1);
        fundTransferLoggerUpi.info("New transaction initiated");
        return String.format("Amount transferred successfully to %s and balance - %.2f",upiFundTransferDTO.getToUpiId(),bankBalance1.getBalance());
    }
}
