package fon.master.nst.orchestrationservice.service;

import fon.master.nst.orchestrationservice.dto.OrderRequest;
import fon.master.nst.orchestrationservice.dto.OrderResponse;

public interface OrchestrationService {

    OrderResponse processOrderRequest(OrderRequest orderRequest);


}
