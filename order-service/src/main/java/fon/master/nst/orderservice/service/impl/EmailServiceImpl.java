package fon.master.nst.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fon.master.nst.orderservice.dto.CartItem;
import fon.master.nst.orderservice.dto.ShoppingCart;

@Service
public class EmailService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private CurrentLoggedInUserService currentLoggedInUserService;
	

	public void getOrderItemsAndsendEmail(String recipient) {
		HttpHeaders httpHeaders=new HttpHeaders();
		httpHeaders.add("Authorization", AccesTokenService.getAccesToken());
		HttpEntity<ShoppingCart> httpEntity=new HttpEntity<>(httpHeaders);
		ResponseEntity<ShoppingCart> responseEntity=restTemplate.exchange("http://SHOPPING-CART/cart/get", HttpMethod.GET, httpEntity, ShoppingCart.class);
		ShoppingCart shoppingCart=responseEntity.getBody();
		String order="ID: 	 NAZIV:	  CENA:\r\n \r\n";
		for(CartItem cartItem : shoppingCart.getCartItem()) {
			order=order + cartItem.getProductId() + " " + cartItem.getProductName() + " " + cartItem.getPrice() + "\r\n";
		}
		
		order=order+"\r\n \r\n"+"Ukupan iznos: "+shoppingCart.getBill()+" dinara";
		
		sendMail(recipient, order);
	}
	
	public void sendMail(String recipient, String order) {
		SimpleMailMessage sms=new SimpleMailMessage();
		sms.setFrom("nst.mail.sender.test@gmail.com");
		sms.setTo(recipient);
		sms.setSubject("Porudžbina korisnika: " + currentLoggedInUserService.getCurrentUser());
		sms.setText(order);
		javaMailSender.send(sms);
	}
	
}
