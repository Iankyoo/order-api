package com.iankyoo.orderapi.order_api.service;

import com.iankyoo.orderapi.order_api.dto.CreateCustomerRequest;
import com.iankyoo.orderapi.order_api.dto.CustomerResponse;
import com.iankyoo.orderapi.order_api.entity.Customer;
import com.iankyoo.orderapi.order_api.exception.CustomerNotFoundException;
import com.iankyoo.orderapi.order_api.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository){
        this.repository = repository;
    }

    private CustomerResponse toResponse(Customer customer){
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCreatedAt()
        );
    }

    public Page<CustomerResponse> findAll(Pageable pageable){
        log.info("Finding all customers");
        return repository.findAll(pageable)
                .map(this::toResponse);
    }

    public CustomerResponse findById(Long id){
        log.info("Finding customer with id: {}", id);
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return toResponse(customer);
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request){
        log.info("Creating customer");
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());
        Customer saved = repository.save(customer);
        return toResponse(saved);
    }

    public void deleteCustomer(Long id){
        log.info("Deleting customer with id: {}", id);
        Customer toDelete = repository.findById(id)
                        .orElseThrow(()-> new CustomerNotFoundException(id));
        repository.delete(toDelete);
    }
}
