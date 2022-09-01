package fon.master.nst.customerservice.controller;

import fon.master.nst.customerservice.model.Customer;
import fon.master.nst.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {

        Customer savedCustomer = customerService.saveUser(customer);

        if (savedCustomer == null) {
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<Customer> getCustomerByUsername(@RequestParam String username) {

        Customer customer = customerService.getCustomerByUsername(username);

        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(customer);
    }

}