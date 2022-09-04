package fon.master.nst.orchestrationservice.client;

import fon.master.nst.orchestrationservice.config.FeignAuthConfiguration;
import fon.master.nst.orchestrationservice.dto.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CUSTOMER-SERVICE", configuration = FeignAuthConfiguration.class)
public interface CustomerClient {

    @GetMapping("/customers-api/customers")
    Customer getCustomerByUsername(@RequestParam String username);

}
