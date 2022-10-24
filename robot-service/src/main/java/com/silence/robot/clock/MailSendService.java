package com.silence.robot.clock;

import java.util.Date;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.silence.robot.exception.BusinessException;

@Service
public class MailSendService {

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromUser;
    @Async
    public void send(String subject, String text, String sendAddress) {
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发件人
        message.setFrom(fromUser);
        //邮件收件人 1或多个
        message.setTo(sendAddress);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(text);
        //邮件发送时间
        message.setSentDate(new Date());

        javaMailSender.send(message);
    }

    @Async
    public void sendHtml(String subject, String text, String sendAddress) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);

            //邮件发件人
            mimeMessageHelper.setFrom(fromUser);
            //邮件收件人 1或多个
            mimeMessageHelper.setTo(sendAddress);
            //邮件主题
            mimeMessageHelper.setSubject(subject);
            //邮件内容
            mimeMessageHelper.setText(text, true);
            //邮件发送时间
            mimeMessageHelper.setSentDate(new Date());

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
