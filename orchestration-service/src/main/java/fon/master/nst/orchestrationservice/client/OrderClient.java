package fon.master.nst.orchestrationservice.client;

import fon.master.nst.orchestrationservice.config.FeignAuthConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ORDER-SERVICE", configuration = FeignAuthConfiguration.class)
public interface OrderClient {
}
