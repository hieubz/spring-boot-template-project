package com.example.demo.shared.utils;

import com.example.demo.shared.exception.EmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.mail.internet.MimeMessage;
import java.io.File;

@Service
@Slf4j
@RequiredArgsConstructor
public class BaseEmailSender {

  private final JavaMailSender sender;

  public void sendEmail(String fromAddress, String toAddress, String[] ccAddresses, String subject,
                        String emailBody, String attachmentFileName, File file) throws EmailException {
    try {
      if (!StringUtils.hasLength(fromAddress)
          || !StringUtils.hasLength(toAddress)
          || !StringUtils.hasLength(subject)) {
        log.error("> ERROR: Missing required input for sending email");
        throw new EmailException("Missing required input for sending email");
      }
      MimeMessage message = sender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message);
      helper.setFrom(fromAddress);
      helper.setTo(toAddress);
      helper.setText(emailBody, true);
      helper.setSubject(subject);
      if (!ArrayUtils.isEmpty(ccAddresses)) {
        helper.setCc(ccAddresses);
      }
      if (file != null && StringUtils.hasLength(attachmentFileName)) {
        helper.addAttachment(attachmentFileName, file);
      }
      sender.send(message);
      log.info(
          "> BaseEmailSender sent email from {} to {}, with cc {}",
          fromAddress,
          toAddress,
          ccAddresses);
    } catch (Exception e) {
      log.error("> ERROR: Something went wrong when sending email: ", e);
      throw new EmailException("Something went wrong when sending email");
    }
  }
}
