package com.example.customerservice;

import com.example.customerservice.CustomerManagement.*;
import com.example.customerservice.ResponseMessage.EntityResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {


	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerService customerService;

	@Test
	void testCreateCustomer() {
		CustomerDTO dto = CustomerDTO.builder()
				.firstName("John")
				.lastName("Doe")
				.otherName("Middle")
				.build();

		Customer saved = Customer.builder()
				.firstName("John")
				.lastName("Doe")
				.otherName("Middle")
				.build();
		saved.setId(1L);


		when(customerRepository.save(any())).thenReturn(saved);

		EntityResponse<CustomerDTO> response = customerService.createCustomer(dto);
		CustomerDTO result = response.getEntity();

		assertNotNull(result.getId());
		assertEquals("Customer created successfully", response.getMessage());
		assertEquals(201, response.getStatusCode());
		assertEquals("John", result.getFirstName());
		verify(customerRepository, times(1)).save(any());
	}

	@Test
	void testSearchCustomer() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Customer> page = new PageImpl<>(Collections.emptyList());
		when(customerRepository.search(null, null, null, pageable)).thenReturn(page);

		EntityResponse<Page<Customer>> response = customerService.search(null, null, null, pageable);
		Page<Customer> result = response.getEntity();

		assertNotNull(result);
		assertEquals(0, result.getTotalElements());
		assertEquals("Search completed", response.getMessage());
		assertEquals(200, response.getStatusCode());
		verify(customerRepository, times(1)).search(null, null, null, pageable);
	}

	@Test
	void testGetCustomerFound() {
		Customer customer = Customer.builder()
				.firstName("Jane")
				.lastName("Doe")
				.otherName("Middle")
				.build();
		customer.setId(1L);

		when(customerRepository.findByIdAndDeletedFlag(1L, 'N')).thenReturn(Optional.of(customer));

		EntityResponse<CustomerDTO> response = customerService.getCustomer(1L);
		CustomerDTO result = response.getEntity();

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Jane", result.getFirstName());
		assertEquals(200, response.getStatusCode());
	}


	@Test
	void testGetCustomerNotFound() {
		when(customerRepository.findByIdAndDeletedFlag(1L, 'N')).thenReturn(Optional.empty());

		EntityResponse<CustomerDTO> response = customerService.getCustomer(1L);

		assertNull(response.getEntity());
		assertEquals(404, response.getStatusCode());
		assertEquals("Customer not found", response.getMessage());
	}


	@Test
	void testDeleteCustomerSuccess() {
		Customer existing = Customer.builder()
				.firstName("Mark")
				.lastName("Twain")
				.build();
		existing.setId(1L);

		when(customerRepository.findById(1L)).thenReturn(Optional.of(existing));
		when(customerRepository.save(any())).thenReturn(existing);

		EntityResponse<String> response = customerService.deleteCustomer(1L);

		assertEquals(200, response.getStatusCode());
		assertEquals("Customer deleted successfully", response.getMessage());
		verify(customerRepository, times(1)).save(any());
	}


	@Test
	void testDeleteCustomerNotFound() {
		when(customerRepository.findById(1L)).thenReturn(Optional.empty());

		EntityResponse<String> response = customerService.deleteCustomer(1L);

		assertEquals(404, response.getStatusCode());
		assertEquals("Customer not found", response.getMessage());
	}


	@Test
	void testUpdateCustomerSuccess() {
		CustomerDTO dto = CustomerDTO.builder()
				.firstName("Updated")
				.lastName("Name")
				.otherName("New")
				.build();

		Customer existing = Customer.builder()
				.firstName("Old")
				.lastName("Name")
				.otherName("Old")
				.build();
		existing.setId(1L);

		when(customerRepository.findById(1L)).thenReturn(Optional.of(existing));
		when(customerRepository.save(any())).thenReturn(existing);

		EntityResponse<CustomerDTO> response = customerService.updateCustomer(1L, dto);
		CustomerDTO updated = response.getEntity();

		assertEquals(200, response.getStatusCode());
		assertEquals("Customer updated successfully", response.getMessage());
		assertNotNull(updated.getId());
		assertEquals("Updated", updated.getFirstName());
	}


	@Test
	void testUpdateCustomerNotFound() {
		CustomerDTO dto = CustomerDTO.builder()
				.firstName("Updated")
				.lastName("Name")
				.otherName("New")
				.build();

		when(customerRepository.findById(1L)).thenReturn(Optional.empty());

		EntityResponse<CustomerDTO> response = customerService.updateCustomer(1L, dto);

		assertEquals(404, response.getStatusCode());
		assertEquals("Customer not found", response.getMessage());
		assertNull(response.getEntity());
	}
}