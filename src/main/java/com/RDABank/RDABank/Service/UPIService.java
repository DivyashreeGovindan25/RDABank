package com.RDABank.RDABank.Service;

import com.RDABank.RDABank.DTO.ForgotUPIPinDTO;
import com.RDABank.RDABank.DTO.UPIRegisterDTO;
import com.RDABank.RDABank.Exception.*;
import com.RDABank.RDABank.Models.AccountDetails;
import com.RDABank.RDABank.Models.PersonalDetails;
import com.RDABank.RDABank.Models.UPIDetails;
import com.RDABank.RDABank.Repository.AccountDetailsRepository;
import com.RDABank.RDABank.Repository.PersonalDetailsRepository;
import com.RDABank.RDABank.Repository.UPIRepository;
import com.RDABank.RDABank.Utils.UPIUtils.UPIRegiterValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.RDABank.RDABank.Utils.RDALogger.upiLogger;
import static com.RDABank.RDABank.Utils.UPIUtils.UPIIDType.SAMEASBANKEMAIL;

@Service
public class UPIService {
    @Autowired
    UPIRepository upiRepo;
    @Autowired
    AccountDetailsRepository accountDetailsRepository;
    @Autowired
    PersonalDetailsRepository personalDetailsRepository;
    public String registerUPI(UPIRegisterDTO upiRegisterDTO) throws AccountDoesnotExistException, InvalidAccountNumberException,
            InvalidEmailException, InvalidUPIPinException, RegisteredForUPIOrNotException {
        //Write Logic to check if user has account in bank
        //Fetching Account details
        AccountDetails accountDetails = accountDetailsRepository.findById(upiRegisterDTO.getAccountNo()).orElse(null);
        //Account number/Account validation
        UPIRegiterValidations.UPIRegisterAccountNumberValidation(upiRegisterDTO,upiRepo.getUPIByAccountNo(accountDetails),accountDetails);
        upiLogger.info("Account/Account number validation passed");
        PersonalDetails personalDetails = personalDetailsRepository.getPersonalDetailsByAccountNo(accountDetails);
        //Valid UPI ID check
        if(upiRegisterDTO.getUpiIdType().equals(SAMEASBANKEMAIL)){
            upiRegisterDTO.setUpiId(personalDetails.getEmail());
        }
        String UPI_ID = UPIRegiterValidations.generateUPIId(upiRegisterDTO.getUpiId());
        UPIRegiterValidations.UPIRegisterUPIIdValidation(upiRegisterDTO,upiRepo.getUPIWithTheProvidedUPIId(UPI_ID));
        upiRegisterDTO.setUpiId(UPI_ID);
        upiLogger.info("UPI Id validation passed");
        //UPI PIN validation
        UPIRegiterValidations.UPIRegisterUPIPinValidation(upiRegisterDTO);
        upiLogger.info("UPI Pin validation passed");
        UPIDetails upiDetails = new UPIDetails();
        upiDetails.setAccountNo(accountDetailsRepository.findById(upiRegisterDTO.getAccountNo()).orElse(null));
        upiDetails.setUpiId(upiRegisterDTO.getUpiId());
        upiDetails.setUpiPin(upiRegisterDTO.getUpiPin());
        upiRepo.save(upiDetails);
        upiLogger.info("Details saved into database");
        return String.format("UPI register successfull for account number %d ",upiDetails.getAccountNo().getAccountNo());
    }
    public String getUPIDetails(Long accountNumber) throws RegisteredForUPIOrNotException,AccountDoesnotExistException,InvalidAccountNumberException{
        UPIRegiterValidations.accountLenValidation(accountNumber);
        upiLogger.info("Passed account length validation");
        AccountDetails accountDetails = accountDetailsRepository.findById(accountNumber).orElse(null);
        boolean accountExist = UPIRegiterValidations.UPIaccountExistOrNot(accountDetails);
        if(!accountExist) throw new AccountDoesnotExistException(String.format("No account exist with account number %d ",accountNumber));
        upiLogger.info("Passed account exist or not validation");
        UPIDetails upiDetails = upiRepo.getUPIByAccountNo(accountDetails);
        boolean upiId = UPIRegiterValidations.UPIaccountExistOrNot(upiDetails);
        if(!upiId) throw new RegisteredForUPIOrNotException("Account not registered for UPI");
        upiLogger.info("Passed Upi account exist or not validation");
        return upiDetails.getUpiId();
    }
    public String forgotUPIPin(ForgotUPIPinDTO forgotUPIPinDTO) throws InvalidEmailException,AccountDoesnotExistException,
            InvalidUPIPinException,InvalidAccountNumberException{
        //Account Number validation
        UPIRegiterValidations.accountLenValidation(forgotUPIPinDTO.getAccountNo());
        upiLogger.info("Passed account length validation");
        //Provided UPI Id exist or not
        boolean doesExist = UPIRegiterValidations.UPIaccountExistOrNot(upiRepo.getUPIWithTheProvidedUPIId(forgotUPIPinDTO.getUpiId()));
        if(!doesExist) throw new AccountDoesnotExistException(String.format("The provided UPI Id is invalid"));
        upiLogger.info("Passed UPI Id registered or not validation");
        //Upi Pin Validaiton
        UPIRegiterValidations.UPIPinLenValidation(forgotUPIPinDTO.getUpiPin());
        UPIRegiterValidations.UPIPinCompareValidation(forgotUPIPinDTO.getUpiPin(),forgotUPIPinDTO.getConfirmUPIPin());
        upiLogger.info("Passed UPI Pin validation");
        //Is same UPI Id registered for this account
        UPIDetails userUpiDetails = upiRepo.getUPIWithTheProvidedUPIId(forgotUPIPinDTO.getUpiId());
        if(!Objects.equals(forgotUPIPinDTO.getAccountNo(),userUpiDetails.getAccountNo().getAccountNo())){
            throw new AccountDoesnotExistException(String.format("The provided UPI ID is not registered for the given account number"));
        }
        upiLogger.info("Passed UPI Id registered for the given account number validation");
        //Verifying card number
        //.....
        upiRepo.updateUPIPin(forgotUPIPinDTO.getUpiId(),forgotUPIPinDTO.getUpiPin());
        return String.format("Upi Pin reset is successfully");
    }
}
