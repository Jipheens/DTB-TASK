package com.example.customerservice.Utils.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeStateDTO {
    private String status;
    private String verifierRemarks;
    private Long id;
}
