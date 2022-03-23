package fon.master.nst.orderservice.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import fon.master.nst.orderservice.client.ShoppingCartClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import fon.master.nst.orderservice.dto.CartItem;
import fon.master.nst.orderservice.dto.ShoppingCart;
import fon.master.nst.orderservice.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private ShoppingCartClient shoppingCartClient;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Configuration configuration;
    @Autowired
    private CurrentLoggedInUserService currentLoggedInUserService;

    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    public void sendPDFReport(String recipient) {

        logger.info("Order microservice calls Shopping cart microservice to get shopping cart of currently logged user");

        ShoppingCart shoppingCart = shoppingCartClient.getShoppingCart(AccessTokenService.getAccessToken());

        List<CartItem> listOfItems = shoppingCart.getCartItems();
        listOfItems.forEach(item -> item.setShoppingCart(shoppingCart));
        OutputStream out;
        byte[] reportData;
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/OrderReport.jasper", new HashMap<String, Object>(), new JRBeanCollectionDataSource(listOfItems));

            reportData = JasperExportManager.exportReportToPdf(jasperPrint);

            File tempPdf = File.createTempFile(shoppingCart.getUsername() + "-Report", ".pdf");
            out = new FileOutputStream(tempPdf);
            out.write(reportData);
            out.close();
            sendMailWithPDF(recipient, tempPdf, shoppingCart.getBill());
            tempPdf.delete();
            logger.info("Jasper report was created and the email was sent");

        } catch (JRException | IOException e) {
            // TODO Auto-generated catch block
            logger.error("OrderServiceImpl: Error in sendPDFReport method");
            e.printStackTrace();
        }
    }

    public void sendMailWithPDF(String recipient, File tempPdf, Long bill) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("nst.mail.sender.test@gmail.com");
            helper.setTo(recipient);
            helper.setSubject("Porudžbina korisnika: " + currentLoggedInUserService.getCurrentUser());
            FileSystemResource file = new FileSystemResource(tempPdf);
            helper.addAttachment(file.getFilename(), file);

            configuration.setDirectoryForTemplateLoading(new File("C:/Users/aleks/git/NST-Project/order-service/src/main/resources/templates/"));
            Template template = configuration.getTemplate("email-template.ftl");

            Map<String, Object> model = new HashMap<>();
            model.put("Name", currentLoggedInUserService.getCurrentUser());
            model.put("Bill", bill);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setText(html, true);
        } catch (Exception e) {
            logger.error("Error in sendMailWithPDF method");
            System.err.println(e.getMessage());
        }
        javaMailSender.send(message);
    }
}
