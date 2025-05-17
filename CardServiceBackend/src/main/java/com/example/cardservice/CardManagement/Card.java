package com.example.cardservice.CardManagement;

import com.example.cardservice.Utils.shared.Audittrails;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card extends Audittrails {

    @Column(nullable = false)
    private String alias;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private String type; // VIRTUAL or PHYSICAL

    @Column(nullable = false, unique = true)
    private String pan;

    @Column(nullable = false)
    private String cvv;
}