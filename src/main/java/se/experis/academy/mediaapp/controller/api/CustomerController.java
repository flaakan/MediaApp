package se.experis.academy.mediaapp.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.mediaapp.model.dao.CountryCustomersDAO;
import se.experis.academy.mediaapp.model.dao.CustomerFavoriteGenreDAO;
import se.experis.academy.mediaapp.model.dao.CustomerSpentDAO;
import se.experis.academy.mediaapp.model.dao.CustomerDAO;
import se.experis.academy.mediaapp.model.entity.Customer;
import se.experis.academy.mediaapp.repository.CustomerRepository;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    CustomerRepository customerRepository = new CustomerRepository();

    @GetMapping(value = "/all")
    public ArrayList<CustomerDAO> getAllCustomers() {
        ArrayList<CustomerDAO> allCustomers = customerRepository.getAllCustomers();
        return allCustomers;
    }

    @GetMapping(value = "/country/total")
    public ResponseEntity<ArrayList<CountryCustomersDAO>> getNumberOfCustomerPerCountry() {
        return ResponseEntity.ok(customerRepository.getCustomerQuantityPerCountry());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Boolean addNewCustomer(@RequestBody Customer customer) {
        return customerRepository.addCustomer(customer);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Boolean updateCustomer(@RequestBody Customer customer) {
        return customerRepository.updateCustomer(customer);
    }

    @GetMapping("/top/spenders")
    public ResponseEntity<ArrayList<CustomerSpentDAO>> getAllTopSpenders() {
        return ResponseEntity.ok(customerRepository.getAllTopSpenders());
    }

    @GetMapping("/favorite/genre/{customerId}")
    public ResponseEntity<CustomerFavoriteGenreDAO> getCustomerFavoritGenreByCustomerId(@PathVariable int customerId) {
        return ResponseEntity.ok(customerRepository.getCustomerFavoriteGenres(customerId));
    }
}
