package fon.master.nst.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fon.master.nst.orderservice.service.EmailService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private EmailService emailService;
	
	/*
	@PostMapping("/submit/{recipient}")
	public void getOrderItemsAndsendEmail(@PathVariable("recipient") String recipient) {
		emailService.getOrderItemsAndsendEmail(recipient);
	}
	*/
	
	@PostMapping("/submit")
	public void getOrderItemsAndsendEmail() {
		emailService.getOrderItemsAndsendEmail("nikolicka.995@gmail.com"); //hardkotovati mejl primaoca radi testiranja
	}
}
