package com.RDABank.RDABank.Service.DesignImpl;

import com.RDABank.RDABank.DTO.ForgotUPIPinDTO;
import com.RDABank.RDABank.DTO.UPIRegisterDTO;
import com.RDABank.RDABank.Exception.*;

public interface UPIServiceDesign {
    public String registerUPI(UPIRegisterDTO upiRegisterDTO) throws AccountDoesnotExistException, InvalidAccountNumberException,
            InvalidEmailException, InvalidUPIPinException, RegisteredForUPIOrNotException;
    public String getUPIDetails(Long accountNumber) throws RegisteredForUPIOrNotException,AccountDoesnotExistException,InvalidAccountNumberException;
    public String forgotUPIPin(ForgotUPIPinDTO forgotUPIPinDTO) throws InvalidEmailException,AccountDoesnotExistException,
            InvalidUPIPinException,InvalidAccountNumberException, IncorrectCardNumException, InvalidExpiryDateException;
}
