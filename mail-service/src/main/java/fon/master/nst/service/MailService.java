package fon.master.nst.service;

import fon.master.nst.dto.MailResponse;
import fon.master.nst.dto.ShoppingCart;

public interface MailService {

    MailResponse sendPDFReport(String recipientEmail, ShoppingCart shoppingCart);

}
