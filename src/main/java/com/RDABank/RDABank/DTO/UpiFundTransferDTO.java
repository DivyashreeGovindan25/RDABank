package com.RDABank.RDABank.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpiFundTransferDTO {
    String upiId;
    String toUpiId;
    Long accountNo;
    Integer upiPin;
    Double transferAmount;
}
