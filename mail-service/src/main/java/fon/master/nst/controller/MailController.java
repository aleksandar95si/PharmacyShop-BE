package fon.master.nst.controller;

import fon.master.nst.dto.MailRequest;
import fon.master.nst.dto.MailResponse;
import fon.master.nst.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<MailResponse> sendPdfReport(@RequestBody MailRequest mailRequest) {

        MailResponse mailResponse = mailService.sendPDFReport(mailRequest.getEmail(), mailRequest.getShoppingCart());

        return ResponseEntity.ok(mailResponse);
    }
}
