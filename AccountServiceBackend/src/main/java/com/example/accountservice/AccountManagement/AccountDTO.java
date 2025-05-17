package com.example.accountservice.AccountManagement;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String iban;
    private String bicSwift;
    private Long customerId;
}