package com.RDABank.RDABank.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ForgotUPIPinDTO {
    Long accountNo;
    String upiId;
    Integer last6Digit;
    String cardExpiry;
    Integer upiPin;
    Integer confirmUPIPin;
}
