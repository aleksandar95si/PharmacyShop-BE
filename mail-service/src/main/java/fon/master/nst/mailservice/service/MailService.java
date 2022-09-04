package fon.master.nst.mailservice.service;

import fon.master.nst.mailservice.dto.MailResponse;
import fon.master.nst.mailservice.dto.ShoppingCart;

public interface MailService {

    MailResponse sendPDFReport(String recipientEmail, ShoppingCart shoppingCart);

}
