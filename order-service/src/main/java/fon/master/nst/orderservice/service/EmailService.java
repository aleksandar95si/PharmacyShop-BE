package fon.master.nst.orderservice.service;

public interface EmailService {
	void getOrderItemsAndSendEmail(String recipient);
	void sendPDFReport(String recipient);
}
