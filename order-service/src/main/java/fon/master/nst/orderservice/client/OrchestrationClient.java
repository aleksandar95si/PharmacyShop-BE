package fon.master.nst.orderservice.client;

import fon.master.nst.orderservice.config.FeignAuthConfiguration;
import fon.master.nst.orderservice.dto.OrderRequest;
import fon.master.nst.orderservice.dto.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ORCHESTRATION-SERVICE", configuration = FeignAuthConfiguration.class)
public interface OrchestrationClient {

    @PostMapping("/orchestration-api/orders")
    public OrderResponse processOrderRequest(@RequestBody OrderRequest orderRequest);

}
