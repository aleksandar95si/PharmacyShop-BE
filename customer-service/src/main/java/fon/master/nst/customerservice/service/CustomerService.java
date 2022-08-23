package fon.master.nst.customerservice.service;

import fon.master.nst.customerservice.model.Customer;

public interface CustomerService {

    Customer saveUser(Customer customer);

    Customer getCustomerByUsername(String username);
}
