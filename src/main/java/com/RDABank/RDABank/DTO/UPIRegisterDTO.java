package com.RDABank.RDABank.DTO;

import com.RDABank.RDABank.Utils.UPIUtils.UPIIDType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UPIRegisterDTO {
    Long accountNo;
    String upiId;
    Integer upiPin;
    Integer confirmUpiPin;
    UPIIDType upiIdType;
}
