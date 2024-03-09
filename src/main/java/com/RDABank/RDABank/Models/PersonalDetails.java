package com.RDABank.RDABank.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "PERSONAL_DETAILS")
public class PersonalDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "ACCOUNT_NO")
    private AccountDetails accountNo;
    @Column(name = "EMAIL",nullable = false)
    private String email;
    @Column(name = "PHONE",length = 10,nullable = false)
    private Long phone;
}
