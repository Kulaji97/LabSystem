package com.example.springboot.ApiController;
import com.example.springboot.Services.EmailRequestService;
import com.example.springboot.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody EmailRequestService emailRequest) {
        emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
        return "Email sent successfully!";
    }

    @PostMapping("/send-html-email")
    public String sendHtmlEmail(@RequestBody EmailRequestService emailRequest) {
        Context context = new Context();
        context.setVariable("date", "2024-03-16");
        context.setVariable("name", "Kulaji Lawanya");
        context.setVariable("email", "klawanya97@gmail.com");
        context.setVariable("test_name", "Blood");
        context.setVariable("test_type", "1");
        context.setVariable("test_amount", "1100");
        context.setVariable("payment_method", "CREDIT CARD");

        emailService.sendEmailWithHtmlTemplate(emailRequest.getTo(), emailRequest.getSubject(), "email-template", context);
        return "HTML email sent successfully!";
    }
}