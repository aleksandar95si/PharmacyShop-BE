package fon.master.nst.mailservice.service.impl;

import fon.master.nst.mailservice.dto.MailResponse;
import fon.master.nst.mailservice.dto.MailStatus;
import fon.master.nst.mailservice.dto.ShoppingCart;
import fon.master.nst.mailservice.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Configuration configuration;

    private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Override
    public MailResponse sendPDFReport(String recipientEmail, ShoppingCart shoppingCart) {

        System.out.println(shoppingCart);

        OutputStream out;

        byte[] reportData;
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport("C:/Users/aleks/workspace/PharmacyShop-BE/mail-service/src/main/resources/OrderReport.jasper", new HashMap<String, Object>(), new JRBeanCollectionDataSource(shoppingCart.getCartItems()));

            reportData = JasperExportManager.exportReportToPdf(jasperPrint);

            File tempPdf = File.createTempFile(AuthService.getCurrentUsersUsername() + "-Report", ".pdf");
            out = new FileOutputStream(tempPdf);
            out.write(reportData);
            out.close();
            sendMailWithPDF(recipientEmail, tempPdf, shoppingCart.getBill());
            tempPdf.delete();
            logger.info("Jasper report was created and the email was sent");

        } catch (JRException | IOException e) {
            e.printStackTrace();
            return new MailResponse(MailStatus.FAILED);
        }

        return new MailResponse(MailStatus.SENT);
    }

    private void sendMailWithPDF(String recipient, File tempPdf, Long bill) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("nst.mail.sender@gmail.com");
            helper.setTo(recipient);
            helper.setSubject("Porudžbina korisnika: " + AuthService.getCurrentUsersUsername());
            FileSystemResource file = new FileSystemResource(tempPdf);
            helper.addAttachment(file.getFilename(), file);

            configuration.setDirectoryForTemplateLoading(new File("C:/Users/aleks/workspace/PharmacyShop-BE/mail-service/src/main/resources/templates/"));
            Template template = configuration.getTemplate("email-template.ftl");

            Map<String, Object> model = new HashMap<>();
            model.put("Name", AuthService.getCurrentUsersUsername());
            model.put("Bill", bill);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setText(html, true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            logger.error("An error has occurred while trying to send email");
        }
    }
}
