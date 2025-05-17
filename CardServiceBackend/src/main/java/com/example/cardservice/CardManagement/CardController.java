package com.example.cardservice.CardManagement;

import com.example.cardservice.ResponseMessage.EntityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<EntityResponse<CardDTO>> createCard(@RequestBody CardDTO dto) {
        EntityResponse<CardDTO> response = cardService.createCard(dto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityResponse<CardDTO>> getCard(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") boolean showSensitive) {
        EntityResponse<CardDTO> response = cardService.getCard(id, showSensitive);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping
    public ResponseEntity<EntityResponse<Page<CardDTO>>> searchCards(
            @RequestParam(required = false) String alias,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String pan,
            @RequestParam(required = false, defaultValue = "false") boolean showSensitive,
            Pageable pageable) {
        EntityResponse<Page<CardDTO>> response = cardService.searchCards(alias, type, pan, pageable, showSensitive);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityResponse<CardDTO>> updateCard(@PathVariable Long id, @RequestBody CardDTO dto) {
        EntityResponse<CardDTO> response = cardService.updateCard(id, dto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityResponse<String>> deleteCard(@PathVariable Long id) {
        EntityResponse<String> response = cardService.deleteCard(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}