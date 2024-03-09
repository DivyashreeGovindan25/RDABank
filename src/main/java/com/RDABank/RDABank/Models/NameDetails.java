package com.RDABank.RDABank.Models;

import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
public class NameDetails {
    @Id
    private Long accountNo;
    private String prefix;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
}
