package fon.master.nst.customerservice.service.impl;

import fon.master.nst.customerservice.model.Customer;
import fon.master.nst.customerservice.repository.CustomerRepository;
import fon.master.nst.customerservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer saveUser(Customer customer) {

        Customer savedCustomer;

        try {

            log.info("Trying to save new customer...");

            savedCustomer = customerRepository.save(customer);

            log.info("The new Customer saved!");

        } catch (Exception e) {

            log.error("An error has occurred while trying to save Customer");

            savedCustomer = null;
        }

        return savedCustomer;
    }

    @Override
    public Long getCustomersCredit(String username) {

        Customer customer;

        try {

            customer = customerRepository.findByUsername(username);

        } catch (Exception e) {

            log.error("An error has occurred while trying to get Customer's credit info");

            customer = null;
        }

        if (customer == null) {
            return null;
        }

        return customer.getCredit();
    }

}
