package com.example.cardservice;

import com.example.cardservice.CardManagement.Card;
import com.example.cardservice.CardManagement.CardDTO;
import com.example.cardservice.CardManagement.CardRepository;
import com.example.cardservice.CardManagement.CardService;
import com.example.cardservice.ResponseMessage.EntityResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

	@Mock
	private CardRepository cardRepository;

	@InjectMocks
	private CardService cardService;

	private Card sampleCard;

	@BeforeEach
	void setup() {
		sampleCard = Card.builder()
				.alias("My Card")
				.accountId(100L)
				.type("virtual")
				.pan("1234567890123456")
				.cvv("123")
				.build();
		sampleCard.setId(1L);
	}


	@Test
	void testCreateCard() {
		CardDTO dto = CardDTO.builder()
				.alias("My Card")
				.accountId(100L)
				.type("virtual")
				.pan("1234567890123456")
				.cvv("123")
				.build();

		when(cardRepository.save(any())).thenReturn(sampleCard);

		EntityResponse<CardDTO> response = cardService.createCard(dto);

		assertEquals(201, response.getStatusCode());
		assertEquals("My Card", response.getEntity().getAlias());
		verify(cardRepository, times(1)).save(any(Card.class));
	}

	@Test
	void testGetCard_found_withMasking() {
		when(cardRepository.findByIdAndDeletedFlag(1L, 'N')).thenReturn(Optional.of(sampleCard));

		EntityResponse<CardDTO> response = cardService.getCard(1L, false);

		assertEquals(200, response.getStatusCode());
		assertEquals("My Card", response.getEntity().getAlias());
		assertEquals("************3456", response.getEntity().getPan());
		assertEquals("***", response.getEntity().getCvv());
	}

	@Test
	void testGetCard_notFound() {
		when(cardRepository.findByIdAndDeletedFlag(any(), eq('N'))).thenReturn(Optional.empty());

		EntityResponse<CardDTO> response = cardService.getCard(2L, false);

		assertEquals(404, response.getStatusCode());
		assertNull(response.getEntity());
	}

	@Test
	void testUpdateCard_found() {
		when(cardRepository.findByIdAndDeletedFlag(1L, 'N')).thenReturn(Optional.of(sampleCard));
		when(cardRepository.save(any())).thenReturn(sampleCard);

		CardDTO dto = CardDTO.builder().alias("Updated Alias").build();
		EntityResponse<CardDTO> response = cardService.updateCard(1L, dto);

		assertEquals(200, response.getStatusCode());
		assertEquals("Updated Alias", response.getEntity().getAlias());
	}

	@Test
	void testUpdateCard_notFound() {
		when(cardRepository.findByIdAndDeletedFlag(2L, 'N')).thenReturn(Optional.empty());

		CardDTO dto = CardDTO.builder().alias("Updated Alias").build();
		EntityResponse<CardDTO> response = cardService.updateCard(2L, dto);

		assertEquals(404, response.getStatusCode());
		assertNull(response.getEntity());
	}

	@Test
	void testDeleteCard_found() {
		when(cardRepository.findByIdAndDeletedFlag(1L, 'N')).thenReturn(Optional.of(sampleCard));
		when(cardRepository.save(any(Card.class))).thenReturn(sampleCard); // Mock the save to return the card

		EntityResponse<String> response = cardService.deleteCard(1L);

		assertEquals(200, response.getStatusCode());
		assertEquals("Card deleted successfully", response.getMessage());
		verify(cardRepository, times(1)).save(any(Card.class)); // Verify save is called
	}

	@Test
	void testDeleteCard_notFound() {
		when(cardRepository.findByIdAndDeletedFlag(2L, 'N')).thenReturn(Optional.empty());

		EntityResponse<String> response = cardService.deleteCard(2L);

		assertEquals(404, response.getStatusCode());
		assertEquals("Card not found", response.getMessage());
		verify(cardRepository, never()).deleteById(2L); // Verify deleteById is not called
		verify(cardRepository, never()).save(any(Card.class)); // Verify save is not called
	}


	@Test
	void testSearchCards() {
		Page<Card> page = new PageImpl<>(Collections.singletonList(sampleCard));
		Pageable pageable = PageRequest.of(0, 10);
		when(cardRepository.search(any(), any(), any(), eq(pageable))).thenReturn(page);

		EntityResponse<Page<CardDTO>> response = cardService.searchCards("My", "virtual", "1234", pageable, false);

		assertEquals(200, response.getStatusCode());
		assertEquals(1, response.getEntity().getTotalElements());
		verify(cardRepository, times(1)).search(any(), any(), any(), eq(pageable));
	}
}
