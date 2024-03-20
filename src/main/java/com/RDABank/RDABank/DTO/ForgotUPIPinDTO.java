package com.RDABank.RDABank.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ForgotUPIPinDTO {
    Long accountNo;
    String upiId;
    Integer last6Digit;
    String cardExpiry;
    Integer upiPin;
    Integer confirmUPIPin;
}
