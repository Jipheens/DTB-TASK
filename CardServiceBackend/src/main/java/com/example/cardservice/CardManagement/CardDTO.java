package com.example.cardservice.CardManagement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    private Long id;
    private String alias;
    private Long accountId;
    private String type;
    private String pan;
    private String cvv;
}