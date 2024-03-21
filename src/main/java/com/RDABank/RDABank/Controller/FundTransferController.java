package com.RDABank.RDABank.Controller;

import com.RDABank.RDABank.DTO.GeneralMessageDTO;
import com.RDABank.RDABank.DTO.UpiFundTransferDTO;
import com.RDABank.RDABank.Exception.*;
import com.RDABank.RDABank.Service.FundTransferService;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.RDABank.RDABank.Utils.RDALogger.fundTransferLoggerUpi;

@RestController
@RequestMapping("/fundTransfer")
public class FundTransferController {
    @Autowired
    FundTransferService fundTransferService;
    @PostMapping("/upiTransfer")
    public ResponseEntity upiTransfer(@RequestBody UpiFundTransferDTO upiFundTransferDTO){
        fundTransferLoggerUpi.info("Fund transfer controller - UPI");
        try{
            String response = fundTransferService.upiTransfer(upiFundTransferDTO);
            return new ResponseEntity<>(new GeneralMessageDTO(response),HttpStatus.OK);
        }
        catch(InvalidAccountNumberException | InvalidEmailException  |
               InvalidUPIPinException |
               LowBalanceException invalidAccountNumberException){
            return new ResponseEntity<>(new GeneralMessageDTO(invalidAccountNumberException.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
        catch(RegisteredForUPIOrNotException registeredForUPIOrNotException){
            return new ResponseEntity<>(new GeneralMessageDTO(registeredForUPIOrNotException.getMessage()),HttpStatus.NOT_FOUND);
        }

    }
}
