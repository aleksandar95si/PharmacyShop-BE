package fon.master.nst.mailservice.service;

import fon.master.nst.mailservice.dto.MailResponse;
import fon.master.nst.mailservice.dto.ShoppingCart;
import fon.master.nst.mailservice.event.OrderEvent;

public interface MailService {

    MailResponse sendPDFReport(String recipientEmail, OrderEvent orderEvent);

}
