package com.RDABank.RDABank.Utils.UPIUtils;


import com.RDABank.RDABank.DTO.UPIRegisterDTO;
import com.RDABank.RDABank.Exception.*;
import com.RDABank.RDABank.Models.UPIDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.RDABank.RDABank.Utils.RDALogger.upiLogger;
import static com.RDABank.RDABank.Utils.UPIUtils.UPIIDType.NEWEMAIL;


@Component
public class UPIValidations {
    private static final String regex = "^[a-zA-Z0-9+-_&*]+(?:\\.[a-zA-Z0-9+-_&*]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern pattern = Pattern.compile(regex);

    public static void UPIRegisterAccountNumberValidation(UPIRegisterDTO upiRegisterDTO, UPIDetails upiDetails, Object accountDetails)
    throws AccountDoesnotExistException,RegisteredForUPIOrNotException{
        accountLenValidation(upiRegisterDTO.getAccountNo());
        if(accountDetails == null){
            throw new AccountDoesnotExistException(String.format("No account exist with account number %d ",upiRegisterDTO.getAccountNo()));
        }
        if(UPIaccountExistOrNot(upiDetails)){
            throw new RegisteredForUPIOrNotException(String.format("Account number %d already registered for UPI",upiRegisterDTO.getAccountNo()));
        }
    }
    public static void UPIRegisterUPIPinValidation(UPIRegisterDTO upiRegisterDTO) throws InvalidUPIPinException{
        UPIPinLenValidation(upiRegisterDTO.getUpiPin());
        UPIPinCompareValidation(upiRegisterDTO.getUpiPin(),upiRegisterDTO.getConfirmUpiPin(),"Upi Pin and confirm pin are not matching");
    }
    public static void UPIPinCompareValidation(Integer upiPin,Integer confirmPin,String message) throws InvalidUPIPinException{
        if(!Objects.equals(upiPin, confirmPin)){
            throw new InvalidUPIPinException(String.format(message));
        }
    }
    public static void UPIRegisterUPIIdValidation(UPIRegisterDTO upiRegisterDTO,UPIDetails upiDetails) throws InvalidEmailException{
        isValidUPIId(upiRegisterDTO.getUpiId());
        if(upiRegisterDTO.getUpiIdType() == NEWEMAIL && upiDetails != null){
            throw new InvalidEmailException(String.format("A different account is already registered with the UPI Id %s",upiRegisterDTO.getUpiId()));
        }
    }
    public static void accountLenValidation(Long accountNumber) throws InvalidAccountNumberException{
        String accountNumberString = String.valueOf(accountNumber);
        if (accountNumberString.length() != 11) {
            throw new InvalidAccountNumberException(String.format("Kindly enter valid 11 digit account number"));
        }
    }
    public static void UPIPinLenValidation(Integer upiPin) throws InvalidUPIPinException{
        String pinString = String.valueOf(upiPin);
        if(pinString.length() != 4){
            throw new InvalidUPIPinException(String.format("Kindly enter valid 4 digit UPI"));
        }
    }
    public static boolean UPIaccountExistOrNot(Object UPIaccount){
        if(!Objects.equals(UPIaccount,null)){
            return true;
        }
        return false;
    }
    public static void isValidUPIId(String email) throws InvalidEmailException{
        if(!isValidEmail(email))
        {
            throw new InvalidEmailException(String.format("Provided UPI Id is invalid %s , Kindly recheck",email));
        }
    }
    public static boolean isValidEmail(String email){
        return pattern.matcher(email).matches();
    }
    public static String generateUPIId(String email){
        int idx = email.indexOf("@");
        String userName = email.substring(0,idx+1);
        userName += "rdabank";
        upiLogger.info("UPI Id generated with bank name as extension");
        return userName;
    }
    public static void expiryDateValidation(String cardExpiry) throws InvalidExpiryDateException {
        if(!cardExpiry.matches("(?:0[1-9]|1[1-2])/[0-9]{2}")) throw new InvalidExpiryDateException(String.format("The provided expiry date is invalid %s , The valid date format is MM/YY",cardExpiry));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth yearMonth = YearMonth.parse(cardExpiry, dateTimeFormatter);
        LocalDate currentDate = LocalDate.now();
        //New comment-Do nothing
        if(yearMonth.isBefore(YearMonth.from(currentDate))){
            throw new InvalidExpiryDateException(String.format("The card expiry date is expired, Kindly renew the card"));
        }
    }

}

