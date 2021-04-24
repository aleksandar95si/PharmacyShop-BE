package fon.master.nst.orderservice.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.itextpdf.text.pdf.PdfObject;

import fon.master.nst.orderservice.dto.CartItem;
import fon.master.nst.orderservice.dto.OrderReport;
import fon.master.nst.orderservice.dto.ShoppingCart;
import fon.master.nst.orderservice.service.EmailService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private CurrentLoggedInUserService currentLoggedInUserService;
	
	public void getOrderItemsAndSendEmail(String recipient) {
		
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
	
	public void sendPDFReport(String recipient) {
		HttpHeaders httpHeaders=new HttpHeaders();
		httpHeaders.add("Authorization", AccesTokenService.getAccesToken());
		HttpEntity<ShoppingCart> httpEntity=new HttpEntity<>(httpHeaders);
		ResponseEntity<ShoppingCart> responseEntity=restTemplate.exchange("http://SHOPPING-CART/cart/get", HttpMethod.GET, httpEntity, ShoppingCart.class);
		
		ShoppingCart shoppingCart=responseEntity.getBody();
		
		/*
		OrderReport orderReport=new OrderReport();
		orderReport.setUsername(shoppingCart.getUsername());
		orderReport.setBill(shoppingCart.getBill());
		orderReport.setOrderDataSource(new JRBeanCollectionDataSource(shoppingCart.getCartItem(), false));	
		List<OrderReport> dataSource=new LinkedList<>();
		dataSource.add(orderReport);
		*/
		
		List<CartItem> listOfItems=shoppingCart.getCartItem();
		listOfItems.forEach(item -> {item.setShoppingCart(shoppingCart);});
		byte[] reportData = null;
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/OrderReport.jasper", new HashMap<String, Object>(), new JRBeanCollectionDataSource(listOfItems));
			
			reportData = JasperExportManager.exportReportToPdf(jasperPrint);
			OutputStream out;
			File tempPdf=File.createTempFile(shoppingCart.getUsername()+"-Report", ".pdf");
			out = new FileOutputStream(tempPdf);
			out.write(reportData);
			out.close();
			sendMailWithPDF(recipient, tempPdf);
			tempPdf.delete();
			
		} catch (JRException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMail(String recipient, String order) {
		SimpleMailMessage sms=new SimpleMailMessage();
		sms.setFrom("nst.mail.sender.test@gmail.com");
		sms.setTo(recipient);
		sms.setSubject("Porudžbina korisnika: " + currentLoggedInUserService.getCurrentUser());
		sms.setText(order);
		javaMailSender.send(sms);
	}
	
	public void sendMailWithPDF(String recipient, File tempPdf) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
		    MimeMessageHelper helper = new MimeMessageHelper(message, true);
		    helper.setFrom("nst.mail.sender.test@gmail.com");
		    helper.setTo(recipient);
		    helper.setSubject("Porudžbina korisnika: " + currentLoggedInUserService.getCurrentUser());
		    FileSystemResource file = new FileSystemResource(tempPdf);
		    helper.addAttachment(file.getFilename(), file);
		    helper.setText("");
		} catch (Exception e) {
			 System.err.println(e.getMessage());
		}
		javaMailSender.send(message);
	}
	
}
