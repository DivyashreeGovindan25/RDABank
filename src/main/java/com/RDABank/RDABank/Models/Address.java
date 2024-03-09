package com.RDABank.RDABank.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
public class Address {
    @Id
    private Long accountNo;
    private String doorno;
    private String add1;
    private String add2;
    private String add3;
    private String city;
    private String state;
    private Integer zipcode;
    private String country;
}
