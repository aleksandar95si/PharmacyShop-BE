package fon.master.nst.orchestrationservice.client;

import fon.master.nst.orchestrationservice.config.FeignAuthConfiguration;
import fon.master.nst.orchestrationservice.dto.MailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MAIL-SERVICE", configuration = FeignAuthConfiguration.class)
public interface MailClient {

    @PostMapping("/mails-api/send")
    ResponseEntity<?> send(@RequestBody MailRequest mailRequest);

}
