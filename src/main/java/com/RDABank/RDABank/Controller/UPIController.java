package com.RDABank.RDABank.Controller;

import com.RDABank.RDABank.DTO.GeneralMessageDTO;
import com.RDABank.RDABank.DTO.UPIRegisterDTO;
import com.RDABank.RDABank.Exception.*;
import com.RDABank.RDABank.Service.UPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.RDABank.RDABank.Utils.RDALogger.upiLogger;

@RestController
@RequestMapping("/UPI")
public class UPIController {
    @Autowired
    UPIService upiService;
    @PostMapping("/UPIRegister")
    public ResponseEntity registerUPI(@RequestBody UPIRegisterDTO upiRegisterDTO){
        upiLogger.info("In UPI Register controller");
        try{
            String response = upiService.registerUPI(upiRegisterDTO);
            upiLogger.info("Successfully registered for UPI");
            return new ResponseEntity<>(new GeneralMessageDTO(response), HttpStatus.CREATED);
        }
        catch (InvalidUPIPinException upiPinException){
            return new ResponseEntity<>(new GeneralMessageDTO(upiPinException.getMessage()),HttpStatus.LENGTH_REQUIRED);
        }
        catch (RegisteredForUPIOrNotException alreadyRegisteredForUPI){
            return new ResponseEntity<>(new GeneralMessageDTO(alreadyRegisteredForUPI.getMessage()),HttpStatus.CONFLICT);
        }
        catch (AccountDoesnotExistException accountDoesnotExistException){
            return new ResponseEntity<>(new GeneralMessageDTO(accountDoesnotExistException.getMessage()),HttpStatus.NOT_FOUND);
        }
        catch (InvalidEmailException | InvalidAccountNumberException invalidEmailException){
            return new ResponseEntity<>(new GeneralMessageDTO(invalidEmailException.getMessage()),HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new GeneralMessageDTO(e.getMessage()),HttpStatus.BAD_GATEWAY);
        }
    }
    @GetMapping("/{accountNumber}/getUPIID")
    public ResponseEntity getUPIDetails(@PathVariable Long accountNumber){
        upiLogger.info("In UPI Details controller");
        try{
            String upiId = upiService.getUPIDetails(accountNumber);
            upiLogger.info("Found the details");
            return new ResponseEntity<>(new GeneralMessageDTO(upiId),HttpStatus.FOUND);
        }
        catch(RegisteredForUPIOrNotException registeredForUPIOrNotException){
            return new ResponseEntity<>(new GeneralMessageDTO(registeredForUPIOrNotException.getMessage()),HttpStatus.NOT_FOUND);
        }
        catch(InvalidAccountNumberException invalidAccountNumberException){
            return new ResponseEntity<>(new GeneralMessageDTO(invalidAccountNumberException.getMessage()),HttpStatus.NOT_FOUND);
        }
        catch(AccountDoesnotExistException accountDoesnotExistException){
            return new ResponseEntity<>(new GeneralMessageDTO(accountDoesnotExistException.getMessage()),HttpStatus.NOT_FOUND);
        }
    }
}
