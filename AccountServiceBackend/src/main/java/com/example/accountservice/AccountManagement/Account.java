package com.example.accountservice.AccountManagement;

import com.example.accountservice.Utils.shared.Audittrails;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends  Audittrails{


    @Column(nullable = false, unique = true)
    private String iban;

    @Column(nullable = false)
    private String bicSwift;

    @Column(nullable = false)
    private Long customerId;
}