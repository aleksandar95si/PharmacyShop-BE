package fon.master.nst.orchestrationservice.controller;

import fon.master.nst.orchestrationservice.dto.OrderRequest;
import fon.master.nst.orchestrationservice.dto.OrderResponse;
import fon.master.nst.orchestrationservice.service.OrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class OrchestrationController {

    @Autowired
    private OrchestrationService orchestrationService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> receiveOrderRequest (@RequestBody OrderRequest orderRequest){

        OrderResponse orderResponse = orchestrationService.processOrderRequest(orderRequest);

        return ResponseEntity.ok(orderResponse);
    }

}
