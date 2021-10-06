//package com.demo.kudaclone.services;
//
//import org.springframework.context.MessageSource;
//import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import java.nio.charset.StandardCharsets;
//
//@Service
//public class MailService {
//
//    private final JavaMailSender javaMailSender;
//
//    private final MessageSource messageSource;
//
//
//    public MailService(JavaMailSender javaMailSender, MessageSource messageSource) {
//        this.javaMailSender = javaMailSender;
//        this.messageSource = messageSource;
//    }
//
//    public void sendMail(String to, String from) {
//
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
//            message.setTo(to);
//            message.setFrom();
//            message.setSubject(subject);
//            message.setText(content, isHtml);
//            javaMailSender.send(mimeMessage);
//        } catch (MailException | MessagingException e) {
//            System.out.println("encountered an error: " + e.getMessage() );
//        }
//
//    }
//}
