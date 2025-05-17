package com.example.customerservice.CustomerManagement;

import com.example.customerservice.ResponseMessage.EntityResponse;
import com.example.customerservice.Utils.shared.AuditTrailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public EntityResponse<CustomerDTO> createCustomer(CustomerDTO dto) {
        Customer customer = Customer.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .otherName(dto.getOtherName())
                .build();
        customer = customerRepository.save(AuditTrailService.POSTAudit(customer));
        dto.setId(customer.getId());
        return new EntityResponse<>("Customer created successfully", dto, 201);
    }

    public EntityResponse<CustomerDTO> getCustomerById(Long id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            CustomerDTO dto = CustomerDTO.builder()
                    .id(customer.getId())
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .otherName(customer.getOtherName())
                    .build();
            return new EntityResponse<>("Customer found", dto, 200);
        } else {
            return new EntityResponse<>("Customer not found", null, 404);
        }
    }

    public EntityResponse<CustomerDTO> updateCustomer(Long id, CustomerDTO dto) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            customer.setFirstName(dto.getFirstName());
            customer.setLastName(dto.getLastName());
            customer.setOtherName(dto.getOtherName());
            customerRepository.save(AuditTrailService.MODIFYAudit(customerOpt.get(),customer));
            dto.setId(customer.getId());
            return new EntityResponse<>("Customer updated successfully", dto, 200);
        } else {
            return new EntityResponse<>("Customer not found", null, 404);
        }
    }

    public EntityResponse<CustomerDTO> getCustomer(Long id) {
        Optional<Customer> customerOpt = customerRepository.findByIdAndDeletedFlag(id, 'N');
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            CustomerDTO dto = CustomerDTO.builder()
                    .id(customer.getId())
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .otherName(customer.getOtherName())
                    .build();
            return new EntityResponse<>("Customer found", dto, 200);
        } else {
            return new EntityResponse<>("Customer not found", null, 404);
        }
    }


    public EntityResponse<String> deleteCustomer(Long id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            customer = AuditTrailService.DELETEAudit(customer);
            customerRepository.save(customer);
            return new EntityResponse<>("Customer deleted successfully", null, 200);
        } else {
            return new EntityResponse<>("Customer not found", null, 404);
        }
    }


    public EntityResponse<Page<Customer>> search(String name, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        String searchTerm = (name == null || name.isBlank()) ? null : "%" + name.trim() + "%";
        Page<Customer> result = customerRepository.search(searchTerm, start, end, pageable);
        return new EntityResponse<>("Search completed", result, 200);
    }
}
