package fon.master.nst.mailservice.service.impl;

import fon.master.nst.mailservice.dto.MailResponse;
import fon.master.nst.mailservice.dto.MailStatus;
import fon.master.nst.mailservice.dto.ShoppingCart;
import fon.master.nst.mailservice.event.OrderEvent;
import fon.master.nst.mailservice.model.OrderStatus;
import fon.master.nst.mailservice.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Configuration configuration;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Override
    public MailResponse sendPDFReport(String recipientEmail, OrderEvent orderEvent) {

        System.out.println(orderEvent.getShoppingCart());

        OutputStream out;

        byte[] reportData;
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport("C:/Users/aleks/workspace/PharmacyShop-BE/mail-service/src/main/resources/OrderReport.jasper", new HashMap<String, Object>(), new JRBeanCollectionDataSource(orderEvent.getShoppingCart().getCartItems()));

            reportData = JasperExportManager.exportReportToPdf(jasperPrint);

            File tempPdf = File.createTempFile(orderEvent.getShoppingCart().getUsername() + "-Report", ".pdf");
            out = new FileOutputStream(tempPdf);
            out.write(reportData);
            out.close();
            sendMailWithPDF(recipientEmail, orderEvent.getShoppingCart().getUsername(), tempPdf, orderEvent.getShoppingCart().getBill(), orderEvent);
            tempPdf.delete();
            logger.info("Jasper report was created and the email was sent");

        } catch (JRException | IOException e) {
            e.printStackTrace();
            return new MailResponse(MailStatus.FAILED);
        }

        return new MailResponse(MailStatus.SENT);
    }

    private void sendMailWithPDF(String recipient, String recipientName, File tempPdf, Long bill, OrderEvent orderEvent) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("nst.mail.sender@gmail.com");
            helper.setTo(recipient);
            helper.setSubject("Porudžbina korisnika: " + recipientName);
            FileSystemResource file = new FileSystemResource(tempPdf);
            helper.addAttachment(file.getFilename(), file);
            helper.setText("", true);

            configuration.setDirectoryForTemplateLoading(new File("C:/Users/aleks/workspace/PharmacyShop-BE/mail-service/src/main/resources/templates/"));
            Template template = configuration.getTemplate("email-template.ftl");

            Map<String, Object> model = new HashMap<>();
            model.put("Name", recipientName);
            model.put("Bill", bill);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setText(html, true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            javaMailSender.send(message);
            orderEvent.setOrderStatus(OrderStatus.MAIL_SENT);
        } catch (Exception exception) {
            orderEvent.setOrderStatus(OrderStatus.MAIL_FAILED);
        }

        Headers headers = new RecordHeaders();
        headers.add(new RecordHeader("orderStatus", orderEvent.getOrderStatus().name().getBytes()));

        ProducerRecord<String, OrderEvent> record = new ProducerRecord<String, OrderEvent>("order-topic", null, null, orderEvent, headers);

        ListenableFuture<SendResult<String, OrderEvent>> future = kafkaTemplate.send(record);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, OrderEvent> result) {
                log.info("Sent message={} to topic={} with offset={}", orderEvent, result.getRecordMetadata().topic(), result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message={} due to : {}", orderEvent, ex.getMessage());
                // TODO: 4/15/2023 retry mechanism
            }
        });
    }

    @KafkaListener(topics = "order-topic", groupId = "mail-service-group")
    public void receive(@Payload OrderEvent orderEvent, @org.springframework.messaging.handler.annotation.Headers Map<String, Object> headers) {
        // Proveri vrednost orderStatus header-a
        if (headers.containsKey("orderStatus")) {
            String orderStatus = new String((byte[]) headers.get("orderStatus"));
            if (orderStatus.equals(OrderStatus.PAYMENT_SUCCESSFUL.name())) {
                // logika za obradu poruke
                System.out.println(orderEvent);
                sendPDFReport(orderEvent.getCustomer().getEmail(), orderEvent);
                // sendPDFReport(orderEvent.getCustomer().getEmail(), 24124L);
            } else {
                // ne želimo da deserijalizujemo poruku koja nije za nas, zato preskačemo obradu
            }
        } else {
        }
    }
}
