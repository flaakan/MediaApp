package se.experis.academy.mediaapp.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.experis.academy.mediaapp.model.dao.CustomerFavoriteGenreDAO;
import se.experis.academy.mediaapp.model.dao.CustomerSpentDAO;
import se.experis.academy.mediaapp.model.dao.CustomerDAO;
import se.experis.academy.mediaapp.repository.CustomerRepository;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    CustomerRepository customerRepository = new CustomerRepository();

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDAO> getCustomerById(@PathVariable String customerId){
        CustomerDAO customer = customerRepository.getCustomerById(customerId);

        return ResponseEntity.ok(customer);
    }

    @GetMapping("/top/spenders")
    public  ResponseEntity<ArrayList<CustomerSpentDAO>> getAllTopSpenders(){
        return ResponseEntity.ok(customerRepository.getAllTopSpenders());
    }

    @GetMapping("/favorite/genre/{customerId}")
    public ResponseEntity<CustomerFavoriteGenreDAO> getCustomerFavoritGenreByCustomerId(@PathVariable int customerId){
        return ResponseEntity.ok(customerRepository.getCustomerFavoriteGenres(customerId));
    }
}
