package com.example.customerservice.CustomerManagement;
import com.example.customerservice.ResponseMessage.EntityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<EntityResponse<CustomerDTO>> create(@RequestBody CustomerDTO dto) {
        EntityResponse<CustomerDTO> response = customerService.createCustomer(dto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityResponse<CustomerDTO>> update(@PathVariable Long id, @RequestBody CustomerDTO dto) {
        EntityResponse<CustomerDTO> response = customerService.updateCustomer(id, dto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityResponse<CustomerDTO>> getCustomer(@PathVariable Long id) {
        EntityResponse<CustomerDTO> response = customerService.getCustomer(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityResponse<String>> delete(@PathVariable Long id) {
        EntityResponse<String> response = customerService.deleteCustomer(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/search")
    public ResponseEntity<EntityResponse<Page<Customer>>> search(@RequestParam(required = false) String name,
                                                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                                 Pageable pageable) {
        EntityResponse<Page<Customer>> response = customerService.search(name, startDate, endDate, pageable);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
