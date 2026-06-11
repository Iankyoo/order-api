package com.iankyoo.orderapi.order_api.controller;

import com.iankyoo.orderapi.order_api.dto.CreateCustomerRequest;
import com.iankyoo.orderapi.order_api.dto.CustomerResponse;
import com.iankyoo.orderapi.order_api.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service){
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<Page<CustomerResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort
    ){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping()
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createCustomer(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }


}
