package se.experis.academy.mediaapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.experis.academy.mediaapp.model.web.CustomerWeb;
import se.experis.academy.mediaapp.repository.CustomerRepository;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    CustomerRepository customerRepository = new CustomerRepository();

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerWeb> getCustomerById(@PathVariable String customerId){
        CustomerWeb customer = customerRepository.getCustomerById(customerId);

        return ResponseEntity.ok(customer);
    }
}
