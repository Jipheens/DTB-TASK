package com.example.cardservice.CardManagement;

import com.example.cardservice.ResponseMessage.EntityResponse;
import com.example.cardservice.Utils.shared.AuditTrailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    public EntityResponse<CardDTO> createCard(CardDTO dto) {
        if (cardRepository.existsByAccountIdAndType(dto.getAccountId(), dto.getType())) {
            return new EntityResponse<>("Each account can only have one card per type", null, 400);
        }

        if (cardRepository.countByAccountId(dto.getAccountId()) >= 2) {
            return new EntityResponse<>("An account cannot have more than two cards", null, 400);
        }

        Card card = Card.builder()
                .alias(dto.getAlias())
                .accountId(dto.getAccountId())
                .type(dto.getType())
                .pan(dto.getPan())
                .cvv(dto.getCvv())
                .build();

        card = cardRepository.save(AuditTrailService.POSTAudit(card));
        dto.setId(card.getId());
        return new EntityResponse<>("Card created successfully", maskCardFields(dto, false), 201);
    }

    public EntityResponse<CardDTO> getCard(Long id, boolean showSensitive) {
        Optional<Card> cardOpt = cardRepository.findByIdAndDeletedFlag(id, 'N');
        if (cardOpt.isPresent()) {
            Card card = cardOpt.get();
            CardDTO dto = CardDTO.builder()
                    .id(card.getId())
                    .alias(card.getAlias())
                    .accountId(card.getAccountId())
                    .type(card.getType())
                    .pan(card.getPan())
                    .cvv(card.getCvv())
                    .build();
            return new EntityResponse<>("Card found", maskCardFields(dto, !showSensitive), 200);
        } else {
            return new EntityResponse<>("Card not found", null, 404);
        }
    }

    public EntityResponse<Page<CardDTO>> searchCards(String alias, String type, String pan, Pageable pageable, boolean showSensitive) {
        Page<Card> result = cardRepository.search(alias, type, pan, pageable);

        Page<CardDTO> mappedPage = result.map(card -> {
            CardDTO dto = CardDTO.builder()
                    .id(card.getId())
                    .alias(card.getAlias())
                    .accountId(card.getAccountId())
                    .type(card.getType())
                    .pan(card.getPan())
                    .cvv(card.getCvv())
                    .build();
            return maskCardFields(dto, !showSensitive);
        });

        return new EntityResponse<>("Search completed", mappedPage, 200);
    }

    public EntityResponse<CardDTO> updateCard(Long id, CardDTO dto) {
        Optional<Card> existingOpt = cardRepository.findByIdAndDeletedFlag(id, 'N');
        if (existingOpt.isPresent()) {
            Card existing = existingOpt.get();
            existing.setAlias(dto.getAlias());
            cardRepository.save(AuditTrailService.MODIFYAudit(existingOpt.get(),existing));

            dto.setId(existing.getId());
            dto.setAccountId(existing.getAccountId());
            dto.setType(existing.getType());
            dto.setPan(existing.getPan());
            dto.setCvv(existing.getCvv());

            return new EntityResponse<>("Card updated successfully", maskCardFields(dto, true), 200);
        } else {
            return new EntityResponse<>("Card not found", null, 404);
        }
    }

    public EntityResponse<String> deleteCard(Long id) {
        Optional<Card> cardOpt = cardRepository.findByIdAndDeletedFlag(id, 'N');
        if (cardOpt.isPresent()) {
            Card card = cardOpt.get();

            cardRepository.save(AuditTrailService.DELETEAudit(card));
            return new EntityResponse<>("Card deleted successfully", null, 200);
        } else {
            return new EntityResponse<>("Card not found", null, 404);
        }
    }

    private CardDTO maskCardFields(CardDTO dto, boolean mask) {
        if (dto == null || !mask) return dto;

        String maskedPan = dto.getPan() != null && dto.getPan().length() > 4
                ? "*".repeat(dto.getPan().length() - 4) + dto.getPan().substring(dto.getPan().length() - 4)
                : dto.getPan();

        String maskedCvv = dto.getCvv() != null ? "***" : null;

        return CardDTO.builder()
                .id(dto.getId())
                .alias(dto.getAlias())
                .accountId(dto.getAccountId())
                .type(dto.getType())
                .pan(maskedPan)
                .cvv(maskedCvv)
                .build();
    }
}
